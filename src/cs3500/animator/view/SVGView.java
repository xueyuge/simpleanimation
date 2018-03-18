package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.command.ColorCommand;
import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.CommandVisitor;
import cs3500.animator.model.command.MoveCommand;
import cs3500.animator.model.command.ScaleCommand;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.DrawableVisitor;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;

import java.io.IOException;

/**
 * An AnimationView that outputs the data in a Scalable Vector Graphics format.
 */
public class SVGView implements AnimationView {
  private Appendable output;

  /**
   * Constructs an SVGView.
   *
   * @param output the Appendable to receive output
   */
  public SVGView(Appendable output) {
    this.output = output;
  }

  @Override
  public void run(AnimationModel model, int tickRate) {
    StringBuilder beginString = new StringBuilder();

    beginString.append(String.format("<?xml version=\"1.0\"?>%n<svg width=\"800\" height=\"600"));
    beginString.append("\" viewPort=\"0 0 800 600\" version=\"1.1\" ");
    beginString.append(String.format("xmlns=\"http://www.w3.org/2000/svg\">%n"));

    try {
      this.output.append(beginString);
    } catch (IOException exception) {
      throw new IllegalStateException("This SVGView's Appendable cannot be appended to!");
    }

    for (Drawable drawable : model.getDrawables()) {
      StringBuilder drawableString = new StringBuilder();
      SVGDrawableVisitor visitor = new SVGDrawableVisitor();
      drawable.accept(visitor);
      drawableString.append(visitor.getOpenString());
      drawableString.append(String.format("%n"));
      String appear = String.format("\t\t<animate attributeType=\"XML\" attributeName="
              + "\"visibility\" from=\"hidden\" to=\"visible\" begin=\"%.3fs\" dur=\"0.001s\" "
              + "fill=\"freeze\"/>%n", (float)drawable.getAppearTime() / (float)tickRate);
      String disappear = String.format("\t\t<animate attributeType=\"XML\" attributeName="
              + "\"visibility\" from=\"visible\" to=\"hidden\" begin=\"%.3fs\" dur=\"0.001s\" "
              + "fill=\"freeze\"/>%n", (float)drawable.getDisappearTime() / (float)tickRate);
      drawableString.append(appear);
      drawableString.append(disappear);
      for (Command command : drawable.getCommands()) {
        SVGCommandVisitor commandVisitor = new SVGCommandVisitor(tickRate);
        command.accept(commandVisitor);
        drawableString.append(commandVisitor.getDataString());
        drawableString.append(String.format("%n"));
      }
      drawableString.append(visitor.getCloseString());
      drawableString.append(String.format("%n"));
      try {
        this.output.append(drawableString);
      } catch (IOException exception) {
        throw new IllegalStateException("This SVGView's Appendable cannot be appended to!");
      }
    }

    try {
      this.output.append("</svg>");
    } catch (IOException exception) {
      throw new IllegalStateException("This SVGView's Appendable cannot be appended to!");
    }
  }

  private class SVGDrawableVisitor implements DrawableVisitor {
    private String openString;
    private String closeString;

    private SVGDrawableVisitor() {
      this.openString = "";
      this.closeString = "";
    }

    @Override
    public void visit(Rectangle rectangle) {
      float[] position = rectangle.getPosition();
      float[] color = rectangle.getColor();
      float[] dimensions = rectangle.getDimensions();
      this.openString = String.format("\t<rect id=\"%s\" width=\"%d\" height=\"%d\" x=\"%d\" "
              + "y=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\">", rectangle.getName(),
              (int)dimensions[0], (int)dimensions[1], (int)position[0], (int)position[1],
              (int)(color[0] * 255.0f), (int)(color[1] * 255.0f), (int)(color[2] * 255.0f));
      this.closeString = "\t</rect>";
    }

    @Override
    public void visit(Oval oval) {
      float[] position = oval.getPosition();
      float[] color = oval.getColor();
      float[] dimensions = oval.getDimensions();
      this.openString = String.format("\t<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" "
              + "ry=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\">", oval.getName(),
              (int)position[0], (int)position[1], (int)(dimensions[0] / 2.0f),
              (int)(dimensions[1] / 2.0f), (int)(color[0] * 255.0f), (int)(color[1] * 255.0f),
              (int)(color[2] * 255.0f));
      this.closeString = "\t</ellipse>";
    }

    private String getOpenString() {
      return this.openString;
    }

    private String getCloseString() {
      return this.closeString;
    }
  }

  private class SVGCommandVisitor implements CommandVisitor {
    private String  dataString;
    private int     tickRate;

    private SVGCommandVisitor(int tickRate) {
      this.dataString = "";
      this.tickRate   = tickRate;
    }

    @Override
    public void visit(MoveCommand command) {
      String horizontal;
      String vertical;
      switch (command.getOperand().getType()) {
        case RECTANGLE:
          horizontal  = "x";
          vertical    = "y";
          break;

        case OVAL:
          horizontal  = "cx";
          vertical    = "cy";
          break;

        default:
          throw new IllegalStateException("The Command has an operand of bad DrawableType!");
      }
      this.dataString = String.format("\t\t<animate attributeType=\"XML\" attributeName=\"%s\" "
              + "from=\"%d\" to=\"%d\" begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>%n\t\t"
              + "<animate attributeType=\"XML\" attributeName=\"%s\" from=\"%d\" to=\"%d\" "
              + "begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>", horizontal,
              (int)command.getInitialData()[0], (int)command.getFinalData()[0],
              (float)command.getStartTime() / (float)tickRate,
              (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate,
              vertical, (int)command.getInitialData()[1], (int)command.getFinalData()[1],
              (float)command.getStartTime() / (float)tickRate,
              (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate);
    }

    @Override
    public void visit(ColorCommand command) {
      this.dataString = String.format("\t\t<animate attributeType=\"XML\" attributeName=\"fill\" "
              + "from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" begin=\"%.3fs\" dur=\"%.3fs\" "
              + "fill=\"freeze\"/>", (int)(command.getInitialData()[0] * 255.0f),
              (int)(command.getInitialData()[1] * 255.0f),
              (int)(command.getInitialData()[2] * 255.0f),
              (int)(command.getFinalData()[0] * 255.0f), (int)(command.getFinalData()[1] * 255.0f),
              (int)(command.getFinalData()[2] * 255.0f),
              (float)command.getStartTime() / (float)tickRate,
              (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate);
    }

    @Override
    public void visit(ScaleCommand command) {
      String horizontal;
      String vertical;
      float initialWidth;
      float initialHeight;
      float finalWidth;
      float finalHeight;
      switch (command.getOperand().getType()) {
        case RECTANGLE:
          horizontal    = "width";
          vertical      = "height";
          initialWidth  = command.getInitialData()[0];
          initialHeight = command.getInitialData()[1];
          finalWidth    = command.getFinalData()[0];
          finalHeight   = command.getFinalData()[1];
          break;

        case OVAL:
          horizontal  = "rx";
          vertical    = "ry";
          initialWidth  = command.getInitialData()[0] / 2.0f;
          initialHeight = command.getInitialData()[1] / 2.0f;
          finalWidth    = command.getFinalData()[0] / 2.0f;
          finalHeight   = command.getFinalData()[1] / 2.0f;
          break;

        default:
          throw new IllegalStateException("The Command has an operand of bad DrawableType!");
      }
      this.dataString = String.format("\t\t<animate attributeType=\"XML\" attributeName=\"%s\" "
                      + "from=\"%d\" to=\"%d\" begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>%n\t"
                      + "\t<animate attributeType=\"XML\" attributeName=\"%s\" from=\"%d\" to=\"%d"
                      + "\" begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>",
              horizontal, (int)initialWidth, (int)finalWidth,
              (float)command.getStartTime() / (float)tickRate,
              (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate,
              vertical, (int)initialHeight, (int)finalHeight,
              (float)command.getStartTime() / (float)tickRate,
              (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate);
    }

    private String getDataString() {
      return this.dataString;
    }
  }
}
