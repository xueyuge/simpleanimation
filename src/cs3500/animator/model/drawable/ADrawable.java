package cs3500.animator.model.drawable;

import java.util.ArrayList;

import cs3500.animator.model.command.ColorCommand;
import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.CommandType;
import cs3500.animator.model.command.MoveCommand;
import cs3500.animator.model.command.ScaleCommand;

/**
 * An abstract class for common data and functionality of Drawables.
 */
public abstract class ADrawable implements Drawable {
  private   final String                        name;
  private   final int                           appearTime;
  private   final int                           disappearTime;
  private   final float[]                       position;
  private   final XAnchor                       xAnchor;
  private   final YAnchor                       yAnchor;
  private   final float[]                       color;
  private   final float[]                       dimensions;
  private   final ArrayList<ArrayList<Command>> commands;

  /**
   * Constructs a Drawable from the necessary data.
   *
   * @param name the name
   * @param appearTime the time at which this Drawable appears
   * @param disappearTime the time at which this Drawable disappears
   * @param x the initial horizontal position of this Drawable
   * @param y the initial vertical position of this Drawable
   * @param xAnchor the horizontal reference
   * @param yAnchor the vertical reference
   * @param red the red component of the color
   * @param green the green component of the color
   * @param blue the blue component of the color
   * @param width the width
   * @param height the height
   */
  public ADrawable(String name, int appearTime, int disappearTime, float x, float y,
                   XAnchor xAnchor, YAnchor yAnchor, float red, float green, float blue,
                   float width, float height) {
    this.name           = name;
    this.appearTime     = appearTime;
    this.disappearTime  = disappearTime;
    this.position       = new float[] {x, y};
    this.xAnchor        = xAnchor;
    this.yAnchor        = yAnchor;
    this.color          = new float[] {red, green, blue};
    this.dimensions     = new float[] {width, height};
    this.commands       = new ArrayList<>();

    if (this.appearTime < 0 || this.disappearTime < 0) {
      throw new IllegalArgumentException("Drawables cannot appear or disappear in negative time!");
    }

    if (this.appearTime >= this.disappearTime) {
      throw new IllegalArgumentException("Drawables cannot disappear before they appear!");
    }

    for (float color : this.color) {
      if (color < 0.0f || color > 1.0f) {
        throw new IllegalArgumentException("Color components must be between 0.0 and 1.0!");
      }
    }

    if (this.dimensions[0] <= 0.0f || this.dimensions[1] <= 0.0f) {
      throw new IllegalArgumentException("Drawables must have positive dimensions!");
    }

    for (int i = 0; i < CommandType.values().length; i++) {
      this.commands.add(new ArrayList<>());
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getAppearTime() {
    return this.appearTime;
  }

  @Override
  public int getDisappearTime() {
    return this.disappearTime;
  }

  @Override
  public float[] getPosition() {
    return new float[] {this.position[0], this.position[1]};
  }

  @Override
  public float[] getPosition(float time) {
    ArrayList<Command> commandList = this.commands.get(CommandType.MOVE.ordinal());
    for (int i = 0; i < commandList.size(); i++) {
      MoveCommand command = (MoveCommand)commandList.get(i);
      if (command.getEndTime() < time) {
        continue;
      }

      if (command.getStartTime() > time) {
        if (i == 0) {
          return new float[] {this.position[0], this.position[1]};
        } else {
          float[] data = ((MoveCommand)commandList.get(i - 1)).getFinalData();
          return new float[] {data[0], data[1]};
        }
      }

      return command.getIntermediateData(time);
    }
    if (commandList.size() == 0) {
      return new float[] {this.position[0], this.position[1]};
    } else {
      float[] data = ((MoveCommand)commandList.get(commandList.size() - 1)).getFinalData();
      return new float[] {data[0], data[1]};
    }
  }

  @Override
  public XAnchor getXAnchor() {
    return this.xAnchor;
  }

  @Override
  public YAnchor getYAnchor() {
    return this.yAnchor;
  }

  @Override
  public float[] getColor() {
    return this.color;
  }

  @Override
  public float[] getColor(float time) {
    ArrayList<Command> commandList = this.commands.get(CommandType.COLOR.ordinal());
    for (int i = 0; i < commandList.size(); i++) {
      ColorCommand command = (ColorCommand)commandList.get(i);
      if (command.getEndTime() < time) {
        continue;
      }

      if (command.getStartTime() > time) {
        if (i == 0) {
          return new float[] {this.color[0], this.color[1], this.color[2]};
        } else {
          float[] data = ((ColorCommand)commandList.get(i - 1)).getFinalData();
          return new float[] {data[0], data[1], data[2]};
        }
      }

      return command.getIntermediateData(time);
    }
    if (commandList.size() == 0) {
      return new float[] {this.color[0], this.color[1], this.color[2]};
    } else {
      float[] data = ((ColorCommand)commandList.get(commandList.size() - 1)).getFinalData();
      return new float[] {data[0], data[1], data[2]};
    }
  }

  @Override
  public float[] getDimensions() {
    return new float[] {this.dimensions[0], this.dimensions[1]};
  }

  @Override
  public float[] getDimensions(float time) {
    ArrayList<Command> commandList = this.commands.get(CommandType.SCALE.ordinal());
    for (int i = 0; i < commandList.size(); i++) {
      ScaleCommand command = (ScaleCommand)commandList.get(i);
      if (command.getEndTime() < time) {
        continue;
      }

      if (command.getStartTime() > time) {
        if (i == 0) {
          return new float[] {this.dimensions[0], this.dimensions[1]};
        } else {
          float[] data = ((ScaleCommand)commandList.get(i - 1)).getFinalData();
          return new float[] {data[0], data[1]};
        }
      }

      return command.getIntermediateData(time);
    }
    if (commandList.size() == 0) {
      return new float[] {this.dimensions[0], this.dimensions[1]};
    } else {
      float[] data = ((ScaleCommand)commandList.get(commandList.size() - 1)).getFinalData();
      return new float[] {data[0], data[1]};
    }
  }

  @Override
  public ArrayList<Command> getCommands() {
    ArrayList<Command> result = new ArrayList<>();
    for (ArrayList<Command> subList : this.commands) {
      result.addAll(subList);
    }
    return result;
  }


  @Override
  public ArrayList<Command> getCommands(CommandType type) {
    return this.commands.get(type.ordinal());
  }

  @Override
  public boolean addCommand(Command newCommand) {
    ArrayList<Command> commandList = this.commands.get(newCommand.getType().ordinal());
    for (int i = 0; i < commandList.size(); i++) {
      Command command = commandList.get(i);
      if (newCommand.getStartTime() > command.getEndTime()) {
        continue;
      }

      if (newCommand.getEndTime() < command.getStartTime()) {
        commandList.add(i, newCommand);
        return true;
      }

      return false;
    }
    commandList.add(newCommand);
    return true;
  }
}
