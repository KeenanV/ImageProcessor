import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.SimpleController;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.Layer;
import model.LayerCreator;
import model.SimpleImage;
import model.SimpleLayer;
import org.junit.Test;
import utils.TestingUtil;

/**
 * Tests to be run on the Controller to ensure all the commands work properly.
 */
public class ControllerTests {

  private final StringWriter output = new StringWriter();
  private SimpleController controller;
  private Image image;
  private Layer layer1;
  private Layer layer2;
  private final String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/imgproctests/";

  private InputStreamReader createInput(String input) {
    return new InputStreamReader(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
  }

  private void setup() {
    output.flush();
    image = new SimpleImage(100, 100);
    layer1 = new SimpleLayer(100, 100);
    layer2 = new SimpleLayer(100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructorNullArgTest() {
    setup();
    controller = new SimpleController(null, null, null);
  }

  @Test
  public void controllerNoInputAndQuitTest() {
    setup();
    controller = new SimpleController(output, createInput("quit\n"));
    controller.runImageProcessor();
    String expected = "Welcome to Image Processor\nInput a command:\n"
        + "Thanks for using Image Processor.\n";
    assertEquals(expected, output.toString());
  }

  @Test
  public void controllerNewTest() {
    setup();
    assertEquals(0, image.getNumLayers());
    controller = new SimpleController(image, output, createInput("new\nquit\n"));
    controller.runImageProcessor();
    assertEquals(1, image.getNumLayers());
  }

  @Test
  public void controllerRemoveTest() {
    setup();
    image.addLayer(new SimpleLayer(100, 100));
    controller = new SimpleController(image, output, createInput("remove 1\nquit\n"));
    assertEquals(1, image.getNumLayers());
    controller.runImageProcessor();
    assertEquals(0, image.getNumLayers());

  }

  @Test
  public void controllerPrintTest() {
    setup();
    layer1.setVisible(true);
    layer2.setVisible(false);
    image.addLayer(layer1);
    image.addLayer(layer2);
    controller = new SimpleController(image, output, createInput("print\nquit\n"));
    controller.runImageProcessor();
    String expected = "Welcome to Image Processor\nInput a command:\n"
        + "Size: 100x100 pixels\nLayer 1: visible\nLayer 2: invisible\n"
        + "Thanks for using Image Processor.\n";
    assertEquals(expected, output.toString());
  }

  @Test
  public void controllerCurrentTest() {
    setup();
    layer1.setVisible(false);
    layer2 = LayerCreator.createSolidRect(100, 100, 255, 0, 0);
    image.addLayer(layer1);
    image.addLayer(layer2);
    controller = new SimpleController(image, output, createInput("current 1\nquit\n"));
    assertTrue(TestingUtil.equalLayers(image.getLayer(0), layer1));
    controller.runImageProcessor();
    assertFalse(TestingUtil.equalLayers(image.getLayer(0), layer1));
    assertTrue(layer1.getVisible());
  }

  @Test
  public void controllerInvisibleTest() {
    setup();
    image.addLayer(layer1);
    assertTrue(layer1.getVisible());
    controller = new SimpleController(image, output, createInput("invisible 1\nquit\n"));
    controller.runImageProcessor();
    assertFalse(layer1.getVisible());
  }

  @Test
  public void controllerLoadAndSaveProjectTest() {
    setup();
    image.addLayer(LayerCreator.createSolidRect(100, 100, 255, 255, 255));
    image.addLayer(LayerCreator.createSolidRect(100, 100, 0, 0, 255));
    image.getLayer(0).setVisible(false);
    controller = new SimpleController(image, output,
        createInput("save project " + path + "testproj\nquit\n"));
    controller.runImageProcessor();

    setup();
    controller = new SimpleController(output,
        createInput("load project " + path + "testproj.imgproc\nquit\n"));
    image = controller.getImage();
    assertEquals(0, image.getNumLayers());
    controller.runImageProcessor();
    image = controller.getImage();
    assertEquals(2, image.getNumLayers());
    assertFalse(image.getLayer(0).getVisible());
    assertTrue(image.getLayer(1).getVisible());
    assertTrue(TestingUtil
        .equalLayers(LayerCreator.createSolidRect(100, 100, 255, 255, 255), image.getLayer(0)));
    assertTrue(TestingUtil
        .equalLayers(LayerCreator.createSolidRect(100, 100, 0, 0, 254), image.getLayer(1)));
  }

  @Test
  public void controllerSaveAndLoadImageTest() {
    setup();
    layer1.setVisible(false);
    image.addLayer(LayerCreator.createSolidRect(100, 100, 255, 0, 0));
    image.addLayer(layer1);
    controller = new SimpleController(image, output,
        createInput("save " + path + "redTest.jpg\nquit\n"));
    controller.runImageProcessor();

    setup();
    controller = new SimpleController(output, createInput("load " + path + "redTest.jpg\nquit\n"));
    controller.runImageProcessor();
    image = controller.getImage();
    assertTrue(TestingUtil
        .equalLayers(LayerCreator.createSolidRect(100, 100, 254, 0, 0), image.getLayer(0)));
  }

  @Test
  public void controllerCreateCheckerboardTest() {
    setup();
    image.addLayer(layer1);
    controller = new SimpleController(image, output, createInput("create checkerboard\nquit\n"));
    controller.runImageProcessor();
    assertTrue(TestingUtil.equalLayers(LayerCreator.createCheckerboard(10, 10), image.getLayer(0)));
  }

  @Test
  public void controllerBlurTest() {
    setup();
    image.addLayer(LayerCreator.createCheckerboard(10, 10));
    controller = new SimpleController(image, output, createInput("blur\nquit\n"));
    controller.runImageProcessor();
    Layer expected = LayerCreator.createCheckerboard(10, 10);
    expected = new Filter(FilterMatrix.BLUR).start(expected);
    assertTrue(TestingUtil.equalLayers(expected, image.getLayer(0)));
  }

  @Test
  public void controllerSharpenTest() {
    setup();
    image.addLayer(LayerCreator.createCheckerboard(10, 10));
    controller = new SimpleController(image, output, createInput("sharpen\nquit\n"));
    controller.runImageProcessor();
    Layer expected = LayerCreator.createCheckerboard(10, 10);
    expected = new Filter(FilterMatrix.SHARPEN).start(expected);
    assertTrue(TestingUtil.equalLayers(expected, image.getLayer(0)));
  }

  @Test
  public void controllerSepiaTest() {
    setup();
    image.addLayer(LayerCreator.createCheckerboard(10, 10));
    controller = new SimpleController(image, output, createInput("sepia\nquit\n"));
    controller.runImageProcessor();
    Layer expected = LayerCreator.createCheckerboard(10, 10);
    expected = new ColorTransformation(ColorTransformationMatrix.SEPIA).start(expected);
    assertTrue(TestingUtil.equalLayers(expected, image.getLayer(0)));
  }

  @Test
  public void controllerGrayTest() {
    setup();
    image.addLayer(LayerCreator.createCheckerboard(10, 10));
    controller = new SimpleController(image, output, createInput("gray\nquit\n"));
    controller.runImageProcessor();
    Layer expected = LayerCreator.createCheckerboard(10, 10);
    expected = new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(expected);
    assertTrue(TestingUtil.equalLayers(expected, image.getLayer(0)));
  }
}
