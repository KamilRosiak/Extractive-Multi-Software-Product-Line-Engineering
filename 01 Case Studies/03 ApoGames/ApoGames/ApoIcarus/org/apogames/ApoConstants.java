package org.apogames;

/**
 * Konstanten
 * @author Dirk Aporius
 *
 */
public class ApoConstants {

	public static boolean BUFFER_STRATEGY = false;
	/** gibt zurück, ob die Anwendung eine Applikation oder ein Applet ist */
	public static boolean B_APPLET = false;
	/** gibt zurück, ob die Anwendung eine Applikation oder ein Applet ist */
	public static boolean B_FPS = false;
	/** gibt zurück, ob die Anwendung online ist */
	public static boolean B_ONLINE = true;

	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static int FPS_RENDER = 60;
	public static int FPS_THINK = 100;

	public final static String HIGHSCORE_GETPHP = "http://www.apo-games.de/apoIcarus/get_highscore.php";
	public final static String HIGHSCORE_SAVEPHP = "http://www.apo-games.de/apoIcarus/save_highscore.php";

}