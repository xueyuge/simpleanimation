Primary changes from Assignment 5:
- IDrawableProperty and its implementing structures have been removed.
- Visitors were added for both Commands and Drawables.
- Scale dimensions and colors were moved up to ADrawable from its extending classes.

Design:
- Drawables represent basic shapes and images to be displayed.
- Commands attach to Drawables and modify some basic property, such as position, color, or size.
- AnimationModels represent a collection of Drawables and Commands.
- AnimationViews represent a way to display an AnimationModel.

Usage:
	Program arguments [note that all arguments ARE case-sensitive]:
		"-iv <type>": Determines what type (visual, svg, or text) to construct the program view.
		"-if <filepath>": Determines whhat filepath the program should try to read animation data from.
		"-o <output>": [optional] If the view is text-based (text or svg), the name of the file to write to; if set to "out" or left out, the program will use System.out.
		"-speed <tick rate>": [optional] Determines the number of ticks/second the program will run on; 1 tick/second by default.
		"-test": [RESERVED FOR PROGRAM USE] Reserved to allow the main method to throw exceptions instead of displaying an error and exiting.
	Note that all filepaths will be relative to whichever folder EasyAnimator.jar is located in unless an absolute filepath is given.
