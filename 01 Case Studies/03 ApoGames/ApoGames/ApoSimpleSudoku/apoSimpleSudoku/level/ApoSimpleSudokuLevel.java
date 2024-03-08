package apoSimpleSudoku.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoButton;

import apoSimpleSudoku.ApoSimpleSudokuConstants;
import apoSimpleSudoku.ApoSimpleSudokuImages;
import apoSimpleSudoku.entity.ApoSimpleSudokuMenu;
import apoSimpleSudoku.game.ApoSimpleSudokuGame;

public class ApoSimpleSudokuLevel {

	public static final Color c = new Color(ApoSimpleSudokuConstants.BROWN.getRed(), ApoSimpleSudokuConstants.BROWN.getGreen(), ApoSimpleSudokuConstants.BROWN.getBlue(), 120);
	public static final Color same = new Color(200, 200, 0, 120);
	
	private final ApoSimpleSudokuLevelHelp help;

	private ApoButton[][] buttons;
	
	private byte[][] level;
	
	private BufferedImage[][] buttonImages;
	
	private BufferedImage iBackground;
	
	private int over;
	
	private int overValue;
	
	private byte[][] connected;
	
	private ApoSimpleSudokuMenu helpMenu;
	
	private int valueHelpMenu;
	
	private String[][] helpMenuString;

	private ApoSimpleSudokuMenu menu;
	
	private boolean bWin;
	
	private ArrayList<Point> mistakes;
	
	private int mouseX, mouseY;
	
	public ApoSimpleSudokuLevel(final ApoSimpleSudokuLevelHelp help) {
		this.help = help;
		this.init();
	}
	
	public void init() {
		this.level = new byte[this.help.getWidth()][this.help.getWidth()];
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if (this.help.getLevel()[y][x] > 0) {
					this.level[y][x] = this.help.getLevel()[y][x];
				}
			}
		}
		
		this.helpMenuString = new String[this.help.getWidth()][this.help.getWidth()];
		for (int y = 0; y < this.helpMenuString.length; y++) {
			for (int x = 0; x < this.helpMenuString[0].length; x++) {
				this.helpMenuString[y][x] = "";
			}
		}
		this.helpMenu = null; 
		
		this.mistakes = new ArrayList<Point>();
		this.over = -1;
		this.overValue = 0;
		this.bWin = false;
		final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		final ApoSimpleSudokuImages images = new ApoSimpleSudokuImages();
		this.buttons = new ApoButton[this.help.getWidth()][this.help.getWidth()];
		float oneWidth = (ApoSimpleSudokuGame.width) / this.level.length;
		int width = (int)(oneWidth - 20);
		for (int y = 0; y < this.buttons.length; y++) {
			for (int x = 0; x < this.buttons[0].length; x++) {
				int drawX = (int)(0.5f * oneWidth + x * oneWidth) - width/2 + ApoSimpleSudokuGame.startX;
				int drawY = (int)(0.5f * oneWidth + y * oneWidth) - width/2 + ApoSimpleSudokuGame.startY;
				this.buttons[y][x] = new ApoButton(images.getButtonImageLevelChooser(width * 3, width, "", ApoSimpleSudokuLevel.c, new Color(0, 0, 0, 0), Color.BLACK, new Color(254, 254, 0, 140), new Color(254, 0, 0, 140), font, 250), drawX, drawY, width, width, "");
				this.buttons[y][x].setBOpaque(true);
			}
		}
		
		this.connected = new byte[this.help.getWidth()][this.help.getWidth()];
		this.buttonImages = new BufferedImage[this.help.getWidth()][2];
		for (int i = 0; i < this.buttonImages.length; i++) {
			String connected = this.help.getConnected()[i];
			for (int j = 0; j < 2; j++) {
				BufferedImage image = new BufferedImage(ApoSimpleSudokuGame.width, ApoSimpleSudokuGame.height, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D g = image.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				Stroke stroke = new BasicStroke(3);
				Stroke basicStroke = new BasicStroke(7);
				
				boolean[][] visit = new boolean[this.help.getWidth()][this.help.getWidth()];
				
				for (int st = 0; st < connected.length(); st+=2) {
					int x = Integer.valueOf(String.valueOf(connected.charAt(st)));
					int y = Integer.valueOf(String.valueOf(connected.charAt(st + 1)));
					
					this.connected[y][x] = (byte)i;
					
					int drawX = (int)(0.5f * oneWidth + x * oneWidth);
					int drawY = (int)(0.5f * oneWidth + y * oneWidth);
					
					if (st + 2 < connected.length()) {
						int nextX = Integer.valueOf(String.valueOf(connected.charAt(st + 2)));
						int nextY = Integer.valueOf(String.valueOf(connected.charAt(st + 3)));
						
						int nextDrawX = (int)(0.5f * oneWidth + nextX * oneWidth);
						int nextDrawY = (int)(0.5f * oneWidth + nextY * oneWidth);
						
						if (j == 0) {
							g.setColor(Color.WHITE);
						} else {
							g.setColor(c);
						}
						g.setStroke(basicStroke);
						g.drawLine(drawX, drawY, nextDrawX, nextDrawY);
						
						g.setColor(Color.BLACK);
						g.setStroke(stroke);
						g.drawLine(drawX, drawY, nextDrawX, nextDrawY);
					}

					g.setStroke(basicStroke);
					if (!visit[y][x]) {
						if (j != 0) {
							g.setColor(c);
							g.fillOval(drawX - width/2 - 8, drawY - width/2 - 8, width + 16, width + 16);
						}
					}
					g.setColor(Color.WHITE);
					g.fillOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
					
					g.setColor(Color.BLACK);
					g.setStroke(stroke);
					g.drawOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
					visit[y][x] = true;
				}
				g.dispose();
				
				this.buttonImages[i][j] = image;
			}			
		}
		
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoSimpleSudokuGame.width, ApoSimpleSudokuGame.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackground.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.iBackground.getWidth(), this.iBackground.getHeight());
			g.setColor(new Color(220, 220, 220));
			g.fillRect(0, 0, this.iBackground.getWidth(), this.iBackground.getHeight());
			
			for (int i = 0; i < this.buttonImages.length; i++) {
				g.drawImage(this.buttonImages[i][0], 0, 0, null);
			}
			
			g.dispose();
		}
	}
	
	public BufferedImage getIBackground() {
		return this.iBackground;
	}
	
	public boolean isWin() {
		return this.bWin;
	}
	
	private void checkMistakes() {
		this.mistakes.clear();
		for (int y = 0; y < this.level.length; y++) {
			ArrayList<ApoSimpleSudokuHelp> value = new ArrayList<ApoSimpleSudokuHelp>();
			for (int x = 0; x < this.level.length; x++) {
				ApoSimpleSudokuHelp help = new ApoSimpleSudokuHelp(x, y, (int)this.level[y][x]);
				boolean bBreak = false;
				for (int i = 0; i < value.size(); i++) {
					if (value.get(i).getValue() == help.getValue()) {
						this.mistakes.add(new Point(x, y));
						this.mistakes.add(new Point(value.get(i).getX(), value.get(i).getY()));
						bBreak = true;
					}
				}
				if (!bBreak) {
					value.add(help);
				}
			}
		}
		
		for (int x = 0; x < this.level.length; x++) {
			ArrayList<ApoSimpleSudokuHelp> value = new ArrayList<ApoSimpleSudokuHelp>();
			for (int y = 0; y < this.level.length; y++) {
				ApoSimpleSudokuHelp help = new ApoSimpleSudokuHelp(x, y, (int)this.level[y][x]);
				boolean bBreak = false;
				for (int i = 0; i < value.size(); i++) {
					if (value.get(i).getValue() == help.getValue()) {
						this.mistakes.add(new Point(x, y));
						this.mistakes.add(new Point(value.get(i).getX(), value.get(i).getY()));
						bBreak = true;
					}
				}
				if (!bBreak) {
					value.add(help);
				}
			}
		}
		
		// check connect
		for (int i = 0; i < this.level.length; i++) {
			ArrayList<ApoSimpleSudokuHelp> value = new ArrayList<ApoSimpleSudokuHelp>();
			for (int x = 0; x < this.level.length; x++) {
				for (int y = 0; y < this.level.length; y++) {
					if (this.connected[y][x] == i) {
						ApoSimpleSudokuHelp help = new ApoSimpleSudokuHelp(x, y, (int)this.level[y][x]);
						boolean bBreak = false;
						for (int j = 0; j < value.size(); j++) {
							if (value.get(j).getValue() == help.getValue()) {
								this.mistakes.add(new Point(x, y));
								this.mistakes.add(new Point(value.get(j).getX(), value.get(j).getY()));
								bBreak = true;
							}
						}
						if (!bBreak) {
							value.add(help);
						}
					}
				}
			}
		}
	}
	
	private void checkWin() {
		this.checkMistakes();
		this.bWin = false;
		// check x
		for (int y = 0; y < this.level.length; y++) {
			ArrayList<Integer> value = new ArrayList<Integer>();
			for (int x = 0; x < this.level.length; x++) {
				if (this.level[y][x] == 0) {
					return;
				} else {
					if (value.indexOf((int)this.level[y][x]) < 0) {
						value.add((int)this.level[y][x]);
					} else {
						return;
					}
				}
			}
		}
		// check y
		for (int x = 0; x < this.level.length; x++) {
			ArrayList<Integer> value = new ArrayList<Integer>();
			for (int y = 0; y < this.level.length; y++) {
				if (value.indexOf((int)this.level[y][x]) < 0) {
					value.add((int)this.level[y][x]);
				} else {
					return;
				}
			}
		}
		// check connect
		for (int i = 0; i < this.level.length; i++) {
			ArrayList<Integer> connect = new ArrayList<Integer>();
			for (int x = 0; x < this.level.length; x++) {
				for (int y = 0; y < this.level.length; y++) {
					if (this.connected[y][x] == i) {
						if (connect.indexOf((int)this.level[y][x]) < 0) {
							connect.add((int)this.level[y][x]);
						} else {
							return;
						}
					}
				}
			}
		}
		this.bWin = true;
	}
	
	public boolean keyButtonReleased(int button, char character) {
		if ((this.menu != null) || (this.helpMenu != null)) {
			if ((button == KeyEvent.VK_DELETE) || (button == KeyEvent.VK_BACK_SPACE) || (button == KeyEvent.VK_0)) {
				this.setValue(0);
				return true;
			} else if (button == KeyEvent.VK_1) {
				this.setValue(1);
				return true;
			} else if (button == KeyEvent.VK_2) {
				this.setValue(2);
				return true;
			} else if (button == KeyEvent.VK_3) {
				this.setValue(3);
				return true;
			} else if (button == KeyEvent.VK_4) {
				this.setValue(4);
				return true;
			} else if (button == KeyEvent.VK_5) {
				if (this.help.getWidth() >= 5) {
					this.setValue(5);
					return true;
				}
			} else if (button == KeyEvent.VK_6) {
				if (this.help.getWidth() >= 6) {
					this.setValue(6);
					return true;
				}
			} else if (button == KeyEvent.VK_7) {
				if (this.help.getWidth() >= 7) {
					this.setValue(7);
					return true;
				}
			} else if (button == KeyEvent.VK_8) {
				if (this.help.getWidth() >= 8) {
					this.setValue(8);
					return true;
				}
			} else if (button == KeyEvent.VK_9) {
				if (this.help.getWidth() >= 9) {
					this.setValue(9);
					return true;
				}
			}
		}
		return false;
	}
	
	private void setValue(int value) {
		if (this.helpMenu != null) {
			this.setValueHelpMenu(value);
		} else {
			if (value >= 0) {
				this.level[this.menu.getLevelY()][this.menu.getLevelX()] = (byte)value;
				this.checkWin();
				this.overValue = value;
			} else {
				this.overValue = this.level[this.menu.getLevelY()][this.menu.getLevelX()];
			}
			this.menu = null;
		}
	}
	
	private void setValueHelpMenu(int value) {
		if (value < 0) {
		} else if (value == 0) {
			this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()] = this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].substring(0, this.valueHelpMenu) + this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].substring(this.valueHelpMenu + 1);
		} else {
			if ((this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].length() <= 0) ||
				(this.valueHelpMenu >= this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].length())) {
				if (this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].indexOf(String.valueOf(value)) < 0) {
					this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()] += String.valueOf(value);
				}
			} else {
				char oldString = this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].charAt(this.valueHelpMenu);
				if (this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].indexOf(String.valueOf(value)) < 0) {
					this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()] = this.helpMenuString[this.helpMenu.getLevelY()][this.helpMenu.getLevelX()].replace(oldString, String.valueOf(value).charAt(0));
				}
			}
		}
        this.helpMenu = null;
	}
	
	public boolean mouseReleased(int mouseX, int mouseY, boolean right) {
		if (this.helpMenu != null) {
			int value = this.helpMenu.mouseReleased(mouseX, mouseY, right);
			this.setValueHelpMenu(value);
			return true;
		} else if (this.menu != null) {
			int value = this.menu.mouseReleased(mouseX, mouseY, right);
			this.setValue(value);
			return true;
		} else if (this.buttons != null) {
			boolean bButton = false;
			for (int y = 0; y < this.buttons.length; y++) {
				for (int x = 0; x < this.buttons[0].length; x++) {
					if (this.buttons[y][x].isBVisible()) {
						boolean bReleased = this.buttons[y][x].getReleased(mouseX, mouseY);
						if ((bReleased) && (this.help.getLevel()[y][x] <= 0)) {
							boolean bInsert = false;
							if (this.level[y][x] <= 0) {
								final float oneWidth = (ApoSimpleSudokuGame.width) / this.level.length;
								int drawX = (int)(0.5f * oneWidth + x * oneWidth) + ApoSimpleSudokuGame.startX;
								int drawY = (int)(0.5f * oneWidth + y * oneWidth) + ApoSimpleSudokuGame.startY;
								
								int nextWidth = 15;
								int change = 30;
								if (this.help.getWidth() > 4) {
									change -= (this.help.getWidth()-4)*5;
									nextWidth -= (this.help.getWidth()-4)*1;
								}
								int cX = drawX - change;
								int cY = drawY - change;
								for (int i = 0; i < this.helpMenuString[y][x].length() + 1; i++) {
									if ((new Rectangle2D.Double(cX, cY, nextWidth, nextWidth)).intersects(mouseX, mouseY, 1, 1)) {
										bInsert = true;
										this.valueHelpMenu = i;
										break;
									}
											
									if (i < 2) {
										cX += nextWidth + 3;
									} else if ((i == 2) || (i == 4)) {
										cX = drawX - change;
										cY += nextWidth + 3;
									} else if (i == 3) {
										cX += 2 * (nextWidth + 3);
									} else {
										cX += nextWidth + 3;
									}
								}
							}
							
							int width = 150;
							if (this.help.getWidth() > 6) {
								width = 200;
							}
							int height = 130;
							int menuY = (int)(this.buttons[y][x].getY());
							boolean bChange = false;
							if (menuY + height + this.buttons[y][x].getHeight() > ApoSimpleSudokuGame.startY + ApoSimpleSudokuGame.height) {
								menuY = (int)(this.buttons[y][x].getY() - height);
								bChange = true;
							}
							int menuX = (int)(this.buttons[y][x].getX() + this.buttons[y][x].getWidth()/2 - width/2);
							if (menuX + width > ApoSimpleSudokuGame.startX + ApoSimpleSudokuGame.width) {
								menuX = (int)(ApoSimpleSudokuGame.startX + ApoSimpleSudokuGame.width - width);
							} else if (menuX < ApoSimpleSudokuGame.startX) {
								menuX = (int)(ApoSimpleSudokuGame.startX);
							}
							if (!bInsert) {
								this.menu = new ApoSimpleSudokuMenu(menuX, menuY, width, height + this.buttons[y][x].getHeight(), this.level.length, x, y, bChange, this.buttons[y][x].getHeight());
							} else {
								this.helpMenu = new ApoSimpleSudokuMenu(menuX, menuY, width, height + this.buttons[y][x].getHeight(), this.level.length, x, y, bChange, this.buttons[y][x].getHeight());
							}
							
							return true;
						} else if (bReleased) {
							if (this.level[y][x] > 0) {
								this.overValue = this.level[y][x];
								bButton = true;
								return true;
							}
						}
					}
				}
			}
			if (!bButton) {
				this.overValue = 0;
			}
		}
		this.checkWin();
		return true;
	}
	
	public boolean mousePressed(int mouseX, int mouseY, boolean bRight) {
		if (this.helpMenu != null) {
			return this.helpMenu.mousePressed(mouseX, mouseY, bRight);
		} else if (this.menu != null) {
			return this.menu.mousePressed(mouseX, mouseY, bRight);
		} else if (this.buttons != null) {
			for (int y = 0; y < this.buttons.length; y++) {
				for (int x = 0; x < this.buttons[0].length; x++) {
					if (this.buttons[y][x].isBVisible()) {
						if (this.buttons[y][x].getPressed(mouseX, mouseY)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public int mouseMoved(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		if (this.helpMenu != null) {
			int value = this.helpMenu.mouseMoved(mouseX, mouseY);
			if (value == 3) {
				this.helpMenu = null;
				return 2;
			} else {
				return value;
			}
		} else if (this.menu != null) {
			int value = this.menu.mouseMoved(mouseX, mouseY);
			if (value == 3) {
				this.menu = null;
				return 2;
			} else {
				return value;
			}
		} else if (this.buttons != null) {
			boolean bOver = false;
			this.over = -1;
			for (int y = 0; y < this.buttons.length; y++) {
				for (int x = 0; x < this.buttons[0].length; x++) {
					if (this.buttons[y][x].getMove(mouseX, mouseY)) {
						bOver = true;
						this.over = this.connected[y][x];
						break;
					} else if (this.buttons[y][x].isBOver()) {
						bOver = true;
						this.over = this.connected[y][x];
					}
				}
			}
			if (bOver) {
				return 1;
			} else {
				return 2;
			}
		}
		return -1;
	}
	
	public void render(Graphics2D g) {		
		if (this.buttonImages != null) {
			for (int i = 0; i < this.buttonImages.length; i++) {
				if (this.over == i) {
					g.drawImage(this.buttonImages[i][1], ApoSimpleSudokuGame.startX, ApoSimpleSudokuGame.startY, null);
				}
			}
		}

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final float oneWidth = (ApoSimpleSudokuGame.width) / this.level.length;
		final int width = (int)(oneWidth - 20);
		final Stroke stroke = new BasicStroke(3);
		final Stroke basicStroke = new BasicStroke(7);
		
		for (int y = 0; y < this.buttons.length; y++) {
			for (int x = 0; x < this.buttons[0].length; x++) {
				if (this.buttons[y][x].isBVisible()) {
					int drawX = (int)(0.5f * oneWidth + x * oneWidth) + ApoSimpleSudokuGame.startX;
					int drawY = (int)(0.5f * oneWidth + y * oneWidth) + ApoSimpleSudokuGame.startY;
					boolean bInsert = false;
					if ((this.buttons[y][x].isBOver()) && (this.help.getLevel()[y][x] <= 0)) {
						if (this.level[y][x] <= 0) {							
							int nextWidth = 15;
							int change = 30;
							if (this.help.getWidth() > 4) {
								change -= (this.help.getWidth()-4)*5;
								nextWidth -= (this.help.getWidth()-4)*1;
							}
							int cX = drawX - change;
							int cY = drawY - change;
							for (int i = 0; i < this.helpMenuString[y][x].length() + 1; i++) {
								if ((new Rectangle2D.Double(cX, cY, nextWidth, nextWidth)).intersects(mouseX, mouseY, 1, 1)) {
									bInsert = true;
									this.valueHelpMenu = i;
									break;
								}
										
								if (i < 2) {
									cX += nextWidth + 3;
								} else if ((i == 2) || (i == 4)) {
									cX = drawX - change;
									cY += nextWidth + 3;
								} else if (i == 3) {
									cX += 2 * (nextWidth + 3);
								} else {
									cX += nextWidth + 3;
								}
							}
						}						
						
						if ((!bInsert) && (this.helpMenu == null)) {
							g.setStroke(basicStroke);
							g.setColor(ApoSimpleSudokuLevel.c);
							g.fillOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
						
							g.setColor(Color.BLACK);
							g.setStroke(stroke);
							g.drawOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
						}
					} else {
						if ((this.overValue > 0) && (this.overValue == this.level[y][x])) {							
							g.setStroke(basicStroke);
							g.setColor(ApoSimpleSudokuLevel.same);
							g.fillOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
							
							g.setColor(Color.BLACK);
							g.setStroke(stroke);
							g.drawOval(drawX - width/2 - 1, drawY - width/2 - 1, width + 2, width + 2);
						}
					}
					
					if (this.level[y][x] <= 0) {
						int nextWidth = 15;
						int change = 30;
						if (this.help.getWidth() > 4) {
							change -= (this.help.getWidth()-4)*4;
							nextWidth -= (this.help.getWidth()-4)*1;
						}
						if ((this.helpMenuString[y][x] == null) || (this.helpMenuString[y][x].length() <= 0)) {
							if ((this.buttons[y][x].isBOver()) && (this.help.getLevel()[y][x] <= 0)) {
								g.setColor(Color.WHITE);
								if ((bInsert) || (this.helpMenu != null)) {
									g.setColor(ApoSimpleSudokuConstants.BROWN);
								}
								g.fillRoundRect(drawX - change, drawY - change, nextWidth, nextWidth, 15, 15);
							}
						} else {
							int cX = drawX - change;
							int cY = drawY - change;
							g.setFont(ApoSimpleSudokuConstants.FONT_LEVEL_FPS);
							int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
							for (int i = 0; i < this.helpMenuString[y][x].length() + 1; i++) {
								if ((this.buttons[y][x].isBOver()) && (this.help.getLevel()[y][x] <= 0)) {
									g.setColor(Color.WHITE);
									if ((bInsert) || (this.helpMenu != null)) {
										g.setColor(ApoSimpleSudokuConstants.BROWN);
									}
									g.fillRoundRect(cX, cY, nextWidth, nextWidth, 15, 15);
									g.setColor(Color.BLACK);
									if ((bInsert) || (this.helpMenu != null)) {
										g.setColor(Color.WHITE);
									}
								} else if (i < this.helpMenuString[y][x].length()) {
									g.setColor(Color.BLACK);
									if (bInsert) {
										g.setColor(Color.WHITE);
									}
								} else {
									break;
								}
								String s = "";
								if (i < this.helpMenuString[y][x].length()) {
									s = String.valueOf(this.helpMenuString[y][x].charAt(i));
								}
								int w = g.getFontMetrics().stringWidth(s);
								g.drawString(s, cX + nextWidth/2 - w/2, cY + nextWidth/2 + h/2);
								
								if (i < 2) {
									cX += nextWidth + 3;
								} else if ((i == 2) || (i == 4)) {
									cX = drawX - change;
									cY += nextWidth + 3;
								} else if (i == 3) {
									cX += 2 * (nextWidth + 3);
								} else {
									cX += nextWidth + 3;
								}
							}
						}
					}
					
					g.setFont(ApoSimpleSudokuConstants.FONT_INGAME);
					if (this.level[y][x] > 0) {
						String s = String.valueOf(this.level[y][x]);
						if (this.help.getLevel()[y][x] > 0) {
							g.setColor(Color.BLACK);
						} else {
							g.setColor(ApoSimpleSudokuConstants.BROWN);
						}
						for (int i = 0; i < this.mistakes.size(); i++) {
							if ((this.mistakes.get(i).x == x) &&
								(this.mistakes.get(i).y == y)) {
								g.setColor(Color.RED);
								break;
							}
						}
						int w = g.getFontMetrics().stringWidth(s);
						int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
						g.drawString(s, drawX - w/2, drawY + h /2);
					}
				}
			}
		}
		
		if (this.menu != null) {
			this.menu.render(g, 0, 0);
		}
		if (this.helpMenu != null) {
			this.helpMenu.render(g, 0, 0);
		}
	}
}
