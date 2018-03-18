package cs3500.animator.model;

import java.util.List;

import cs3500.animator.model.command.Command;
import cs3500.animator.model.drawable.Drawable;

/**
 * Represents a general model for an animation.
 */
public interface AnimationModel {
  /**
   * Gets a list of the Commands this AnimationModel uses.
   *
   * @return the Commands
   */
  List<Command> getCommands();

  /**
   * Gets a list of the Drawables this AnimationModel uses.
   *
   * @return the Drawables
   */
  List<Drawable> getDrawables();

  /**
   * Adds a Drawable to this AnimationModel.
   *
   * @param drawable the Drawable
   */
  void addDrawable(Drawable drawable, String name);

  /**
   * Adds a Command to this AnimationModel.
   *
   * @param command the Command
   */
  void addCommand(Command command, String name);
}
