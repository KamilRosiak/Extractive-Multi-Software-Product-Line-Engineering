package apoSimple.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntityMenu;

public class ApoSimpleTutorial extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "tutorial_menu";
	public static final String ENDLESS = "endless";
	public static final String PUZZLE = "puzzle";

	private BufferedImage iBackground;
	
	private boolean bEndless;
	
	private ApoSimpleTutorialEndless endless;
	
	private boolean bPuzzle;
	
	private ApoSimpleTutorialLevel level;
	
	public ApoSimpleTutorial(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/tutorial.png", false);
		}
		if (this.level == null) {
			this.level = new ApoSimpleTutorialLevel(this);
		}
		if (this.endless == null) {
			this.endless = new ApoSimpleTutorialEndless(this);
		}
		this.closeTutorial();
		
//		if (super.getMenuSheep().size() <= 0) {
//			int width = ApoSimpleConstants.ENTITY_WIDTH;
//			int minX = 225;
//			int minY = 0;
//			int maxWidth = ApoSimpleConstants.GAME_WIDTH - minX + width;
//			int maxHeight = 165 - minY - width;
//			for (int i = 10; i < maxWidth; i+= width + 40) {
//				for (int j = 10; j < maxHeight; j+= width + 10) {
//					super.getMenuSheep().add(new ApoSimpleEntityMenu(minX + i, minY + j, width, width, new Rectangle2D.Float(minX, minY, maxWidth, maxHeight)));					
//				}
//			}
//			
//			minX = 0;
//			minY = 325;
//			maxWidth = ApoSimpleConstants.GAME_WIDTH - minX - width;
//			maxHeight = ApoSimpleConstants.GAME_HEIGHT - minY - width;
//			for (int i = 10; i < maxWidth; i+= width + 30) {
//				for (int j = 10; j < maxHeight; j+= width + 10) {
//					super.getMenuSheep().add(new ApoSimpleEntityMenu(minX + i, minY + j, width, width, new Rectangle2D.Float(minX, minY, maxWidth, maxHeight)));					
//				}
//			}
//			
//			for (int i = 0; i < 1000; i++) {
//				super.thinkSheep(ApoSimpleConstants.WAIT_TIME_THINK);
//			}
//		}
	}
	
	public void closeTutorial() {
		this.bPuzzle = false;
		this.bEndless = false;
		this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_TUTORIAL);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (this.bPuzzle) {
			this.level.keyButtonReleased(button, character);
		} else if (this.bEndless) {
			this.endless.keyButtonReleased(button, character);
		} else if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.bPuzzle) {
			this.level.mouseButtonFunction(function);
		} else if (this.bEndless) {
			this.endless.mouseButtonFunction(function);
		} else {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			if (function.equals(ApoSimpleTutorial.MENU)) {
				this.getGame().setMenu();
			} else if (function.equals(ApoSimpleTutorial.PUZZLE)) {
				this.bPuzzle = true;
				this.level.init();
			} else if (function.equals(ApoSimpleTutorial.ENDLESS)) {
				this.bEndless = true;
				this.endless.init();
			}
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		if (this.bPuzzle) {
			this.level.mouseButtonReleased(x, y, right);
		} else if (this.bEndless) {
			this.endless.mouseButtonReleased(x, y, right);
		}
	}
	
	public boolean mouseMoved(int x, int y) {
		if (this.bPuzzle) {
			return this.level.mouseMoved(x, y);
		} else if (this.bEndless) {
			return this.endless.mouseMoved(x, y);
		}
		return false;
	}
	
	public boolean mouseDragged(int x, int y) {
		if (this.bPuzzle) {
			return this.level.mouseDragged(x, y);
		} else if (this.bEndless) {
			return this.endless.mouseDragged(x, y);
		}
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.bPuzzle) {
			return this.level.mousePressed(x, y, bRight);
		} else if (this.bEndless) {
			return this.endless.mousePressed(x, y, bRight);
		}
		return false;
	}

	@Override
	public void think(int delta) {
		if (this.bPuzzle) {
			this.level.think(delta);
		} else if (this.bEndless) {
			this.endless.think(delta);
		}
		super.thinkSheep(delta);
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.bPuzzle) {
			this.level.render(g);
		} else if (this.bEndless) {
			this.endless.render(g);
		} else {
			if (this.iBackground != null) {
				g.drawImage(this.iBackground, 0, 0, null);
			}
			super.renderSheep(g);
		}
	}
	
	public void drawOverlay(Graphics2D g) {
		if ((!this.bPuzzle) && (!this.bEndless)) {
			super.getGame().renderCoins(g, 0, 0);
		}
	}

}
