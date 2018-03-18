package cs3500.animator.model.command;

/**
 * A Command that moves a Drawable.
 */
public class MoveCommand extends FloatArrayCommand {
  /**
   * Constructs a MoveCommand from the necessary data.
   *
   * @param startTime the time at which this Command starts
   * @param endTime the time at which this Command ends
   * @param initialX the initial x-position
   * @param initialY the initial y-position
   * @param finalX the final x-position
   * @param finalY the final y-position
   */
  public MoveCommand(int startTime, int endTime,
                     float initialX, float initialY, float finalX, float finalY) {
    super(startTime, endTime, new float[] {initialX, initialY}, new float[] {finalX, finalY});
  }

  @Override
  public float[] getInitialData() {
    return this.initialData;
  }

  @Override
  public float[] getFinalData() {
    return this.finalData;
  }

  @Override
  public CommandType getType() {
    return CommandType.MOVE;
  }

  @Override
  public void accept(CommandVisitor visitor) {
    visitor.visit(this);
  }
}
