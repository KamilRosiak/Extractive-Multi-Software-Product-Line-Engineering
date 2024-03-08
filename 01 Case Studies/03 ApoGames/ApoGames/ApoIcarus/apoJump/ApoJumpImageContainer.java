package apoJump;

import java.awt.image.BufferedImage;

public class ApoJumpImageContainer {

	public static BufferedImage iJumpFeather;
	public static BufferedImage iFeather;
	public static BufferedImage iWings;
	public static BufferedImage iGoodieArrow;
	public static BufferedImage iPlayer;
	public static BufferedImage iPlayerGood;
	public static BufferedImage iPlayerBest;
	public static BufferedImage iCloud;
	public static BufferedImage iCloudOne;
	public static BufferedImage iCloudTwo;
	public static BufferedImage iButtonBackground;
	public static BufferedImage iAnalysisBackground;
	public static BufferedImage iHighscoreBackground;
	public static BufferedImage iButtonLeft;
	public static BufferedImage iArrow;
	public static BufferedImage iEnemyOne;
	public static BufferedImage iEnemyTwo;
	public static BufferedImage iEnemyThree;
	public static BufferedImage iEnemyFour;
	public static BufferedImage iEnemyFive;
	public static BufferedImage iEnemySix;
	public static BufferedImage iEnemySeven;
	public static BufferedImage iEnemyEight;
	public static BufferedImage iEnemyNine;
	public static BufferedImage iAchievement;
	public static BufferedImage iGameHud;
	
	public static void load() {
		ApoJumpImages images  = new ApoJumpImages();
		if (ApoJumpImageContainer.iJumpFeather == null) {
			ApoJumpImageContainer.iJumpFeather = images.getImage("images/goodie_ok.png", false);
		}
		if (ApoJumpImageContainer.iFeather == null) {
			ApoJumpImageContainer.iFeather = images.getImage("images/goodie_good.png", false);
		}
		if (ApoJumpImageContainer.iWings == null) {
			ApoJumpImageContainer.iWings = images.getImage("images/goodie_best.png", false);
		}
		if (ApoJumpImageContainer.iGoodieArrow == null) {
			ApoJumpImageContainer.iGoodieArrow = images.getImage("images/goodie_arrow.png", false);
		}
		if (ApoJumpImageContainer.iPlayer == null) {
			ApoJumpImageContainer.iPlayer = images.getImage("images/icarus.png", false);
		}
		if (ApoJumpImageContainer.iPlayerGood == null) {
			ApoJumpImageContainer.iPlayerGood = images.getImage("images/icarus_good.png", false);
		}
		if (ApoJumpImageContainer.iPlayerBest == null) {
			ApoJumpImageContainer.iPlayerBest = images.getImage("images/icarus_best.png", false);
		}
		if (ApoJumpImageContainer.iCloud == null) {
			ApoJumpImageContainer.iCloud = images.getImage("images/wall.png", false);
		}
		if (ApoJumpImageContainer.iCloudOne == null) {
			ApoJumpImageContainer.iCloudOne = images.getImage("images/wall_2.png", false);
		}
		if (ApoJumpImageContainer.iCloudTwo == null) {
			ApoJumpImageContainer.iCloudTwo = images.getImage("images/wall_3.png", false);
		}
		if (ApoJumpImageContainer.iButtonBackground == null) {
			ApoJumpImageContainer.iButtonBackground = images.getImage("images/button_background.png", false);
		}
		if (ApoJumpImageContainer.iAnalysisBackground == null) {
			ApoJumpImageContainer.iAnalysisBackground = images.getImage("images/analysis.png", false);
		}
		if (ApoJumpImageContainer.iHighscoreBackground == null) {
			ApoJumpImageContainer.iHighscoreBackground = images.getImage("images/highscore.png", false);
		}
		if (ApoJumpImageContainer.iButtonLeft == null) {
			ApoJumpImageContainer.iButtonLeft = images.getImage("images/button_left.png", false);
		}
		if (ApoJumpImageContainer.iArrow == null) {
			ApoJumpImageContainer.iArrow = images.getImage("images/arrow.png", false);
		}
		if (ApoJumpImageContainer.iEnemyOne == null) {
			ApoJumpImageContainer.iEnemyOne = images.getImage("images/enemy_one.png", false);
		}
		if (ApoJumpImageContainer.iEnemyTwo == null) {
			ApoJumpImageContainer.iEnemyTwo = images.getImage("images/enemy_two.png", false);
		}
		if (ApoJumpImageContainer.iEnemyThree == null) {
			ApoJumpImageContainer.iEnemyThree = images.getImage("images/enemy_three.png", false);
		}
		if (ApoJumpImageContainer.iEnemyFour == null) {
			ApoJumpImageContainer.iEnemyFour = images.getImage("images/enemy_four.png", false);
		}
		if (ApoJumpImageContainer.iEnemyFive == null) {
			ApoJumpImageContainer.iEnemyFive = images.getImage("images/enemy_five.png", false);
		}
		if (ApoJumpImageContainer.iEnemySix == null) {
			ApoJumpImageContainer.iEnemySix = images.getImage("images/enemy_six.png", false);
		}
		if (ApoJumpImageContainer.iEnemySeven == null) {
			ApoJumpImageContainer.iEnemySeven = images.getImage("images/enemy_seven.png", false);
		}
		if (ApoJumpImageContainer.iEnemyEight == null) {
			ApoJumpImageContainer.iEnemyEight = images.getImage("images/enemy_eight.png", false);
		}
		if (ApoJumpImageContainer.iEnemyNine == null) {
			ApoJumpImageContainer.iEnemyNine = images.getImage("images/enemy_nine.png", false);
		}
		if (ApoJumpImageContainer.iAchievement == null) {
			ApoJumpImageContainer.iAchievement = images.getImage("images/achievement.png", false);
		}
		if (ApoJumpImageContainer.iGameHud == null) {
			ApoJumpImageContainer.iGameHud = images.getImage("images/clouds_ingame.png", false);
		}
	}
	
}
