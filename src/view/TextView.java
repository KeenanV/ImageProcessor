package view;

import java.io.IOException;
import model.Image;

/**
 * Represents an interface through with the output of the program is shown to the user.
 */
public interface TextView {

  /**
   * Renders a message to the given appendable. No newline character is added.
   *
   * @param message String containing the message to be printed
   * @throws IOException if writing fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Prints the size of the image and all layers with their visibility.
   *
   * @throws IOException if writing fails
   */
  void printLayers() throws IOException;

  /**
   * Sets the image to be used by this view.
   *
   * @param image image to be used
   * @throws IllegalArgumentException if the given arg is null
   */
  void setImage(Image image) throws IllegalArgumentException;
}
