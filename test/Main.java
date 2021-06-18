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

    ImageUtil.writePPM(path + "guitarBlur.ppm", new Filter(FilterMatrix.BLUR).start(guitar));
    ImageUtil.writePPM(path + "guitarSharpen.ppm", new Filter(FilterMatrix.SHARPEN).start(guitar));
    ImageUtil.writePPM(path + "guitarGray.ppm",
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(guitar));
    ImageUtil.writePPM(path + "guitarSepia.ppm",
        new ColorTransformation(ColorTransformationMatrix.SEPIA).start(guitar));

    ImageUtil.writePPM(path + "treesBlur.ppm", new Filter(FilterMatrix.BLUR).start(trees));
    ImageUtil.writePPM(path + "treesSharpen.ppm", new Filter(FilterMatrix.SHARPEN).start(trees));
    ImageUtil.writePPM(path + "treesGray.ppm",
        new ColorTransformation(ColorTransformationMatrix.GRAYSCALE).start(trees));
    ImageUtil.writePPM(path + "treesSepia.ppm",
        new ColorTransformation(ColorTransformationMatrix.SEPIA).start(trees));
  }
}

/*
TODO:
- Change name from Image to Layer
- Create interface for Image
- Implement Image to store multiple layers
- Create interface for Layer
- Add util methods to support jpg and png
- Add util methods to support multi-layered images
- Add util method for reading from script
- Create controller interface
- Implement controller to work with batch commands
- Create view interface
- Implement text-based view interface
- Create interface for Pixel
- Add Pixel implementation to have a transparency channel
- Move PixelChannel enum to separate file
 */
