import controller.Controller;
import controller.SimpleController;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import utils.TestingUtil;

/**
 * Tests to be run on the Controller to ensure all the commands work properly.
 */
public class ControllerTests {

  private final StringWriter output = new StringWriter();
  private Controller controller;

  private InputStreamReader createInput(String input) {
    return new InputStreamReader(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
  }

  private void setup() {
    output.flush();
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructorNullArgTest() {

  }

  @Test
  public void controllerNoInputTest() {

  }

  @Test
  public void controllerNewTest() {

  }

  @Test
  public void controllerRemoveTest() {

  }

  @Test
  public void controllerPrintTest() {

  }

  @Test
  public void controllerCurrentTest() {

  }

  @Test
  public void controllerInvisibleTest() {

  }

  @Test
  public void controllerLoadImageTest() {
    //TODO
  }

  @Test
  public void controllerLoadProjectTest() {

  }

  @Test
  public void controllerSaveImageTest() {
    //TODO
  }

  @Test
  public void controllerSaveProjectTest() {

  }

  @Test
  public void controllerCreateCheckerboardTest() {
    //TODO
  }

  @Test
  public void controllerCreateRectangleTest() {
    //TODO
  }

  @Test
  public void controllerBlurTest() {
    //TODO
  }

  @Test
  public void controllerSharpenTest() {
    //TODO
  }

  @Test
  public void controllerSepiaTest() {
    //TODO
  }

  @Test
  public void controllerGrayTest() {
    //TODO
  }

  @Test
  public void controllerQuitTest() {

  }
}
