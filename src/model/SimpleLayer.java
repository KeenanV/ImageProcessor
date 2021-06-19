package model;

import model.Pixel.PixelChannel;

/**
 * Represents an image as a 2D array of Pixels.
 */
public class SimpleLayer implements Layer {

  private final Pixel[][] pixels;
  private boolean visible;

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
    visible = true;
    pixels = new Pixel[width][height];
    for (int col = 0; col < width; col += 1) {
      for (int row = 0; row < height; row += 1) {
        pixels[col][row] = new SimplePixel(0, 0, 0);
      }
    }
  }

  @Override
  public Pixel getPixel(int xx, int yy) throws IllegalArgumentException {
    if (invalidIndex(xx, yy)) {
      throw new IllegalArgumentException("Invalid pixel index.");
    }
    Pixel pixel = pixels[xx][yy];
    return new SimplePixel(pixel.getChannel(PixelChannel.RED),
        pixel.getChannel(PixelChannel.GREEN),
        pixel.getChannel(PixelChannel.BLUE));
  }

  @Override
  public void setPixel(int xx, int yy, Pixel pixel) throws IllegalArgumentException {
    if (invalidIndex(xx, yy)) {
      throw new IllegalArgumentException("Invalid pixel index.");
    }
    pixels[xx][yy] = pixel;
  }

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  @Override
  public int getWidth() {
    return pixels.length;
  }

  @Override
  public int getHeight() {
    return pixels[0].length;
  }

  @Override
  public boolean getVisible() {
    return visible;
  }

  private boolean invalidIndex(int xx, int yy) {
    return xx < 0 || xx >= pixels.length || yy < 0 || yy >= pixels[0].length;
  }
}
