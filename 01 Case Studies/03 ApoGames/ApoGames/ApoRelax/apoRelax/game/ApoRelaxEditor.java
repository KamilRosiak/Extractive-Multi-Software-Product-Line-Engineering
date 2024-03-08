package apoRelax.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoNewTextfield;
import org.apogames.help.ApoHelp;

import apoRelax.ApoRelaxConstants;
import apoRelax.entity.ApoRelaxString;
import apoRelax.level.ApoRelaxLevel;
import apoRelax.level.ApoRelaxLevelGeneration;

public class ApoRelaxEditor extends ApoRelaxState {

	public static final int MAX_TIME_RENDER = 100;
	
	private final int MIN_SIZE = 40;
	
	private final int LEFT = 0;
	private final int UP = 1;
	private final int RIGHT = 2;
	private final int DOWN = 3;
	private final int CENTER = 4;
	private final int NONE = -1;
	
	public static final String MENU = "editor_menu";
	public static final String LOAD = "editor_load";
	public static final String TEST = "editor_test";

	private BufferedImage iBackground;
	
	private BufferedImage iLevel;
	
	private ApoRelaxLevel level;
	
	private ArrayList<ApoRelaxString> strings;
	
	private ApoNewTextfield textFieldLevelname;
	
	private ArrayList<Rectangle2D.Double> recs;
	
	private int curSelection = -1;
	
	private int direction = -1;
	
	private Point lastPoint;
	private Point changePoint;
	
	public ApoRelaxEditor(ApoRelaxPanel game) {
		super(game);
		
		this.init();
	}
	
	@Override
	public void init() {
		if (this.textFieldLevelname == null) {
			int width = 255;
			int height = 30;
			int x = ApoRelaxLevel.MAX/2  - 25;
			int y = 10;
			this.textFieldLevelname = new ApoNewTextfield(x, y, width, height, ApoRelaxConstants.FONT_INSTRUCTIONS);
			this.textFieldLevelname.setMaxLength(20);
			this.textFieldLevelname.setCurString("My EditorLevel");
		}
		
		if (this.iLevel == null) {
			this.iLevel = ApoRelaxLevelGeneration.getImage("images/first.png", false);
		}
		
		if (this.recs == null) {
			this.recs = new ArrayList<Rectangle2D.Double>();
		}

		if (this.level == null) {
			this.level = null;
		}
		this.strings = new ArrayList<ApoRelaxString>();

		if (this.level != null) {
			this.level.restart();
		}
		this.makeBackground();
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

	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
		
		g.setStroke(stroke);
		
		g.dispose();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		} else {
			if ((!this.checkKeyReleased(this.textFieldLevelname, button, character))) {
				if ((button == KeyEvent.VK_T) || (button == KeyEvent.VK_ENTER)) {
					this.level = ApoRelaxLevelGeneration.getEditorLevel(this.iLevel, this.recs);
					this.getGame().setGameWithLevel(this.level);
				} else if (button == KeyEvent.VK_L) {
					this.loadImage();
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoRelaxEditor.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoRelaxEditor.LOAD)) {
			if (!ApoConstants.B_APPLET) {
				this.loadImage();
			}
		} else if (function.equals(ApoRelaxEditor.TEST)) {
			if (this.recs.size() > 1) {
				System.out.println("ApoRelaxTile[] tiles = new ApoRelaxTile["+this.recs.size()+"];");
				for (int i = 0; i < this.recs.size(); i++) {
					System.out.println("tiles["+i+"] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, "+(int)((float)this.recs.get(i).x)+", "+(int)(this.recs.get(i).y)+", "+(int)(this.recs.get(i).width)+", "+(int)(this.recs.get(i).height)+", "+i+");");	
				}
				System.out.println();
				
				System.out.println("ApoRelaxLevelGeneration.recs[0][0] = new ApoRelaxLevelGenerationRec("+this.recs.size()+", 0, 0, 0);");
				for (int i = 0; i < this.recs.size(); i++) {
					int percentX = (int)Math.round((float)this.recs.get(i).x * 100f / (float)this.iLevel.getWidth());
					int percentY = (int)Math.round((float)this.recs.get(i).y * 100f / (float)this.iLevel.getHeight());
					int percentWidth = (int)Math.round((float)this.recs.get(i).width * 100f / (float)this.iLevel.getWidth());
					int percentHeight = (int)Math.round((float)this.recs.get(i).height * 100f / (float)this.iLevel.getHeight());
					System.out.println("ApoRelaxLevelGeneration.recs[0]["+(i+1)+"] = new ApoRelaxLevelGenerationRec("+percentX+", "+percentY+", "+percentWidth+", "+percentHeight+");");
				}
				
				this.level = ApoRelaxLevelGeneration.getEditorLevel(this.iLevel, this.recs);
				this.getGame().setGameWithLevel(this.level);
			} else {
				this.strings.add(new ApoRelaxString(225, 200, "You need more then 1 tile to test", true));
			}
		}
		this.textFieldLevelname.setBSelect(false);
	}
	
	private void loadImage() {
		if (this.getGame().getFileChooser().showOpenDialog(this.getGame().getScreen().getComponent()) == JFileChooser.APPROVE_OPTION) {
			String path = this.getGame().getFileChooser().getSelectedFile().getPath();
			BufferedImage iOld = this.iLevel;
			try {
				this.iLevel = ApoRelaxLevelGeneration.getImage(path, true);
			} catch (Exception ex) {
				this.iLevel = iOld;
			}
			this.getGame().render();
		}
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
			
		x -= this.getXLevel(10);
		y -= this.getYLevel(15);
		if (new Rectangle2D.Double(0, 0, this.iLevel.getWidth(), this.iLevel.getHeight()).contains(x, y)) {
			if (bRight) {
				for (int i = 0; i < this.recs.size(); i++) {
					if (this.recs.get(i).contains(x, y)) {
						this.recs.remove(i);
						this.getGame().render();
						break;
					}
				}
			} else {
				if (this.direction == this.NONE) {
					int size = this.MIN_SIZE;
					boolean bAdd = true;
					for (int i = 0; i < this.recs.size(); i++) {
						if (this.recs.get(i).intersects(x, y, size, size)) {
							bAdd = false;
						}
					}
					if (bAdd) {
						if (y + size >= this.iLevel.getHeight()) {
							y = this.iLevel.getHeight() - size;
						}
						if (x + size >= this.iLevel.getWidth()) {
							x = this.iLevel.getWidth() - size;
						}
						this.recs.add(new Rectangle2D.Double(x, y, size, size));
						this.getGame().render();
					}
				}
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
		
		x -= this.getXLevel(10);
		y -= this.getYLevel(15);
		boolean bAdd = false;
		for (int i = 0; i < this.recs.size(); i++) {
			if (this.recs.get(i).contains(x, y)) {
				if (getRec(this.LEFT, this.recs.get(i)).contains(x, y)) {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				} else if (getRec(this.RIGHT, this.recs.get(i)).contains(x, y)) {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				} else if (getRec(this.UP, this.recs.get(i)).contains(x, y)) {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				} else if (getRec(this.DOWN, this.recs.get(i)).contains(x, y)) {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				} else {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				bAdd = true;
			}
		}
		if (!bAdd) {
			this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		return false;
	}
	
	public boolean mouseDragged(int x, int y, boolean bRight) {
		super.mouseDragged(x, y, bRight);
		this.mouseMoved(x, y);
		this.setSelection(bRight);
		
		x -= this.getXLevel(10);
		y -= this.getYLevel(15);
		
		if ((!bRight) && (this.direction != this.NONE)) {
			boolean bAdd = false;
			if (new Rectangle2D.Double(0, 0, this.iLevel.getWidth(), this.iLevel.getHeight()).contains(x, y)) {
				bAdd = true;
				Rectangle2D.Double rec = this.recs.get(this.curSelection);
				if (this.direction == this.CENTER) {
					this.checkCollision((int)(x - this.changePoint.x), (int)(y - this.changePoint.y), (int)(rec.width), (int)(rec.height), this.curSelection);
					this.getGame().render();
				} else if (this.direction == this.LEFT) {
					int nextWidth = (int)(rec.width + this.lastPoint.x - x);
					if (nextWidth < this.MIN_SIZE) {
						nextWidth = this.MIN_SIZE;
					}
					this.checkCollision((int)(x - this.changePoint.x), (int)(rec.y), (int)(nextWidth), (int)(rec.height), this.curSelection);
					this.getGame().render();
				} else if (this.direction == this.RIGHT) {
					int nextWidth = (int)(rec.width - this.lastPoint.x + x);
					if (nextWidth < this.MIN_SIZE) {
						nextWidth = this.MIN_SIZE;
					}
					this.checkCollision((int)(rec.x), (int)(rec.y), (int)(nextWidth), (int)(rec.height), this.curSelection);
					this.getGame().render();
				} else if (this.direction == this.UP) {
					int nextHeight = (int)(rec.height + this.lastPoint.y - y);
					if (nextHeight < this.MIN_SIZE) {
						nextHeight = this.MIN_SIZE;
					}
					this.checkCollision((int)(rec.x), (int)(y - this.changePoint.y), (int)(rec.width), (int)(nextHeight), this.curSelection);
					this.getGame().render();
				} else if (this.direction == this.DOWN) {
					int nextHeight = (int)(rec.height - this.lastPoint.y + y);
					if (nextHeight < this.MIN_SIZE) {
						nextHeight = this.MIN_SIZE;
					}
					this.checkCollision((int)(rec.x), (int)(rec.y), (int)(rec.width), (int)(nextHeight), this.curSelection);
					this.getGame().render();
				}
				this.lastPoint = new Point(x, y);
			}
			if (!bAdd) {
				this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		return true;
	}
	
	private void checkCollision(int x, int y, int width, int height, int r) {
		Rectangle2D.Double rec = this.recs.get(r);
		Rectangle2D.Double oldRec = new Rectangle2D.Double(rec.x, rec.y, rec.width, rec.height);
		rec.x = x;
		rec.y = y;
		rec.width = width;
		rec.height = height;
		if (rec.x < 0) {
			rec.x = 0;
		} else if (rec.x + rec.width >= this.iLevel.getWidth()) {
			rec.x = this.iLevel.getWidth() - rec.width;
		}
		
		if (rec.y < 0) {
			rec.y = 0;
		} else if (rec.y + rec.height >= this.iLevel.getHeight()) {
			rec.y = this.iLevel.getHeight() - rec.height;
		}
		
		for (int i = 0; i < this.recs.size(); i++) {
			if (i != r) {
				if (this.recs.get(i).intersects(rec)) {
					this.recs.get(r).x = oldRec.x;
					this.recs.get(r).y = oldRec.y;
					this.recs.get(r).width = oldRec.width;
					this.recs.get(r).height = oldRec.height;
					break;
				}
			}
		}
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if ((this.textFieldLevelname != null) && (this.textFieldLevelname.isBVisible())) {
			if (this.textFieldLevelname.mousePressed(x, y)) {
				return true;
			}
		}
		this.lastPoint = new Point(-1, -1);
		if (!bRight) {
			x -= this.getXLevel(10);
			y -= this.getYLevel(15);
			for (int i = 0; i < this.recs.size(); i++) {
				if (this.recs.get(i).contains(x, y)) {
					this.lastPoint = new Point(x, y);
					Rectangle2D.Double left = this.getRec(this.LEFT, this.recs.get(i)); 
					if (left.contains(x, y)) {
						this.direction = this.LEFT;
					} else if (getRec(this.RIGHT, this.recs.get(i)).contains(x, y)) {
						this.direction = this.RIGHT;
					} else if (getRec(this.UP, this.recs.get(i)).contains(x, y)) {
						this.direction = this.UP;
					} else if (getRec(this.DOWN, this.recs.get(i)).contains(x, y)) {
						this.direction = this.DOWN;
					} else {
						this.direction = this.CENTER;
					}
					this.curSelection = i;
					this.changePoint = new Point((int)(x - this.recs.get(i).x), (int)(y - this.recs.get(i).y));
				}
			}
		}
		if (this.lastPoint.x < 0) {
			this.direction = this.NONE;
		}
		return false;
	}
	
	private Rectangle2D.Double getRec(int direction, Rectangle2D.Double rec) {
		int size = 8;
		if (direction == this.LEFT) {
			return new Rectangle2D.Double(rec.x, rec.y, size, rec.height);
		} else if (direction == this.RIGHT) {
			return new Rectangle2D.Double(rec.x + rec.width - size, rec.y, size, rec.height);
		} else if (direction == this.UP) {
			return new Rectangle2D.Double(rec.x, rec.y, rec.width, size);
		} else if (direction == this.DOWN) {
			return new Rectangle2D.Double(rec.x, rec.y + rec.height - size, rec.width, size);
		}
		return null;
	}
	
	/**
	 * wird aufgerufen, wenn die Maus losgelassen wird oder die Maus gezogen wird<br />
	 * sie setzt dann die Leveltiles, wenn benötigt<br />
	 * @param bRight : TRUE, rechte Maustaste gedrückt, ansonsten FALSE
	 */
	private void setSelection(final boolean bRight) {
	}

	@Override
	public void think(int delta) {
		if (this.textFieldLevelname != null) {
			this.textFieldLevelname.think(delta);
		}
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
		}
	}
	
	public int getXLevel(int changeX) {
		return changeX + 25 + (ApoRelaxLevel.MAX - this.iLevel.getWidth())/2;
	}
	
	public int getYLevel(int changeY) {
		return changeY + 25 + (ApoRelaxLevel.MAX - this.iLevel.getHeight())/2;
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
			
			int x = this.getXLevel(10);
			int y = this.getYLevel(15);
			if (this.iLevel != null) {
				g.drawImage(this.iLevel, x, y, null); 
			}
//			this.level.render(g, 10, 0);

			g.setColor(Color.RED);
			Stroke stroke = g.getStroke();
//			g.setStroke(new BasicStroke(3));
			for (int i = 0; i < this.recs.size(); i++) {
				g.drawRect((int)(x + this.recs.get(i).x), (int)(y + this.recs.get(i).y), (int)(this.recs.get(i).width), (int)(this.recs.get(i).height));
			}
			g.setStroke(stroke);
			
			g.setColor(Color.BLACK);
			if (this.textFieldLevelname.isBVisible()) {
				this.textFieldLevelname.render(g, 0, 0);
				
				g.setFont(ApoRelaxConstants.FONT_INSTRUCTIONS);
				String s = "Levelname: ";
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(this.textFieldLevelname.getX() - w), (int)(this.textFieldLevelname.getY() + this.textFieldLevelname.getHeight()/2 + h/2));
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