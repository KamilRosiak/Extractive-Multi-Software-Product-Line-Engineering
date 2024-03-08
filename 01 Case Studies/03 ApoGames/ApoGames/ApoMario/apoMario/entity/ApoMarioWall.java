package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die eine Wand im Spiel repräsentiert
 * Sie kann zerstörbar oder unzerstörbar sein
 * und eine Animation oder keine enthalten
 * @author Dirk Aporius
 */
public class ApoMarioWall extends ApoMarioEntity {

	private final float MAX_CHANGE_Y = -2.5f * ApoMarioConstants.SIZE;
	private final float VEC_CHANGE_Y = -25 / 1000f * ApoMarioConstants.SIZE;
	
	/** boolean-Variable die angibt, ob die Wand nur im Hintergrund ist */
	private boolean bNoCollision;
	/** boolean-Variable die angibt, ob bei der Kollision nur die Wand oben betrachtet werden soll */
	private boolean bOnlyAboveWall;
	/** boolean-Variable die angibt, ob die Wand durch anspringen nach oben versetzt werden kann */
	private boolean bCanBeUp;
	/** boolean-Variable die angibt, ob die Wand zerstörbar sein soll oder nicht */
	private boolean bDestructable;
	/** int-Variable die angibt, wie lange gewartet werden soll bis die Animation wieder abläuft */
	private int timeToRepeat;
	/** int-Variable die angibt, wie lange noch gewartet werden muss bis die Animation wieder ablaufen soll */
	private int curTimeToRepeat;
	
	private int goodie;
	
	private float curChangeY;
	
	private float changeY;
	
	private boolean bUp;
	
	private boolean bGround;
	
	private boolean bCanon;
	
	private boolean bTube;
	
	private Point tubePoint;
	
	public ApoMarioWall(BufferedImage animation, float x, float y, int id, boolean bGround) {
		this(animation, x, y, false, bGround, id);
	}
	
	public ApoMarioWall(BufferedImage animation, float x, float y, boolean bDestructable, boolean bGround, int id) {
		this(animation, x, y, animation.getWidth() / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, 1, 1000000, bDestructable, bGround, id);
	}
	
	public ApoMarioWall(BufferedImage animation, float x, float y, float width, float height, boolean bDestructable, int id) {
		this(animation, x, y, width, height, 1, 1000000, bDestructable, false, id);
	}
	
	public ApoMarioWall(BufferedImage animation, float x, float y, float width, float height, int tiles, int time, boolean bDestructable, boolean bGround, int id) {
		super(animation, x, y, width, height, tiles, time, 1, id);
		this.bDestructable = bDestructable;
		this.bGround = bGround;
		this.bTube = false;
		this.bCanon = false;
		this.setGoodie(-1);
	}
	
	public ApoMarioWall(BufferedImage animation, float x, float y, float width, float height, int tiles, int time, int id) {
		this(animation, x, y, width, height, tiles, time, false, false, id);
	}
	
	public void init() {
		super.init();
		
		this.curTimeToRepeat = 0;
		this.curChangeY = 0;
		this.bUp = false;
		
		this.changeY = 0;
	}

	public Point getTubePoint() {
		return this.tubePoint;
	}

	public void setTubePoint(Point tubePoint) {
		this.tubePoint = tubePoint;
	}

	public boolean isBTube() {
		return this.bTube;
	}

	public void setBTube(boolean tube) {
		this.bTube = tube;
	}

	public boolean isBCanon() {
		return this.bCanon;
	}

	public void setBCanon(boolean canon) {
		this.bCanon = canon;
	}

	public boolean isBGround() {
		return this.bGround;
	}

	public void addGoodie(ApoMarioLevel level, int player) {
		if (this.getGoodie() == ApoMarioConstants.GOODIE_COIN) {
			level.getPlayers().get(player).setPoints(level.getPlayers().get(player).getPoints() + ApoMarioConstants.POINTS_COIN);
			level.getGoodies().add(new ApoMarioCoinParticle(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), ApoMarioImageContainer.PARTICLE.getSubimage(0 * ApoMarioConstants.TILE_SIZE/2, 2 * ApoMarioConstants.TILE_SIZE/2, 3 * ApoMarioConstants.TILE_SIZE/2, ApoMarioConstants.TILE_SIZE/2), (int)(this.getX()), (int)(this.getY()), ApoMarioConstants.COIN_ANIMATION_FRAMES, ApoMarioConstants.COIN_ANIMATION_TIME));			
			level.getPlayers().get(player).setCoins(level.getPlayers().get(player).getCoins() + 1);
		} else if (this.getGoodie() == ApoMarioConstants.GOODIE_GOODIE) {
			level.getPlayers().get(player).setPoints(level.getPlayers().get(player).getPoints() + ApoMarioConstants.POINTS_GOODIE);
			if (level.getPlayers().get(player).getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
				level.getGoodies().add(new ApoMarioGoodieMushroom(ApoMarioImageContainer.ITEMS.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), this.getX(), this.getY(), false, ++ApoMarioLevel.ID));
			} else {
				level.getGoodies().add(new ApoMarioGoodieFireflower(ApoMarioImageContainer.ITEMS.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), this.getX(), this.getY(), ++ApoMarioLevel.ID));					
			}
		}
	}
	
	public boolean isBCanBeUp() {
		return this.bCanBeUp;
	}

	public void setBCanBeUp(boolean canBeUp) {
		this.bCanBeUp = canBeUp;
	}

	public float getCurChangeY() {
		return this.curChangeY;
	}

	public void setCurChangeY(float curChangeY) {
		this.curChangeY = curChangeY;
	}

	public float getChangeY() {
		return this.changeY;
	}

	public void setChangeY(float changeY) {
		this.changeY = changeY;
	}

	public boolean isBUp() {
		return this.bUp;
	}

	public void setBUp(boolean up) {
		this.bUp = up;
	}

	/**
	 * gibt zurück, ob mit dieser Wand eine Kollision möglich ist
	 * @return TRUE, keine Kollision, sonst FALSE
	 */
	public boolean isBNoCollision() {
		return this.bNoCollision;
	}

	public void setBNoCollision(boolean bNoCollision) {
		this.bNoCollision = bNoCollision;
	}

	/**
	 * gibt zurück, ob die Wand nur oben kollidiert werden kann
	 * @return TRUE, Kollision nur oben, sonst FALSE
	 */
	public boolean isBOnlyAboveWall() {
		return this.bOnlyAboveWall;
	}

	public void setBOnlyAboveWall(boolean bOnlyAboveWall) {
		this.bOnlyAboveWall = bOnlyAboveWall;
	}

	protected void enemyCheck(ApoMarioLevel level, int player) {
		Rectangle2D.Float myRec = new Rectangle2D.Float(this.getX(), this.getY() - 5 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight());
		for (int i = 0; i < level.getEnemies().size(); i++) {
			if (!level.getEnemies().get(i).isBDie()) {
				if (myRec.intersects(level.getEnemies().get(i).getRec())) {
					level.getEnemies().get(i).die(level, player);
				}
			}
		}
	}
	
	/**
	 * gibt zurück, welches Goodie es enthält
	 * @return -1 falls kein Goodie, ansonsten Zahl >= 0
	 */
	public int getGoodie() {
		return this.goodie;
	}

	/**
	 * setzt den Wert, welches Goodie es enthält, auf den übergebenen
	 * @param goodie : neues Goodie ansonsten -1
	 */
	public void setGoodie(int goodie) {
		this.goodie = goodie;
	}
	
	/**
	 * gibt zurück, ob eine Wand zerstörbar ist
	 * @return TRUE, Wand zerstörbar, FALSE Wand unzerstörbar
	 */
	public boolean isBDestructable() {
		return this.bDestructable;
	}

	/**
	 * setzt die Variable, ob die Wand zerstört werden kann auf den übergebenen Wert
	 * @param bDestructable : TRUE, Wand zerstörbar, ansonsten FALSE
	 */
	public void setBDestructable(boolean bDestructable) {
		this.bDestructable = bDestructable;
	}

	/**
	 * gibt zurück, wie lange immer zwischen der ANimation gewartet werden soll
	 * @return gibt zurück, wie lange immer zwischen der ANimation gewartet werden soll
	 */
	public int getTimeToRepeat() {
		return this.timeToRepeat;
	}

	/**
	 * setzt die Variable, wie lange immer zwischen den Animationen gewartet werden soll auf den übergebenen Wert
	 * @param timeToRepeat : neuer Wert wie lange gewartet werden soll zwischen den Animationen
	 */
	public void setTimeToRepeat(int timeToRepeat) {
		this.timeToRepeat = timeToRepeat;
	}
	
	/**
	 * wird aufgerufen, wenn eine Wand getroffen wird
	 */
	public void damageWall(ApoMarioLevel level, int player) {
		if (level.getPlayers().get(player).getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
			if (this.isBCanBeUp()) {
				this.setChangeY(this.VEC_CHANGE_Y);
				this.curChangeY += this.changeY;
			}
		}
		this.addGoodie(level, player);
	}

	public void think(int delta, ApoMarioLevel level) {
		if (!super.isBVisible()) {
			return;
		}
		if (this.isBAnimation()) {
			super.setLevel(level);
			super.think(delta);
			if (!this.isBAnimation()) {
				this.curTimeToRepeat = this.timeToRepeat;
			}
		} else {
			this.curTimeToRepeat -= delta;
			if (this.curTimeToRepeat <= 0) {
				this.setBAnimation(true);
			}
		}
	}
	
	public void thinkBeUp(int delta) {
		if ((this.bCanBeUp) && (this.curChangeY != 0)) {
			this.curChangeY += this.changeY * delta;
			if (this.curChangeY < this.MAX_CHANGE_Y) {
				this.changeY = -this.changeY;
				this.curChangeY += this.changeY * delta;
			} else if (this.curChangeY >= 0) {
				this.curChangeY = 0;
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(super.getIBackground().getSubimage((int)(this.getFrame() * this.getWidth() * ApoMarioConstants.APP_SIZE), 0, (int)this.getWidth() * ApoMarioConstants.APP_SIZE, (int)this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.curChangeY) * ApoMarioConstants.APP_SIZE, null);
			}
			if (ApoMarioConstants.DEBUG) {
				g.setColor(Color.RED);
				g.drawRect((int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.curChangeY) * ApoMarioConstants.APP_SIZE, (int)(this.getWidth()) * ApoMarioConstants.APP_SIZE, (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE);
			}
		}
	}

	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
	}
	
}