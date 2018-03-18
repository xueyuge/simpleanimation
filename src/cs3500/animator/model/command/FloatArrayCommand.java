package cs3500.animator.model.command;

/**
 * A Command that operates on an array of floats of arbitrary size.
 */
public abstract class FloatArrayCommand extends ACommand<float[]> {
  /**
   * Constructs a FloatArrayCommand from the necessary data.
   *
   * @param startTime the time at which this Command starts
   * @param endTime the time at which this Command ends
   * @param initialData the data at the start time of this Command
   * @param finalData the data at the end time of this Command
   */
  public FloatArrayCommand(int startTime, int endTime, float[] initialData, float[] finalData) {
    super(startTime, endTime, initialData, finalData);
  }

  @Override
  public float[] getIntermediateData(float time)
          throws IllegalArgumentException, IllegalStateException {
    if (this.finalData.length != this.initialData.length) {
      throw new IllegalStateException("The sizes of the initial and final data should match!");
    }
    float[] result = new float[this.initialData.length];
    float timeCoefficient = (time - (float)this.startTime) / (float)(this.endTime - this.startTime);
    for (int i = 0; i < this.initialData.length; i++) {
      result[i] = this.initialData[i]
              + timeCoefficient * (this.finalData[i] - this.initialData[i]);
    }
    return result;
  }
}
