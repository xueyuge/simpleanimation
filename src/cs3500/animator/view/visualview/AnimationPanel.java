package cs3500.animator.view.visualview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.DrawableVisitor;
import cs3500.animator.model.drawable.XAnchor;
import cs3500.animator.model.drawable.YAnchor;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;

/**
 * A helper for VisualView that draws the shapes and keeps track of time.
 */
public class AnimationPanel extends JPanel implements ActionListener {
  private AnimationModel  model;
  private float           currentTick;
  private int             currentFrame;
  private float           tickTime;
  private float           frameTime;

  /**
   * Constructs an AnimationPanel.
   *
   * @param model the AnimationModel
   * @param frameTime the change in time between frames, in seconds
   * @param tickTime the change in time between animation ticks, in seconds
   */
  public AnimationPanel(AnimationModel model, float frameTime, float tickTime) {
    super();

    this.model        = model;
    this.currentTick  = 0.0f;
    this.currentFrame = 0;
    this.tickTime     = tickTime;
    this.frameTime    = frameTime;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);

    PaintVisitor visitor = new PaintVisitor(graphics);
    for (Drawable drawable : model.getDrawables()) {
      drawable.accept(visitor);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.currentFrame++;
    this.currentTick = this.currentFrame * this.frameTime / this.tickTime;
  }

  private class PaintVisitor implements DrawableVisitor {
    private Graphics  graphics;

    private PaintVisitor(Graphics graphics) {
      this.graphics = graphics;
    }

    @Override
    public void visit(Rectangle rectangle) {
      float[] position = rectangle.getPosition(currentTick);
      float[] color = rectangle.getColor(currentTick);
      float[] dimensions = rectangle.getDimensions(currentTick);
      this.graphics.setColor(new Color(color[0], color[1], color[2]));
      graphics.fillRect(getTrueX((int)position[0], (int)dimensions[0], rectangle.getXAnchor()),
              getTrueY((int)position[1], (int)dimensions[1], rectangle.getYAnchor()),
              (int)dimensions[0], (int)dimensions[1]);
    }

    @Override
    public void visit(Oval oval) {
      float[] position = oval.getPosition(currentTick);
      float[] color = oval.getColor(currentTick);
      float[] dimensions = oval.getDimensions(currentTick);
      this.graphics.setColor(new Color(color[0], color[1], color[2]));
      graphics.fillOval(getTrueX((int)position[0], (int)dimensions[0], oval.getXAnchor()),
              getTrueY((int)position[1], (int)dimensions[1], oval.getYAnchor()),
              (int)dimensions[0], (int)dimensions[1]);
    }

    private int getTrueX(int oldX, int width, XAnchor anchor) throws IllegalArgumentException {
      switch (anchor) {
        case LEFT:
          return oldX;

        case RIGHT:
          return oldX - width;

        case CENTER:
          return oldX - width / 2;

        default:
          throw new IllegalArgumentException("A bad XAnchor was given!");
      }
    }

    private int getTrueY(int oldY, int height, YAnchor anchor) throws IllegalArgumentException {
      switch (anchor) {
        case TOP:
          return oldY;

        case BOTTOM:
          return oldY - height;

        case CENTER:
          return oldY - height / 2;

        default:
          throw new IllegalArgumentException("A bad XAnchor was given!");
      }
    }
  }
}
