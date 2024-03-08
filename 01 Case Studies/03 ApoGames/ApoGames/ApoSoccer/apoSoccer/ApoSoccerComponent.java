package apoSoccer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoComponentBufferedStrategy;

/**
 * Klasse, die das Grundgerüst für das Spielfeld bildet
 * @author Dirk Aporius
 *
 */
public abstract class ApoSoccerComponent extends ApoComponentBufferedStrategy {

	/** */
	private static final long serialVersionUID = 1L;

	/** gibt an, ob die rechts Maustaste gedrueckt wird oder nicht */
	private boolean bRightMouse;
	
	private ArrayList<Integer> releasedKeys;
	
	private boolean[] keys;
	
	private int curX, curY, curWidth, curHeight;
	private int levelWidth, levelHeight;
	/** alte x-Koordinate der Maus */
	private int oldMouseX;
	/** alte y-Koordinate der Maus */
	private int oldMouseY;
	/** das eigentliche Spielfeldbild */
	private BufferedImage iBackground;
	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoSoccerImages images;
	/** boolean Variable, die angibt, ob das Spiel läuft oder nicht */
	private boolean bStartGame;
	/** boolean Variable, die angibt, ob die Analyse gerade angezeigt wird */
	private boolean bAnalysis;
	/** boolean Variable, die angibt, ob das Menu gerade angezeigt wird */
	private boolean bMenu;
	/** boolean Variable, die angibt, ob die Spieler auf ihre Positionen laufen */
	private boolean bRunPlayer;
	/** boolean Variable, die angibt, ob das Replayzeichen gerade angezeigt wird */
	private boolean bReplayShow;
	/** boolean Variable, die angibt, ob der Sound abgespielt werden soll */
	private boolean bSound;
	/** Variable die die Zeit für die Ausgabe speichert */
	private long time;
	/** Variable die die Zeit für das Replay speichert */
	private long replayTime;
	/** boolean Variable, die angibt, ob das SPiel gerade gestoppt wird */
	private boolean bCurStop;
	
	public ApoSoccerComponent(boolean bMouse, boolean bKey, int wait_time_think, int wait_time_render) {
		super(bMouse, bKey, wait_time_think, wait_time_render);
	}

	@Override
	public void init() {
		this.curX = 0;
		this.curY = 0;
		this.curWidth = super.getWidth();
		this.curHeight = super.getHeight();
		this.oldMouseX = -1;
		this.oldMouseY = -1;
		
		this.levelWidth = this.curWidth;
		this.levelHeight = this.curHeight;
		
		this.images = new ApoSoccerImages();
		
		this.bMenu = true;
		this.bStartGame = false;
		this.bSound = false;
		this.bAnalysis = false;
		this.bRunPlayer = false;
		this.bReplayShow = false;
		this.time = 0;
		this.replayTime = 0;
		this.setBCurStop(false);
		
		this.keys = new boolean[256];
		this.releasedKeys = new ArrayList<Integer>();
	}
	
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_Q) {
			ApoSoccerConstants.B_STOP_SHOOT = !ApoSoccerConstants.B_STOP_SHOOT;
		}
		this.releasedKeys.add(code);
		this.keys[code] = false;
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int code = e.getKeyCode();
		this.keys[code] = true;
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
	 * gibt die Zeit in Millisekunden zurück
	 * @return gibt die Zeit in Millisekunden zurück
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * setzt die Zeit in Millisekunden, auf den übergebenen Wert
	 * @param time : neue Zeit in Millisekunden
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * gibt die Zeit in 90 Minuten umgerechnet zurück
	 * @return gibt die Zeit in 90 Minuten umgerechnet zurück
	 */
	public int getTimeForGraphic() {
		return (int)(90 * time / ApoSoccerConstants.TIME);
	}

	/**
	 * gibt zurück, ob die Analyse gerade angezeigt wird oder nicht
	 * @return TRUE, die Analyse wird gerade angezeigt sonst FALSE
	 */
	public boolean isBAnalysis() {
		return this.bAnalysis;
	}

	/**
	 * setzt die boolean Variable ob die Analyse gerade angezeigt wird auf den übergebenen Wert
	 * @param bAnalysis : TRUE, die Analyse wird gerade angezeigt sonst FALSE
	 */
	public void setBAnalysis(boolean bAnalysis) {
		this.bAnalysis = bAnalysis;
	}
	
	/**
	 * gibt zurück, ob der Sound abgespielt wird oder nicht
	 * @return TRUE, der Sound wird abgespielt, sonst FALSE
	 */
	public boolean isBSound() {
		return this.bSound;
	}

	/**
	 * setzt die boolean Variable, ob der Sound abgespielt werden soll auf den übergebenen Wert
	 * @param bAnalysis : TRUE, der Sound wird abgespielt, sonst FALSE
	 */
	public void setBSound(boolean bSound) {
		this.bSound = bSound;
	}
	
	/**
	 * gibt zurück, ob das Menu gerade angezeigt wird oder nicht
	 * @return TRUE, das menu wird gerade angezeigt sonst FALSE
	 */
	public boolean isBMenu() {
		return this.bMenu;
	}

	/**
	 * setzt die boolean Variable ob das Menu gerade angezeigt wird auf den übergebenen Wert
	 * @param bMenu : TRUE, das Menu wird gerade angezeigt sonst FALSE
	 */
	public void setBMenu(boolean bMenu) {
		this.bMenu = bMenu;
	}

	/**
	 * gibt zurück, ob das Spiel gerade gestartet ist oder nicht
	 * @return TRUE, das Spiel ist gestartet sonst FALSE
	 */
	public boolean isBStartGame() {
		return this.bStartGame;
	}

	/**
	 * setzt die boolean Variable, ob das Spiel gestartet ist, auf den übergeben Wert
	 * @param bStartGame : TRUE, das Spiel ist gestartet sonst FALSE
	 */
	public void setBStartGame(boolean bStartGame) {
		this.bStartGame = bStartGame;
	}

	/**
	 * gibt zurück, ob die Spieler gerade auf ihre Positionen laufen
	 * @return TRUE, Spieler laufen auf ihre Positionen, sonst FALSE
	 */
	public boolean isBRunPlayer() {
		return this.bRunPlayer;
	}

	/**
	 * setzt die boolean Variable, ob die Spieler auf ihre Positionen laufen, auf den übergebenen Wert
	 * @param bRunPlayer : TRUE, Spieler laufen auf ihre Positionen, sonst FALSE
	 */
	public void setBRunPlayer(boolean bRunPlayer) {
		this.bRunPlayer = bRunPlayer;
	}

	/**
	 * gibt zurück, ob das Replay gerade angezeigt werden soll
	 * @return TRUE, Replaybild soll angezeigt werden, sonst FALSE
	 */
	public boolean isBReplayShow() {
		return this.bReplayShow;
	}

	/**
	 * setzt die boolean Variable, ob das Replay gerade angezeigt werden soll, auf den übergebenen Wert
	 * @param bRunPlayer : TRUE, Replaybild sll angezeigt werden, sonst FALSE
	 */
	public void setBReplayShow(boolean bReplayShow) {
		this.bReplayShow = bReplayShow;
	}
	
	/**
	 * gibt die ReplayTime wieder
	 * @return gibt die ReplayTime wieder
	 */
	public long getReplayTime() {
		return this.replayTime;
	}

	/**
	 * setzt die ReplayTime auf den übergebenen Wert und überprüft gleichzeitig ob
	 * diese größer als die zulässige ist und schaltet dann bReplayShow um
	 * @param replayTime : setzt die ReplayTime auf den übergebenen Wert
	 */
	public void setReplayTime(long replayTime) {
		this.replayTime = replayTime;
		if (this.replayTime >= ApoSoccerConstants.REPLAY_TIME) {
			this.replayTime -= ApoSoccerConstants.REPLAY_TIME;
			this.setBReplayShow(!this.isBReplayShow());
		}
	}

	/**
	 * gibt das Hintergrundbild zurück
	 * @return gibt das Hintergrundbild zurück
	 */
	public BufferedImage getIBackground() {
		return this.iBackground;
	}

	/**
	 * setzt das Hintergrundbild auf den übergebenen Wert
	 * @param iBackground : das neue Hintergrundbild
	 */
	public void setIBackground(BufferedImage iBackground) {
		this.iBackground = iBackground;
	}

	/**
	 * gibt das Images Objekt mitdem Bilder aus Werten erstellt werden können zurück
	 * @return gibt das Images Objekt mitdem Bilder aus Werten erstellt werden können zurück
	 */
	public ApoSoccerImages getImages() {
		return this.images;
	}

	/**
	 * setzt das Images Objekt mitdem Bilder aus Werten erstellt werden können auf den übergebenen Werten
	 * @param images : neues ImagesObjekt
	 */
	public void setImages(ApoSoccerImages images) {
		this.images = images;
	}

	public boolean isBCurStop() {
		return this.bCurStop;
	}

	public void setBCurStop(boolean bCurStop) {
		this.bCurStop = bCurStop;
	}

	public void mousePressed(MouseEvent e) {
		if ( e.getButton() == MouseEvent.BUTTON3 ) {
			this.bRightMouse = true;
			this.oldMouseX = e.getX();
			this.oldMouseY = e.getY();
		} else {
			super.mousePressed( e );
		}
		if (this.isBCurStop()) {
			this.setBCurStop(false);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if ( ( this.bRightMouse ) ) {
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
					super.repaint();
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
					super.repaint();
			}
		}
	}
	
}
