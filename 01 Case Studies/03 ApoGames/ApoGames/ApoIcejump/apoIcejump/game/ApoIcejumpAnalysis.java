package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpAnalysis extends ApoIcejumpModel {
	
	public static final String MENU = "menu";
	public static final String START = "start";
	
	private int time;
	
	private boolean bStart;
	
	private float changeY;
	
	private BufferedImage iBackgroundForAnalysis;
	
	public ApoIcejumpAnalysis(ApoIcejumpPanel game) {
		super(game);
	}
	
	public void start(boolean bFirst) {
		if (!bFirst) {
			this.bStart = false;
			this.changeY = ApoIcejumpConstants.GAME_HEIGHT;
		} else {
			this.bStart = true;
			this.changeY = 0;
		}
		if (this.iBackgroundForAnalysis == null) {
			this.iBackgroundForAnalysis = this.makeAndGetBackgroundForAnalysis(ApoIcejumpConstants.GAME_WIDTH/2 + 60, ApoIcejumpConstants.GAME_HEIGHT/2 + 80);
		}

		this.makeTheFish();
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			this.getGame().getPlayers()[i].setRealVecX(0);
			this.getGame().getPlayers()[i].setVecX(0);
			this.getGame().getPlayers()[i].setVecY(0);
			this.getGame().getPlayers()[i].noGoodies();
			this.getGame().getPlayers()[i].setX(ApoIcejumpConstants.GAME_WIDTH*1/4 - this.getGame().getPlayers()[i].getWidth()/2);
			if (this.getGame().getPlayers()[i].isBVisible()) {
				this.getGame().getPlayers()[i].setY(ApoIcejumpConstants.GAME_HEIGHT/2 + 0);
			} else {
				this.getGame().getPlayers()[i].setY(ApoIcejumpConstants.GAME_HEIGHT/2 + 40);
				this.getGame().getPlayers()[i].setRidiculeTime(-1);
			}
		}
		this.getGame().makeNewParticle();
	}
	
	private BufferedImage makeAndGetBackgroundForAnalysis(int width, int height) {
		BufferedImage iBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		
		g.dispose();
		
		return iBackground;
	}
	
	private void makeTheFish() {
		this.getGame().makeFish((int)(Math.random() * 20 + 25), false);
		this.time = (int)(Math.random() * 3000 + 500);
		
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].init();
				this.getGame().getButtons()[i].setVecX((float)(Math.random() * 0.05 - 0.025f));
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu(true, true);
		} else if ((button == KeyEvent.VK_ENTER) || (button == KeyEvent.VK_R)) {
			this.getGame().setGame();
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
		if (!this.bStart) {
			this.getGame().thinkFish(delta);
			this.changeY -= delta * ApoIcejumpConstants.CHANGE_MENU_TO_GAME;
			if (this.changeY <= 0) {
				this.changeY = 0;
				this.bStart = true;
			}
			this.getGame().thinkBird(delta);
			this.getGame().thinkWaves(delta);
			this.getGame().thinkBlocks(delta);
		} else {
			for (int i = 0; i < this.getGame().getPlayers().length; i++) {
				if (this.getGame().getPlayers()[i].isBVisible()) {
					this.getGame().getPlayers()[i].think(delta, this.getGame());
				}
			}
		}
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
		if (!this.bStart) {
			this.getGame().renderBackground(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderBird(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderParticle(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderSnowflakes(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWavesWithBlocks(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWater(g, 0, (int)this.changeY);
			this.getGame().renderFish(g, 0, (int)this.changeY);
			
			this.renderAnalysis(g, 0, (int)this.changeY);
			
			this.getGame().renderButtons(g, 0, (int)this.changeY);			
		} else {
			this.getGame().renderWater(g, 0, 0);
			this.getGame().renderFish(g, 0, 0);
			
			this.renderAnalysis(g, 0, 0);
			
			this.getGame().renderButtons(g);
		}
	}
	
	private void renderAnalysis(Graphics2D g, int changeX, int changeY) {
		
		//this.getGame().renderPlayer(g, changeX, changeY);
		g.drawImage(this.iBackgroundForAnalysis, changeX + ApoIcejumpConstants.GAME_WIDTH/2 - this.iBackgroundForAnalysis.getWidth()/2, changeY + ApoIcejumpConstants.GAME_HEIGHT/2 - this.iBackgroundForAnalysis.getHeight()/2, null);
		
		g.setColor(Color.BLACK);
		g.setFont(ApoIcejumpConstants.FONT_PLAYER);
		String s = "The winner is";
		this.drawString(g, s, changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 4 + 30));

		int player = -1;
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			if (this.getGame().getPlayers()[i].isBVisible()) {
				player = i;
			}
		}
		if (player != -1) {
			this.drawString(g, this.getGame().getPlayers()[player].getName() + "("+(player + 1)+")", changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 2 - 5));
			this.drawString(g, "by " + this.getGame().getPlayers()[player].getAuthor(), changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 2 + 5 + 25));	
		} else {
			this.drawString(g, "nobody", changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 2));			
		}
		
		this.getGame().renderPlayer(g, changeX, changeY);
		this.getGame().renderPlayer(g, changeX + ApoIcejumpConstants.GAME_WIDTH/2, changeY);
	}
	
	private void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}

}
