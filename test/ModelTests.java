import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Layer;
import model.SimpleImage;
import model.SimpleLayer;
import model.LayerCreator;
import model.Pixel.PixelChannel;
import model.SimplePixel;
import org.junit.Test;
import utils.ImageUtil;
import utils.TestingUtil;

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
  String projectPath = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor";
  String path = projectPath + "/test/images/";
  String pathRes = projectPath + "/res/";

  enum SupportedFileType {
    PPM("ppm"), JPEG("jpeg"), PNG("png");

    private final String suffix;

    SupportedFileType(String suffix) {
      this.suffix = suffix;
    }

    public String toString() {
      return suffix;
    }
  }

  private void setup(SupportedFileType fileType) {
    checker = ImageUtil.readPPM(path + "checker." + fileType);
    white = ImageUtil.readPPM(path + "white." + fileType);
    black = ImageUtil.readPPM(path + "black." + fileType);
    red = ImageUtil.readPPM(path + "red." + fileType);
    green = ImageUtil.readPPM(path + "green." + fileType);
    blue = ImageUtil.readPPM(path + "blue." + fileType);
    gray = ImageUtil.readPPM(pathRes + "guitarGray." + fileType);
    sepia = ImageUtil.readPPM(pathRes + "guitarSepia." + fileType);
    blur = ImageUtil.readPPM(pathRes + "guitarBlur." + fileType);
    sharpen = ImageUtil.readPPM(pathRes + "guitarSharpen." + fileType);
    guitar = ImageUtil.readPPM(pathRes + "guitar." + fileType);
  }

  @Test
  public void readFilesTest() {
    for (SupportedFileType type : SupportedFileType.values()) {
      setup(type);
      assertEquals(100, white.getHeight());
      assertEquals(100, white.getWidth());
      assertTrue(TestingUtil.equalPixels(white.getPixel(10, 10),
          new SimplePixel(255, 255, 255)));

      assertEquals(100, black.getHeight());
      assertEquals(100, black.getWidth());
      assertTrue(TestingUtil.equalPixels(black.getPixel(10, 10),
          new SimplePixel(0, 0, 0)));

      assertEquals(100, red.getHeight());
      assertEquals(100, red.getWidth());
      assertTrue(TestingUtil.equalPixels(red.getPixel(10, 10),
          new SimplePixel(255, 0, 0)));

      assertEquals(100, green.getHeight());
      assertEquals(100, green.getWidth());
      assertTrue(TestingUtil.equalPixels(green.getPixel(10, 10),
          new SimplePixel(0, 255, 0)));

      assertEquals(100, blue.getHeight());
      assertEquals(100, blue.getWidth());
      assertTrue(TestingUtil.equalPixels(blue.getPixel(10, 10),
          new SimplePixel(0, 0, 255)));
    }
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
  public void writeFilesTest() {
    for (SupportedFileType type : SupportedFileType.values()) {
      setup(type);
      ImageUtil.writePPM(path + "checkerTest.ppm",
          LayerCreator.createCheckerboard(10, 10));
      Layer checkerTest = ImageUtil.readPPM(path + "checkerTest.ppm");

      assertEquals(100, checkerTest.getWidth());
      assertEquals(100, checkerTest.getHeight());
      assertTrue(TestingUtil.equalLayers(checker, checkerTest));
    }
  }

  @Test
  public void createCheckerboardTest() {
    setup(SupportedFileType.PPM);
    SimpleLayer checkerTest = LayerCreator.createCheckerboard(10, 10);

    assertTrue(TestingUtil.equalLayers(checker, checkerTest));
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
    setup(SupportedFileType.PPM);
    SimpleLayer whiteTest = LayerCreator.createSolidSquare(100, 255, 255, 255);
    SimpleLayer blackTest = LayerCreator.createSolidSquare(100, 0, 0, 0);
    SimpleLayer redTest = LayerCreator.createSolidSquare(100, 255, 0, 0);
    SimpleLayer greenTest = LayerCreator.createSolidSquare(100, 0, 255, 0);
    SimpleLayer blueTest = LayerCreator.createSolidSquare(100, 0, 0, 255);

    assertTrue(TestingUtil.equalLayers(white, whiteTest));
    assertTrue(TestingUtil.equalLayers(black, blackTest));
    assertTrue(TestingUtil.equalLayers(red, redTest));
    assertTrue(TestingUtil.equalLayers(green, greenTest));
    assertTrue(TestingUtil.equalLayers(blue, blueTest));
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
    setup(SupportedFileType.PPM);

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
    setup(SupportedFileType.PPM);
    SimpleLayer test = LayerCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getWidth());
    assertEquals(1, test.getWidth());
  }

  @Test
  public void getHeightTest() {
    setup(SupportedFileType.PPM);
    SimpleLayer test = LayerCreator.createSolidSquare(1, 0, 0, 0);

    assertEquals(100, white.getHeight());
    assertEquals(1, test.getHeight());
  }

  @Test
  public void setPixelTest() {
    setup(SupportedFileType.PPM);

    assertTrue(TestingUtil.equalPixels(new SimplePixel(255, 255, 255),
        white.getPixel(10, 10)));

    white.setPixel(10, 10, new SimplePixel(144, 100, 200));
    assertTrue(TestingUtil.equalPixels(new SimplePixel(144, 100, 200),
        white.getPixel(10, 10)));
  }

  @Test
  public void filterBlurTest() {
    setup(SupportedFileType.PPM);

    assertFalse(TestingUtil.equalLayers(blur, guitar));
    assertTrue(TestingUtil.equalLayers(blur, new Filter(FilterMatrix.BLUR).start(guitar)));
  }

  @Test
  public void filterSharpenTest() {
    setup(SupportedFileType.PPM);

    assertFalse(TestingUtil.equalLayers(sharpen, guitar));
    assertTrue(TestingUtil.equalLayers(sharpen, new Filter(FilterMatrix.SHARPEN).start(guitar)));
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
    setup(SupportedFileType.PPM);

    assertFalse(TestingUtil.equalLayers(gray, guitar));
    assertTrue(
        TestingUtil.equalLayers(gray,
            new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(guitar)));
  }

  @Test
  public void colorTransformationSepiaTest() {
    setup(SupportedFileType.PPM);

    assertFalse(TestingUtil.equalLayers(sepia, guitar));
    assertTrue(
        TestingUtil.equalLayers(sepia,
            new ColorTransformation(ColorTransformationMatrix.SEPIA).start(guitar)));
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
  public void layerConstructorTest() {
    SimpleLayer image = new SimpleLayer(100, 200);

    assertEquals(100, image.getWidth());
    assertEquals(200, image.getHeight());
    assertTrue(TestingUtil.equalPixels(new SimplePixel(0, 0, 0),
        image.getPixel(10, 10)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void layerConstructorNegativeWidthTest() {
    new SimpleLayer(-1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void layerConstructorNegativeHeightTest() {
    new SimpleLayer(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorNegativeTest() {
    new SimplePixel(-2, 100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pixelConstructorPositiveTest() {
    new SimplePixel(20, 300, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeWidthTest() {
    new SimpleImage(-1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorNegativeHeightTest() {
    new SimpleImage(100, -1);
  }

  @Test
  public void imageGetWidthTest() {
    assertEquals(100, new SimpleImage(100, 200).getWidth());
  }

  @Test
  public void imageGetHeightTest() {
    assertEquals(200, new SimpleImage(100, 200).getHeight());
  }

  @Test
  public void addOneLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(red);

    assertEquals(1, test.getNumLayers());

    assertTrue(TestingUtil.equalLayers(red, test.getLayer(0)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void incorrectDimensionsAddLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(10, 10);
    test.addLayer(red);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullLayerAddLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidIndexAddLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidIndexGetLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(red);
    test.getLayer(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidIndexRemoveLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(red);
    test.removeLayer(1);
  }

  @Test
  public void removeLayerTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(red);
    assertEquals(1, test.getNumLayers());
    assertTrue(TestingUtil.equalLayers(red, test.removeLayer(0)));
    assertEquals(0, test.getNumLayers());
  }

  @Test
  public void removeMultipleLayersTest() {
    setup(SupportedFileType.PPM);

    SimpleImage test = new SimpleImage(red.getWidth(), red.getHeight());
    test.addLayer(red);
    test.addLayer(green);
    assertEquals(2, test.getNumLayers());
    assertTrue(TestingUtil.equalLayers(red, test.removeLayer(0)));
    assertTrue(TestingUtil.equalLayers(green, test.removeLayer(0)));
    assertEquals(0, test.getNumLayers());
  }
}
