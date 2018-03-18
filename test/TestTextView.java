import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DrawableSystem;
import cs3500.animator.view.TextView;
import util.AnimationFileReader;

import static org.junit.Assert.assertEquals;

/**
 * A class to test TextViews.
 */
public class TestTextView {
  /**
   * Tests that the output of a TextView matches what is expected.
   */
  @Test
  public void testTextViewOutput() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    TextView view = new TextView(output);
    view.run(model, 2);

    assertEquals(String.format("Shapes:%nName: R%nType: rectangle%nLower-left corner: (200.0,200.0"
            + "), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)%nAppears at t=0.5s%n"
            + "Disappears at t=50.0s%n%nName: C%nType: oval%nCenter: (500.0,100.0), X radius: 60.0"
            + ", Y radius: 30.0, Color: (0.0,0.0,1.0)%nAppears at t=3.0s%nDisappears at t=50.0s%n"
            + "%nShape R moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=25.0s%n"
            + "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=10.0s to t=35.0s%n"
            + "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=25.0s to t=40.0s%n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s%nShape R "
            + "scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from t=25.5s "
            + "to t=35.0s"), output.toString());
  }
}
