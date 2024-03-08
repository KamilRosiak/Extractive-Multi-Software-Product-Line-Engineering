package apoSlitherLink;

import java.awt.Font;

public class ApoSlitherLinkConstants {
	
	public static final double VERSION = 1.0;
	
	public static final String TITLE = "=== ApoSlitherLink === (Version: "+ApoSlitherLinkConstants.VERSION+")";
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 40;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;

	public static final Font FONT_FPS = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
	
	public static final Font FONT_STATISTICS = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public static final Font FONT_TUTORIAL = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, true, true, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false};
	public static final boolean[] BUTTON_LEVELCHOOSER = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false};

	public static final String[] TUTORIAL_SPEECH = new String[] {
		"Hello, in ApoSlitherlink there are only 3 little rules to solve the levels.",
		"The first rule: Lines are drawn on the edges of cells. Just click the edge to draw a line. Click again to erase it.",
		"The second rule: Numbers inside a cell indicate how many edges (out of four) are drawn in the final solution.",
		"If a cell contains no number, you are free to draw as many lines as you need around it, but if a cell has a zero in it,you may not draw any lines.",
		"When you have the right number of lines drawn around a certain cell, it will turn bright.",
		"The third rule: All of the lines must be incorporated into a single non-intersecting loop.",
		"That means NO loose ends, NO figure eights, and NO DICE trying to use two or more closed loops to solve the puzzle.",
		"Sounds easy? Please solve that level now. Left click on an edge to draw a line, click again erase. Right click: mark which segment should NOT be drawn",
		"Ok thats all. Have fun and play",
	};
}
