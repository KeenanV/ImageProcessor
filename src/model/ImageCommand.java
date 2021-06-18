package model;

/**
 * Represents a command that could be used upon an image.
 */
public interface ImageCommand {

  /**
   * Executes this command upon the given image.
   *
   * @param image the image to use this command on
   * @throws IllegalArgumentException if the given image is invalid
   */
  Image start(Image image);
}
