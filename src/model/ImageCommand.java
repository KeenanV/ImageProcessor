package model;

/**
 * Represents a command that could be used upon an image.
 */
public interface ImageCommand {

  /**
   * Executes this command upon the given image.
   *
   * @param image the image to use this command on
   * @throws IllegalStateException if this command cannot be used on the given image
   */
  Image go(Image image);
}
