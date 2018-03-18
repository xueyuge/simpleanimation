package cs3500.animator.model.command;

import cs3500.animator.model.drawable.Drawable;

/**
 * A class containing general data and functionality for Commands.
 *
 * @param <DataType> the type of data being manipulated
 */
public abstract class ACommand<DataType> implements Command<DataType> {
  protected int       startTime;
  protected int       endTime;
  protected DataType  initialData;
  protected DataType  finalData;
  protected Drawable  operand;

  /**
   * Constructs an ACommand from the necessary data.
   *
   * @param startTime the time at which this Command starts
   * @param endTime the time at which this Command ends
   * @param initialData the data at the start time of this Command
   * @param finalData the data at the end time of this Command
   */
  public ACommand(int startTime, int endTime, DataType initialData, DataType finalData) {
    this.startTime    = startTime;
    this.endTime      = endTime;
    this.initialData  = initialData;
    this.finalData    = finalData;
    this.operand      = null;

    if (this.startTime < 0 || this.endTime < 0) {
      throw new IllegalArgumentException("Commands cannot have negative start or end times!");
    }

    if (this.startTime >= this.endTime) {
      throw new IllegalArgumentException("A Command must start before it ends!");
    }
  }

  @Override
  public void attach(Drawable operand) {
    if (operand == null) {
      throw new IllegalArgumentException("Cannot attach a null operand to a Command!");
    }

    if (!operand.addCommand(this)) {
      throw new IllegalArgumentException("The ACommand being built has a time conflict!");
    }

    this.operand = operand;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public Drawable getOperand() {
    return this.operand;
  }

  //Command.getInitialData() and Command.getFinalData() are unimplemented to allow extenders to
  //Copy their data rather than forcing them to provide a pointer.
}
