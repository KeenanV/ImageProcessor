package model;

import model.Pixel.PixelChannel;

/**
 * Overlays this matrix centered on a pixel in the given image, multiplies the matrix's value by a
 * channel in each overlaid pixel, then sets the value of that channel in the target pixel to the
 * sum of all the new values. Repeats for every channel of every pixel in the given image.
 */
public class Filter implements ImageCommand {

  /**
   * Represents a premade matrix for a filter
   */
  public enum FilterMatrix {
    BLUR(new double[][]{
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}}),
    SHARPEN(new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}}),
    ;

    private final double[][] matrix;

    FilterMatrix(double[][] matrix) {
      this.matrix = matrix;
    }

    public double[][] getMatrix() {
      return this.matrix;
    }

    public int height() {
      return matrix.length;
    }

    public int width() {
      return matrix[0].length;
    }
  }

  double[][] matrix;

  /**
   * Creates a new filter command with the given matrix.
   *
   * @param matrix the matrix, represented by a 2D array of doubles
   * @throws IllegalArgumentException if the given matrix is null or has an even dimension
   */
  public Filter(double[][] matrix) {
    if (matrix == null || matrix.length % 2 != 1 || matrix[0].length % 2 != 1) {
      throw new IllegalArgumentException("Invalid filter matrix");
    }
    this.matrix = matrix;
  }

  /**
   * Creates a new filter command with the given FilterMatrix.
   *
   * @param matrix the filter matrix
   */
  public Filter(FilterMatrix matrix) {
    this(matrix.getMatrix());
  }

  @Override
  public Image go(Image image) {
    if (image == null) {
      throw new IllegalArgumentException();
    }

    Image newImage = new Image(image.getWidth(), image.getHeight());
    for (int x = 0; x < image.getWidth(); x += 1) {
      for (int y = 0; y < image.getHeight(); y += 1) {
        newImage.setPixel(x, y, applyToPixel(image, x, y));
      }
    }
    return newImage;
  }

  /**
   * Modifies the pixel at the given position in the given image according to this object's matrix.
   * NOTE: does not modify the given image in any way.
   * @param image the image containing the target pixel
   * @param pixelX the x coordinate of the target pixel
   * @param pixelY the y coordinate of the target pixel
   * @return a new pixel, which would fit at the target location in the filtered image
   * @throws IllegalArgumentException if the given image is null or the given coordinates are out of
   *                                  bounds on the given image
   */
  private Pixel applyToPixel(Image image, int pixelX, int pixelY) {
    if (image == null
        || pixelX < 0 || pixelX >= image.getWidth()
        || pixelY < 0 || pixelY >= image.getHeight()) {
      throw new IllegalArgumentException();
    }

    int newRed = 0;
    int newGreen = 0;
    int newBlue = 0;

    for (int x = 0; x < matrix.length; x += 1) {
      int xPosn = (pixelX + x) - ((matrix.length - 1) / 2);
      if (xPosn >= 0 && xPosn < image.getWidth()) {
        for (int y = 0; y < matrix[0].length; y += 1) {
          int yPosn = (pixelY + y) - ((matrix[0].length - 1) / 2);
          if (yPosn >= 0 && yPosn < image.getHeight()) {
            Pixel pixel = image.getPixel(xPosn, yPosn);
            newRed += pixel.getChannel(PixelChannel.RED) * matrix[x][y];
            newGreen += pixel.getChannel(PixelChannel.GREEN) * matrix[x][y];
            newBlue += pixel.getChannel(PixelChannel.BLUE) * matrix[x][y];
          }
        }
      }
    }

    return new Pixel(newRed, newGreen, newBlue);
  }
}
