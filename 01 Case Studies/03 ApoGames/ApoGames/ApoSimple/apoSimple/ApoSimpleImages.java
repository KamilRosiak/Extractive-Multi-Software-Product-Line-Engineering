package apoSimple;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

public class ApoSimpleImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public static BufferedImage ORIGINAL;
	public static BufferedImage ORIGINAL_OVER;
	public static BufferedImage ORIGINAL_RED;
	public static BufferedImage ORIGINAL_LEVELUP;
	public static BufferedImage ORIGINAL_GREEN;

	public static BufferedImage ORIGINAL_DRIVE;
	
	public static BufferedImage ORIGINAL_ABOVE;
	public static BufferedImage ORIGINAL_LEFT;
	public static BufferedImage ORIGINAL_RIGHT;
	public static BufferedImage ORIGINAL_DOWN;
	
	public static BufferedImage HIDDEN_DUCK;
	public static BufferedImage HIDDEN_RABBIT;
	public static BufferedImage HIDDEN_DUCK_2;

	public static BufferedImage LITTLE_DOG_LEFT;
	public static BufferedImage LITTLE_DOG_DOWN;
	public static BufferedImage LITTLE_DOG_RIGHT;
	public static BufferedImage LITTLE_DOG_UP;
	public static BufferedImage LITTLE_SHEEP;
	public static BufferedImage LITTLE_FLOWER;
	
	public static BufferedImage ORIGINAL_ABOVE_ARROW;
	public static BufferedImage ORIGINAL_LEFT_ARROW;
	public static BufferedImage ORIGINAL_RIGHT_ARROW;
	public static BufferedImage ORIGINAL_DOWN_ARROW;
	
	public static BufferedImage ORIGINAL_DOG_UP;
	public static BufferedImage ORIGINAL_DOG_LEFT;
	public static BufferedImage ORIGINAL_DOG_RIGHT;
	public static BufferedImage ORIGINAL_DOG_DOWN;

	public static BufferedImage ORIGINAL_BLACK_UP;
	public static BufferedImage ORIGINAL_BLACK_LEFT;
	public static BufferedImage ORIGINAL_BLACK_RIGHT;
	public static BufferedImage ORIGINAL_BLACK_DOWN;
	
	public static BufferedImage FENCE_BIG;
	public static BufferedImage FENCE_FREE;

	public static BufferedImage DUCK_LEFT;
	public static BufferedImage DUCK_RIGHT;
	public static BufferedImage ONEBUTTON_SHEEP_LEFT;
	public static BufferedImage ONEBUTTON_SHEEP_RIGHT;
	public static BufferedImage ONEBUTTON_DOG_LEFT;
	public static BufferedImage ONEBUTTON_DOG_RIGHT;
	public static BufferedImage ONEBUTTON_BUSH_0;
	public static BufferedImage ONEBUTTON_BUSH_1;
	public static BufferedImage ONEBUTTON_BUSH_2;
	public static BufferedImage ONEBUTTON_BLOOD;
	public static BufferedImage ONEBUTTON_RABBIT_LEFT;
	public static BufferedImage ONEBUTTON_RABBIT_RIGHT;
	public static BufferedImage ONEBUTTON_DUCK2_LEFT;
	public static BufferedImage ONEBUTTON_DUCK2_RIGHT;
	public static BufferedImage ONEBUTTON_PFAHL;
	
	public static BufferedImage STONE_3;
	public static BufferedImage STONE_2;
	public static BufferedImage STONE_1;
	
	public static BufferedImage ORIGINAL_SHADOW;
	public static BufferedImage ORIGINAL_SORRY;
	
	public static BufferedImage GAME_POINTS;
	public static BufferedImage GAME_SORTED;
	public static BufferedImage COINS;
	
	public static BufferedImage ANIMATION_RABBIT_1;
	public static BufferedImage ANIMATION_RABBIT_2;
	public static BufferedImage ANIMATION_RABBIT_3;
	
	public static BufferedImage ANIMATION_DUCK_1;
	public static BufferedImage ANIMATION_DUCK_2;
	public static BufferedImage ANIMATION_DUCK_3;
	public static BufferedImage ANIMATION_DUCK_4;
	
	public static BufferedImage ANIMATION_TAIL_WHITE_1;
	public static BufferedImage ANIMATION_TAIL_WHITE_2;
	
	public static BufferedImage ANIMATION_TAIL_BLACK_1;
	public static BufferedImage ANIMATION_TAIL_BLACK_2;
	
	public static BufferedImage ANIMATION_BLINK_WHITE_1;
	
	public static BufferedImage ANIMATION_BLINK_BLACK_1;

	public static BufferedImage ANIMATION_LEFT_BLINK_WHITE_1;
	
	public static BufferedImage ANIMATION_LEFT_BLINK_BLACK_1;

	public static BufferedImage ANIMATION_RIGHT_BLINK_WHITE_1;
	
	public static BufferedImage ANIMATION_RIGHT_BLINK_BLACK_1;
	
	public static BufferedImage ANIMATION_YAWN_WHITE_1;
	public static BufferedImage ANIMATION_YAWN_WHITE_2;
	public static BufferedImage ANIMATION_YAWN_WHITE_3;
	public static BufferedImage ANIMATION_YAWN_WHITE_4;
	public static BufferedImage ANIMATION_YAWN_WHITE_5;
	public static BufferedImage ANIMATION_YAWN_WHITE_6;
	
	public static BufferedImage ANIMATION_YAWN_BLACK_1;
	public static BufferedImage ANIMATION_YAWN_BLACK_2;
	public static BufferedImage ANIMATION_YAWN_BLACK_3;
	public static BufferedImage ANIMATION_YAWN_BLACK_4;
	public static BufferedImage ANIMATION_YAWN_BLACK_5;
	public static BufferedImage ANIMATION_YAWN_BLACK_6;
	
	public static BufferedImage ANIMATION_LAUGH_WHITE_1;
	public static BufferedImage ANIMATION_LAUGH_WHITE_2;
	public static BufferedImage ANIMATION_LAUGH_WHITE_3;
	public static BufferedImage ANIMATION_LAUGH_WHITE_4;
	public static BufferedImage ANIMATION_LAUGH_WHITE_5;
	public static BufferedImage ANIMATION_LAUGH_WHITE_6;
	public static BufferedImage ANIMATION_LAUGH_WHITE_7;
	
	public static BufferedImage ANIMATION_LAUGH_BLACK_1;
	public static BufferedImage ANIMATION_LAUGH_BLACK_2;
	public static BufferedImage ANIMATION_LAUGH_BLACK_3;
	public static BufferedImage ANIMATION_LAUGH_BLACK_4;
	public static BufferedImage ANIMATION_LAUGH_BLACK_5;
	public static BufferedImage ANIMATION_LAUGH_BLACK_6;
	public static BufferedImage ANIMATION_LAUGH_BLACK_7;
	
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_1;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_2;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_3;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_4;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_5;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_6;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_WHITE_7;
	
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_1;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_2;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_3;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_4;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_5;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_6;
	public static BufferedImage ANIMATION_LAUGH_RIGHT_BLACK_7;
	
	public static BufferedImage ANIMATION_LOOK_BLACK_1;
	public static BufferedImage ANIMATION_LOOK_BLACK_2;
	public static BufferedImage ANIMATION_LOOK_BLACK_3;

	public static BufferedImage ANIMATION_LOOK_WHITE_1;
	public static BufferedImage ANIMATION_LOOK_WHITE_2;
	public static BufferedImage ANIMATION_LOOK_WHITE_3;

	public static BufferedImage ANIMATION_SLEEP_WHITE_1;
	public static BufferedImage ANIMATION_SLEEP_WHITE_2;
	public static BufferedImage ANIMATION_SLEEP_WHITE_3;
	public static BufferedImage ANIMATION_SLEEP_WHITE_4;
	public static BufferedImage ANIMATION_SLEEP_WHITE_5;
	public static BufferedImage ANIMATION_SLEEP_WHITE_6;
	public static BufferedImage ANIMATION_SLEEP_WHITE_7;
	public static BufferedImage ANIMATION_SLEEP_WHITE_8;
	public static BufferedImage ANIMATION_SLEEP_WHITE_9;
	public static BufferedImage ANIMATION_SLEEP_WHITE_10;
	public static BufferedImage ANIMATION_SLEEP_WHITE_11;
	
	public static BufferedImage ANIMATION_SLEEP_BLACK_1;
	public static BufferedImage ANIMATION_SLEEP_BLACK_2;
	public static BufferedImage ANIMATION_SLEEP_BLACK_3;
	public static BufferedImage ANIMATION_SLEEP_BLACK_4;
	public static BufferedImage ANIMATION_SLEEP_BLACK_5;
	public static BufferedImage ANIMATION_SLEEP_BLACK_6;
	public static BufferedImage ANIMATION_SLEEP_BLACK_7;
	public static BufferedImage ANIMATION_SLEEP_BLACK_8;
	public static BufferedImage ANIMATION_SLEEP_BLACK_9;
	public static BufferedImage ANIMATION_SLEEP_BLACK_10;
	public static BufferedImage ANIMATION_SLEEP_BLACK_11;
	
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_1;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_2;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_3;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_4;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_5;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_6;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_7;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_8;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_9;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_10;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_11;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_12;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_13;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_14;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_15;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_16;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_17;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_18;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_19;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_20;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_21;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_22;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_23;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_24;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_25;
	public static BufferedImage ANIMATION_SUNGLASSES_WHITE_26;
	
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_1;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_2;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_3;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_4;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_5;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_6;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_7;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_8;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_9;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_10;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_11;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_12;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_13;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_14;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_15;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_16;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_17;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_18;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_19;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_20;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_21;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_22;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_23;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_24;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_25;
	public static BufferedImage ANIMATION_SUNGLASSES_BLACK_26;
	
	
	static {
		
		ApoSimpleImages.ORIGINAL = ApoSimpleImages.getInstance().getImage("images/empty.png", false);
		ApoSimpleImages.ORIGINAL_LEVELUP = ApoSimpleImages.getInstance().getImage("images/levelup.png", false);
		ApoSimpleImages.ORIGINAL_OVER = ApoSimpleImages.getOverImage(Color.YELLOW);
		ApoSimpleImages.ORIGINAL_RED = ApoSimpleImages.getOverImage(Color.RED);
		
		ApoSimpleImages.ORIGINAL_GREEN = ApoSimpleImages.getInstance().getImage("images/simple_color.png", false);
		
		ApoSimpleImages.ORIGINAL_ABOVE = ApoSimpleImages.getInstance().getImage("images/simple_up.png", false);
		ApoSimpleImages.ORIGINAL_LEFT = ApoSimpleImages.getInstance().getImage("images/simple_left.png", false);
		ApoSimpleImages.ORIGINAL_RIGHT = ApoSimpleImages.getInstance().getImage("images/simple_right.png", false);
		ApoSimpleImages.ORIGINAL_DOWN = ApoSimpleImages.getInstance().getImage("images/simple_down.png", false);

		ApoSimpleImages.HIDDEN_DUCK = ApoSimpleImages.getInstance().getImage("images/hidden_duck.png", false);
		ApoSimpleImages.HIDDEN_RABBIT = ApoSimpleImages.getInstance().getImage("images/hidden_rabbit.png", false);
		ApoSimpleImages.HIDDEN_DUCK_2 = ApoSimpleImages.getInstance().getImage("images/hidden_duck2.png", false);
		
		ApoSimpleImages.ORIGINAL_ABOVE_ARROW = ApoSimpleImages.getInstance().getImage("images/simple_up_arrow.png", false);
		ApoSimpleImages.ORIGINAL_LEFT_ARROW = ApoSimpleImages.getInstance().getImage("images/simple_left_arrow.png", false);
		ApoSimpleImages.ORIGINAL_RIGHT_ARROW = ApoSimpleImages.getInstance().getImage("images/simple_right_arrow.png", false);
		ApoSimpleImages.ORIGINAL_DOWN_ARROW = ApoSimpleImages.getInstance().getImage("images/simple_down_arrow.png", false);

		ApoSimpleImages.ORIGINAL_DOG_UP = ApoSimpleImages.getInstance().getImage("images/dog_up.png", false);
		ApoSimpleImages.ORIGINAL_DOG_LEFT = ApoSimpleImages.getInstance().getImage("images/dog_left.png", false);
		ApoSimpleImages.ORIGINAL_DOG_RIGHT = ApoSimpleImages.getInstance().getImage("images/dog_right.png", false);
		ApoSimpleImages.ORIGINAL_DOG_DOWN = ApoSimpleImages.getInstance().getImage("images/dog_down.png", false);

		ApoSimpleImages.ORIGINAL_BLACK_UP = ApoSimpleImages.getInstance().getImage("images/sheep_up_black.png", false);
		ApoSimpleImages.ORIGINAL_BLACK_LEFT = ApoSimpleImages.getInstance().getImage("images/sheep_left_black.png", false);
		ApoSimpleImages.ORIGINAL_BLACK_RIGHT = ApoSimpleImages.getInstance().getImage("images/sheep_right_black.png", false);
		ApoSimpleImages.ORIGINAL_BLACK_DOWN = ApoSimpleImages.getInstance().getImage("images/sheep_down_black.png", false);
		
		ApoSimpleImages.ORIGINAL_SHADOW = ApoSimpleImages.getInstance().getImage("images/shadow.png", false);
		ApoSimpleImages.ORIGINAL_SORRY = ApoSimpleImages.getInstance().getImage("images/sheep_sorry.png", false);

		ApoSimpleImages.LITTLE_DOG_DOWN = ApoSimpleImages.getInstance().getImage("images/dog_down_little.png", false);
		ApoSimpleImages.LITTLE_DOG_UP = ApoSimpleImages.getInstance().getImage("images/dog_up_little.png", false);
		ApoSimpleImages.LITTLE_DOG_LEFT = ApoSimpleImages.getInstance().getImage("images/dog_left_little.png", false);
		ApoSimpleImages.LITTLE_DOG_RIGHT = ApoSimpleImages.getInstance().getImage("images/dog_right_little.png", false);
		ApoSimpleImages.LITTLE_SHEEP = ApoSimpleImages.getInstance().getImage("images/simple_down_little.png", false);
		ApoSimpleImages.LITTLE_FLOWER = ApoSimpleImages.getInstance().getImage("images/bubble_flower.png", false);
		
		ApoSimpleImages.FENCE_BIG = ApoSimpleImages.getInstance().getImage("images/fence_big.png", false);
		ApoSimpleImages.FENCE_FREE = ApoSimpleImages.getFenceImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 65, 65, 65));		

		ApoSimpleImages.DUCK_LEFT = ApoSimpleImages.getInstance().getImage("images/onebutton_duck.png", false);
		ApoSimpleImages.DUCK_RIGHT = ApoSimpleImages.getInstance().getImage("images/onebutton_duck_right.png", false);
		ApoSimpleImages.ONEBUTTON_DOG_LEFT = ApoSimpleImages.getInstance().getImage("images/dog_left_little.png", false);
		ApoSimpleImages.ONEBUTTON_DOG_RIGHT = ApoSimpleImages.getInstance().getImage("images/dog_right_little.png", false);
		ApoSimpleImages.ONEBUTTON_BUSH_0 = ApoSimpleImages.getInstance().getImage("images/bush_little.png", false);
		ApoSimpleImages.ONEBUTTON_BUSH_1 = ApoSimpleImages.getInstance().getImage("images/bush_big.png", false);
		ApoSimpleImages.ONEBUTTON_BUSH_2 = ApoSimpleImages.getInstance().getImage("images/bush_biggest.png", false);
		ApoSimpleImages.ONEBUTTON_SHEEP_LEFT = ApoSimpleImages.getInstance().getImage("images/simple_left_little.png", false);
		ApoSimpleImages.ONEBUTTON_SHEEP_RIGHT = ApoSimpleImages.getInstance().getImage("images/simple_right_little.png", false);
		ApoSimpleImages.ONEBUTTON_BLOOD = ApoSimpleImages.getInstance().getImage("images/blood.png", false);
		ApoSimpleImages.ONEBUTTON_RABBIT_LEFT = ApoSimpleImages.getInstance().getImage("images/onebutton_rabbit_left.png", false);
		ApoSimpleImages.ONEBUTTON_RABBIT_RIGHT = ApoSimpleImages.getInstance().getImage("images/onebutton_rabbit_right.png", false);
		ApoSimpleImages.ONEBUTTON_DUCK2_LEFT = ApoSimpleImages.getInstance().getImage("images/onebutton_duck2_left.png", false);
		ApoSimpleImages.ONEBUTTON_DUCK2_RIGHT = ApoSimpleImages.getInstance().getImage("images/onebutton_duck2_right.png", false);
		ApoSimpleImages.ONEBUTTON_PFAHL = ApoSimpleImages.getInstance().getImage("images/onebutton_pfahl.png", false);
		
		ApoSimpleImages.STONE_3 = ApoSimpleImages.getInstance().getImage("images/stone_3.png", false);
		ApoSimpleImages.STONE_2 = ApoSimpleImages.getInstance().getImage("images/stone_2.png", false);
		ApoSimpleImages.STONE_1 = ApoSimpleImages.getInstance().getImage("images/stone_1.png", false);

		ApoSimpleImages.GAME_POINTS = ApoSimpleImages.getInstance().getImage("images/game_points.png", false);
		ApoSimpleImages.GAME_SORTED = ApoSimpleImages.getInstance().getImage("images/game_sorted.png", false);
		ApoSimpleImages.COINS = ApoSimpleImages.getInstance().getImage("images/coin.png", false);

		ApoSimpleImages.ANIMATION_SLEEP_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf01.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf01.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf02.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf02.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf03.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf03.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf04.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_4 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf04.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_5 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf05.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_5 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf05.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_6 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf06.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_6 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf06.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_7 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf07.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_7 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf07.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_8 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf08.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_8 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf08.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_9 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf09.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_9 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf09.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_10 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf10.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_10 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf10.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_BLACK_11 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_black_schlaf11.png", false);
		ApoSimpleImages.ANIMATION_SLEEP_WHITE_11 = ApoSimpleImages.getInstance().getImage("images/animationen/sleep/right_white_schlaf11.png", false);

		ApoSimpleImages.ANIMATION_BLINK_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/down_black_blinzeln01.png", false);
		ApoSimpleImages.ANIMATION_BLINK_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/down_white_blinzeln01.png", false);
		ApoSimpleImages.ANIMATION_LEFT_BLINK_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/left_black_blinzeln01.png", false);
		ApoSimpleImages.ANIMATION_LEFT_BLINK_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/left_white_blinzeln01.png", false);
		ApoSimpleImages.ANIMATION_RIGHT_BLINK_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/right_black_blinzeln01.png", false);
		ApoSimpleImages.ANIMATION_RIGHT_BLINK_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/blink/right_white_blinzeln01.png", false);

		
		ApoSimpleImages.ANIMATION_YAWN_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen01.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen01.png", false);
		ApoSimpleImages.ANIMATION_YAWN_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen02.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen02.png", false);
		ApoSimpleImages.ANIMATION_YAWN_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen03.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen03.png", false);
		ApoSimpleImages.ANIMATION_YAWN_BLACK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen04.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_4 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen04.png", false);
		ApoSimpleImages.ANIMATION_YAWN_BLACK_5 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen05.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_5 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen05.png", false);
		ApoSimpleImages.ANIMATION_YAWN_BLACK_6 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_black_gaehnen06.png", false);
		ApoSimpleImages.ANIMATION_YAWN_WHITE_6 = ApoSimpleImages.getInstance().getImage("images/animationen/yawn/down_white_gaehnen06.png", false);

		ApoSimpleImages.ANIMATION_TAIL_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/tail/up_black_schwanz01.png", false);
		ApoSimpleImages.ANIMATION_TAIL_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/tail/up_white_schwanz01.png", false);
		ApoSimpleImages.ANIMATION_TAIL_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/tail/up_black_schwanz03.png", false);
		ApoSimpleImages.ANIMATION_TAIL_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/tail/up_white_schwanz03.png", false);

		ApoSimpleImages.ANIMATION_LAUGH_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen01.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen01.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen02.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen02.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen02_blinzeln.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen02_blinzeln.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen03.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_4 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen03.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_5 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen04.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_5 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen04.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_6 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen08.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_6 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen08.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_BLACK_7 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_black_grinsen09.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_WHITE_7 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/left_white_grinsen09.png", false);
	
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen01.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen01.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen02.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen02.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen02_blinzeln.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen02_blinzeln.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen03.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_4 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen03.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_5 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen04.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_5 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen04.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_6 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen08.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_6 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen08.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_7 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_black_grinsen09.png", false);
		ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_7 = ApoSimpleImages.getInstance().getImage("images/animationen/laugh/right_white_grinsen09.png", false);
		
		ApoSimpleImages.ANIMATION_LOOK_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_black_2.png", false);
		ApoSimpleImages.ANIMATION_LOOK_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_white_2.png", false);
		ApoSimpleImages.ANIMATION_LOOK_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_black_1.png", false);
		ApoSimpleImages.ANIMATION_LOOK_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_white_1.png", false);
		ApoSimpleImages.ANIMATION_LOOK_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_black_3.png", false);
		ApoSimpleImages.ANIMATION_LOOK_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/lookaround/down_look_white_3.png", false);
		
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille01.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_1 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille01.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille02.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_2 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille02.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille03.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_3 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille03.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille04.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_4 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille04.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_5 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille05.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_5 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille05.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_6 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille06.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_6 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille06.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_7 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille07.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_7 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille07.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_8 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille08.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_8 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille08.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_9 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille09.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_9 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille09.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_10 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille10.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_10 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille10.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_11 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille11.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_11 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille11.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_12 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille12.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_12 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille12.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_13 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille13.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_13 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille13.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_14 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille14.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_14 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille14.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_15 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille15.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_15 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille15.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_16 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille16.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_16 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille16.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_17 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille17.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_17 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille17.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_18 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille18.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_18 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille18.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_19 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille19.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_19 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille19.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_20 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille20.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_20 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille20.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_21 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille21.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_21 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille21.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_22 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille22.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_22 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille22.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_23 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille23.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_23 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille23.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_24 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille24.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_24 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille24.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_25 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille25.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_25 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille25.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_26 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_black_sonnenbrille26.png", false);
		ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_26 = ApoSimpleImages.getInstance().getImage("images/animationen/sunglasses/down_white_sonnenbrille26.png", false);
	
		ApoSimpleImages.ANIMATION_RABBIT_1 = ApoSimpleImages.getInstance().getImage("images/animationen/rabbit/hidden_rabbit_blinzelnd01.png", false);
		ApoSimpleImages.ANIMATION_RABBIT_2 = ApoSimpleImages.getInstance().getImage("images/animationen/rabbit/hidden_rabbit_blinzelnd02.png", false);
		ApoSimpleImages.ANIMATION_RABBIT_3 = ApoSimpleImages.getInstance().getImage("images/animationen/rabbit/hidden_rabbit_blinzelnd03.png", false);	
		
		ApoSimpleImages.ANIMATION_DUCK_1 = ApoSimpleImages.getInstance().getImage("images/animationen/duck/hidden_duck_flatternd01.png", false);
		ApoSimpleImages.ANIMATION_DUCK_2 = ApoSimpleImages.getInstance().getImage("images/animationen/duck/hidden_duck_flatternd02.png", false);
		ApoSimpleImages.ANIMATION_DUCK_3 = ApoSimpleImages.getInstance().getImage("images/animationen/duck/hidden_duck_flatternd03.png", false);
		ApoSimpleImages.ANIMATION_DUCK_4 = ApoSimpleImages.getInstance().getImage("images/animationen/duck/hidden_duck_flatternd04.png", false);
	}
	
	public final static BufferedImage getFenceImage(BufferedImage newImage) {
		BufferedImage image;
		if (newImage != null) {
			image = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		} else {
			image = new BufferedImage(65, 65, BufferedImage.TYPE_INT_RGB);
		}
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(133, 209, 2));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		if (newImage != null) {
			g.drawImage(newImage, 0, 0, null);
		}
		g.dispose();
		
		return image;
	}
	
	private final static BufferedImage getOverImage(Color color) {
		BufferedImage image = new BufferedImage(ApoSimpleConstants.ENTITY_WIDTH, ApoSimpleConstants.ENTITY_WIDTH, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(5));
		g.setColor(color);
		g.drawOval(2, 2, ApoSimpleConstants.ENTITY_WIDTH - 5, ApoSimpleConstants.ENTITY_WIDTH - 5);
		g.dispose();
		
		return image;
	}
	
	private static ApoSimpleImages INSTANCE;
	
	public static ApoSimpleImages getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ApoSimpleImages();
		}
		return INSTANCE;
	}
	
	public ApoSimpleImages() {
		this.image = new ApoImageFromValue();
	}
	
	/**
	 * Methode um ein Bild aus der Jar oder vom FIlesystem zu laden
	 * @param path : Pfad zum Bild
	 * @param bFile : TRUE, Bild von der Festplatte laden, FALSE aus der JAR
	 * @return hoffentlich das Bild
	 */
	public BufferedImage getImage(String path, boolean bFile) {
		return this.image.getImageFromPath(path, bFile);
	}
	
	/**
	 * gibt das Logo für das Spiel zurück
	 * @param width : Breite des Logos
	 * @param height : Höhe des Logos
	 * @param iPlayer : Bild des Spielers
	 * @return Logo für das Spiel
	 */
	public BufferedImage getLogo(BufferedImage iPlayer) {
		BufferedImage iLogo = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iPlayer.getWidth(), iPlayer.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g	= (Graphics2D)iLogo.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iPlayer, 0, 0, null);
		
		g.dispose();
		
		return iLogo;
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal) {
		return this.getImageMouseOver(iOriginal, 0x00000000, new Color(255, 0, 0, 230), 2);
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal, int width) {
		return this.getImageMouseOver(iOriginal, 0x00000000, new Color(255, 0, 0, 230), width);
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal, int searchColor, Color drawColor, int w) {
		int width = iOriginal.getWidth();
		int height = iOriginal.getHeight();
		BufferedImage iSelect = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iSelect.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iOriginal, 0, 0, null);
		
		g.setColor(drawColor);
		int color = searchColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x + 1 < iOriginal.getWidth()) {
					if ((iOriginal.getRGB(x + 1, y) != color) &&
						(iOriginal.getRGB(x, y) == color)) {
						g.fillRect(x - w/2, y, w, 1);
					}
				}
				if ((x - 1 >= 0)) {
					if ((iOriginal.getRGB(x, y) == color) &&
						(iOriginal.getRGB(x - 1, y) != color)) {
						g.fillRect(x, y, w, 1);
					}
				} 
				if ((y - 1 >= 0)) {
					if ((iOriginal.getRGB(x, y) == color) &&
						(iOriginal.getRGB(x, y - 1) != color)) {
						g.fillRect(x, y, 1, w);
					}
				}
				if (y + 1 < height) {
					if ((iOriginal.getRGB(x, y + 1) != color) &&
						(iOriginal.getRGB(x, y) == color)) {
						g.fillRect(x, y - w/2, 1, w);
					}
				}
			}
		}
				
		g.dispose();
		
		return iSelect;
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, int round) {
		return this.image.getButtonImage(width, height, text, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, new Color(254, 254, 0, 140), new Color(254, 0, 0, 140), font, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color mouseOver, Color mousePressed, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, mouseOver, mousePressed, font, round);
	}
	
	public BufferedImage getButtonImageSimple(int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed, boolean bLeft, boolean bRight, Font writeFont, int round) {
		BufferedImage iButton = this.getButtonImage(width, height, text, background, font, border, over, pressed, writeFont, round);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.BLACK);
		Stroke stroke = g.getStroke();
		Shape shape = g.getClip();
		
		g.setStroke(new BasicStroke(7));
		for (int i = 0; i < 3; i++) {
			int startX = 8 + width * i / 3;
			int startY = 8;
			int myHeight = height - 2 * startY;
			if (bLeft) {
				g.setClip(new Rectangle2D.Float(startX, startY, myHeight/2, myHeight));
				g.drawOval(startX + 3, startY + 3, myHeight - 7, myHeight - 7);
			}
			if (bRight) {
				g.setClip(new Rectangle2D.Float(width * (i + 1) / 3 - startY - myHeight/2, startY, myHeight/2, myHeight));
				g.drawOval(width * (i + 1) / 3 - startY - myHeight, startY + 3, myHeight - 7, myHeight - 7);
			}
		}
		g.setStroke(stroke);
		g.setClip(shape);
		
		g.dispose();
		return iButton;
	}

	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, String text, Color textColor, Color mousePressed, Color mouseReleased) {
		return this.getButtonWithImage(width, height, iPic, text, textColor, mousePressed, mouseReleased, new Font("Dialog", Font.BOLD, 13));
	}
	
	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, String text, Color textColor, Color mousePressed, Color mouseReleased, Font font) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int oneWidth = width/3;
		int widthPic = 0;
		if (iPic != null) {
			widthPic = iPic.getWidth();
		}
		g.setFont(font);
		int fontHeight = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			if (iPic != null) {
				g.drawImage(iPic, oneWidth * i + oneWidth/2 - widthPic/2, height/2 - iPic.getHeight()/2, null);
			}
			
			int w = g.getFontMetrics().stringWidth(text);
			if (i > 0) {
				if (i == 1) {
					g.setColor(mousePressed);
				} else {
					g.setColor(mouseReleased);
				}
				g.fillRoundRect(oneWidth * i + oneWidth/2 - w/2 - 2, height/2 - fontHeight/2, w + 4, fontHeight - 10, 10, 10);
			}
			g.setColor(textColor);
			g.drawString(text, i * oneWidth + oneWidth/2 - w/2, height/2 + 5);
		}
		
		g.dispose();
		return iButton;
	}
	
}
