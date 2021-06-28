package view;

import java.awt.Color;
import view.SimpleGUIView.FileType;

/**
 * Extension of the text view interface that adds a GUI functionality to it.
 */
public interface GUIView extends TextView {

  /**
   * Updates the GUI and repaints it.
   */
  void refresh();

  /**
   * Opens the color chooser dialogue and returns the chosen color.
   *
   * @return the chosen color
   */
  Color chooseColor();

  /**
   * Opens the file chooser dialogue and returns the path of the chosen file.
   *
   * @param type true if this is a project file false if it is an image file
   * @return the chosen file path
   */
  String openFile(FileType type);

  /**
   * Opens the file save dialogue and saves the current image or project to a file.
   *
   * @param proj true if saving as a project file false if saving as image file
   */
  void saveFile(boolean proj);

  /**
   * Opens a dialog to get input from the user to use for creating a new image.
   *
   * @return an array of arguments holding the dimensions of the image
   */
  String[] newImage();

}
