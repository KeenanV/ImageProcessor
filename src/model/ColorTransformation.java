package model;

import model.Pixel.PixelChannel;
import utils.ImageUtil;

/**
 * Applies the given matrix to the channels of each pixel in a given image.
 */
public class ColorTransformation implements ImageCommand {

  /**
   * Represents a premade matrix for a certain color transformation.
   */
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
  }

  double[][] matrix;

  /**
   * Creates a new color transformation command with the given 3x3 matrix.
   *
   * @param matrix the color transformation matrix
   * @throws IllegalArgumentException if the given matrix is not 3x3
   */
  public ColorTransformation(double[][] matrix) {
    if (matrix == null || matrix.length != 3 || matrix[0].length != 3) {
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
  public Image start(Image image) {
    if (image == null) {
      throw new IllegalArgumentException();
    }

    Image newImage = new Image(image.getWidth(), image.getHeight());
    for (int xx = 0; xx < image.getWidth(); xx++) {
      for (int yy = 0; yy < image.getHeight(); yy++) {
        newImage.setPixel(xx, yy, applyToPixel(image.getPixel(xx, yy)));
      }
    }
    return newImage;
  }

  /**
   * Modifies a given pixel according to this object's matrix.
   *
   * @param pixel the pixel to be modified
   * @return the modified pixel
   * @throws IllegalArgumentException if the given pixel is null
   */
  private Pixel applyToPixel(Pixel pixel) {
    int oldRed = pixel.getChannel(PixelChannel.RED);
    int oldGreen = pixel.getChannel(PixelChannel.GREEN);
    int oldBlue = pixel.getChannel(PixelChannel.BLUE);

    int newRed = (int) ((oldRed * matrix[0][0]) +
        (oldGreen * matrix[0][1]) +
        (oldBlue * matrix[0][2]));
    int newGreen = (int) ((oldRed * matrix[1][0]) +
        (oldGreen * matrix[1][1]) +
        (oldBlue * matrix[1][2]));
    int newBlue = (int) ((oldRed * matrix[2][0]) +
        (oldGreen * matrix[2][1]) +
        (oldBlue * matrix[2][2]));
    return new Pixel(
        ImageUtil.clamp(newRed, 255),
        ImageUtil.clamp(newGreen, 255),
        ImageUtil.clamp(newBlue, 255));
  }
}
