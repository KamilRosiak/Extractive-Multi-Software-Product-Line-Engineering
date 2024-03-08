package apoIcejump;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apogames.ApoConstants;

public class ApoIcejumpConstants {

	public static boolean ACCELERATION = false;
	
	private static final String PROP_FPS = "FPS";
	private static final String PROP_AI_LEFT = "aiLeft";
	private static final String PROP_AI_RIGHT = "aiRight";
	
	private static Object props;
	private static final String ICEJUMP_PROPERTIES = "icejump.properties";

	public static boolean FPS = false;
	public static String AI_LEFT = "";
	public static String AI_RIGHT = "";
	
	public static void saveProperties() {
		if (!ApoConstants.B_APPLET) {
			try {
				if (props != null) {
					Properties props = (Properties)(ApoIcejumpConstants.props);
					props.setProperty(ApoIcejumpConstants.PROP_FPS, String.valueOf(ApoIcejumpConstants.FPS));
					props.setProperty(ApoIcejumpConstants.PROP_AI_LEFT, String.valueOf(ApoIcejumpConstants.AI_LEFT));
					props.setProperty(ApoIcejumpConstants.PROP_AI_RIGHT, String.valueOf(ApoIcejumpConstants.AI_RIGHT));
					FileOutputStream out = new FileOutputStream(ApoIcejumpConstants.ICEJUMP_PROPERTIES);
					props.store(out, null);
					out.close();
				}
			} catch (IOException e) {
			} finally {
			}
		}
	}
	
	public static void loadProperties() {
		if (!ApoConstants.B_APPLET) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(ApoIcejumpConstants.ICEJUMP_PROPERTIES));
			} catch (IOException ex) {
				System.out.println("exception: can not load properties");
			}
			ApoIcejumpConstants.AI_LEFT = props.getProperty(ApoIcejumpConstants.PROP_AI_LEFT, "");
			ApoIcejumpConstants.AI_RIGHT = props.getProperty(ApoIcejumpConstants.PROP_AI_RIGHT, "");
			String myFPS = props.getProperty(ApoIcejumpConstants.PROP_FPS, "false");
			try {
				ApoIcejumpConstants.FPS = Boolean.valueOf(myFPS);
			} catch (Exception ex) {
				ApoIcejumpConstants.FPS = false;
			}
			ApoIcejumpConstants.props = props;
		}
	}
	
	public static final double VERSION = 1.01;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static final String GAME_NAME = "=== ApoIcejumpReloaded (" + ApoIcejumpConstants.VERSION + ") ===";
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;

	public static int PARTICLE_TILES = 3;
	
	public static final int GAME_SUDDEN_DEATH_TIME = 60000;
	
	public static final int PLAYER_WIDTH = 30;
	public static final int PLAYER_HEIGHT = 30;
	public static final float PLAYER_DECREASE_Y = 0.0005f;
	public static final float PLAYER_HIT_BLOCK_VEC_Y = -0.4f;
	public static final float PLAYER_HIT_BLOCK_DIFFERENCE_VEC_Y = 0.003f;
	public static final float PLAYER_HIT_PLAYER_VEC_Y = -0.25f;
	public static final float PLAYER_HIT_BIRD_VEC_Y = -0.3f;
	public static final float PLAYER_MAX_VEC_X = 0.16f;
	public static final float PLAYER_MAX_VEC_Y = 0.4f;
	public static final float PLAYER_ACCELERATION_X = 0.0027f;
	public static final float PLAYER_DECREASE_X = 0.0027f;
	public static final float PLAYER_MAX_VEC_X_OVER_ENEMY = 0.13f;

	public static final int PLAYER_FIRE_TILES = 4;
	public static final int PLAYER_FIRE_TIME = 150;

	public static final int ICEBLOCK_WIDTH = 30;
	public static final int ICEBLOCK_HEIGHT = 30;
	public static final int ICEBLOCK_HITS = 3;
	public static final float ICEBLOCK_DECREASE_VEC_X = 0.4f / 1000f;
	public static final float ICEBLOCK_HIT_VEC_X = 6.5f / 1000f;
	
	public static final float CHANGE_MENU_TO_GAME = 0.3f;
	public static final int WATER_HEIGHT = 70;
	
	public static final int FISH_TILES = 8;
	public static final int FISH_TIME = 150;
	
	public static final int BIRD_TILES = 4;
	public static final int BIRD_TIME = 150;
	public static final float BIRD_DECREASE_Y = 0.00035f;
	
	public static final int ABOVE_PIXEL = 5;
	public static final int BUTTON_MAX_CHANGE_Y = 3;
	public static final int WAVE_MAX_CHANGE_Y = 3;
	public static final int ICEBLOCK_MAX_CHANGE_Y = 4;

	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	public static final int AI_HUMAN = 0;
	public static final int AI_JOJO = 1;
	public static final int AI_TRAZZAG = 2;
	public static final int AI_EASY = 3;
	public static final int AI_MIDDLE = 4;
	public static final int AI_HARD = 5;
	
	public static final int GOODIE_WIDTH = 40;
	public static final int GOODIE_HEIGHT = 45;
	public static final int GOODIE_FIRE = 0;
	public static final int GOODIE_ICE_LITTLE = 1;
	public static final int GOODIE_ICE_BIG = 2;
	public static final int GOODIE_SLOWER = 3;
	public static final int GOODIE_HIGHER = 4;
	public static final int GOODIE_FASTER = 5;
	
	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 10);
	public static final Font FONT_STATISTICS = new Font("Dialog", Font.BOLD, 18);
	public static final Font FONT_PLAYER = new Font("Dialog", Font.BOLD, 23);
	public static final Font FONT_TITLE = new Font("Dialog", Font.BOLD, 30);
	public static final Font FONT_COUNTDOWN = new Font("Dialog", Font.BOLD, 90);
	public static final Font FONT_PLAYER_STATISTICS = new Font("Dialog", Font.PLAIN, 16);
	public static final Font FONT_TUTORIAL = new Font("Dialog", Font.PLAIN, 20);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, false, true, true, true, true, true, true, true, false, false, false, true, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_TUTORIAL = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_ANALYSIS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, true ,true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false ,false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_NETWORK = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false ,false, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_SIMULATE = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false ,false, false, false, false, false, false, false, false, true, false, true, true, true, true, true, true, true, false};

	public static final String[] RIDICULE = new String[]{ 
		"You will loose, sweetheart!", 
		"Du kannst nichts und das nichtmal richtig!", 
		"Look behind you, a three-headed monkey!!!",
		"Geh endlich baden und mit deiner Ente spielen!",
		"Meine Freundin spielt besser als du!",
		"Nenne mich MEISTER oder einfach GODLIKE",
		"Sink du Leonardo Di Caprio, du Titanic",
		"Bete mich an und \"versinke\" vor Scham",
		"Ich kann Springen, was kannst du?",
		"Brot kann Schimmeln, was kannst du?",
		"Gegen dich Spielen ist ja total langweilig *gaehn*",
		"Mehr kannst du wirklich nicht? Ich bin enttaeuscht!",
		"Ich mag springen, springen & springen.",
		"HUIII *spring* HUIIIIIII *spring* HUIIIII",
		"Es ist kein Mensch, es ist kein Tier, oh du bist es ...",
		"Was muss ich nochmal einkaufen? Kaese, Wurst ..."
	};
	
	public static final String[] TUTORIAL_SPEECH = new String[] {
		"Hallo zum Tutorial, um weiterzukommen druecke bitte einfach Enter oder die linke Maustaste.",
		"In ApoIcejumpReloaded gibt es nur ein einfaches Ziel. Versenke deinen Gegner. Wenn das Spiel startet, dann bewege dich als linker Spieler mit den Pfeiltasten, ",
		"und als rechter Spieler mit 'a' und 'd'. Auf den Eisbloecken kannst du wieder nach oben springen, aber leider haben Sie nur eine Halbwertzeit von 3 Spruegen,",
		"bevor sie sich aufloesen. Auch die Voegel kann man als Sprungmatten misbrauchen. Die Goodies haben unterschiedliche Wirkungen. Von einem hitzigen Temperament,",
		"ueber neue Eisbloecke, zu schnelleren bzw. auch langsameren Geschwindigkeiten ist alles dabei. Versuche einfach selber zu ergruenden, welches was bewirkt.",
		"Es hat der verloren, der als erster unter Wasser ist. Klingt einfach? Ist EINFACH! Versuche es jetzt doch einfach mal.",
		"Macht doch Spaﬂ =) In diesem Sinne viel Spaﬂ beim Spielen.",
	};
}
