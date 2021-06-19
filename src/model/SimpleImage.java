package model;

import java.util.ArrayList;
import java.util.List;

public class SimpleImage implements Image {
  private final int width;
  private final int height;
  private final List<Layer> layers;

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
  public void addLayer(Layer layer, int index) throws IllegalArgumentException {
    if (layer == null || layer.getWidth() != this.width || layer.getHeight() != this.height
        || invalidIndex(index)) {
      throw new IllegalArgumentException("Unable to add layer.");
    }

    layers.add(index, layer);
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
  public Layer getTopVisibleLayer() {
    if (layers.isEmpty()) {
      return LayerCreator.createSolidRect(width, height, 0, 0, 0);
    }
    for (int ii = layers.size() - 1; ii >= 0; ii--) {
      if (layers.get(ii).getVisible()) {
        return layers.get(ii);
      }
    }

    return LayerCreator.createSolidRect(width, height, 0, 0, 0);
  }

  private boolean invalidIndex(int index) {
    return index < 0 || (index >= layers.size() && !layers.isEmpty());
  }
}
