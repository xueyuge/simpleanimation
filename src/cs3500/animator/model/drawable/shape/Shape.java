package cs3500.animator.model.drawable.shape;

import cs3500.animator.model.drawable.ADrawable;
import cs3500.animator.model.drawable.DrawableBaseType;
import cs3500.animator.model.drawable.XAnchor;
import cs3500.animator.model.drawable.YAnchor;

/**
 * Represents a simple shape with one solid color.
 */
public abstract class Shape extends ADrawable {
  /**
   * Constructs a shape from the necessary data.
   *
   * @param name the name
   * @param appearTime the time at which this Drawable appears
   * @param disappearTime the time at which this Drawable disappears
   * @param x the initial horizontal position of this Drawable
   * @param y the initial vertical position of this Drawable
   * @param xAnchor the horizontal reference point of this Drawable's position
   * @param yAnchor the vertical reference point of this Drawable's position
   * @param r the red component of the color
   * @param g the green component of the color
   * @param b the blue component of the color
   * @param width the width
   * @param height the height
   */
  public Shape(String name, int appearTime, int disappearTime,
        float x, float y, XAnchor xAnchor, YAnchor yAnchor,
        float r, float g, float b, float width, float height) {
    super(name, appearTime, disappearTime, x, y, xAnchor, yAnchor, r, g, b, width,
            height);
  }

  @Override
  public DrawableBaseType getBaseType() {
    return DrawableBaseType.SHAPE;
  }
}
