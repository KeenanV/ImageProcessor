import controller.Controller;
import controller.SimpleController;
import controller.SimpleGUIController;
import java.io.InputStreamReader;
import model.SimpleImage;
import utils.ImageUtil;

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
    if (args.length == 2 && args[0].equals("-script")) {
      Controller controller = new SimpleController(System.out, ImageUtil.removeComments(args[1]));
      controller.runImageProcessor();
    }
    if (args.length == 1) {
      if (args[0].equals("-text")) {
        Controller controller = new SimpleController(System.out, new InputStreamReader(System.in));
        controller.runImageProcessor();
      } else if (args[0].equals("-interactive")) {
        Controller controller = new SimpleGUIController(new SimpleImage(400, 400));
        controller.runImageProcessor();
      } else {
        System.out.println("Invalid input.");
      }
    } else {
      System.out.println("Invalid input.");
    }
  }
}
