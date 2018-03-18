package cs3500.animator;

import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DrawableSystem;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.visualview.VisualView;
import util.AnimationFileReader;
import util.TweenModelBuilder;

import javax.swing.JOptionPane;

/**
 * The entry point for the EasyAnimator software.
 */
public final class EasyAnimator {
  /**
   * Begins the program.
   *
   * @param args flags determining the animation to view and the type of view. Flags:
   *             -if filepath
   *                gives the filepath of the file from which data should be read.
   *             -iv view
   *                determines the type of view to be used ("text", "svg", or "visual").
   *             -o output
   *                determines the output location of svg/txt viws; if set to "out", will use
   *                System.out, otherwise will try to write to a file of the given name
   *             -speed tickrate
   *                determines the number of ticks per second.
   */
  public static void main(String[] args) {
    AnimationFileReader fr = new AnimationFileReader();
    TweenModelBuilder<AnimationModel> tmb = new DrawableSystem.Builder();

    String viewType       = null;
    String inputFilepath  = null;

    Appendable  ap              = System.out;
    boolean     appendingToFile = false;
    int         tickRate        = 1;
    boolean     testing         = false;

    String currentFlag = null;

    try {
      for (String arg : args) {
        if (arg.length() < 1) {
          throw new IllegalArgumentException("Arguments cannot be of zero length!");
        }

        if (currentFlag == null) {
          if (arg.equals("-test")) {
            testing = true;
            continue;
          } else if (arg.charAt(0) != '-') {
            throw new IllegalArgumentException("Found input when expecting a flag!");
          }
          currentFlag = arg;
        } else {
          if (arg.charAt(0) == '-') {
            throw new IllegalArgumentException("Found a flag when expecting an input!");
          }

          switch (currentFlag) {
            case "-iv":
              viewType = arg;
              break;

            case "-if":
              inputFilepath = arg;
              break;

            case "-o":
              if (arg.equals("out")) {
                ap = System.out;
              } else {
                try {
                  ap = new FileWriter(arg);
                  appendingToFile = true;
                } catch (IOException exception) {
                  throw new IllegalArgumentException("The specified output file couldn't "
                          + "be created!");
                }
              }
              break;

            case "-speed":
              try {
                tickRate = Integer.parseInt(arg);
              } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("A bad tick rate was given!");
              }
              if (tickRate < 1) {
                throw new IllegalArgumentException("The tick rate must be at least 1!");
              }
              break;

            default:
              throw new IllegalArgumentException("The argument reader has a bad flag stored!");
          }

          currentFlag = null;
        }
      }

      if (currentFlag != null) {
        throw new IllegalArgumentException("A flag has been given no input!");
      }

      if (viewType == null) {
        throw new IllegalArgumentException("A view type must be provided!");
      }

      if (inputFilepath == null) {
        throw new IllegalArgumentException("An input filepath must be provided!");
      }

      try {
        fr.readFile(inputFilepath, tmb);
      } catch (IOException exception) {
        throw new IllegalArgumentException("A bad animation filepath was given!");
      }
      AnimationModel model = tmb.build();

      AnimationView view;

      switch (viewType) {
        case "text":
          view = new TextView(ap);
          break;

        case "visual":
          view = new VisualView();
          break;

        case "svg":
          view = new SVGView(ap);
          break;

        default:
          throw new IllegalArgumentException("A bad view type was given!");
      }

      view.run(model, tickRate);

      if (appendingToFile) {
        try {
          ((FileWriter)ap).close();
        } catch (IOException exception) {
          throw new IllegalStateException("Unable to close file!");
        } catch (ClassCastException exception) {
          throw new IllegalStateException("Appendable was not a FileWriter!");
        }
      }
    } catch (Exception exception) {
      if (testing) {
        throw exception;
      }
      JOptionPane.showMessageDialog(null, exception.getMessage(), "Animator argument error!",
              JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
  }
}

