package utils;

import model.Layer;
import model.Pixel;
import model.Pixel.PixelChannel;

/**
 * Util methods for the testing classes.
 */
public class TestingUtil {

  /**
   * Checks if two Layers are identical.
   *
   * @param expected expected output
   * @param actual   actual output
   * @return true for equal and false otherwise
   */
  public static boolean equalLayers(Layer expected, Layer actual) {
    if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
      return false;
    }
    for (int xx = 0; xx < expected.getWidth(); xx++) {
      for (int yy = 0; yy < expected.getHeight(); yy++) {
        if (!equalPixels(expected.getPixel(xx, yy), actual.getPixel(xx, yy))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks if two Pixels are identical.
   *
   * @param expected expected output
   * @param actual   actual output
   * @return true for equal and false otherwise
   */
  public static boolean equalPixels(Pixel expected, Pixel actual) {
    if (expected.getChannel(PixelChannel.RED) != actual.getChannel(PixelChannel.RED)) {
      return false;
    } else if (expected.getChannel(PixelChannel.GREEN) != actual.getChannel(PixelChannel.GREEN)) {
      return false;
    } else {
      return expected.getChannel(PixelChannel.BLUE) == actual.getChannel(PixelChannel.BLUE);
    }
  }
}
