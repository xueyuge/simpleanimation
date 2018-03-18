package cs3500.animator.model.command;

import cs3500.animator.model.drawable.Drawable;

/**
 * Describes a change of some property of a Drawable over the course of time.
 *
 * @param <DataType> the property's data type; i.e. the type of data being manipulated
 */
public interface Command<DataType> {
  /**
   * Attaches the operand of this Command to it.
   *
   * @param operand the operand
   */
  void attach(Drawable operand);

  /**
   * Gets the time at which this Command starts.
   *
   * @return the time
   */
  int getStartTime();

  /**
   * Gets the time at which this Command ends.
   *
   * @return the time
   */
  int getEndTime();

  /**
   * Gets the Drawable on which this Command operates.
   *
   * @return the operand
   */
  Drawable getOperand();

  /**
   * Gets the type of Command this is.
   *
   * @return the type
   */
  CommandType getType();

  /**
   * Gets the data at the time at which the Command begins.
   *
   * @return the data
   */
  DataType getInitialData();

  /**
   * Gets the data at the time at which the Command ends.
   *
   * @return the data
   */
  DataType getFinalData();
  
  /**
   * Gets the data at the specified time.
   *
   * @param time the time, in ticks
   * @return the data
   * @throws IllegalArgumentException when the given time is out of the bounds of this Command.
   */
  DataType getIntermediateData(float time) throws IllegalArgumentException;

  /**
   * Accepts a CommandVisitor.
   *
   * @param visitor the visitor
   */
  void accept(CommandVisitor visitor);
}
