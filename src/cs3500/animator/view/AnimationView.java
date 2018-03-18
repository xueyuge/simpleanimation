package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;

/**
 * A means by which an AnimationModel can be displayed or viewed.
 */
public interface AnimationView {
  /**
   * Runs this AnimationView on the given AnimationModel at the given tick rate.
   *
   * @param model the model
   * @param tickRate the tick rate
   */
  void run(AnimationModel model, int tickRate);
}
