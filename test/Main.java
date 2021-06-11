import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
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
    String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/Image Processor/res/";
    Image guitar = ImageUtil.readPPM(path + "guitar.ppm");
    Image trees = ImageUtil.readPPM(path + "trees.ppm");

    ImageUtil.writePPM(path + "guitarBlur.ppm", new Filter(FilterMatrix.BLUR).go(guitar));
    ImageUtil.writePPM(path + "guitarSharpen.ppm", new Filter(FilterMatrix.SHARPEN).go(guitar));
    ImageUtil.writePPM(path + "guitarGray.ppm",
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).go(guitar));
    ImageUtil.writePPM(path + "guitarSepia.ppm",
        new ColorTransformation(ColorTransformationMatrix.SEPIA).go(guitar));

    ImageUtil.writePPM(path + "treesBlur.ppm", new Filter(FilterMatrix.BLUR).go(trees));
    ImageUtil.writePPM(path + "treesSharpen.ppm", new Filter(FilterMatrix.SHARPEN).go(trees));
    ImageUtil.writePPM(path + "treesGray.ppm",
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).go(trees));
    ImageUtil.writePPM(path + "treesSepia.ppm",
        new ColorTransformation(ColorTransformationMatrix.SEPIA).go(trees));
  }
}
