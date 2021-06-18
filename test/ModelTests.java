import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.ImageCreator;
import model.Pixel;
import model.Pixel.PixelChannel;
import org.junit.Test;
import utils.ImageUtil;

/**
 * Test class for the model.
 */
public class ModelTests {

  Image checker;
  Image white;
  Image black;
  Image red;
  Image green;
  Image blue;
  Image gray;
  Image sepia;
  Image blur;
  Image sharpen;
  Image guitar;
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

  private boolean equalImages(Image expected, Image actual) {
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
    } else {
      return expected.getChannel(PixelChannel.BLUE) == actual.getChannel(PixelChannel.BLUE);
    }
  }

  @Test
  public void readPPMTest() {
    setup();
    assertEquals(100, white.getHeight());
    assertEquals(100, white.getWidth());
    assertTrue(equalPixels(white.getPixel(10, 10), new Pixel(255, 255, 255)));

    assertEquals(100, black.getHeight());
    assertEquals(100, black.getWidth());
    assertTrue(equalPixels(black.getPixel(10, 10), new Pixel(0, 0, 0)));

    assertEquals(100, red.getHeight());
    assertEquals(100, red.getWidth());
    assertTrue(equalPixels(red.getPixel(10, 10), new Pixel(255, 0, 0)));

    assertEquals(100, green.getHeight());
    assertEquals(100, green.getWidth());
    assertTrue(equalPixels(green.getPixel(10, 10), new Pixel(0, 255, 0)));

    assertEquals(100, blue.getHeight());
    assertEquals(100, blue.getWidth());
    assertTrue(equalPixels(blue.getPixel(10, 10), new Pixel(0, 0, 255)));
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
    ImageUtil.writePPM(path + "checkerTest.ppm", ImageCreator.createCheckerboard(10, 10));
    Image checkerTest = ImageUtil.readPPM(path + "checkerTest.ppm");

    assertEquals(100, checkerTest.getWidth());
    assertEquals(100, checkerTest.getHeight());
    assertTrue(equalImages(checker, checkerTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void writePPMCannotWriteTest() {
    ImageUtil.writePPM(path + "readonly.ppm", new Image(10, 10));
  }

  @Test
  public void createCheckerboardTest() {
    setup();
    Image checkerTest = ImageCreator.createCheckerboard(10, 10);

    assertTrue(equalImages(checker, checkerTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCheckerboardNegSizeTest() {
    ImageCreator.createCheckerboard(0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCheckerboardNegSquaresTest() {
    ImageCreator.createCheckerboard(10, 0);
  }

  @Test
  public void createSolidSquareTest() {
    setup();
    Image whiteTest = ImageCreator.createSolidSquare(100, 255, 255, 255);
    Image blackTest = ImageCreator.createSolidSquare(100, 0, 0, 0);
    Image redTest = ImageCreator.createSolidSquare(100, 255, 0, 0);
    Image greenTest = ImageCreator.createSolidSquare(100, 0, 255, 0);
    Image blueTest = ImageCreator.createSolidSquare(100, 0, 0, 255);

    assertTrue(equalImages(white, whiteTest));
    assertTrue(equalImages(black, blackTest));
    assertTrue(equalImages(red, redTest));
    assertTrue(equalImages(green, greenTest));
    assertTrue(equalImages(blue, blueTest));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createSolidSquareNegSizeTest() {
    ImageCreator.createSolidSquare(0, 10, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createSolidSquareInvalidChannelTest() {
    ImageCreator.createSolidSquare(10, 256, -1, 10);
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
    Image test = ImageCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getWidth());
    assertEquals(1, test.getWidth());
  }

  @Test
  public void getHeightTest() {
    setup();
    Image test = ImageCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getHeight());
    assertEquals(1, test.getHeight());
  }

  @Test
  public void setPixelTest() {
    setup();

    assertTrue(equalPixels(new Pixel(255, 255, 255), white.getPixel(10, 10)));

    white.setPixel(10, 10, new Pixel(144, 100, 200));
    assertTrue(equalPixels(new Pixel(144, 100, 200), white.getPixel(10, 10)));
  }

  @Test
  public void filterBlurTest() {
    setup();

    assertFalse(equalImages(blur, guitar));
    assertTrue(equalImages(blur, new Filter(FilterMatrix.BLUR).start(guitar)));
  }

  @Test
  public void filterSharpenTest() {
    setup();

    assertFalse(equalImages(sharpen, guitar));
    assertTrue(equalImages(sharpen, new Filter(FilterMatrix.SHARPEN).start(guitar)));
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

    assertFalse(equalImages(gray, guitar));
    assertTrue(
        equalImages(gray,
            new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(guitar)));
  }

  @Test
  public void colorTransformationSepiaTest() {
    setup();

    assertFalse(equalImages(sepia, guitar));
    assertTrue(
        equalImages(sepia, new ColorTransformation(ColorTransformationMatrix.SEPIA).start(guitar)));
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
    Image image = new Image(100, 200);

    assertEquals(100, image.getWidth());
    assertEquals(200, image.getHeight());
    assertTrue(equalPixels(new Pixel(0, 0, 0), image.getPixel(10, 10)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeWidthTest() {
    new Image(-1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeHeightTest() {
    new Image(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorNegativeTest() {
    new Pixel(-2, 100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorPositiveTest() {
    new Pixel(20, 300, 20);
  }
}
