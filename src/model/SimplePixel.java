package model;

/**
 * Represents a pixel in an image, with four variables for the red, green, blue, and transparency
 * channels.
 */
public class SimplePixel implements Pixel {

  private int red;
  private int green;
  private int blue;
  private int transparency;

  /**
   * Creates a new pixel, with its channels set to the three given values.
   *
   * @param red   the pixel's red channel value
   * @param green the pixel's green channel value
   * @param blue  the pixel's blue channel value
   * @throws IllegalArgumentException if any given channel value is below 0 or above 255
   */
  public SimplePixel(int red, int green, int blue, int transparency) {
    if (invalidChannelValue(red) || invalidChannelValue(green) || invalidChannelValue(blue)
        || transparency < 0 || transparency > 100) {
      throw new IllegalArgumentException("Invalid channel value");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.transparency = transparency;
  }

  /**
   * Checks to see if the given value is a valid channel value (between 0 and 255 inclusive).
   *
   * @param value the value to check
   * @return Whether the value is a valid channel value
   */
  private boolean invalidChannelValue(int value) {
    return value < 0 || value > 255;
  }

  @Override
  public int getChannel(PixelChannel channel) {
    switch (channel) {
      case RED:
        return red;
      case GREEN:
        return green;
      case BLUE:
        return blue;
      case TRANSPARENCY:
        return transparency;
      default:
        throw new IllegalArgumentException();
    }
  }
}
