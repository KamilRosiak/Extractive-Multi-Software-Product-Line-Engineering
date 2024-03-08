package apoSimple.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleString;

public class ApoSimpleGameAchievements {

	public static final int POINTS = 150;
	public static final int ADD_TIME = 20;
	
	private boolean bHalfOfField;
	
	private boolean bRow;
	
	private boolean bColumn;
	
	private boolean bAllInOne;
	
	private BufferedImage iBackground, iBackgroundAchievement, iAchievement;
	
	private ApoSimpleGame game;
	
	private int transparency;
	
	private int curTime;
	
	public ApoSimpleGameAchievements(ApoSimpleGame game) {
		this.game = game;
		this.bHalfOfField = false;
		this.bRow = false;
		this.bColumn = false;
		this.bAllInOne = false;
	}
	
	public void setAll(boolean bAll, boolean bColumn, boolean bHalf, boolean bRow) {
		this.bHalfOfField = bHalf;
		this.bAllInOne = bAll;
		this.bColumn = bColumn;
		this.bRow = bRow;
	}
	
	public boolean isBHalfOfField() {
		return this.bHalfOfField;
	}

	public void setBHalfOfField(boolean halfOfField) {
		if ((halfOfField) && (!this.bHalfOfField)) {
			this.setBackgroundImage();
		}
		this.bHalfOfField = halfOfField;
	}

	public boolean isBRow() {
		return this.bRow;
	}

	public void setBRow(boolean row) {
		if ((row) && (!this.bRow)) {
			this.setBackgroundImage();
		}
		this.bRow = row;
	}

	public boolean isBColumn() {
		return this.bColumn;
	}

	public void setBColumn(boolean column) {
		if ((column) && (!this.bColumn)) {
			this.setBackgroundImage();
		}
		this.bColumn = column;
	}

	public boolean isBAllInOne() {
		return this.bAllInOne;
	}

	public void setBAllInOne(boolean allInOne) {
		if ((allInOne) && (!this.bAllInOne)) {
			this.setBackgroundImage();
		}
		this.bAllInOne = allInOne;
	}

	public void setBackgroundImage() {
		if (this.iAchievement == null) {
			this.iAchievement = this.game.getGame().getImages().getImage("images/achievement_circle.png", false);	
		}
		
		if (this.iBackgroundAchievement == null) {
			this.iBackgroundAchievement = this.game.getGame().getImages().getImage("images/achievement.png", false);	
		}
		
		
		
		int width = (this.game.getEntities()[0].length) * (ApoSimpleConstants.ENTITY_WIDTH + 5);
		int nextWidth = 200;
		this.iBackground = new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();

		this.game.setBAchievement(false);
		this.game.render(g);
		this.game.setBAchievement(true);
		g.drawImage(this.iBackgroundAchievement, 0, 0, null);
		this.game.getStrings().add(new ApoSimpleString(width / 2 - nextWidth/2, width / 2 - 30, 60, "achievement bonus "+String.valueOf(ApoSimpleGameAchievements.POINTS), true));
		this.game.setPoints(this.game.getPoints() + ApoSimpleGameAchievements.POINTS);

		this.transparency = 0;
		this.curTime = 0;
		
		this.game.getGame().playSound(ApoSimplePanel.SOUND_DUCK, 100);
	}
	
	public void think(int delta) {
		this.curTime += delta;
		if (this.curTime >= ApoSimpleGameAchievements.ADD_TIME) {
			this.curTime -= ApoSimpleGameAchievements.ADD_TIME;
			this.transparency += 2;
			if (this.transparency > 255) {
				this.transparency = 255;
			}
		}
	}
	
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		int x = 0;
		int width = (this.game.getEntities()[0].length) * (ApoSimpleConstants.ENTITY_WIDTH + 5);
		int realWidth = width/2 - 70;
		Color background = new Color(255, 204, 153);
		g.setColor(background);
		Composite composite = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(this.transparency/255f)));
		if (this.bHalfOfField) {
//			g.setColor(background);
//			g.fillOval(10 + width/4 - realWidth/2 - 2, 10 + width/4 - realWidth/2 - 2, realWidth, realWidth);
//			g.setColor(border);
//			g.drawOval(10 + width/4 - realWidth/2 - 2, 10 + width/4 - realWidth/2 - 2, realWidth, realWidth);
			g.drawImage(this.iAchievement, x + 10 + width/4 - realWidth/2 - 2, 10 + width/4 - realWidth/2 - 2, null);
			
			g.setColor(Color.BLACK);
			String s = "Half";
			g.setFont(ApoSimpleGame.FONT_RESTART);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*1/4 - 35);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			
			s = "Clear more";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*1/4 - 15);
			
			s = "than half";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*1/4 + 5);
		}
		if (this.bAllInOne) {
//			g.setColor(background);
//			g.fillOval(10 + width/4 - realWidth/2 - 2, 10 + width * 3/4 - realWidth/2 - 2, realWidth, realWidth);
//			g.setColor(border);
//			g.drawOval(10 + width/4 - realWidth/2 - 2, 10 + width * 3/4 - realWidth/2 - 2, realWidth, realWidth);
			g.drawImage(this.iAchievement, x + 10 + width/4 - realWidth/2 - 2, 10 + width*3/4 - realWidth/2 - 2, null);
			
			g.setColor(Color.BLACK);
			String s = "AllInOne";
			g.setFont(ApoSimpleGame.FONT_RESTART);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*3/4 - 35);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			
			s = "Clear all colored";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*3/4 - 15);
			
			s = "in one move";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width/4 - w/2, 10 + width*3/4 + 5);
		}
		if (this.bRow) {
//			g.setColor(background);
//			g.fillOval(10 + width * 3/4 - realWidth/2 - 2, 10 + width * 1/4 - realWidth/2 - 2, realWidth, realWidth);
//			g.setColor(border);
//			g.drawOval(10 + width * 3/4 - realWidth/2 - 2, 10 + width * 1/4 - realWidth/2 - 2, realWidth, realWidth);
//			
			g.drawImage(this.iAchievement, x + 10 + width*3/4 - realWidth/2 - 2, 10 + width*1/4 - realWidth/2 - 2, null);
			
			g.setColor(Color.BLACK);
			String s = "Row";
			g.setFont(ApoSimpleGame.FONT_RESTART);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*1/4 - 35);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			s = "For clearing";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*1/4 - 15);
			
			s = "full row";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*1/4 + 5);
		}
		if (this.bColumn) {
//			g.setColor(background);
//			g.fillOval(10 + width * 3/4 - realWidth/2 - 2, 10 + width * 3/4 - realWidth/2 - 2, realWidth, realWidth);
//			g.setColor(border);
//			g.drawOval(10 + width * 3/4 - realWidth/2 - 2, 10 + width * 3/4 - realWidth/2 - 2, realWidth, realWidth);
//			
			g.drawImage(this.iAchievement, x + 10 + width*3/4 - realWidth/2 - 2, 10 + width*3/4 - realWidth/2 - 2, null);
			
			g.setColor(Color.BLACK);
			String s = "Column";
			g.setFont(ApoSimpleGame.FONT_RESTART);
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*3/4 - 35);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			s = "For clearing";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*3/4 - 15);
			
			s = "full column";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 10 + width*3/4 - w/2, 10 + width*3/4 + 5);
		}
		g.setComposite(composite);
	}
	
}
