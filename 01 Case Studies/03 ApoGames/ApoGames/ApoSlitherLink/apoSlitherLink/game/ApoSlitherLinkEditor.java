package apoSlitherLink.game;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.apogames.help.ApoHighscore;

import apoSlitherLink.level.ApoSlitherLinkLevelHelp;

public class ApoSlitherLinkEditor extends ApoSlitherLinkModel {

	public static final String RETURN_STRING = "Menu";
	public static final String RETURN = "game_return";
	public static final String LEFTWIDTH_STRING = "<";
	public static final String LEFTWIDTH = "game_leftwidth";
	public static final String RIGHTWIDTH_STRING = ">";
	public static final String RIGHTWIDTH = "game_rightwidth";
	public static final String LEFTHEIGHT_STRING = "<";
	public static final String LEFTHEIGHT = "game_leftheight";
	public static final String RIGHTHEIGHT_STRING = ">";
	public static final String RIGHTHEIGHT = "game_rightheight";
	public static final String SET_STRING = "Set";
	public static final String SET = "game_set";
	public static final String TEST_STRING = "Test";
	public static final String TEST = "game_test";
	public static final String UPLOAD_STRING = "Upload";
	public static final String UPLOAD = "game_upload";
	
	private final int MAX_WIDTH = 20;
	private final int MIN_WIDTH = 4;
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	private final int MAX_HEIGHT = 15;
	private final int MIN_HEIGHT = 4;
	
	private int width;
	private int height;
	
	private boolean[][] over;
	
	private boolean bIn;
	
	private boolean bSolved;
	
	private boolean bUploadCheck;
	
	private boolean bUpload;
	
	public ApoSlitherLinkEditor(ApoSlitherLinkPanel game) {
		super(game);
		
		this.width = 4;
		this.height = 4;
		
		this.bSolved = false;
	}
	
	public void init(boolean bSolved) {
		this.bUploadCheck = false;
		if (this.over == null) {
			this.over = new boolean[this.height][this.width];
			this.set();
			this.getGame().getButtons()[22].setBVisible(false);
			this.getGame().getButtons()[23].setBVisible(false);
		} else {
			this.getGame().getLevel().retryLevel();
			this.bSolved = bSolved;
			this.getGame().getButtons()[22].setBVisible(false);
			this.getGame().getButtons()[23].setBVisible(this.bSolved);
		}
		this.getGame().getLevel().makeLevelImage();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSlitherLinkEditor.RETURN)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoSlitherLinkEditor.LEFTHEIGHT)) {
			this.height -= 1;
			if (this.height < this.MIN_HEIGHT) {
				this.height = this.MAX_HEIGHT;
			}
		} else if (function.equals(ApoSlitherLinkEditor.RIGHTHEIGHT)) {
			this.height += 1;
			if (this.height > this.MAX_HEIGHT) {
				this.height = this.MIN_HEIGHT;
			}
		} else if (function.equals(ApoSlitherLinkEditor.LEFTWIDTH)) {
			this.width -= 1;
			if (this.width < this.MIN_WIDTH) {
				this.width = this.MAX_WIDTH;
			}
		} else if (function.equals(ApoSlitherLinkEditor.RIGHTWIDTH)) {
			this.width += 1;
			if (this.width > this.MAX_WIDTH) {
				this.width = this.MIN_WIDTH;
			}
		} else if (function.equals(ApoSlitherLinkEditor.SET)) {
			this.set();
		} else if (function.equals(ApoSlitherLinkEditor.TEST)) {
			this.getGame().testLevelFromEditor();
			this.getGame().getLevel().makeLevelImage();
		} else if (function.equals(ApoSlitherLinkEditor.UPLOAD)) {
			if ((this.bSolved) && (this.getGame().getLevel().isCorrectLevel())) {
				this.bUpload = false;
				this.bUploadCheck = true;
				this.getGame().getLevel().addCustomLevels();
				new Thread() {
					public void run() {
						ApoSlitherLinkEditor.this.bUpload = ApoHighscore.getInstance().save(ApoSlitherLinkEditor.this.getGame().getLevel().getCurrentLevel().getLevelString(), ApoSlitherLinkEditor.this.getGame().getLevel().getCurrentLevel().getSolvedLevelString(), "Apo");						
					}
				}.start();
			}
		}
	}
	
	private void set() {
		this.getGame().getButtons()[22].setBVisible(true);
		this.getGame().getButtons()[23].setBVisible(false);
		this.getGame().getLevel().setLevel(this.width, this.height);
		this.over = new boolean[this.height][this.width];
		this.bUploadCheck = false;
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
		int xLength = this.getGame().getLevel().getLevel()[0].length * width;
		int yLength = this.getGame().getLevel().getLevel().length * width;

		if (new Rectangle2D.Double(this.getGame().getGame().getChangeX(), this.getGame().getGame().getChangeY(), xLength, yLength).contains(x, y)) {
			int levelX = (int)(((float)x - (float)this.getGame().getGame().getChangeX()) / (float)width);
			int levelY = (int)(((float)y - (float)this.getGame().getGame().getChangeY()) / (float)width);
			int value = this.getGame().getLevel().getLevel()[levelY][levelX].getValue();
			if (right) {
				value -= 1;
			} else {
				value += 1;
			}
			if (value < -1) {
				value = 3;
			} else if (value > 3) {
				value = -1;
			}
			String s = String.valueOf(value);
			if (value == -1) {
				s = " ";
			}
			this.getGame().getLevel().changeLevelString(levelY * this.getGame().getLevel().getLevel()[0].length + levelX + 4, String.valueOf(s));
			this.bSolved = false;
			this.bUploadCheck = false;
			if (this.getGame().getLevel().isCorrectLevel()) {
				this.getGame().getButtons()[22].setBVisible(true);
				this.getGame().getButtons()[23].setBVisible(false);
			}
		}
	}
	
	public void mouseMoved(int x, int y) {
		int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
		int xLength = this.getGame().getLevel().getLevel()[0].length * width;
		int yLength = this.getGame().getLevel().getLevel().length * width;

		if (new Rectangle2D.Double(this.getGame().getGame().getChangeX(), this.getGame().getGame().getChangeY(), xLength, yLength).contains(x, y)) {
			int levelX = (int)(((float)x - (float)this.getGame().getGame().getChangeX()) / (float)width);
			int levelY = (int)(((float)y - (float)this.getGame().getGame().getChangeY()) / (float)width);
			for (int i = 0; i < this.over.length; i++) {
				for (int j = 0; j < this.over[0].length; j++) {
					if ((levelY == i) && (levelX == j)) {
						this.over[i][j] = true;
					} else {
						this.over[i][j] = false;
					}
				}
			}
			this.bIn = true;
		} else {
			if (this.bIn) {
				this.bIn = false;
				for (int i = 0; i < this.over.length; i++) {
					for (int j = 0; j < this.over[0].length; j++) {
						this.over[i][j] = false;
					}
				}
			}
		}
	}

	@Override
	public void think(int delta) {
		super.think(delta);
	}


	@Override
	public void render(Graphics2D g) {
		super.getGame().renderBackground(g);
		
		int changeX = this.getGame().getGame().getChangeX();
		int changeY = this.getGame().getGame().getChangeY();
		this.getGame().getLevel().render(g, changeX, changeY);
		
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_MOVE);
		for (int i = 0; i < this.over.length; i++) {
			for (int j = 0; j < this.over[0].length; j++) {
				if (this.over[i][j]) {
					Stroke stroke = g.getStroke();
					g.setStroke(new BasicStroke(3));
					int width = this.getGame().getLevel().getLevel()[0][0].getWidth();
					g.drawRect(changeX + j * width, changeY + i * width, width - 3, width - 3);
					g.setStroke(stroke);
				}
			}
		}

		g.setColor(ApoSlitherLinkLevelHelp.COLOR_LINE);
		g.setFont(this.FONT);
		int x = (int)((this.getGame().getButtons()[17].getX() - this.getGame().getButtons()[16].getWidth() - this.getGame().getButtons()[16].getX())/2 + this.getGame().getButtons()[16].getWidth() + this.getGame().getButtons()[16].getX());
		int y = (int)(this.getGame().getButtons()[17].getY() + this.getGame().getButtons()[17].getHeight()/2);
		String s = String.valueOf(this.width);
		int w = g.getFontMetrics().stringWidth(s);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		g.drawString(s, x - w/2, y + h/2);
		
		y = (int)(this.getGame().getButtons()[18].getY() + this.getGame().getButtons()[18].getHeight()/2);
		s = String.valueOf(this.height);
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y + h/2);
		
		if (this.bUploadCheck) {
			x = (int)(this.getGame().getButtons()[23].getX() + this.getGame().getButtons()[23].getWidth()) + 2;
			y = (int)(this.getGame().getButtons()[23].getY() + this.getGame().getButtons()[23].getHeight()/2);
			if (this.bUpload) {
				s = "upload complete";
			} else {
				s = "upload in progress";
			}
			g.drawString(s, x, y + h/2);
		}
	}
}
