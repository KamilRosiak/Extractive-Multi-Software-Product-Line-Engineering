package apoMario.game.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioDestructableWall;
import apoMario.entity.ApoMarioEntity;
import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioGumba;
import apoMario.entity.ApoMarioKoopa;
import apoMario.entity.ApoMarioQuestionmark;
import apoMario.entity.ApoMarioWall;
import apoMario.game.ApoMarioPanel;
import apoMario.level.ApoMarioLevel;

/**
 * Editorpanelklasse<br />
 * Klasse, die den Editor darstellt und sich darum kümmert,<br />
 * wann welche Buttons angezeigt werden und wo was hingesetzt werden kann ...<br />
 * halt ein Editor ;) <br />
 * @author Dirk Aporius
 */
public class ApoMarioEditor extends ApoMarioModel {
	
	public static final String FUNCTION_SET = "empty";
	public static final String FUNCTION_RANDOM = "random";
	public static final String FUNCTION_TEST = "test";
	public static final String FUNCTION_LOAD = "load";
	public static final String FUNCTION_SAVE = "save";
	public static final String FUNCTION_MENU = "menu";
	public static final String FUNCTION_WALL = "wall";
	public static final String FUNCTION_WALL_NO_COLLISION = "no_collision";
	public static final String FUNCTION_WALL_BREAKABLE = "breakable";
	public static final String FUNCTION_WALL_COLLISION = "collision";
	public static final String FUNCTION_WALL_QUESTION = "question";
	public static final String FUNCTION_WALL_COIN = "coin";
	public static final String FUNCTION_ENEMY_FLOWER = "flower";
	public static final String FUNCTION_ENEMY_CANON = "canon";
	public static final String FUNCTION_ENEMY_GUMBA = "gumba";
	public static final String FUNCTION_ENEMY_KOOPA = "koopa";
	public static final String FUNCTION_DETAIL_LEFT = "look left:";
	public static final String FUNCTION_DETAIL_FALL = "can fall:";
	public static final String FUNCTION_DETAIL_FLY = "can fly:";
	public static final String FUNCTION_DETAIL_IMMORTAL = "is immortal:";
	public static final String FUNCTION_DETAIL_LEFTTIMESHOW = "left_timeshow";
	public static final String FUNCTION_DETAIL_RIGHTTIMESHOW = "right_timeshow";
	public static final String FUNCTION_DETAIL_LEFTSTART = "left_start";
	public static final String FUNCTION_DETAIL_RIGHTSTART = "right_start";
	public static final String FUNCTION_DETAIL_ENEMYFLOWER = "with enemy:";
	public static final String FUNCTION_DETAIL_QUESTION_GOODIE = "with goodie";
	public static final String FUNCTION_DETAIL_BREAKABLE_COIN = "with coin:";
	public static final String FUNCTION_DETAIL_BREAKABLE_GOODIE = "with goodie:";
	public static final String FUNCTION_DETAIL_BREAKABLE = "breakable:";
	
	private final int WIDTH = 100 * ApoMarioConstants.APP_SIZE;
	
	private ApoMarioOptionsDrag dragWidth;
	private ApoMarioOptionsDrag dragDifficulty;
	
	private ApoButton[] editorButtons;
	
	private int curChoose;
	
	private String curFunction;
	
	private BufferedImage iCurChoose;
	
	private int mouseX, mouseY;
	
	private boolean bRight;
	
	private ApoMarioEntity curChooseEntity;
	
	public ApoMarioEditor(ApoMarioPanel game) {
		super(game);
	}

	public void init() {
		int x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5 * ApoMarioConstants.APP_SIZE;
		int y = 10 + 5 * ApoMarioConstants.APP_SIZE;
		int maxWidth = this.WIDTH - 10 * ApoMarioConstants.APP_SIZE;
		if (this.dragWidth == null) {
			this.dragWidth = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
		}
		y = y + 20 + 5 * ApoMarioConstants.APP_SIZE;
		if (this.dragDifficulty == null) {
			this.dragDifficulty = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
		}
		
		this.editorButtons = new ApoButton[29];
		
		int lastY = 0;
		Font font = ApoMarioConstants.FONT_BUTTON;
		String text = "empty";
		String function = ApoMarioEditor.FUNCTION_SET;
		int width = (int)((this.WIDTH - 10) / 2);
		int height = ApoMarioConstants.APP_SIZE * 5 + 10;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5;
		y = y + height + 5;
		BufferedImage iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[0] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "random";
		function = ApoMarioEditor.FUNCTION_RANDOM;
		width = (int)((this.WIDTH - 10) / 2);
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5 + width;
		iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[1] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "";
		function = ApoMarioEditor.FUNCTION_WALL;
		width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE + 4);
		height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE + 4;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1;
		y = y + (int)(1.8f * height) + 2 * 3;
		BufferedImage iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[2] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "";
		function = ApoMarioEditor.FUNCTION_WALL_NO_COLLISION;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + width + 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[3] = new ApoButton(iButton, x, y, width, height, function);

		text = "";
		function = ApoMarioEditor.FUNCTION_WALL_COLLISION;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 2 * width + 2 * 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[4] = new ApoButton(iButton, x, y, width, height, function);

		text = "";
		function = ApoMarioEditor.FUNCTION_WALL_BREAKABLE;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 3 * width + 3 * 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[5] = new ApoButton(iButton, x, y, width, height, function);

		text = "";
		function = ApoMarioEditor.FUNCTION_WALL_QUESTION;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 4 * width + 4 * 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[6] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "";
		function = ApoMarioEditor.FUNCTION_WALL_COIN;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 0 * width + 0 * 3;
		y += height + 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[7] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "";
		function = ApoMarioEditor.FUNCTION_ENEMY_FLOWER;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 0 * width + 0 * 3;
		y += height + 3;
		BufferedImage iTile2 = this.getCurChooseImage(function, false);
		iTile = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iTile.createGraphics();
		g.drawImage(iTile2.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[8] = new ApoButton(iButton, x, y, width, height, function);
		
		text = "";
		function = ApoMarioEditor.FUNCTION_ENEMY_CANON;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 1 * width + 1 * 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[9] = new ApoButton(iButton, x, y, width, height, function);

		text = "";
		function = ApoMarioEditor.FUNCTION_ENEMY_GUMBA;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 2 * width + 2 * 3;
		iTile = this.getCurChooseImage(function, false);
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[10] = new ApoButton(iButton, x, y, width, height, function);

		text = "";
		function = ApoMarioEditor.FUNCTION_ENEMY_KOOPA;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 1 + 3 * width + 3 * 3;
		iTile2 = this.getCurChooseImage(function, false);
		iTile = new BufferedImage(width/2, height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = iTile.createGraphics();
		g.drawImage(iTile2.getScaledInstance(width/2, height, Image.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		iButton = this.getGame().getImages().getButtonWithImage(3 * width, height, iTile, text, new Color(0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font);
		this.editorButtons[11] = new ApoButton(iButton, x, y, width, height, function);
		
		lastY = y;
		
		text = "load";
		function = ApoMarioEditor.FUNCTION_LOAD;
		width = (int)((this.WIDTH - 10) / 2);
		height = ApoMarioConstants.APP_SIZE * 5 + 10;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5;
		y = ApoMarioConstants.GAME_HEIGHT - 2 * 3 - 2 * height;
		iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[12] = new ApoButton(iButton, x, y, width, height, function);
		if (ApoConstants.B_APPLET) {
			this.editorButtons[12].setBVisible(false);
		}
		
		text = "save";
		function = ApoMarioEditor.FUNCTION_SAVE;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5 + width;
		y = ApoMarioConstants.GAME_HEIGHT - 2 * 3 - 2 * height;
		iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[13] = new ApoButton(iButton, x, y, width, height, function);
		if (ApoConstants.B_APPLET) {
			this.editorButtons[13].setBVisible(false);
		}
		
		text = "test";
		function = ApoMarioEditor.FUNCTION_TEST;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5;
		y = ApoMarioConstants.GAME_HEIGHT - 1 * 3 - height;
		iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[14] = new ApoButton(iButton, x, y, width, height, function);

		text = "menu";
		function = ApoMarioEditor.FUNCTION_MENU;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5 + width;
		y = ApoMarioConstants.GAME_HEIGHT - 1 * 3 - height;
		iButton = this.getGame().getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
		this.editorButtons[15] = new ApoButton(iButton, x, y, width, height, function);

		text = ApoMarioEditor.FUNCTION_DETAIL_LEFT;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y = lastY + 2 * width + 5;
		this.editorButtons[16] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_FALL;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y += width + 5;
		this.editorButtons[17] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_FLY;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y += width + 5;
		this.editorButtons[18] = new ApoMarioOptionsButton(x, y, width, width, text);

		text = ApoMarioEditor.FUNCTION_DETAIL_IMMORTAL;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y += width + 5;
		this.editorButtons[19] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = "<";
		function = ApoMarioEditor.FUNCTION_DETAIL_LEFTTIMESHOW;
		width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 3;
		y = lastY + 3 * width + 2 * 5;
		iButton = this.getGame().getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
		this.editorButtons[20] = new ApoButton(iButton, x, y, width, height, function);
		this.editorButtons[20].setBWait(true);
		
		text = ">";
		function = ApoMarioEditor.FUNCTION_DETAIL_RIGHTTIMESHOW;
		width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - 3 - width;
		iButton = this.getGame().getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
		this.editorButtons[21] = new ApoButton(iButton, x, y, width, height, function);
		this.editorButtons[21].setBWait(true);
		
		text = "<";
		function = ApoMarioEditor.FUNCTION_DETAIL_LEFTSTART;
		width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 3;
		y += width + 5;
		iButton = this.getGame().getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
		this.editorButtons[22] = new ApoButton(iButton, x, y, width, height, function);
		this.editorButtons[22].setBWait(true);
		
		text = ">";
		function = ApoMarioEditor.FUNCTION_DETAIL_RIGHTSTART;
		width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - 3 - width;
		iButton = this.getGame().getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
		this.editorButtons[23] = new ApoButton(iButton, x, y, width, height, function);
		this.editorButtons[23].setBWait(true);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_ENEMYFLOWER;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y = lastY + 2 * width + 5;
		this.editorButtons[24] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_QUESTION_GOODIE;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y = lastY + 2 * width + 5;
		this.editorButtons[25] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y = lastY + 2 * width + 5;
		this.editorButtons[26] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE_COIN;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y += width + 5;
		this.editorButtons[27] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		text = ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE_GOODIE;
		width = 15 * ApoMarioConstants.APP_SIZE;
		x = ApoMarioConstants.GAME_WIDTH - width - 5;
		y += width + 5;
		this.editorButtons[28] = new ApoMarioOptionsButton(x, y, width, width, text);
		
		if (this.curChoose <= 0) {
			this.setCurChoose(2, ApoMarioEditor.FUNCTION_WALL);
			this.getGame().getLevel().makeEmptyLevel();
		}
		this.setCurChooseEntity(this.curChooseEntity);
		this.getGame().getLevel().restart(false);
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().getLevel().setLevelInt(-1);
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (x > ApoMarioConstants.GAME_WIDTH - this.WIDTH) {
			if (this.dragWidth != null) {
				if (this.dragWidth.mouseReleased(x, y)) {
				}
			}
			if (this.dragDifficulty != null) {
				if (this.dragDifficulty.mouseReleased(x, y)) {
				}
			}
			if (this.editorButtons != null) {
				for (int i = 0; i < this.editorButtons.length; i++) {
					if (this.editorButtons[i].getReleased(x, y)) {
						String function = this.editorButtons[i].getFunction();
						this.checkFunction(function, i);
					}
				}
			}
		} else {
			int myX = this.getMouseXInLevel(x);
			int myY = this.getMouseYInLevel(y);
			if ((myY < this.getGame().getLevel().getHeight()) &&
				(myX < this.getGame().getLevel().getWidth())) {
				if (this.bRight) {
					this.setCurChooseEntity(null);
					if (this.isThereAnEnemy(myX, myY, true) == null) {
						if (this.getGame().getLevel().getLevelEntities()[myY][myX] != null) {
							if ((this.getGame().getLevel().getLevelEntities()[myY][myX] != null) && (this.getGame().getLevel().getLevelEntities()[myY][myX] instanceof ApoMarioWall) && (((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[myY][myX])).isBCanon())) {
								boolean bBreak = false;
								int i = myY;
								while ((i < this.getGame().getHeight()) && (i >= 0) && (!bBreak)) {
									if ((this.getGame().getLevel().getLevelEntities()[i][myX] != null) && (this.getGame().getLevel().getLevelEntities()[i][myX] instanceof ApoMarioWall) && (((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[i][myX])).isBCanon())) {
										this.getGame().getLevel().getLevelEntities()[i][myX] = null;		
									} else {
										bBreak = true;
									}
									i += 1;
								}
								bBreak = false;
								i = myY - 1;
								while ((i < this.getGame().getHeight()) && (i >= 0) && (!bBreak)) {
									if ((this.getGame().getLevel().getLevelEntities()[i][myX] != null) && (this.getGame().getLevel().getLevelEntities()[i][myX] instanceof ApoMarioWall) && (((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[i][myX])).isBCanon())) {
										this.getGame().getLevel().getLevelEntities()[i][myX] = null;		
									} else {
										bBreak = true;
									}
									i -= 1;
								}
							} else if ((this.getGame().getLevel().getLevelEntities()[myY][myX] != null) && (this.getGame().getLevel().getLevelEntities()[myY][myX] instanceof ApoMarioWall) && (((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[myY][myX])).isBTube())) {
								ApoMarioWall wall = (ApoMarioWall)this.getGame().getLevel().getLevelEntities()[myY][myX];
								Point p = wall.getTubePoint();
								if (p == null) {
									p = new Point(0, 0);
								}
								int wallX = myX - p.x;
								int wallY = myY - p.y;
								for (int i = 0; i < 2; i++) {
									for (int j = 0; j < 2; j++) {
										this.getGame().getLevel().getLevelEntities()[wallY + i][wallX + j] = null;		
									}
								}
							} else {
								this.getGame().getLevel().getLevelEntities()[myY][myX] = null;
							}
							this.getGame().getLevel().makeGroundCorrect();
							this.getGame().getLevel().makeGroundCorrectNoCollision();
						}					
					} else {
						this.deleteEnemy(myX, myY);
					}
				} else {
					ApoMarioEntity enemy = this.isThereAnEnemy(myX, myY, false);
					if (enemy != null) {
						this.setCurChooseEntity(enemy);
					} else if ((this.getGame().getLevel().getLevelEntities()[myY][myX] == null) && (enemy == null)) {
						if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL))) {
							this.getGame().getLevel().makeGroundWall(myX, myY);
							this.getGame().getLevel().makeGroundCorrect();
						} else if (myY < this.getGame().getLevel().getHeight() - 1) {
							if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL_QUESTION))) {
								this.getGame().getLevel().makeBoxQuestionMark(myX, myY, 0);
								this.getGame().getLevel().makeGroundCorrect();
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL_COIN))) {
								this.getGame().getLevel().makeBoxCoin(myX, myY);
								this.getGame().getLevel().makeGroundCorrect();
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL_BREAKABLE))) {
								this.getGame().getLevel().makeWall(true, false, ApoMarioConstants.GOODIE_COIN, myX, myY);
								this.getGame().getLevel().makeGroundCorrect();
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL_COLLISION))) {
								this.getGame().getLevel().makeWall(false, true, -1, myX, myY);
								this.getGame().getLevel().makeGroundCorrect();
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_WALL_NO_COLLISION))) {
								this.getGame().getLevel().makeWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), true, true, false, false, -1, myX, myY);
								this.getGame().getLevel().makeGroundCorrectNoCollision();
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_ENEMY_GUMBA))) {
								this.addEnemy(myX, myY, 0);
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_ENEMY_KOOPA))) {
								this.addEnemy(myX, myY, 1);
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_ENEMY_CANON))) {
								this.getGame().getLevel().makeCanon(myX, myY, this.getGame().getLevel().getHeight(), 6000, 3000);
							} else if ((this.curFunction != null) && (this.curFunction.equals(FUNCTION_ENEMY_FLOWER))) {
								this.getGame().getLevel().makeFlower(myX, myY, 2, 2500, true, 3000);
							}
						}
					}
				}
			}
		}
	}
	
	public void setCurChooseEntity(ApoMarioEntity curChooseEntity) {
		for (int i = 16; i < this.editorButtons.length; i++) {
			this.editorButtons[i].setBVisible(false);
		}
		this.curChooseEntity = curChooseEntity;
		if (curChooseEntity != null) {
			this.curChooseEntity = curChooseEntity;
			if (this.curChooseEntity instanceof ApoMarioGumba) {
				for (int i = 16; i < 19; i++) {
					this.editorButtons[i].setBVisible(true);
				}
				ApoMarioGumba gumba = (ApoMarioGumba)this.curChooseEntity;
				if (gumba.isBLeft()) {
					((ApoMarioOptionsButton)(this.editorButtons[16])).setBActive(true);
				} else {
					((ApoMarioOptionsButton)(this.editorButtons[16])).setBActive(false);
				}
				if (gumba.isBFall()) {
					((ApoMarioOptionsButton)(this.editorButtons[17])).setBActive(true);
				} else {
					((ApoMarioOptionsButton)(this.editorButtons[17])).setBActive(false);
				}
				if (gumba.isBFlyOriginal()) {
					((ApoMarioOptionsButton)(this.editorButtons[18])).setBActive(true);
				} else {
					((ApoMarioOptionsButton)(this.editorButtons[18])).setBActive(false);
				}
				if (!(this.curChooseEntity instanceof ApoMarioKoopa)) {
					for (int i = 19; i < 20; i++) {
						this.editorButtons[i].setBVisible(true);
					}
					if (gumba.isBImmortal()) {
						((ApoMarioOptionsButton)(this.editorButtons[19])).setBActive(true);
					} else {
						((ApoMarioOptionsButton)(this.editorButtons[19])).setBActive(false);
					}
				}
			} else if (this.curChooseEntity instanceof ApoMarioWall) {
				ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
				if (this.curChooseEntity instanceof ApoMarioDestructableWall) {
					for (int i = 26; i < 29; i++) {
						this.editorButtons[i].setBVisible(true);
					}
					ApoMarioDestructableWall dWall = (ApoMarioDestructableWall)(this.curChooseEntity);
					ApoMarioOptionsButton button = ((ApoMarioOptionsButton)(this.editorButtons[27]));
					if (wall.getGoodie() == ApoMarioConstants.GOODIE_COIN) {
						button.setBActive(true);
					} else {
						button.setBActive(false);
					}
					button = ((ApoMarioOptionsButton)(this.editorButtons[28]));
					if (wall.getGoodie() == ApoMarioConstants.GOODIE_GOODIE) {
						button.setBActive(true);
					} else {
						button.setBActive(false);
					}
					button = ((ApoMarioOptionsButton)(this.editorButtons[26]));
					if (dWall.getIWall() == null) {
						button.setBActive(true);
					} else {
						button.setBActive(false);
					}
				} else if (this.curChooseEntity instanceof ApoMarioQuestionmark) {
					this.editorButtons[25].setBVisible(true);
					if (wall.getGoodie() == ApoMarioConstants.GOODIE_GOODIE) {
						((ApoMarioOptionsButton)(this.editorButtons[25])).setBActive(true);
					} else {
						((ApoMarioOptionsButton)(this.editorButtons[25])).setBActive(false);
					}
				} else {
					if (wall.isBTube()) {
						for (int i = 20; i < 25; i++) {
							this.editorButtons[i].setBVisible(true);
						}
						Point p = wall.getTubePoint();
						if (p == null) {
							p = new Point(0, 0);
						}
						int x = (int)(this.curChooseEntity.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
						int y = (int)(this.curChooseEntity.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
						if (isThereAnEnemy(x, y, true) != null) {
							((ApoMarioOptionsButton)(this.editorButtons[24])).setBActive(true);
						} else {
							((ApoMarioOptionsButton)(this.editorButtons[24])).setBActive(false);
							for (int i = 20; i < 24; i++) {
								this.editorButtons[i].setBVisible(false);
							}
						}
					} else if (wall.isBCanon()) {
						Point p = wall.getTubePoint();
						if (p == null) {
							p = new Point(0, 0);
						}
						int x = (int)(this.curChooseEntity.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
						int y = (int)(this.curChooseEntity.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
						this.curChooseEntity = this.getGame().getLevel().getLevelEntities()[y][x];
						for (int i = 20; i < 24; i++) {
							this.editorButtons[i].setBVisible(true);
						}
					}
				}
			}
			
		}
	}

	private ApoMarioEntity isThereAnEnemy(int x, int y, boolean bRealEnemies) {
		for (int i = 0; i < this.getGame().getLevel().getEnemies().size(); i++) {
			if (this.getGame().getLevel().getEnemies().get(i).intersects((float)x * (float)ApoMarioConstants.TILE_SIZE, ((float)y) * (float)ApoMarioConstants.TILE_SIZE, this.getGame().getLevel().getEnemies().get(i).getWidth(), this.getGame().getLevel().getEnemies().get(i).getHeight())) {
				if ((!bRealEnemies) && (this.getGame().getLevel().getEnemies().get(i) instanceof ApoMarioFlower)) {
				} else {
					return this.getGame().getLevel().getEnemies().get(i);
				}
			}
		}
		if (!bRealEnemies) {
			if ((this.getGame().getLevel().getLevelEntities()[y][x] != null) && 
				(this.getGame().getLevel().getLevelEntities()[y][x] instanceof ApoMarioWall) && 
				(((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[y][x])).isBTube())) {
				return this.getGame().getLevel().getLevelEntities()[y][x];
			} else if ((this.getGame().getLevel().getLevelEntities()[y][x] != null) && 
				(this.getGame().getLevel().getLevelEntities()[y][x] instanceof ApoMarioWall) && 
				(((ApoMarioWall)(this.getGame().getLevel().getLevelEntities()[y][x])).isBCanon())) {
					return this.getGame().getLevel().getLevelEntities()[y][x];
			} else if ((this.getGame().getLevel().getLevelEntities()[y][x] != null) && 
				(this.getGame().getLevel().getLevelEntities()[y][x] instanceof ApoMarioQuestionmark)) {
					return this.getGame().getLevel().getLevelEntities()[y][x];
			} else if ((this.getGame().getLevel().getLevelEntities()[y][x] != null) && 
				(this.getGame().getLevel().getLevelEntities()[y][x] instanceof ApoMarioDestructableWall)) {
				return this.getGame().getLevel().getLevelEntities()[y][x];
			}
		}
		return null;
	}
	
	private boolean deleteEnemy(int x, int y) {
		for (int i = 0; i < this.getGame().getLevel().getEnemies().size(); i++) {
			if (this.getGame().getLevel().getEnemies().get(i).intersects((float)x * (float)ApoMarioConstants.TILE_SIZE, ((float)y) * (float)ApoMarioConstants.TILE_SIZE, this.getGame().getLevel().getEnemies().get(i).getWidth(), this.getGame().getLevel().getEnemies().get(i).getHeight())) {
				this.getGame().getLevel().getEnemies().remove(i);
				return true;
			}
		}
		return false;
	}
	
	private boolean addEnemy(int x, int y, int enemy) {
		if (enemy == 0) {
			this.getGame().getLevel().makeGumba(x, y, false, false, true);
		} else {
			this.getGame().getLevel().makeKoopa(true, x, y, false, false, true);			
		}
		return false;
	}
	
	private void checkFunction(String function, int choose) {
		if (function.equals(ApoMarioEditor.FUNCTION_SET)) {
			int width = (int)(this.dragWidth.getPercent() * (ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH) / 100f) + ApoMarioConstants.MIN_WIDTH;
			this.getGame().getLevel().setWidth(width);
			int difficulty = (int)(this.dragDifficulty.getPercent() * (ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY) / 100f) + ApoMarioConstants.MIN_DIFFICULTY;
			this.getGame().getLevel().setDifficulty(difficulty);
			this.getGame().getLevel().makeEmptyLevel();
		} else if (function.equals(ApoMarioEditor.FUNCTION_RANDOM)) {
			int width = (int)(this.dragWidth.getPercent() * (ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH) / 100f) + ApoMarioConstants.MIN_WIDTH;
			this.getGame().getLevel().setWidth(width);
			int difficulty = (int)(this.dragDifficulty.getPercent() * (ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY) / 100f) + ApoMarioConstants.MIN_DIFFICULTY;
			this.getGame().getLevel().setDifficulty(difficulty);
			this.getGame().getLevel().makeLevel(System.nanoTime(), true, true, width, difficulty);
		} else if (function.equals(ApoMarioEditor.FUNCTION_LOAD)) {
			this.getGame().loadEditorLevel();
		} else if (function.equals(ApoMarioEditor.FUNCTION_SAVE)) {
			this.getGame().saveEditorLevel();
		} else if (function.equals(ApoMarioEditor.FUNCTION_MENU)) {
			this.getGame().getLevel().setLevelInt(-1);
			this.getGame().setMenu();
		} else if (function.equals(ApoMarioEditor.FUNCTION_TEST)) {
			this.getGame().getLevel().setLevelInt(-1);
			this.getGame().setGame(false, true);
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_FLY)) {
			((ApoMarioOptionsButton)(this.editorButtons[choose])).setBActive(!((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioGumba)) {
				ApoMarioGumba gumba = (ApoMarioGumba)(this.curChooseEntity);
				gumba.setBFlyOriginal(((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_FALL)) {
			((ApoMarioOptionsButton)(this.editorButtons[choose])).setBActive(!((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioGumba)) {
				ApoMarioGumba gumba = (ApoMarioGumba)(this.curChooseEntity);
				gumba.setBFall(((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_LEFT)) {
			((ApoMarioOptionsButton)(this.editorButtons[choose])).setBActive(!((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioGumba)) {
				ApoMarioGumba gumba = (ApoMarioGumba)(this.curChooseEntity);
				gumba.setBLeft(((ApoMarioOptionsButton)(this.editorButtons[choose])).isBActive());
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_IMMORTAL)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioGumba) && (!(this.curChooseEntity instanceof ApoMarioKoopa))) {
				ApoMarioGumba gumba = (ApoMarioGumba)(this.curChooseEntity);
				gumba.setBImmortal(button.isBActive());
				if (button.isBActive()) {
					gumba.setIOriginal(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 7 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE));
				} else {
					if ((ApoMarioImageContainer.TILE_SET == ApoMarioConstants.TILE_SET_EAT) && (Math.random() * 100 > 50)) {
						gumba.setIOriginal(ApoMarioImageContainer.ENEMIES.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE));			
					} else {
						gumba.setIOriginal(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE));
					}
				}
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_ENEMYFLOWER)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioWall)) {
				ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
				if (wall.isBTube()) {
					Point p = wall.getTubePoint();
					if (p == null) {
						p = new Point(0, 0);
					}
					int x = (int)(wall.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
					int y = (int)(wall.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
					if ((button.isBActive()) && (isThereAnEnemy(x, y, true) == null)) {
						BufferedImage iFlower = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
						Graphics2D g = iFlower.createGraphics();
						g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 12 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
						g.dispose();
						this.getGame().getLevel().getEnemies().add(new ApoMarioFlower(iFlower, (int)((x + 0.5f) * ApoMarioConstants.TILE_SIZE), (int)(y * ApoMarioConstants.TILE_SIZE), ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.ENEMY_ANIMATION_FRAMES, ApoMarioConstants.FLOWER_ANIMATION_TIME, 3000, 3000, ++ApoMarioLevel.ID));
						for (int i = 20; i < 24; i++) {
							this.editorButtons[i].setBVisible(true);
						}
					} else if ((!button.isBActive()) && (isThereAnEnemy(x, y, true) != null)) {
						this.deleteEnemy(x, y);
						for (int i = 20; i < 24; i++) {
							this.editorButtons[i].setBVisible(false);
						}
					}
				}
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_QUESTION_GOODIE)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioQuestionmark)) {
				ApoMarioQuestionmark wall = (ApoMarioQuestionmark)(this.curChooseEntity);
				if (button.isBActive()) {
					wall.setGoodie(ApoMarioConstants.GOODIE_GOODIE);
				} else {
					wall.setGoodie(ApoMarioConstants.GOODIE_COIN);
				}
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioDestructableWall)) {
				ApoMarioDestructableWall wall = (ApoMarioDestructableWall)(this.curChooseEntity);
				if (!button.isBActive()) {
					BufferedImage iWall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
					Graphics2D g = iWall.createGraphics();
					g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
					g.dispose();
					wall.setIWall(iWall);
				} else {
					wall.setIWall(null);
					((ApoMarioOptionsButton)(this.editorButtons[27])).setBActive(false);
					((ApoMarioOptionsButton)(this.editorButtons[28])).setBActive(false);
					wall.setGoodie(-1);
				}
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE_COIN)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioDestructableWall)) {
				ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
				if (button.isBActive()) {
					wall.setGoodie(ApoMarioConstants.GOODIE_COIN);
					ApoMarioOptionsButton otherButton = (ApoMarioOptionsButton)(this.editorButtons[28]);
					otherButton.setBActive(false);
					if (((ApoMarioOptionsButton)(this.editorButtons[26])).isBActive()) {
						this.checkFunction(ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE, 26);
					}
				} else {
					wall.setGoodie(-1);
				}
			}
		} else  if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE_GOODIE)) {
			ApoMarioOptionsButton button = (ApoMarioOptionsButton)(this.editorButtons[choose]);
			button.setBActive(!button.isBActive());
			if ((this.curChooseEntity != null) && (this.curChooseEntity instanceof ApoMarioDestructableWall)) {
				ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
				if (button.isBActive()) {
					wall.setGoodie(ApoMarioConstants.GOODIE_GOODIE);
					ApoMarioOptionsButton otherButton = (ApoMarioOptionsButton)(this.editorButtons[27]);
					otherButton.setBActive(false);
					if (((ApoMarioOptionsButton)(this.editorButtons[26])).isBActive()) {
						this.checkFunction(ApoMarioEditor.FUNCTION_DETAIL_BREAKABLE, 26);
					}
				} else {
					wall.setGoodie(-1);
				}
			}
		} else if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_LEFTSTART)) {
			this.setStartTime(-1);
		} else if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_RIGHTSTART)) {
			this.setStartTime(+1);
		} else if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_LEFTTIMESHOW)) {
			this.setShootTime(-1);
		} else if (function.equals(ApoMarioEditor.FUNCTION_DETAIL_RIGHTTIMESHOW)) {
			this.setShootTime(+1);
		} else if (choose < 16) {
			this.setCurChoose(choose, function);
		}
	}
	
	private void setStartTime(int plus) {
		if (this.curChooseEntity == null) {
			return;
		}
		if (this.curChooseEntity instanceof ApoMarioWall) {
			ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
			if (wall.isBTube()) {
				Point p = wall.getTubePoint();
				if (p == null) {
					p = new Point(0, 0);
				}
				int x = (int)(wall.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
				int y = (int)(wall.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
				try {
					ApoMarioFlower flower = (ApoMarioFlower)(isThereAnEnemy(x, y, true));
					if (flower != null) {
						flower.setStartTime(flower.getStartTime() + 10 * plus);
						if (flower.getStartTime() < 1500) {
							flower.setStartTime(1500);
						} else if (flower.getStartTime() > 7000) {
							flower.setStartTime(7000);
						}
					}
				} catch (Exception ex) {
				}
			} else if (wall.isBCanon()) {
				ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
				canon.setStartTime(canon.getStartTime() + 10 * plus);
				if (canon.getStartTime() < 1500) {
					canon.setStartTime(1500);
				} else if (canon.getStartTime() > 7000) {
					canon.setStartTime(7000);
				}
			}
		}
	}
	
	private void setShootTime(int plus) {
		if (this.curChooseEntity == null) {
			return;
		}
		if (this.curChooseEntity instanceof ApoMarioWall) {
			ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
			if (wall.isBTube()) {
				Point p = wall.getTubePoint();
				if (p == null) {
					p = new Point(0, 0);
				}
				int x = (int)(wall.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
				int y = (int)(wall.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
				try {
					ApoMarioFlower flower = (ApoMarioFlower)(isThereAnEnemy(x, y, true));
					if (flower != null) {
						flower.setTimeToShow(flower.getTimeToShow() + 10 * plus);
						if (flower.getTimeToShow() < 1500) {
							flower.setTimeToShow(1500);
						} else if (flower.getTimeToShow() > 7000) {
							flower.setTimeToShow(7000);
						}
					}
				} catch (Exception ex) {
				}
			} else if (wall.isBCanon()) {
				ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
				canon.setShootTime(canon.getShootTime() + 10 * plus);
				if (canon.getShootTime() < 1500) {
					canon.setShootTime(1500);
				} else if (canon.getShootTime() > 8000) {
					canon.setShootTime(8000);
				}
			}
		}
	}
	
	private BufferedImage getCurChooseImage(String function, boolean bAlpha) {
		BufferedImage iImageLoad = null;
		if (function.equals(ApoMarioEditor.FUNCTION_WALL)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_WALL_NO_COLLISION)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_WALL_COLLISION)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_WALL_BREAKABLE)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_WALL_QUESTION)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(5 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_WALL_COIN)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_ENEMY_FLOWER)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(10 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_ENEMY_CANON)) {
			iImageLoad = ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_ENEMY_GUMBA)) {
			iImageLoad = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 5 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		} else if (function.equals(ApoMarioEditor.FUNCTION_ENEMY_KOOPA)) {
			iImageLoad = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE);
		}
		BufferedImage iImage = null;
		if (iImageLoad != null) {
			iImage = new BufferedImage(iImageLoad.getWidth(), iImageLoad.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = iImage.createGraphics();
			if (bAlpha) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			}
			g.drawImage(iImageLoad, 0, 0, null);
			g.dispose();
		}
		return iImage;
	}
	
	private void setCurChoose(int curChoose, String function) {
		this.curChoose = curChoose;
		this.iCurChoose = this.getCurChooseImage(function, true);
		this.curFunction = function;
	}

	@Override
	public boolean mouseDragged(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;
		this.mouseButtonReleased(x, y);
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		if (this.dragWidth != null) {
			if (this.dragWidth.mouseMoved(x, y)) {
				return true;
			}
		}
		if (this.dragDifficulty != null) {
			if (this.dragDifficulty.mouseMoved(x, y)) {
				return true;
			}
		}

		if (this.editorButtons != null) {
			for (int i = 0; i < this.editorButtons.length; i++) {
				try {
					if (this.editorButtons[i].getMove(x, y)) {
					}
				} catch (Exception ex) {
					
				}
			}
		}
		this.mouseX = x;
		this.mouseY = y;
		return false;
	}

	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.editorButtons != null) {
			for (int i = 0; i < this.editorButtons.length; i++) {
				if (this.editorButtons[i].getPressed(x, y)) {
				}
			}
		}
		this.bRight = bRight;
		return false;
	}
	
	public void changeCamera(float myChangeX) {
		float x = ((float)this.getGame().getChangeX() + (float)myChangeX) * (float)ApoMarioConstants.APP_SIZE;
		
		float changeX = x;
		if (changeX <= 0) {
			changeX = 0;
		} else if (changeX + ApoMarioConstants.GAME_WIDTH - this.WIDTH >= this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE) {
			changeX = this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE - ApoMarioConstants.GAME_WIDTH + this.WIDTH;
		}
		if ((changeX != this.getGame().getChangeX()) || (changeX <= 0)) {
			this.getGame().setChangeX((float)changeX / (float)ApoMarioConstants.APP_SIZE);
		}
	}

	protected int getMouseXInLevel() {
		return this.getMouseXInLevel(this.mouseX);
	}
	
	private int getMouseXInLevel(int x) {
		return (int)((float)x / (float)ApoMarioConstants.TILE_SIZE / (float)ApoMarioConstants.APP_SIZE + this.getChangeX());
	}
	
	private float getChangeX() {
		 return (this.getGame().getChangeX() / ApoMarioConstants.TILE_SIZE);
	}
	
	private int getMouseYInLevel() {
		return this.getMouseYInLevel(this.mouseY);
	}
	
	private int getMouseYInLevel(int y) {
		return (int)(y / ApoMarioConstants.TILE_SIZE / ApoMarioConstants.APP_SIZE);
	}
	
	@Override
	public void think(int delta) {
		if ((this.getGame().getKeys()[KeyEvent.VK_LEFT]) || (this.mouseX < 10)) {
			this.changeCamera(-(200f / 1000f * (float)delta));
		} else if ((this.getGame().getKeys()[KeyEvent.VK_RIGHT]) || ((this.mouseX < ApoMarioConstants.GAME_WIDTH - this.WIDTH) && (this.mouseX > ApoMarioConstants.GAME_WIDTH - this.WIDTH - 10))) {
			this.changeCamera((200f / 1000f * (float)delta));
		}
		for (int i = 20; i < 24; i++) {
			if (this.editorButtons[i].isBWait()) {
				int wait = this.editorButtons[i].getWait();
				this.editorButtons[i].think(delta);
				if (this.editorButtons[i].getWait() < wait) {
					this.checkFunction(this.editorButtons[i].getFunction(), i);
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		Shape shape = g.getClip();
		g.setClip(0, 0, ApoMarioConstants.GAME_WIDTH - this.WIDTH, ApoMarioConstants.GAME_HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		if (this.getGame().getLevel() != null) {
			this.getGame().getLevel().render(g, (int)this.getGame().getChangeX(), (int)this.getGame().getChangeY());
		}
		this.getGame().getLevel().renderEnemies(g, (int)this.getGame().getChangeX(), (int)this.getGame().getChangeY(), true);
		g.setClip(shape);
		
		this.renderGrid(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(ApoMarioConstants.GAME_WIDTH - this.WIDTH, 0, this.WIDTH, ApoMarioConstants.GAME_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(ApoMarioConstants.GAME_WIDTH - this.WIDTH, 0, this.WIDTH, ApoMarioConstants.GAME_HEIGHT);

		g.drawLine(ApoMarioConstants.GAME_WIDTH - this.WIDTH, (int)(this.editorButtons[0].getY() + this.editorButtons[0].getHeight() + 4), ApoMarioConstants.GAME_WIDTH, (int)(this.editorButtons[0].getY() + this.editorButtons[0].getHeight() + 4));
		g.drawLine(ApoMarioConstants.GAME_WIDTH - this.WIDTH, (int)(this.editorButtons[11].getY() + this.editorButtons[11].getHeight() + 4), ApoMarioConstants.GAME_WIDTH, (int)(this.editorButtons[11].getY() + this.editorButtons[11].getHeight() + 4));
		g.drawLine(ApoMarioConstants.GAME_WIDTH - this.WIDTH, (int)(this.editorButtons[12].getY() - 4), ApoMarioConstants.GAME_WIDTH, (int)(this.editorButtons[12].getY() - 4));
		
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		if (this.dragWidth != null) {
			String s = "Levelwidth: "+String.valueOf((int)(this.dragWidth.getPercent() * (ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH) / 100f) + ApoMarioConstants.MIN_WIDTH);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, (ApoMarioConstants.GAME_WIDTH - this.WIDTH) + this.WIDTH/2 - w/2, this.dragWidth.getY() - 2);
			this.dragWidth.render(g, 0, 0);
		}
		g.setColor(Color.BLACK);
		if (this.dragDifficulty != null) {
			String s = "Difficulty: "+String.valueOf((int)(this.dragDifficulty.getPercent() * (ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY) / 100f) + ApoMarioConstants.MIN_DIFFICULTY);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, (ApoMarioConstants.GAME_WIDTH - this.WIDTH) + this.WIDTH/2 - w/2, this.dragDifficulty.getY() - 2);
			this.dragDifficulty.render(g, 0, 0);
		}
		
		for (int i = 0; i < this.editorButtons.length; i++) {
			if ((this.editorButtons[i] != null) && (this.editorButtons[i].isBVisible())) {
				this.editorButtons[i].render(g, 0, 0);
			} else if (this.editorButtons[i] == null) {
				System.out.println("i: "+i);
			}
		}
		
		if (this.curChooseEntity != null) {
			int y = (int)(this.editorButtons[11].getY() + this.editorButtons[11].getHeight() + 6);
			int x = (int)(ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5);
			
			BufferedImage iImage = this.curChooseEntity.getCurFrameImage();
			g.drawImage(iImage, x, y, null);
			
			String s = "x: "+(this.curChooseEntity.getX() / ApoMarioConstants.TILE_SIZE) + ", y: "+(this.curChooseEntity.getY() / ApoMarioConstants.TILE_SIZE);
			g.drawString(s, x + iImage.getWidth() + 5, this.editorButtons[16].getY() - 5);
			
			if (this.editorButtons[20].isBVisible()) {
				if (this.curChooseEntity instanceof ApoMarioWall) {
					ApoMarioWall wall = (ApoMarioWall)this.curChooseEntity;
					if (wall.isBTube()) {
						try {
							ApoMarioFlower flower = (ApoMarioFlower)this.isThereAnEnemy((int)(wall.getX() / ApoMarioConstants.TILE_SIZE), (int)(wall.getY() / ApoMarioConstants.TILE_SIZE), true);
							s = String.valueOf(flower.getTimeToShow());
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[20].getY() + this.editorButtons[20].getHeight());
							
							s = "showrepeat time";
							w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[20].getY() + this.editorButtons[20].getHeight() - g.getFontMetrics().getHeight() + 1 * g.getFontMetrics().getDescent());
						} catch (Throwable ex) {
						}
					} else if (wall.isBCanon()) {
						try {
							ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
							s = String.valueOf(canon.getShootTime());
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[20].getY() + this.editorButtons[20].getHeight());
							
							s = "shootrepeat time";
							w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[20].getY() + this.editorButtons[20].getHeight() - g.getFontMetrics().getHeight() + 1 * g.getFontMetrics().getDescent());
						} catch (Throwable ex) {
						}
					}
				}
			}
			
			if (this.editorButtons[22].isBVisible()) {
				if (this.curChooseEntity instanceof ApoMarioWall) {
					ApoMarioWall wall = (ApoMarioWall)this.curChooseEntity;
					if (wall.isBTube()) {
						try {
							ApoMarioFlower flower = (ApoMarioFlower)this.isThereAnEnemy((int)(wall.getX() / ApoMarioConstants.TILE_SIZE), (int)(wall.getY() / ApoMarioConstants.TILE_SIZE), true);
							s = String.valueOf(flower.getStartTime());
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[22].getY() + this.editorButtons[22].getHeight());
						} catch (Throwable ex) {
						}
					} else if (wall.isBCanon()) {
						try {
							ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
							s = String.valueOf(canon.getStartTime());
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[22].getY() + this.editorButtons[22].getHeight());
						} catch (Throwable ex) {
						}
					}
					s = "start time";
					int w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, ApoMarioConstants.GAME_WIDTH - this.WIDTH/2 - w/2, this.editorButtons[22].getY() + this.editorButtons[22].getHeight() - g.getFontMetrics().getHeight() + 1 * g.getFontMetrics().getDescent());
				}
			}
		}
		
		if (this.curChoose > 0) {
			g.setColor(Color.BLACK);
			String s = "curChoose: ";
			int w = g.getFontMetrics().stringWidth(s);
			int x = ApoMarioConstants.GAME_WIDTH - this.WIDTH + 5 + w;
			int y = (int)(this.editorButtons[2].getY() - 5 - this.editorButtons[this.curChoose].getIBackground().getHeight());
			g.drawString(s, x - w, y + g.getFontMetrics().getHeight());
			g.drawImage(this.editorButtons[this.curChoose].getIBackground().getSubimage(0, 0, this.editorButtons[this.curChoose].getIBackground().getWidth()/3, this.editorButtons[this.curChoose].getIBackground().getHeight()), x, y, null);
		}
		
		g.setColor(Color.BLACK);
		int size = ApoMarioConstants.TILE_SIZE;
		g.drawString("Pos: "+this.getGame().getChangeX() / size, 5, 25);
	}
	
	private void renderGrid(Graphics2D g) {
		g.setColor(Color.BLACK);
		int size = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
		float myChangeXFloat = this.getChangeX();
		int myChangeX = (int)((float)(myChangeXFloat - (int)(myChangeXFloat)) * (float)size);
		int width = (ApoMarioConstants.GAME_WIDTH - this.WIDTH) / size + 2;
		int height = (ApoMarioConstants.GAME_HEIGHT) / size + 1;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				g.drawRect(x * size - myChangeX, y * size, size, size);
			}
		}
		if ((this.iCurChoose != null) && (this.mouseX >= 0) && (this.mouseY >= 0) && (this.mouseX < ApoMarioConstants.GAME_WIDTH - this.WIDTH - this.iCurChoose.getWidth())) {
			int x = (int)((float)this.getMouseXInLevel());
			int y = this.getMouseYInLevel();
			g.drawImage(this.iCurChoose, x * size - (int)(this.getChangeX() * size), y * size, null);
		}
	}

}
