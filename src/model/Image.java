package model;

import model.Pixel.PixelChannel;

/**
 * Represents an image as a 2D array of Pixels.
 */
public class Image {

  private Pixel[][] pixels;

  /**
   * Creates a rectangular image of black pixels, with given width and height.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @throws IllegalArgumentException if either dimension is less than or equal to zero
   */
  public Image(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid image dimension");
    }
    pixels = new Pixel[width][height];
    for (int col = 0; col < width; col += 1) {
      for (int row = 0; row < height; row += 1) {
        pixels[col][row] = new Pixel(0, 0, 0);
      }
    }
  }

  /**
   * Gets a copy of the pixel at the given coordinates.
   *
   * @param xx the x coordinate of the target pixel
   * @param yy the y coordinate of the target pixel
   * @return the indicated pixel
   */
  public Pixel getPixel(int xx, int yy) {
    Pixel pixel = pixels[xx][yy];
    return new Pixel(pixel.getChannel(PixelChannel.RED),
        pixel.getChannel(PixelChannel.GREEN),
        pixel.getChannel(PixelChannel.BLUE));
  }

  /**
   * Places the given pixel at the given coordinates in the image.
   *
   * @param xx    the x coordinate of the pixel's location
   * @param yy    the y coordinate of the pixel's location
   * @param pixel the pixel to be placed at the given coordinates
   */
  public void setPixel(int xx, int yy, Pixel pixel) {
    pixels[xx][yy] = pixel;
  }

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  public int getWidth() {
    return pixels.length;
  }

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  public int getHeight() {
    return pixels[0].length;
  }
}
