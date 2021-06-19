package model;

import model.Pixel.PixelChannel;
import utils.ImageUtil;

/**
 * Overlays this matrix centered on a pixel in the given layer, multiplies the matrix's value by a
 * channel in each overlaid pixel, then sets the value of that channel in the target pixel to the
 * sum of all the new values. Repeats for every channel of every pixel in the given layer.
 */
public class Filter implements LayerCommand {

  /**
   * Represents a premade matrix for a filter.
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
  public Layer start(Layer layer) {
    if (layer == null) {
      throw new IllegalArgumentException();
    }

    Layer newImage = new SimpleLayer(layer.getWidth(), layer.getHeight());
    for (int xx = 0; xx < layer.getWidth(); xx++) {
      for (int yy = 0; yy < layer.getHeight(); yy++) {
        newImage.setPixel(xx, yy, applyToPixel(layer, xx, yy));
      }
    }
    return newImage;
  }

  /**
   * Modifies the pixel at the given position in the given layer according to this object's matrix.
   * NOTE: does not modify the given layer in any way.
   *
   * @param layer  the layer containing the target pixel
   * @param pixelX the x coordinate of the target pixel
   * @param pixelY the y coordinate of the target pixel
   * @return a new pixel, which would fit at the target location in the filtered layer
   * @throws IllegalArgumentException if the given layer is null or the given coordinates are out of
   *                                  bounds on the given layer
   */
  private Pixel applyToPixel(Layer layer, int pixelX, int pixelY) {
    int newRed = 0;
    int newGreen = 0;
    int newBlue = 0;

    for (int xx = 0; xx < matrix.length; xx++) {
      int xPosn = (pixelX + xx) - ((matrix.length - 1) / 2);
      if (xPosn >= 0 && xPosn < layer.getWidth()) {
        for (int yy = 0; yy < matrix[0].length; yy++) {
          int yPosn = (pixelY + yy) - ((matrix[0].length - 1) / 2);
          if (yPosn >= 0 && yPosn < layer.getHeight()) {
            Pixel pixel = layer.getPixel(xPosn, yPosn);
            newRed += pixel.getChannel(PixelChannel.RED) * matrix[xx][yy];
            newGreen += pixel.getChannel(PixelChannel.GREEN) * matrix[xx][yy];
            newBlue += pixel.getChannel(PixelChannel.BLUE) * matrix[xx][yy];
          }
        }
      }
    }

    return new SimplePixel(
        Math.max(0, Math.min(newRed, 255)),
        Math.max(0, Math.min(newGreen, 255)),
        Math.max(0, Math.min(newBlue, 255)));
  }
}
