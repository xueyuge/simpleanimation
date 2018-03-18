import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DrawableSystem;
import cs3500.animator.view.SVGView;
import org.junit.Assert;
import org.junit.Test;
import util.AnimationFileReader;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;

/**
 * A class for testing SVGAnimationViews.
 */
public class TestSVGView {
  /**
   * Tests that an SVGView correctly outputs what is expected for smalldemo.txt.
   */
  @Test
  public void testSVGOutput() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    SVGView view = new SVGView(output);
    view.run(model, 1);

    String comparison = String.format("<?xml version=\"1.0\"?>%n<svg width=\"800\" "
            + "height=\"600\" viewPort="
            + "\"0 0 800 600\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">%n"
            + "\t<rect id=\"R\" width=\"50\" height=\"100\" x=\"200\" y=\"200\" "
            + "fill=\"rgb(255,0,0)\" visibility=\"hidden\">%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" begin=\"1.000s\" "
            + "dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" begin=\"100.000s\" "
            + "dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"x\" from=\"200\" to=\"300\" begin=\"10.000s\" dur=\"40.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"y\" from=\"200\" to=\"300\" begin=\"10.000s\" dur=\"40.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"x\" from=\"300\" to=\"200\" begin=\"70.000s\" dur=\"30.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"y\" from=\"300\" to=\"200\" begin=\"70.000s\" dur=\"30.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"width\" from=\"50\" to=\"25\" begin=\"51.000s\" dur=\"19.000s\""
            + " fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"height\" from=\"100\" to=\"100\" begin=\"51.000s\" dur=\"19.000"
            + "s\" fill=\"freeze\"/>%n\t</rect>%n\t<ellipse id=\"C\" cx=\"500\" cy=\"100\" "
            + "rx=\"60\" ry=\"30\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"visibility\" from=\"hidden\" "
            + "to=\"visible\" begin=\"6.000s\" dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"visibility\" from=\"visible\" "
            + "to=\"hidden\" begin=\"100.000s\" dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate"
            + " attributeType=\"XML\" attributeName=\"cx\" from=\"500\" to=\"500\" "
            + "begin=\"20.000s\" dur=\"50.000s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"cy\" from=\"100\" to=\"400\" "
            + "begin=\"20.000s\" dur=\"50.000s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"fill\" from=\"rgb(0,0,255)\" "
            + "to=\"rgb(0,255,0)\" begin=\"50.000s\" dur=\"30.000s\" fill=\"freeze\"/>"
            + "%n\t</ellipse>%n</svg>");

    assertEquals(comparison, output.toString());
  }
}
