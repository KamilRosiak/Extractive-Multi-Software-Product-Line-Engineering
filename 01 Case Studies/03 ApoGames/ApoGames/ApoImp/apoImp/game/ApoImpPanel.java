package apoImp.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoImp.ApoImpComponent;
import apoImp.ApoImpConstants;
import apoImp.editor.ApoImpEditor;
import apoImp.game.background.ApoImpBackground;
import apoImp.game.level.ApoImpLevel;
import apoImp.game.level.ApoImpUserlevels;

public class ApoImpPanel extends ApoImpComponent {

	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoImpModel model;
	
	private ApoImpMenu menu;

	private ApoImpGame game;

	private ApoImpEditor editor;
	
	private ApoImpUserlevels userlevels;

	private float[] snow;
	
	private BufferedImage iBackground, iTree, iHouse, iHouseHit, iSled, iSnowman;
	
	private ApoImpBackground background;
	
	public ApoImpPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		super.setShowFPS(false);
		
		ApoImpButtons buttons = new ApoImpButtons(this);
		buttons.init();
		
		if (this.iTree == null) {
			this.iTree = this.getImages().getImage("images/tree.png", false);
		}
		if (this.iHouse == null) {
			this.iHouse = this.getImages().getImage("images/house.gif", false);
		}
		if (this.iHouseHit == null) {
			this.iHouseHit = this.getImages().getImage("images/house_hit.gif", false);
		}
		if (this.iSled == null) {
			this.iSled = this.getImages().getImage("images/sled.png", false);
		}
		if (this.iSnowman == null) {
			this.iSnowman = this.getImages().getImage("images/snowmen.png", false);
		}
		
		if (this.menu == null) {
			this.menu = new ApoImpMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoImpGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoImpEditor(this);
		}
		if (this.userlevels == null) {
			this.userlevels = new ApoImpUserlevels(this);
			this.loadUserlevels();
		}
		if (snow == null) {
			snow = new float[600];
			for (int i = 0; i < snow.length; i += 4) {
				snow[i] = (int)(Math.random() * 470 + 1);
				snow[i+1] = (int)(Math.random() * 560 + 1);;
				snow[i+2] = 0.004f + (float)(Math.random() * 0.004f);
				snow[i+3] = (float)(Math.random() * 0.1f) + 0.01f;
			}
		}
		
		this.makeNewBackground();
		
		this.setMenu();
	}
	
	public BufferedImage getSnowman() {
		return this.iSnowman;
	}

	public BufferedImage getTree() {
		return this.iTree;
	}

	public BufferedImage getHouse() {
		return this.iHouse;
	}

	public BufferedImage getHouseHit() {
		return this.iHouseHit;
	}

	public BufferedImage getSled() {
		return this.iSled;
	}

	public void makeNewBackground() {
		this.background = new ApoImpBackground(this);
		
		this.iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoImpConstants.GAME_WIDTH, ApoImpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = this.iBackground.createGraphics();
		g.setColor(ApoImpConstants.c2);
		g.fillRect(0, 0, this.iBackground.getWidth(), this.iBackground.getHeight());
		
		this.background.renderBackground(g);
		
		g.dispose();
	}
	
	public final void setMenu() {
		if (this.model != null) {
			this.model.close();
		}
		this.setShouldRepaint(true);
		
		this.model = this.menu;
		
		this.setButtonVisible(ApoImpConstants.BUTTON_MENU);
		this.setUserlevelsVisible();
		this.model.init();
		
		this.render();
	}
	
	public final void setGame(boolean bUserlevel, boolean bEditor, String level) {
		if (this.model != null) {
			this.model.close();
		}
		
		this.setShouldRepaint(true);
		
		this.model = this.game;
		
		this.setButtonVisible(ApoImpConstants.BUTTON_GAME);

		this.game.setUserlevel(bUserlevel);
		this.model.init();
				
		if (bEditor) {
			this.game.loadLevel(bEditor, level);
		}
	}
	
	public final void setEditor() {
		if (this.model != null) {
			this.model.close();
		}
		this.setShouldRepaint(true);
		
		this.model = this.editor;
		
		this.setButtonVisible(ApoImpConstants.BUTTON_EDITOR);
		
		this.model.init();
		
		this.render();
	}
	
	public final void setUserlevelsVisible() {
		if (this.model.equals(this.game)) {
			if (ApoImpLevel.editorLevels != null) {
				super.getButtons()[8].setBVisible(true);
			} else {
				super.getButtons()[8].setBVisible(false);
			}
		}
	}
	
	public final void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public ApoImpUserlevels getUserlevels() {
		return this.userlevels;
	}

	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoImpPanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}
	
	public void save() {
		this.editor.save();
	}
	
	public void createEmptyLevel(String l) {
		this.editor.createEmptyLevel(l);
	}

	@Override
	public void keyReleased(final int keyCode, final char keyChar) {
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
		}
		if (this.model != null) {
			this.model.keyButtonReleased(keyCode, keyChar);
		}
	}
	
	public void keyPressed(final int keyCode, final char keyCharacter) {
		if (this.model != null) {
			this.model.keyPressed(keyCode, keyCharacter);
		}
	}
	
	@Override
	public void setButtonFunction(final String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public boolean mouseReleased(final int x, final int y, final boolean left) {
		if (!super.mouseReleased(x, y, left)) {
			if (this.model != null) {
				this.model.mouseButtonReleased(x, y, !left);
			}
		}
		return false;
	}
	
	public void mousePressed(final int x, final int y, final boolean left) {
		super.mousePressed(x, y, left);		
		if (this.model != null) {
			this.model.mousePressed(x, y, !left);
		}
	}
	
	public void mouseMoved(final int x, final int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	public void mouseDragged(final int x, final int y, final boolean left) {
		super.mouseDragged(x, y, left);
		if (this.model != null) {
			this.model.mouseDragged(x, y);
		}
	}
	
	@Override
	public void mouseWheelChanged(final int changed) {
		if (this.model != null) {
			this.model.mouseWheelChanged(changed);
		}
	}
	
	public void think(final long delta) {
		long t = System.nanoTime();
		
		this.background.think((int)delta);
		
		for (int i = 0; i < snow.length; i += 4) {
			if ((snow[i+1] <= 0) || (snow[i+1] > 560)) {
				snow[i] = (int)(Math.random() * 470 + 1);
				snow[i+1] = 1;
				snow[i+2] = 0.004f + (float)(Math.random() * 0.004f);
				snow[i+3] = (float)(Math.random() * 0.1f) + 0.01f;
			} else {
				snow[i] += snow[i+2];
				snow[i+1] += snow[i+3];
			}
		}
		
		if (this.model != null) {
			this.model.think((int)delta);
		}
		this.thinkTime = (int) (System.nanoTime() - t);
	}

	@Override
	protected void render(final Graphics2D g) {
		long t = System.nanoTime();
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			this.background.renderHouseLightAndSled(g);
		}
		if (this.model != null) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.model.render(g);
		}
		super.renderButtons(g);
		if (this.model != null) {
			this.model.drawOverlay(g);
		}
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderSnow(final Graphics2D g) {
		g.setColor(new Color(230,230,230));
		for (int i = 0; i < snow.length; i+=4) {
			g.fillRect((int)(snow[i] - 1), (int)(snow[i + 1] - 1), 2, 2);
		}
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(final Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoImpConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoImpConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoImpConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoImpConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoImpConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoImpConstants.GAME_HEIGHT - 5);
		}
	}
	
	public void renderBackgroundInfo(Graphics2D g) {
		
	}

}
