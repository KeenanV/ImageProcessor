package controller;

import java.io.IOException;
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
import view.SimpleTextView;
import view.View;

/**
 * Represents a text-based implementation of a controller that a user uses to interact with the
 * image processor.
 */
public class SimpleController implements Controller {

  private final Scanner scan;
  private final View view;
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

  private void checkIOException() throws IllegalStateException {
    IOException rv = scan.ioException();
    if (rv != null) {
      throw new IllegalStateException("Could not read from readable.");
    }
  }

  private int[] getColor(String color) throws IllegalArgumentException {
    switch (color) {
      case "red":
        return new int[]{255, 0, 0};
      case "green":
        return new int[]{0, 255, 0};
      case "blue":
        return new int[]{0, 0, 255};
      case "white":
        return new int[]{255, 255, 255};
      case "black":
        return new int[]{0, 0, 0};
      default:
        throw new IllegalArgumentException("Invalid color.");
    }
  }

  private int stringToLayerNum(String layerNum) {
    try {
      return Integer.parseInt(layerNum);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid layer number");
    }
  }

  private void runCommand(String command, String[] args) throws IllegalArgumentException {
    switch (command) {
      case "create":
        if (args[0].equals("checkerboard") && image.getHeight() == image.getWidth()) {
          image.insertLayer(image.getNumLayers() - 1,
              LayerCreator.createCheckerboard(10, image.getWidth() / 10));
          return;
        }
        if (args.length == 2) {
          int[] rgb = getColor(args[0]);
          if (args[1].equals("rectangle")) {
            image.insertLayer(image.getNumLayers() - 1, LayerCreator
                .createSolidRect(image.getWidth(), image.getHeight(), rgb[0], rgb[1], rgb[2]));
            return;
          } else if (args[1].equals("square") && image.getWidth() == image.getHeight()) {
            image.insertLayer(image.getNumLayers() - 1,
                LayerCreator.createSolidSquare(image.getHeight(), rgb[0], rgb[1], rgb[2]));
            return;
          }
        }
        break;
      case "current":
        image.moveToTop(stringToLayerNum(args[0]) - 1);
        return;
      case "remove":
        image.removeLayer(stringToLayerNum(args[0]) - 1);
        return;
      case "load":
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
        return;
      case "invisible":
        image.getLayer(stringToLayerNum(args[0]) - 1).setVisible(false);
        return;
      case "save":
        if (args[0].equals("project") && args.length >= 2) {
          ImageUtil.writeMultiLayerImage(args[1], image);
        } else {
          ImageUtil.writeFile(args[0], image.getTopVisibleLayer());
        }
        return;
    }
    throw new IllegalArgumentException("Unable to execute command.");
  }

  private void runCommand(String command) throws IllegalArgumentException {
    switch (command) {
      case "new":
        image.addLayer(new SimpleLayer(image.getWidth(), image.getHeight()));
        break;
      case "print":
        try {
          view.printLayers();
        } catch (IOException e) {
          throw new IllegalArgumentException("Unable to write to appendable.");
        }
        break;
      case "blur":
        image.insertLayer(image.getNumLayers() - 1,
            new Filter(FilterMatrix.BLUR).start(image.getTopVisibleLayer()));
        break;
      case "sharpen":
        image.insertLayer(image.getNumLayers() - 1,
            new Filter(FilterMatrix.SHARPEN).start(image.getTopVisibleLayer()));
        break;
      case "sepia":
        image.insertLayer(image.getNumLayers() - 1,
            new ColorTransformation(ColorTransformationMatrix.SEPIA)
                .start(image.getTopVisibleLayer()));
        break;
      case "gray":
        image.insertLayer(image.getNumLayers() - 1,
            new ColorTransformation(ColorTransformationMatrix.GRAYSCALE)
                .start(image.getTopVisibleLayer()));
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
}
