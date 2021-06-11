import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.ImageCreator;
import utils.ImageUtil;

public class Main {

  //demo main
  public static void main(String[] args) {
    String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/res/";
    String readKoala = "Koala.ppm";
    String writeKoala = "test.ppm";
    String blur = "blur.ppm";
    String blur2 = "blur2.ppm";
    String sharpen = "sharpen.ppm";
    String gray = "gray.ppm";
    String sepia = "sepia.ppm";
    String writeCheckers = "checkers.ppm";

    /*
    Image koala = ImageUtil.readPPM(path + readKoala);
    ImageUtil.writePPM(path + blur, new Filter(FilterMatrix.BLUR).go(koala));
    Image blurred = ImageUtil.readPPM(path + blur);
    ImageUtil.writePPM(path + blur2, new Filter(FilterMatrix.BLUR).go(blurred));
    ImageUtil.writePPM(path + sharpen, new Filter(FilterMatrix.SHARPEN).go(koala));
    ImageUtil.writePPM(path + gray,
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).go(koala));
    ImageUtil
        .writePPM(path + sepia, new ColorTransformation(ColorTransformationMatrix.SEPIA).go(koala));
    ImageUtil.writePPM(path + writeCheckers, ImageCreator.createCheckerboard(5, 100));
     */

    ImageUtil.writePPM(path + "white.ppm", ImageCreator.createSolidSquare(100, 255, 255, 255));
    ImageUtil.writePPM(path + "black.ppm", ImageCreator.createSolidSquare(100, 0, 0, 0));
    ImageUtil.writePPM(path + "red.ppm", ImageCreator.createSolidSquare(100, 255, 0, 0));
    ImageUtil.writePPM(path + "green.ppm", ImageCreator.createSolidSquare(100, 0, 255, 0));
    ImageUtil.writePPM(path + "blue.ppm", ImageCreator.createSolidSquare(100, 0, 0, 255));
    ImageUtil.writePPM(path + "checker.ppm", ImageCreator.createCheckerboard(10, 10));
  }
}
