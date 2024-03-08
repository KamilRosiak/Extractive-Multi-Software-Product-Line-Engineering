package apoSimple;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ApoSimpleConstants {

	public static final int START_MOVES = 5;
	public static final int ADD_MOVES = 4;
	public static final int MAX_MOVES = 6;

	public static final int ADD_COINS_UPLOAD = 50;
	
	public static final int MIN_DOG_SIZE = 7;
	
	public static final int ADD_COINS = 5;
	public static final int SIZE_ADD_COINS = 16;
	
	public static final int GOODIE_COUNT = 1;
	public static final int MAX_GOODIE_COUNT = 3;
	public static final int ADD_MOVE = 11;
	
	public static Font ORIGINAL_FONT;
	
	public static String REGION;
	
	public static String[] LEVEL_FIRST;
	public static String[] LEVEL_SECOND;
	public static String[] LEVEL_THIRD;
	public static String[] LEVEL_FOURTH;
	public static String[] LEVEL_FIFTH;
	public static String[] LEVEL_SIX;
	public static String[] LEVEL_SEVEN;
	public static String[] LEVEL_EIGHT;
	public static String[] LEVEL_NINE;
	public static String[] LEVEL_TEN;
	public static String[] LEVEL_ELEVEN;
	
	public static String[] OVER_GOODIE;
	public static String[] OVER_COIN_ENDLESS;
	public static String[] OVER_COIN_LEVEL;
	public static String[] OVER_MUSIC;
	public static String[] OVER_EFFECTS;
	public static String[] OVER_NOTICE;
	public static String[] OVER_RELOAD;
	public static String[] OVER_HELP;
	
	public static String[] TUTORIAL_ENDLESS_SPEECH;
	
	public static String[] TUTORIAL_LEVEL_SPEECH;
	
	public static String[] HELP;
	public static String[] ERROR;
	
	public static String HELP_NOTICE;
	
	public static String DRIVE_EXTRAMOVE;
	public static String DRIVE_EXTRACOINS;
	public static String DRIVE_STRONG;
	
	public static final String DRIVE_EXTRAMOVE_ENG = "+1 Extramove";
	public static final String DRIVE_EXTRAMOVE_DE = "+1 Extrazug";
	
	public static final String DRIVE_EXTRACOINS_ENG = "+"+ApoSimpleConstants.ADD_COINS+" Coins";
	public static final String DRIVE_EXTRACOINS_DE = "+"+ApoSimpleConstants.ADD_COINS+" Münzen";
	
	public static final String DRIVE_STRONG_ENG = "We are STRONG!";
	public static final String DRIVE_STRONG_DE = "Wir sind STARK!";
	
	public static String[] LEVEL_FIRST_ENG = new String[] {
		"Your task is to clear the field",
		"Fewer moves => more stars",
		"Press 'r' to restart a level",
	};
	
	public static String[] LEVEL_FIRST_DE = new String[] {
		"Ziel ist es das komplette Feld abzuräumen",
		"Weniger Schritte bedeuten mehr Sterne",
		"Drücke 'r' um das Level neu zu starten",
	};
	
	public static String[] LEVEL_SECOND_ENG = new String[] {
		"1 star = solved the level",
		"2 stars = nice result",
		"3 stars = best or apo result ;)",
		"Press 'n' to load the next level",
		"Press 'p' to load the previous level",
	};
	
	public static String[] LEVEL_SECOND_DE = new String[] {
		"1 Stern bedeutet Level gelöst",
		"2 Sterne ist ein schönes Ergebnis",
		"3 Sterne bekommt ihr für das beste Ergebnis",
		"Drücke 'n' um das nächste Level zu laden",
		"Drücke 'p' um das vorherige Level zu laden",
	};
	
	public static String[] LEVEL_THIRD_ENG = new String[] {
		"You can only start sheep."
	};
	
	public static String[] LEVEL_THIRD_DE = new String[] {
		"Nur Schafe können können angeklickt werden."
	};
	
	public static String[] LEVEL_FOURTH_ENG = new String[] {
		"Sheep are scared of dogs!",
		"They will turn their direction",
		"when they meet a dog. Except there",
		"are more than "+(ApoSimpleConstants.MIN_DOG_SIZE)+" sheep then the dog",
		"is scared and will run away."
	};
	
	public static String[] LEVEL_FOURTH_DE = new String[] {
		"Schafe haben Angst vor Hunden!",
		"Sie drehen sich einfach um, ",
		"wenn sie auf sie treffen.",
		"Außer es sind mehr als "+(ApoSimpleConstants.MIN_DOG_SIZE)+" Schafe in der Herde!",
		"Dann laufen die Hunde weg."
	};
	
	public static String[] LEVEL_FIFTH_ENG = new String[] {
		"Sometimes it is good to be scared",
		"for one time to form",
		"a bigger flock of sheep!"
	};
	
	public static String[] LEVEL_FIFTH_DE = new String[] {
		"Manchmal ist es besser erstmal Angst",
		"zu haben und später mit einer größeren",
		"Schafherde den Hunden Angst zu machen!"
	};
	
	public static String[] LEVEL_SIX_ENG = new String[] {
		"Sometimes you need more",
		"than one step to solve the level."
	};
	
	public static String[] LEVEL_SIX_DE = new String[] {
		"In einigen Levels benötigt ihr mehr als",
		"nur einen Schritt, um das Level zu lösen."
	};
	
	public static String[] LEVEL_SEVEN_ENG = new String[] {
		"Black sheep are a little bit strange",
		"After one move they change",
		"their direction clockwise."
	};
	
	public static String[] LEVEL_SEVEN_DE = new String[] {
		"Schwarze Schafe sind etwas eingenartig.",
		"Nach einem Zug verändern sie ihre",
		"Richtung im Uhrzeigersinn."
	};
	
	public static String[] LEVEL_EIGHT_ENG = new String[] {
		"Fields with double sheep you have",
		"to visit twice to clear them.",
		"Please try it out now."
	};
	
	public static String[] LEVEL_EIGHT_DE = new String[] {
		"Zwei Schafe auf einem Feld bedeuten,",
		"dass die Schafhederde zweimal dorthin",
		"zurückkehren muss, um das Feld zu leeren."
	};
	
	public static String[] LEVEL_NINE_ENG = new String[] {
		"When you reach a bush the flock of sheep",
		"will turn around.",
	};
	
	public static String[] LEVEL_NINE_DE = new String[] {
		"Wenn die Schafherde auf einen Busch",
		"trifft, dann isst sie ihn und dreht um.",
	};

	public static String[] LEVEL_TEN_ENG = new String[] {
		"Some bushes you have to visited multiple",
		"times to delete them.",
	};
	
	public static String[] LEVEL_TEN_DE = new String[] {
		"Einige Büsche müssen mehrfach besucht",
		"werden, um sie aus dem Weg zu räumen.",
	};
	
	public static String[] LEVEL_ELEVEN_ENG = new String[] {
		"Like in the endless mode not visited",
		"objects will fall down after one move.",
		"But no new random sheep will appear."
	};
	
	public static String[] LEVEL_ELEVEN_DE = new String[] {
		"Genau wie im Endlosmodus fallen die nicht",
		"abgeräumten Objekte herunter nach einem Zug.",
		"Aber es kommen keine neuen Zufallsschafe",
		"nachgerückt."
	};
	
	public static final String[] TUTORIAL_ENDLESS_SPEECH_ENG = new String[] {
		"In the endlessmode of ApoSheeptastic you have only one important goal: EAT ALL FLOWERS. Click to start the next tutorial step.",
		"You can start a sheep by clicking on it and it will start running with its direction. If it meets another sheep it will change the direction and the new sheep will add to the flock of sheep.",
		"If the flock of sheep are over a flower piece they will eat the flowers. If you collide with the fence, your flock of sheeps will getaway and you have to start a new sheep. The empty spaces will be filled with new sheep.",
		"You have a limited count of moves. Higher levels means more flower pieces and on a flower piece you can't start a sheep. If your count of your flock of sheep can be divided by "+ApoSimpleConstants.ADD_MOVE+" , you get an extra move.",
		"Please try it out now and start the marked sheep and see how the game works.",
		"Great! There are more then 30 possibilities to start a move. So you have to watch which sheep makes the biggest flock of sheep. This is why you can click on the notice block on the upper right corner. Please press the block now.",
		"Here you can draw and erase lines with different colors. Try it out. When you are ready click the 'back'-button to start the next tutorial step.",
		"After level 3 there will be more colors on the field because the black sheep will arrive! Black sheep are a little bit crazy. After 1 move they will change their direction clockwise.",
		"After level 6 dogs will arrive. Sheep are afraid of dogs, so they will turn their direction backwards when they hit them.",
		"But if there are more then "+ApoSimpleConstants.MIN_DOG_SIZE+" sheep then the dog is scared and will run away. You can't start dogs, only sheep. Keep that in mind. Try it out now and clear all flowers.",
		"Great! Thats all. Can you upload your score after running out of moves. Will you be the next highscore leader?"
	};
	
	public static final String[] TUTORIAL_LEVEL_SPEECH_ENG = new String[] {
		"In the puzzlemode of ApoSheeptastic you have to clear the field in as few as possible moves. Click to start the next tutorial step.",
		"Fewer moves means more stars. How much stars you will get you can see on the right side.",
		"You can restart a level by clicking on the restart button. If you are stuck or need to much moves? Press the restart button.",
		"You can start a sheep simply by clicking on it. Try it out now and solve the level.",
		"Great! Whenever a new sheep is reached movement changes to its direction. Find the sheep to solve that level in one move and try it out.",
		"Often there are many sheep. You have to watch which sheep will clear the field. This is why you can click on the notice block on the upper right corner. Please press the block now.",
		"Here you can draw lines with different colors. Try it out. When you are ready click the 'back'-button to start the next tutorial step.",
		"In some levels there are dogs. Sheep are afraid of dogs, so they will turn their direction backwards when they hit them.",
		"But if there are more then "+ApoSimpleConstants.MIN_DOG_SIZE+" sheep then the dog is scared and will run away. You can't start dogs, only sheep. Keep that in mind. Try it out now and solve the level.",
		"Good! Black sheep are a little bit crazy. After 1 move they will change their direction clockwise. In that level you will need at least 2 moves. Please try it out now.",
		"Great! Thats all. Now have fun with more than 40 original levels and many userlevels. Perhaps you will add another level with the ingame editor?"
	};
	
	public static final String[] HELP_ENG = new String[] {
		"Get all the flowers beds with a flock of sheep to adavance!",
		"If your count of your flock of sheep can be divided by "+ApoSimpleConstants.ADD_MOVE+", you get an extra move.",
		"Black sheep change there direction clockwise after one move!",
		"If your count of your flock of sheep can be divided by "+ (ApoSimpleConstants.SIZE_ADD_COINS + 1) +", you get "+ ApoSimpleConstants.DRIVE_EXTRACOINS +" extra coins!",
		"Some tiles you have to visit twice or thrice to clear it.",
		"",
		"Dogs will scare your sheep away without a herd of at least "+ (ApoSimpleConstants.MIN_DOG_SIZE + 1) +"!",
		"Upload your score and get "+ ApoSimpleConstants.ADD_COINS_UPLOAD +" extra coins!",
		"",
		"Bushes has to be visited twice to crush tem"
	};
	
	public static final String HELP_NOTICE_ENG = "You don't want to see that help or the arrow above the sheep? Switch it off!";
	
	public static final String[] OVER_GOODIE_ENG = new String[] {"Change the direction of a sheep clockwise"};
	public static final String[] OVER_COIN_ENDLESS_ENG = new String[] {"Show the sheep which will build the biggest flock of sheep", "Cost: "};
	public static final String[] OVER_COIN_LEVEL_ENG = new String[] {"Show the sheep which will solve the level", "Cost: "};
	public static final String[] OVER_MUSIC_ENG = new String[] {"Switch the music on/off"};
	public static final String[] OVER_EFFECTS_ENG = new String[] {"Switch the sound effects on/off"};
	public static final String[] OVER_NOTICE_ENG = new String[] {"Open the notice block to draw and think about possible routes."};
	public static final String[] OVER_RELOAD_ENG = new String[] {"Restart the level."};
	public static final String[] OVER_HELP_ENG = new String[] {"Switch the help on/off."};
	
	public static final String[] ERROR_ENG = new String[] {
		"Can't start from a flower tile.",
		"Sheep can't reach the fence.",
		"Can't start an empty field.",
		"Can't start dogs.",
		"Can't start a fence.",
		"Can't start a bush."
	};
	
	public static final String[] TUTORIAL_ENDLESS_SPEECH_DE = new String[] {
		"Im Endlosmodus von ApoSheeptastic gibt es nur ein Ziel: \"Esst alle Blumen\". Drücke eine Maustaste, um mit dem Tutorial fortzufahren.",
		"Um dies zu erreichen, könnt ihr ein Schaf per Mausklick starten und es wird loslaufen. Wenn es ein anderes Schaf trifft, wird es mitgenommen und mit seiner Richtung weitergelaufen.",
		"Wenn die Schafherde über einer BLume ist, wird sie gegessen. Wenn die Schafherde mit dem Zaun kollidiert, haut sie ab und ihr müsst ein neues Schaf starten. Die leeren Felder werden mit neuen Schafen aufgefüllt.",
		"Ihr habt aber nur eine bestimmt Anzahl an Zügen. Wenn keine Züge mehr möglich sind, ist das Spiel vorbei. Wenn eure Schafherde durch "+ApoSimpleConstants.ADD_MOVE+" geteilt werden kann, bekommt ihr einen Extrazug.",
		"Versuch es bitte nun aus und starte das markierte Schad und schau wie das Spiel funktioniert.",
		"Super! Bei mehr als 30 Möglichkeiten einen Zug zu starten, ist eine Hilfe sehr brauchbar. Um besser planen zu können, welches Schaf die größte Herde bilden wird, könnt ihr auf den Notizblock klicken und dort genauer planen. Bitte drückt nun auf den Notizblock.",
		"Hier könnt ihr mit zwei Farben malen, was euer Herz begehrt.  Versucht es nun aus und drückt auf den back-Button, wenn ihr fertig seid.",
		"Ab dem 3 Level kommen schwarze Schafe hinzu. Sie ändern ihre Richtung im Uhrzeigersinn nachdem eine Schafherde entkommen ist.",
		"Ab dem 7 Level kommen Hunde hinzu. Schafe haben Angst vor Hunden, deshalb laufen sie zurück, wenn sie auf sie treffen.",
		"Aber wenn mehr als  "+ApoSimpleConstants.MIN_DOG_SIZE+" Schafe in der Herde sind, dann haben die Hunde Angst und verschwinden und die Schafe können weiterlaufen. Versucht es aus und verspeist alle Blumenfelder.",
		"Super! Das ist alles. Ladet euren Punktestand am Ende hoch und schaut wo ihr im Vergleich mit anderen Spielern landet. Wirst du der neue Highscoreleader?"
	};
	
	public static final String[] TUTORIAL_LEVEL_SPEECH_DE = new String[] {
		"Im Puzzlemodus von ApoSheeptastic habt ihr das Ziel, das Level in so wenig Schritten wie möglich zu lösen. Drücke eine Maustaste, um mit dem Tutorial fortzufahren.",
		"Weniger Schritten bedeuten mehr Sterne. Wieviel Sterne es für wieviel Schritte gibt, seht ihr auf der rechten Seite.",
		"Ihr könnt ein Level mit dem Restart-Button neu starten. Wenn ihr mal nicht weiterkommt, dann drückt ihn einfach!",
		"Ihr könnt ein Schaf einfach per Klick starten. Versucht es nun aus und löst das Level.",
		"Super! Wenn eure Schafherde auf ein neues Schaf trifft, dann übernehmen sie die Richtung des neuen Schafes und sammeln es ein. Also finde das Schaf, welches alle Schafe in so wenig Schritten wie möglich einsammelt.",
		"Sehr häufig gibt es viele Schafe. Um besser planen zu können, welches Schaf die größte Herde bilden wird, könnt ihr auf den Notizblock klicken und dort genauer planen. Bitte drückt nun auf den Notizblock.",
		"Hier könnt ihr mit zwei Farben malen, was euer Herz begehrt.  Versucht es nun aus und drückt auf den back-Button, wenn ihr fertig seid.",
		"In einigen Levels gibt es Hunde. Schafe haben Angst vor Hunden, deshalb laufen sie zurück, wenn sie auf sie treffen.",
		"Aber wenn mehr als  "+ApoSimpleConstants.MIN_DOG_SIZE+" Schafe in der Herde sind, dann haben die Hunde Angst und verschwinden und die Schafe können weiterlaufen. Versucht es aus.",
		"Gut! Auch schwarze Schafe kommen in einigen Levels vor. Sie ändern ihre Richtung im Uhrzeigersinn, nachdem eine Schafherde entkommen ist. Dies macht natürlich erst richtig Sinn, wenn das Level erst in mehreren Schritten lösbar ist. Bitte versucht es aus.",
		"Super! Nun habt Spaß mit mehr als 100 Originallevels und vielen von anderen Spielern erstellten Levels. Versucht auch den Editor mal aus und ladet euer eigenes Level hoch. Nun habt viel Spaß."
	};
	
	public static final String[] HELP_DE = new String[] {
		"Räumt alle Blumen ab, um ins nächste Level zu kommen.",
		"Wenn eure Schafherde durch "+ApoSimpleConstants.ADD_MOVE+" geteilt werden kann, bekommt ihr einen Extrazug.",
		"Schwarze Schafe ändern ihre Richtung im Uhrzeigersinn nach einem beendeten Schritt.",
		"Alle "+ (ApoSimpleConstants.SIZE_ADD_COINS + 1) +" Schafe pro Herde gibt es "+ ApoSimpleConstants.DRIVE_EXTRACOINS +" Extramünzen!",
		"Auf manchen Feldern stehen zwei Schafe, dann müsst ihr zweimal dahin um es komplett abzuräumen.",
		"",
		"Hunde verängsten Schafe, außer die Herde besteht aus mindestens "+ (ApoSimpleConstants.MIN_DOG_SIZE + 1) +" Schafen!",
		"Lade deinen Highscore hoch und bekomme "+ ApoSimpleConstants.ADD_COINS_UPLOAD +" Extramünzen!",
		"",
		"Büsche müssen zweimal besucht werden, um sie zu zertrampeln."
	};
	
	public static final String HELP_NOTICE_DE = "Ihr braucht keine Hilfe und Pfeile über den Schafen? Dann schaltet sie oben rechts aus.";
	
	public static final String[] OVER_GOODIE_DE = new String[] {"Ändert die Richtung eines Schafes eurer Wahl im Uhrzeigersinn."};
	public static final String[] OVER_COIN_ENDLESS_DE = new String[] {"Zeigt das Schaf, welches die größte Schafsherde bildet.", "Kosten: "};
	public static final String[] OVER_COIN_LEVEL_DE = new String[] {"Löst das aktuelle Level.", "Kosten: "};
	public static final String[] OVER_MUSIC_DE = new String[] {"Schaltet die Hintergrundmusik an/aus."};
	public static final String[] OVER_EFFECTS_DE = new String[] {"Schaltet die Soundeffekte an/aus."};
	public static final String[] OVER_NOTICE_DE = new String[] {"Öffnet den Notizblock, um in aller Ruhe nachzudenken und zu zeichnen."};
	public static final String[] OVER_RELOAD_DE = new String[] {"Startet ein Spiel neu."};
	public static final String[] OVER_HELP_DE = new String[] {"Schaltet die Hilfe an/aus."};
	
	public static final String[] ERROR_DE = new String[] {
		"Von einem Blumenfeld darf nicht gestartet werden.",
		"Schaf ist in einer Endlosschleife gefangen.",
		"Es gibt hier kein Schaf zum Starten.",
		"Hunde können nicht gestartet werden.",
		"Zäune können doch nicht laufen ...",
		"Büsche können nicht gestartet werden.",
	};
	
	static {
		try {
			ApoSimpleConstants.ORIGINAL_FONT = Font.createFont(Font.TRUETYPE_FONT, ApoSimpleConstants.class.getResourceAsStream("/font/sheep.ttf") );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ApoSimpleConstants.REGION = "en";
		try {
			ApoSimpleConstants.REGION = System.getProperty("user.language");
		} catch (Exception ex) {
			ApoSimpleConstants.REGION = "en";
		}
		ApoSimpleConstants.setLanguage(ApoSimpleConstants.REGION);
	}
	
	public static boolean CHEAT = false;
	
	public static final String PROGRAM_NAME = "=== Sheeptastic ===";
	public static final double VERSION = 1.17;
	
	public static boolean FPS = false;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static final int ENTITY_WIDTH = 60;
	
	public static final int EMPTY = 0;
	public static final int GREEN = 1;
	public static final int YELLOW = 2;
	public static final int RED = 3;
	public static final int BLUE = 4;
	
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int RIGHT = 4;
	public static final int DOG_UP = 5;
	public static final int DOG_LEFT = 6;
	public static final int DOG_DOWN = 7;
	public static final int DOG_RIGHT = 8;
	public static final int BLACK_UP = 9;
	public static final int BLACK_LEFT = 10;
	public static final int BLACK_DOWN = 11;
	public static final int BLACK_RIGHT = 12;
	public static final int REAL_EMPTY = 13;
	public static final int DOUBLE_UP = 14;
	public static final int DOUBLE_LEFT = 15;
	public static final int DOUBLE_DOWN = 16;
	public static final int DOUBLE_RIGHT = 17;
	public static final int DOUBLE_BLACK_UP = 18;
	public static final int DOUBLE_BLACK_LEFT = 19;
	public static final int DOUBLE_BLACK_DOWN = 20;
	public static final int DOUBLE_BLACK_RIGHT = 21;
	public static final int FENCE = 22;
	public static final int STONE_3 = 23;
	public static final int STONE_2 = 24;
	public static final int STONE_1 = 25;
	public static final int HIDDEN_RABBIT = 26;
	public static final int HIDDEN_DUCK = 27;
	public static final int HIDDEN_DUCK_2 = 28;
	
	public static final int GOODIE_ADD_ALL = 6;
	
	public static Font FONT_FPS = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(10f).deriveFont(Font.PLAIN);
	
	public static Font FONT_STATISTICS = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_SCORE = new boolean[] {false, false, false, false, false, true, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVEL_SCORE = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVEL_RESTART = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, false, false, false, false, false, true, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_TUTORIAL = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_HIGHSCORE = new boolean[] {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, true, true, false, true, true, true, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVELCHOOSER = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_PUZZLEMODE = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_NOTICE = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false};
	public static final boolean[] BUTTON_ONEBUTTON = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_BUBBLE = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,        false, false, false, false, false, false, false, false, false, false,       false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true};
	public static final boolean[] BUTTON_PUZZLE = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,        false, false, false, false, false, false, false, false, false, false,       false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true};

	public static final void setLanguage(final String region) {
		if ((region != null) && (region.equals("de"))) {
			ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH = ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH_DE;
			ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH = ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH_DE;
			ApoSimpleConstants.HELP = ApoSimpleConstants.HELP_DE;
			ApoSimpleConstants.HELP_NOTICE = ApoSimpleConstants.HELP_NOTICE_DE;
			ApoSimpleConstants.OVER_COIN_ENDLESS = ApoSimpleConstants.OVER_COIN_ENDLESS_DE;
			ApoSimpleConstants.OVER_COIN_LEVEL = ApoSimpleConstants.OVER_COIN_LEVEL_DE;
			ApoSimpleConstants.OVER_EFFECTS = ApoSimpleConstants.OVER_EFFECTS_DE;
			ApoSimpleConstants.OVER_GOODIE = ApoSimpleConstants.OVER_GOODIE_DE;
			ApoSimpleConstants.OVER_HELP = ApoSimpleConstants.OVER_HELP_DE;
			ApoSimpleConstants.OVER_MUSIC = ApoSimpleConstants.OVER_MUSIC_DE;
			ApoSimpleConstants.OVER_NOTICE = ApoSimpleConstants.OVER_NOTICE_DE;
			ApoSimpleConstants.OVER_RELOAD = ApoSimpleConstants.OVER_RELOAD_DE;
			ApoSimpleConstants.ERROR = ApoSimpleConstants.ERROR_DE;
			ApoSimpleConstants.LEVEL_FIRST = ApoSimpleConstants.LEVEL_FIRST_DE;
			ApoSimpleConstants.LEVEL_SECOND = ApoSimpleConstants.LEVEL_SECOND_DE;
			ApoSimpleConstants.LEVEL_THIRD = ApoSimpleConstants.LEVEL_THIRD_DE;
			ApoSimpleConstants.LEVEL_FOURTH = ApoSimpleConstants.LEVEL_FOURTH_DE;
			ApoSimpleConstants.LEVEL_FIFTH = ApoSimpleConstants.LEVEL_FIFTH_DE;
			ApoSimpleConstants.LEVEL_SIX = ApoSimpleConstants.LEVEL_SIX_DE;
			ApoSimpleConstants.LEVEL_SEVEN = ApoSimpleConstants.LEVEL_SEVEN_DE;
			ApoSimpleConstants.LEVEL_EIGHT = ApoSimpleConstants.LEVEL_EIGHT_DE;
			ApoSimpleConstants.LEVEL_NINE = ApoSimpleConstants.LEVEL_NINE_DE;
			ApoSimpleConstants.LEVEL_TEN = ApoSimpleConstants.LEVEL_TEN_DE;
			ApoSimpleConstants.LEVEL_ELEVEN = ApoSimpleConstants.LEVEL_ELEVEN_DE;
			ApoSimpleConstants.DRIVE_EXTRACOINS = ApoSimpleConstants.DRIVE_EXTRACOINS_DE;
			ApoSimpleConstants.DRIVE_EXTRAMOVE = ApoSimpleConstants.DRIVE_EXTRAMOVE_DE;
			ApoSimpleConstants.DRIVE_STRONG = ApoSimpleConstants.DRIVE_STRONG_DE;
		} else {
			ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH = ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH_ENG;
			ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH = ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH_ENG;
			ApoSimpleConstants.HELP = ApoSimpleConstants.HELP_ENG;
			ApoSimpleConstants.HELP_NOTICE = ApoSimpleConstants.HELP_NOTICE_ENG;
			ApoSimpleConstants.OVER_COIN_ENDLESS = ApoSimpleConstants.OVER_COIN_ENDLESS_ENG;
			ApoSimpleConstants.OVER_COIN_LEVEL = ApoSimpleConstants.OVER_COIN_LEVEL_ENG;
			ApoSimpleConstants.OVER_EFFECTS = ApoSimpleConstants.OVER_EFFECTS_ENG;
			ApoSimpleConstants.OVER_GOODIE = ApoSimpleConstants.OVER_GOODIE_ENG;
			ApoSimpleConstants.OVER_HELP = ApoSimpleConstants.OVER_HELP_ENG;
			ApoSimpleConstants.OVER_MUSIC = ApoSimpleConstants.OVER_MUSIC_ENG;
			ApoSimpleConstants.OVER_NOTICE = ApoSimpleConstants.OVER_NOTICE_ENG;
			ApoSimpleConstants.OVER_RELOAD = ApoSimpleConstants.OVER_RELOAD_ENG;
			ApoSimpleConstants.ERROR = ApoSimpleConstants.ERROR_ENG;
			ApoSimpleConstants.LEVEL_FIRST = ApoSimpleConstants.LEVEL_FIRST_ENG;
			ApoSimpleConstants.LEVEL_SECOND = ApoSimpleConstants.LEVEL_SECOND_ENG;
			ApoSimpleConstants.LEVEL_THIRD = ApoSimpleConstants.LEVEL_THIRD_ENG;
			ApoSimpleConstants.LEVEL_FOURTH = ApoSimpleConstants.LEVEL_FOURTH_ENG;
			ApoSimpleConstants.LEVEL_FIFTH = ApoSimpleConstants.LEVEL_FIFTH_ENG;
			ApoSimpleConstants.LEVEL_SIX = ApoSimpleConstants.LEVEL_SIX_ENG;
			ApoSimpleConstants.LEVEL_SEVEN = ApoSimpleConstants.LEVEL_SEVEN_ENG;
			ApoSimpleConstants.LEVEL_EIGHT = ApoSimpleConstants.LEVEL_EIGHT_ENG;
			ApoSimpleConstants.LEVEL_NINE = ApoSimpleConstants.LEVEL_NINE_ENG;
			ApoSimpleConstants.LEVEL_TEN = ApoSimpleConstants.LEVEL_TEN_ENG;
			ApoSimpleConstants.LEVEL_ELEVEN = ApoSimpleConstants.LEVEL_ELEVEN_ENG;
			ApoSimpleConstants.DRIVE_EXTRACOINS = ApoSimpleConstants.DRIVE_EXTRACOINS_ENG;
			ApoSimpleConstants.DRIVE_EXTRAMOVE = ApoSimpleConstants.DRIVE_EXTRAMOVE_ENG;
			ApoSimpleConstants.DRIVE_STRONG = ApoSimpleConstants.DRIVE_STRONG_ENG;
		}
	}
	
	public static ArrayList<String> drawSpeech(Graphics2D g, String s, int width) {
		int curHeight = 0;
		int[] maxLength = new int[] {width - 4, width - 4, width - 4, width - 4};
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s);
		int cur = 0;
		int w = g.getFontMetrics().stringWidth(strings.get(cur));
		if (w > maxLength[curHeight]) {
			int curPos = strings.get(cur).indexOf(" ");
			while ((curPos > -1) && (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, curPos)) < maxLength[curHeight])) {
				int nextPos = strings.get(cur).indexOf(" ", curPos + 1);
				if (nextPos != -1) {
					if (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, nextPos)) >= maxLength[curHeight]) {
						String curString = strings.get(cur);
						strings.set(cur, curString.substring(0, curPos));
						cur++;
						if (curHeight == 0) {
							curHeight++;
						}
						strings.add(curString.substring(curPos + 1));
						curPos = strings.get(cur).indexOf(" ");
					} else {
						curPos = nextPos;
					}
				} else {
					String curString = strings.get(cur);
					if (g.getFontMetrics().stringWidth( curString ) > maxLength[curHeight]) {
						strings.set( cur, curString.substring(0, curPos) );
						cur++;
						strings.add( curString.substring(curPos + 1));
					}
					break;
				}
			}
		}
		return strings;
	}
}
