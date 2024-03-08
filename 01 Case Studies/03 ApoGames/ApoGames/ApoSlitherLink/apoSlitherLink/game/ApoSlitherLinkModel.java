package apoSlitherLink.game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class ApoSlitherLinkModel {

	private ApoSlitherLinkPanel game;
	
	private Rectangle2D.Double rec;
	
	private boolean bOver;
	
	public ApoSlitherLinkModel(ApoSlitherLinkPanel game) {
		this.game = game;
	}
	
	public final ApoSlitherLinkPanel getGame() {
		return this.game;
	}
	
	public void init() {
		
	}

	public Rectangle2D.Double getRec() {
		return this.rec;
	}

	public void setRec(Rectangle2D.Double rec) {
		this.rec = rec;
	}

	public boolean isBOver() {
		return this.bOver;
	}

	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y, boolean bRight);
	
	public void mousePressed(int x, int y) {
		
	}
	
	public void mouseMoved(int x, int y) {
		
	}
	
	public void think(int delta) {
		this.bOver = false;
		if (this.getRec() != null) {
			for (int i = 0; i < this.getGame().getClouds().size(); i++) {
				this.getGame().getClouds().get(i).setOverRec(null);
				if (this.getRec().contains(this.getGame().getClouds().get(i).getRec())) {
					this.getGame().getClouds().get(i).setBVisible(false);
				} else {
					if (this.getRec().intersects(this.getGame().getClouds().get(i).getRec())) {
						this.bOver = true;
						this.getGame().getClouds().get(i).setOverRec(this.getRec().createIntersection(this.getGame().getClouds().get(i).getRec()));
					}
					this.getGame().getClouds().get(i).setBVisible(true);					
				}
			}
		}
	}
	
	public abstract void render(Graphics2D g);
	
	public void renderAfter(Graphics2D g) {
		
	}
	
}
