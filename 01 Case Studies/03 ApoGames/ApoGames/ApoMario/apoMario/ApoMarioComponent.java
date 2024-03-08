package apoMario;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoComponentBufferedStrategy;
import org.apogames.ApoConstants;
import org.apogames.help.ApoFileFilter;
import org.apogames.image.ApoImageScale;
import org.apogames.sound.ApoSounds;

import apoMario.game.ApoMarioDebugFrame;
import apoMario.level.ApoMarioLevel;

/**
 * Abstrakte Klasse von der das eigentliche Spiel erbt und grundlegende Funktionalitäten
 * dem Spielobjekt bereitstellt
 * @author Dirk Aporius
 *
 */
public abstract class ApoMarioComponent extends ApoComponentBufferedStrategy {

	public static final int SHIFT_DOWN = 0;
	public static final int CTRL_DOWN = 1;
	public static final int ALT_DOWN = 2;
	public static final int ALT_GR_DOWN = 3;
	private final int MAX_DOWN = ApoMarioComponent.ALT_GR_DOWN + 1;
	
	private static final long serialVersionUID = 1L;

	/** Angabe, wo die Levels für das Applet liegen */
	public static final String APPLET_LEVEL = "http://apo-games.de/apoMario/";
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private final ApoFileFilter	fileFilter = new ApoFileFilter("class");
	/** A FileChooser */
	private JFileChooser fileChooserReplay;
	/** A Class file filter */
	private final ApoFileFilter	fileFilterReplay = new ApoFileFilter("rep");
	/** A FileChooser */
	private JFileChooser fileChooserEditor;
	/** A Class file filter */
	private final ApoFileFilter	fileFilterEditor = new ApoFileFilter("mar");
	
	private String curMouseFunction;
	
	/** gibt an, ob die rechts Maustaste gedrueckt wird oder nicht */
	private boolean bRightMouse;
	
	private ArrayList<Integer> releasedKeys;
	
	private ArrayList<Character> charKeys;
	
	private boolean bReleasedMouse;

	private boolean bPause;
	
	private boolean[] keys;
	
	private int curX, curY, curWidth, curHeight;
	private float changeX, changeY;
	private int levelWidth, levelHeight;
	/** alte x-Koordinate der Maus */
	private int oldMouseX, mouseX;
	/** alte y-Koordinate der Maus */
	private int oldMouseY, mouseY;
	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoMarioImage images;
	
	private boolean bTwoPlayers;
	
	private int time;
	
    private ApoSounds sounds;
    
    private boolean bSound;
	
    private ApoMarioLevel level;
    
    private ApoImageScale scale;
    
	private boolean bMiddle;
	
	private ApoMarioDebugFrame debugFrame;
	
	private int speed;
	
	private boolean[] down;
    
	public ApoMarioComponent() {
		super(true, true, ApoMarioConstants.FPS_THINK, ApoMarioConstants.FPS_RENDER, ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, ApoMarioConstants.BUFFER_STRATEGY);
	}

	@Override
	public void init() {
		if ((!ApoConstants.B_APPLET) && (this.fileChooser == null)) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator));
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		if ((!ApoConstants.B_APPLET) && (this.fileChooserReplay == null)) {
			this.fileChooserReplay = new JFileChooser();
			this.fileChooserReplay.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "replay" + File.separator));
			this.fileChooserReplay.setFileFilter(this.fileFilterReplay);
		}
		if ((!ApoConstants.B_APPLET) && (this.fileChooserEditor == null)) {
			this.fileChooserEditor = new JFileChooser();
			this.fileChooserEditor.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "levels" + File.separator));
			this.fileChooserEditor.setFileFilter(this.fileFilterEditor);
		}
		if (this.down == null) {
			this.down = new boolean[this.MAX_DOWN];
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
		
		this.bPause = false;
		
		this.speed = ApoMarioConstants.TIMES_NORMAL;
		
		this.levelWidth = this.curWidth;
		this.levelHeight = this.curHeight;
		
		this.scale = new ApoImageScale("");
		
		if (this.images == null) {
			this.images = new ApoMarioImage();
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
		
		ApoMarioImageContainer.init(this);
		
		this.level = new ApoMarioLevel(this);
		
		this.keys = new boolean[256];
		this.releasedKeys = new ArrayList<Integer>();
		this.charKeys = new ArrayList<Character>();
	}
	
	public ApoFileFilter getFileFilterEditor() {
		return this.fileFilterEditor;
	}

	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}

	public JFileChooser getFileChooserReplay() {
		return this.fileChooserReplay;
	}
	
	public JFileChooser getFileChooserEditor() {
		return this.fileChooserEditor;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void addSpeed(int plus) {
		this.speed += plus;
		if (this.speed > ApoMarioConstants.TIMES_FASTER_10) {
			this.speed = ApoMarioConstants.TIMES_SLOWER_50;
		} else if (this.speed < ApoMarioConstants.TIMES_SLOWER_50) {
			this.speed = ApoMarioConstants.TIMES_FASTER_10;
		}
	}
	
	public ApoMarioDebugFrame getDebugFrame() {
		return this.debugFrame;
	}

	protected void setDebugFrame(ApoMarioDebugFrame debugFrame) {
		this.debugFrame = debugFrame;
	}

	public boolean isBPause() {
		return this.bPause;
	}

	public void setBPause(boolean bPause) {
		this.bPause = bPause;
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

	public BufferedImage getResizeImage(BufferedImage orgImage) {
		if (ApoMarioConstants.APP_SIZE == 2) {
			return this.scale.getDoubleScaledImage(orgImage);
		} else if (ApoMarioConstants.APP_SIZE == 3) {
			return this.scale.getTrippleScaledImage(orgImage);
		}
		return orgImage;
	}
	
	public ApoMarioLevel getLevel() {
		return this.level;
	}

	public void setLevel(ApoMarioLevel level) {
		this.level = level;
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
	
	public synchronized void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		int code = e.getKeyCode();
		this.releasedKeys.add(code);
		if (code < this.keys.length) {
			this.keys[code] = false;
		}
		this.charKeys.add(e.getKeyChar());
		if (!e.isAltDown()) {
			this.down[ApoMarioComponent.ALT_DOWN] = false;
		}
		if (!e.isAltGraphDown()) {
			this.down[ApoMarioComponent.ALT_GR_DOWN] = false;
		}
		if (!e.isControlDown()) {
			this.down[ApoMarioComponent.CTRL_DOWN] = false;
		}
		if (!e.isShiftDown()) {
			this.down[ApoMarioComponent.SHIFT_DOWN] = false;
		}
	}

	public synchronized void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int code = e.getKeyCode();
		if (code < this.keys.length) {
			this.keys[code] = true;
		}
		if (e.isAltDown()) {
			this.down[ApoMarioComponent.ALT_DOWN] = true;
		}
		if (e.isAltGraphDown()) {
			this.down[ApoMarioComponent.ALT_GR_DOWN] = true;
		}
		if (e.isControlDown()) {
			this.down[ApoMarioComponent.CTRL_DOWN] = true;
		}
		if (e.isShiftDown()) {
			this.down[ApoMarioComponent.SHIFT_DOWN] = true;
		}
	}	

	public boolean[] getDown() {
		return this.down;
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
	public ApoMarioImage getImages() {
		return this.images;
	}

	/**
	 * setzt das Images Objekt mitdem Bilder aus Werten erstellt werden können auf den übergebenen Werten
	 * @param images : neues ImagesObjekt
	 */
	public void setImages(ApoMarioImage images) {
		this.images = images;
	}
	
	@Override
	public void setButtonFunction(String function) {
		this.curMouseFunction = function;
	}

	public abstract void mouseButtonReleased(int x, int y);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void keyButtonReleased(int button, char character);
	
	@Override
	public synchronized void think(int delta) {
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
			this.mouseButtonReleased(this.mouseX, this.mouseY);
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
