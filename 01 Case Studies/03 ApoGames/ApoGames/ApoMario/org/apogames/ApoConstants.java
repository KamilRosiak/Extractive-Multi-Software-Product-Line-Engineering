package org.apogames;

/**
 * Konstanten
 * @author Dirk Aporius
 *
 */
public class ApoConstants {

	/**
	 * gibt zur�ck, ob die Anwendung eine Applikation oder ein Applet ist
	 */
	public static boolean B_APPLET = false;
	
	public static void setApplet(boolean bApplet) {
		B_APPLET = bApplet;
	}
	
	/**
	 * gibt zur�ck, ob die Anwendung eine Applikation oder ein Applet ist
	 */
	public static boolean B_FPS = false;
	
	/**
	 * Konstanten f�r die Pfeiltasten
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
