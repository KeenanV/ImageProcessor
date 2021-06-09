package model;

/**
 * Applies the given matrix to the channels of each pixel in a given image.
 */
public class ColorTransformation implements ImageCommand {

  public enum ColorTransformationMatrix {
    GRAYSCALE(new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}}),
    SEPIA(new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}});

    private final double[][] matrix;

    ColorTransformationMatrix(double[][] matrix) {
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
   * Creates a new color transformation command with the given 3x3 matrix.
   *
   * @param matrix the color transformation matrix
   * @throws IllegalArgumentException if the given matrix is not 3x3
   */
  public ColorTransformation(double[][] matrix) {
    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Invalid color transformation matrix");
    }
    this.matrix = matrix;
  }

  /**
   * Calls the constructor for a double[][] using the given ColorTransformationMatrix.
   *
   * @param matrix the ColorTransformationMatrix
   */
  public ColorTransformation(ColorTransformationMatrix matrix) {
    this(matrix.getMatrix());
  }

  @Override
  public Image go(Image image) {
    return image;
  }
}
