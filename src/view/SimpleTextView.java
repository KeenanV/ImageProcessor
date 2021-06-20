package view;

import java.io.IOException;
import model.Image;

public class SimpleTextView implements View {

  private final Appendable ap;
  private Image image;

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
