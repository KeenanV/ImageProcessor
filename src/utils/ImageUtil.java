package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import model.Image;
import model.ImageCreator;
import model.Pixel;
import model.Pixel.PixelChannel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return it as an Image
   *
   * @param filename the path of the file.
   * @return Image representing the image file
   * @throws IllegalArgumentException if unable to access/read file
   */
  public static Image readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    Image image = new Image(width, height);

    int maxValue = sc.nextInt();

    for (int ii = 0; ii < height; ii++) {
      for (int jj = 0; jj < width; jj++) {
        int rr = sc.nextInt();
        int gg = sc.nextInt();
        int bb = sc.nextInt();
        image.setPixel(jj, ii, new Pixel(rr, gg, bb));
      }
    }

    return image;
  }

  /**
   * Writes an image to a ppm file and creates it if it doesn't exist.
   *
   * @param filename path of the file.
   * @param image    Image to be written to file
   * @throws IllegalArgumentException if unable to access/write to file
   */
  public static void writePPM(String filename, Image image) throws IllegalArgumentException {
    File file;
    FileWriter output;
    try {
      file = new File(filename);
      file.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to access file.");
    }

    if (!file.canWrite()) {
      throw new IllegalArgumentException("File is not writeable.");
    }

    try {
      output = new FileWriter(file);
      output.write("P3\n");
      output.write(image.getWidth() + " " + image.getHeight() + "\n");
      output.write("255\n");

      for (int ii = 0; ii < image.getHeight(); ii++) {
        for (int jj = 0; jj < image.getWidth(); jj++) {
          output.write(image.getPixel(jj, ii).getChannel(PixelChannel.RED) + "\n");
          output.write(image.getPixel(jj, ii).getChannel(PixelChannel.GREEN) + "\n");
          output.write(image.getPixel(jj, ii).getChannel(PixelChannel.BLUE) + "\n");
        }
      }
      output.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to write to file.");
    }
  }

  public static int clamp(int channelValue, int maxValue) {
    if (channelValue > maxValue) {
      return maxValue;
    } else if (channelValue < 0) {
      return 0;
    } else {
      return channelValue;
    }
  }
}

