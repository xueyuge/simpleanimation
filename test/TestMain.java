import cs3500.animator.EasyAnimator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing that the EasyAnimator.main() function accepts input correctly.
 */
public class TestMain {
  /**
   * Ensures that EasyAnimator.main() properly rejects invalid input.
   */
  @Test
  public void testInvalidArgs() {
    try {
      EasyAnimator.main(new String[] {"-test", "badarg"});
      Assert.fail("Should not be able to pass in non-flag first!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Found input when expecting a flag!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-if"});
      Assert.fail("Should not be able to pass a flag and then no input!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A flag has been given no input!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-bad", "input"});
      Assert.fail("Should not be able to pass a flag and then no input!");
    } catch (IllegalArgumentException exception) {
      assertEquals("The argument reader has a bad flag stored!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-out", "-worse"});
      Assert.fail("Should not be able to pass in two flags!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Found a flag when expecting an input!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-o", "spaghetti", "pasta"});
      Assert.fail("Should not be able to pass in two inputs!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Found input when expecting a flag!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", ""});
      Assert.fail("Should not be able to pass in zero-length arguments!");
    } catch (IllegalArgumentException exception) {
      assertEquals("Arguments cannot be of zero length!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "potato"});
      Assert.fail("Should not be able to allow a non-int tick rate!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A bad tick rate was given!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "20.5"});
      Assert.fail("Should not be able to allow a non-int tick rate!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A bad tick rate was given!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "0"});
      Assert.fail("Should not be able to allow a tick rate < 1");
    } catch (IllegalArgumentException exception) {
      assertEquals("The tick rate must be at least 1!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "20"});
      Assert.fail("Should not be able to avoid passing a view type!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A view type must be provided!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "20", "-iv", "text"});
      Assert.fail("Should not be able to avoid passing a filepath!");
    } catch (IllegalArgumentException exception) {
      assertEquals("An input filepath must be provided!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-speed", "20", "-iv", "text", "-if", "potato"});
      Assert.fail("Should not be able to pass bad filepath!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A bad animation filepath was given!", exception.getMessage());
    }

    try {
      EasyAnimator.main(new String[] {"-test", "-iv", "meme", "-if",
          "./src/cs3500/animator/resources/smalldemo.txt"});
      Assert.fail("Should not be able to pass bad view type!");
    } catch (IllegalArgumentException exception) {
      assertEquals("A bad view type was given!", exception.getMessage());
    }
  }
}
