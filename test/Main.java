import controller.Controller;
import controller.SimpleController;
import controller.SimpleGUIController;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import model.SimpleImage;
import view.GUIView;

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
    /*
    String script = "/Users/keenanv/Documents/NEU/CS3500/Projects/imgproctests/testscript.txt";
    Controller controller = new SimpleController(new SimpleImage(400, 400), System.out,
        new InputStreamReader(System.in));

    controller.runImageProcessor();
     */

    Controller controller = new SimpleGUIController(new SimpleImage(400, 400));
    controller.runImageProcessor();
  }
}
