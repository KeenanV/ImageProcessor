import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Layer;
import model.SimpleLayer;
import model.LayerCreator;
import model.Pixel;
import model.Pixel.PixelChannel;
import model.SimplePixel;
import org.junit.Test;
import utils.ImageUtil;

/**
 * Test class for the model.
 */
public class ModelTests {

  Layer checker;
  Layer white;
  Layer black;
  Layer red;
  Layer green;
  Layer blue;
  Layer gray;
  Layer sepia;
  Layer blur;
  Layer sharpen;
  Layer guitar;
  String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/test/images/";
  String pathRes = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/res/";

  private void setup() {
    checker = ImageUtil.readPPM(path + "checker.ppm");
    white = ImageUtil.readPPM(path + "white.ppm");
    black = ImageUtil.readPPM(path + "black.ppm");
    red = ImageUtil.readPPM(path + "red.ppm");
    green = ImageUtil.readPPM(path + "green.ppm");
    blue = ImageUtil.readPPM(path + "blue.ppm");
    gray = ImageUtil.readPPM(pathRes + "guitarGray.ppm");
    sepia = ImageUtil.readPPM(pathRes + "guitarSepia.ppm");
    blur = ImageUtil.readPPM(pathRes + "guitarBlur.ppm");
    sharpen = ImageUtil.readPPM(pathRes + "guitarSharpen.ppm");
    guitar = ImageUtil.readPPM(pathRes + "guitar.ppm");
  }

  private boolean equalLayers(Layer expected, Layer actual) {
    if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
      return false;
    }
    for (int xx = 0; xx < expected.getWidth(); xx++) {
      for (int yy = 0; yy < expected.getHeight(); yy++) {
        if (expected.getPixel(xx, yy).getChannel(PixelChannel.RED)
            != actual.getPixel(xx, yy).getChannel(PixelChannel.RED)) {
          return false;
        } else if (expected.getPixel(xx, yy).getChannel(PixelChannel.GREEN)
            != actual.getPixel(xx, yy).getChannel(PixelChannel.GREEN)) {
          return false;
        } else if (expected.getPixel(xx, yy).getChannel(PixelChannel.BLUE)
            != actual.getPixel(xx, yy).getChannel(PixelChannel.BLUE)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean equalPixels(Pixel expected, Pixel actual) {
    if (expected.getChannel(PixelChannel.RED) != actual.getChannel(PixelChannel.RED)) {
      return false;
    } else if (expected.getChannel(PixelChannel.GREEN) != actual.getChannel(PixelChannel.GREEN)) {
      return false;
    } else if (expected.getChannel(PixelChannel.BLUE) != actual.getChannel(PixelChannel.BLUE)) {
      return false;
    } else {
      return expected.getChannel(PixelChannel.TRANSPARENCY) ==
             actual.getChannel(PixelChannel.TRANSPARENCY);
    }
  }

  @Test
  public void readPPMTest() {
    setup();
    assertEquals(100, white.getHeight());
    assertEquals(100, white.getWidth());
    assertTrue(equalPixels(white.getPixel(10, 10),
               new SimplePixel(255, 255, 255, 100)));

    assertEquals(100, black.getHeight());
    assertEquals(100, black.getWidth());
    assertTrue(equalPixels(black.getPixel(10, 10),
                           new SimplePixel(0, 0, 0, 100)));

    assertEquals(100, red.getHeight());
    assertEquals(100, red.getWidth());
    assertTrue(equalPixels(red.getPixel(10, 10),
                           new SimplePixel(255, 0, 0, 100)));

    assertEquals(100, green.getHeight());
    assertEquals(100, green.getWidth());
    assertTrue(equalPixels(green.getPixel(10, 10),
                           new SimplePixel(0, 255, 0, 100)));

    assertEquals(100, blue.getHeight());
    assertEquals(100, blue.getWidth());
    assertTrue(equalPixels(blue.getPixel(10, 10),
                           new SimplePixel(0, 0, 255, 100)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMFileNotFoundTest() {
    ImageUtil.readPPM("random.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMInvalidPPMFileTest() {
    ImageUtil.readPPM(path + "p6.ppm");
  }

  @Test
  public void writePPMTest() {
    setup();
    ImageUtil.writePPM(path + "checkerTest.ppm",
                        LayerCreator.createCheckerboard(10, 10));
    Layer checkerTest = ImageUtil.readPPM(path + "checkerTest.ppm");

    assertEquals(100, checkerTest.getWidth());
    assertEquals(100, checkerTest.getHeight());
    assertTrue(equalLayers(checker, checkerTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void writePPMCannotWriteTest() {
    ImageUtil.writePPM(path + "readonly.ppm", new SimpleLayer(10, 10));
  }

  @Test
  public void createCheckerboardTest() {
    setup();
    SimpleLayer checkerTest = LayerCreator.createCheckerboard(10, 10);

    assertTrue(equalLayers(checker, checkerTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCheckerboardNegSizeTest() {
    LayerCreator.createCheckerboard(0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCheckerboardNegSquaresTest() {
    LayerCreator.createCheckerboard(10, 0);
  }

  @Test
  public void createSolidSquareTest() {
    setup();
    SimpleLayer whiteTest = LayerCreator.createSolidSquare(100, 255, 255, 255);
    SimpleLayer blackTest = LayerCreator.createSolidSquare(100, 0, 0, 0);
    SimpleLayer redTest = LayerCreator.createSolidSquare(100, 255, 0, 0);
    SimpleLayer greenTest = LayerCreator.createSolidSquare(100, 0, 255, 0);
    SimpleLayer blueTest = LayerCreator.createSolidSquare(100, 0, 0, 255);

    assertTrue(equalLayers(white, whiteTest));
    assertTrue(equalLayers(black, blackTest));
    assertTrue(equalLayers(red, redTest));
    assertTrue(equalLayers(green, greenTest));
    assertTrue(equalLayers(blue, blueTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createSolidSquareNegSizeTest() {
    LayerCreator.createSolidSquare(0, 10, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createSolidSquareInvalidChannelTest() {
    LayerCreator.createSolidSquare(10, 256, -1, 10);
  }

  @Test
  public void getChannelTest() {
    setup();

    assertEquals(255, red.getPixel(0, 0).getChannel(PixelChannel.RED));
    assertEquals(0, red.getPixel(0, 0).getChannel(PixelChannel.GREEN));
    assertEquals(0, red.getPixel(0, 0).getChannel(PixelChannel.BLUE));

    assertEquals(0, green.getPixel(0, 0).getChannel(PixelChannel.RED));
    assertEquals(255, green.getPixel(0, 0).getChannel(PixelChannel.GREEN));
    assertEquals(0, green.getPixel(0, 0).getChannel(PixelChannel.BLUE));

    assertEquals(0, blue.getPixel(0, 0).getChannel(PixelChannel.RED));
    assertEquals(0, blue.getPixel(0, 0).getChannel(PixelChannel.GREEN));
    assertEquals(255, blue.getPixel(0, 0).getChannel(PixelChannel.BLUE));
  }

  @Test
  public void getWidthTest() {
    setup();
    SimpleLayer test = LayerCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getWidth());
    assertEquals(1, test.getWidth());
  }

  @Test
  public void getHeightTest() {
    setup();
    SimpleLayer test = LayerCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getHeight());
    assertEquals(1, test.getHeight());
  }

  @Test
  public void setPixelTest() {
    setup();

    assertTrue(equalPixels(new SimplePixel(255, 255, 255, 100),
               white.getPixel(10, 10)));

    white.setPixel(10, 10, new SimplePixel(144, 100, 200, 100));
    assertTrue(equalPixels(new SimplePixel(144, 100, 200, 100),
               white.getPixel(10, 10)));
  }

  @Test
  public void filterBlurTest() {
    setup();

    assertFalse(equalLayers(blur, guitar));
    assertTrue(equalLayers(blur, new Filter(FilterMatrix.BLUR).start(guitar)));
  }

  @Test
  public void filterSharpenTest() {
    setup();

    assertFalse(equalLayers(sharpen, guitar));
    assertTrue(equalLayers(sharpen, new Filter(FilterMatrix.SHARPEN).start(guitar)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNullImageTest() {
    new Filter(FilterMatrix.SHARPEN).start(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterEvenMatrixTest() {
    double[][] matrix = {{0, 1}, {2, 1.5}};
    new Filter(matrix).start(guitar);
  }

  @Test
  public void colorTransformationGrayTest() {
    setup();

    assertFalse(equalLayers(gray, guitar));
    assertTrue(
        equalLayers(gray,
            new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(guitar)));
  }

  @Test
  public void colorTransformationSepiaTest() {
    setup();

    assertFalse(equalLayers(sepia, guitar));
    assertTrue(
        equalLayers(sepia, new ColorTransformation(ColorTransformationMatrix.SEPIA).start(guitar)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void colorTransformationNullImageTest() {
    new ColorTransformation(ColorTransformationMatrix.SEPIA).start(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void colorTransformationInvalidMatrixTest() {
    double[][] matrix = {{1, 2, 3}, {4, 1}};
    new ColorTransformation(matrix).start(guitar);
  }

  @Test
  public void imageConstructorTest() {
    SimpleLayer image = new SimpleLayer(100, 200);

    assertEquals(100, image.getWidth());
    assertEquals(200, image.getHeight());
    assertTrue(equalPixels(new SimplePixel(0, 0, 0, 100),
               image.getPixel(10, 10)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeWidthTest() {
    new SimpleLayer(-1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeHeightTest() {
    new SimpleLayer(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorNegativeTest() {
    new SimplePixel(-2, 100, 100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorPositiveTest() {
    new SimplePixel(20, 300, 20, 100);
  }
}
