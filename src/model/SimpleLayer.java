package model;

import model.Pixel.PixelChannel;

/**
 * Represents an image as a 2D array of Pixels.
 */
public class SimpleLayer implements Layer {

  private Pixel[][] pixels;

  /**
   * Creates a rectangular image of black pixels, with given width and height.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @throws IllegalArgumentException if either dimension is less than or equal to zero
   */
  public SimpleLayer(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid image dimension");
    }
    pixels = new Pixel[width][height];
    for (int col = 0; col < width; col += 1) {
      for (int row = 0; row < height; row += 1) {
        pixels[col][row] = new SimplePixel(0, 0, 0, 100);
      }
    }
  }

  @Override
  public Pixel getPixel(int xx, int yy) {
    Pixel pixel = pixels[xx][yy];
    return new SimplePixel(pixel.getChannel(PixelChannel.RED),
        pixel.getChannel(PixelChannel.GREEN),
        pixel.getChannel(PixelChannel.BLUE),
        pixel.getChannel(PixelChannel.TRANSPARENCY));
  }

  @Override
  public void setPixel(int xx, int yy, Pixel pixel) {
    pixels[xx][yy] = pixel;
  }

  @Override
  public int getWidth() {
    return pixels.length;
  }

  @Override
  public int getHeight() {
    return pixels[0].length;
  }
}
