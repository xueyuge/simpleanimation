package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.command.ColorCommand;
import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.MoveCommand;
import cs3500.animator.model.command.ScaleCommand;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.XAnchor;
import cs3500.animator.model.drawable.YAnchor;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;
import util.TweenModelBuilder;

/**
 * Represents a system of Drawables and Commands to form an AnimationModel.
 */
public class DrawableSystem implements AnimationModel {
  private final ArrayList<Command> commands;
  private final ArrayList<Drawable> drawables;

  /**
   * Constructs a DrawableSystem with no Commands or Drawables.
   */
  private DrawableSystem() {
    this.commands = new ArrayList<>();
    this.drawables = new ArrayList<>();
  }

  @Override
  public List<Command> getCommands() {
    return this.commands;
  }

  @Override
  public List<Drawable> getDrawables() {
    return this.drawables;
  }

  @Override
  public void addDrawable(Drawable drawable, String name) {
    for (Drawable current : this.drawables) {
      if (current.getName().equals(name)) {
        throw new IllegalArgumentException("A Drawable with that name is already in the model!");
      }
    }
    this.drawables.add(drawable);
  }

  @Override
  public void addCommand(Command command, String name) {
    Drawable operand = null;
    for (Drawable current : this.drawables) {
      if (current.getName().equals(name)) {
        operand = current;
        break;
      }
    }
    if (operand == null) {
      throw new IllegalArgumentException("No Drawable with that name was found!");
    }
    command.attach(operand);
    this.commands.add(command);
  }

  /**
   * A class to construct DrawableSystems from lists of Commands and Drawables.
   */
  public static final class Builder implements TweenModelBuilder<AnimationModel> {
    private final DrawableSystem model;

    /**
     * Constructs a Builder with empty lists of Commands and Drawables.
     */
    public Builder() {
      this.model = new DrawableSystem();
    }

    @Override
    public TweenModelBuilder<AnimationModel> addOval(String name, float cx, float cy,
                                                     float xRadius, float yRadius,
                                                     float red, float green, float blue,
                                                     int startOfLife, int endOfLife) {
      this.model.addDrawable(new Oval(name, startOfLife, endOfLife, cx, cy,
                  XAnchor.CENTER, YAnchor.CENTER, red, green, blue,
                  xRadius * 2.0f, yRadius * 2.0f), name);
      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModel> addRectangle(String name, float lx, float ly,
                                                          float width, float height,
                                                          float red, float green, float blue,
                                                          int startOfLife, int endOfLife) {
      this.model.addDrawable(new Rectangle(name, startOfLife, endOfLife, lx, ly,
              XAnchor.LEFT, YAnchor.TOP, red, green, blue, width, height), name);
      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModel> addMove(String name, float moveFromX, float moveFromY,
                                                     float moveToX, float moveToY,
                                                     int startTime, int endTime) {
      this.model.addCommand(new MoveCommand(startTime, endTime, moveFromX, moveFromY,
              moveToX, moveToY), name);
      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModel> addColorChange(String name,
                                                            float oldR, float oldG, float oldB,
                                                            float newR, float newG, float newB,
                                                            int startTime, int endTime) {
      this.model.addCommand(new ColorCommand(startTime, endTime, oldR, oldG, oldB,
              newR, newG, newB), name);
      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModel> addScaleToChange(String name,
                                                              float fromSx, float fromSy,
                                                              float toSx, float toSy,
                                                              int startTime, int endTime) {
      this.model.addCommand(new ScaleCommand(startTime, endTime, fromSx, fromSy,
              toSx, toSy), name);
      return this;
    }

    @Override
    public AnimationModel build() {
      return this.model;
    }
  }
}
