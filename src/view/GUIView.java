package view;

import java.awt.Color;

public interface GUIView extends TextView {

  /**
   * Updates the GUI
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
   * @param proj true if this is a project file false if it is an image file
   * @return the chosen file path
   */
  String openFile(boolean proj);

  /**
   * Opens the file save dialogue and saves the current image or project to a file.
   *
   * @param proj true if saving as a project file false if saving as image file
   */
  void saveFile(boolean proj);

  String[] newImage();

}
