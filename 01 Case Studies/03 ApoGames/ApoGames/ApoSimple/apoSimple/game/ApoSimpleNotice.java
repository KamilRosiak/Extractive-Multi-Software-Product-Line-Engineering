package apoSimple.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleNotice extends ApoSimpleModel {

	public static final String BACK = "back";
	public static final String ERASEALL = "eraseall";
	public static final String ERASER = "eraser";
	public static final String PENCIL = "pencil";
	public static final String RED = "red";
	public static final String GREEN = "green";

	private BufferedImage iBackground;
	
	private ApoSimpleGame game;
	
	private Color color;
	
	private Point oldPosition;
	
	private BufferedImage iDraw;
	
	private boolean bErase;
	
	public ApoSimpleNotice(ApoSimpleGame game) {
		super(game.getGame());
		
		this.game = game;
		
		this.init();
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB_PRE);
			
			Graphics2D g = this.iBackground.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(new Color(255, 255, 255, 128));
			int width = 470;
			g.fillRect(0, 0, width, ApoSimpleConstants.GAME_HEIGHT);
			g.setColor(Color.WHITE);
			g.fillRect(width, 0, ApoSimpleConstants.GAME_WIDTH - width, ApoSimpleConstants.GAME_HEIGHT);
			
			g.dispose();
		}
		if (this.iDraw == null) {
			this.createDraw();
		}
		if (this.color == null) {
			this.color = Color.BLUE;
		}
		if (this.oldPosition == null) {
			this.oldPosition = new Point(-1, -1);
		}
	}

	private void createDraw() {
		this.iDraw = new BufferedImage(470, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB_PRE);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.game.changeNoticeVisible(false);
		} else if (button == KeyEvent.VK_S) {
			this.color = Color.GREEN;
			this.bErase = false;
		} else if (button == KeyEvent.VK_D) {
			this.color = Color.RED;
			this.bErase = false;
		} else if (button == KeyEvent.VK_Q) {
			this.createDraw();
		} else if (button == KeyEvent.VK_E) {
			this.color = Color.BLUE;
			this.bErase = false;
		} else if (button == KeyEvent.VK_W) {
			this.bErase = true;
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleNotice.BACK)) {
			this.game.changeNoticeVisible(false);
			this.oldPosition = new Point(-1, -1);
		} else if (function.equals(ApoSimpleNotice.ERASEALL)) {
			this.createDraw();
			this.oldPosition = new Point(-1, -1);
		} else if (function.equals(ApoSimpleNotice.PENCIL)) {
			this.color = Color.BLUE;
			this.bErase = false;
			this.oldPosition = new Point(-1, -1);
		} else if (function.equals(ApoSimpleNotice.ERASER)) {
			this.bErase = true;
			this.oldPosition = new Point(-1, -1);
		} else if (function.equals(ApoSimpleNotice.GREEN)) {
			this.color = Color.GREEN;
			this.bErase = false;
			this.oldPosition = new Point(-1, -1);
		} else if (function.equals(ApoSimpleNotice.RED)) {
			this.color = Color.RED;
			this.bErase = false;
			this.oldPosition = new Point(-1, -1);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		this.draw(x, y);
		this.oldPosition = new Point(-1, -1);
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
//		this.oldPosition = new Point(x, y);
		return super.mousePressed(x, y, bRight);
	}
	
	public boolean mouseDragged(int x, int y) {
		if (this.oldPosition.x >= 0) {
			this.draw(x, y);
		} else {
			this.oldPosition = new Point(x, y);
		}
		return false;
//		return super.mouseDragged(x, y);
	}
	
	private void draw(int x, int y) {
		if ((this.oldPosition.x > 0) && (this.oldPosition.y > 0) && (x >= 0) && (x < 470) && (y >= 0) && (y < ApoSimpleConstants.GAME_HEIGHT)) {
			Graphics2D g = this.iDraw.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (this.bErase) {
				g.setComposite(AlphaComposite.Src);
				g.setColor(new Color(0, 0, 50, 0));
				g.setStroke(new BasicStroke(10));
				g.drawLine(x, y, this.oldPosition.x, this.oldPosition.y);
			} else {
				g.setColor(this.color);
				g.setStroke(new BasicStroke(5));
				g.drawLine(x, y, this.oldPosition.x, this.oldPosition.y);
			}
			
			g.dispose();
			this.oldPosition = new Point(x, y);
		}
	}

	@Override
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
		String s = "Memo";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, 470 + 85 - w/2, 40);
		
		g.setColor(this.color);
		g.fillRect(470 + 30, 50, 20, 20);
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		s = "draw";
		if (this.bErase) {
			s = "erase";	
		}
		g.drawString(s, 470 + 60, 70);
		
		if (this.iDraw != null) {
			g.drawImage(this.iDraw, 0, 0, null);
		}
	}

}
