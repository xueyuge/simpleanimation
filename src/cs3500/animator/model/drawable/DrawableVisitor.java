package cs3500.animator.model.drawable;

import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;

/**
 * An interface to be used by model readers to visit specific types of Drawables.
 */
public interface DrawableVisitor {
  /**
   * Visits a Rectangle.
   *
   * @param rectangle the rectangle
   */
  void visit(Rectangle rectangle);

  /**
   * Visits an Oval.
   *
   * @param oval the oval
   */
  void visit(Oval oval);
}
