package model;

/**
 * Overlays this matrix centered on a pixel in the given image, multiplies the matrix's value by a
 * channel in each overlaid pixel, then sets the value of that channel in the target pixel to the
 * sum of all the new values. Repeats for every channel of every pixel in the given image.
 */
public class Filter implements ImageCommand {

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
   */
  public Filter(double[][] matrix) {
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
    return image;
  }
}
