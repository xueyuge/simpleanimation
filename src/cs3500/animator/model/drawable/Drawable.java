package cs3500.animator.model.drawable;

import java.util.ArrayList;

import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.CommandType;

/**
 * A single element of an Animation that can be rendered.
 */
public interface Drawable {
  /**
   * Gets the name of this Drawable.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the base type of this Drawable.
   *
   * @return the base type
   */
  DrawableBaseType getBaseType();

  /**
   * Gets the type of this Drawable.
   *
   * @return the type
   */
  DrawableType getType();

  /**
   * Gets the time at which this Drawable appears.
   *
   * @return the time
   */
  int getAppearTime();

  /**
   * Gets the time at which this Drawable disappears.
   *
   * @return the time
   */
  int getDisappearTime();

  /**
   * Gets the position of this Drawable before the effect of any Commands.
   *
   * @return the position
   */
  float[] getPosition();

  /**
   * Gets the position of this Drawable at the given time.
   *
   * @param time the time, in ticks
   * @return the position
   */
  float[] getPosition(float time);

  /**
   * Gets the horizontal reference point of this Drawable.
   *
   * @return the horizontal reference point
   */
  XAnchor getXAnchor();

  /**
   * Gets the vertical reference point of this Drawable.
   *
   * @return the vertical reference point
   */
  YAnchor getYAnchor();

  /**
   * Gets the Color of this Drawable before the effect of any Commands.
   *
   * @return the color
   */
  float[] getColor();

  /**
   * Gets the Color of this Drawable at the given time.
   *
   * @param time the time, in ticks
   * @return the color
   */
  float[] getColor(float time);

  /**
   * Gets the width and height of this Drawable before the effect of any Commands.
   *
   * @return the dimensions
   */
  float[] getDimensions();

  /**
   * Gets the width and height of this Drawable at the given time.
   *
   * @param time the time
   * @return the dimensions
   */
  float[] getDimensions(float time);

  /**
   * Accepts a visitor.
   *
   * @param visitor the visitor
   */
  void accept(DrawableVisitor visitor);

  /**
   * Gets all Commands that modify this Drawable.
   *
   * @return the commands
   */
  ArrayList<Command> getCommands();

  /**
   * Gets all Commands that modify this Drawable of the specified CommandType.
   *
   * @param type the type
   * @return the commands
   */
  ArrayList<Command> getCommands(CommandType type);

  /**
   * Attempts to add a Command to this Drawable.
   *
   * @param command the command
   * @return whether the addition was successful
   */
  boolean addCommand(Command command);
}
