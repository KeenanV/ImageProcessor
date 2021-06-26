package view;

import java.io.IOException;
import model.Image;

/**
 * Implementation which outputs a text view to the user.
 */
public class SimpleTextView implements TextView {

  private final Appendable ap;
  private Image image;

  /**
   * Constructs a view with the given arguments.
   *
   * @param ap    Appendable to be used for output
   * @param image Image to be used
   */
  public SimpleTextView(Appendable ap, Image image) {
    this.ap = ap;
    this.image = image;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message);
  }

  @Override
  public void printLayers() throws IOException {
    renderMessage("Size: " + image.getWidth() + "x" + image.getHeight() + " pixels\n");
    for (int ii = 0; ii < image.getNumLayers(); ii++) {
      renderMessage("Layer " + (ii + 1) + ": ");
      if (image.getLayer(ii).getVisible()) {
        renderMessage("visible\n");
      } else {
        renderMessage("invisible\n");
      }
    }
  }

  @Override
  public void setImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have null arg.");
    }
    this.image = image;
  }
}
