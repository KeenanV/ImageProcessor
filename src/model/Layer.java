package model;

/**
 * Represents a layer of an Image.
 */
public interface Layer {
  /**
   * Gets a copy of the pixel at the given coordinates.
   *
   * @param xx the x coordinate of the target pixel
   * @param yy the y coordinate of the target pixel
   * @return the indicated pixel
   */
  Pixel getPixel(int xx, int yy);

  /**
   * Places the given pixel at the given coordinates in the layer.
   *
   * @param xx    the x coordinate of the pixel's location
   * @param yy    the y coordinate of the pixel's location
   * @param pixel the pixel to be placed at the given coordinates
   */
  void setPixel(int xx, int yy, Pixel pixel);

  /**
   * Gets the width of the layer.
   *
   * @return the width of the layer
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();
}
