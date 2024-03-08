package apoJump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpStateHelp extends ApoJumpState {

	public static final String BUTTON_BACK = "back";
	
	private BufferedImage iBackground;
	
	public ApoJumpStateHelp(ApoJumpPanel game) {
		super(game);
	}
	
	public void init() {
		this.getGame().setShouldRepaint(false);
		if (this.iBackground == null) {
			this.makeBackground();
		}
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.getGame().renderBackground(g);
		
		g.drawImage(this.getGame().getImages().getImage("images/tutorial.png", false), 0, 0, null);
		
		g.setFont(ApoJumpConstants.FONT_TEXTFIELD);
		g.setColor(Color.BLACK);
		
		g.setFont(ApoJumpConstants.FONT_STATISTICS);
		String s = "Instructions";
		int w = g.getFontMetrics().stringWidth(s);
		int x = ApoJumpConstants.GAME_WIDTH/2 - w/2;
		int y = 10;
		int h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
		g.drawString(s, (int)(x), (int)(y + h/2));

		g.setFont(ApoJumpConstants.FONT_BUTTON);
		g.drawImage(ApoJumpImageContainer.iPlayer.getSubimage(0, 0, ApoJumpImageContainer.iPlayer.getWidth(), ApoJumpImageContainer.iPlayer.getHeight()/2), 20, y + 30, null);
		g.drawImage(ApoJumpImageContainer.iPlayer.getSubimage(0, ApoJumpImageContainer.iPlayer.getHeight()/2, ApoJumpImageContainer.iPlayer.getWidth(), ApoJumpImageContainer.iPlayer.getHeight()/2), ApoJumpConstants.GAME_WIDTH - ApoJumpImageContainer.iPlayer.getWidth() - 30, y + 30, null);
		
		y += 20;
		this.drawCloud(g, null, 80, y, "Your goal? Jump on the clouds to reach the sun!");
		y += 25;
		this.drawCloud(g, null, 80, y, "Arrow Keys left and right or a and d to move the character.");
		y += 20;
		this.drawCloud(g, null, 80, y, "Left mouse button to shoot an arrow.");
		
		g.setFont(ApoJumpConstants.FONT_STATISTICS);
		s = "Clouds";
		w = g.getFontMetrics().stringWidth(s);
		x = ApoJumpConstants.GAME_WIDTH/2 - w/2;
		y += 30;
		h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
		g.drawString(s, (int)(x), (int)(y + h/2));

		g.setFont(ApoJumpConstants.FONT_BUTTON);
		y += 25;
		this.drawCloud(g, null, 80, y, "Clouds can be used for jumping");		
		
		y += 20;
		this.drawCloud(g, ApoJumpImageContainer.iCloud, 80, y, "Normal cloud");
		
		y += 20;
		this.drawCloud(g, ApoJumpImageContainer.iCloudOne, 80, y, "The black cloud will be invisible after jumping on it");

		y += 20;
		this.drawCloud(g, ApoJumpImageContainer.iCloudTwo, 80, y, "The yellow cloud will be invisible after a short time");
		
		g.setFont(ApoJumpConstants.FONT_STATISTICS);
		s = "Goodies";
		w = g.getFontMetrics().stringWidth(s);
		y += 30;
		g.drawString(s, (int)(x), (int)(y + h/2));

		g.setFont(ApoJumpConstants.FONT_BUTTON);	
		
		y += 25;
		this.drawCloud(g, ApoJumpImageContainer.iJumpFeather, 80, y, "Trampoline to jump higher once");
		
		y += ApoJumpImageContainer.iJumpFeather.getHeight();
		this.drawCloud(g, ApoJumpImageContainer.iFeather, 80, y, "Feather to fly inviolable for a short time");

		y += ApoJumpImageContainer.iFeather.getHeight();
		this.drawCloud(g, ApoJumpImageContainer.iWings, 80, y, "Wings to fly inviolable for a longer time");
		
		y += ApoJumpImageContainer.iWings.getHeight();
		this.drawCloud(g, ApoJumpImageContainer.iGoodieArrow, 80, y, "Shoot 4 automatic arrows to the next enemies");

		g.setFont(ApoJumpConstants.FONT_STATISTICS);
		y += ApoJumpImageContainer.iGoodieArrow.getHeight() + 5;
		s = "Enemies";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, (int)(x), (int)(y + h/2));

		g.setFont(ApoJumpConstants.FONT_BUTTON);	
		
		y += 25;
		this.drawCloud(g, null, 80, y, "Avoid the enemies or jump on their head.");
		
		x = 80;
		y += 20;
		g.drawImage(ApoJumpImageContainer.iEnemyOne.getSubimage(0, 0, ApoJumpImageContainer.iEnemyOne.getWidth()/2, ApoJumpImageContainer.iEnemyOne.getHeight()), x, y, null);
		x += ApoJumpImageContainer.iEnemyOne.getWidth()/2 + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyTwo, x, y, null);
		x += ApoJumpImageContainer.iEnemyTwo.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyThree, x, y, null);
		x += ApoJumpImageContainer.iEnemyThree.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyFour, x, y, null);
		x += ApoJumpImageContainer.iEnemyFour.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyFive, x, y, null);
		x += ApoJumpImageContainer.iEnemyFive.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemySix, x, y, null);
		x += ApoJumpImageContainer.iEnemySix.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemySeven, x, y, null);
		x += ApoJumpImageContainer.iEnemySeven.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyEight, x, y, null);
		x += ApoJumpImageContainer.iEnemyEight.getWidth() + 5;
		g.drawImage(ApoJumpImageContainer.iEnemyNine.getSubimage(0, 0, ApoJumpImageContainer.iEnemyNine.getWidth()/2, ApoJumpImageContainer.iEnemyNine.getHeight()), x, y, null);
		
		g.dispose();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoJumpStateHelp.BUTTON_BACK)) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	@Override
	public void think(int delta) {
	}

	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
	}
	
	private void drawCloud(Graphics2D g, BufferedImage iCloud, int x, int y, String s) {
		if (iCloud != null) {
			g.drawImage(iCloud, x, y, null);
		
			if (s != null) {
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(x + iCloud.getWidth() + 5), (int)(y + iCloud.getHeight()/2 + h/2));
			}
		} else if (s != null) {
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, (int)(x), (int)(y + h));			
		}
	}

}
