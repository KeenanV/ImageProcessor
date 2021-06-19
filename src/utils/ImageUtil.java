package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import model.Layer;
import model.SimpleLayer;
import model.Pixel.PixelChannel;
import model.SimplePixel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return it as an Image.
   *
   * @param filename the path of the file.
   * @return Image representing the image file
   * @throws IllegalArgumentException if unable to access/read file
   */
  public static Layer readPPM(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null argument.");
    }

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

    SimpleLayer layer = new SimpleLayer(width, height);

    int _maxValue = sc.nextInt();

    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        int rr = sc.nextInt();
        int gg = sc.nextInt();
        int bb = sc.nextInt();
        layer.setPixel(xx, yy, new SimplePixel(rr, gg, bb));
      }
    }

    return layer;
  }

  /**
   * Writes an image to a ppm file and creates it if it doesn't exist.
   *
   * @param filename path of the file.
   * @param image    Image to be written to file
   * @throws IllegalArgumentException if unable to access/write to file
   */
  public static void writePPM(String filename, Layer image) throws IllegalArgumentException {
    if (filename == null || image == null) {
      throw new IllegalArgumentException("Cannot have null arguments.");
    }

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

      for (int yy = 0; yy < image.getHeight(); yy++) {
        for (int xx = 0; xx < image.getWidth(); xx++) {
          output.write(image.getPixel(xx, yy).getChannel(PixelChannel.RED) + "\n");
          output.write(image.getPixel(xx, yy).getChannel(PixelChannel.GREEN) + "\n");
          output.write(image.getPixel(xx, yy).getChannel(PixelChannel.BLUE) + "\n");
        }
      }
      output.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to write to file.");
    }
  }

  /**
   * Reads an image file and creates a Layer from it.
   *
   * @param filename path to the file
   * @return a Layer representing the image file
   * @throws IllegalArgumentException if image cannot be read or if null argument is input
   */
  public static Layer readFile(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null argument.");
    }

    File file = new File(filename);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read image.");
    }

    Layer layer = new SimpleLayer(image.getWidth(), image.getHeight());
    for (int xx = 0; xx < image.getWidth(); xx++) {
      for (int yy = 0; yy < image.getHeight(); yy++) {
        Color pixel = new Color(image.getRGB(xx, yy));
        layer.setPixel(xx, yy, new SimplePixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
      }
    }

    return layer;
  }

  /**
   * Writes a Layer to an image file.
   *
   * @param filename path of the file
   * @param layer layer to be written
   * @throws IllegalArgumentException if the writing fails or if null arguments are input
   */
  public static void writeFile(String filename, Layer layer) throws IllegalArgumentException {
    if (filename == null || layer == null) {
      throw new IllegalArgumentException("Cannot have null arguments.");
    }

    String type = "jpg";
    for (int ii = filename.length() - 1; ii >= 0; ii--) {
      if (filename.charAt(ii) == '.' && ii != filename.length() - 1) {
        type = filename.substring(ii + 1);
        break;
      }
    }

    BufferedImage image = new BufferedImage(layer.getWidth(), layer.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    for (int xx = 0; xx < layer.getWidth(); xx++) {
      for (int yy = 0; yy < layer.getHeight(); yy++) {
        int red = layer.getPixel(xx, yy).getChannel(PixelChannel.RED);
        int green = layer.getPixel(xx, yy).getChannel(PixelChannel.GREEN);
        int blue = layer.getPixel(xx, yy).getChannel(PixelChannel.BLUE);
        image.setRGB(xx, yy, new Color(red, green, blue).getRGB());
      }
    }

    try {
      File file = new File(filename);
      file.createNewFile();
      ImageIO.write(image, type, file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file.");
    }
  }

  /**
   * Ensures a pixel channel value remains between 0 and the maxValue.
   *
   * @param channelValue value of channel to be clamped
   * @param maxValue     maximum channel value for image
   * @return clamped channel value
   */
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

