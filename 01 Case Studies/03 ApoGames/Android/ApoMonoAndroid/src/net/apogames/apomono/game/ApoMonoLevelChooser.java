package net.apogames.apomono.game;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoLevelChooserButton;
import net.apogames.apomono.level.ApoMonoLevel;
import net.gliblybits.bitsengine.graphics.BitsGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoLevelChooser extends ApoMonoModel {

	public static final int COUNT = 40;
	
	public static final String BACK = "back";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	
	private ApoLevelChooserButton[] levels;
	
	private int curStart = 0;
	
	public int canStart = 1;
	
	public ApoMonoLevelChooser(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.changeCurStart(0);
		
		if (this.levels == null) {
			this.levels = new ApoLevelChooserButton[ApoMonoLevel.MAX_LEVELS];
			
			int xPos = 60;
			int yPos = ApoMonoPuzzleGame.changeY + 45;
			int radius = 40;
			int add = 5;
			int curLevel = 0;
			for (int y = 0; y < 100; y++) {
				for (int x = 0; x < 8; x++) {
					this.levels[curLevel] = new ApoLevelChooserButton(xPos, yPos, radius, radius, String.valueOf(curLevel + 1), "");
					
					if (this.getGame().getSolvedLevels().isLevelSolved(curLevel)) {
						this.levels[curLevel].setSelected(true);
					}
					
					xPos += radius + add;
					curLevel += 1;
					if (curLevel >= this.levels.length) {
						break;
					}
				}
				xPos = 60;
				yPos += radius + add;
				if (curLevel >= this.levels.length) {
					break;
				}
				if (curLevel % ApoMonoLevelChooser.COUNT == 0) {
					yPos = ApoMonoPuzzleGame.changeY + 45;
				}
			}
			this.canStart = this.getGame().getMaxCanChoosen();
		}
	}
	
	public void solveLevel(int level) {
		if ((level >= 0) && (level < ApoMonoLevel.MAX_LEVELS)) {
			this.levels[level].setSelected(true);
		}
		
		this.canStart = this.getGame().getMaxCanChoosen();
	}
	
	public void onResume() {
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if (this.levels != null) {
			for (int i = this.curStart; i < this.curStart + ApoMonoLevelChooser.COUNT && i < this.levels.length; i++) {
				if ((this.levels[i].intersects(x, y)) && (i <= this.canStart)) {
					this.getGame().setGame(i, "", false);
				}
			}
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoMonoLevelChooser.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoMonoLevelChooser.LEFT)) {
			this.changeCurStart(-ApoMonoLevelChooser.COUNT);
		} else if (function.equals(ApoMonoLevelChooser.RIGHT)) {
			this.changeCurStart(ApoMonoLevelChooser.COUNT);
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON_2);
	}
	
	private void changeCurStart(int add) {
		this.curStart += add;
		if (this.curStart < 0) {
			this.curStart = 0;
		} else if (this.curStart >= ApoMonoLevel.MAX_LEVELS) {
			this.curStart -= add;
		}
		this.getGame().getButtons()[19].setVisible(true);
		if (this.curStart <= 0) {
			this.getGame().getButtons()[19].setVisible(false);
		}
		
		this.getGame().getButtons()[20].setVisible(true);
		if (this.curStart + ApoMonoLevelChooser.COUNT >= ApoMonoLevel.MAX_LEVELS) {
			this.getGame().getButtons()[20].setVisible(false);
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(final BitsGLGraphics g) {
		int addY = 15;
		if (ApoMonoConstants.FREE_VERSION) {
			addY += 36;
		}
		this.getGame().drawString(g, ApoMonoConstants.LEVELCHOOSER_TITLE, (int)(240 - ApoMonoPanel.title_font.getLength(ApoMonoConstants.LEVELCHOOSER_TITLE)/2), 5 + addY, ApoMonoPanel.title_font);
		
		if (this.levels != null) {
			for (int i = this.curStart; i < this.curStart + ApoMonoLevelChooser.COUNT && i < this.levels.length; i++) {
				int x = (int)(this.levels[i].getX());
				int y = (int)(this.levels[i].getY());
				int width = (int)(this.levels[i].getWidth());
				int height = (int)(this.levels[i].getHeight());
				String s = this.levels[i].getFunction();
				
				this.drawInputBox(g, x, y, width, height, this.levels[i].isSelected());
				
				if (i < this.canStart + 1) {
					if (this.levels[i].isSelected()) {
						this.getGame().drawString(g, s, (int)(x + width/2 - ApoMonoPanel.game_font.getLength(s)/2), y + height/2 - 1 - ApoMonoPanel.game_font.mCharCellHeight/2, ApoMonoPanel.game_font, ApoMonoConstants.GREEN);
					} else {
						this.getGame().drawString(g, s, (int)(x + width/2 - ApoMonoPanel.game_font.getLength(s)/2), y + height/2 - 1 - ApoMonoPanel.game_font.mCharCellHeight/2, ApoMonoPanel.game_font, ApoMonoConstants.BRIGHT_DARK);						
					}
				} else {
					this.getGame().drawString(g, s, (int)(x + width/2 - ApoMonoPanel.game_font.getLength(s)/2), y + height/2 - 1 - ApoMonoPanel.game_font.mCharCellHeight/2, ApoMonoPanel.game_font, ApoMonoConstants.DARK_BRIGHT);
				}
			}
		}
		
		if (this.getGame().getButtons() != null) {
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isBVisible()) {
					int x = (int)(this.getGame().getButtons()[i].getX());
					int y = (int)(this.getGame().getButtons()[i].getY());
					int width = (int)(this.getGame().getButtons()[i].getWidth());
					int height = (int)(this.getGame().getButtons()[i].getHeight());
					String s = this.getGame().getButtons()[i].getFunction();
					
					this.drawInputBox(g, x, y, width, height, false);
					if (s.equals(ApoMonoLevelChooser.LEFT)) {
						this.drawInputLeftBox(g, x, y);
					} else if (s.equals(ApoMonoLevelChooser.RIGHT)) {
						this.drawInputRightBox(g, x, y);
					} else if (s.equals(ApoMonoLevelChooser.BACK)) {
						this.getGame().drawString(g, s, (int)(x + width/2 - ApoMonoPanel.game_font.getLength(s)/2), y + height/2 - ApoMonoPanel.game_font.mCharCellHeight/2 - 1, ApoMonoPanel.game_font, ApoMonoConstants.BRIGHT);
					}
				}
			}
		}
		
	}
	
	private void drawInputBox(final BitsGraphics g, int x, int y, final int width, final int height, boolean bSolved) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 2, y + 2, width - 4, height - 4);
		if (bSolved) {
			ApoMonoPuzzleGame.setBrighterColor(g);
		} else {
			ApoMonoPuzzleGame.setDarkerColor(g);
		}
		g.fillRect(x + 2, y,  width - 4, 2);
		g.fillRect(x + 2, y + height - 2, width - 4, 2);
		g.fillRect(x, y + 2, 2, height - 4);
		g.fillRect(x + width - 2, y + 2, 2, height - 4);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(x + 4, y + height, width - 4, 2);
		g.fillRect(x + width, y + 4, 2, height - 4);
		g.fillRect(x + width - 2, y + height - 2, 2, 2);
	}
	
	private void drawInputRightBox(final BitsGraphics g, final int x, final int y) {
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputLeftBox(final BitsGraphics g, final int x, final int y) {
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 30 - i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
}
