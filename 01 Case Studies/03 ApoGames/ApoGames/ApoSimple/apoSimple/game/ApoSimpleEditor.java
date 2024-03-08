package apoSimple.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleString;
import apoSimple.game.level.ApoSimpleLevel;
import apoSimple.game.level.ApoSimpleLevelSolution;

public class ApoSimpleEditor extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "editor_menu";
	public static final String TEST = "test";
	public static final String CLEAR = "clear";
	public static final String SOLVE = "solve";
	public static final String RANDOM = "random";
	public static final String LEFT_3 = "left_3";
	public static final String RIGHT_3 = "right_3";
	public static final String LEFT_2 = "left_2";
	public static final String RIGHT_2 = "right_2";
	public static final String LEFT_1 = "left_1";
	public static final String RIGHT_1 = "right_1";
	public static final String UPLOAD = "upload";

	private BufferedImage iBackground;
	
	private ApoSimpleLevel level;
	
	private boolean bChecked = false;
	
	private ArrayList<ApoSimpleString> strings;
	
	private Point overPoint;
	
	public ApoSimpleEditor(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		this.overPoint = new Point(-1, -1);
		
		if (this.strings == null) {
			this.strings = new ArrayList<ApoSimpleString>();
		} else {
			this.strings.clear();
		}
		
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/editor.png", false);
		}
		if (this.level == null) {
			int[] stars = new int[3];
			stars[0] = 1;
			stars[1] = 3;
			stars[2] = stars[1] + 2;
			this.level = new ApoSimpleLevel(new int[7][7], stars);
//			0304020203030303010104040301020301040302010202040101010402030303040404020301010302010104030404010435
//			this.level.makeLevelFromString("0203030302030401030102040303020102010402030304030104040402030202040102030102020103040102010201020124");
//			this.level.makeLevelFromString("0304020203030303010104040301020301040302010202040101010402030303040404020301010302010104030404010435");
			
			
//			this.level.makeLevelFromString("13131313131313131313131313131313131313131313131313131313131302040613131313020203131313130109041313124");
//			this.level.makeLevelFromString("13131313131313131313131313131311151212121313112222221113131102040611131311020203111313100109040913124");
//			this.level.makeLevelFromString("13131313131313131303000400042024120004040125222222222200021904160200092222220900222202020602091700135");
//			this.level.makeLevelFromString("13131313131313131313131313131313132213131313132222221313131302042513131313020203131313020109041313124");
//			this.level.makeLevelFromString("13131313131313131313131313131313131313131309020006030213120202010603130301020108041002010102081004246");
			this.level.makeLevelFromString("22110422200422220224041201222222252225222222101402040322220303252117222201002210032222151923010922124");
			
			
			this.changeChecked(false);
			
//			this.level.makeLevelFromString("13131313131313131313131313131313131313131313130302031313131301030413130302040103040302000200020101134");
		}
	}
	
	public void setLevel(String levelString) {
		this.level.makeLevelFromString(levelString);		
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setPuzzle();
		} else if (button == KeyEvent.VK_C) {
			this.clear();
		} else if (button == KeyEvent.VK_S) {
			this.solution();
		} else if (button == KeyEvent.VK_U) {
			this.uploadLevel();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleEditor.MENU)) {
			this.getGame().setPuzzle();
		} else if (function.equals(ApoSimpleEditor.TEST)) {
			System.out.println(this.level.makeString());
			this.getGame().setLevel(this.level, true, 0);
		} else if (function.equals(ApoSimpleEditor.CLEAR)) {
			this.clear();
		} else if (function.equals(ApoSimpleEditor.RANDOM)) {
			this.save();
		} else if (function.equals(ApoSimpleEditor.SOLVE)) {
			this.solution();
		} else if (function.equals(ApoSimpleEditor.LEFT_3)) {
			this.changeStars(0, -1);
		} else if (function.equals(ApoSimpleEditor.RIGHT_3)) {
			this.changeStars(0, +1);
		} else if (function.equals(ApoSimpleEditor.LEFT_2)) {
			this.changeStars(1, -1);
		} else if (function.equals(ApoSimpleEditor.RIGHT_2)) {
			this.changeStars(1, +1);
		} else if (function.equals(ApoSimpleEditor.LEFT_1)) {
			this.changeStars(2, -1);
		} else if (function.equals(ApoSimpleEditor.RIGHT_1)) {
			this.changeStars(2, +1);
		} else if (function.equals(ApoSimpleEditor.UPLOAD)) {
			this.uploadLevel();
		}
		super.getGame().render();
	}
	
	private void uploadLevel() {
		if (this.bChecked) {
			boolean bUpload = this.getGame().getUserlevels().addLevel(this.level.makeString());
			if (bUpload) {
				this.strings.add(new ApoSimpleString(210, 290, 200, "upload was successfully", true, 5000, false));
				this.getGame().loadUserlevels();
				this.getGame().setCoins(this.getGame().getCoins() + ApoSimpleConstants.ADD_COINS_UPLOAD, true);
			} else {
				this.strings.add(new ApoSimpleString(200, 200, 200, "upload wasn't successfully", true, 5000, false));
				this.strings.add(new ApoSimpleString(190, 250, 200, "either level is already online", true, 5000, false));
				this.strings.add(new ApoSimpleString(230, 300, 200, "or you are not online", true, 5000, false));
				this.strings.add(new ApoSimpleString(250, 350, 200, "or behind a firewall", true, 5000, false));
			}
		}
	}
	
	private void changeStars(int star, int change) {
		this.changeChecked(false);
		int value = this.level.getStars()[star];
		value += change;
		if (value <= 0) {
			if (star == 0) {
				value = this.level.getStars()[star + 1] - 1;
			} else {
				value = 9;
			}
		}
		if (star >= 1) {
			if (value <= this.level.getStars()[star - 1]) {
				if (star == 2) {
					value = 9;
				} else {
					value = this.level.getStars()[star + 1] - 1;
				}
			}
			if (star == 1) {
				if (value >= this.level.getStars()[star + 1]) {
					value = this.level.getStars()[star - 1] + 1;
				}
			} else {
				if (value >= 10) {
					value = this.level.getStars()[star - 1] + 1;
				}
			}
		}
		if (value >= 10) {
			value = 1;
			if (star == 1) {
				value = this.level.getStars()[star - 1] + 1;
			}
		}
		if ((star == 0) && (value >= this.level.getStars()[star + 1])) {
			value = 1;
		}
		this.level.getStars()[star] = value;
	}
	
	private void clear() {
		this.changeChecked(false);
		for (int y = 0; y < this.level.getLevel().length; y++) {
			for (int x = 0; x < this.level.getLevel()[0].length; x++) {
				this.level.getLevel()[y][x] = ApoSimpleConstants.REAL_EMPTY;
			}
		}
	}
	
	private void save() {
		int[][] newLevel = new int[7][7];
		for (int y = 0; y < newLevel.length; y++) {
			for (int x = 0; x < newLevel[0].length; x++) {
				int rand = 0;
				if (Math.random() * 100 > 0) {
					rand = (int)(Math.random() * ApoSimpleConstants.RIGHT) + 1;
				} else {
					rand = ApoSimpleConstants.EMPTY;
				}
				newLevel[y][x] = rand;
			}
		}
		this.level.setLevel(newLevel);

		System.out.println(this.level.makeString());
	}
	
	private void solution() {
		ArrayList<Point> solutionsPoints = ApoSimpleLevelSolution.getMinSolution(this.level.makeString(), 4);
		System.out.println("solution: "+solutionsPoints.size());
		for (Point p : solutionsPoints) {
			System.out.println(p);
		}
		
		if ((this.level.getStars()[0] >= solutionsPoints.size()) && (solutionsPoints.size() > 0)) {
			this.changeChecked(true);
			if (solutionsPoints.size() == 1) {
				this.strings.add(new ApoSimpleString(200, 290, 200, "level solvable in "+(solutionsPoints.size())+" move!", true, 3000, false));
			} else {
				this.strings.add(new ApoSimpleString(200, 290, 200, "level solvable in "+(solutionsPoints.size())+" moves!", true, 3000, false));
			}
			for (int i = 0; i < solutionsPoints.size(); i++) {
				this.strings.add(new ApoSimpleString(270, 290 - 50 * (solutionsPoints.size() - i), 200, (i+1)+".) x: "+solutionsPoints.get(i).x+" y: "+solutionsPoints.get(i).y, true, 3000, false));
			}
		} else {
			this.changeChecked(false);
			if ((this.level.getStars()[0] < solutionsPoints.size()) && (solutionsPoints.size() > 0)) {
				this.strings.add(new ApoSimpleString(150, 290, 200, "Find a solution with "+(solutionsPoints.size())+" moves!", true, 3000, false));
				this.strings.add(new ApoSimpleString(150, 340, 200, "You have to increase the stars", true, 3000, false));
				
				for (int i = 0; i < solutionsPoints.size(); i++) {
					this.strings.add(new ApoSimpleString(270, 290 - 50 * (solutionsPoints.size() - i), 200, (i+1)+".) x: "+solutionsPoints.get(i).x+" y: "+solutionsPoints.get(i).y, true, 3000, false));
				}
			} else {
				this.strings.add(new ApoSimpleString(150, 290, 200, "can't find a solution in less than 5 moves!", true, 3000, false));
			}
		}
	}
	
	public void changeChecked(boolean bChecked) {
		this.bChecked = bChecked;
		this.getGame().getButtons()[38].setBVisible(bChecked);
	}
	
	@Override
	public void mouseWheelChanged(int changed) {
		if ((this.overPoint.x != -1) && (this.overPoint.y != -1)) {
			this.changeChecked(false);
			int value = this.level.getLevel()[this.overPoint.y][this.overPoint.x]%(ApoSimpleConstants.STONE_1 + 1);
			value += changed;
			if (value < ApoSimpleConstants.EMPTY) {
				this.level.getLevel()[this.overPoint.y][this.overPoint.x] = ApoSimpleConstants.STONE_1;
			} else if (value > ApoSimpleConstants.STONE_1) {
				this.level.getLevel()[this.overPoint.y][this.overPoint.x] = this.level.getLevel()[this.overPoint.y][this.overPoint.x] - ApoSimpleConstants.STONE_1;
			} else {
				this.level.getLevel()[this.overPoint.y][this.overPoint.x] = value;
			}
			super.getGame().render();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		int width = ApoSimpleConstants.ENTITY_WIDTH + 5;
		int mouseX = (x - 10)/width;
		int mouseY = (y - 10)/width;
		if ((y - 10 >= 0) && (x - 10 >= 0) && 
			(mouseX < this.level.getLevel()[0].length) &&
			(mouseY < this.level.getLevel().length)) {
			this.changeChecked(false);
			if (bRight) {
				int value = this.level.getLevel()[mouseY][mouseX]%(ApoSimpleConstants.STONE_1 + 1);
				value -= 1;
				if (value < ApoSimpleConstants.EMPTY) {
					if (this.level.getLevel()[mouseY][mouseX] > ApoSimpleConstants.STONE_1) {
						this.level.getLevel()[mouseY][mouseX] = ApoSimpleConstants.STONE_1 + 50;
					} else {
						this.level.getLevel()[mouseY][mouseX] = ApoSimpleConstants.STONE_1;
					}
				} else {
					this.level.getLevel()[mouseY][mouseX] = value;
				}
			} else {
				int value = this.level.getLevel()[mouseY][mouseX]%(ApoSimpleConstants.STONE_1 + 1);
				value += 1;
				if (value > ApoSimpleConstants.STONE_1) {
					this.level.getLevel()[mouseY][mouseX] = this.level.getLevel()[mouseY][mouseX] - ApoSimpleConstants.STONE_1;
				} else {
					this.level.getLevel()[mouseY][mouseX] = value;
				}
			}
			super.getGame().render();
		}
	}

	@Override
	public boolean mouseMoved(int moveX, int moveY) {
		boolean bRender = false;
		int width = ApoSimpleConstants.ENTITY_WIDTH + 5;
		int mouseX = (moveX - 10)/width;
		int mouseY = (moveY - 10)/width;
		if ((moveY - 10 >= 0) && (moveX - 10 >= 0) && 
			(mouseX < this.level.getLevel()[0].length) &&
			(mouseY < this.level.getLevel().length)) {
			if ((this.overPoint.x != mouseX) || (this.overPoint.y != mouseY)) {
				bRender = true;
			}
			this.overPoint.x = mouseX;
			this.overPoint.y = mouseY;
		} else {
			if ((this.overPoint.x != -1) || (this.overPoint.y != -1)) {
				bRender = true;
			}
			this.overPoint.x = -1;
			this.overPoint.y = -1;
		}
		if (bRender) {
			this.getGame().render();
		}
		return true;
	}
	
	@Override
	public void think(int delta) {
		boolean bRender = false;
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
			bRender = true;
		}
		if (bRender) {
			this.getGame().render();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		int width = ApoSimpleConstants.ENTITY_WIDTH + 5;
		for (int y = 0; y < this.level.getLevel().length; y++) {
			for (int x = 0; x < this.level.getLevel()[0].length; x++) {
				if (this.level.getLevel()[y][x]%(ApoSimpleConstants.STONE_1 + 1) != ApoSimpleConstants.REAL_EMPTY) {
					if (this.level.getLevel()[y][x] > 50) {
						g.drawImage(ApoSimpleImages.ORIGINAL_GREEN, x * width + 10, y * width + 10, null);
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL, x * width + 10, y * width + 10, null);
					}
					int doubleChange = 5;
					int value = this.level.getLevel()[y][x]%(ApoSimpleConstants.STONE_1 + 1);
					if (value == ApoSimpleConstants.UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOG_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOG_DOWN, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOG_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOG_UP, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOG_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOG_LEFT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOG_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOG_RIGHT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.BLACK_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.BLACK_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.BLACK_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.BLACK_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.FENCE) {
						g.drawImage(ApoSimpleImages.FENCE_FREE, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.STONE_1) {
						g.drawImage(ApoSimpleImages.STONE_1, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.STONE_2) {
						g.drawImage(ApoSimpleImages.STONE_2, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.STONE_3) {
						g.drawImage(ApoSimpleImages.STONE_3, x * width + 10, y * width + 10, null);
					} else if (value == ApoSimpleConstants.DOUBLE_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_BLACK_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_BLACK_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_BLACK_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					} else if (value == ApoSimpleConstants.DOUBLE_BLACK_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, x * width + 10 - doubleChange, y * width + 10 - doubleChange, null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, x * width + 10 + doubleChange, y * width + 10 + doubleChange, null);
					}
				}
				if ((this.overPoint.x == x) && (this.overPoint.y == y)) {
					g.drawImage(ApoSimpleImages.ORIGINAL_OVER, x * width + 10, y * width + 10, null);
				}
			}
		}
		
		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 -  ApoSimplePanel.iStarLittle.getWidth()/2), (int)(this.getGame().getButtons()[24].getY() + this.getGame().getButtons()[24].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2), null);
		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 - ApoSimplePanel.iStarLittle.getWidth()), (int)(this.getGame().getButtons()[24].getY() + this.getGame().getButtons()[24].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2 - 8), null);
		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 + 5), (int)(this.getGame().getButtons()[24].getY() + this.getGame().getButtons()[24].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2 - 8), null);

		
		g.setFont(ApoSimpleGame.FONT_STATICS);
		g.setColor(Color.BLACK);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		String s = String.valueOf(this.level.getStars()[0]);
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSimpleConstants.GAME_HEIGHT + 80 - w/2, this.getGame().getButtons()[24].getY() + this.getGame().getButtons()[24].getHeight()/2 + h/2);
		
		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 - ApoSimplePanel.iStarLittle.getWidth()), (int)(this.getGame().getButtons()[26].getY() + this.getGame().getButtons()[26].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2), null);
		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 + 5), (int)(this.getGame().getButtons()[26].getY() + this.getGame().getButtons()[26].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2), null);
		
		s = String.valueOf(this.level.getStars()[1]);
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSimpleConstants.GAME_HEIGHT + 80 - w/2, this.getGame().getButtons()[26].getY() + this.getGame().getButtons()[26].getHeight()/2 + h/2);

		g.drawImage(ApoSimplePanel.iStarLittle, (int)(ApoSimpleConstants.GAME_HEIGHT + 80 -  ApoSimplePanel.iStarLittle.getWidth()/2), (int)(this.getGame().getButtons()[33].getY() + this.getGame().getButtons()[33].getHeight()/2 -  ApoSimplePanel.iStarLittle.getHeight()/2), null);
		
		s = String.valueOf(this.level.getStars()[2]);
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSimpleConstants.GAME_HEIGHT + 80 - w/2, this.getGame().getButtons()[33].getY() + this.getGame().getButtons()[33].getHeight()/2 + h/2);

		this.drawString(g);
	}

	private void drawString(Graphics2D g) {
		try {
			for (int i = this.strings.size() - 1; i >= 0; i--) {
				this.strings.get(i).render(g, 0, 0);
			}
		} catch (Exception ex) {	
		}
	}
	
	public void drawOverlay(Graphics2D g) {
	}
	
}
