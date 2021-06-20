package controller;

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

}
