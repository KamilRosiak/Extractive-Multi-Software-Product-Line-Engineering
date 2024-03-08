package apoSimple.oneButton;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleImages;

public class ApoSimpleOneButtonLevels {

	public static ApoSimpleOneButtonLevel getLevel(int level) {
		if (level == 1) {
			return ApoSimpleOneButtonLevels.getLevelSlowFirst();
		} else if (level == 2) {
			return ApoSimpleOneButtonLevels.getLevelSlowSecond();
		} else if (level == 3) {
			return ApoSimpleOneButtonLevels.getLevelFirst();
		} else if (level == 4) {
			return ApoSimpleOneButtonLevels.getLevelDouble();
		} else if (level == 5) {
			return ApoSimpleOneButtonLevels.getLevelFence();
		} else if (level == 6) {
			return ApoSimpleOneButtonLevels.getLevelDog();
		} else if (level == 7) {
			return ApoSimpleOneButtonLevels.getLevelLikeDouble();
		} else if (level == 8) {
			return ApoSimpleOneButtonLevels.getLevelChicken();
		} else if (level == 9) {
			return ApoSimpleOneButtonLevels.getLevelPfahl();
		} else if (level == 10) {
			return ApoSimpleOneButtonLevels.getLevelFenceTwo();
		} else if (level == 11) {
			return ApoSimpleOneButtonLevels.getLevelLast();
		} else if (level == 12) {
			return ApoSimpleOneButtonLevels.getLevelSlowThird();
		} else if (level == 13) {
			return ApoSimpleOneButtonLevels.getLevelTunnel();
		}
		return null;
	}
	
	private static ApoSimpleOneButtonLevel getLevelSlowFirst() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.setVecSheep(0.11f);
		
		level.addMessage(0, "Have sheeptastic fun");
		
		level.addMessage(1, "You know \"space\" is your friend!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "Jump and jump and jump");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(0, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelSlowSecond() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.setVecSheep(0.11f);
		
		level.addMessage(0, "There are big big bushes in a little little world");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "Tripple jump!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(220, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(140, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "I see many animals!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 160, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));

		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(220, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 75));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 267, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 270, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 280, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelSlowThird() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.setVecSheep(0.11f);
		
		level.addMessage(0, "Hehe you will die in that level!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 130, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(200, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 60, 100, 30));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 335, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "oh oh!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(245, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "really slow and hard level");
//		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 75, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));

		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(200, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 40));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(400, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 25));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(400, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 15));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelFirst() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "Like the first level but faster!");
		
		level.addMessage(1, "You know \"space\" is your friend!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "Jump and jump and jump");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(0, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelLikeDouble() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "Like the second level!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 375, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "The right timing is the key!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));

		level.addMessage(2, "Not to easy!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));

		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(245, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 75));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 265, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 285, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 305, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelDouble() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "Let us doublejump!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "Way too easy, isn't it!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));

		level.addMessage(2, "I hope it will be harder next level!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 290, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 310, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 330, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelFence() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "In that level the most die more than one time!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(140, 50, 50, 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 50, 50, 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(0, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "You think that is hard?");
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(100, 270, 50, 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(190, 200, 50, 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(280, 270, 50, 50));

		level.addMessage(2, "A nice long jump!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(195, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(275, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(350, 360, 50, 50));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelDog() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "Doggy style level");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 215, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 260, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 350, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "OMG I see dogs");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(false, 320, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(false, 265, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(false, 205, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(false, 120, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(false,  70, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));

		level.addMessage(2, "But I like cats too");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 130, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 198, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));

		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 330, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 395, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelChicken() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "So cute chickens");
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 280, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(false, 145, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(120, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 100));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(false, 250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "Next level will be hard! *hihi*");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 150, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 50));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 300, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	

	private static ApoSimpleOneButtonLevel getLevelPfahl() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "The lion jump level");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 05));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 85));
		
		
		level.addMessage(1, "Roar, roar");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(260, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 00));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 00));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(120, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		
		level.addMessage(2, "");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(180, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 20));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(222, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 30));

		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(330, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 01));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(330, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 78));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelFenceTwo() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "I see a long tunnel!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 20, 50, 20));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 70, 170, 40));
		
		level.addMessage(1, "You think that is hard?");
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 280, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getFence(110, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 60, 140, 30));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 60, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));

		level.addMessage(2, "Two nice long jumps!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 160, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 315, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 347, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelLast() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(0, "Jump fast!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDog(true, 100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getChicken(true, 225, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 380, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));

		level.addMessage(1, "");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl( 35, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(112, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(250, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 70));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(270, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(2, "Yes, it is possible ...");
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(2, 120, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(1, 240, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getBush(0, 360, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		
		return level;
	}
	
	private static ApoSimpleOneButtonLevel getLevelTunnel() {
		ApoSimpleOneButtonLevel level = new ApoSimpleOneButtonLevel();
		
		level.addMessage(2, "Timing is the key!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(100, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 33));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(180, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 33));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(230, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(290, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 33));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(355, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		level.getEntities().add(ApoSimpleOneButtonLevels.getPfahl(405, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0));
		
		level.addMessage(1, "Rabbitheaven!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 290, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 230, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 40));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 210, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 30));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 190, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 30));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 170, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1 - 40));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 120, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		level.getEntities().add(ApoSimpleOneButtonLevels.getRabbit(false, 30, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1));
		
		level.addMessage(0, "Don't step on the ducks!");
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 160, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 30));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 215, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 270, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 40));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 325, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2));
		level.getEntities().add(ApoSimpleOneButtonLevels.getDuck2(true, 380, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2 - 30));
		
		return level;
	}
	
	private static ApoSimpleOneButtonEntity getChicken(boolean bLeft, int x, int y) {
		BufferedImage iDuck = ApoSimpleImages.DUCK_LEFT;
		if (!bLeft) {
			iDuck = ApoSimpleImages.DUCK_RIGHT;
		}
		return new ApoSimpleOneButtonEntity(iDuck, x, y - iDuck.getHeight(), iDuck.getWidth(), iDuck.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getDog(boolean bLeft, int x, int y) {
		BufferedImage iDog = ApoSimpleImages.ONEBUTTON_DOG_LEFT;
		if (!bLeft) {
			iDog = ApoSimpleImages.ONEBUTTON_DOG_RIGHT;
		}
		return new ApoSimpleOneButtonEntity(iDog, x, y - iDog.getHeight(), iDog.getWidth(), iDog.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getRabbit(boolean bLeft, int x, int y) {
		BufferedImage iRabbit = ApoSimpleImages.ONEBUTTON_RABBIT_LEFT;
		if (!bLeft) {
			iRabbit = ApoSimpleImages.ONEBUTTON_RABBIT_RIGHT;
		}
		return new ApoSimpleOneButtonEntity(iRabbit, x, y - iRabbit.getHeight(), iRabbit.getWidth(), iRabbit.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getDuck2(boolean bLeft, int x, int y) {
		BufferedImage iDuck = ApoSimpleImages.ONEBUTTON_DUCK2_LEFT;
		if (!bLeft) {
			iDuck = ApoSimpleImages.ONEBUTTON_DUCK2_RIGHT;
		}
		return new ApoSimpleOneButtonEntity(iDuck, x, y - iDuck.getHeight(), iDuck.getWidth(), iDuck.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getPfahl(int x, int y) {
		BufferedImage iPfahl = ApoSimpleImages.ONEBUTTON_PFAHL;
		return new ApoSimpleOneButtonEntity(iPfahl, x, y - iPfahl.getHeight(), iPfahl.getWidth(), iPfahl.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getBush(int big, int x, int y) {
		BufferedImage iBush = ApoSimpleImages.ONEBUTTON_BUSH_0;
		if (big == 1) {
			iBush = ApoSimpleImages.ONEBUTTON_BUSH_1;
		} else if (big > 1) {
			iBush = ApoSimpleImages.ONEBUTTON_BUSH_2;
		}
		return new ApoSimpleOneButtonEntity(iBush, x, y - iBush.getHeight(), iBush.getWidth(), iBush.getHeight());
	}
	
	private static ApoSimpleOneButtonEntity getFence(int x, int y, int width, int height) {
		BufferedImage iFence = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iFence.createGraphics();
		
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 15, 15), 0, 0, null);
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, ApoSimpleImages.FENCE_BIG.getHeight() - 15, 15, 15), 0, iFence.getHeight() - 15, null);
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(ApoSimpleImages.FENCE_BIG.getWidth() - 15, 0, 15, 15), iFence.getWidth() - 15, 0, null);
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(ApoSimpleImages.FENCE_BIG.getWidth() - 15, ApoSimpleImages.FENCE_BIG.getHeight() - 15, 15, 15), iFence.getWidth() - 15, iFence.getHeight() - 15, null);
		
		int w = width - 30;
		int i = 0;
		int fenceW = 100;
		while (w > 0) {
			if (w < fenceW) {
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(15, 0, w, 15), 15 + i * fenceW, 0, null);
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(15, ApoSimpleImages.FENCE_BIG.getHeight() - 15, w, 15), 15 + i * fenceW, iFence.getHeight() - 15, null);
			} else {
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(15, 0, fenceW, 15), 15 + i * fenceW, 0, null);
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(15, ApoSimpleImages.FENCE_BIG.getHeight() - 15, fenceW, 15), 15 + i * fenceW, iFence.getHeight() - 15, null);				
			}
			w -= fenceW;
			i += 1;
		}
		
		w = height - 30;
		i = 0;
		fenceW = 100;
		while (w > 0) {
			if (w < fenceW) {
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 15, 15, w), 0, 15 + i * fenceW, null);
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(ApoSimpleImages.FENCE_BIG.getWidth() - 15, 15, 15, w), iFence.getWidth() - 15, 15 + i * fenceW, null);
			} else {
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 15, 15, fenceW), 0, 15 + i * fenceW, null);
				g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(ApoSimpleImages.FENCE_BIG.getWidth() - 15, 15, 15, fenceW), iFence.getWidth() - 15, 15 + i * fenceW, null);
			}
			w -= fenceW;
			i += 1;
		}
		
		g.dispose();
		
		return new ApoSimpleOneButtonEntity(iFence, x, y, iFence.getWidth(), iFence.getHeight());
	}
}
