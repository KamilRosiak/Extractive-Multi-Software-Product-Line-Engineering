package apoStarz.editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apogames.ApoComponent;
import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoFileFilter;

import apoStarz.ApoStarzApplet;
import apoStarz.ApoStarzConstants;
import apoStarz.ApoStarzImages;
import apoStarz.ApoStarzMain;
import apoStarz.game.ApoStarzIO;
import apoStarz.level.ApoStarzLevel;
import apoStarz.solver.ApoStarzSolver;

public class ApoStarzEditor extends ApoComponent {

	private static final long serialVersionUID = 1L;
	
	private ApoStarzImages images;
	
	private BufferedImage iBackground, iTransparent;
	
	private ApoStarzLevel level;
	
	private ApoStarzMain main;
	
	private ApoStarzApplet applet;
	
	private JTextField levelField;
	
	private int setX, setY, curImage, pressedX, pressedY;
	
	private boolean bLeftMouse;
	
	private JTextArea textArea;
	
	private JScrollPane scrollPane;
	
	private ApoStarzIO io;
	
	private int curLevel;
	
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private final ApoFileFilter	fileFilter = new ApoFileFilter("ase");
	
	private ApoStarzSolver solver;
	
	private String curSolution;
	
	private String curLevelString;
	
	private String curMouseFunction;
	
	public ApoStarzEditor(ApoStarzMain main) {
		this(main, true, true, ApoStarzConstants.WAIT_TIME);
	}
	
	public ApoStarzEditor(ApoStarzMain main, boolean mouse, boolean key, int wait_time) {
		super(mouse, key, wait_time);
		
		this.main = main;
	}

	public ApoStarzEditor(ApoStarzApplet applet) {
		this(applet, true, true, ApoStarzConstants.WAIT_TIME);
	}
	
	public ApoStarzEditor(ApoStarzApplet applet, boolean mouse, boolean key, int wait_time) {
		super(mouse, key, wait_time);
		
		this.applet = applet;
	}
	
	public void setGame(String levelString) {
		if (this.main != null) {
			this.main.setGame(levelString);
		} else if (this.applet != null) {
			this.applet.setGame(levelString);
		}
	}

	@Override
	public void init() {
		this.setX = -1;
		this.setY = -1;
		this.curMouseFunction = null;
		if (!ApoConstants.B_APPLET) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File( System.getProperty("user.dir") + File.separator+ "levels" ) );
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		if (this.solver == null) {
			this.solver = new ApoStarzSolver();
		}
		if (this.io == null) {
			this.io = new ApoStarzIO();
			this.curLevel = -1;
		}
		if (this.levelField == null) {
			this.levelField = new JTextField("12");
			this.levelField.setBounds(ApoStarzConstants.GAME_WIDTH - 60, 70, 55, 20);
			this.add(this.levelField);
		}
		if (this.textArea == null) {
			this.textArea = new JTextArea("");
			this.textArea.setEditable(true);
			this.textArea.setLineWrap(true);
		}
		if (this.scrollPane == null) {
			this.scrollPane = new JScrollPane(this.textArea);
			this.scrollPane.setBounds(ApoStarzConstants.GAME_GAME_WIDTH + 5, ApoStarzConstants.GAME_HEIGHT - 135, ApoStarzConstants.GAME_MENU_WIDTH - 10, 60);
			this.add(this.scrollPane);
		}
		if (this.images == null) {
			this.images = new ApoStarzImages();
		}
		if (this.iBackground == null) {
			this.iBackground = this.images.getBackgroundEditor(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT, Color.black);
		}
		
		if (this.level == null) {
			this.level = new ApoStarzLevel(this.images);
			this.makeNewLevel(12);
			this.textArea.setText(this.level.getLevelString());
		}
		
		if (super.getButtons() == null) {
			super.setButtons(new ApoButton[18]);
			
			String text = "X";
			String function = "quit";
			int width = 30;
			int height = 20;
			int x = this.getWidth() - 5 - width;
			int y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[0] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = "test";
			function = "game";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[1] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = "new Level";
			function = "new";
			width = 80;
			height = 20;
			x = this.getWidth() - width/2 - ApoStarzConstants.GAME_MENU_WIDTH/2;
			y = 95;
			super.getButtons()[2] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );	
		
			text = "wall";
			function = "wall";
			width = 60;
			height = 36;
			x = this.getWidth() - width - 5;
			y = 125;
			super.getButtons()[3] = new ApoButton( this.images.getButtonWithImage( width * 3, height, this.level.getIWall(), Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "block";
			function = "block";
			width = 60;
			height = 36;
			x = this.getWidth() - width - 5;
			y = 165;
			super.getButtons()[4] = new ApoButton( this.images.getButtonWithImage( width * 3, height, this.level.getIBlock(), Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "fire";
			function = "fire";
			width = 60;
			height = 36;
			x = this.getWidth() - width - 5;
			y = 205;
			super.getButtons()[5] = new ApoButton( this.images.getButtonWithImage( width * 3, height, this.level.getIFire().getSubimage(0, 0, this.level.getIFire().getWidth()/4, this.level.getIFire().getHeight()), Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "goal";
			function = "goal";
			width = 60;
			height = 36;
			x = this.getWidth() - width - 5;
			y = 245;
			super.getButtons()[6] = new ApoButton( this.images.getButtonWithImage( width * 3, height, this.level.getIGoal(), Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "star";
			function = "star";
			width = 60;
			height = 36;
			x = this.getWidth() - width - 5;
			y = 285;
			super.getButtons()[7] = new ApoButton( this.images.getButtonWithImage( width * 3, height, this.level.getIStar(), Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "loadS";
			function = "loadLevelString";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 55;
			y = ApoStarzConstants.GAME_HEIGHT - 50;
			super.getButtons()[8] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );	
			
			text = "load";
			function = "loadLevel";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = ApoStarzConstants.GAME_HEIGHT - 50;
			super.getButtons()[9] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );	
			
			text = "save";
			function = "saveLevel";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - width - 5;
			y = ApoStarzConstants.GAME_HEIGHT - 50;
			super.getButtons()[10] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = "<";
			function = "levelBefore";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[11] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = ">";
			function = "levelAfter";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - width - 5;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[12] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "<";
			function = "levelPullBefore";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 25;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[13] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = ">";
			function = "levelPullAfter";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - width - 25;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[14] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );

			text = "add";
			function = "levelAdd";
			width = 35;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 45;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[15] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = "rem";
			function = "levelRem";
			width = 35;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 80;
			y = ApoStarzConstants.GAME_HEIGHT - 75;
			super.getButtons()[16] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );
			
			text = "solve";
			function = "solver";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 55;
			y = ApoStarzConstants.GAME_HEIGHT - height - 5;
			super.getButtons()[17] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white ), x, y, width, height, function );	
		}
		if (ApoConstants.B_APPLET) {
			for (int i = 9; i < super.getButtons().length; i++) {
				super.getButtons()[i].setBVisible(false);
			}
			super.getButtons()[0].setBVisible(false);
			super.getButtons()[17].setBVisible(true);
		}
	}
	
	public void setLevelString(String levelString) {
		this.level.loadLevel(levelString);
		this.textArea.setText(this.level.getLevelString());
	}
	
	private boolean levelLoad() {
		int p = this.fileChooser.showOpenDialog(this);
		if(p == 0) {
			String s = this.fileChooser.getSelectedFile().getPath();
			
			if (this.io.readLevel(s)) {
				this.curLevel = 0;
				this.setLevelString(this.io.getLevel(this.curLevel));
				return true;
			}
		}
		return false;
	}
	
	private boolean levelSave() {
		int p = this.fileChooser.showSaveDialog(this);
		if(p == 0) {
			String s = this.fileChooser.getSelectedFile().getPath();
			int t = s.indexOf(46);
			if ( t != -1 ) {
				s	= s.substring( 0, t );
			}
			s	+= this.fileFilter.getLevelName();
			
			this.io.writeLevel( s );
		}
		return false;
	}

	private boolean levelCheck(String newLevel) {
		if ((newLevel == null) || (newLevel.length() <= 0)) {
			return false;
		}
		boolean bCheck = this.level.loadLevel(newLevel);
		if (bCheck) {
			return this.level.isStarAndGoalEqual();
		}
		return bCheck;
	}
	
	@Override
	public void setButtonFunction(String function) {
		this.curMouseFunction = function;
	}
	
	private void thinkMouseFunction() {
		if ((this.curMouseFunction == null) || (this.curMouseFunction.length() <= 0)) {
			return;
		}
		String function = this.curMouseFunction;
		if (function.equals("quit")) {
			System.exit(0);			
		} else if (function.equals("game")) {
			if (this.levelCheck(this.level.getLevelString())) {
				if (this.io.addLevel(this.level.getLevelString())) {
					if (this.curLevel == -1) {
						this.curLevel = 0;
					} else {
						this.curLevel = this.io.getLevelSize() - 1;
					}
				}
				this.setGame(this.level.getLevelString());
			}
		} else if (function.equals("loadLevelString")) {
			this.setLevelString(this.textArea.getText());
		} else if (function.equals("levelAdd")) {
			if (this.levelCheck(this.level.getLevelString())) {
				if (this.io.addLevel(this.level.getLevelString())) {
					if (this.curLevel == -1) {
						this.curLevel = 0;
					} else {
						this.curLevel = this.io.getLevelSize() - 1;
					}
				}
			}
		} else if (function.equals("levelRem")) {
			this.io.remLevel(this.textArea.getText());
			this.curLevel--;
			if ((this.curLevel < 0) && (this.io.getLevelSize() > 0)) {
				this.curLevel = this.io.getLevelSize() - 1;
			} else if (this.curLevel < 0) {
				this.curLevel = -1;
			}
			if (this.curLevel >= 0) {
				this.setLevelString(this.io.getLevel(this.curLevel));
			}
		} else if (function.equals("levelBefore")) {
			if (this.curLevel >= 0) {
				this.curLevel--;
				if (this.curLevel < 0) {
					this.curLevel = this.io.getLevelSize() - 1;
				}
				this.setLevelString(this.io.getLevel(this.curLevel));
			}
		} else if (function.equals("levelAfter")) {
			if (this.curLevel >= 0) {
				this.curLevel++;
				if (this.curLevel >= this.io.getLevelSize()) {
					this.curLevel = 0;
				}
				this.setLevelString(this.io.getLevel(this.curLevel));
			}
		} else if (function.equals("levelPullBefore")) {
			if (this.io.getLevelSize() > 1) {
				this.io.pullLevel(this.curLevel, -1);
				this.curLevel--;
				if (this.curLevel < 0) {
					this.curLevel = this.io.getLevelSize() - 1;
				}
			}
		} else if (function.equals("levelPullAfter")) {
			if (this.io.getLevelSize() > 1) {
				this.io.pullLevel(this.curLevel, +1);
				this.curLevel++;
				if (this.curLevel >= this.io.getLevelSize()) {
					this.curLevel = 0;
				}
			}
		} else if (function.equals("saveLevel")) {
			if (this.curLevel >= 0) {
				this.levelSave();
			}
		} else if (function.equals("loadLevel")) {
			this.levelLoad();
		} else if (function.equals("new")) {
			int width = 12;
			try {
				width = Integer.parseInt(this.levelField.getText());
			} catch (NumberFormatException ex) {
				width = 12;
				this.levelField.setText("12");
			}
			if (width > 15) {
				width = 15;
				this.levelField.setText("15");
			} else if (width < 5) {
				width = 5;
				this.levelField.setText("5");
			}
			this.makeNewLevel(width);
			this.textArea.setText(this.level.getLevelString());
		} else if (function.equals("wall")) {
			this.curImage = ApoStarzConstants.BLOCK;
			this.makeTransImage();
		} else if (function.equals("block")) {
			this.curImage = ApoStarzConstants.BLOCK_FALLING;
			this.makeTransImage();
		} else if (function.equals("goal")) {
			this.curImage = ApoStarzConstants.GOAL;
			this.makeTransImage();
		} else if (function.equals("star")) {
			this.curImage = ApoStarzConstants.STAR;
			this.makeTransImage();
		} else if (function.equals("fire")) {
			this.curImage = ApoStarzConstants.FIRE;
			this.makeTransImage();
		} else if (function.equals("solver")) {
			if (this.levelCheck(this.level.getLevelString())) {
				super.getButtons()[17].setBVisible(false);
				super.getButtons()[1].setBVisible(false);
				this.solver.getSolutionForLevel(this.level.getLevelString());
				this.curLevelString = this.level.getLevelString();
				if (this.io.addLevel(this.level.getLevelString())) {
					if (this.curLevel == -1) {
						this.curLevel = 0;
					} else {
						this.curLevel = this.io.getLevelSize() - 1;
					}
				}
			}
		}
		this.curMouseFunction = null;
	}

	private void makeTransImage() {
		this.iTransparent = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)this.iTransparent.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f) );

		if (this.curImage == ApoStarzConstants.BLOCK) {
			g.drawImage(this.level.getIWall(), 0, 0, null);
		} else if (this.curImage == ApoStarzConstants.BLOCK_FALLING) {
			g.drawImage(this.level.getIBlock(), 0, 0, null);
		} else if (this.curImage == ApoStarzConstants.FIRE) {
			g.drawImage(this.level.getIFire(), 0, 0, null);
		} else if (this.curImage == ApoStarzConstants.GOAL) {
			g.drawImage(this.level.getIGoal(), 0, 0, null);
		} else if (this.curImage == ApoStarzConstants.STAR) {
			g.drawImage(this.level.getIStar(), 0, 0, null);
		}
		
		g.dispose();
	}
	
	private void makeNewLevel(int width) {
		this.level.setFallingEntities(null);
		this.level.makeNewLevel(width);
		this.level.setCurDirection(this.level.getCurDirection());
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)	{
		if ((!super.isBWin()) && (!super.isBLoose()))	{
			int wheel = e.getWheelRotation();
			this.curImage += wheel;;
			if (this.curImage < ApoStarzConstants.BLOCK) {
				this.curImage = ApoStarzConstants.FIRE;
			} else if (this.curImage > ApoStarzConstants.FIRE) {
				this.curImage = ApoStarzConstants.BLOCK;
			}
			this.makeTransImage();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		int[] cor = this.getCoordinatesForMouse(e);
		if ((cor[0] > 0) && (cor[0] < this.level.getWidth() - 1) && 
			(cor[1] > 0) && (cor[1] < this.level.getHeight() - 1)) {
			this.setX = cor[0] * ApoStarzConstants.TILE_SIZE + cor[2];
			this.setY = cor[1] * ApoStarzConstants.TILE_SIZE + cor[3];
		} else {
			this.setX = -1;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		int[] cor = this.getCoordinatesForMouse(e);
		if ((cor[0] > 0) && (cor[0] < this.level.getWidth() - 1) && 
			(cor[1] > 0) && (cor[1] < this.level.getHeight() - 1)) {
			this.pressedX = cor[0];
			this.pressedY = cor[1];
			if (e.getButton() == MouseEvent.BUTTON1) {
				this.bLeftMouse = true;
			} else {
				this.bLeftMouse = false;
			}
		} else {
			this.pressedX = -1;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		int[] cor = this.getCoordinatesForMouse(e);
		if ((cor[0] > 0) && (cor[0] < this.level.getWidth() - 1) && 
			(cor[1] > 0) && (cor[1] < this.level.getHeight() - 1)) {
			int x = cor[0];
			int y = cor[1];
			if ((x == this.pressedX) && (y == this.pressedY)) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					this.level.addEntity(x, y, this.curImage, true);
				} else {
					this.level.addEntity(x, y, ApoStarzConstants.EMPTY, true);
				}
				this.textArea.setText(this.level.getLevelString());
			}
		} else {
			this.pressedX = -1;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		super.mouseReleased(e);
		int[] cor = this.getCoordinatesForMouse(e);
		if ((cor[0] > 0) && (cor[0] < this.level.getWidth() - 1) && 
			(cor[1] > 0) && (cor[1] < this.level.getHeight() - 1)) {
			int x = cor[0];
			int y = cor[1];
			this.pressedX = x;
			this.pressedY = y;
			this.setX = cor[0] * ApoStarzConstants.TILE_SIZE + cor[2];
			this.setY = cor[1] * ApoStarzConstants.TILE_SIZE + cor[3];
			if (this.bLeftMouse) {
				this.level.addEntity(x, y, this.curImage, true);
			} else {
				this.level.addEntity(x, y, ApoStarzConstants.EMPTY, true);
			}
			this.textArea.setText(this.level.getLevelString());
		} else {
			this.pressedX = -1;
		}
	}
	
	private int[] getCoordinatesForMouse(MouseEvent e) {
		int[] returnInt = new int[4];
		int curMouseX = e.getX();
		int curMouseY = e.getY();
		if (curMouseX <= ApoStarzConstants.GAME_GAME_WIDTH) {
			int curX = (ApoStarzConstants.GAME_GAME_WIDTH - ApoStarzConstants.TILE_SIZE * this.level.getWidth()) / 2;
			int curY = (ApoStarzConstants.GAME_HEIGHT - ApoStarzConstants.TILE_SIZE * this.level.getHeight()) / 2;
			curMouseX -= curX;
			curMouseY -= curY;
			returnInt[0] = curMouseX / ApoStarzConstants.TILE_SIZE;
			returnInt[1] = curMouseY / ApoStarzConstants.TILE_SIZE;
			returnInt[2] = curX;
			returnInt[3] = curY;
		} else {
			returnInt[0] = -1;
			returnInt[1] = -1;
		}
		return returnInt;
	}
	
	@Override
	public void think(int delta) {
		if (this.curMouseFunction != null) {
			this.thinkMouseFunction();
		} else if ((this.solver != null) && (!this.solver.isBRun()) && (!super.getButtons()[17].isBVisible())) {
			this.curSolution = this.solver.getCurSolution();
			super.getButtons()[17].setBVisible(true);
			super.getButtons()[1].setBVisible(true);
			if (this.curSolution.indexOf("a") != -1) {
				this.curSolution = "no solution in max "+(ApoStarzSolver.MAX_DEPTH + 1)+" steps for this level";
			}
		} else if (this.solver.isBRun()) {
			this.curSolution = "aaaaaaaaaaaaaaaaaaaaaa";
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}

		this.level.render(g);
		this.renderButtons(g);
		
		this.renderLevelCount(g);
		this.renderLevelSolution(g);
		
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
				
		if (this.setX >= 0) {
			g.drawImage(this.iTransparent, this.setX, this.setY, null);
		}
	}
	
	/**
	 * malt die Buttons
	 * @param g : das GraphicsObject
	 */
	public void renderButtons(Graphics2D g) {
		if (super.getButtons() != null) {
			for (int i = 0; i < super.getButtons().length; i++) {
				super.getButtons()[i].render(g);
			}
		}
	}
	
	private void renderLevelCount(Graphics2D g) {
		if (this.curLevel >= 0) {
			String s = (this.curLevel+1) + "/" + this.io.getLevelSize();
			g.drawString(s, ApoStarzConstants.GAME_WIDTH - 30, ApoStarzConstants.GAME_HEIGHT - 140);
		}
	}
	
	private void renderLevelSolution(Graphics2D g) {
		if (this.curLevelString != null) {
			g.setColor(Color.white);
			g.setFont(ApoStarzConstants.FONT_NORMAL);
			String s = "level = " + this.curLevelString;
			g.drawString(s, 5, ApoStarzConstants.GAME_HEIGHT - 20);
			if ((this.curSolution != null) && (this.curSolution.indexOf("a") == -1)) {
				s = "solution = " + this.curSolution;
				g.drawString(s, 5, ApoStarzConstants.GAME_HEIGHT - 5);
			} else if ((this.curSolution != null) && (this.curSolution.indexOf("a") > 2)) {
				g.drawString(this.curSolution, 5, ApoStarzConstants.GAME_HEIGHT - 5);
			}
		}
	}

}
