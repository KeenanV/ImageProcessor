package controller;

import model.Image;

/**
 * Represents the interface which the user uses to interact with the model of the image processor
 * and edit, load, and save images.
 */
public interface Controller {

  /**
   * Starts the image processor.
   *
   * @throws IllegalStateException if writing to the appendable fails
   */
  void runImageProcessor() throws IllegalArgumentException, IllegalStateException;

  void createCommand(String[] args) throws IllegalArgumentException;

  void currentCommand(String[] args);

  void removeCommand(String[] args);

  void loadCommand(String[] args);

  void newCommand();

  void invisibleCommand(String[] args);

  void saveCommand(String[] args);

  void blurCommand();

  void sharpenCommand();

  void sepiaCommand();

  void grayCommand();

  void newImageCommand(String[] args);

  Image getImage();

}
