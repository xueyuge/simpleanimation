package cs3500.animator.model.command;

/**
 * A FloatArrayCommand that transforms a Color.
 */
public class ColorCommand extends FloatArrayCommand {
  /**
   * Constructs a ColorCommand from the necessary data.
   *
   * @param startTime the time at which this Command starts
   * @param endTime the time at which this Command ends
   * @param oldR the red component of the initial Color
   * @param oldG the green component of the initial Color
   * @param oldB the blue component of the initial Color
   * @param newR the red component of the final Color
   * @param newG the green component of the final Color
   * @param newB the blue component of the final Color
   */
  public ColorCommand(int startTime, int endTime,
                      float oldR, float oldG, float oldB, float newR, float newG, float newB) {
    super(startTime, endTime, new float[] {oldR, oldG, oldB}, new float[] {newR, newG, newB});
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
    return CommandType.COLOR;
  }

  @Override
  public void accept(CommandVisitor visitor) {
    visitor.visit(this);
  }
}
