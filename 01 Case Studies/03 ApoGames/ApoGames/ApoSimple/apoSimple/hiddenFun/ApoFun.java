package apoSimple.hiddenFun;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoHelp;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleButtonNot;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleModel;
import apoSimple.game.ApoSimplePanel;

public class ApoFun extends ApoSimpleModel {

	public static final String MENU = "credits_menu";

	private BufferedImage iBackground;
	
	private int curLevel;
	
	private int curScore;
	
	private BufferedImage iBackgroundScore;
	
	private boolean bAnalysis;
	
	private ApoFunEntity sheep, flower;
	
	private ArrayList<ApoFunDog> dogs;
	
	private int curGames;
	
	private int pointsOverall;
	
	private int pointsBest;
	
	private final Rectangle2D.Float rec = new Rectangle2D.Float(10, 10, 455, 455);
	
	public ApoFun(ApoSimplePanel game) {
		super(game);

		this.curGames = 0;
		this.pointsOverall = 0;
		this.pointsBest = -1;
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/game.png", false);
		}
		this.loadLevel(0);
		this.getBackgroundScore();
		this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() + 130);
		this.curScore = -1;
		this.winGame();
		
		this.getGame().getButtons()[68].setBVisible(false);
		this.getGame().getButtons()[70].setBVisible(false);
	}
	
	private void loadLevel(int level) {
		this.curLevel = level;
		this.curScore = 0;
		this.bAnalysis = false;
		this.getGame().getButtons()[28].setBVisible(false);
		this.sheep = new ApoFunEntity(ApoSimpleImages.LITTLE_SHEEP, 230, 230, ApoFunEntity.SHEEP);
		this.setRandomFlower();
		if (this.dogs == null) {
			this.dogs = new ArrayList<ApoFunDog>();
		} else {
			this.dogs.clear();
		}
	}
	
	private void setRandomFlower() {
		int x = -1;
		int y = -1;
		while (x < 0) {
			x = (int)(Math.random() * 425 + 10);
			y = (int)(Math.random() * 425 + 10);
			if (ApoHelp.rectangleIntersects(this.sheep.getX() - 10, this.sheep.getY() - 10, this.sheep.getWidth() + 20, this.sheep.getHeight() + 20, x, y, 30, 30)) {
				x = -1;
				y = -1;
			}
		}
		this.flower = new ApoFunEntity(ApoSimpleImages.LITTLE_FLOWER, x, y, ApoFunEntity.FLOWER);
	}
	
	private void addRandomDog() {
		int x = -1;
		int y = -1;
		while (x < 0) {
			x = (int)(Math.random() * 425 + 10);
			y = (int)(Math.random() * 425 + 10);
			if (ApoHelp.rectangleIntersects(this.sheep.getX() - 10, this.sheep.getY() - 10, this.sheep.getWidth() + 20, this.sheep.getHeight() + 20, x, y, 30, 30)) {
				x = -1;
				y = -1;
			}
		}
		this.dogs.add(new ApoFunDog(x, y));
	}
	
	private void winGame() {
		this.bAnalysis = true;
		this.getGame().getButtons()[28].setBVisible(true);
		
		this.curGames += 1;
		if (this.curScore > this.pointsBest) {
			this.pointsBest = this.curScore;
		}
		if (this.pointsOverall == -1) {
			this.pointsOverall += this.curScore;
		} else {
			this.pointsOverall += this.curScore;
		}
	}
	
	public Rectangle2D.Float getRec() {
		return this.rec;
	}

	public BufferedImage getBackgroundScore() {
		if (this.iBackgroundScore == null) {
			this.iBackgroundScore = this.getGame().getImages().getImage("images/game_real.png", false);
			
			Color c = new Color(133, 209, 2);
			Color c2 = new Color(200, 255, 108);
			for (int y = 0; y < this.iBackgroundScore.getHeight(); y++) {
				for (int x = 0; x < this.iBackgroundScore.getWidth(); x++) {
					int rgb = this.iBackgroundScore.getRGB(x, y);
					if (rgb == c.getRGB())
					this.iBackgroundScore.setRGB(x, y, c2.getRGB());
				}
			}
		}
		return this.iBackgroundScore;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() - 130);
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_ENTER) {
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleGame.MENU)) {
			this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() - 130);
			this.getGame().setCredits();
		}else if (function.equals(ApoSimpleGame.MUSIC)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[53]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundMusic(b.isBActive());
		} else if (function.equals(ApoSimpleGame.SOUND)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[54]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundEffects(b.isBActive());
		} else if (function.equals(ApoSimpleGame.RESTART)) {
			this.loadLevel(this.curLevel);
		} else if (function.equals(ApoSimpleGame.RELOAD)) {
			this.loadLevel(this.curLevel);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		return true;
	}
	
	public boolean mouseDragged(int x, int y) {
		this.mouseMoved(x, y);
		return true;
	}
	
	public boolean mouseMoved(int x, int y) {
		if (!this.bAnalysis) {
			this.sheep.setX(x - this.sheep.getWidth()/2);
			this.sheep.setY(y - this.sheep.getHeight()/2);
			
			if (this.sheep.getX() + this.sheep.getWidth() > this.rec.x + this.rec.width) {
				this.sheep.setX(this.rec.x + this.rec.width - this.sheep.getWidth());
			}
			if (this.sheep.getX() < this.rec.x) {
				this.sheep.setX(this.rec.x);
			}
			if (this.sheep.getY() + this.sheep.getHeight() > this.rec.y + this.rec.height) {
				this.sheep.setY(this.rec.y + this.rec.height - this.sheep.getHeight());
			}
			if (this.sheep.getY() < this.rec.y) {
				this.sheep.setY(this.rec.y);
			}
		}
		return false;
	}

	@Override
	public void think(int delta) {
		if (!this.bAnalysis) {
			if (this.sheep.intersects(this.flower)) {
				this.curScore += 10;
				this.setRandomFlower();
				this.addRandomDog();
			}
			for (int i = 0; i < this.dogs.size(); i++) {
				this.dogs.get(i).think(delta, this);
				if (this.dogs.get(i).intersects(this.sheep)) {
					this.winGame();
					break;
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);

			this.flower.render(g, 0, 0);
			this.sheep.render(g, 0, 0);
			for (int i = 0; i < this.dogs.size(); i++) {
				this.dogs.get(i).render(g, 0, 0);
			}
			
			this.drawLevelInfo(g);
			this.drawMoves(g);
		}
	}
	
	private void drawAnalysis(Graphics2D g) {
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * 7;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		
		g.drawImage(this.iBackgroundScore, x, y, null);

		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_STATICS);
		if (this.curScore >= 0) {
			String s = "You got "+this.curScore+" points.";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 60);
		}

		g.setFont(ApoSimpleGame.FONT_RESTART);
		
		if (ApoSimpleConstants.REGION.equals("de")) {
			String s = "Eure Aufgabe ist es, das Schaf 'Wolly'";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 105);
			
			s = "zu den Blumen zu geleiten.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 127);
			
			s = "Aber Achtung vor den Hunden!";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 149);
			
			s = "Steuere Wolly einfach mit der Maus.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 180);			
		} else {
			String s = "Your task is to reach the";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 105);
			
			s = "flower with your the sheep 'Wolly'";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 127);
			
			s = "But beware of the dogs!";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 149);
			
			s = "Move your sheep with the mouse.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 180);
		}
		if (this.pointsBest >= 0) {
			String s = "Best result";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 - 150, y + 240);
			
			s = String.valueOf(this.pointsBest);
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 - 150, y + 275);
			
			s = "Average score";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 + 150, y + 240);
			
			s = String.valueOf(this.pointsOverall/this.curGames);
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 + 150, y + 275);
		}
		
		this.getGame().getButtons()[28].render(g, 0, 0);
	}
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.curLevel + 1);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 552;
		int y = 193;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
	
	public void drawMoves(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.curScore);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 561;
		int y = 245;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
		
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		x = 535;
		y = 278;
	}
	
	public void drawOverlay(Graphics2D g) {
		this.drawCoins(g);
		this.drawMusicOver(g);
		this.drawEffectOver(g);
		this.drawNoticeOver(g);
		this.drawReloadOver(g);
		
		if (this.bAnalysis) {
			this.drawAnalysis(g);
		}
	}
	
	public void drawMusicOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_MUSIC, 53);
	}
	
	public void drawEffectOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_EFFECTS, 54);
	}
	
	public void drawNoticeOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_NOTICE, 39);
	}
	
	public void drawReloadOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_RELOAD, 40);
	}
	
	public void drawCoins(Graphics2D g) {
		int y = 354;
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_COIN);
		String s = String.valueOf(this.getGame().getCoins());
		int w = g.getFontMetrics().stringWidth(s);
		int x = 625;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		ApoSimpleGame.drawString(g, s, x - w, y + ApoSimpleImages.COINS.getHeight()/2 + h/2);
	}
	
}