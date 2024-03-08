package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.entity.ApoIcejumpDrag;

public class ApoIcejumpSimulate extends ApoIcejumpModel {
	
	private final int MIN_GAMES = 1;
	private final int MAX_GAMES = 200;
	
	public static final String MENU = "menu";
	public static final String SIMULATE = "simulate";
	public static final String CANCEL = "cancel";
	
	private int time;
	
	private BufferedImage iSimulate;
	
	private ApoIcejumpSimulation simulation;
	
	private int gamesPlay;
	
	private ApoIcejumpDrag drag;
	
	public ApoIcejumpSimulate(ApoIcejumpPanel game) {
		super(game);
	}
	
	public void start() {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
		if (this.iSimulate == null) {
			this.iSimulate = this.makeAndGetBackgroundForSimulate(ApoIcejumpConstants.GAME_WIDTH - 100, ApoIcejumpConstants.GAME_HEIGHT/2 + 80);
		}
		if (this.simulation == null) {
			this.simulation = new ApoIcejumpSimulation(this.getGame());
		}
		if (this.drag == null) {
			int x = ApoIcejumpConstants.GAME_WIDTH * 1 / 6;
			int y = ApoIcejumpConstants.GAME_HEIGHT/2 - this.iSimulate.getHeight()/2;
			this.drag = new ApoIcejumpDrag(x, y + 75, 20, 20, ApoIcejumpConstants.GAME_WIDTH * 2 / 3);
		}
		if (this.gamesPlay <= 0) {
			this.gamesPlay = 100;
			this.drag.setToPercent(50);
		}
		this.simulation.deleteResults();
		this.getGame().makeNewParticle();
	}
	
	private BufferedImage makeAndGetBackgroundForSimulate(int width, int height) {
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
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (this.simulation.isBSimulate()) {
			return;
		}
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu(true, true);
		} else if (button == KeyEvent.VK_ENTER) {
			this.getGame().setMenu(true, true);
		} else if (button == KeyEvent.VK_S) {
			this.simulation.simulate(this.gamesPlay);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.simulation.isBSimulate()) {
			if (function.equals(ApoIcejumpSimulate.CANCEL)) {
				this.simulation.breakSimulation();
			}
			return;
		}
		if (function.equals(ApoIcejumpSimulate.MENU)) {
			this.getGame().setMenu(true, true);
		} else if (function.equals(ApoIcejumpSimulate.SIMULATE)) {
			this.simulation.simulate(this.gamesPlay);
		}else if (function.equals(ApoIcejumpMenu.LEFT_PLAYERONE)) {
			this.simulation.deleteResults();
			this.getGame().getMenu().mouseButtonFunction(function);
		} else if (function.equals(ApoIcejumpMenu.RIGHT_PLAYERONE)) {
			this.simulation.deleteResults();
			this.getGame().getMenu().mouseButtonFunction(function);
		} else if (function.equals(ApoIcejumpMenu.LEFT_PLAYERTWO)) {
			this.simulation.deleteResults();
			this.getGame().getMenu().mouseButtonFunction(function);
		} else if (function.equals(ApoIcejumpMenu.RIGHT_PLAYERTWO)) {
			this.simulation.deleteResults();
			this.getGame().getMenu().mouseButtonFunction(function);
		} else if (function.equals(ApoIcejumpMenu.LOAD_PLAYERONE)) {
			this.simulation.deleteResults();
			this.getGame().loadPlayer(ApoIcejumpConstants.PLAYER_ONE, null);
		} else if (function.equals(ApoIcejumpMenu.LOAD_PLAYERTWO)) {
			this.simulation.deleteResults();
			this.getGame().loadPlayer(ApoIcejumpConstants.PLAYER_TWO, null);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.drag != null) {
			if (this.drag.mouseReleased(x, y)) {
				this.simulation.deleteResults();
				this.gamesPlay = (int)(this.drag.getPercent() / 100 * (this.MAX_GAMES - this.MIN_GAMES)) + this.MIN_GAMES;
			}
		}
	}
	
	public boolean mouseMoved(MouseEvent e) {
		if (this.drag != null) {
			if (this.drag.mouseMoved(e.getX(), e.getY())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (this.mouseMoved(e)) {
			this.mouseButtonReleased(x, y);
			return true;
		}
		return false;
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
		if (this.simulation.isBSimulate()) {
			if (this.getGame().getButtons()[23].isBVisible()) {
				this.makeLoadButton(false);
			}
		} else {
			if (!this.getGame().getButtons()[23].isBVisible()) {
				this.makeLoadButton(true);
			}
		}
	}
	
	private void makeLoadButton(boolean bVisible) {
		this.getGame().getButtons()[20].setBVisible(bVisible);
		for (int i = 22; i < 29; i++) {
			this.getGame().getButtons()[i].setBVisible(bVisible);
		}
		this.getGame().getButtons()[29].setBVisible(!bVisible);
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
		
		this.renderSimulation(g);
		
		this.getGame().renderButtons(g);
	}
	
	private void renderSimulation(Graphics2D g) {
		if (this.iSimulate != null) {
			int x = ApoIcejumpConstants.GAME_WIDTH/2 - this.iSimulate.getWidth()/2;
			int y = ApoIcejumpConstants.GAME_HEIGHT/2 - this.iSimulate.getHeight()/2;
			
			g.drawImage(this.iSimulate, x, y, null);
			
			g.setColor(Color.BLACK);
			g.setFont(ApoIcejumpConstants.FONT_TITLE);
			String s = "Simulation";
			this.drawString(g, s, ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 30));

			g.setFont(ApoIcejumpConstants.FONT_PLAYER);
			if (!this.simulation.getResults().noResults()) {
				s = this.simulation.getResults().getWinsPlayerOne() + "";
				this.drawString(g, s, ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(y + 120));
				s = this.simulation.getResults().getWinsPlayerTwo() + "";
				this.drawString(g, s, ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(y + 120));
				this.drawString(g, "games won", ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 120));
			}
			if (this.simulation.isBSimulate()) {
				s = "Simulation runs (" + this.simulation.getResults().getResultCount() + " / " + String.valueOf(this.gamesPlay) + ")...";
				this.drawString(g, s, ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 90));
			} else {
				this.drawString(g, String.valueOf(this.gamesPlay) + " rounds", ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 75));
				this.drag.render(g, 0, 0);
			}
			g.setColor(Color.BLACK);
			this.drawString(g, this.getGame().getPlayers()[0].getName(), ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(y + 155));
			this.drawString(g, "by", ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(y + 180));
			this.drawString(g, this.getGame().getPlayers()[1].getName(), ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(y + 155));
			this.drawString(g, "by", ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(y + 180));
			
			g.setFont(ApoIcejumpConstants.FONT_PLAYER_STATISTICS);
			this.drawString(g, this.getGame().getPlayers()[0].getAuthor(), ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(y + 205));
			this.drawString(g, this.getGame().getPlayers()[1].getAuthor(), ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(y + 205));
			
			
		}
	}
	
	private void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}

}
