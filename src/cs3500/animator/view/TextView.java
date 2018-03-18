package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.command.ColorCommand;
import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.CommandVisitor;
import cs3500.animator.model.command.MoveCommand;
import cs3500.animator.model.command.ScaleCommand;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.DrawableBaseType;
import cs3500.animator.model.drawable.DrawableVisitor;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;

/**
 * A view for an AnimationModel that appends a String representation of the model to a
 * given Appendable.
 */
public class TextView implements AnimationView {
  private Appendable output;

  /**
   * Constructs a TextView that appends to the given Appendable.
   *
   * @param output the appendable
   */
  public TextView(Appendable output) {
    this.output = output;
  }

  @Override
  public void run(AnimationModel model, int tickRate) {
    ArrayList<Drawable> drawables = new ArrayList<>();
    drawables.addAll(model.getDrawables());

    ArrayList<Command> commands = new ArrayList<>();
    commands.addAll(model.getCommands());

    StringBuilder resultBuilder = new StringBuilder();

    for (DrawableBaseType baseType : DrawableBaseType.values()) {
      resultBuilder.append(String.format(getDrawableBaseTypeName(baseType, false) + ":%n"));

      for (Drawable drawable : drawables) {
        DrawableDataStringVisitor visitor = new DrawableDataStringVisitor(tickRate);
        drawable.accept(visitor);
        if (drawable.getBaseType() != baseType) {
          continue;
        }
        resultBuilder.append(visitor.getDataString());
        resultBuilder.append(String.format("%n"));
      }
    }

    for (int i = 0; i < commands.size(); i++) {
      Command command = commands.get(i);
      CommandDataStringVisitor visitor = new CommandDataStringVisitor(tickRate);
      command.accept(visitor);
      resultBuilder.append(visitor.getDataString());
      if (i != commands.size() - 1) {
        resultBuilder.append(String.format("%n"));
      }
    }

    try {
      this.output.append(resultBuilder);
    } catch (IOException exception) {
      throw new IllegalStateException("This TextView's Appendable cannot be appended to!");
    }
  }

  private static String getDrawableBaseTypeName(DrawableBaseType type, boolean singular)
      throws IllegalArgumentException {
    if (type == DrawableBaseType.SHAPE) {
      return singular ? "Shape" : "Shapes";
    } else {
      throw new IllegalArgumentException("That DrawableBaseType was not recognized!");
    }
  }

  private class DrawableDataStringVisitor implements DrawableVisitor {
    private float   tickRate;
    private String  dataString;

    private DrawableDataStringVisitor(float tickRate) {
      this.tickRate   = tickRate;
      this.dataString = "";
    }

    @Override
    public void visit(Rectangle rectangle) {
      float[] position = rectangle.getPosition();
      float[] color = rectangle.getColor();
      float[] dimensions = rectangle.getDimensions();
      this.dataString = String.format("Name: %s%nType: rectangle%nLower-left corner: (%.1f,%.1f), "
              + "Width: %.1f, Height: %.1f, Color: (%.1f,%.1f,%.1f)%nAppears at t=%.1fs%n"
              + "Disappears at t=%.1fs%n", rectangle.getName(), position[0],
              position[1], dimensions[0], dimensions[1], color[0], color[1], color[2],
              (float)rectangle.getAppearTime() / this.tickRate,
              (float)rectangle.getDisappearTime() / this.tickRate);
    }

    @Override
    public void visit(Oval oval) {
      float[] position = oval.getPosition();
      float[] color = oval.getColor();
      float[] dimensions = oval.getDimensions();
      this.dataString = String.format("Name: %s%nType: oval%nCenter: (%.1f,%.1f), X radius: %.1f, "
              + "Y radius: %.1f, Color: (%.1f,%.1f,%.1f)%nAppears at t=%.1fs%n"
              + "Disappears at t=%.1fs%n", oval.getName(), position[0], position[1],
              dimensions[0] / 2.0f, dimensions[1] / 2.0f,
              color[0], color[1], color[2], (float)oval.getAppearTime() / this.tickRate,
              (float)oval.getDisappearTime() / this.tickRate);
    }

    private String getDataString() {
      return this.dataString;
    }
  }

  private class CommandDataStringVisitor implements CommandVisitor {
    private float   tickRate;
    private String  dataString;

    private CommandDataStringVisitor(float tickRate) {
      this.tickRate   = tickRate;
      this.dataString = "";
    }

    private String getDataString() {
      return this.dataString;
    }

    @Override
    public void visit(MoveCommand command) {
      Drawable operand    = command.getOperand();
      float[] initialData = command.getInitialData();
      float[] finalData   = command.getFinalData();
      float start         = (float)command.getStartTime() / this.tickRate;
      float end           = (float)command.getEndTime() / this.tickRate;

      this.dataString = String.format("%s %s moves from (%.1f,%.1f) to (%.1f,%.1f) from t=%.1fs "
              + "to t=%.1fs", getDrawableBaseTypeName(operand.getBaseType(), true),
              operand.getName(), initialData[0], initialData[1], finalData[0], finalData[1],
              start, end);
    }

    @Override
    public void visit(ColorCommand command) {
      Drawable operand    = command.getOperand();
      float[] initialData = command.getInitialData();
      float[] finalData   = command.getFinalData();
      float start         = (float)command.getStartTime() / this.tickRate;
      float end           = (float)command.getEndTime() / this.tickRate;

      this.dataString = String.format("%s %s changes color from (%.1f,%.1f,%.1f) to "
              + "(%.1f,%.1f,%.1f) from t=%.1fs to t=%.1fs",
              getDrawableBaseTypeName(operand.getBaseType(), true), operand.getName(),
              initialData[0], initialData[1], initialData[2], finalData[0], finalData[1],
              finalData[2], start, end);
    }

    @Override
    public void visit(ScaleCommand command) {
      Drawable operand    = command.getOperand();
      float[] initialData = command.getInitialData();
      float[] finalData   = command.getFinalData();
      float start         = (float)command.getStartTime() / this.tickRate;
      float end           = (float)command.getEndTime() / this.tickRate;

      String horizontal;
      String vertical;
      switch (operand.getType()) {
        case RECTANGLE:
          horizontal = "Width";
          vertical = "Height";
          break;

        case OVAL:
          horizontal = "X radius";
          vertical = "Y radius";
          break;

        default:
          throw new IllegalArgumentException("The given Command has a Drawable of a bad type!");
      }

      this.dataString = String.format("%s %s scales from %s: %.1f, %s: %.1f to %s: %.1f, %s: %.1f "
              + "from t=%.1fs to t=%.1fs", getDrawableBaseTypeName(operand.getBaseType(), true),
              operand.getName(), horizontal, initialData[0], vertical, initialData[1],
              horizontal, finalData[0], vertical, finalData[1], start, end);
    }
  }
}
