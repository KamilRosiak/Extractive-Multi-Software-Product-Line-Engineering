package apoSlitherLink.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.level.ApoSlitherLinkLevelHelp;

public class ApoSlitherLinkGame extends ApoSlitherLinkModel {
	
	public static final String MENU_STRING = "Levels";
	public static final String MENU = "game_menu";
	public static final String RETRY_STRING = "Retry";
	public static final String RETRY = "game_retry";
	public static final String NEXT_STRING = "Next Puzzle";
	public static final String NEXT = "game_next";
	public static final String RETURN_STRING = "Return To Menu";
	public static final String RETURN = "game_return";
	public static final String LEFT_STRING = "<";
	public static final String LEFT = "game_left";
	public static final String RIGHT_STRING = ">";
	public static final String RIGHT = "game_right";
	
	private boolean bMove;
	
	private boolean bWin;
	
	private BufferedImage iWin;
	
	private boolean bEditor;
	
	private ApoEntity entity;
	
	public ApoSlitherLinkGame(ApoSlitherLinkPanel game) {
		super(game);
	}
	
	public void init(boolean bEditor) {
		this.bWin = false;
		this.bEditor = bEditor;
		this.buttonVisibile();
		
		if (this.entity == null) {
			int width = 100;
			this.entity = new ApoEntity(null, ApoSlitherLinkConstants.GAME_WIDTH/2 - width/2, 15, width, 35);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSlitherLinkGame.MENU)) {
			if (this.bEditor) {
				this.getGame().setEditor(false);
			} else {
				this.getGame().startLevelChooser();
			}
		} else if (function.equals(ApoSlitherLinkGame.RETURN)) {
			if (this.bEditor) {
				this.getGame().setEditor(true);
			} else {
				this.getGame().setMenu();
			}
		} else if (function.equals(ApoSlitherLinkGame.RETRY)) {
			this.getGame().getLevel().retryLevel();
			this.init();
		} else if (function.equals(ApoSlitherLinkGame.NEXT)) {
			if (this.bEditor) {
				this.getGame().setEditor(true);
			} else {
				this.init(this.bEditor);
			}
		} else if (function.equals(ApoSlitherLinkGame.LEFT)) {
			this.getGame().getLevel().nextLevel(false, -1);
			this.init(this.bEditor);
		} else if (function.equals(ApoSlitherLinkGame.RIGHT)) {
			this.getGame().getLevel().nextLevel(false);
			this.init(this.bEditor);
		}
	}
	
	public void mouseMoved(int x, int y) {
		int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
		int xLength = this.getGame().getLevel().getLevel()[0].length * width;
		int yLength = this.getGame().getLevel().getLevel().length * width;

		if (new Rectangle2D.Double(this.getChangeX(), this.getChangeY(), xLength, yLength).contains(x, y)) {
			this.getGame().getLevel().mouseMoved(x - this.getChangeX(), y - this.getChangeY());
			this.bMove = true;
		} else {
			if (this.bMove) {
				this.getGame().getLevel().mouseMoved();
			}
			this.bMove = false;
			if (this.entity.intersects(x, y)) {
				this.entity.setBSelect(true);
			} else {
				this.entity.setBSelect(false);
			}
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (this.bWin) {
			
		} else {
			int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
			int xLength = this.getGame().getLevel().getLevel()[0].length * width;
			int yLength = this.getGame().getLevel().getLevel().length * width;
	
			if (new Rectangle2D.Double(this.getChangeX(), this.getChangeY(), xLength, yLength).contains(x, y)) {
				this.getGame().getLevel().mouseReleased(x - this.getChangeX(), y - this.getChangeY(), bRight);
			}
			if (this.getGame().getLevel().isSolved()) {
				this.iWin = new BufferedImage(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D)(this.iWin.getGraphics());
				this.getGame().renderBackground(g);
				
				int changeX = this.getChangeX();
				int changeY = this.getChangeY();
				this.getGame().getLevel().render(g, changeX, changeY);
				
				g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
			    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			    String s = "Congratulation";
			    int w = g.getFontMetrics().stringWidth(s);
			    int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			    g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, 35 + h/2);
				
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(0, 70, ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT - 70);
				
				g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND);
				g.fillRoundRect(25, 180, ApoSlitherLinkConstants.GAME_WIDTH - 50, 130, 50, 50);
				g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(10));
				g.drawRoundRect(29, 184, ApoSlitherLinkConstants.GAME_WIDTH - 58, 122, 50, 50);
				g.setStroke(stroke);
				
				g.setFont(ApoSlitherLinkMenu.FONT_LEVEL);
				s = "PUZZLE CLEAR";
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, this.getGame().getButtons()[8].getY() - 20);
				
				g.dispose();
				this.bWin = true;
				this.buttonVisibile();
				if (this.bEditor) {
					this.getGame().getLevel().addCustomLevels();
				}
				if (this.getGame().getLevel().getCurrentLevel() != null) {
					this.getGame().getLevel().getCurrentLevel().setSolvedLevelString(this.getGame().getLevel());
				}
				if (!this.bEditor) {
					this.getGame().getLevel().nextLevel(true);
				}
			}
		}
	}
	
	private void buttonVisibile() {
		if (this instanceof ApoSlitherLinkTutorial) {
			this.getGame().getButtons()[5].setBVisible(false);
			this.getGame().getButtons()[6].setBVisible(false);
			this.getGame().getButtons()[10].setBVisible(false);
			this.getGame().getButtons()[11].setBVisible(false);
			this.getGame().getButtons()[8].setBVisible(false);
			this.getGame().getButtons()[9].setBVisible(false);
		} else {
			this.getGame().getButtons()[5].setBVisible(!this.bWin);
			this.getGame().getButtons()[6].setBVisible(!this.bWin);
			this.getGame().getButtons()[10].setBVisible(!this.bWin);
			this.getGame().getButtons()[11].setBVisible(!this.bWin);
			this.getGame().getButtons()[8].setBVisible(this.bWin);
			this.getGame().getButtons()[9].setBVisible(this.bWin);
		}
	}

	@Override
	public void think(int delta) {
		super.think(delta);
	}
	
	public int getChangeX() {
		int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
		return ApoSlitherLinkConstants.GAME_WIDTH/2 - this.getGame().getLevel().getLevel()[0].length * width / 2;
	}
	
	public int getChangeY() {
		int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
		return (ApoSlitherLinkConstants.GAME_HEIGHT - 70)/2 - this.getGame().getLevel().getLevel().length * width / 2 + 70;
	}
	
	public boolean isBWin() {
		return this.bWin;
	}

	@Override
	public void render(Graphics2D g) {
		if (!this.bWin) {
			int changeX = this.getChangeX();
			int changeY = this.getChangeY();
			this.getGame().getLevel().render(g, changeX, changeY);
			
			if (!(this instanceof ApoSlitherLinkTutorial)) {
			    g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
			    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			    String s = (this.getGame().getLevel().getCurLevel() + 1) + " / " + this.getGame().getLevel().getMaxLevel();
			    int w = g.getFontMetrics().stringWidth(s);
			    int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			    if (this.getGame().getLevel().isCurLevelSolved()) {
			    	g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, 33);
			    } else {
			    	g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, 35 + h/2);
			    }
			    s = "";
			    if (this.getGame().getLevel().isCurLevelSolved()) {
			    	s = "solved";
			    	if ((this.entity != null) && (this.entity.isBSelect())) {
						BufferedImage iSolved = this.getGame().getLevel().getCurLevelSolvedImage();
						if (iSolved != null) {
							int imageX = (int)(this.entity.getX() + this.entity.getWidth()/2) - iSolved.getWidth()/2;
							if (imageX < 0) {
								imageX = 0;
							} else if (imageX + iSolved.getWidth() > ApoSlitherLinkConstants.GAME_WIDTH) {
								imageX = ApoSlitherLinkConstants.GAME_WIDTH - iSolved.getWidth();
							}
							int imageY = (int)(this.entity.getY() + this.entity.getHeight());
							g.drawImage(iSolved, imageX, imageY, null);
						}
			    	}
			    }
			    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
			    w = g.getFontMetrics().stringWidth(s);
			    h = g.getFontMetrics().getHeight();
			    g.drawString(s, this.getGame().getIBackground().getWidth()/2 - w/2, 33 + h);				
			}
		} else {
			g.drawImage(this.iWin, 0, 0, null);
		}
	}
}
