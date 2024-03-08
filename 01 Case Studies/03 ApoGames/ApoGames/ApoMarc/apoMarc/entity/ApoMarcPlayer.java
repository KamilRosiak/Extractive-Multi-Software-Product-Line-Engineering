package apoMarc.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.apogames.help.ApoHelp;

import apoMarc.ApoMarcConstants;
import apoMarc.game.ApoMarcGame;

public class ApoMarcPlayer extends ApoMarcEntity {
	
	private int points;
	
	private boolean bSelect;
	
	private float speed;
	
	private float los;
	
	private ApoMarcPaddleAI ai;
	
	private Rectangle2D.Float collisionRec;
	
	private int difficulty;
	
	public ApoMarcPlayer(float x, float y, float width, Color myColor, boolean bHit, Rectangle2D.Float collisionRec) {
		super(x, y, width, width, myColor, bHit);
		
		this.collisionRec = collisionRec;
		
		this.difficulty = ApoMarcConstants.DIFFICULTY_MARC;
		
		this.init();
	}
	
	public void init() {
		super.init();
		
		super.setBRepeat(false);
		super.setMinWidth(3);
		super.setMaxWidth(7);
		super.setMaxTime(150);
		
		this.los = 0;
		this.speed = 0;

		this.points = 0;
		
		this.bSelect = false;
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		if (this.ai != null) {
			if (this.difficulty == ApoMarcConstants.DIFFICULTY_MARC) {
				this.setWidth(ApoMarcConstants.PLAYER_WIDTH_MARC);
			} else if (this.difficulty == ApoMarcConstants.DIFFICULTY_MANDY) {
				this.setWidth(ApoMarcConstants.PLAYER_WIDTH_MANDY);
			} else if (this.difficulty == ApoMarcConstants.DIFFICULTY_APO) {
				this.setWidth(ApoMarcConstants.PLAYER_WIDTH_APO);
			} else if (this.difficulty == ApoMarcConstants.DIFFICULTY_UNBEATABLE) {
				this.setWidth(ApoMarcConstants.PLAYER_WIDTH_UNBEATABLE);
			}
		}
	}

	public ApoMarcPaddleAI getAi() {
		return this.ai;
	}

	public void setAi(ApoMarcPaddleAI ai) {
		this.ai = ai;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getLos() {
		return this.los;
	}

	public void setLos(float los) {
		this.los = los;
	}

	public boolean isBSelect() {
		return this.bSelect;
	}

	public void setBSelect(boolean select) {
		this.bSelect = select;
	}

	public boolean setNewPosition(float newX, float newY, ApoMarcGame game) {
		float oldX = this.getX();
		float oldY = this.getY();
		
		if (newY < ApoMarcConstants.GAME_HEIGHT/2) {
			return false;
		}
		if (newY + this.getWidth() > ApoMarcConstants.GAME_HEIGHT - 10) {
			return false;
		}
		this.setX(newX);
		this.setY(newY);
		for (int i = 0; i < game.getWalls().length; i++) {
			if (game.getWalls()[i].intersects(this)) {
				this.setX(oldX);
				this.setY(oldY);
				return false;
			}
		}
		if (!this.collisionRec.contains(this.getX(), this.getY(), this.getWidth(), this.getWidth())) {
			this.setX(oldX);
			this.setY(oldY);
			return false;
		}
		float maxSpeed = (Math.abs(oldX - newX) + Math.abs(oldY - newY)) * 0.015f;
		if (this.checkPlayerIntersects(game, maxSpeed)) {
			this.makeEffects((int)(game.getPaddle().getXMiddle()), (int)(game.getPaddle().getY() + game.getPaddle().getWidth()/2));
			game.getPaddle().setBFirstTouch(false);
		}
		return true;
	}
	
	public void setNewSpeedAndPosition(int delta, ApoMarcGame game, ApoMarcPlayer player) {
		float oldX = this.getX();
		float oldY = this.getY();
		
		float radius = (float)player.getSpeed() * delta * 30;
		double alpha = player.getLos();
		if (alpha > 360) {
			alpha = 360 - alpha;
		} else if (alpha < 0) {
			alpha = 360 + alpha;
		}
		if (radius > 0.001f) {
			float newX = player.getX() + player.getWidth()/2 + radius * (float)Math.cos(Math.toRadians(alpha));
			float newY = player.getY() + player.getWidth()/2 + radius * (float)Math.sin(Math.toRadians(alpha));
			this.setX(newX - player.getWidth()/2);
			this.setY(newY - player.getWidth()/2);
			float next = 0.000002f * delta;
			this.setSpeed(player.getSpeed() - next);
			
			if (!this.collisionRec.contains(this.getX(), this.getY(), this.getWidth(), this.getWidth())) {
				this.setX(oldX);
				this.setY(oldY);
			}
		} else {
			this.setSpeed(0);
		}
	}
	
	public boolean checkPlayerIntersects(ApoMarcGame game, float maxSpeed) {
		for (int i = 0; i < game.getPlayers().length; i++) {
			if (game.getPlayers()[i].intersects(game.getPaddle().getX() + game.getPaddle().getWidth()/2, game.getPaddle().getY() + game.getPaddle().getWidth()/2, game.getPaddle().getWidth()/2)) {
				double angle = ApoHelp.getAngleBetween2Points(game.getPaddle().getX() + game.getPaddle().getWidth()/2, game.getPaddle().getY() + game.getPaddle().getWidth()/2, game.getPlayers()[i].getX()+ game.getPlayers()[i].getWidth()/2, game.getPlayers()[i].getY()+ game.getPlayers()[i].getWidth()/2);
				game.getPaddle().setLos((float)angle);
				if (maxSpeed == 0) {
					maxSpeed = game.getPaddle().getSpeed();
				}
				if (Math.abs(maxSpeed) > 0.022) {
					maxSpeed = 0.022f;
				}
				game.getPaddle().setSpeed(maxSpeed);
				game.getPaddle().setMyColor(game.getPlayers()[i].getMyColor());
				this.setPaddleBackPlayer(game.getPlayers()[i], game.getPaddle());
				game.getPlayers()[i].setBRepeat(true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * wird aufgerufen, wenn der Ball mit einem Spieler kollidiert und setzt den Ball zurück
	 * @param entityOne : Spieler der mit dem Ball kollidiert
	 * @param ball : der Ball
	 * @return immer TRUE
	 */
	public boolean setPaddleBackPlayer(ApoMarcPlayer player, ApoMarcPaddle paddle) {
		int count = 0;
		float los = paddle.getLos() + 180;
		while (los > 360) {
			los -= 360;
		}
		while ((player.intersects(paddle.getX() + paddle.getWidth()/2, paddle.getY() + paddle.getWidth()/2, paddle.getWidth()/2)) && (count < 20)) {
			float radiusOne = 0.5f;
			count++;
			paddle.setX(paddle.getX() - radiusOne * (float)Math.cos(Math.toRadians(los)) );
			paddle.setY(paddle.getY() - radiusOne * (float)Math.sin(Math.toRadians(los)) );
		}
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn der Ball mit einem Spieler kollidiert und setzt den Ball zurück
	 * @param entityOne : Spieler der mit dem Ball kollidiert
	 * @param ball : der Ball
	 * @return immer TRUE
	 */
	public boolean setPaddleBackWall(ApoMarcWall wall, ApoMarcPaddle paddle) {
		int count = 0;
		float los = paddle.getLos() + 180;
		while (los > 360) {
			los -= 360;
		}
		while ((paddle.intersects(wall)) && (count < 30)) {
			float radiusOne = 0.5f;
			count++;
			paddle.setX(paddle.getX() - radiusOne * (float)Math.cos(Math.toRadians(los)) );
			paddle.setY(paddle.getY() - radiusOne * (float)Math.sin(Math.toRadians(los)) );
		}
		return true;
	}
	
	/**
	 * überprüft, ob diese Entity sich mit den übergebenen Werten schneidet
	 * @param x : x-Wert des Mittelpunktes der mit überprüft werden soll
	 * @param y : y-Wert des Mittelpunktes der mit überprüft werden soll
	 * @param radius : Radius der zu überprüfenden Werte
	 * @return TRUE, falls sie sich schneiden, sonst FALSE
	 */
	public boolean intersects(float x, float y, float radius) {
		float newX = ((x - (this.getX() + this.getWidth()/2))*(x - (this.getX() + this.getWidth()/2)));
		float newY = ((y - (this.getY() + this.getWidth()/2))*(y - (this.getY() + this.getWidth()/2)));
		float newRadius = ((radius + this.getWidth()/2)*(radius + this.getWidth()/2));
		if ( newX + newY <= newRadius ) {
			return true;
		}
		return false;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void addOnePoint() {
		this.points += 1;
	}
	
	@Override
	public void thinkEntity(int delta, ApoMarcGame game) {
		if (this.ai != null) {
			float oldX = this.getX();
			float oldY = this.getY();
			this.ai.think(game, this, delta);
			float newX = this.getX();
			float newY = this.getY();
			float maxSpeed = (Math.abs(oldX - newX) + Math.abs(oldY - newY)) * 0.01f;
			if (this.checkPlayerIntersects(game, maxSpeed)) {
				this.makeEffects((int)(game.getPaddle().getXMiddle()), (int)(game.getPaddle().getY() + game.getPaddle().getWidth()/2));
			}
		}
	}
	
	public void littleMinSize() {
		super.littleMinSize();
		super.setBRepeat(false);
	}

	@Override
	public void renderEntity(Graphics2D g) {
		int startX = (int)(this.getX());
		int startY = (int)(this.getY());
		int width = (int)(this.getWidth());
		
		Color color = super.getMyColor();
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(3));
		int add = 254;
		try {
			add = 255 / this.getCurWidth();
		} catch (Exception ex) {
			add = 254;
		}
		for (int j = 0; j < this.getCurWidth(); j++) {
			int alpha = 255 - (j + 1) * add;
			if (alpha < 0) {
				alpha = 0;
			}
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
			g.drawOval(startX - (j+1) - 3, startY - (j+1) - 3, width + (j * 2) + 3, width + (j * 2) + 3);
			g.drawOval(startX + (j+1), startY + (j+1), width - (j * 2) - 7, width - (j * 2) - 7);
		}
		
		g.setStroke(stroke);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(5));
		g.drawOval(startX - 2, startY - 2, width - 1, width - 1);
		g.setStroke(stroke);
	}
}
