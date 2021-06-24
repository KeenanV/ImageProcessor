package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of an Image that has a list of layers and a width and a height.
 */
public class SimpleImage implements Image {

  private final int width;
  private final int height;
  private final List<Layer> layers;

  /**
   * Creates an image of the given dimensions.
   *
   * @param width  width of the image in pixels
   * @param height height of the image in pixels
   */
  public SimpleImage(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid image dimension");
    }
    this.width = width;
    this.height = height;
    this.layers = new ArrayList<>();
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getNumLayers() {
    return layers.size();
  }

  @Override
  public void addLayer(Layer layer) throws IllegalArgumentException {
    if (layer == null || layer.getWidth() != this.width || layer.getHeight() != this.height) {
      throw new IllegalArgumentException("Unable to add layer.");
    }

    layers.add(layer);
  }

  @Override
  public void insertLayer(int index, Layer layer) throws IllegalArgumentException {
    if (invalidIndex(index) || layer == null || layer.getHeight() != this.height
        || layer.getWidth() != this.width) {
      throw new IllegalArgumentException("Unable to insert layer.");
    }

    layers.set(index, layer);
  }

  @Override
  public void moveToTop(int index) throws IllegalArgumentException {
    if (invalidIndex(index)) {
      throw new IllegalArgumentException("Invalid layer index.");
    }
    Layer layer = layers.remove(index);
    layer.setVisible(true);
    addLayer(layer);
  }

  @Override
  public Layer removeLayer(int index) throws IllegalArgumentException {
    if (invalidIndex(index)) {
      throw new IllegalArgumentException("Invalid layer index.");
    }

    return layers.remove(index);
  }

  @Override
  public Layer getLayer(int index) throws IllegalArgumentException {
    if (invalidIndex(index)) {
      throw new IllegalArgumentException("Invalid layer index.");
    }

    return layers.get(index);
  }

  @Override
  public Layer getTopVisibleLayer() throws IllegalArgumentException {
    if (layers.isEmpty()) {
      throw new IllegalArgumentException("no layers.");
    }
    for (int ii = layers.size() - 1; ii >= 0; ii--) {
      if (layers.get(ii).getVisible()) {
        return layers.get(ii);
      }
    }

    throw new IllegalArgumentException("no visible layers.");
  }

  private boolean invalidIndex(int index) {
    return index < 0 || (index >= layers.size() && !layers.isEmpty());
  }
}
