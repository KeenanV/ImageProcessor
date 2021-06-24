package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import model.Image;
import model.Layer;
import model.SimpleImage;
import model.SimpleLayer;
import model.Pixel.PixelChannel;
import model.SimplePixel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Removes the comment lines from a file. Comments start with a '#'.
   *
   * @param filename path of the file
   * @return a Readable object with the contents of the file without comments
   * @throws IllegalArgumentException if the file doesn't exist or null arg is passed
   */
  public static Readable removeComments(String filename) throws IllegalArgumentException {
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
    InputStream stream = new ByteArrayInputStream(
        builder.toString().getBytes(StandardCharsets.UTF_8));
    return new InputStreamReader(stream);
  }

  /**
   * Read an image file in the PPM format and return it as an Image.
   *
   * @param filename the path of the file.
   * @return Image representing the image file
   * @throws IllegalArgumentException if unable to access/read file
   */
  public static Layer readPPM(String filename) throws IllegalArgumentException {

    String token;
    Scanner sc = new Scanner(removeComments(filename));

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    SimpleLayer layer = new SimpleLayer(width, height);

    sc.nextInt();

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
    for (int ii = filename.length() - 1; ii >= 0; ii--) {
      if (filename.charAt(ii) == '.' && ii != filename.length() - 1) {
        if (filename.substring(ii + 1).equals("ppm")) {
          return readPPM(filename);
        }
        break;
      }
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
   * @param layer    layer to be written
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

    if (type.equals("ppm")) {
      writePPM(filename, layer);
      return;
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
   * Writes all layers of a file to images and stores paths in a text file.
   *
   * @param filename name of the image
   * @param image    image to  be stored
   * @throws IllegalArgumentException if writing fails or inputs are null
   */
  public static void writeMultiLayerImage(String filename, Image image)
      throws IllegalArgumentException {
    if (filename == null || image == null) {
      throw new IllegalArgumentException("Cannot have null arguments.");
    }
    File file;
    FileWriter output;
    try {
      file = new File(filename + ".imgproc");
      file.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to access file.");
    }

    if (!file.canWrite()) {
      throw new IllegalArgumentException("File is not writeable.");
    }

    try {
      output = new FileWriter(file);
      output.write("CS3500 Image Processor File v1.0\n");
      output.write(image.getWidth() + "\n");
      output.write(image.getHeight() + "\n");
      for (int ii = 0; ii < image.getNumLayers(); ii++) {
        writeFile(filename + "layer" + ii + ".jpg", image.getLayer(ii));
        output.write(filename + "layer" + ii + ".jpg\n");
        if (image.getLayer(ii).getVisible()) {
          output.write("visible\n");
        } else {
          output.write("invisible\n");
        }
      }
      output.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to write to file.");
    }
  }

  /**
   * Reads a multi-layered image to an Image.
   *
   * @param filename name of the text file containing the layers
   * @return an Image with all the layers
   * @throws IllegalArgumentException if a null argument is passed or file is invalid
   */
  public static Image readMultiLayerImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null arguments.");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Could not find file.");
    }

    try {
      if (!sc.nextLine().equals("CS3500 Image Processor File v1.0")) {
        throw new IllegalArgumentException("Invalid file.");
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Empty file.");
    }

    Image image = new SimpleImage(Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()));
    while (sc.hasNextLine()) {
      Layer layer = readFile(sc.nextLine());
      layer.setVisible(sc.nextLine().equals("visible"));
      image.addLayer(layer);
    }

    return image;
  }
}

