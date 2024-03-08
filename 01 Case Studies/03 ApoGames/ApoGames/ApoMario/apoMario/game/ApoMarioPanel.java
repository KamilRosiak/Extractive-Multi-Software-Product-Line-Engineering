package apoMario.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;

import apoMario.ApoMarioComponent;
import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioPlayer;
import apoMario.game.panels.ApoMarioAnalysis;
import apoMario.game.panels.ApoMarioCredits;
import apoMario.game.panels.ApoMarioEditor;
import apoMario.game.panels.ApoMarioEditorIO;
import apoMario.game.panels.ApoMarioGame;
import apoMario.game.panels.ApoMarioMenu;
import apoMario.game.panels.ApoMarioModel;
import apoMario.game.panels.ApoMarioOptions;
import apoMario.game.panels.ApoMarioSimulation;

/**
 * Klasse, die sich um das eigentliche Spiel kümmert und alle Objekt überwacht, anspricht und steuert
 * @author Dirk Aporius
 *
 */
public class ApoMarioPanel extends ApoMarioComponent {

	private static final long serialVersionUID = 3446351722587948000L;
	
	private final boolean bMiniMap = false;
	
	private boolean bShow;
	
	private int curTime;
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private boolean bJump;
	
	private boolean bInit;
	
	private ArrayList<Long> middleCount;
	
	private ApoMarioMenu menu;
	
	private ApoMarioOptions options;
	
	private ApoMarioAnalysis analysis;
	
	private ApoMarioGame game;

	private ApoMarioEditor editor;

	private ApoMarioCredits credits;

	private ApoMarioSimulation simulation;
	
	private ApoMarioModel model;
	
	private ApoMarioButtons buttons;
	
	private BufferedImage iMenuBackground;
	
	private ApoMarioMiniMap miniMap;
	
	private int speedCounter;
	
	private boolean bSimulate;
	
	private boolean bEditor;
	
	private ApoMarioEditorIO editorIO;
	
	private boolean bMiddleWall;
	
	public ApoMarioPanel() {
		super();
	}
	
	public void init() {
		this.bInit = true;
		super.init();
		this.bJump = false;
		super.setBPause(false);
		this.curTime = 0;
		this.bShow = true;
		this.middleCount = new ArrayList<Long>();
		if (this.buttons == null) {
			this.buttons = new ApoMarioButtons(this);
			this.buttons.makeButtons();
		}
		if ((this.miniMap == null) && (this.bMiniMap)) {
			this.miniMap = new ApoMarioMiniMap(this.getLevel());
		}
		if (this.options == null) {
			this.options = new ApoMarioOptions(this);
		}
		if (this.menu == null) {
			this.menu = new ApoMarioMenu(this);
		}
		if (this.analysis == null) {
			this.analysis = new ApoMarioAnalysis(this);
		}
		if (this.game == null) {
			this.game = new ApoMarioGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoMarioEditor(this);
		}
		if (this.credits == null) {
			this.credits = new ApoMarioCredits(this);
		}
		if (this.simulation == null) {
			this.simulation = new ApoMarioSimulation(this);
		}
		if (this.editorIO == null) {
			this.editorIO = new ApoMarioEditorIO(this.getLevel());
		}
		this.setMenu();
		this.loadProperties();
		
		this.bInit = false;
		this.bSimulate = false;
		this.setBMiddle(false);
		this.speedCounter = 0;
	}
	
	public boolean isBMiddleWall() {
		return this.bMiddleWall;
	}

	public void setBMiddleWall(boolean middleWall) {
		this.bMiddleWall = middleWall;
		if (this.bMiddleWall) {
			this.middleCount.clear();
		}
	}

	private void loadProperties() {
		ApoMarioConstants.loadProperties();
		
		this.loadPropertiesPlayer(ApoMarioConstants.PLAYER_ONE, ApoMarioConstants.AI_LEFT);
		this.loadPropertiesPlayer(ApoMarioConstants.PLAYER_TWO, ApoMarioConstants.AI_RIGHT);
	}
	
	private void loadPropertiesPlayer(int player, String playerPath) {
		if ((playerPath == null) || (playerPath.length() <= 0)) {
			return;
		}
		int playerInt = -1;
		try {
			playerInt = Integer.parseInt(playerPath);
		} catch (NumberFormatException ex) {
			playerInt = -1;
		}
		if (playerInt >= 0) {
			if (player == ApoMarioConstants.PLAYER_ONE) {
				this.getLevel().setPlayerOneAI(playerInt);
				this.getLevel().changePlayerOneAI(0);
			} else if (player == ApoMarioConstants.PLAYER_TWO) {
				this.getLevel().setPlayerTwoAI(playerInt);
				this.getLevel().changePlayerTwoAI(0);
			}
		} else {
			this.getLevel().loadPlayer(player, playerPath);
		}
	}
	
	public void setDebugMode(boolean bDebug) {
		ApoMarioConstants.DEBUG = bDebug;
		if ((ApoMarioConstants.DEBUG) && (!ApoConstants.B_APPLET)) {
			if (this.getDebugFrame() == null) {
				this.setDebugFrame(new ApoMarioDebugFrame(this));
			} else {
				this.getDebugFrame().setVisible(true);
			}
		} else if ((!ApoMarioConstants.DEBUG) && (!ApoConstants.B_APPLET)) {
			if (this.getDebugFrame() != null) {
				this.getDebugFrame().setVisible(false);
			}
		}
		if (this.options != null) {
			this.options.setDebugChange(ApoMarioConstants.DEBUG);
		}
	}

	public int getSpeedCounter() {
		return this.speedCounter;
	}

	public void setSpeedCounter(int speedCounter) {
		this.speedCounter = speedCounter;
	}

	public boolean isBJump() {
		return this.bJump;
	}
	
	public void setBJump(boolean bJump) {
		this.bJump = bJump;
	}

	public void setMenu() {
		this.setBPause(false);
		if (this.options != null) {
			this.options.stopOptions();
		}
		if (this.model != null) {
			this.getLevel().setBReplay(false);
		}
		this.model = this.menu;
		this.menu.init();
		
		this.setButtons(ApoMarioConstants.BUTTON_MENU);
	}
	
	public void setCredits() {
		this.setBPause(false);
		this.model = this.credits;
		this.credits.init();
		
		this.setButtons(ApoMarioConstants.BUTTON_CREDITS);
	}
	
	public void setSimulation() {
		this.setBPause(false);
		this.model = this.simulation;
		this.simulation.init();
		
		this.setButtons(ApoMarioConstants.BUTTON_SIMULATION);
	}
	
	public ApoMarioMiniMap getMiniMap() {
		if (!this.bMiniMap) {
			return null;
		}
		return this.miniMap;
	}
	
	public boolean loadEditorLevel() {
		this.setBThink(false);
		if (super.getFileChooserEditor().showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = super.getFileChooserEditor().getSelectedFile().getPath();
			this.editorIO.readLevel(path);
			this.setBThink(true);
			
			return true;
		}
		this.setBThink(true);
		return false;
	}
	
	public void saveEditorLevel() {
		if (this.getFileChooserEditor().showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = this.getFileChooserEditor().getSelectedFile().getPath();
			if (path.indexOf(this.getFileFilterEditor().getLevelName()) == -1) {
				path = path + this.getFileFilterEditor().getLevelName();
			}
			this.editorIO.writeLevel(path);
		}
	}
	
	public void loadReplay() {
		if (super.getFileChooserReplay().showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = super.getFileChooserReplay().getSelectedFile().getPath();
			this.getLevel().getReplay().load(path);
			if (this.getLevel().getReplay().getLevelString() != null) {
				this.editorIO.readLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + this.getLevel().getReplay().getLevelString());
			}
			this.setReplay(this.getLevel().getReplay());
		}
	}
	
	public void setReplay(ApoMarioReplay replay) {
		this.getLevel().setReplay(replay.getClone());
		this.playReplay(true);
	}
	
	public void playReplay(boolean bReplay) {
		this.setGame(bReplay, false);	
	}
	
	public int getPlayerSelectCamera() {
		return this.game.getPlayerSelectCamera();
	}

	public BufferedImage getBackgroundImage() {
		BufferedImage iMenuBackground = new BufferedImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		boolean bOrg = ApoMarioConstants.DEBUG;
		ApoMarioConstants.DEBUG = false;
		Graphics2D g = (Graphics2D)iMenuBackground.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.getLevel().render(g, (int)this.getChangeX(), (int)this.getChangeY());
		ApoMarioConstants.DEBUG = bOrg;
		g.dispose();
		return iMenuBackground;
	}
	
	public boolean isBEditor() {
		return this.bEditor;
	}

	public void setBEditor(boolean editor) {
		this.bEditor = editor;
	}

	public void setGame(boolean bReplay) {
		this.setGame(bReplay, false);
	}
	
	public void setGame(boolean bReplay, boolean bEditor) {
		super.setBThink(false);
		super.setBRepaint(false);
		this.setBEditor(bEditor);
		this.setBPause(false);
		
		this.restartLevel(bReplay);
		
		this.setButtons(ApoMarioConstants.BUTTON_GAME);
		
		this.model = this.game;
		this.game.init();
		
		if (!bReplay) {
			this.getLevel().setNames();
		}
		
		super.setBThink(true);
		super.setBRepaint(true);
	}
	
	public void setEditor() {
		this.model = this.editor;
		this.editor.init();
		this.setBPause(false);
		
		this.setButtons(ApoMarioConstants.BUTTON_EDITOR);
	}
	
	public void setOptions() {
		this.model = this.options;
		this.setBPause(false);
		
		if (this.options != null) {
			this.options.setOptionsDrag();
			this.options.init();
		}
		
		this.setButtons(ApoMarioConstants.BUTTON_OPTIONS);
	}
	
	public void setAnalysis() {
		this.setBPause(false);
		this.model = this.analysis;
		this.analysis.init();
		
		this.setButtons(ApoMarioConstants.BUTTON_ANALYSIS);
		
		this.iMenuBackground = this.getBackgroundImageWithRect(false);
	}
	
	private BufferedImage getBackgroundImageWithRect(boolean bDown) {
		BufferedImage iMenuBackground = this.getBackgroundImage();
		Graphics2D g = (Graphics2D)iMenuBackground.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(254, 254, 254, 200));
		int width = iMenuBackground.getWidth() - 50 * ApoMarioConstants.SIZE;
		int height = iMenuBackground.getHeight() - 30 * ApoMarioConstants.SIZE;
		if (!bDown) {
			g.fillRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height, 40, 40);
		} else {
			g.fillRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height + 12 * ApoMarioConstants.SIZE, 40, 40);			
		}
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(2));
		if (!bDown) {
			g.drawRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height, 40, 40);
		} else {
			g.drawRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height + 12 * ApoMarioConstants.SIZE, 40, 40);			
		}
		g.dispose();
		return iMenuBackground;
	}
	
	private void setButtons(boolean[] visible) {
		for (int i = 0; i < visible.length && i < this.getButtons().length; i++) {
			this.getButtons()[i].setBVisible(visible[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
			this.getButtons()[2].setBVisible(false);
			this.getButtons()[3].setBVisible(false);
			this.getButtons()[20].setBVisible(false);
			this.getButtons()[22].setBVisible(false);
			this.getButtons()[23].setBVisible(false);
			this.getButtons()[37].setBVisible(false);
		}
	}
	
	public boolean isBSimulate() {
		return this.bSimulate;
	}

	public void setBSimulate(boolean bSimulate) {
		this.bSimulate = bSimulate;
	}

	@Override
	public void saveLevel(String s) {
		
	}
	
	@Override
	public void loadLevel(String s) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_F) {
			ApoMarioConstants.FPS = !ApoMarioConstants.FPS;
		} else if (button == KeyEvent.VK_B) {
			this.setDebugMode(!ApoMarioConstants.DEBUG);
		} else if (button == KeyEvent.VK_I) {
			ApoMarioConstants.ICARUS_BURN = !ApoMarioConstants.ICARUS_BURN;
		} else if (button == KeyEvent.VK_PLUS) {
			this.setBMiddleWall(!this.isBMiddleWall());
		}
		this.model.keyButtonReleased(button, character);
	}
	
	public void restartLevel() {
		this.restartLevel(false);
	}
	
	public void restartLevel(boolean bReplay) {
		this.getLevel().restart(bReplay);
		super.setBMiddle(true);
		super.setChangeX(0);
		this.middleCount.clear();
		if (this.bMiniMap) {
			this.miniMap.makeMiniMap();
		}
	}
	
	public void newLevel() {
		this.newLevel(-1, -1);
	}
	
	public void newLevel(int width, int difficulty) {
		this.getLevel().setBReplay(false);
		if (width < 0) {
			this.getLevel().newLevel();
		} else {
			this.getLevel().newSameLevel();
		}
		super.setBMiddle(true);
		super.setChangeX(0);
		this.middleCount.clear();
		if (this.bMiniMap) {
			this.miniMap.makeMiniMap();
		}
		this.game.setPlayerSelectCamera(ApoMarioGame.PLAYER_ONE);
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if (this.model != null) {
			this.model.mouseButtonReleased(e.getX(), e.getY());
		}
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);		
		boolean bRight = false;
		if (e.getButton() == MouseEvent.BUTTON3) {
			bRight = true;
		}
		if (this.model != null) {
			this.model.mousePressed(e.getX(), e.getY(), bRight);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (this.model != null) {
			this.model.mouseMoved(e.getX(), e.getY());
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (this.model != null) {
			this.model.mouseDragged(e.getX(), e.getY());
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if ((this.model != null) && (this.isBSimulate())) {
			this.simulation.keyButtonReleased(e.getKeyCode(), e.getKeyChar());
		} else {
			super.keyReleased(e);
		}
	}
	
	public ApoMarioOptions getOptions() {
		return this.options;
	}
	
	public BufferedImage getIMenuBackground() {
		return this.iMenuBackground;
	}

	public synchronized void think(int delta) {
		if ((this.bInit) || (this.isBSimulate())) {
			return;
		}
		this.middleCount.add(this.renderTime);
		if (this.middleCount.size() >= 100) {
			this.middleCount.remove(0);
			long middle = 0;
			for (int i = 0; i < this.middleCount.size(); i++) {
				middle += this.middleCount.get(i);
			}
			middle = middle / (long)this.middleCount.size();
			if (middle > 100000000l) {
				this.setBMiddle(false);
			}
			this.middleCount.clear();
		}
		if (this.isBPause()) {
			this.curTime += delta;
			if (this.curTime > ApoMarioReplayPlay.CHANGE_TIME) {
				this.curTime -= ApoMarioReplayPlay.CHANGE_TIME;
				this.bShow = !this.bShow;
			}
		}
		long t = System.nanoTime();
		super.think(delta);
		this.thinkLevelGame(delta);
		this.thinkTime = (int) (System.nanoTime() - t);
	}
	
	public synchronized void thinkLevelGame(int delta) {
		if (this.model != null) {
			this.model.think(delta);
		}
	}
	
	@Override
	public synchronized void render(Graphics2D g) {
		long t = System.nanoTime();
		
		Shape s = g.getClip();
		g.setClip(0, 0, ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT);
		
		try {
			if (this.model != null) {
				this.model.render(g);
			}
			if (!this.bSimulate) {
				if (this.model != null) {
					this.model.render(g);
				}
				if ((this.model != null) && ((this.model instanceof ApoMarioGame))) {
					if (this.bMiniMap) {
						this.miniMap.render(g);
					} else {
						this.renderLevelWidthStatistics(g);
					}
					this.renderPause(g);
				}
				this.renderFPS(g);
			}
			this.renderSimulate(g);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		g.setClip(s);
		this.renderTime = (int)(System.nanoTime() - t);
		//System.out.println(this.renderTime);
	}

	private void renderSimulate(Graphics2D g) {
		if (this.bSimulate) {
			g.setColor(Color.WHITE);
			int width = 200 * ApoMarioConstants.SIZE * ApoMarioConstants.APP_SIZE;
			int height = 50 * ApoMarioConstants.SIZE * ApoMarioConstants.APP_SIZE;
			int x = ApoMarioConstants.GAME_WIDTH/2 - width/2;
			int y = ApoMarioConstants.GAME_HEIGHT/2 - height/2;
			g.fillRoundRect(x, y, width, height, 15, 15);
			g.setColor(Color.BLACK);
			g.drawRoundRect(x, y, width, height, 15, 15);
			g.setFont(ApoMarioConstants.FONT_STATISTICS);
			String s = "Simulate Game ... please wait!";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, y + height/2);
			s = "Round: "+this.simulation.getCurRound()+" von "+this.simulation.getCount();
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, y + height/2 + g.getFontMetrics().getHeight() + 1 * 5);
		}
	}
	
	private void renderLevelWidthStatistics(Graphics2D g) {
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		int difWidth = 20;
		int levelWidth = ApoMarioConstants.GAME_WIDTH - difWidth * 2;
		float maxWidth = -1;
		for (int i = 0; i < this.getLevel().getPlayers().size(); i++) {
			ApoMarioPlayer player = this.getLevel().getPlayers().get(i);
			if (player.isBVisible()) {
				float marioWidth = (player.getX() * 100f / (this.getLevel().getWidth() * ApoMarioConstants.TILE_SIZE));
				if (maxWidth < marioWidth) {
					maxWidth = marioWidth;
				}
				if (i == 0) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLUE);
				}
				g.drawString("M", difWidth + levelWidth * marioWidth / 100, ApoMarioConstants.GAME_HEIGHT - 5);
			}
		}
		int distance = 15 * ApoMarioConstants.SIZE;
		for (int i = 0; i < levelWidth / distance; i++) {
			int width = i * distance;
			if ((float)width / (float)levelWidth < maxWidth / 100f) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.BLACK);
			}
			g.fillRect(difWidth + width, ApoMarioConstants.GAME_HEIGHT - 10, 5, 5);
		}
		String s = "F";
		int w = g.getFontMetrics().stringWidth(s);
		
		g.drawString(s, difWidth + levelWidth - w, ApoMarioConstants.GAME_HEIGHT - 5);
	}

	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (ApoMarioConstants.FPS) {
			g.setColor(Color.WHITE);
			g.setFont(ApoMarioConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5 + 1, ApoMarioConstants.GAME_HEIGHT - 45 + 1);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5 + 1, ApoMarioConstants.GAME_HEIGHT - 35 + 1);

			g.drawString("draw time: " + this.renderTime + " ns", 5 + 1, ApoMarioConstants.GAME_HEIGHT - 25 + 1);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5 + 1, ApoMarioConstants.GAME_HEIGHT - 15 + 1);
			g.drawString("FPS: " + super.getFPS(), 5 + 1, ApoMarioConstants.GAME_HEIGHT - 5 + 1);
			
			g.setColor(Color.BLACK);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoMarioConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoMarioConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoMarioConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoMarioConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoMarioConstants.GAME_HEIGHT - 5);
		}
	}
	
	private void renderPause(Graphics2D g) {
		if ((super.isBPause()) && (this.bShow)) {
			String s = "PAUSE";
			g.setFont(ApoMarioReplayPlay.FONT_REPLAY);
			int hei = 60 + 10 * ApoMarioConstants.SIZE;
			int w = g.getFontMetrics().stringWidth(s);
			g.setColor(Color.WHITE);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2 + 1, hei + 20 * ApoMarioConstants.SIZE + 1);
			g.setColor(Color.BLACK);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, hei + 20 * ApoMarioConstants.SIZE);
		}
	}
}