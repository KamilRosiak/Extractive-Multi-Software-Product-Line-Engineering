package apoJump.game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;
import apoJump.entity.ApoJumpPlatformNormal;
import apoJump.entity.ApoJumpPlayer;

public class ApoJumpStateMenu extends ApoJumpState {

	public static final String BUTTON_QUIT = "quit";
	public static final String BUTTON_GAME = "game";
	public static final String BUTTON_ACHIEVEMENTS = "achievements";
	public static final String BUTTON_HIGHSCORE = "highscore";
	public static final String BUTTON_HELP = "help";
	public static final String BUTTON_OPTIONS = "options";
	
	private ApoJumpPlayer player;
	
	private int curTarget;
	
	private String curFunction;	
	
	private BufferedImage iBackground;
	
	public ApoJumpStateMenu(ApoJumpPanel game) {
		super(game);
	}
	
	public void init() {
		this.getGame().setShouldRepaint(true);
		
		if (this.player == null) {
			BufferedImage iPlayer = ApoJumpImageContainer.iPlayer;
			int width = iPlayer.getWidth();
			int height = iPlayer.getHeight()/2;
			int x = (int)(this.getGame().getButtons()[0].getX() + this.getGame().getButtons()[0].getWidth()/2 - width/2);
			int y = (int)(this.getGame().getButtons()[0].getY() - height);
			this.player = new ApoJumpPlayer(iPlayer, x, y, width, height);
		}
		this.player.init();
		this.player.setVelocityY(-ApoJumpPlatformNormal.NEW_VEC_Y);
		this.curTarget = -1;
		if (this.iBackground == null) {
			this.makeBackground();
		}
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.getGame().renderBackground(g);
		
		g.drawImage(this.getGame().getImages().getImage("images/title.png", false), 70, 0, null);

		g.dispose();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (this.getGame().getLevel() != null) {
			if (button == KeyEvent.VK_ENTER) {
				this.getGame().setGame();	
			} else if (button == KeyEvent.VK_ESCAPE) {
				if (!ApoConstants.B_APPLET) {
					System.exit(0);
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoJumpStateMenu.BUTTON_QUIT)) {
			if (!ApoConstants.B_APPLET) {
				System.exit(0);
			}
		} else {
			this.curFunction = function;
			this.player.setVelocityX(-ApoJumpConstants.MAX_VEC_X);
			this.player.setDirection(1);
			this.player.setVelocityY(-ApoJumpConstants.MAX_VEC_Y);
			if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_GAME)) {
				this.curTarget = 1;
			} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_HELP)) {
				this.curTarget = 2;
				this.player.setVelocityX(ApoJumpConstants.MAX_VEC_X);
				this.player.setDirection(0);
			} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_OPTIONS)) {
				this.curTarget = 13;
				this.player.setVelocityX(ApoJumpConstants.MAX_VEC_X);
				this.player.setDirection(0);
			} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_ACHIEVEMENTS)) {
				this.curTarget = 4;
			} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_HIGHSCORE)) {
				this.curTarget = 5;
			} else {
				this.curTarget = -1;
			}
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	private void executeFunction() {
		if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_GAME)) {
			this.getGame().setGame();
		} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_OPTIONS)) {
			this.getGame().setOptions();
		} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_HELP)) {
			this.getGame().setHelp();
		} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_ACHIEVEMENTS)) {
			this.getGame().setAchievements();
		} else if (this.curFunction.equals(ApoJumpStateMenu.BUTTON_HIGHSCORE)) {
			this.getGame().setHighscore();
		}
	}

	@Override
	public void think(int delta) {
		this.player.thinkPosition(delta);
		if (this.curTarget == -1) {
			if (this.player.intersects(this.getGame().getButtons()[0])) {
				this.player.setVelocityY(-ApoJumpPlatformNormal.NEW_VEC_Y);
				this.player.setY(this.getGame().getButtons()[0].getY() - this.player.getHeight());
			}
		} else {
			float middleX = this.getGame().getButtons()[this.curTarget].getX() + this.getGame().getButtons()[this.curTarget].getWidth()/2;
			if ((this.player.getX() + this.player.getWidth()/2 + 2 > middleX) && (this.player.getX() + this.player.getWidth()/2 - 2 < middleX)) {
				this.player.setVelocityX(0);
			}
			if (this.player.getVelocityY() > 0) {
				if (this.player.intersects(this.getGame().getButtons()[this.curTarget])) {
					this.executeFunction();
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		if (this.player != null) {
			this.player.render(g, 0, 0);
		}
		if (ApoConstants.B_APPLET) {
			g.drawImage(ApoJumpImageContainer.iButtonBackground, (int)(this.getGame().getButtons()[0].getX()), (int)(this.getGame().getButtons()[0].getY()), null);
		}
	}
	
}
