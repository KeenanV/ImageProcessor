package model;

/**
 * Represents a command that could be used upon an layer.
 */
public interface LayerCommand {

  /**
   * Executes this command upon the given layer.
   *
   * @param layer the layer to use this command on
   * @throws IllegalArgumentException if the given layer is invalid
   */
  Layer start(Layer layer);
}
