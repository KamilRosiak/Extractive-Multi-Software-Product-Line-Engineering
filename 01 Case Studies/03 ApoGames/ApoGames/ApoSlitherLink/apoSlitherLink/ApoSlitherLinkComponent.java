package apoSlitherLink;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoComponentBufferedStrategy;
import org.apogames.ApoConstants;
import org.apogames.help.ApoFileFilter;
import org.apogames.sound.ApoSounds;

/**
 * Abstrakte Klasse von der das eigentliche Spiel erbt und grundlegende Funktionalitäten
 * dem Spielobjekt bereitstellt
 * @author Dirk Aporius
 *
 */
public abstract class ApoSlitherLinkComponent extends ApoComponentBufferedStrategy {

	private static final long serialVersionUID = 1L;

	/** Angabe, wo die Levels für das Applet liegen */
	public static final String APPLET_LEVEL = "http://apo-games.de/apoSlitherLink/levels/";
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private final ApoFileFilter	fileFilter = new ApoFileFilter("class");

	private String curMouseFunction;
	
	/** gibt an, ob die rechts Maustaste gedrueckt wird oder nicht */
	private boolean bRightMouse;
	
	private ArrayList<Integer> releasedKeys;
	
	private ArrayList<Character> charKeys;
	
	private boolean bReleasedMouse;
	
	private boolean[] keys;
	
	private int curX, curY, curWidth, curHeight;
	private float changeX, changeY;
	private int levelWidth, levelHeight;
	/** alte x-Koordinate der Maus */
	private int oldMouseX, mouseX;
	/** alte y-Koordinate der Maus */
	private int oldMouseY, mouseY;
	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoSlitherLinkImages images;
	
	private boolean bFPS;
	
	private boolean bTwoPlayers;
	
	private int time;
	
    private ApoSounds sounds;
    
    private boolean bSound;
    
	private boolean bMiddle;
    
	public ApoSlitherLinkComponent() {
		super(true, true, ApoSlitherLinkConstants.FPS_THINK, ApoSlitherLinkConstants.FPS_RENDER, ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT);
	}

	@Override
	public void init() {
		if ((!ApoConstants.B_APPLET) && (this.fileChooser == null)) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File( System.getProperty("user.dir") + File.separator+ "levels" ) );
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		this.bTwoPlayers = false;
		this.bMiddle = true;
		
		this.curMouseFunction = null;
		
		this.curX = 0;
		this.curY = 0;
		this.curWidth = super.getWidth();
		this.curHeight = super.getHeight();
		this.oldMouseX = -1;
		this.oldMouseY = -1;
		
		this.levelWidth = this.curWidth;
		this.levelHeight = this.curHeight;
		
		if (this.images == null) {
			this.images = new ApoSlitherLinkImages();
		}

		if (this.sounds == null) {
			try {
				this.sounds = new ApoSounds();
				//this.sounds.loadSound(ApoZombieConstants.SAMPLE_EXPLOSION, "/sounds/explode.wav");
				//this.sounds.loadSound(ApoZombieConstants.SAMPLE_KISS, "/sounds/kiss.wav");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.keys = new boolean[256];
		this.releasedKeys = new ArrayList<Integer>();
		this.charKeys = new ArrayList<Character>();
	}
	
	public boolean isBTwoPlayers() {
		return this.bTwoPlayers;
	}

	public void setBTwoPlayers(boolean bTwoPlayers) {
		this.bTwoPlayers = bTwoPlayers;
	}

	public boolean isBMiddle() {
		return this.bMiddle;
	}

	public void setBMiddle(boolean bMiddle) {
		this.bMiddle = bMiddle;
	}

	public boolean isBSound() {
		return this.bSound;
	}

	public void setBSound(boolean sound) {
		this.bSound = sound;
	}
	
	public ApoSounds getSounds() {
		return sounds;
	}

	public void playSound(String song) {
		if (this.isBSound()) {
			this.sounds.playSound(song);
		}
	}
	
	public void stopSound() {
		
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
	
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		int code = e.getKeyCode();
		this.releasedKeys.add(code);
		this.keys[code] = false;
		this.charKeys.add(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int code = e.getKeyCode();
		this.keys[code] = true;
	}	

	/**
	 * gibt den boolean Wert zurück, ob die FPS angezeigt werden sollen
	 * @return TRUE, FPS soll angezeigt werden, ansonsten FALSE
	 */
	public boolean isBFPS() {
		return this.bFPS;
	}

	/**
	 * setzt die boolean Variable, ob die angezeigt werden sollen, auf den übergebenen Wert
	 * @param bfps : TRUE, FPS soll angezeigt werden, ansonsten FALSE
	 */
	public void setBFPS(boolean bfps) {
		bFPS = bfps;
	}

	/**
	 * gibt zurück um wieviel X-Werte sich das Spielfeld zur Darstellung verschoben wird
	 * @return gibt zurück um wieviel X-Werte sich das Spielfeld zur Darstellung verschoben wird
	 */
	public float getChangeX() {
		return this.changeX;
	}

	public void setChangeX(float changeX) {
		this.changeX = changeX;
	}

	/**
	 * gibt zurück um wieviel Y-Werte sich das Spielfeld zur Darstellung verschoben wird
	 * @return gibt zurück um wieviel Y-Werte sich das Spielfeld zur Darstellung verschoben wird
	 */
	public float getChangeY() {
		return this.changeY;
	}

	public void setChangeY(float changeY) {
		this.changeY = changeY;
	}

	/**
	 * gibt die X-Position der Maus zurück
	 * @return gibt die X-Position der Maus zurück
	 */
	public int getMouseX() {
		return this.mouseX;
	}

	/**
	 * gibt die Y-Position der Maus zurück
	 * @return gibt die Y-Position der Maus zurück
	 */
	public int getMouseY() {
		return this.mouseY;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public ArrayList<Integer> getReleasedKeys() {
		return this.releasedKeys;
	}
	
	public boolean[] getKeys() {
		return this.keys;
	}
	
	public void setKeys(int key, boolean bKey) {
		this.keys[key] = bKey;
	}
	
	public void clearKeys() {
		this.keys = new boolean[256];		
	}

	/**
	 * gibt das Images Objekt mitdem Bilder aus Werten erstellt werden können zurück
	 * @return gibt das Images Objekt mitdem Bilder aus Werten erstellt werden können zurück
	 */
	public ApoSlitherLinkImages getImages() {
		return this.images;
	}

	/**
	 * setzt das Images Objekt mitdem Bilder aus Werten erstellt werden können auf den übergebenen Werten
	 * @param images : neues ImagesObjekt
	 */
	public void setImages(ApoSlitherLinkImages images) {
		this.images = images;
	}
	
	@Override
	public void setButtonFunction(String function) {
		this.curMouseFunction = function;
	}

	public abstract void mouseButtonReleased(int x, int y, boolean bRight);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void keyButtonReleased(int button, char character);
	
	@Override
	public void think(int delta) {
		if (this.curMouseFunction != null) {
			this.mouseButtonFunction(this.curMouseFunction);
			this.curMouseFunction = null;
			this.bReleasedMouse = false;
		} else if ((this.getReleasedKeys() != null) && (this.getReleasedKeys().size() > 0)) {
			try {
				while (this.getReleasedKeys().size() > 0) {
					this.keyButtonReleased(this.getReleasedKeys().get(0), this.charKeys.get(0));
					this.getReleasedKeys().remove(0);
					this.charKeys.remove(0);
				}
			} catch (Exception ex) {
			}
		} else if (this.bReleasedMouse) {
			this.bReleasedMouse = false;
			this.mouseButtonReleased(this.mouseX, this.mouseY, this.bRightMouse);
		}
	}

	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		this.bReleasedMouse = true;
	}
	
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		super.mouseMoved(e);
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			this.bRightMouse = true;
			this.oldMouseX = e.getX();
			this.oldMouseY = e.getY();
		} else {
			this.bRightMouse = false;
			super.mousePressed(e);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		if ((this.bRightMouse)) {
			int changeX = this.oldMouseX - e.getX();
			int changeY = this.oldMouseY - e.getY();
			this.oldMouseX = e.getX();
			this.oldMouseY = e.getY();
			if ( ( this.curWidth < this.levelWidth ) ) {
				this.curX += changeX;
				if ( this.curX + this.curWidth >= this.levelWidth ) {
						this.curX = this.levelWidth - this.curWidth;
				}
				if ( this.curX < 0 ) {
					this.curX = 0;
				}
				if ( !super.isBRepaint() )
					super.render();
			}
			if ( this.curHeight < this.levelHeight ) {
				this.curY += changeY;
				if ( this.curY + this.curHeight >= this.levelHeight ) {
						this.curY = this.levelHeight - this.curHeight;
				}
				if ( this.curY < 0 ) {
					this.curY = 0;
				}
				if ( !super.isBRepaint() )
					super.render();
			}
		}
	}
	
}
