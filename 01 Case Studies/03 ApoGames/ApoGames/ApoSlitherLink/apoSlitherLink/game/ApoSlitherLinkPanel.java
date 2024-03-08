package apoSlitherLink.game;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHighscore;

import apoSlitherLink.ApoSlitherLinkComponent;
import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.ApoSlitherLinkImages;
import apoSlitherLink.level.ApoSlitherLinkLevelIO;
import apoSlitherLink.level.ApoSlitherLinkLevel;

public class ApoSlitherLinkPanel extends ApoSlitherLinkComponent {

	private static final long serialVersionUID = 3446351722587948000L;

	public static final String LOAD_STRING = "highscore.apo";
	
	private final Color COLOR_START = new Color(173, 228, 220);
	private final Color COLOR_MIDDLE = new Color(204, 236, 221);
	public static final Color COLOR_ABOVE_FIRST = new Color(0, 102, 0);
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoSlitherLinkButtons buttons;
	
	private ApoSlitherLinkModel model;
	
	private ApoSlitherLinkMenu menu;
	
	private ApoSlitherLinkTutorial options;
	
	private ApoSlitherLinkGame game;

	private ApoSlitherLinkEditor editor;
	
	private ApoSlitherLinkLevelChooser levelChooser;
	
	private ApoSlitherLinkLevel level;
	
	private BufferedImage iBackground;
	
	private ApoSlitherLinkLevelIO io;
	
	private ArrayList<ApoSlitherLinkCloud> clouds;
	
	private int cloudTime;
	
	public ApoSlitherLinkPanel() {
		super();
	}
	
	public void init() {
		super.init();
		super.setBFPS(false);
		
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D)(this.iBackground.getGraphics());
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Paint paint = g.getPaint();
			
			GradientPaint gp = new GradientPaint(0, 0, this.COLOR_START, 0, ApoSlitherLinkConstants.GAME_HEIGHT/2, this.COLOR_MIDDLE, true);
		    g.setPaint(gp);
		    g.fillRect(0, 0, ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT);
		    g.setPaint(paint);
		    
		    g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
		    g.fillRect(0, 0, ApoSlitherLinkConstants.GAME_WIDTH, 70);
		    g.setColor(new Color(204, 236, 182));
		    g.fillRect(0, 7, ApoSlitherLinkConstants.GAME_WIDTH, 7);
		    g.setColor(new Color(160, 220, 120));
		    g.fillRect(0, 57, ApoSlitherLinkConstants.GAME_WIDTH, 7);
		    g.setColor(new Color(190, 230, 164));
		    g.fillRect(0, 14, ApoSlitherLinkConstants.GAME_WIDTH, 43);
		    
			g.dispose();
		}
		
		if (this.level == null) {
			this.level = new ApoSlitherLinkLevel(this);
		}
		
		if (this.buttons == null) {
			this.buttons = new ApoSlitherLinkButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoSlitherLinkMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoSlitherLinkGame(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoSlitherLinkLevelChooser(this);
		}
		if (this.editor == null) {
			this.editor = new ApoSlitherLinkEditor(this);
		}
		if (this.io == null) {
			this.io = new ApoSlitherLinkLevelIO();
			this.loadLevels();
		}
		if (this.clouds == null) {
			this.clouds = new ArrayList<ApoSlitherLinkCloud>();
			this.cloudTime = 0;
		}
		if (this.options == null) {
			this.options = new ApoSlitherLinkTutorial(this);
		}
		this.setMenu();
	}
	
	public void loadLevels() {
		if (!ApoConstants.B_APPLET) {
			this.io.readLevel(ApoSlitherLinkPanel.LOAD_STRING);
		}
		this.io.checkLevelsIsIn(this.level.getLevels().getEasyLevels(), this.level);
		this.io.checkLevelsIsIn(this.level.getLevels().getMediumLevels(), this.level);
		this.io.checkLevelsIsIn(this.level.getLevels().getHardLevels(), this.level);
		this.io.addCustomsLevels(this.level.getLevels().getCustomLevels(), this.level);
		new Thread() {
			public void run() {
				ApoHighscore highscore = ApoHighscore.getInstance();
				highscore.load();
				for (int i = 0; i < highscore.getLevels().size(); i++) {
					ApoSlitherLinkPanel.this.level.getLevels().addCustomLevel(highscore.getLevels().get(i));
				}
			}
		}.start();
	}
	
	public void saveLevels() {
		this.io.saveLevels(this.level.getLevels().getEasyLevels());
		this.io.saveLevels(this.level.getLevels().getMediumLevels());
		this.io.saveLevels(this.level.getLevels().getHardLevels());
		this.io.saveLevels(this.level.getLevels().getCustomLevels());
		if (!ApoConstants.B_APPLET) {
			this.io.writeLevel(ApoSlitherLinkPanel.LOAD_STRING);
		}
	}
	
	public ApoSlitherLinkLevelIO getIO() {
		return this.io;
	}

	public BufferedImage getIBackground() {
		return this.iBackground;
	}

	public ApoSlitherLinkLevel getLevel() {
		return this.level;
	}

	public void setMenu() {
		this.model = this.menu;
		
		this.setButtonVisible(ApoSlitherLinkConstants.BUTTON_MENU);
	}
	
	public void setEditor(boolean bSolved) {
		this.model = this.editor;
		
		this.setButtonVisible(ApoSlitherLinkConstants.BUTTON_EDITOR);
		
		this.level.setCustomLevel();
		this.editor.init(bSolved);
	}
	
	public void setGame(boolean bEditor) {
		this.model = this.game;
		
		this.setButtonVisible(ApoSlitherLinkConstants.BUTTON_GAME);
		
		this.game.init(bEditor);
	}
	
	public void setLevelChooser() {
		this.model = this.levelChooser;
		
		this.setButtonVisible(ApoSlitherLinkConstants.BUTTON_LEVELCHOOSER);
	}
	
	public void setOptions() {
		this.model = this.options;
		
		this.setButtonVisible(ApoSlitherLinkConstants.BUTTON_OPTIONS);
		
		this.options.init();
	}
	
	private void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}
	
	public ApoSlitherLinkGame getGame() {
		return this.game;
	}

	public void startLevelChooser() {
		if (this.level.isEasyLevel()) {
			this.startEasyLevel();
		} else if (this.level.isMediumLevel()) {
			this.startMediumLevel();
		} else if (this.level.isHardLevel()) {
			this.startHardLevel();
		} else {
			this.startCustomLevel();
		}
	}
	
	/**
	 * Methode zum Starten des Spiels
	 */
	public void startLevel(int level) {
		this.setGame(false);
		
		this.level.setLevel(level);
	}
	
	public void testLevelFromEditor() {
		this.setGame(true);
	}
	
	/**
	 * Methode zum Starten des Spiels
	 */
	public void startEasyLevel() {
		this.setLevelChooser();
		
		this.level.setEasyLevels(this.level.getCurLevel());
		this.levelChooser.init();
	}
	
	public void startMediumLevel() {
		this.setLevelChooser();
		
		this.level.setMediumLevels(this.level.getCurLevel());
		this.levelChooser.init();
	}
	
	public void startHardLevel() {
		this.setLevelChooser();
		
		this.level.setHardLevels(this.level.getCurLevel());
		this.levelChooser.init();
	}
	
	public void startCustomLevel() {
		this.setLevelChooser();
		
		this.level.setCustomLevels(this.level.getCurLevel());
		this.levelChooser.init();
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_F) {
			super.setBFPS(!super.isBFPS());
		} else if (button == KeyEvent.VK_P) {
			this.level.setBPoints(!this.level.isBPoints());
		}
		if (this.model != null) {
			this.model.keyButtonReleased(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y, bRight);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		int x = e.getX();
		int y = e.getY();
		if (this.model != null) {
			this.model.mousePressed(x, y);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		int x = e.getX();
		int y = e.getY();
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	@Override
	public void loadLevel(String s) {
	}

	@Override
	public void saveLevel(String s) {
		
	}
	
	public void think(int delta) {
		long t = System.nanoTime();
		super.think(delta);
		this.thinkLevel(delta);
		this.thinkCloud(delta);
		if (this.model != null) {
			this.model.think(delta);
		}
		this.thinkTime = (int) (System.nanoTime() - t);
	}
	
	public void thinkLevel(int delta) {
		if (this.level != null) {
			this.level.think(delta);
		}
	}
	
	public ApoSlitherLinkModel getModel() {
		return this.model;
	}

	public void thinkCloud(int delta) {
		this.cloudTime -= delta;
		if (this.cloudTime <= 0) {
			this.cloudTime = 20000 + (int)(Math.random() * 25000);
			int cloud = (int)(Math.random() * ApoSlitherLinkImages.clouds.length + 1);
			if (cloud >= ApoSlitherLinkImages.clouds.length) {
				cloud = ApoSlitherLinkImages.clouds.length - 1;
			}
			int width = ApoSlitherLinkImages.clouds[cloud].getWidth();
			int height = ApoSlitherLinkImages.clouds[cloud].getHeight();
			int y = (int)(Math.random() * (ApoSlitherLinkConstants.GAME_HEIGHT - 90 - height)) + 90;
			int x = -width;
			float vecX = 0.0055f + (float)(Math.random() * 0.01);
			if (Math.random() * 100 > 50) {
				x = ApoSlitherLinkConstants.GAME_WIDTH;
				vecX = -0.0055f - (float)(Math.random() * 0.01);
			}
			this.clouds.add(new ApoSlitherLinkCloud(cloud, x, y, width, height, vecX));
		}
		for (int i = this.clouds.size() - 1; i >= 0; i--) {
			this.clouds.get(i).think(delta);
			if (this.clouds.get(i).shouldDelete()) {
				this.clouds.remove(i);
			}
		}
	}

	public final ArrayList<ApoSlitherLinkCloud> getClouds() {
		return this.clouds;
	}

	@Override
	public void render(Graphics2D g) {
		long t = System.nanoTime();
		g.setClip(0, 0, ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT);
		if (this.model != null) {
			this.model.render(g);
		}
		super.renderButtons(g);
		if (this.model != null) {
			this.model.renderAfter(g);
		}
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderClouds(Graphics2D g) {
		for (int i = this.clouds.size() - 1; i >= 0; i--) {
			this.clouds.get(i).render(g, 0, 0);
		}
	}
	
	public void renderBackground(Graphics2D g) {
		this.renderBackground(g, 0, 0);
	}
	
	public void renderBackground(Graphics2D g, int changeX, int changeY) {
		g.drawImage(this.iBackground, changeX, changeY, null);
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isBFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoSlitherLinkConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoSlitherLinkConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoSlitherLinkConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoSlitherLinkConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoSlitherLinkConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoSlitherLinkConstants.GAME_HEIGHT - 5);
		}
	}

}
