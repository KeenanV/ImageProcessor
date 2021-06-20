import controller.Controller;
import controller.SimpleController;
import java.io.InputStreamReader;
import model.SimpleImage;

/**
 * Main class for testing.
 */
public class Main {

  /**
   * Main method for testing purposes and to generate res files.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    String script = "/Users/keenanv/Documents/NEU/CS3500/Projects/imgproctests/testscript.txt";
    Controller controller = new SimpleController(new SimpleImage(400, 400), System.out,
        new InputStreamReader(System.in));

    controller.runImageProcessor();
  }
}
