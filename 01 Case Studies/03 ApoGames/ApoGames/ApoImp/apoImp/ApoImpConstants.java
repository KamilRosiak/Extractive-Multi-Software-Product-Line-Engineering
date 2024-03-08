package apoImp;

import java.awt.Color;
import java.awt.Font;

public class ApoImpConstants {

	public static String REGION;
	
	public static Font ORIGINAL_FONT;
	
	public static Color c = new Color(150, 150, 190);
	public static Color c2 = new Color(40, 40, 80);
	
	public final static String USERLEVELS_GETPHP = "http://www.apo-games.de/apoElf/get_level.php";
	public final static String USERLEVELS_SAVEPHP = "http://www.apo-games.de/apoElf/save_level.php";
	
	static {
		ApoImpConstants.ORIGINAL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		ApoImpConstants.REGION = "en";
		try {
			ApoImpConstants.REGION = System.getProperty("user.language");
		} catch (Exception ex) {
			ApoImpConstants.REGION = "en";
		}
		ApoImpConstants.setLanguage(ApoImpConstants.REGION);
	}
	
	public static final String PROGRAM_NAME = "=== ApoElf ===";
	public static final double VERSION = 0.01;
	
	public static boolean FPS = false;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 560;

	public static Font FONT_FPS = ApoImpConstants.ORIGINAL_FONT.deriveFont(10f).deriveFont(Font.PLAIN);
	
	public static Font FONT_STATISTICS = ApoImpConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
	
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, false, false, false, false, true, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, true, false, false, false, false, false, true, true};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, true, true, true, false, false, false, false};

	public static final void setLanguage(final String region) {
		if ((region != null) && (region.equals("de"))) {
		} else {
		}
	}
}
