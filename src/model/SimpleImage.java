package model;

import java.util.ArrayList;
import java.util.List;

public class SimpleImage implements Image {
  private final int width;
  private final int height;
  private List<Layer> layers;

  public SimpleImage(int width, int height) {
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
  public void addLayer(Layer layer, int index) {
    if (layer == null || layer.getWidth() != this.width || layer.getHeight() != this.height
        || invalidIndex(index)) {
      throw new IllegalArgumentException();
    }

    layers.add(index, layer);
  }

  @Override
  public Layer removeLayer(int index) {
    if (invalidIndex(index)) {
      throw new IllegalArgumentException();
    }

    return layers.remove(index);
  }

  @Override
  public Layer getLayer(int index) {
    if (invalidIndex(index)) {
      throw new IllegalArgumentException();
    }

    return layers.get(index);
  }

  private boolean invalidIndex(int index) {
    return index >= 0 && index < layers.size();
  }
}
