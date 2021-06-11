package model;

/**
 * Creates images based on pre-written algorithms.
 */
public class ImageCreator {

  /**
   * Creates a checkerboard with black in the top-right corner.
   *
   * @param squareSize the side length of a square on the board, in pixels
   * @param numSquares the number of squares on each side of the checkerboard
   * @return the resulting checkerboard
   * @throws IllegalArgumentException if either input is invalid (less than or equal to 0)
   */
  public static Image createCheckerboard(int squareSize, int numSquares) {
    if (squareSize <= 0 || numSquares <= 0) {
      throw new IllegalArgumentException();
    }
    int sideLength = squareSize * numSquares;
    Image image = new Image(sideLength, sideLength);
    for (int x = 0; x < sideLength; x += 1) {
      for (int y = 0; y < sideLength; y += 1) {
        if ((x / squareSize) % 2 != (y / squareSize) % 2) {
          image.setPixel(x, y, new Pixel(255, 255, 255));
        }
      }
    }
    return image;
  }

  public static Image createSolidSquare(int size, int red, int green, int blue) {
    if (size <= 0 || red < 0 || green < 0 || blue < 0 || red > 255 ||  green > 255 || blue > 255) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    Image image = new Image(size, size);
    for (int xx = 0; xx < size; xx++) {
      for (int yy = 0; yy < size; yy++) {
        image.setPixel(xx, yy, new Pixel(red, green, blue));
      }
    }

    return image;
  }
}
