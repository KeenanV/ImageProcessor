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
   * @throws IllegalArgumentException if the pixel coordinates are invalid
   */
  Pixel getPixel(int xx, int yy) throws IllegalArgumentException;

  /**
   * Places the given pixel at the given coordinates in the layer.
   *
   * @param xx    the x coordinate of the pixel's location
   * @param yy    the y coordinate of the pixel's location
   * @param pixel the pixel to be placed at the given coordinates
   * @throws IllegalArgumentException if the pixel coordinates are invalid
   */
  void setPixel(int xx, int yy, Pixel pixel) throws IllegalArgumentException;

  /**
   * Sets the visibility of the layer.
   * @param visible true for visible and false for invisible
   */
  void setVisible(boolean visible);

  /**
   * Gets the width of the layer.
   *
   * @return the width of the layer in pixels
   */
  int getWidth();

  /**
   * Gets the height of the layer.
   *
   * @return the height of the layer in pixels
   */
  int getHeight();

  /**
   * Gets the visibility of the layer.
   *
   * @return true for visible and false for invisible
   */
  boolean getVisible();
}
