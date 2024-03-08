package apoSimple.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoGameCounter;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleDrive;
import apoSimple.entity.ApoSimpleEntity;
import apoSimple.entity.ApoSimpleString;
import apoSimple.game.level.ApoSimpleLevel;
import apoSimple.game.level.ApoSimpleLevelSolution;
import apoSimple.game.level.ApoSimpleLevels;

public class ApoSimpleLevelGame extends ApoSimpleGame {

	public static final int USERLEVELS = -2;
	public static final int TUTORIAL = -3;
	
	private boolean bEditor;
	
	private ApoSimpleLevel level;
	
	private int stars;
	
	private int levelInt;
	
	private ArrayList<Point> solutionsPoints;
	
	public ApoSimpleLevelGame(ApoSimplePanel game) {
		super(game);
	}
	
	public void makeTextField() {
	}
	
	public void init() {
		super.makeBackground();
		super.makePreview();
		super.setDriver(null);
		super.bHandCursor = false;
		
		super.makeButtonVisible();
	}

	public void makeLevel(final ApoSimpleLevel level, final boolean bEditor, final int levelInt) {
		if (level == null) {
			return;
		}
		this.level = level;
		this.bEditor = bEditor;
		
		super.resetValues();
		this.bHandCursor = false;
		this.setPoints(0);
		super.setMoves(0);
		
		if (levelInt > 0) {
			String name = "level_"+levelInt;
			if (this.getGame().getTextField() != null) {
				if (this.getGame().getTextField().getCurString() != null) {
					name += "_"+this.getGame().getTextField().getCurString();
				}
			}
			ApoGameCounter.getInstance().save(name);
		}
		this.levelInt = levelInt;
		int width = ApoSimpleConstants.ENTITY_WIDTH;		
		for (int y = 0; y < this.getEntities().length; y++) {
			for (int x = 0; x < this.getEntities()[0].length; x++) {
				int direction = this.level.getLevel()[y][x];
				while (direction >= 50) {
					direction -= 50;
				}
				int color = ApoSimpleConstants.EMPTY;
				if (this.level.getLevel()[y][x] >= 50) {
					color = super.getRandomColor();
				}
				if (direction != ApoSimpleConstants.REAL_EMPTY) {
					this.getEntities()[y][x] = new ApoSimpleEntity(10 + x * (width + 5), 10 + y * (width + 5), width, width, color, direction);
				} else {
					this.getEntities()[y][x] = new ApoSimpleEntity(10 + x * (width + 5), 10 + y * (width + 5), width, width, color, ApoSimpleConstants.UP);
					this.getEntities()[y][x].setBVisible(false);
				}
			}
		}
		super.setLevel(levelInt);
		super.makeBackgroundWithFence();
		super.makeRec();
		super.setbHelp(false);
		super.setbGoodie(false);
		
		super.getGame().getButtons()[40].setBVisible(true);
		
		if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
			for (int i = 67; i < 71; i++) {
				super.getGame().getButtons()[i].setBVisible(true);		
			}
		}
		
		if (this.solutionsPoints == null) {
			this.solutionsPoints = new ArrayList<Point>();
		}
		this.solutionsPoints.clear();
		if (!bEditor) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					ApoSimpleLevelGame.this.solutionsPoints = ApoSimpleLevelSolution.getMinSolution(ApoSimpleLevelGame.this.level.makeString(), 4);
				}
	 		});
	 		t.start();
			
		}
	}
	
	public ApoSimpleLevel getCurLevel() {
		return this.level;
	}
	
	public void showButtons() {
	}
	
	public int getCost() {
		if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
			return (5 * this.solutionsPoints.size());
		} else if (this.bEditor) {
			return 0;
		} else {
			if (this.levelInt < 10) {
				return 25;	
			}
			return 200 + ((this.levelInt - 10) * 15);
		}
	}

	public void mouseButtonFunction(final String function) {
		if (function.equals(ApoSimpleGame.NEW)) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.makeLevel(this.level, this.bEditor, super.getLevel());
			return;
		} else if (function.equals(ApoSimpleGame.RELOAD)) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.makeNewLevel();
			return;
		} else if (function.equals(ApoSimpleGame.COINS)) {
			if (super.getDriver() == null) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				this.setCoinsCost(this.getCost());
			}
			return;
		} else if (function.equals(ApoSimpleGame.LEVEL_LEFT)) {
			this.nextUserlevel(-1);
		} else if (function.equals(ApoSimpleGame.LEVEL_RIGHT)) {
			this.nextUserlevel(+1);
		} else if (function.equals(ApoSimpleGame.SORTED_RIGHT)) {
			this.getGame().getUserlevels().nextCurMode(+1);
			this.getGame().getUserlevels().setCurLevel(0);
			this.makeNewLevel();
		} else if (function.equals(ApoSimpleGame.SORTED_LEFT)) {
			this.getGame().getUserlevels().nextCurMode(-1);
			this.getGame().getUserlevels().setCurLevel(0);
			this.makeNewLevel();
		}
		if (this.bEditor) {
			if ((function.equals(ApoSimpleGame.MENU)) || (function.equals(ApoSimpleGame.MENU_SCORE))) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				this.getGame().setEditor();
				if (this.stars == 3) {
					this.getGame().changeChecked(true);	
				}
				return;
			} else if (function.equals(ApoSimpleGame.NEXT)) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				this.getGame().setEditor();
				if (this.stars == 3) {
					this.getGame().changeChecked(true);
				}
				return;
			}
		} else {
			if ((function.equals(ApoSimpleGame.MENU)) || (function.equals(ApoSimpleGame.MENU_SCORE))) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
					this.getGame().setPuzzle();
				} else {
					this.getGame().setLevelChooser();
				}
				return;
			} else if (function.equals(ApoSimpleGame.NEXT)) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
					this.nextUserlevel(+1);
				} else {
					this.nextLevel(+1, false);
				}
				return;
			} else if (function.equals(ApoSimpleGame.RESTART)) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				this.makeNewLevel();
				return;
			}
		}
		super.mouseButtonFunction(function);
	}
	
	public void makeNewLevel() {
		if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
			this.getGame().setLevel(this.getGame().getUserlevels().getCurrentLevel(), false, this.levelInt);
		} else if (this.levelInt == ApoSimpleLevelGame.TUTORIAL) {
			this.getGame().setLevel(this.level, false, this.levelInt);
		} else {
			int levelInt = super.getLevel();
			ApoSimpleLevel level = ApoSimpleLevels.getLevel(levelInt);
			this.getGame().setLevel(level, false, levelInt);
		}
	}
	
	public void setCoinsCost(int cost) {
		if (this.isbCoinShow()) {
			if (ApoSimpleConstants.REGION.equals("de")) {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Tipp wird schon angezeigt!", true, 2000, false));				
			} else {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Tipp is visible!", true, 2000, false));
			}
			return;
		}
		if (cost == 0) {
			ApoSimpleLevelGame.this.solutionsPoints = ApoSimpleLevelSolution.getMinSolution(ApoSimpleLevelGame.this.level.makeString(), 4);
		}
		if ((cost <= this.getGame().getCoins()) && (this.solutionsPoints.size() > 0)) {
			this.getGame().setCoins(this.getGame().getCoins() - cost, true);
			
			for (int i = 0; i < this.solutionsPoints.size(); i++) {
				this.getEntities()[this.solutionsPoints.get(i).y][this.solutionsPoints.get(i).x].setbGoodie((i + 1));
			}
			this.setbCoinShow(true);
		} else {
			if (ApoSimpleConstants.REGION.equals("de")) {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Nicht genug Münzen vorhanden!", true, 2000, false));				
			} else {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Not enough coins!", true, 2000, false));
			}
		}
	}
	
	public void startDriver(int x, int y) {
		this.setbCoinShow(false);
		super.getGame().playSound(ApoSimplePanel.SOUND_START, 100);
		this.setDriver(new ApoSimpleDrive(this.getEntities()[y][x].getX(), this.getEntities()[y][x].getY(), this.getEntities()[y][x].getWidth(), this.getEntities()[y][x].getWidth(), this.getEntities()[y][x].getType(), this.getEntities()[y][x].getDirection(), this.getEntities()[y][x].isBBlack()));
		this.getEntities()[y][x].setBVisible(false);
		if (!this.getEntities()[y][x].isBVisible()) {
			this.getEntities()[y][x].setBBlack(false);
		}
		for (y = 0; y < this.getEntities().length; y++) {
			for (x = 0; x < this.getEntities()[0].length; x++) {
				this.getEntities()[y][x].setbGoodie(-1);
				this.getEntities()[y][x].setBOver(false);
			}
		}
		this.setMoves(this.getMoves() + 1);
		
		if (this.getMoves() > 0) {
			this.getGame().getButtons()[57].setBVisible(false);
		}
	}
	
	@Override
	public void mouseButtonReleased(int mouseX, int mouseY, boolean bRight) {
		if (this.bEditor) {
			if (this.getGame().isBLoose()) {
				this.getGame().setEditor();
				return;
			}
		}
		super.mouseButtonReleased(mouseX, mouseY, bRight);
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (super.getGame().isBLoose()) {
			if ((button == KeyEvent.VK_ENTER) && (!this.bEditor)) {
				if (this.stars <= 0) {
					this.makeNewLevel();
				} else {
					this.nextLevel(1, false);
				}
				return;
			}
		} else {
			if (!this.bEditor) {
				if (button == KeyEvent.VK_N) {
					if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
						this.nextUserlevel(+1);
					} else {
						this.nextLevel(+1, true);
					}
					return;
				} else if (button == KeyEvent.VK_P) {
					if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
						this.nextUserlevel(-1);
					} else {
						this.nextLevel(-1, true);
					}
					return;
				}
			}
			if (button == KeyEvent.VK_S) {
				ArrayList<Point> solutionsPoints = ApoSimpleLevelSolution.getMinSolution(this.level.makeString(), 4);
				System.out.println("solution: "+solutionsPoints.size());
				for (Point p : solutionsPoints) {
					System.out.println(p);
				}
				return;
			} else if (button == KeyEvent.VK_R) {
				if (this.stars <= 0) {
					this.makeLevel(this.level, this.bEditor, super.getLevel());
					return;
				}
			} else if (button == KeyEvent.VK_E) {
				this.getGame().setEditor(this.level.makeString());
			}
		}
		super.keyButtonReleased(button, character);
	}
	
	private boolean nextUserlevel(int add) {
		this.getGame().getUserlevels().setCurLevel(this.getGame().getUserlevels().getCurLevel() + add);
		ApoSimpleLevel level = this.getGame().getUserlevels().getCurrentLevel();
		if (level == null) {
			this.getGame().getUserlevels().setCurLevel(this.getGame().getUserlevels().getCurLevel() - add);
			return false;
		} else {
			this.getGame().setLevel(level, false, this.levelInt);
			return true;
		}
	}
	
	private boolean nextLevel(int change, boolean bCheck) {
		int levelInt = super.getLevel() + change;
		if (bCheck) {
			if (!this.getGame().isLevelSolved(levelInt)) {
				if (change < 0) {
					levelInt = this.getGame().getMaxLevel() + 1;
				} else {
					levelInt = 1;
				}
			}
		}
		if (levelInt > ApoSimpleLevels.getMaxLevels()) {
			levelInt = 1;
		}
		ApoSimpleLevel level = ApoSimpleLevels.getLevel(levelInt);
		if (level == null) {
			levelInt = 1;
			level = ApoSimpleLevels.getLevel(levelInt);
		}
		this.getGame().setLevel(level, false, levelInt);
		return true;
	}
	
	public void driverOutside() {		
		this.setDriver(null);
		
		this.setbCoinShow(false);
		
		this.getPreview().setMouse(-1, -1);
		boolean bBreak = false;
		boolean bNoOnes = true;
		for (int x = 0; x < this.getEntities()[0].length; x++) {
			for (int y = this.getEntities().length - 1; y >= 0; y--) {
				if ((this.getEntities()[y][x].isBVisible()) && (this.getEntities()[y][x].getDirection() != ApoSimpleConstants.FENCE)) {
					bBreak = true;
					if ((this.getEntities()[y][x].getDirection() != ApoSimpleConstants.EMPTY) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.DOG_UP) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.DOG_LEFT) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.DOG_RIGHT) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.DOG_DOWN) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.FENCE) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.STONE_1) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.STONE_2) &&
						(this.getEntities()[y][x].getDirection() != ApoSimpleConstants.STONE_3)) {
						bNoOnes = false;
						break;
					}
				}
			}
		}

		if (!bBreak) {
			this.winGame();
			return;
		}
		if (bNoOnes) {
			this.loseGameField();
			return;
		}
		if (this.level.getStars()[2] <= this.getMoves()) {
			this.loseGameSteps();
			return;
		}
		
		super.changeDirectionBlackSheep();
		
		int height = (int)(this.getEntities()[0][0].getWidth() + 5);
		for (int x = 0; x < this.getEntities()[0].length; x++) {
			int[] count = new int[this.getEntities().length];
			for (int y = this.getEntities().length - 1; y >= 0; y--) {
				count[y] = 0;
				if (this.getEntities()[y][x].getDirection() == ApoSimpleConstants.FENCE) {
					count[y] = 0;
				} else if (y < this.getEntities().length - 1) {
					count[y] = count[y + 1];
				} else if (!this.getEntities()[y][x].isBVisible()) {
					count[y] += 1;
				}
				int check = count[y];
				if (check >= 0) {
					for (int myY = y - check; myY >= 0; myY--) {
						if (this.getEntities()[myY][x].getDirection() == ApoSimpleConstants.FENCE) {
							count[y] = 0;
							break;
						} else if (!this.getEntities()[myY][x].isBVisible()) {
							count[y] += 1;
						} else {
							break;
						}
					}
				}
			}
			for (int y = this.getEntities().length - 1; y >= 0; y--) {
				if (count[y] != 0) {
					if ((y - count[y] >= 0) && (this.getEntities()[y-count[y]][x].getDirection() != ApoSimpleConstants.FENCE)) {
						if (this.getEntities()[y-count[y]][x].isBVisible()) {
							this.getEntities()[y][x].setDirection(this.getEntities()[y-count[y]][x].getDirection());
							this.getEntities()[y][x].setBVisible(true);
						} else {
							this.getEntities()[y][x].setDirection(ApoSimpleConstants.EMPTY);
							this.getEntities()[y][x].setBVisible(false);
						}
						this.getEntities()[y][x].setBBlack(this.getEntities()[y-count[y]][x].isBBlack());
						this.getEntities()[y][x].setChangeY(count[y] * height);
						
						this.getEntities()[y-count[y]][x].setDirection(ApoSimpleConstants.EMPTY);
						this.getEntities()[y-count[y]][x].setBVisible(false);
						this.getEntities()[y-count[y]][x].setBBlack(false);
						this.getEntities()[y-count[y]][x].setChangeY(0);	
					} else {
						this.getEntities()[y][x].setDirection(ApoSimpleConstants.EMPTY);
						this.getEntities()[y][x].setBVisible(false);
						this.getEntities()[y][x].setBBlack(false);
						this.getEntities()[y][x].setChangeY(0);	
					}
				}
			}
		}
	}
	
	public void loseGameField() {
		this.getStrings().clear();
		this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_LEVEL_RESTART);
		this.setiBackgroundReal(new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
		Graphics2D g = this.getiBackgroundReal().createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.render(g);
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		g.drawImage(this.getBackgroundScore(), x, y, null);
		
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = "No sheep is left on the field but";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Es gibt kein Schaf mehr auf dem Feld, aber";
		}
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 55);
		
		s = "you haven't cleared the entire field.";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "das Feld ist noch nicht komplett leer!";
		}
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 90);
		
		g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
		s = "Please maeh again";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Bitte versuchs nochmal!";
		}
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 160);
		
		g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + 20, y + 125, null);
		g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + width - ApoSimpleImages.ORIGINAL_SORRY.getWidth() - 20, y + 125, null);
		
		g.dispose();
		this.getGame().setBLoose(true);
		
		this.stars = -1;
		super.getGame().playSound(ApoSimplePanel.SOUND_LOOSE, 100);
	}
	
	public void winGame() {
		this.getStrings().clear();
		this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_LEVEL_SCORE);
		this.setiBackgroundReal(new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
		Graphics2D g = this.getiBackgroundReal().createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.render(g);
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		g.drawImage(this.getBackgroundScore(), x, y, null);
		
		g.dispose();
		this.getGame().setBLoose(true);
		
		this.stars = 1;
		if (this.level.getStars()[0] >= this.getMoves()) {
			this.stars = 3;
		} else if (this.level.getStars()[1] >= this.getMoves()) {
			this.stars = 2;
		}
		if (super.getLevel() > 0) {
			this.getGame().setLevelSolved(super.getLevel(), this.stars);
		}
		super.getGame().playSound(ApoSimplePanel.SOUND_WIN_LEVELUP, 100);
	}
	
	public void loseGameSteps() {
		this.getStrings().clear();
		this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_LEVEL_RESTART);
		this.setiBackgroundReal(new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
		Graphics2D g = this.getiBackgroundReal().createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.render(g);
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		g.drawImage(this.getBackgroundScore(), x, y, null);
		
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = "You need to many steps!";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Du hast zuviele Schritte benötigt!";
		}
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 55);
		
		s = "Max step count in this level is "+this.level.getStars()[2];
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Die maximale Schrittanzahl: "+this.level.getStars()[2];
		}
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 90);
		
		g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
		s = "Please maeh again";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Bitte versuchs nochmal!";
		}
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x + width/2 - w/2, y + 160);
		
		g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + 20, y + 125, null);
		g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + width - ApoSimpleImages.ORIGINAL_SORRY.getWidth() - 20, y + 125, null);
		
		g.dispose();
		this.getGame().setBLoose(true);
		
		this.stars = -1;
		super.getGame().playSound(ApoSimplePanel.SOUND_LOOSE, 100);
	}
	
	public void drawLevel(Graphics2D g) {
		super.drawLevel(g);
		
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		int x = 535;
		int y = 268;
		
		g.setColor(Color.BLACK);
		g.drawImage(ApoSimplePanel.iStarLittle, x, y - 16, null);
		if (this.level != null) {
			ApoSimpleGame.drawString(g, String.valueOf(this.level.getStars()[2]), x + 50, y);
			
			if (this.getMoves() <= this.level.getStars()[1]) {
				y += 25;
				g.drawImage(ApoSimplePanel.iStarLittle, x, y - 16, null);
				g.drawImage(ApoSimplePanel.iStarLittle, x + 10, y - 16, null);
				ApoSimpleGame.drawString(g, String.valueOf(this.level.getStars()[1]), x + 50, y);
			}
			
			if (this.getMoves() <= this.level.getStars()[0]) {
				y += 25;
				g.drawImage(ApoSimplePanel.iStarLittle, x, y - 16, null);
				g.drawImage(ApoSimplePanel.iStarLittle, x + 10, y - 16, null);
				g.drawImage(ApoSimplePanel.iStarLittle, x + 20, y - 16, null);
				ApoSimpleGame.drawString(g, String.valueOf(this.level.getStars()[0]), x + 50, y);
			}
		}
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		if (this.getLevel() <= 120) {
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			if (this.getLevel() == 1) {
				int maxY = 105;
				this.drawTutorial(g, maxY);
				
				String s = ApoSimpleConstants.LEVEL_FIRST[0];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45);
				
				s = ApoSimpleConstants.LEVEL_FIRST[1];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 70);
				
				s = ApoSimpleConstants.LEVEL_FIRST[2];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, maxY);
			} else if (this.getLevel() == 2) {
				int maxY = 140 + 1 * 25;
				this.drawTutorial(g, maxY);
				
				String s = ApoSimpleConstants.LEVEL_SECOND[0];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45);
				
				s = ApoSimpleConstants.LEVEL_SECOND[1];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 1);
				
				s = ApoSimpleConstants.LEVEL_SECOND[2];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 2);
				

				s = ApoSimpleConstants.LEVEL_SECOND[3];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 140);
				
				s = ApoSimpleConstants.LEVEL_SECOND[4];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, maxY);
			} else if (this.getLevel() == 4) {
				int maxY = 45;
				this.drawTutorial(g, maxY);
				
				String s = ApoSimpleConstants.LEVEL_THIRD[0];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, maxY);
			} else if (this.getLevel() == 11) {
				int maxY = 45 + 25 * (ApoSimpleConstants.LEVEL_EIGHT.length - 1);
				this.drawTutorial(g, maxY);
				
				for (int i = 0; i < ApoSimpleConstants.LEVEL_EIGHT.length; i++) {
					String s = ApoSimpleConstants.LEVEL_EIGHT[i];
					int w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 10 + width/2 - w/2, 45 + 25 * i);
				}
			} else if (this.getLevel() == 19) {
				int maxY = 45 + 25 * (ApoSimpleConstants.LEVEL_NINE.length - 1);
				this.drawTutorial(g, maxY);
				
				for (int i = 0; i < ApoSimpleConstants.LEVEL_NINE.length; i++) {
					String s = ApoSimpleConstants.LEVEL_NINE[i];
					int w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 10 + width/2 - w/2, 45 + 25 * i);
				}
			} else if ((this.getLevel() == 20) || (this.getLevel() == 21)) {
				int maxY = 45 + 25 * (ApoSimpleConstants.LEVEL_TEN.length - 1);
				this.drawTutorial(g, maxY);
				
				for (int i = 0; i < ApoSimpleConstants.LEVEL_TEN.length; i++) {
					String s = ApoSimpleConstants.LEVEL_TEN[i];
					int w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 10 + width/2 - w/2, 45 + 25 * i);
				}
			} else if (this.getLevel() == 29) {
				int maxY = 45 + 25 * 4;
				this.drawTutorial(g, maxY);
				
				String s = ApoSimpleConstants.LEVEL_FOURTH[0];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45);
				
				s = ApoSimpleConstants.LEVEL_FOURTH[1];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 1);

				s = ApoSimpleConstants.LEVEL_FOURTH[2];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 2);
				
				s = ApoSimpleConstants.LEVEL_FOURTH[3];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 3);
				

				s = ApoSimpleConstants.LEVEL_FOURTH[4];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, maxY);
			} else if (this.getLevel() == 30) {
				int maxY = 45 + 25 * 2;
				this.drawTutorial(g, maxY);
				
				String s = ApoSimpleConstants.LEVEL_FIFTH[0];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45);
				
				s = ApoSimpleConstants.LEVEL_FIFTH[1];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, 45 + 25 * 1);

				s = ApoSimpleConstants.LEVEL_FIFTH[2];
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 10 + width/2 - w/2, maxY);
			} else if (this.getLevel() == 43) {
				this.drawLevelHelpString(g, ApoSimpleConstants.LEVEL_SIX);
			} else if (this.getLevel() == 44) {
				this.drawLevelHelpString(g, ApoSimpleConstants.LEVEL_ELEVEN);
			} else if (this.getLevel() == 45) {
				this.drawLevelHelpString(g, ApoSimpleConstants.LEVEL_SEVEN);
			}
		}
	}
	
	private void drawLevelHelpString(Graphics2D g, String[] drawString) {
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		
		int maxY = 45 + 25 * drawString.length;
		this.drawTutorial(g, maxY);
		
		for (int i = 0; i < drawString.length; i++) {
			String s = drawString[i];
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/2 - w/2, 45 + 25 * i);
		}
	}
	
	public void drawLoose(Graphics2D g) {
		if (this.getiBackgroundReal() != null) {
			g.drawImage(this.getiBackgroundReal(), 0, 0, null);
		}
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		
		if (this.stars <= 0) {
			
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_STATICS);
			String s = "";
			if (this.stars == 1) {
				s = "'ok'-result";
			} else if (this.stars == 2) {
				s = "'good'-result";
			} else if (this.stars == 3) {
				s = "PERFECT";
			}
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 60);
			
			g.setFont(ApoSimpleGame.FONT_RESTART);
			if (ApoSimpleConstants.REGION.equals("de")) {
				if (this.getMoves() > 1) {
					s = "Du hast das Level in "+String.valueOf(this.getMoves()) + " Schritten gelöst!";
				} else {
					s = "Du hast das Level in "+String.valueOf(this.getMoves()) + " Schritt gelöst!";
				}
			} else {
				s = "You solved the level in "+String.valueOf(this.getMoves()) + " step";

				if (this.getMoves() > 1) {
					s += "s";
				}
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 110);
			
			if (ApoSimplePanel.iStar == null) {
				g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
				s = "You got " + String.valueOf(this.stars) + " stars";
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, x + width/2 - w/2, y + 155);
			} else {
				int myY = 210;
				if (this.stars == 1) {
					g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth()/2, myY, null);
				} else 	if (this.stars == 2) {
					g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth() - 5, myY, null);
					g.drawImage(ApoSimplePanel.iStar, x + width/2 - 0*ApoSimplePanel.iStar.getWidth() + 5, myY, null);
				} else {
					g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth()/2, myY, null);
					g.drawImage(ApoSimplePanel.iStar, x + width/2 - 10 - 3 * ApoSimplePanel.iStar.getWidth()/2, myY - 10, null);
					g.drawImage(ApoSimplePanel.iStar, x + width/2 + 10 + 1 * ApoSimplePanel.iStar.getWidth()/2, myY - 10, null);
				}
			}
		}
	}
	
	public void drawPoints(Graphics2D g) {
		if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
			int y = 147;
			g.drawImage(ApoSimpleImages.GAME_SORTED, 510, y - 40, null);
			g.setColor(Color.BLACK);
			String s = String.valueOf(this.getGame().getUserlevels().getSortedBy());
			g.setFont(ApoSimpleGame.FONT_RESTART);
			int w = g.getFontMetrics().stringWidth(s);
			if (w > 80) {
				g.setFont(ApoSimpleGame.FONT_BUTTON);
				w = g.getFontMetrics().stringWidth(s);
				if (w > 80) {
					g.setFont(ApoSimpleGame.FONT_LITTLE);
					w = g.getFontMetrics().stringWidth(s);	
				}
			}
			int x = 565;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		}
	}
	
	public void renderArrow(Graphics2D g, int x, int y, int changeX, int changeY) {
	}
	
	public void drawGoodie(Graphics2D g) {
	}
	
	public void drawLevelInfo(Graphics2D g) {
		if (this.levelInt == ApoSimpleLevelGame.USERLEVELS) {
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = String.valueOf((this.getGame().getUserlevels().getCurLevel()+1)+" / "+this.getGame().getUserlevels().getMaxLevel());
			int w = g.getFontMetrics().stringWidth(s);
			int x = 555;
			int y = 193;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		} else {
			super.drawLevelInfo(g);
		}
	}
	
	public void drawCoinOver(Graphics2D g) {
		String[] over = new String[ApoSimpleConstants.OVER_COIN_LEVEL.length];
		for (int i = 0; i < over.length; i++) {
			over[i] = ApoSimpleConstants.OVER_COIN_LEVEL[i];
			if (i == 1) {
				over[i] += String.valueOf(this.getCost());
			}
		}
		this.getGame().renderOver(g, over, 57);
	}
	
}
