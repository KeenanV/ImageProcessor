package model;

/**
 * Represents a pixel in an image.
 */
public interface Pixel {

  /**
   * The three channels of the pixel's color, and a fourth for its transparency.
   */
  enum PixelChannel {
    RED, GREEN, BLUE
  }

  /**
   * Return the value of one of the pixel's channels, as indicated by the PixelChannel value.
   *
   * @param channel The channel whose value this method will return
   * @return The value in the indicated channel
   * @throws IllegalArgumentException if the given PixelChannel is invalid
   */
  int getChannel(PixelChannel channel);
}
