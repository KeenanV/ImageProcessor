import model.Image;

public class Main {
  //demo main
  public static void main(String[] args) {
    String readFile = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/src/Koala.ppm";
    String writeFile = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/src/test.ppm";

    Image koala = ImageUtil.readPPM(readFile);
    ImageUtil.writePPM(writeFile, koala);
  }
}
