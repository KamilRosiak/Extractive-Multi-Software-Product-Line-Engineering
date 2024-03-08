package org.apogames;

/**
 * Konstanten
 * @author Dirk Aporius
 *
 */
public class ApoConstants {

	/**
	 * gibt zurück, ob die Anwendung eine Applikation oder ein Applet ist
	 */
	public static boolean B_APPLET = false;
	
	/**
	 * gibt zurück, ob die Anwendung eine Applikation oder ein Applet ist
	 */
	public static boolean B_FPS = false;
	
	/**
	 * gibt zurück, ob die Anwendung online ist
	 */
	public static boolean B_ONLINE = true;
	
	public final static String HIGHSCORE_GETPHP = "http://www.apo-games.de/apoSlitherLink/get_highscore.php";
	public final static String HIGHSCORE_SAVEPHP = "http://www.apo-games.de/apoSlitherLink/save_highscore.php";
	
	/**
	 * Konstanten für die Pfeiltasten
	 */
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int STAND = 4;
	
	public static final int NO_THREAD = 0;
	public static final int ONE_THREADS = 1;
	public static final int TWO_THREADS = 2;

}
