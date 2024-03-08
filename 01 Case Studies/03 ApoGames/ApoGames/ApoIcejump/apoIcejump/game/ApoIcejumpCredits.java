package apoIcejump.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.entity.ApoIcejumpCreditsBlock;

public class ApoIcejumpCredits extends ApoIcejumpModel {
	
	public static final String MENU = "menu";
	
	private int time;
	
	private ArrayList<ApoIcejumpCreditsBlock> creditsBlock;
	
	public ApoIcejumpCredits(ApoIcejumpPanel game) {
		super(game);
	}
	
	public void start() {
		if (this.creditsBlock == null) {
			this.creditsBlock = new ArrayList<ApoIcejumpCreditsBlock>();
			BufferedImage iBlock = this.getGame().makeAndGetIceblock(ApoIcejumpConstants.ICEBLOCK_WIDTH, ApoIcejumpConstants.ICEBLOCK_HEIGHT);
			String[] strings = new String[] { "A", "P", "O", " ", "I", "C", "E", "J", "U", "M", "P" };
			int x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 20, strings[i]));
			}
			strings = new String[] { "R", "E", "L", "O", "A", "D", "E", "D" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 60, strings[i]));
			}
			strings = new String[] { "B", "Y", " ", "D", "I", "R", "K", " ", "A", "P", "O", "R", "I", "U", "S" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 110, strings[i]));
			}
			
			strings = new String[] { "G", "R", "A", "P", "H", "I", "C", "S", " ", "B", "Y" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 170, strings[i]));
			}
			strings = new String[] { "D", "O", "N", "K", "E", "Y", " ", "K", "O", "N", "G" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 210, strings[i]));
			}
			strings = new String[] { "T", "R", "I", "C", "K", "S", "T", "E", "R", " ", "O", "N", "L", "I", "N", "E" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 250, strings[i]));
			}
			strings = new String[] { "D", "I", "R", "K", " ", "A", "P", "O", "R", "I", "U", "S" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 290, strings[i]));
			}
			
			strings = new String[] { "P", "R", "O", "G", "R", "A", "M", "M", "E", "D", " ", "B", "Y" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 350, strings[i]));
			}
			strings = new String[] { "D", "I", "R", "K", " ", "A", "P", "O", "R", "I", "U", "S" };
			x = ApoIcejumpConstants.GAME_WIDTH/2 - strings.length / 2 * (iBlock.getWidth() + 3);
			for (int i = 0; i < strings.length; i++) {
				this.creditsBlock.add(new ApoIcejumpCreditsBlock(iBlock, x + i * (iBlock.getWidth() + 3), 390, strings[i]));
			}
		}
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
		this.getGame().makeNewParticle();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu(true, true);
		} else if (button == KeyEvent.VK_ENTER) {
			this.getGame().setMenu(true, true);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoIcejumpAnalysis.MENU)) {
			this.getGame().setMenu(true, true);
		} else if (function.equals(ApoIcejumpAnalysis.START)) {
			this.getGame().setGame();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	@Override
	public void think(int delta) {
		this.time -= delta;
		if (this.time <= 0) {
			this.time = (int)(Math.random() * 1000 + 200);
			int y = (int)(Math.random() * ApoIcejumpConstants.GAME_HEIGHT);
			if (Math.random() * 100 > 50) {
				this.getGame().makeFish(true, y);
			} else {
				this.getGame().makeFish(false, y);
			}
		}
		this.getGame().thinkFish(delta);
		this.thinkButtons(delta);
		for (int i = 0; i < this.creditsBlock.size(); i++) {
			this.creditsBlock.get(i).think(delta);
		}
	}
	
	private void thinkButtons(int delta) {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			ApoButton button = this.getGame().getButtons()[i];
			if (button.isBVisible()) {
				button.setY(button.getY() + button.getVecY() * delta);
				if (Math.abs(button.getStartY() - button.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
					button.setVecY(-button.getVecY());
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderWater(g, 0, 0);
		this.getGame().renderFish(g, 0, 0);
		
		this.renderCredits(g, 0, 0);
		
		this.getGame().renderButtons(g);
	}
	
	private void renderCredits(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.creditsBlock.size(); i++) {
			this.creditsBlock.get(i).render(g, changeX, changeY);
		}
	}
}
