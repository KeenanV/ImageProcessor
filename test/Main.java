import model.Image;
import model.ImageCreator;

public class Main {

  //demo main
  public static void main(String[] args) {
    String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/src/";
    String readKoala = "Koala.ppm";
    String writeKoala = "test.ppm";
    String writeCheckers = "checkers.ppm";

    Image koala = ImageUtil.readPPM(path + readKoala);
    ImageUtil.writePPM(path + writeKoala, koala);
    ImageUtil.writePPM(path + writeCheckers, ImageCreator.createCheckerboard(5, 100));
  }
}
