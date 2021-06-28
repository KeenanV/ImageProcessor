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

  /**
   * Executes the "create" command which creates layers from a LayerCreator.
   *
   * @param args arguments to be passed to the command
   * @throws IllegalArgumentException if input is invalid
   */
  void createCommand(String[] args) throws IllegalArgumentException;

  /**
   * Executes the "current" command which makes a given layer the topmost and visible.
   *
   * @param args arguments to be passed to the command
   */
  void currentCommand(String[] args);

  /**
   * Executes the "remove" command which removes the given layer.
   *
   * @param args arguments to be passed to the command
   */
  void removeCommand(String[] args);

  /**
   * Executes the "load" command which loads the given project or image.
   *
   * @param args arguments to be passed to the command
   */
  void loadCommand(String[] args);

  /**
   * Executes the "new" command which creates a new blank layer.
   */
  void newCommand();

  /**
   * Executes the "invisible" command which changes the visibility of the given layer.
   *
   * @param args arguments to be passed to the command
   */
  void invisibleCommand(String[] args);

  /**
   * Executes the "save" command which saves the topmost visible layer as an image or the whole
   * project.
   *
   * @param args arguments to be passed to the command.
   */
  void saveCommand(String[] args);

  /**
   * Executes the "blur" command which blurs the top layer.
   */
  void blurCommand();

  /**
   * Executes the "sharpen" command which sharpens the top layer.
   */
  void sharpenCommand();

  /**
   * Executes the "sepia" command which applies the sepia color transformation to the top layer.
   */
  void sepiaCommand();

  /**
   * Executes the "gray" command which applies the gray color transformation to the top layer.
   */
  void grayCommand();

  /**
   * Executes the "new image" command which creates a new image of the specified dimensions.
   *
   * @param args arguments to be passed to the command
   */
  void newImageCommand(String[] args);

  /**
   * Gets the image currently in use by this controller.
   *
   * @return the current Image
   */
  Image getImage();

}
