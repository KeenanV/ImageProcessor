import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.Image;
import model.ImageCreator;
import model.Pixel;
import model.Pixel.PixelChannel;
import org.junit.Test;
import utils.ImageUtil;

public class ModelTests {

  Image checker;
  Image white;
  Image black;
  Image red;
  Image green;
  Image blue;
  String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/test/images/";
  String blur = "blur.ppm";
  String sharpen = "sharpen.ppm";
  String gray = "gray.ppm";
  String sepia = "sepia.ppm";

  private void setup() {
    checker = ImageUtil.readPPM(path + "checker.ppm");
    white = ImageUtil.readPPM(path + "white.ppm");
    black = ImageUtil.readPPM(path + "black.ppm");
    red = ImageUtil.readPPM(path + "red.ppm");
    green = ImageUtil.readPPM(path + "green.ppm");
    blue = ImageUtil.readPPM(path + "blue.ppm");
  }

  private boolean equalImages(Image expected, Image actual) {
    if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
      return false;
    }
    for (int xx = 0; xx < expected.getWidth(); xx++) {
      for (int yy = 0; yy < expected.getWidth(); yy++) {
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
      return true;
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

  @Test
  public void writePPMTest() {
    setup();
    ImageUtil.writePPM(path + "checkerTest.ppm", ImageCreator.createCheckerboard(10, 10));
    Image checkerTest = ImageUtil.readPPM(path + "checkerTest.ppm");

    assertEquals(100, checkerTest.getWidth());
    assertEquals(100, checkerTest.getHeight());
    assertTrue(equalImages(checker, checkerTest));
  }

  @Test
  public void createCheckerboardTest() {
    setup();
    Image checkerTest = ImageCreator.createCheckerboard(10, 10);

    assertTrue(equalImages(checker, checkerTest));
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
}
