package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.Layer;
import view.GUIView;
import view.SimpleGUIView;

public class SimpleGUIController implements Controller, ActionListener, ListSelectionListener {

  private final GUIView view;
  private final Controller delegate;
  private Image image;
  private final JFrame frame;
  private int selectedLayer;
  private Color color = Color.WHITE;

  public SimpleGUIController(Image image) {
    this.image = image;
    this.view = new SimpleGUIView(this.image, this, this);
    this.frame = (JFrame) this.view;
    this.delegate = new SimpleController(this.image, this.view);
  }

  @Override
  public void runImageProcessor() throws IllegalArgumentException, IllegalStateException {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }
  }

  @Override
  public void createCommand(String[] args) throws IllegalArgumentException {
    delegate.createCommand(args);
  }

  @Override
  public void currentCommand(String[] args) {
    delegate.currentCommand(args);
  }

  @Override
  public void removeCommand(String[] args) {
    delegate.removeCommand(args);
  }

  @Override
  public void loadCommand(String[] args) {
    delegate.loadCommand(args);
  }

  @Override
  public void newCommand() {
    delegate.newCommand();
  }

  @Override
  public void invisibleCommand(String[] args) {
    delegate.invisibleCommand(args);
  }

  @Override
  public void saveCommand(String[] args) {
    delegate.saveCommand(args);
  }

  @Override
  public void blurCommand() {
    delegate.blurCommand();
  }

  @Override
  public void sharpenCommand() {
    delegate.sharpenCommand();
  }

  @Override
  public void sepiaCommand() {
    delegate.sepiaCommand();
  }

  @Override
  public void grayCommand() {
    delegate.grayCommand();
  }

  @Override
  public void newImageCommand(String[] args) {
    delegate.newImageCommand(args);
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String filename;
    try {
      switch (e.getActionCommand()) {
        case "Blur":
          blurCommand();
          break;
        case "Sharpen":
          sharpenCommand();
          break;
        case "Grayscale":
          grayCommand();
          break;
        case "Sepia":
          sepiaCommand();
          break;
        case "Move to Top":
          currentCommand(new String[]{Integer.toString(selectedLayer + 1)});
          break;
        case "Hide/Show":
          invisibleCommand(new String[]{Integer.toString(selectedLayer + 1)});
          break;
        case "Delete Layer":
          removeCommand(new String[]{Integer.toString(selectedLayer + 1)});
          break;
        case "Add Layer":
          newCommand();
          break;
        case "Load Image":
          filename = view.openFile(false);
          loadCommand(new String[]{filename});
          image = delegate.getImage();
          break;
        case "Save Image":
          view.saveFile(false);
          break;
        case "Load Project":
          filename = view.openFile(true);
          loadCommand(new String[]{"project", filename});
          image = delegate.getImage();
          break;
        case "Save Project":
          view.saveFile(true);
          break;
        case "Color Chooser":
          color = view.chooseColor();
          break;
        case "Create Checkerboard":
          try {
            createCommand(new String[]{"checkerboard"});
          } catch (IllegalArgumentException f) {
            writeMessage("Please create a layer first.");
          }
          break;
        case "Create Rectangle":
          try {
            createCommand(
                new String[]{Integer.toString(color.getRed()), Integer.toString(color.getGreen()),
                    Integer.toString(color.getBlue()), "rectangle"});
          } catch (IllegalArgumentException f) {
            writeMessage("Please create a layer first.");
          }
          break;
        case "New Image":
          newImageCommand(view.newImage());
          image = delegate.getImage();
          break;
      }
    } catch (Exception f) {
      writeMessage("An error occurred.");
    }
    view.setImage(image);
    view.refresh();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    JList<String> layerNames = (JList<String>) e.getSource();
    selectedLayer = layerNames.getSelectedIndex();
  }

  private void writeMessage(String message) throws IllegalArgumentException {
    try {
      view.renderMessage("Please create a layer first.");
    } catch (IOException ioException) {
      throw new IllegalArgumentException("Unable to render message.");
    }
  }
}
