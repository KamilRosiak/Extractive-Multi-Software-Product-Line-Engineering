package apoImp.editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoHelp;

import apoImp.ApoImpConstants;
import apoImp.entity.ApoImpString;
import apoImp.game.ApoImpModel;
import apoImp.game.ApoImpPanel;

public class ApoImpEditor extends ApoImpModel {

	public static final String QUIT = "X";
	public static final String TEST = "test";
	public static final String NEW = "new";
	public static final String UPLOAD = "upload";
	
	private int[][] level;
	
	private int curSelect = 0;
	
	private ApoButton[] buttons;
	
	private boolean bHandCursor;
	
	private int curLevelX, curLevelY;
	
	private ApoImpString uploadString;
	
	public ApoImpEditor(ApoImpPanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (this.level == null) {
			this.createEmptyLevel();
		}
		this.curLevelX = -1;
		if (this.buttons == null) {
			buttons = new ApoButton[9];
			
			for (int i = 0; i < buttons.length; i++) {
				Font font = ApoImpConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
				String text = "";
				String function = "";
				int width = 35;
				int height = 35;
				BufferedImage image = this.getGame().getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, Color.YELLOW, Color.RED, font, 10);
				int x = 5 + i * 40;
				int y = 520;
				if (i >= 11) {
					x = 5 + (i - 11) * 40;
					y = 560;
				}
				
				this.buttons[i] = new ApoButton(image, x, y, width, height, function);
			}
		}
	}
	
	private void createEmptyLevel() {
		String l = "00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00001111111000"+
				"00003121114000"+
				"00000100100000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000"+
				"00000000000000";
		
		this.createEmptyLevel(l);
	}
	
	public void createEmptyLevel(String l) {
		
		level = new int[l.length() / 14 + 2][16];
		for (int y = 0; y < level.length - 2; y++) {
			for (int x = 0; x < level[0].length - 2; x++) {
				char c = l.charAt(y * 14 + x);
				level[y+1][x+1] = Integer.valueOf(c) - 48;
			}
		}
	}
	
	private String getLevelString() {
		String s = "";
		for (int y = 0; y < level.length - 2; y++) {
			for (int x = 0; x < level[0].length - 2; x++) {
				if (level[y+1][x+1] < 10) {
					s += String.valueOf(level[y+1][x+1]);
				} else {
					char c = (char)(level[y+1][x+1] + 87);
					s += String.valueOf(c);
				}
			}
		}
		return s;
	}

	public boolean mouseDragged(int x, int y) {
		this.mouseButtonReleased(x, y, false);
		return false;
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().setMenu();
		} else if (button == KeyEvent.VK_T) {
			if (this.getGame().getButtons()[5].isBVisible()) {
				super.getGame().setGame(false, true, this.getLevelString());
			}
		} else if (button == KeyEvent.VK_N) {
			this.createEmptyLevel();
		}
	}

	public boolean mouseMoved(int x, int y) {
		if ((x > 30) && (x < 450) && (y > 60) && (y < 480)) {
			curLevelX = x / 30;
			curLevelY = (y - 30) / 30;
		} else {
			curLevelX = -1;
		}
		boolean bOver = false;
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getMove(x, y)) {
					bOver = true;
					if (!super.getGame().shouldRepaint()) {
						this.getGame().render();
					}
					break;
				} else if (this.buttons[i].isBOver()) {
					bOver = true;
				}
			}
		}
		if (bOver) {
			if (!this.bHandCursor) {
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.bHandCursor = true;
			}
		} else {
			if (this.bHandCursor) {
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.bHandCursor = false;
			}
		}
		return true;
	}
	
	public boolean mousePressed(int x, int y, boolean left) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getPressed(x, y)) {
					if (!super.getGame().shouldRepaint()) {
						this.getGame().render();
					}
					break;
				}
			}
		}
		return true;
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoImpEditor.QUIT)) {
			super.getGame().setMenu();
		} else if (function.equals(ApoImpEditor.TEST)) {
			if (this.getGame().getButtons()[5].isBVisible()) {
				super.getGame().setGame(false, true, this.getLevelString());
			}
		} else if (function.equals(ApoImpEditor.NEW)) {
			this.createEmptyLevel();
		} else if (function.equals(ApoImpEditor.UPLOAD)) {
			this.upload();
		}
	}
	
	private void upload() {
		super.getGame().getButtons()[8].setBVisible(false);
		
		this.uploadString = new ApoImpString(240, 470, 20, "Uploading ...", true, 200, true);
		
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoImpEditor.this.uploadString();
			}
 		});
 		t.start();
	}
	
	private void uploadString() {
		if (this.getGame().getUserlevels().addLevel(this.getLevelString())) {
			this.uploadString = new ApoImpString(240, 470, 20, "Uploading successfully", true, 20, true);
			this.getGame().loadUserlevels();
		} else {
			this.uploadString = new ApoImpString(240, 470, 20, "Uploading failed", true, 20, true);
		}
	}
	
	public void couldTestLevel() {
		int playerCount = 0;
		boolean bGoal = false;
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				if ((level[y][x] == 2) || (level[y][x] == 3)) {
					playerCount += 1;
				}
				if (level[y][x] == 4) {
					bGoal = true;
				}
			}
		}
		if ((playerCount == 2) && (bGoal)) {
			this.getGame().getButtons()[5].setBVisible(true);
		} else {
			this.getGame().getButtons()[5].setBVisible(false);
		}
	}
	
	public void save() {
		String level = this.getLevelString();
		System.out.println(level);
		if (!ApoConstants.B_APPLET) {
			ApoHelp.setClipboardContents(level);
		}
		if (!super.getGame().getButtons()[8].isBVisible()) {
			super.getGame().getButtons()[8].setBVisible(true);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if ((x > 30) && (x < 450) && (y > 60) && (y < 480)) {
			int lX = curLevelX = x / 30;
			int lY = curLevelY = (y - 30) / 30;
			int add = 0;
			if (level[lY][lX] != this.curSelect)  {
				if ((this.curSelect == 2) || (this.curSelect == 3) || (this.curSelect == 4)) {
					for (int ny = 0; ny < level.length; ny++) {
						for (int nx = 0; nx < level[ny].length; nx++) {
							if (level[ny][nx] == this.curSelect) {
								level[ny][nx] = 1;
							}
						}
					}
				}
				level[lY][lX] = this.curSelect + add;
				super.getGame().getButtons()[8].setBVisible(false);
				this.couldTestLevel();
				super.getGame().render();
			}
		} else {
			curLevelX = -1;
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].intersects(x, y)) {
					if (i != this.curSelect) {
						this.curSelect = i;
						super.getGame().render();
					}
				}
			}
		}
	}
	
	public void mouseWheelChanged(int changed) {
		this.curSelect += changed;
		if (this.curSelect < 0) {
			this.curSelect = this.buttons.length - 1;
		} else if (this.curSelect >= this.buttons.length) {
			this.curSelect = 0;
		}
	}

	@Override
	public void think(int delta) {
		if (this.uploadString != null) {
			this.uploadString.think(delta);
			
			if (!this.uploadString.isVisible()) {
				this.uploadString = null;
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		super.getGame().renderSnow(g);
		
		final int changeY = 30;
		g.setColor(new Color(150, 150, 190));
		g.fillRect(0, 0, 480, changeY);
		g.fillRect(0, changeY + 480, 480, 80 - changeY);
		g.setColor(new Color(40, 40, 80));
		g.drawRect(0, 0, 479, changeY);
		g.drawRect(0, changeY + 480, 479, 79 - changeY);
		
		g.setColor(ApoImpConstants.c2);
		String s = "ApoElf - Editor";
		g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
		g.drawString(s, 10, 25);
		
		g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
		for (int i = 0; i < this.buttons.length; i++) {
			this.buttons[i].render(g);
			if (i == 0) {
				g.setColor(ApoImpConstants.c2);
				g.fillRect((int)(this.buttons[i].getX() + 5), (int)(this.buttons[i].getY() + 5), 24, 24);
			} else if (i == 2) {
				this.drawPlayer(g, (int)(this.buttons[i].getX() + 2), (int)(this.buttons[i].getY() + 2), i);
			} else if (i == 3) {
				this.drawPlayer(g, (int)(this.buttons[i].getX() + 2), (int)(this.buttons[i].getY() + 2), i);
			} else if (i == 4) {
				this.drawGoal(g, (int)(this.buttons[i].getX() + 2), (int)(this.buttons[i].getY() + 2));
			} else if (i == 8) {
				this.drawBeamer(g, (int)(this.buttons[i].getX() + 2), (int)(this.buttons[i].getY() + 2));
			} else if (i > 4) {
				this.drawPresent(g, (int)(this.buttons[i].getX() + 2), (int)(this.buttons[i].getY() + 2), i);
			}
			if (i == this.curSelect) {
				g.setColor(Color.RED);
				g.drawRect((int)(this.buttons[i].getX()), (int)(this.buttons[i].getY()), (int)(this.buttons[i].getWidth()), (int)(this.buttons[i].getHeight()));
			}
		}
		
		for (int y = -level.length; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if (y < 0) {
					if (level[y + level.length][x] != 0) {
						g.setColor(new Color(150, 150, 190));
						g.fillRect(x * 30, changeY + (y + level.length) * 30, 30, 30);
					}
					g.setColor(Color.BLACK);
					g.drawRect(x * 30, changeY + (y + level.length) * 30, 30, 30);
					continue;
				}
				if (level[y][x] != 0) {
					g.setColor(Color.BLACK);
					if (level[y-1][x] == 0) g.drawLine(x * 30, changeY + y * 30, (x + 1) * 30, changeY + y * 30);
					if (level[y+1][x] == 0) g.drawLine(x * 30, changeY + (y + 1) * 30, (x + 1) * 30, changeY + (y + 1) * 30);
					if (level[y][x-1] == 0) g.drawLine(x * 30, changeY + y * 30, (x + 0) * 30, changeY + (y + 1) * 30);
					if (level[y][x+1] == 0) g.drawLine((x + 1) * 30, changeY + y * 30, (x + 1) * 30, changeY + (y + 1) * 30);
					
					if (level[y][x] == 4) {
						/** Render das Ziel */							
						this.drawGoal(g, x * 30, y * 30 + changeY);
					}
					int addPX = 0;
					int addPY = 0;
					if (level[y][x] == 8) {
						this.drawBeamer(g, x * 30, y * 30 + changeY);
					}
					if ((level[y][x] >= 5) && (level[y][x] <= 7)) {
						this.drawPresent(g, x * 30, y * 30 + changeY, level[y][x]);
					}
					if ((level[y][x] == 2) || (level[y][x] == 3)) {
						int curX = x * 30 + addPX;
						int curY = changeY + y * 30 + addPY;
						this.drawPlayer(g, curX, curY, level[y][x]);
					}
				}
			}
		}
		
		if (this.uploadString != null) {
			this.uploadString.render(g, 0, 0);
		}
		
		if (this.curLevelX >= 0) {
			Composite composite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(128f/255f)));
			
			int i = this.curSelect;
			if (i == 0) {
				g.setColor(ApoImpConstants.c2);
				g.fillRect(this.curLevelX * 30, this.curLevelY * 30 + 30, 30, 30);
			} else if (i == 1) {
				g.setColor(ApoImpConstants.c);
				g.fillRect(this.curLevelX * 30, this.curLevelY * 30 + 30, 30, 30);
			} else if (i == 2) {
				this.drawPlayer(g, this.curLevelX * 30, this.curLevelY * 30 + 30, 2);
			} else if (i == 3) {
				this.drawPlayer(g, this.curLevelX * 30, this.curLevelY * 30 + 30, 3);
			} else if (i == 4) {
				this.drawGoal(g, this.curLevelX * 30, this.curLevelY * 30 + 30);
			} else if (i == 8) {
				this.drawBeamer(g, this.curLevelX * 30, this.curLevelY * 30 + 30);
			} else {
				this.drawPresent(g, this.curLevelX * 30, this.curLevelY * 30 + 30, i);
			}
			
			g.setComposite(composite);
		}
	}
	
	private void drawBeamer(Graphics2D g, int x, int y) {
		g.setColor(new Color(40, 60, 80));
		g.fillRect(x + 8, y + 10, 14, 20);
		g.fillRect(x + 14, y, 3, 3);
		g.fillRect(x + 15, y + 3, 1, 7);
		g.setColor(Color.WHITE);
		g.fillRect(x + 10, y + 13, 4, 2);
		g.fillRect(x + 16, y + 13, 4, 2);
	}
	
	private void drawPlayer(Graphics2D g, int curX, int curY, int values) {
		g.setColor(new Color(218, 60, 60));
		if (values == 3) {
			g.setColor(new Color(60, 60, 218));
		}
		g.fillRect((int)(curX) + 10, (int)(curY) + 12, 10, 6);
		g.fillRect((int)(curX) + 12, (int)(curY) + 6, 6, 6);
		g.fillRect((int)(curX) + 8, (int)(curY) + 22, 14, 2);
		g.fillRect((int)(curX) + 10, (int)(curY) + 26, 10, 2);
		
		g.setColor(new Color(253, 188, 111));
		g.fillRect((int)(curX) + 10, (int)(curY) + 18, 10, 2);
		g.fillRect((int)(curX) + 8, (int)(curY) + 24, 14, 2);

		g.setColor(Color.BLACK);
		g.fillRect((int)(curX) + 10, (int)(curY) + 24, 10, 2);
		g.fillRect((int)(curX) + 10, (int)(curY) + 28, 2, 2);
		g.fillRect((int)(curX) + 18, (int)(curY) + 28, 2, 2);

		g.fillRect((int)(curX) + 12, (int)(curY) + 18, 2, 2);
		g.fillRect((int)(curX) + 18, (int)(curY) + 18, 2, 2);
		
		g.setColor(Color.YELLOW);
		g.fillRect((int)(curX) + 14, (int)(curY) + 24, 4, 2);
		
		g.setColor(Color.WHITE);
		g.fillRect((int)(curX) + 10, (int)(curY) + 20, 12, 2);
		g.fillRect((int)(curX) + 12, (int)(curY) + 22, 8, 2);
		g.fillRect((int)(curX) + 10, (int)(curY) + 16, 10, 2);
		g.fillRect((int)(curX) + 12, (int)(curY) + 2, 4, 4);
	}
	
	private void drawGoal(Graphics2D g, int x, int y) {
		g.setColor(new Color(26, 157, 26));
		for (int i = 0; i < 20; i += 2) {
			g.fillRect(x + i/2 + 5, y + 25 - i, 20 - i, 2);
		}
		g.setColor(new Color(78, 58, 11));
		g.fillRect(x + 13, y + 25, 4, 5);
		
		g.setColor(new Color(228, 255, 0));
		g.fillRect(x + 11, y + 3, 8, 2);
		g.fillRect(x + 14, y, 2, 8);
		
		Color c = new Color(220, 40, 40);
		g.setColor(c);
		g.fillRect(x + 10, y + 25 - 4, 2, 2);
		g.fillRect(x + 18, y + 25 - 6, 2, 2);
		g.fillRect(x + 12, y + 25 - 11, 2, 2);
	}
	
	private void drawPresent(Graphics2D g, int x, int y, int values) {
		g.setColor(new Color(70, 195, 50));
		if (values == 7) {
			g.setColor(new Color(195, 50, 70));								
		}
		if (values == 6) {
			g.setColor(new Color(100, 120, 245));					
		}
		g.fillRect(x + 3, y + 4, 24, 24);
		g.setColor(Color.BLACK);
		g.drawRect(x + 3, y + 4, 24, 24);
		g.setColor(new Color(167, 73, 70));
		if (values == 7) {
			g.setColor(new Color(73, 70, 167));								
		}
		if (values == 6) {
			g.setColor(new Color(7, 50, 7));						
		}
		g.fillRect(x + 4, y + 4 + 11, 23, 4);
		g.fillRect(x + 3 + 10, y + 5, 4, 23);
		g.fillRect(x + 3 + 7, y + 4 - 3, 3, 3);
		g.fillRect(x + 3 + 14, y + 4 - 3, 3, 3);
		g.fillRect(x + 3 + 10, y + 4 - 2, 4, 2);
	}
}
