package cs3500.animator.model.command;

import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.DrawableType;

/**
 * A Command that scales a Drawable's size.
 */
public class ScaleCommand extends FloatArrayCommand {
  /**
   * Constructs a ScaleCommand.
   *
   * @param startTime the time at which this Command starts
   * @param endTime the time at which this Command ends
   * @param initialX the initial x-scale
   * @param initialY the initial y-scale
   * @param finalX the final x-scale
   * @param finalY the final y-scale
   */
  public ScaleCommand(int startTime, int endTime,
                      float initialX, float initialY, float finalX, float finalY) {
    super(startTime, endTime, new float[] {initialX, initialY}, new float[] {finalX, finalY});
  }

  @Override
  public void attach(Drawable operand) {
    super.attach(operand);
    if (operand.getType() == DrawableType.OVAL) {
      this.initialData[0]  *= 2.0f;
      this.initialData[1]  *= 2.0f;
      this.finalData[0]    *= 2.0f;
      this.finalData[1]    *= 2.0f;
    }
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
    return CommandType.SCALE;
  }


  @Override
  public void accept(CommandVisitor visitor) {
    visitor.visit(this);
  }
}
