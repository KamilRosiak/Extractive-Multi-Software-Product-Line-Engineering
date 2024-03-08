package net.apogames.apoclock.editor;

import net.apogames.apoclock.ApoClockConstants;
import net.apogames.apoclock.entity.ApoClockEntityClock;
import net.apogames.apoclock.game.ApoClockPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockEditorClockStats {

	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private final ApoClockEntityClock clock;
	
	public ApoClockEditorClockStats(final ApoClockEntityClock clock) {
		this.clock = clock;
		this.clock.setSelected(true);
		
		this.width = 350;
		this.height = 250;
		
		this.setCurrentPosition();
	}
	
	public final ApoClockEntityClock getClock() {
		return this.clock;
	}

	public boolean contains(final float x, final float y) {
		if ((this.x < x) && (this.x + this.width > x) &&
			(this.y < y) && (this.y + this.height > y)) {
			return true;
		}
		return false;
	}
	
	private void setCurrentPosition() {
		this.x = (int)(this.clock.getX() - this.width/2);
		if (this.x < 0) {
			this.x = 0;
		} else if (this.x + this.width >= ApoClockConstants.GAME_WIDTH) {
			this.x = ApoClockConstants.GAME_WIDTH - this.width;
		}
		if (this.clock.getY() > 308) {
			this.y = (int)(this.clock.getY() - this.clock.getRadius() - this.height);			
		} else {
			this.y = (int)(this.clock.getY() + this.clock.getRadius());
		}
	}
	
	public boolean touchedPressed(int x, int y, int finger) {
		int newX = (int)(x - this.x);
		int newY = (int)(y - this.y);
		
		if ((newX > 5) && (newX < 45) && (newY > 5) && (newY < 45)) {
			int radius = (int)(this.clock.getRadius()) - 10;
			if (radius < 12) {
				radius = 12;
			}
			this.clock.setRadius(radius);
			return true;
		} else if ((newX > 50) && (newX < 90) && (newY > 5) && (newY < 45)) {
			int radius = (int)(this.clock.getRadius()) - 1;
			if (radius < 12) {
				radius = 12;
			}
			this.clock.setRadius(radius);
			return true;
		} else if ((newX > this.width - 45) && (newX < this.width - 5) && (newY > 5) && (newY < 45)) {
			int radius = (int)(this.clock.getRadius()) + 10;
			if (radius > 60) {
				radius = 60;
			}
			this.clock.setRadius(radius);
			return true;
		}  else if ((newX > this.width - 90) && (newX < this.width - 50) && (newY > 5) && (newY < 45)) {
			int radius = (int)(this.clock.getRadius()) + 1;
			if (radius > 60) {
				radius = 60;
			}
			this.clock.setRadius(radius);
			return true;
		}
		
		if ((newX > 5) && (newX < 45) && (newY > 55) && (newY < 95)) {
			int angle = (int)(this.clock.getAngle()) - 10;
			if (angle < 0) {
				angle += 360;
			}
			this.clock.setAngle(angle);
			return true;
		} else if ((newX > 50) && (newX < 90) && (newY > 55) && (newY < 95)) {
			int angle = (int)(this.clock.getAngle()) - 1;
			if (angle < 0) {
				angle += 360;
			}
			this.clock.setAngle(angle);
			return true;
		} else if ((newX > this.width - 45) && (newX < this.width - 5) && (newY > 55) && (newY < 95)) {
			int angle = (int)(this.clock.getAngle()) + 10;
			if (angle >= 360) {
				angle -= 360;
			}
			this.clock.setAngle(angle);
			return true;
		} else if ((newX > this.width - 90) && (newX < this.width - 50) && (newY > 55) && (newY < 95)) {
			int angle = (int)(this.clock.getAngle()) + 1;
			if (angle >= 360) {
				angle -= 360;
			}
			this.clock.setAngle(angle);
			return true;
		}
		
		if ((newX > this.width - 90) && (newX < this.width - 50) && (newY > 105) && (newY < 145)) {
			int vel = (int)(this.clock.getRotateVelocity()) - 1;
			if (vel < 3) {
				vel = 3;
			}
			this.clock.setRotateVelocity(vel);
			return true;
		} else if ((newX > 50) && (newX < 90) && (newY > 105) && (newY < 145)) {
			int vel = (int)(this.clock.getRotateVelocity()) + 1;
			if (vel > 18) {
				vel = 18;
			}
			this.clock.setRotateVelocity(vel);
			return true;
		}
		
		if ((newX > 50) && (newX < 90) && (newY > 155) && (newY < 195)) {
			boolean bClockwise = !this.clock.isRotateClockwise();
			this.clock.setRotateClockwise(bClockwise);
			return true;
		} else if ((newX > this.width - 90) && (newX < this.width - 50) && (newY > 155) && (newY < 195)) {
			boolean bClockwise = !this.clock.isRotateClockwise();
			this.clock.setRotateClockwise(bClockwise);
			return true;
		}
		
		if ((newX > 5) && (newX < 45) && (newY > 205) && (newY < 245)) {
			int nextX = (int)(this.clock.getX() - 1);
			if (nextX - this.clock.getRadius() < 0) {
				nextX += 1;
			}
			this.clock.setX(nextX);
			return true;
		} else if ((newX > this.width/2 - 45) && (newX < this.width/2 - 5) && (newY > 205) && (newY < 245)) {
			int nextX = (int)(this.clock.getX() + 1);
			if (nextX - this.clock.getRadius() < 0) {
				nextX -= 1;
			}
			this.clock.setX(nextX);
			return true;
		}
		
		if ((newX > this.width/2 + 5) && (newX < this.width/2 + 45) && (newY > 205) && (newY < 245)) {
			int nextY = (int)(this.clock.getY() - 1);
			if (nextY - this.clock.getRadius() < 0) {
				nextY += 1;
			}
			this.clock.setY(nextY);
			return true;
		} else if ((newX > this.width/2 + this.width/2 - 45) && (newX < this.width/2 + this.width/2 - 5) && (newY > 205) && (newY < 245)) {
			int nextY = (int)(this.clock.getY() + 1);
			if (nextY - this.clock.getRadius() < 0) {
				nextY -= 1;
			}
			this.clock.setY(nextY);
			return true;
		}
		return false;
	}
	
	public void touchedReleased(int x, int y, int finger) {
	}

	public void touchedDragged(int x, int y, int finger) {
		
	}
	
	public void think(int delta) {
		this.setCurrentPosition();
	}
	
	public void render(BitsGLGraphics g) {
		g.setColor(1f, 1f, 1f, 0.75f);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(0f, 0f, 0f, 1f);
		g.drawRect(this.x, this.y, this.width, this.height);
		
		g.drawRoundRect(this.x + 5, this.y + 5, 40, 40, 5, 2);
		g.drawRoundRect(this.x + 50, this.y + 5, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 45, this.y + 5, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 90, this.y + 5, 40, 40, 5, 2);
		
		g.drawRoundRect(this.x + 5, this.y + 55, 40, 40, 5, 2);
		g.drawRoundRect(this.x + 50, this.y + 55, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 45, this.y + 55, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 90, this.y + 55, 40, 40, 5, 2);
		
		g.drawRoundRect(this.x + 50, this.y + 105, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 90, this.y + 105, 40, 40, 5, 2);
		
		g.drawRoundRect(this.x + 50, this.y + 155, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 90, this.y + 155, 40, 40, 5, 2);
		
		g.drawRoundRect(this.x + 5, this.y + 205, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width/2 - 45, this.y + 205, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width - 45, this.y + 205, 40, 40, 5, 2);
		g.drawRoundRect(this.x + width/2 + 5, this.y + 205, 40, 40, 5, 2);
		
		g.setFont(ApoClockPanel.game_font);
		String s = "-";
		for (int i = 0; i < 4; i++) {
			g.drawText(s, this.x + 65, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + i * 50);
		}

		g.drawText(s, this.x + this.width/2 + 20, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + 200);
		g.drawText(s, this.x + 20, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + 200);
		s = "+";
		for (int i = 0; i < 4; i++) {
			g.drawText(s, this.x + width - 75, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + i * 50);
		}
		g.drawText(s, this.x + this.width/2 - 30, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + 200);
		g.drawText(s, this.x + this.width - 30, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + 200);
		s = "-10";
		for (int i = 0; i < 2; i++) {
			g.drawText(s, this.x + 10, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + i * 50);
		}
		s = "+10";
		for (int i = 0; i < 2; i++) {
			g.drawText(s, this.x + width - 40, this.y + 25 - ApoClockPanel.game_font.mCharCellHeight/2 + i * 50);
		}
		
		int middle = (int)(this.x + this.width/2);
		s = "radius";
		g.drawText(s, middle - ApoClockPanel.game_font.getLength(s)/2, this.y + 0);
		
		s = "angle";
		g.drawText(s, middle - ApoClockPanel.game_font.getLength(s)/2, this.y + 50);
		
		s = "rotation speed";
		g.drawText(s, middle - ApoClockPanel.game_font.getLength(s)/2, this.y + 100);
		
		
		s = "rotate clockwise";
		g.drawText(s, middle - ApoClockPanel.game_font.getLength(s)/2, this.y + 150);
		
		s = "x";
		g.drawText(s, middle - this.width/4 - ApoClockPanel.game_font.getLength(s)/2, this.y + 195);
		
		s = "y";
		g.drawText(s, middle + this.width/4 - ApoClockPanel.game_font.getLength(s)/2, this.y + 195);
		
		g.setFont(ApoClockPanel.font);
		g.drawText(String.valueOf((int)this.clock.getY()), middle + this.width/4 - 20, this.y + 215);
		g.drawText(String.valueOf((int)this.clock.getX()), middle - this.width/4 - 20, this.y + 215);
		g.drawText(String.valueOf((boolean)this.clock.isRotateClockwise()), middle - 25, this.y + 170);
		int value = 19 - (int)this.clock.getRotateVelocity();
		g.drawText(String.valueOf(value), middle - 5, this.y + 120);
		g.drawText(String.valueOf((int)this.clock.getRadius()), middle - 5, this.y + 20);
		g.drawText(String.valueOf((int)this.clock.getAngle()), middle - 5, this.y + 70);
	}
	
}
