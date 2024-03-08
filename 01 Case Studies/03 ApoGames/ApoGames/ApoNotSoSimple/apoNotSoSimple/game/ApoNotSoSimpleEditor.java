package apoNotSoSimple.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoNewTextfield;
import org.apogames.help.ApoHelp;
import org.apogames.help.ApoHighscore;

import apoNotSoSimple.ApoNotSoSimpleConstants;
import apoNotSoSimple.ApoNotSoSimpleImages;
import apoNotSoSimple.entity.ApoNotSoSimpleString;
import apoNotSoSimple.level.ApoNotSoSimpleLevel;

public class ApoNotSoSimpleEditor extends ApoNotSoSimpleModel {

	public static final int MAX_TIME_RENDER = 100;
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";
	public static final String LEFT_LAYER = "credits_left_layer";
	public static final String RIGHT_LAYER = "credits_right_layer";
	public static final String UPLOAD = "credits_upload";
	public static final String TEST = "credits_test";
	public static final String FIXED = "credits_fixed";
	public static final String PLAYER = "credits_player";
	public static final String FINISH = "credits_finish";
	public static final String LEFT = "credits_left";
	public static final String RIGHT = "credits_right";
	public static final String UP = "credits_up";
	public static final String DOWN = "credits_down";
	public static final String VISIBLE_TRUE = "credits_true";
	public static final String VISIBLE_FALSE = "credits_false";
	public static final String STEP = "credits_step";
	public static final String STEP_FINISH = "credits_step_finish";

	private BufferedImage iBackground;
	
	private ApoNotSoSimpleLevel level;
	
	private ArrayList<ApoNotSoSimpleString> strings;
	
	/** derzeit ausgewähltes Tile */
	private byte selection = -1;
	/** Bild des derzeit ausgewähltem Tile */
	private BufferedImage iSelection, iRealSelection;
	/** aktuelle Mausposition in Leveldatenform */
	private Point curMousePosition;
	
	private int curMouseLayer;
	
	private boolean bUpload;
	
	private int curTime;
	
	private ApoNewTextfield textFieldUsername;
	private ApoNewTextfield textFieldLevelname;
	private ApoNewTextfield textFieldDescription;
	
	public ApoNotSoSimpleEditor(ApoNotSoSimplePanel game) {
		super(game);
		
		this.init();
	}
	
	@Override
	public void init() {
		this.bUpload = false;
		if (this.textFieldUsername == null) {
			int width = 250;
			int height = 30;
			int x = ApoNotSoSimpleConstants.LEVEL_WIDTH/2 - 40;
			int y = ApoConstants.GAME_HEIGHT - height - 20;
			this.textFieldUsername = new ApoNewTextfield(x, y, width, height, ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
			this.textFieldUsername.setMaxLength(15);
			this.textFieldUsername.setCurString("Mister X");
		}
		if (this.textFieldLevelname == null) {
			int width = 250;
			int height = 30;
			int x = ApoNotSoSimpleConstants.LEVEL_WIDTH/2 - 40;
			int y = 15;
			this.textFieldLevelname = new ApoNewTextfield(x, y, width, height, ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
			this.textFieldLevelname.setMaxLength(15);
			this.textFieldLevelname.setCurString("My Level");
		}
		if (this.textFieldDescription == null) {
			int width = 300;
			int height = 30;
			int x = ApoNotSoSimpleConstants.LEVEL_WIDTH/2 - 70;
			int y = ApoConstants.GAME_HEIGHT/2 - height - 80;
			this.textFieldDescription = new ApoNewTextfield(x, y, width, height, ApoNotSoSimpleConstants.FONT_DESCRIPTIONS);
			this.textFieldDescription.setMaxLength(30);
			this.textFieldDescription.setCurString("User-generated level");
		}
		if (this.level == null) {
			byte[][][] array = new byte[1][3][10];
			array[0][1][0] = ApoNotSoSimpleConstants.LEVEL_PLAYER;
			array[0][1][9] = ApoNotSoSimpleConstants.LEVEL_FINISH;
			this.level = new ApoNotSoSimpleLevel(array, new String[2], "");
		}
		if (this.curMousePosition == null) {
			this.curMousePosition = new Point(-1, -1);
		}
		this.strings = new ArrayList<ApoNotSoSimpleString>();
		this.curTime = 0;
		this.curMouseLayer = -1;
		this.level.restart();
		this.makeBackground();
		if (this.selection <= 0) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_FIXED);
		}
	}
	
	private boolean checkKeyReleased(final ApoNewTextfield textField, int button, char character) {
		if ((textField != null) && (textField.isBVisible()) && (textField.isBSelect())) {
			boolean bShift = false;
			if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_CONTROL)) {
				if (button == KeyEvent.VK_V) {
					String s = ApoHelp.getClipboardContents();
					if (s != null) {
						for (int i = 0; i < s.length(); i++) {
							char chara = s.charAt(i);
							textField.addCharacter(button, chara);
						}
						bShift = true;
						this.getGame().render();
					}
				} else if (button == KeyEvent.VK_C) {
					String s = textField.getSelectedString();
					if (s != null) {
						ApoHelp.setClipboardContents(s);
					}
					bShift = true;
					this.getGame().render();
				} else if (button == KeyEvent.VK_X) {
					String s = textField.getSelectedString();
					if (s != null) {
						ApoHelp.setClipboardContents(s);
						textField.deleteSelectedString();
					}
					bShift = true;
					this.getGame().render();
				}
			} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_SHIFT)) {
				if (button == KeyEvent.VK_LEFT) {
					textField.nextSelectedPosition(textField.getPosition() - 1);
					bShift = true;
					this.getGame().render();
				} else if (button == KeyEvent.VK_RIGHT) {
					textField.nextSelectedPosition(textField.getPosition() + 1);
					bShift = true;
					this.getGame().render();
				}
			}
			if (!bShift) {
				textField.addCharacter(button, character);
				this.getGame().render();
			}
			return true;
		}
		return false;
	}
	
	public boolean isbUpload() {
		return this.bUpload;
	}

	public void setbUpload(boolean bUpload) {
		this.bUpload = bUpload;
		this.getGame().getButtons()[8].setBVisible(bUpload);
		if (this.bUpload) {
			this.strings.add(new ApoNotSoSimpleString(ApoConstants.GAME_WIDTH/2 - 60, ApoConstants.GAME_HEIGHT/2 - 10, 200, "Upload possible", true));
			this.strings.add(new ApoNotSoSimpleString(ApoConstants.GAME_WIDTH/2 - 100, ApoConstants.GAME_HEIGHT/2 + 35, 200, "Click the upload button", true));
		}
	}

	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.getGame().renderBackground(g);
		
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		
		g.setColor(Color.DARK_GRAY);
		int startX = 5;
		int startY = 5;
		int width = (int)(65) * 7 + 5;
		int height = (int)(65) * 7 + 5;
		g.drawRoundRect(startX, startY, width, height, 20, 20);
		
		this.level.renderBackground(g, 10, 0);
		
		g.setStroke(stroke);
		
		g.dispose();
	}
	
	/**
	 * wechselt die Auswahl des ausgewählten Tiles
	 * @param newSelection : neu ausgewähltes Tile
	 */
	private void changeSelection(final int newSelection) {
		this.selection = (byte)newSelection;
		if (this.selection < 1) {
			this.selection = ApoNotSoSimpleConstants.LEVEL_STEP_FINISH;
		} else if (this.selection > ApoNotSoSimpleConstants.LEVEL_STEP_FINISH) {
			this.selection = 1;
		}
		BufferedImage iNewSelection = null;
		if (this.selection == ApoNotSoSimpleConstants.LEVEL_FIXED) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_FIXED;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_UP) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_UP;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_DOWN) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_DOWN;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_LEFT) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_LEFT;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_RIGHT) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_RIGHT;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_FINISH) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_FINISH;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_PLAYER) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_PLAYER;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_VISIBLE_TRUE) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_VISIBLE_FALSE) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_STEP) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_STEP;
		} else if (this.selection == ApoNotSoSimpleConstants.LEVEL_STEP_FINISH) {
			iNewSelection = ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH;
		}
		if (iNewSelection != null) {
			this.iRealSelection = new BufferedImage(iNewSelection.getWidth(), iNewSelection.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iRealSelection.createGraphics();

			g.drawImage(iNewSelection, 0, 0, null);
			
			g.dispose();
			
			this.iSelection = new BufferedImage(iNewSelection.getWidth(), iNewSelection.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			g = this.iSelection.createGraphics();
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g.drawImage(iNewSelection, 0, 0, null);
			
			g.dispose();
		}
		this.getGame().render();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		} else {
			if ((!this.checkKeyReleased(this.textFieldLevelname, button, character)) &&
				(!this.checkKeyReleased(this.textFieldUsername, button, character)) &&
				(!this.checkKeyReleased(this.textFieldDescription, button, character))) {
				if ((button == KeyEvent.VK_T) || (button == KeyEvent.VK_ENTER)) {
					this.getGame().setGameWithLevel(this.level);
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoNotSoSimpleEditor.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoNotSoSimpleEditor.TEST)) {
			this.getGame().setGameWithLevel(this.level);
		} else if (function.equals(ApoNotSoSimpleEditor.UPLOAD)) {
			ApoHighscore.getInstance().save(this.level.getLevel(), this.textFieldLevelname.getText(), this.textFieldUsername.getText(), this.textFieldDescription.getText(), this.getGame().getSolution());
			this.getGame().loadHighscore();
			this.strings.add(new ApoNotSoSimpleString(ApoNotSoSimpleConstants.LEVEL_WIDTH/2 - 100 + 10, ApoConstants.GAME_HEIGHT/2 - 100, 200, "Upload successful", true));
		} else if (function.equals(ApoNotSoSimpleEditor.LEFT_LAYER)) {
			if (this.level.getLayer() > 1) {
				this.textFieldDescription.setY(ApoConstants.GAME_HEIGHT/2 - this.textFieldDescription.getHeight() - 80);
				byte[][][] array = new byte[1][3][10];
				array[0] = this.level.getLevel()[0];
				this.level.setLevel(array);
				this.level.restart();
				this.makeBackground();
			}
		} else if (function.equals(ApoNotSoSimpleEditor.RIGHT_LAYER)) {
			if (this.level.getLayer() <= 1) {
				this.textFieldDescription.setY(ApoConstants.GAME_HEIGHT/2 - this.textFieldDescription.getHeight()/2);
				byte[][][] array = new byte[2][3][10];
				array[0] = this.level.getLevel()[0];
				array[1][1][0] = ApoNotSoSimpleConstants.LEVEL_PLAYER;
				array[1][1][9] = ApoNotSoSimpleConstants.LEVEL_FINISH;
				this.level.setLevel(array);
				this.level.restart();
				this.makeBackground();
			}
		} else if (function.equals(ApoNotSoSimpleEditor.LEFT)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_LEFT);
		} else if (function.equals(ApoNotSoSimpleEditor.RIGHT)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_RIGHT);
		} else if (function.equals(ApoNotSoSimpleEditor.UP)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_UP);
		} else if (function.equals(ApoNotSoSimpleEditor.DOWN)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_DOWN);
		} else if (function.equals(ApoNotSoSimpleEditor.VISIBLE_TRUE)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_VISIBLE_TRUE);
		} else if (function.equals(ApoNotSoSimpleEditor.VISIBLE_FALSE)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_VISIBLE_FALSE);
		} else if (function.equals(ApoNotSoSimpleEditor.STEP)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_STEP);
		} else if (function.equals(ApoNotSoSimpleEditor.STEP_FINISH)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_STEP_FINISH);
		} else if (function.equals(ApoNotSoSimpleEditor.PLAYER)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_PLAYER);
		} else if (function.equals(ApoNotSoSimpleEditor.FINISH)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_FINISH);
		} else if (function.equals(ApoNotSoSimpleEditor.FIXED)) {
			this.changeSelection(ApoNotSoSimpleConstants.LEVEL_FIXED);
		}
		this.textFieldLevelname.setBSelect(false);
		this.textFieldUsername.setBSelect(false);
		this.textFieldDescription.setBSelect(false);
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		this.mouseMoved(x, y);
		this.setSelection(bRight);
		
		if ((this.textFieldLevelname != null) && (this.textFieldLevelname.isBVisible())) {
			this.textFieldLevelname.mouseReleased(x, y);
			if (this.textFieldLevelname.intersects(x, y)) {
				this.textFieldLevelname.setBSelect(true);
			} else {
				this.textFieldLevelname.setBSelect(false);
			}
		}
		if ((this.textFieldUsername != null) && (this.textFieldUsername.isBVisible())) {
			this.textFieldUsername.mouseReleased(x, y);
			if (this.textFieldUsername.intersects(x, y)) {
				this.textFieldUsername.setBSelect(true);
			} else {
				this.textFieldUsername.setBSelect(false);
			}
		}
		if ((this.textFieldDescription != null) && (this.textFieldDescription.isBVisible())) {
			this.textFieldDescription.mouseReleased(x, y);
			if (this.textFieldDescription.intersects(x, y)) {
				this.textFieldDescription.setBSelect(true);
			} else {
				this.textFieldDescription.setBSelect(false);
			}
		}
	}
	
	public boolean mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if ((this.textFieldLevelname != null) && (this.textFieldLevelname.isBVisible())) {
			if (this.textFieldLevelname.getMove(x, y)) {
				return true;
			}
		}
		if ((this.textFieldUsername != null) && (this.textFieldUsername.isBVisible())) {
			if (this.textFieldUsername.getMove(x, y)) {
				return true;
			}
		}
		if ((this.textFieldDescription != null) && (this.textFieldDescription.isBVisible())) {
			if (this.textFieldDescription.getMove(x, y)) {
				return true;
			}
		}
		
		int changeX = 10;
		int changeY = this.level.getChangeY();
		if ((changeX < x) && (changeX + ApoNotSoSimpleConstants.LEVEL_WIDTH > x) &&
			(changeY < y) && (changeY + ApoNotSoSimpleConstants.LEVEL_HEIGHT > y)) {
			int pointX = (int)(x - changeX)/ApoNotSoSimpleConstants.TILE_SIZE;
			int pointY = (int)(y - changeY)/ApoNotSoSimpleConstants.TILE_SIZE;
			if ((pointX >= 0) && (pointX + 1 <= this.level.getLevel()[0][0].length) &&
				(pointY >= 0) && (pointY + 1 <= this.level.getLevel()[0].length)) {
				if ((this.curMousePosition.x != pointX) || (this.curMousePosition.y != pointY)) {
					this.curMousePosition = new Point(pointX, pointY);
					this.curMouseLayer = 0;
					this.getGame().render();
				}
			} else {
				this.curMousePosition = new Point(-1, -1);
				this.curMouseLayer = -1;
				this.getGame().render();
			}
		} else if (this.level.getLayer() == 2) {
			changeY += 5 * ApoNotSoSimpleConstants.TILE_SIZE;
			if ((changeX < x) && (changeX + ApoNotSoSimpleConstants.LEVEL_WIDTH > x) &&
				(changeY < y) && (changeY + ApoNotSoSimpleConstants.LEVEL_HEIGHT > y)) {
				int pointX = (int)(x - changeX)/ApoNotSoSimpleConstants.TILE_SIZE;
				int pointY = (int)(y - changeY)/ApoNotSoSimpleConstants.TILE_SIZE;
				if ((pointX >= 0) && (pointX + 1 <= this.level.getLevel()[0][0].length) &&
					(pointY >= 0) && (pointY + 1 <= this.level.getLevel()[0].length)) {
					if ((this.curMousePosition.x != pointX) || (this.curMousePosition.y != pointY)) {
						this.curMousePosition = new Point(pointX, pointY);
						this.curMouseLayer = 1;
						this.getGame().render();
					}
				} else {
					this.curMousePosition = new Point(-1, -1);
					this.curMouseLayer = -1;
					this.getGame().render();
				}
			} else {
				this.curMousePosition = new Point(-1, -1);
				this.curMouseLayer = -1;
				this.getGame().render();
			}
		} else {
			this.curMousePosition = new Point(-1, -1);
			this.curMouseLayer = -1;
			this.getGame().render();
		}
		return false;
	}
	
	private void makeAndRender() {
		this.setbUpload(false);
		this.level.restart();
		this.makeBackground();
		this.getGame().render();
	}
	
	public boolean mouseDragged(int x, int y, boolean bRight) {
		super.mouseDragged(x, y, bRight);
		this.mouseMoved(x, y);
		this.setSelection(bRight);
		return true;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if ((this.textFieldLevelname != null) && (this.textFieldLevelname.isBVisible())) {
			if (this.textFieldLevelname.mousePressed(x, y)) {
				return true;
			}
		}
		if ((this.textFieldUsername != null) && (this.textFieldUsername.isBVisible())) {
			if (this.textFieldUsername.mousePressed(x, y)) {
				return true;
			}
		}
		if ((this.textFieldDescription != null) && (this.textFieldDescription.isBVisible())) {
			if (this.textFieldDescription.mousePressed(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * wird aufgerufen, wenn die Maus losgelassen wird oder die Maus gezogen wird<br />
	 * sie setzt dann die Leveltiles, wenn benötigt<br />
	 * @param bRight : TRUE, rechte Maustaste gedrückt, ansonsten FALSE
	 */
	private void setSelection(final boolean bRight) {
		int x = this.curMousePosition.x;
		int y = this.curMousePosition.y;
		
		// falls die Maus überhaupt über dem Level ist
		if ((this.curMouseLayer >= 0) && (x >= 0) && (y >= 0) &&
			(x < this.level.getLevel()[this.curMouseLayer][0].length) && (y < this.level.getLevel()[this.curMouseLayer].length)) {
			if (bRight) {
				if ((this.level.getLevel()[this.curMouseLayer][y][x] != ApoNotSoSimpleConstants.LEVEL_FREE) &&
					(this.level.getLevel()[this.curMouseLayer][y][x] != ApoNotSoSimpleConstants.LEVEL_PLAYER) &&
					(this.level.getLevel()[this.curMouseLayer][y][x] != ApoNotSoSimpleConstants.LEVEL_FINISH)) {
					this.level.getLevel()[this.curMouseLayer][y][x] = ApoNotSoSimpleConstants.LEVEL_FREE;
					this.makeAndRender();
				}
			} else if (this.level.getLevel()[this.curMouseLayer][y][x] != this.selection) {
				if ((this.selection == ApoNotSoSimpleConstants.LEVEL_FINISH) || (this.selection == ApoNotSoSimpleConstants.LEVEL_PLAYER)) {
					for (int sY = 0; sY < this.level.getLevel()[this.curMouseLayer].length; sY++) {
						for (int sX = 0; sX < this.level.getLevel()[this.curMouseLayer][0].length; sX++) {
							if (this.level.getLevel()[this.curMouseLayer][sY][sX] == this.selection) {
								this.level.getLevel()[this.curMouseLayer][sY][sX] = ApoNotSoSimpleConstants.LEVEL_FREE;
							}
						}
					}
					this.level.getLevel()[this.curMouseLayer][y][x] = this.selection;
					this.makeAndRender();
				} else {
					if ((this.level.getLevel()[this.curMouseLayer][y][x] != ApoNotSoSimpleConstants.LEVEL_FINISH) && 
						(this.level.getLevel()[this.curMouseLayer][y][x] != ApoNotSoSimpleConstants.LEVEL_PLAYER)) {
						this.level.getLevel()[this.curMouseLayer][y][x] = this.selection;
						this.makeAndRender();
					}
				}
			}
		}
	}

	@Override
	public void think(int delta) {
		if (this.textFieldLevelname != null) {
			this.textFieldLevelname.think(delta);
		}
		if (this.textFieldUsername != null) {
			this.textFieldUsername.think(delta);
		}
		if (this.textFieldDescription != null) {
			this.textFieldDescription.think(delta);
		}
		this.curTime += delta;
		if (this.curTime > ApoNotSoSimpleEditor.MAX_TIME_RENDER) {
			this.curTime -= ApoNotSoSimpleEditor.MAX_TIME_RENDER;
			this.getGame().render();
		}
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
			
			this.level.render(g, 10, 0, true);
			
			if (this.selection >= 0) {
				g.setColor(Color.RED);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(3));
				g.drawOval((int)(this.getGame().getButtons()[this.selection + 10].getX() + 5), (int)(this.getGame().getButtons()[this.selection + 10].getY() + 5), (int)(this.getGame().getButtons()[this.selection + 10].getWidth() - 10), (int)(this.getGame().getButtons()[this.selection + 10].getHeight() - 10));
				g.setStroke(stroke);
			}
			g.setColor(Color.BLACK);
			g.setFont(ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
			String s = String.valueOf("Layer");
			int w = g.getFontMetrics().stringWidth(s);
			final int x = (ApoConstants.GAME_WIDTH - 10 - ApoNotSoSimpleConstants.LEVEL_WIDTH)/2 + 10 + ApoNotSoSimpleConstants.LEVEL_WIDTH;
			g.drawString(s, x - w/2, 40);
			
			s = String.valueOf(this.level.getLayer());
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, 70);
			
			if (this.curMouseLayer >= 0) {
				int changeY = this.level.getChangeY();
				if (this.curMouseLayer >= 1) {
					changeY += (curMouseLayer) * ApoNotSoSimpleConstants.TILE_SIZE * 5;
				}
				g.drawImage(this.iSelection, this.curMousePosition.x * ApoNotSoSimpleConstants.TILE_SIZE + 10, changeY + this.curMousePosition.y * ApoNotSoSimpleConstants.TILE_SIZE, null);
			}
			
			if (this.textFieldLevelname.isBVisible()) {
				this.textFieldLevelname.render(g, 0, 0);
				
				g.setFont(ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
				s = "Levelname: ";
				w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(this.textFieldLevelname.getX() - w), (int)(this.textFieldLevelname.getY() + this.textFieldLevelname.getHeight()/2 + h/2));
			}
			if (this.textFieldUsername.isBVisible()) {
				this.textFieldUsername.render(g, 0, 0);
				
				g.setFont(ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
				s = "Username: ";
				w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(this.textFieldUsername.getX() - w), (int)(this.textFieldUsername.getY() + this.textFieldUsername.getHeight()/2 + h/2));
			}
			if (this.textFieldDescription.isBVisible()) {
				this.textFieldDescription.render(g, 0, 0);
				
				g.setFont(ApoNotSoSimpleConstants.FONT_DESCRIPTIONS);
				s = "Description: ";
				w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(this.textFieldDescription.getX() - w), (int)(this.textFieldDescription.getY() + this.textFieldDescription.getHeight()/2 + h/2));
			}
			
			try {
				for (int i = this.strings.size() - 1; i >= 0; i--) {
					this.strings.get(i).render(g, 0, 0);
				}
			} catch (Exception ex) {	
			}
		}
	}

}