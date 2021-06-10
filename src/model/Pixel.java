package model;

/**
 * Represents a pixel in an image, with three variables for the red, green, and blue channels.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * The three channels of the pixel's color.
   */
  public enum PixelChannel {
    RED, GREEN, BLUE
  }

  /**
   * Creates a new pixel, with its channels set to the three given values.
   *
   * @param red   the pixel's red channel value
   * @param green the pixel's green channel value
   * @param blue  the pixel's blue channel value
   * @throws IllegalArgumentException if any given channel value is below 0 or above 255
   */
  public Pixel(int red, int green, int blue) {
    if (invalidChannelValue(red) || invalidChannelValue(green) || invalidChannelValue(blue)) {
      throw new IllegalArgumentException("Invalid channel value");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Checks to see if the given value is a valid channel value (between 0 and 255 inclusive).
   *
   * @param value the value to check
   * @return Whether the value is a valid channel value
   */
  private boolean invalidChannelValue(int value) {
    return value >= 0 && value <= 255;
  }

  /**
   * Return the value of one of the pixel's channels, as indicated by the PixelChannel value.
   *
   * @param channel The channel whose value this method will return
   * @return The value in the indicated channel
   * @throws IllegalArgumentException if the given PixelChannel is invalid
   */
  public int getChannel(PixelChannel channel) {
    switch (channel) {
      case RED:
        return red;
      case GREEN:
        return green;
      case BLUE:
        return blue;
      default:
        throw new IllegalArgumentException();
    }
  }
}
