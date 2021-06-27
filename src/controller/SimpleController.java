package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.Layer;
import model.LayerCreator;
import model.SimpleImage;
import model.SimpleLayer;
import utils.ImageUtil;
import view.SimpleGUIView;
import view.SimpleTextView;
import view.TextView;

/**
 * Represents a text-based implementation of a controller that a user uses to interact with the
 * image processor.
 */
public class SimpleController implements Controller {

  private final Scanner scan;
  private final TextView view;
  private Image image;
  private boolean quit = false;

  /**
   * Creates a controller with the given arguments.
   *
   * @param image image to be used with the controller
   * @param ap    Appendable to be used for output
   * @param rd    Readable to be used for input
   * @throws IllegalArgumentException if arguments are null
   */
  public SimpleController(Image image, Appendable ap, Readable rd) throws IllegalArgumentException {
    if (image == null || ap == null || rd == null) {
      throw new IllegalArgumentException("cannot have null args.");
    }
    this.image = image;
    this.view = new SimpleTextView(ap, image);
    this.scan = new Scanner(rd);
  }

  /**
   * Creates a controller with the given arguments and creates a blank image of size 400x400
   * pixels.
   *
   * @param ap Appendable to be used for output
   * @param rd Readable to be used for input
   * @throws IllegalArgumentException if arguments are null
   */
  public SimpleController(Appendable ap, Readable rd) throws IllegalArgumentException {
    if (ap == null || rd == null) {
      throw new IllegalArgumentException("Cannot have null args.");
    }
    this.image = new SimpleImage(100, 100);
    this.view = new SimpleTextView(ap, image);
    this.scan = new Scanner(rd);
  }

  public SimpleController(Image image, TextView view) {
    this.image = image;
    this.view = view;
    String nullInput = "";
    this.scan = new Scanner(new InputStreamReader(new ByteArrayInputStream(nullInput.getBytes(
        StandardCharsets.UTF_8))));
  }

  private void checkIOException() throws IllegalStateException {
    IOException rv = scan.ioException();
    if (rv != null) {
      throw new IllegalStateException("Could not read from readable.");
    }
  }

  private int stringToLayerNum(String layerNum) {
    try {
      return Integer.parseInt(layerNum);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid layer number");
    }
  }

  @Override
  public void createCommand(String[] args) throws IllegalArgumentException {
    if (args[0].equals("checkerboard") && image.getHeight() == image.getWidth()) {
      image.insertLayer(image.getNumLayers() - 1,
          LayerCreator.createCheckerboard(10, image.getWidth() / 10));
      return;
    }
    if (args.length == 4) {
      int[] rgb = {Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])};
      if (args[3].equals("rectangle")) {
        image.insertLayer(image.getNumLayers() - 1, LayerCreator
            .createSolidRect(image.getWidth(), image.getHeight(), rgb[0], rgb[1], rgb[2]));
        return;
      }
    }
    throw new IllegalArgumentException("Invalid command.");
  }

  @Override
  public void currentCommand(String[] args) {
    image.moveToTop(stringToLayerNum(args[0]) - 1);
  }

  @Override
  public void removeCommand(String[] args) {
    image.removeLayer(stringToLayerNum(args[0]) - 1);
  }

  @Override
  public void loadCommand(String[] args) {
    if (args[0].equals("project") && args.length >= 2) {
      image = ImageUtil.readMultiLayerImage(args[1]);
      view.setImage(image);
    } else if (image.getNumLayers() == 0) {
      Layer layer = ImageUtil.readFile(args[0]);
      image = new SimpleImage(layer.getWidth(), layer.getHeight());
      image.addLayer(layer);
      view.setImage(image);
    } else {
      image.insertLayer(image.getNumLayers() - 1, ImageUtil.readFile(args[0]));
    }
  }

  @Override
  public void newCommand() {
    image.addLayer(new SimpleLayer(image.getWidth(), image.getHeight()));
  }

  @Override
  public void invisibleCommand(String[] args) {
    boolean visible = image.getLayer(stringToLayerNum(args[0]) - 1).getVisible();
    image.getLayer(stringToLayerNum(args[0]) - 1).setVisible(!visible);
  }

  @Override
  public void saveCommand(String[] args) {
    if (args[0].equals("project") && args.length >= 2) {
      ImageUtil.writeMultiLayerImage(args[1], image);
    } else {
      ImageUtil.writeFile(args[0], image.getTopVisibleLayer());
    }
  }

  @Override
  public void blurCommand() {
    image.insertLayer(image.getNumLayers() - 1,
        new Filter(FilterMatrix.BLUR).start(image.getTopVisibleLayer()));
  }

  @Override
  public void sharpenCommand() {
    image.insertLayer(image.getNumLayers() - 1,
        new Filter(FilterMatrix.SHARPEN).start(image.getTopVisibleLayer()));
  }

  @Override
  public void sepiaCommand() {
    image.insertLayer(image.getNumLayers() - 1,
        new ColorTransformation(ColorTransformationMatrix.SEPIA)
            .start(image.getTopVisibleLayer()));
  }

  @Override
  public void grayCommand() {
    image.insertLayer(image.getNumLayers() - 1,
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE)
            .start(image.getTopVisibleLayer()));
  }

  @Override
  public void newImageCommand(String[] args) {
    image = new SimpleImage(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
  }

  private void runCommand(String command, String[] args) throws IllegalArgumentException {
    switch (command) {
      case "create":
        createCommand(args);
        break;
      case "current":
        currentCommand(args);
        return;
      case "remove":
        removeCommand(args);
        return;
      case "load":
        loadCommand(args);
        return;
      case "invisible":
        invisibleCommand(args);
        return;
      case "save":
        saveCommand(args);
        return;
      default:
    }
  }

  private void runCommand(String command) throws IllegalArgumentException {
    switch (command) {
      case "new":
        newCommand();
        break;
      case "print":
        try {
          view.printLayers();
        } catch (IOException e) {
          throw new IllegalArgumentException("Unable to write to appendable.");
        }
        break;
      case "blur":
        blurCommand();
        break;
      case "sharpen":
        sharpenCommand();
        break;
      case "sepia":
        sepiaCommand();
        break;
      case "gray":
        grayCommand();
        break;
      case "quit":
        quit = true;
        break;
      default:
        throw new IllegalArgumentException("invalid command.");
    }
  }

  @Override
  public void runImageProcessor() throws IllegalStateException {
    try {
      view.renderMessage("Welcome to Image Processor\n");
      view.renderMessage("Input a command:\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable.");
    }

    while (!quit) {
      String[] args = scan.nextLine().split("[ \n]+");
      checkIOException();
      if (args.length == 0) {
        try {
          view.renderMessage("Please input a command.\n");
        } catch (IOException e) {
          throw new IllegalStateException("Could not write to appendable");
        }
      }
      try {
        if (args.length > 1) {
          runCommand(args[0], Arrays.copyOfRange(args, 1, args.length));
        } else {
          runCommand(args[0]);
        }
      } catch (IllegalArgumentException e) {
        try {
          view.renderMessage("Unable to run command. Try again.\n");
        } catch (IOException f) {
          throw new IllegalStateException("Unable to write to appendable.");
        }
      }
    }

    try {
      view.renderMessage("Thanks for using Image Processor.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
  }

  /**
   * For testing purposes only.
   *
   * @return the current image being worked on
   */
  public Image getImage() {
    return image;
  }
}
