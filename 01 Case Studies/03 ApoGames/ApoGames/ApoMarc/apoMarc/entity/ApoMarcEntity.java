package apoMarc.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

import apoMarc.ApoMarcConstants;
import apoMarc.game.ApoMarcGame;

public abstract class ApoMarcEntity extends ApoEntity {

	private final int TIME = 100;
	private final int MAX_WIDTH = 18;
	
	private Color myColor;
	private Color myOrgColor;
	
	private ArrayList<ApoMarcEffects> effects;
	
	private boolean bHitEffects;
	
	private int curTime;
	
	private int add;
	
	private int curWidth;
	
	private boolean bRepeat;
	
	private int minWidth;
	private int maxWidth;
	
	private int maxTime;
	
	public ApoMarcEntity(float x, float y, float width, float height, Color myColor, boolean bHitEffects) {
		super(null, x, y, width, height);
	
		this.bHitEffects = bHitEffects;
		this.myColor = myColor;
		this.myOrgColor = myColor;
		
		this.minWidth = 8;
		this.maxWidth = this.MAX_WIDTH;
		this.maxTime = this.TIME;
	}
	
	public void init() {
		super.init();
		this.myColor = this.myOrgColor;
		this.curTime = 0;
		this.add = 1;
		this.curWidth = this.minWidth;
		this.bRepeat = true;
		
		this.effects = new ArrayList<ApoMarcEffects>();
	}
	
	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public Color getMyOrgColor() {
		return this.myOrgColor;
	}

	public void makeEffects(int x, int y) {
		if (ApoMarcConstants.EFFECTS) {
			if (this.effects.size() >= 26) {
				return;
			}
			for (int i = 0; i < 25; i++) {
				this.effects.add(new ApoMarcEffects(x, y, this.getMyColor()));
			}
		}
	}
	
	public int getMinWidth() {
		return this.minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMaxWidth() {
		return this.maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public boolean isBHitEffects() {
		return this.bHitEffects;
	}

	public Color getMyColor() {
		return this.myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}
	
	public int getCurWidth() {
		return this.curWidth;
	}

	public boolean isBRepeat() {
		return this.bRepeat;
	}

	public void setBRepeat(boolean repeat) {
		this.bRepeat = repeat;
		if (this.bRepeat) {
			this.add = 1;
			this.curWidth = this.minWidth;
		}
		if (!ApoMarcConstants.LIGHTS) {
			this.add = 1;
			this.curWidth = this.minWidth;
			this.bRepeat = false;
		}
	}

	public void think(long delta, ApoMarcGame game) {
		super.think((int)delta);
		if (this.effects.size() > 0) {
			for (int i = this.effects.size() - 1; i >= 0; i--) {
				try {
					this.effects.get(i).think((int)delta);
					if (!this.effects.get(i).isBVisible()) {
						this.effects.remove(i);
					}
				} catch (Exception ex) {	
				}
			}
		}
		if (this.bRepeat) {
			this.curTime += delta;
			if (this.curTime >= this.maxTime) {
				this.curTime -= this.maxTime;
				this.curWidth += this.add;
				if (this.curWidth < this.minWidth) {
					this.littleMinSize();
				} else if (this.curWidth > this.maxWidth) {
					this.add = -1;
				}
			}
		}
		this.thinkEntity((int)delta, game);
	}
	
	public void littleMinSize() {
		this.add = 1;
	}

	public abstract void thinkEntity(int delta, ApoMarcGame game);
	
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			if (this.effects.size() > 0) {
				for (int i = this.effects.size() - 1; i >= 0; i--) {
					this.effects.get(i).render(g, changeX, changeY);
				}
			}
		} catch (Exception ex) {	
		}
		this.renderEntity(g);
	}
	
	public abstract void renderEntity(Graphics2D g);

}
