package apoSimpleSudoku.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;
import org.apogames.entity.ApoEntity;

import apoSimpleSudoku.ApoSimpleSudokuImages;
import apoSimpleSudoku.level.ApoSimpleSudokuLevel;

public class ApoSimpleSudokuMenu extends ApoEntity {

	private final int numbers;
	
	private ApoButton[] numberEntites;
	
	private BufferedImage iBackground;
	
	private final int levelX, levelY;
	
	private final boolean bChange;
	
	private final float changeHeight;
	
	public ApoSimpleSudokuMenu(float x, float y, float width, float height, final int numbers, final int levelX, final int levelY, final boolean bChange, final float changeHeight) {
		super(null, x, y, width, height);
		
		this.numbers = numbers;
		this.levelX = levelX;
		this.levelY = levelY;
		this.bChange = bChange;
		this.changeHeight = changeHeight;
		
		this.myInit();
	}
	
	public void myInit() {
		int max = this.numbers / 2;
		int width = (int)(this.getWidth() / (max) - 5);
		if (this.numbers % 2 != 0) {
			max += 1;
		}
		if ((width + 5) * 3 > this.getHeight() - this.changeHeight) {
			width = (int)((this.getHeight() - this.changeHeight) / 3 - 1 * 8);
		}
		this.numberEntites = new ApoButton[this.numbers + 1];
		int count = 1;
		final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		final ApoSimpleSudokuImages images = new ApoSimpleSudokuImages();
		final int startX = (int)(this.getWidth()/2 - (max * width/2) + 0 * width + (0*5) + this.getX());
		for (int y = 0; y < 2; y++) {	
			for (int x = 0; x < max; x++) {
				int drawX = (int)(startX + x * width + (x*5));
				int drawY = (y + 1) * 5 + y * width + (int)(this.getY());
				if (!this.bChange) {
					drawY += (int)this.changeHeight;
				}
				this.numberEntites[count - 1] = new ApoButton(images.getButtonImageLevelChooser(width * 3, width, String.valueOf(count), ApoSimpleSudokuLevel.c, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, font, 250), drawX, drawY, width, width, "");
				this.numberEntites[count - 1].setBOpaque(true);
				count += 1;
				if (count > this.numbers) {
					break;
				}
			}
			if (count > this.numbers) {
				break;
			}
		}
		int drawX = (int)(this.getWidth()/2 - width/2) + (int)(this.getX());
		int drawY = (int)(this.getHeight() - this.changeHeight - 5 - width) + (int)(this.getY());
		if (!this.bChange) {
			drawY += (int)this.changeHeight;
		}
		this.numberEntites[count - 1] = new ApoButton(images.getButtonImageLevelChooser(width * 3, width, "x", ApoSimpleSudokuLevel.c, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, font, 250), drawX, drawY, width, width, "");
		this.numberEntites[count - 1].setBOpaque(true);
		
		if (this.iBackground == null) {
			int myWidth = (max) * width + ((max)*5) + 5;
			this.iBackground = new BufferedImage((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iBackground.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (this.bChange) {
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(startX - 5 - this.getX()), (int)(0), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight - 1), 13, 13);
				g.setColor(ApoSimpleSudokuLevel.c);
				g.fillRoundRect((int)(startX - 5 - this.getX()), (int)(0), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight - 1), 13, 13);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(startX - 5 - this.getX()), (int)(0), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight) - 1, 13, 13);
			} else {
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(startX - 5 - this.getX()), (int)(this.changeHeight), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight - 1), 13, 13);
				g.setColor(ApoSimpleSudokuLevel.c);
				g.fillRoundRect((int)(startX - 5 - this.getX()), (int)(this.changeHeight), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight - 1), 13, 13);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(startX - 5 - this.getX()), (int)(this.changeHeight), myWidth - 1, this.iBackground.getHeight() - (int)(this.changeHeight) - 1, 13, 13);
			}
			g.dispose();
		}
	}
	
	public int getNumbers() {
		return this.numbers;
	}

	public int getLevelX() {
		return this.levelX;
	}

	public int getLevelY() {
		return this.levelY;
	}

	public int mouseReleased(int mouseX, int mouseY, boolean right) {
		if (this.numberEntites != null) {
			for (int x = 0; x < this.numberEntites.length; x++) {
				if (this.numberEntites[x].isBVisible()) {
					if (this.numberEntites[x].getReleased(mouseX, mouseY)) {
						if (x < this.numbers) {
							return (x + 1);
						} else {
							return 0;
						}
					}
				}
			}
		}
		return -1;
	}
	
	public boolean mousePressed(int mouseX, int mouseY, boolean bRight) {
		if (this.numberEntites != null) {
			for (int x = 0; x < this.numberEntites.length; x++) {
				if (this.numberEntites[x].isBVisible()) {
					if (this.numberEntites[x].getPressed(mouseX, mouseY)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int mouseMoved(int mouseX, int mouseY) {
		boolean bOver = false;
		if (!this.intersects(mouseX, mouseY)) {
			return 3;
		}
		if (this.numberEntites != null) {
			for (int x = 0; x < this.numberEntites.length; x++) {
				if (this.numberEntites[x].getMove(mouseX, mouseY)) {
					bOver = true;
					break;
				} else if (this.numberEntites[x].isBOver()) {
					bOver = true;
				}
			}
		}
		if (bOver) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
		}
		if (this.numberEntites != null) {
			for (int i = 0; i < this.numberEntites.length; i++) {
				this.numberEntites[i].render(g, changeX, changeY);
			}
		}
	}

}
