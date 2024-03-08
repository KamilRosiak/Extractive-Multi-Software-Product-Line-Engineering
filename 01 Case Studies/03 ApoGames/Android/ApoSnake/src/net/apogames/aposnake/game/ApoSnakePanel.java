package net.apogames.aposnake.game;

import android.content.SharedPreferences;

import net.apogames.aposnake.ApoSnake;
import net.apogames.aposnake.ApoSnakeButtons;
import net.apogames.aposnake.ApoSnakeComponent;
import net.apogames.aposnake.ApoSnakeConstants;
import net.apogames.aposnake.editor.ApoSnakeUserlevels;

import net.gliblybits.bitsengine.core.BitsFont;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.render.BitsGraphics;

public class ApoSnakePanel extends ApoSnakeComponent {
	
	private ApoSnakeMenu menu;
	
	private ApoSnakePuzzleChooser puzzle;

	private ApoSnakePuzzleGame game;
	
	private ApoSnakeEditor editor;
	
	private int think;
	
	private ApoSnakeUserlevels userlevels;
	
	public ApoSnakePanel(int id) {
		super(id);
	}
	
	public void init() {
		super.init();
		
		BitsGame.getIt().setClearColor(192f/255f, 192f/255f, 192f/255f, 1f);
		
		ApoSnakeButtons buttons = new ApoSnakeButtons(this);
		buttons.init();
		
		this.think = 0;
		
		if (this.menu == null) {
			this.menu = new ApoSnakeMenu(this);
		}
		if (this.puzzle == null) {
			this.puzzle = new ApoSnakePuzzleChooser(this);
		}
		if (this.game == null) {
			this.game = new ApoSnakePuzzleGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoSnakeEditor(this);
		}
		
		this.setMenu();
		
		if (this.userlevels == null) {
			this.userlevels = new ApoSnakeUserlevels(this);
			this.loadUserlevels();
		}
		this.puzzle.init();
		
		this.loadPreferences(ApoSnake.settings);
	}
	
	protected final void loadPreferences(SharedPreferences settings) {
		int solved = settings.getInt("solved", 0);
		this.solvedLevel(solved);
	}
	
	protected final void savePreferences(SharedPreferences settings) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("solved", this.getMaxCanChoosen());
		
		editor.commit();
	}
	
	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoSnakePanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}
	
	public void setUserlevelsVisible() {
		if (this.getModel().equals(this.menu)) {
			this.menu.setUserlevels();
		}
	}
	
	public final ApoSnakeUserlevels getUserlevels() {
		return this.userlevels;
	}

	protected final void setMenu() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.menu);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_MENU);
		
		super.getModel().init();
	}
	
	protected final void setEditor(boolean bSolvedLevel) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.editor);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_EDITOR);
		
		this.editor.setLevelSolved(bSolvedLevel);
		super.getModel().init();
	}
	
	protected final void setPuzzleChooser() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.puzzle);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_PUZZLE);
		
		super.getModel().init();
	}
	
	protected final void setPuzzleGame(final int level, final String levelString, final boolean bUserLevel) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.game);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_GAME);
		
		super.getModel().init();
		this.game.loadLevel(level, bUserLevel, levelString);
	}
	
	public final void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setVisible(bVisibile[i]);
		}
	}
	
	@Override
	public void setButtonFunction(final String function) {
		if (super.getModel() != null) {
			super.getModel().touchedButton(function);
		}
	}
	
	@Override
	public void onResume() {
		if (super.getModel() != null) {
			super.getModel().onResume();
		}
	}
	
	public void onBackButtonPressed() {
		if (super.getModel() != null) {
			super.getModel().onBackButtonPressed();
		}
	}
	
	public int getMaxCanChoosen() {
		return this.puzzle.getSolved();
	}
	
	public void solvedLevel(int level) {
		this.puzzle.setSolved(level);
	}
	
	public void onUpdate(float delta) {
		super.onUpdate(delta);
		
		this.think += delta * 1000;
		
		this.checkTouchInput();
		
		// Update / think
		// Wenn 10 ms vergangen sind, dann denke nach
		while (this.think >= 10) {
			this.think -= 10;
			if (super.getModel() != null) {
				super.getModel().think((int)10);
			}	
		}
	}

	@Override
	public void onDrawFrame(BitsGraphics g) {
		if (super.getModel() != null) {
			super.getModel().render(g);
		}
		super.renderButtons(g);
		if (super.getModel() != null) {
			super.getModel().drawOverlay(g);
		}
	}

	protected void drawString(BitsGraphics g, final String s, final int x, final int y, final BitsFont font) {
		this.drawString(g, s, x, y, font, new float[] {0, 0, 0, 1}, new float[] {1, 1, 1, 1});
	}
	
	protected void drawString(BitsGraphics g, final String s, final int x, final int y, final BitsFont font, float[] colorBack, float[] colorFront) {
		int w = 0;
		if (super.getModel().getStringWidth().containsKey(s)) {
			w = super.getModel().getStringWidth().get(s);
		}
		
		g.setColor(colorBack[0], colorBack[1], colorBack[2], colorBack[3]);
		g.drawText(s, font, x - w/2 + 1, y + 2);
		g.setColor(colorFront[0], colorFront[1], colorFront[2], colorFront[3]);
		g.drawText(s, font, x - w/2 + 0, y + 0);
	}
	
	public void renderButtons(BitsGraphics g) {
		this.renderButtons(g, ApoSnakeMenu.font);
	}
	
	public void renderButtons(BitsGraphics g, final BitsFont font) {
		if (this.getButtons() != null) {
			for (int i = 0; i < this.getButtons().length; i++) {
				if (this.getButtons()[i].isBVisible()) {
					int x = (int)(this.getButtons()[i].getX());
					int y = (int)(this.getButtons()[i].getY());
					int width = (int)(this.getButtons()[i].getWidth());
					int height = (int)(this.getButtons()[i].getHeight());
					
					g.setColor(160, 160, 160, 255);
					g.drawFilledRoundRect(x, y, width, height, 6, 10);
					g.setLineSize(2.0f);
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawRoundRect(x, y, width, height, 6, 10);
					g.setLineSize(1.0f);
					
					this.drawString(g, this.getButtons()[i].getFunction(), x + width/2, y + height/2 - font.mCharCellHeight/2 - 3, font);
				}
			}
		}
	}
}
