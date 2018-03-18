import org.junit.Assert;
import org.junit.Test;

import cs3500.animator.model.DrawableSystem;
import util.AnimationFileReader;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestModel {
  @Test
  public void testExamplesLoad() {
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    AnimationFileReader reader = new AnimationFileReader();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/big-bang-big-crunch.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/buildings.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/toh-3.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/toh-5.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/toh-8.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }

    builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/toh-12.txt", builder);
    } catch (IOException exception) {
      Assert.fail(exception.getMessage());
    }
  }

  @Test
  public void testDrawableAddition() {
    DrawableSystem.Builder builder = new DrawableSystem.Builder();

    try {
      builder.addRectangle("test", 5.0f, 5.0f, -5.0f, 5.0f, 0.0f, 0.0f, 1.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with negative width!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables must have positive dimensions!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, -5.0f, 0.0f, 0.0f, 1.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with negative height!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables must have positive dimensions!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.2f, 0.0f, 0.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a > 1.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 0.0f, 58.9f, 0.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a > 1.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 3.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a > 1.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, -1.0f, 0.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a < 0.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, -1.2f, 0.0f, 0.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a < 0.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, -59.0f, 0, 100);
      Assert.fail("Should not be able to create Drawable with a < 0.0 color component!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Color components must be between 0.0 and 1.0!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, 0, -100);
      Assert.fail("Should not be able to create Drawable with a negative disappear time!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables cannot appear or disappear in negative time!",
              exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, -50, 100);
      Assert.fail("Should not be able to create Drawable with a negative appear time!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables cannot appear or disappear in negative time!",
              exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, 20, 20);
      Assert.fail("Should not be able to create Drawable with bad appear/disappear times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables cannot disappear before they appear!", exception.getMessage());
    }

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, 25, 20);
      Assert.fail("Should not be able to create Drawable with bad appear/disappear times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Drawables cannot disappear before they appear!", exception.getMessage());
    }

    builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, 20, 25);

    try {
      builder.addRectangle("test", 5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 0.0f, 1.0f, 20, 25);
      Assert.fail("Should not be able to add Drawable with same name!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A Drawable with that name is already in the model!", exception.getMessage());
    }
  }

  @Test
  public void testCommandAddition() {
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    builder.addRectangle("testRect", 50.0f, 50.0f, 20.0f, 100.0f, 0.0f, 0.0f, 1.0f, 0, 100);

    try {
      builder.addMove("bad", 50.0f, 50.0f, 300.0f, 300.0f, 10, 20);
      Assert.fail("Should not be able to allow Command with bad operand!");
    } catch (IllegalArgumentException exception) {
      assertEquals("No Drawable with that name was found!", exception.getMessage());
    }

    builder.addMove("testRect", 50.0f, 50.0f, 300.0f, 300.0f, 10, 20);
    builder.addMove("testRect", 300.0f, 300.0f, 400.0f, 20.0f, 21, 41);

    try {
      builder.addMove("testRect", 400.0f, 20.0f, 400.0f, 700.0f, 41, 51);
      Assert.fail("Should not be able to overlap Command endpoints!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The ACommand being built has a time conflict!", exception.getMessage());
    }

    try {
      builder.addMove("testRect", 400.0f, 20.0f, 400.0f, 700.0f, 31, 51);
      Assert.fail("Should not be able to overlap Commands!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The ACommand being built has a time conflict!", exception.getMessage());
    }

    try {
      builder.addMove("testRect", 400.0f, 20.0f, 400.0f, 700.0f, 5, 10);
      Assert.fail("Should not be able to overlap Command endpoints!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The ACommand being built has a time conflict!", exception.getMessage());
    }

    try {
      builder.addMove("testRect", 400.0f, 20.0f, 400.0f, 700.0f, 5, 15);
      Assert.fail("Should not be able to overlap Commands!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The ACommand being built has a time conflict!", exception.getMessage());
    }

    try {
      builder.addMove("testRect", 400.0f, 20.0f, 400.0f, 700.0f, 5, 50);
      Assert.fail("Should not be able to overlap Commands!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The ACommand being built has a time conflict!", exception.getMessage());
    }

    builder.addRectangle("testRect2", 80.0f, 100.0f, 20.0f, 100.0f, 0.0f, 1.0f, 0.0f, 30, 50);

    try {
      builder.addMove("testRect2", 400.0f, 20.0f, 400.0f, 700.0f, -40, 51);
      Assert.fail("Should not be able to create a Command with negative times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Commands cannot have negative start or end times!", exception.getMessage());
    }

    try {
      builder.addMove("testRect2", 400.0f, 20.0f, 400.0f, 700.0f, 40, -51);
      Assert.fail("Should not be able to create a Command with negative times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Commands cannot have negative start or end times!", exception.getMessage());
    }

    try {
      builder.addMove("testRect2", 400.0f, 20.0f, 400.0f, 700.0f, 51, 40);
      Assert.fail("Should not be able to create a Command inconsistent times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A Command must start before it ends!", exception.getMessage());
    }

    try {
      builder.addMove("testRect2", 400.0f, 20.0f, 400.0f, 700.0f, 40, 40);
      Assert.fail("Should not be able to create a Command inconsistent times!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A Command must start before it ends!", exception.getMessage());
    }
  }
}
