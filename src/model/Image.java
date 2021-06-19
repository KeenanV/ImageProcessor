package model;

/**
 * Represents an image, composed of multiple layers.
 */
public interface Image {

  /**
   * Returns the width of this image.
   * @return the width of this image
   */
  int getWidth();

  /**
   * Returns the height of this image.
   * @return the height of this image
   */
  int getHeight();

  /**
   * Returns the number of layers in this image.
   * @return the number of layers in this image
   */
  int getNumLayers();

  /**
   * Inserts the given layer into this image at the indicated index, moving the layer at that index
   * and every layer with a greater index up one position.
   * @param layer the layer to be inserted
   * @param index the index at which the layer is being inserted
   * @throws IllegalArgumentException if the given layer is null, the layer does not match the size
   *                                  of this image, the given index is invalid
   */
  void addLayer(Layer layer, int index) throws IllegalArgumentException;

  /**
   * Removes the layer at the given index from this image, and returns it.
   * @param index the index of the target layer
   * @return the layer previously at the given index
   * @throws IllegalArgumentException if the given index is invalid
   */
  Layer removeLayer(int index) throws IllegalArgumentException;

  /**
   * Returns the layer indicated by the given index.
   * @param index the index of the target layer
   * @return the layer at the given index
   * @throws IllegalArgumentException if the given index is invalid
   */
  Layer getLayer(int index) throws IllegalArgumentException;

  /**
   * Returns the topmost visible layer in the image.
   *
   * @return the topmost visible layer or a black layer if there are no visible layers
   */
  Layer getTopVisibleLayer();
}
