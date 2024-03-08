package apoSoccer.entityForAI;

import java.awt.Color;

public class ApoSoccerAIJNI {

	public static void loadMultiplexer(String lib) {
		try {
			System.load(lib);
		} catch (Exception e) {
			
		} catch (Error er) {
			
		}
	}
	
	/**
	 * Sets the multiplexer to load AI library
	 * @param path name of the library to load
	 * @param side 0 home, 1 visitor
	 * @return false if loading failed, true otherwise
	 */
	public static native boolean loadAI(boolean bHome, String path);
	
	public static native String getTeamName(boolean bHome);
	
	public static native String getAuthor(boolean bHome);
	
	public static native void think(boolean bHome, ApoSoccerBall ball, ApoSoccerTeam[] ownTeam, ApoSoccerEnemy[] enemies);

	public static native Color getShirtColor(boolean bHome);
	
	public static native Color getTrouserColor(boolean bHome);
	
	public static native String[] getNames(boolean bHome);
	
	public static native String[] getSpeech(boolean bHome);
	
	public static native boolean[] getGender(boolean bHome);
	
	public static native int[] getHair(boolean bHome);

	public static native String getEmblem(boolean bHome);
	
	public static native void setPosition(boolean bHome, ApoSoccerTeamSet[] team);

	public static native String getPlayerManImage(boolean bHome);
	
	public static native String getPlayerFemaleImage(boolean bHome);
	
	
}
