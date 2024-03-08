package apoBot;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;

import org.apogames.ApoComponentBufferedStrategy;
import org.apogames.ApoConstants;
import org.apogames.help.ApoFileFilter;
import org.apogames.image.ApoImageScale;

import apoBot.level.ApoBotLevel;
import apoBot.entity.ApoBotPlayer;
import apoBot.game.ApoBotButton;

/**
 * Komponente von der das Spiel erbt und einige wichtigte Funktionen bereitstellt
 * @author Dirk Aporius
 *
 */
public abstract class ApoBotComponent extends ApoComponentBufferedStrategy {

	private static final long serialVersionUID = 1L;
	/** Angabe, wo die Levels für das Applet liegen */
	public static final String APPLET_LEVEL = "http://apo-games.de/apoBot/levels/";
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private final ApoFileFilter	fileFilter = new ApoFileFilter("bot");
	/** gibt an, ob die rechts Maustaste gedrueckt wird oder nicht */
	private boolean bRightMouse;
	
	private int curX, curY, curWidth, curHeight;
	private int levelWidth, levelHeight;
	/** alte x-Koordinate der Maus */
	private int oldMouseX;
	/** alte y-Koordinate der Maus */
	private int oldMouseY;
	/** das eigentliche Spielfeldbild */
	private BufferedImage iBackground;
	/** das Original Spielfeldbild was bei jedem Level leicht verändert wird */
	private BufferedImage iBackgroundOriginal;
	/** Das Bild mit dem Level */
	private BufferedImage iLevel;
	/** Bild des Spielers */
	private BufferedImage iPlayer;
	/** Bild mit den Tiles für das Level */
	private BufferedImage iTile;
	private BufferedImage iButtonEmpty;
	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoBotImage image;
	/** merkt sich, welche Maustaste wurde gerade gedrückt */
	private String functionMouse;
	/** merkt sich, welche Taste gerade gedrückt wurde */
	private int functionKey;
	/** Das Objekt mit dem Level */
	private ApoBotLevel level;
	/** Das Objekt mit dem eigenen Spieler */
	private ApoBotPlayer player;
	
	private int curLevel;
	/** Klasse die die Buttons erstellt */
	private ApoBotButton buttons;
	
	public ApoBotComponent(boolean mouse, boolean key, int wait_time_think,	int wait_time_render) {
		super(mouse, key, wait_time_think, wait_time_render);
	}

	@Override
	public void init() {
		if ((!ApoConstants.B_APPLET) && (this.fileChooser == null)) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File( System.getProperty("user.dir") + File.separator+ "levels" ) );
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		
		this.curLevel = 0;
		this.curX = 0;
		this.curY = 0;
		this.curWidth = ApoBotConstants.GAME_GAME_WIDTH;
		this.curHeight = ApoBotConstants.GAME_GAME_HEIGHT;
		this.oldMouseX = -1;
		this.oldMouseY = -1;
		
		this.levelWidth = this.curWidth;
		this.levelHeight = this.curHeight;
		
		this.image = new ApoBotImage();

		ApoImageScale scale = new ApoImageScale(null);
		this.iTile = this.image.getImage("images/tiles.png", false);
		this.iPlayer = this.image.getImage("images/player.png", false);
		this.iButtonEmpty = this.image.getImage("images/empty.png", false);
		
		if (this.buttons == null) {
			this.buttons = new ApoBotButton(this);
			this.buttons.init();
		}
		
		if (ApoBotConstants.SIZE == 2) {
			this.iTile = scale.getDoubleScaledImage(this.iTile);
		}
		if (ApoBotConstants.SIZE == 2) {
			this.iPlayer = scale.getDoubleScaledImage(this.iPlayer);
		}
		
		this.functionKey = -1;
		this.functionMouse = null;
	}
	
	/**
	 * öffnet einen fileChooser zum Laden eines Levels
	 * @param level : Pfad zum Level der geladen werden soll
	 * @return immer FALSE
	 */
	public boolean levelLoad(String level) {
		int p = 0;
		if (level == null) {
			p = this.fileChooser.showOpenDialog(this);
		}
		if(p == 0) {
			String s = "";
			if (level != null) {
				s = level;
			} else {
				s = this.fileChooser.getSelectedFile().getPath();
			}
			
			this.loadLevel(s);
		}
		return false;
	}
	
	/**
	 * öffnet einen fileChooser zum Speichern eines Levels
	 * @return immer FALSE
	 */
	public boolean levelSave() {
		int p = this.fileChooser.showSaveDialog(this);
		if(p == 0) {
			String s = this.fileChooser.getSelectedFile().getPath();
			int t = s.indexOf(46);
			if ( t != -1 ) {
				s	= s.substring( 0, t );
			}
			s	+= this.fileFilter.getLevelName();
			
			this.saveLevel(s);
		}
		return false;
	}
	
	public abstract void saveLevel(String s);
	
	public abstract void loadLevel(String s);
	
	public int getCurLevel() {
		return this.curLevel;
	}

	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	public BufferedImage getIButtonEmpty() {
		return this.iButtonEmpty;
	}

	public ApoBotPlayer getPlayer() {
		return this.player;
	}

	public BufferedImage getIPlayer() {
		return this.iPlayer;
	}

	public void setIPlayer(BufferedImage player) {
		this.iPlayer = player;
	}

	public ApoBotLevel getLevel() {
		return this.level;
	}

	public BufferedImage getILevel() {
		return this.iLevel;
	}

	/**
	 * setzt alle Werte (wie breit das Level ist, wo der Spieler steht usw) auf den übergebenen Wert
	 * @param level : das neue Level
	 */
	public void setLevel(ApoBotLevel level) {
		this.level = level;
		this.player = new ApoBotPlayer(this.iPlayer, this.level.getStartPoint().x, this.level.getStartPoint().y, ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE, ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE, 4, ApoBotConstants.ANIMATION_TIME);
		this.player.setStartDir(this.level.getDirectionView());
		int w = 16 * ApoBotConstants.SIZE;
		int startWidth = + this.level.getLevel()[0].length * w + this.level.getLevel().length * w;
		int startHeight = this.level.getLevel().length * w/2 + (this.level.getLevel()[0].length)/2 * w;
		
		this.levelWidth = startWidth;
		this.levelHeight = startHeight;
		
		this.centerCamera();
		
		this.makeBackground();
	}

	public BufferedImage getIBackgroundOriginal() {
		return this.iBackgroundOriginal;
	}

	public void setIBackgroundOriginal(BufferedImage iBackgroundOriginal) {
		this.iBackgroundOriginal = iBackgroundOriginal;
	}

	/**
	 * erstellt das Image mit dem Level
	 */
	public void makeBackground() {
		this.iLevel = this.image.getIBackground(this.level, this.iTile, this.levelWidth, this.levelHeight);		
	}
	
	/**
	 * zentriert die Kamera auf den Spieler
	 */
	public void centerCamera() {
		int orgPlayerX = (int)(this.player.getX());
		int orgPlayerY = (int)(this.player.getY());
		
		int w = 16 * ApoBotConstants.SIZE;
		
		int playerX = orgPlayerX * w + orgPlayerY * (w) + (int)(this.player.getRunX());
		int playerY = orgPlayerX * (w)/2 - orgPlayerY * (w)/2 + this.level.getLevel().length * w/2 + (int)(this.player.getRunY());
		
		this.curY = 0;
		if (this.levelHeight > ApoBotConstants.GAME_GAME_HEIGHT) {
			this.curY = playerY - ApoBotConstants.GAME_GAME_HEIGHT/2;
			if (this.curY < -2 * w) {
				this.curY = -2 * w;
			} else if (this.curY + ApoBotConstants.GAME_GAME_HEIGHT - 2 * w >= this.levelHeight) {
				this.curY = this.levelHeight - ApoBotConstants.GAME_GAME_HEIGHT + 2 * w;
			}
		}
		this.curX = 0;
		if (this.levelWidth > ApoBotConstants.GAME_GAME_WIDTH) {
			this.curX =  playerX - ApoBotConstants.GAME_GAME_WIDTH/2;
			if (this.curX < -1 * w / 2) {
				this.curX = -1 * w / 2;
			} else if (this.curX + ApoBotConstants.GAME_GAME_WIDTH - 1 * w / 2 >= this.levelWidth) {
				this.curX = this.levelWidth - ApoBotConstants.GAME_GAME_WIDTH + 1 * w / 2;
			}
		}
	}
	
	public BufferedImage getITile() {
		return this.iTile;
	}

	public void setITile(BufferedImage tile) {
		this.iTile = tile;
	}

	public int getLevelWidth() {
		return this.levelWidth;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	public int getLevelHeight() {
		return this.levelHeight;
	}

	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	public BufferedImage getIBackground() {
		return this.iBackground;
	}

	public void setIBackground(BufferedImage background) {
		this.iBackground = background;
	}

	public ApoBotImage getImage() {
		return this.image;
	}

	public int getCurWidth() {
		return this.curWidth;
	}

	public int getCurHeight() {
		return this.curHeight;
	}

	public void setCurX(int curX) {
		this.curX = curX;
	}

	public void setCurY(int curY) {
		this.curY = curY;
	}

	/**
	 * startet das aktulle Level neu
	 */
	public void restart() {
		this.level.init();
		this.player.init();
		this.makeBackground();
	}
	
	/**
	 * wird zum "nachdenken" aufgerufen und schaut, ob gerade eine Maus oder Taste gedrückt wird
	 */
	public void think(int delta) {
		if (this.functionMouse != null) {
			this.setMouseFunction(this.functionMouse);
			this.functionMouse = null;
		}
		if (this.functionKey >= 0) {
			this.setKeyFunction(this.functionKey);
			this.functionKey = -1;
		}
	}
	
	public void setButtonFunction(String function) {
		this.functionMouse = function;
	}
	
	public abstract void setMouseFunction(String function);
	
	public abstract void setKeyFunction(int function);
	
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		this.functionKey = -1;
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int code = e.getKeyCode();
		this.functionKey = code;
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		this.bRightMouse = false;
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			this.bRightMouse = true;
			this.oldMouseX = e.getX();
			this.oldMouseY = e.getY();
		} else {
			super.mousePressed(e);
		}
	}
	
	/**
	 * wird aufgerufen, wenn mithilfe der rechten Maustaste gescrollt werden soll
	 */
	public void mouseDragged(MouseEvent e) {
		if ((this.bRightMouse)) {
			int changeX = this.oldMouseX - e.getX();
			int changeY = this.oldMouseY - e.getY();
			int w = 16 * ApoBotConstants.SIZE;
			this.oldMouseX = e.getX();
			this.oldMouseY = e.getY();
			if ((this.curWidth < this.levelWidth)) {
				this.curX += changeX;
				if (this.curX + this.curWidth - 1 * w / 2 >= this.levelWidth) {
					this.curX = this.levelWidth - this.curWidth + 1 * w / 2 - 1;
				}
				if (this.curX < -1 * w/2) {
					this.curX = -1 * w/2 + 1;
				}
				if (!super.isBRepaint())
					super.repaint();
			}
			if (this.curHeight < this.levelHeight) {
				this.curY += changeY;
				if (this.curY + this.curHeight - 2 * w >= this.levelHeight) {
						this.curY = this.levelHeight - this.curHeight + 2 * w - 1;
				}
				if (this.curY < -2 * w) {
					this.curY = -2 * w + 1;
				}
				if (!super.isBRepaint())
					super.repaint();
			}
		}
	}
	
	/**
	 * gibt zurück, ob das Level zu breit ist und somit gescrollt werden kann
	 * @return TRUE, Level zu breit, sonst FALSE
	 */
	public boolean isLevelToWidth() {
		if (this.levelWidth > ApoBotConstants.GAME_GAME_WIDTH) {
			return true;
		}
		return false;
	}

	/**
	 * gibt zurück, ob das Level zu hoch ist und somit gescrollt werden kann
	 * @return TRUE, Level zu hoch, sonst FALSE
	 */
	public boolean isLevelToHeight() {
		if (this.levelHeight > ApoBotConstants.GAME_GAME_HEIGHT) {
			return true;
		}
		return false;
	}
	
	/**
	 * rechnet das startX zum Zeichnen des Levels an
	 * @return startX zum Zeichnen des Levels
	 */
	public int getStartX() {
		int w = 16 * ApoBotConstants.SIZE;
		int startX = -this.curX - level.getLevel()[0].length/2 * w - level.getLevel().length/2 * w + ApoBotConstants.GAME_GAME_WIDTH/2 - w;
		if (this.levelWidth > ApoBotConstants.GAME_GAME_WIDTH) {
			startX = -this.curX;
		}
		return startX;
	}
	
	/**
	 * rechnet das startY zum Zeichnen des Levels an
	 * @return startY zum Zeichnen des Levels
	 */
	public int getStartY() {
		int w = 16 * ApoBotConstants.SIZE;
		int startY = -this.curY + ApoBotConstants.GAME_GAME_HEIGHT/2 - ((level.getLevel()[0].length - level.getLevel().length)/2 * w/2);
		if (this.levelHeight > ApoBotConstants.GAME_GAME_HEIGHT) {
			startY = -this.curY + level.getLevel().length * w/2;
		}
		return startY;
	}
	
	public int getCurX() {
		return this.curX;
	}

	public int getCurY() {
		return this.curY;
	}

	/**
	 * malt das Level
	 * @param g : Graphics2D Objekt
	 * @param iLevel : Bild des Levels
	 * @param level : Objekt des Levels
	 */
	public void renderLevel(Graphics2D g, BufferedImage iLevel, ApoBotLevel level) {
		this.renderLevel(g, iLevel, level, false);
	}
	
	/**
	 * malt das Level
	 * @param g : Graphics2D Objekt
	 * @param iLevel : Bild des Levels
	 * @param level : Objekt des Levels
	 * @param bEditor : true, wird vom Editor aufgerufen, sonst FALSE
	 */
	public void renderLevel(Graphics2D g, BufferedImage iLevel, ApoBotLevel level, boolean bEditor) {
		final int w = 16 * ApoBotConstants.SIZE;
		
		int startX = this.getStartX();
		int startY = this.getStartY();
		
		if (!bEditor) {
			//image mit dem Level malen
			int imageX = -this.curX;
			int imageY = -this.curY - 2 * w;
			if (this.levelWidth <= ApoBotConstants.GAME_GAME_WIDTH) {
				imageX = startX;//-this.curX - level.getLevel()[0].length/2 * w - level.getLevel().length/2 * w + ApoBotConstants.GAME_GAME_WIDTH/2 - w/2;			
			}
			if (this.levelHeight <= ApoBotConstants.GAME_GAME_HEIGHT) {
				imageY = -this.curY + ApoBotConstants.GAME_GAME_HEIGHT/2 - ((level.getLevel()[0].length - level.getLevel().length)/2 * w/2) - (level.getLevel().length) * w/2 - 2 * w;
			}
			g.drawImage(iLevel, imageX, imageY, null);
		}
		
		/**
		 * geht das Level durch und malt es, wenn der Aufruf vom Editor kommt
		 */
		for (int y = level.getLevel().length - 1; y >= 0; y--) {
			for (int x = 0; x < level.getLevel()[0].length; x++) {
				for (int height = 0; height <= level.getLevel()[y][x].getHeight(); height++) {
					if ((!bEditor) && (height != level.getLevel()[y][x].getHeight())) {
						continue;
					}
					int myX = startX + x * w + y * w;
					int myY = startY - y * w/2 + x * w/2 - height * w/2;
					if ((myY + 30 < 0) || (myY - 5 > ApoBotConstants.GAME_GAME_HEIGHT) || 
						(myX + 35 < 0) || (myX - 5 > ApoBotConstants.GAME_WIDTH) ) {
						continue;
					}
					if ((bEditor) && (this.level.getLevel()[y][x].getGround() != ApoBotConstants.GROUND_REAL_EMPTY)) {
						if (y % 2 == 0) {
							g.drawImage(iTile.getSubimage(0 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
						} else {
							g.drawImage(iTile.getSubimage(4 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null);
						}
					}
					if (bEditor) {
						if (height == level.getLevel()[y][x].getHeight()) {
							if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_ORIGINAL) {
								g.drawImage(this.iTile.getSubimage(1 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
							} else if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_GOAL) {
								g.drawImage(this.iTile.getSubimage(2 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null);
							}
						}
					}
				}
			}
		}
		// malt noch den Spieler
		this.renderPlayer(g, startX, startY, level);
	}
	
	/**
	 * malt den Spieler
	 * @param g
	 * @param startX
	 * @param startY
	 * @param level
	 */
	private void renderPlayer(Graphics2D g, int startX, int startY, ApoBotLevel level) {
		if (this.player != null) {
			int w = 16 * ApoBotConstants.SIZE;
			int playerY = (int)(this.player.getY());
			int playerX = (int)(this.player.getX());
			int playerHeight = level.getLevel()[playerY][playerX].getHeight();
			int myX = startX + playerX * w + playerY * w;
			int myY = startY - playerY * w/2 + playerX * w/2 - level.getLevel()[playerY][playerX].getHeight() * w/2;
			if ((myY + 30 < 0) || (myY - 5 > ApoBotConstants.GAME_GAME_HEIGHT) || 
				(myX + 35 < 0) || (myX - 5 > ApoBotConstants.GAME_WIDTH) ) {
			} else {
				this.player.render(g, (int)(myX + (4 * ApoBotConstants.SIZE)), (int)(myY - this.player.getHeight() + w - (2 * ApoBotConstants.SIZE)));
				this.renderOvertile(g, playerX, playerY, startX, startY, playerHeight, level);
			}
		}
	}
	
	/**
	 * malt die Tiles, die den Spieler verdecken könnten neu
	 * (Achtung böse unoptimiert, könnte man stark verbessern und durch ein bissl rechnen, weniger malen und somit Zeit zum Malen sparen =) )
	 * @param g
	 * @param playerX
	 * @param playerY
	 * @param startX
	 * @param startY
	 * @param startHeight
	 * @param level
	 */
	private void renderOvertile(Graphics2D g, int playerX, int playerY, int startX, int startY, int startHeight, ApoBotLevel level) {
		int startXPlayer = playerX;
		if ((!this.getPlayer().isBUp() && (this.getPlayer().isBJump()) && (this.getPlayer().getJumpHeight() != 0)) || 
			((this.getPlayer().isBRun()) && (!this.getPlayer().isBUp()))) {
			startXPlayer = playerX + 2;
		} else if ((this.getPlayer().getCurDir() == ApoBotConstants.DIR_NORTH_UPLEFT) &&
				   (this.getPlayer().isBRun()) && (!this.getPlayer().isBJump())) {
			startXPlayer = playerX - 1;
			if (startXPlayer < 0) {
				startXPlayer = 0;
			}
		}
		for (int x = startXPlayer; x < level.getLevel()[0].length; x++) {
			int start = playerY;
			if (start >= level.getLevel().length) {
				start = level.getLevel().length - 1;
			}
			for (int y = start; y >= 0; y--) {
				if ((x == playerX) && (y >= playerY)) {
					continue;
				}
				for (int heightLevel = startHeight + 1; heightLevel <= level.getLevel()[y][x].getHeight(); heightLevel++) {
					this.renderTile(g, x, y, startX, startY, heightLevel);
				}
			}
		}
		
	}
	
	/**
	 * malt das eigentliche Tile
	 * @param g
	 * @param x
	 * @param y
	 * @param startX
	 * @param startY
	 * @param heightLevel
	 */
	private void renderTile(Graphics2D g, int x, int y, int startX, int startY, int heightLevel) {
		int w = 16 * ApoBotConstants.SIZE;
		int myX = startX + x * w + y * w;
		int myY = startY - y * w/2 + x * w/2 - heightLevel * w/2;
		if ((myY + 30 < 0) || (myY - 5 > ApoBotConstants.GAME_GAME_HEIGHT) || 
			(myX + 35 < 0) || (myX - 5 > ApoBotConstants.GAME_WIDTH) ) {
			return;
		}
		if ((level.getLevel()[y][x].getGround() != ApoBotConstants.GROUND_REAL_EMPTY) && (heightLevel > 0)) {
			if (y % 2 == 0) {
				g.drawImage(iTile.getSubimage(0 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
			} else {
				g.drawImage(iTile.getSubimage(4 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null);
			}
		}
		if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_ORIGINAL) {
			g.drawImage(this.iTile.getSubimage(1 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
		} else if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_GOAL) {
			g.drawImage(this.iTile.getSubimage(2 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
		}
	}
	
}