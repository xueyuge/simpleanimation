package cs3500.animator.model.command;

/**
 * Objects that need to get subclass-specific data on Commands can do so by
 * implementing CommandVisitor.
 */
public interface CommandVisitor {
  /**
   * Visits a MoveCommand.
   *
   * @param command the MoveCommand
   */
  void visit(MoveCommand command);

  /**
   * Visits a ColorCommand.
   *
   * @param command the ColorCommand
   */
  void visit(ColorCommand command);

  /**
   * Visits a ScaleCommand.
   *
   * @param command the ScaleCommand
   */
  void visit(ScaleCommand command);
}
