package apoImp.game.background;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import apoImp.game.ApoImpPanel;

public class ApoImpBackground {

	private double lookup[] = new double[1024];
	
	private final ApoImpPanel panel;
	
	private ApoImpSled sled = null;
	
	private int nextSled = 0, nextPresent;
	
	private ArrayList<ApoImpPresent> presents;
	
	public ApoImpBackground(final ApoImpPanel panel) {
		initLookup();
		
		this.panel = panel;
		this.nextPresent = (int)(Math.random() * 1000) + 1000;
		this.nextSled = (int)(Math.random() * 5000) + 2000;
		this.presents = new ArrayList<ApoImpPresent>();
	}
	
	public void initLookup() {
		int rand = (int)(Math.random() * 100);
		double dHeight = (Math.random() * 50d) / 100d;
		double dPi = 1.9 + (double)(Math.random() * 20) / 100;
		double dDiff = (double)(Math.random() * 100) / 100 - 6;
		initLookup(rand, dHeight, dDiff, dPi);
	}
	
	private void initLookup (int rand, double dHeight, double dDiff, double dPi) {
		for (int i=0;i<lookup.length;i++) {
			if ((rand < 50)) {
				lookup[i] 	= (dHeight+0.8)*Math.cos(dDiff + ((double)i/(double)lookup.length*Math.PI*dPi));
			} else {
				lookup[i] 	= (dHeight+0.8)*Math.sin((double)i/(double)lookup.length*Math.PI*dPi);
			}
		}
	}
	
	/**
	 * 
	 * @param x 
	 * @return Zahl aus lookuptabelle
	 */
	private double sin (double x) {
		int i=(int)(x/Math.PI*lookup.length);
		i%=lookup.length;
		if(i<0) i+=lookup.length;
		return lookup[i];
	}
	
	/**
	 * 
	 * @param x
	 * @return Zahl aus lookuptabelle
	 */
	private double cos (double x) {
		int i=(int)((x+Math.PI*0.5)/Math.PI*lookup.length);
		i%=lookup.length;
		if(i<0) i+=lookup.length;
		return lookup[i];
	}
	
	public double getHeight(double pos) {
		pos*=0.25;
		return 	560-(sin(pos*0.1)*5*cos(pos*0.01)+sin(pos*0.01)*15+sin(pos*0.015)*cos(pos*0.02)*30+120);
	}
	
	public void think(int delta) {
		if ((this.sled == null) || (this.nextSled > 0)) {
			this.nextSled -= delta;
			if (this.nextSled <= 0) {
				if (this.sled == null) {
					this.sled = new ApoImpSled(this.panel.getSled(), -40, 40 + (int)(Math.random() * 30));
				} else {
					this.sled.setY(40 + (int)(Math.random() * 30));
				}
				this.sled.setX(-this.sled.getWidth());
				this.sled.setVecX(0.03f + (float)(Math.random() * 0.03f));
			}
		} else {
			this.sled.think(delta);
			this.sled.setX(this.sled.getX() + delta * this.sled.getVecX());
			
			this.nextPresent -= delta;
			if (this.nextPresent <= 0) {
				this.nextPresent = (int)(Math.random() * 3000) + 1000;
				this.dropPresent();
			}
			if (sled.getX() > 480) {
				this.nextSled = (int)(Math.random() * 10000) + 10000;
			}
		}
		for (int i = this.presents.size() - 1; i >= 0; i--) {
			this.presents.get(i).think(delta);
			if (!this.presents.get(i).isVisible()) {
				this.presents.remove(i);
			} else {
				if (this.presents.get(i).getY() > this.getHeight(this.presents.get(i).getX())) {
					this.presents.get(i).setDisabled(true);
				}
			}
		}
	}
	
	private void dropPresent() {
		this.presents.add(new ApoImpPresent(this.sled.getXMiddle(), this.sled.getY() + 10, 0.1f, -0.1f));
	}
	
	public void renderBackground(Graphics2D g) {
		int xpos[]=new int[480+3],ypos[] = new int[480+3];

		//g.fillRect((int)r.getMinX(),(int)(y+200),(int)r.getWidth(),(int)r.getHeight());
		for (int i=0;i <= 480;i++) {
			double pos = i;
			double h = getHeight(pos);
			xpos[i] = i;
			ypos[i] = (int)h;
		}
		xpos[480+1] = 480;
		ypos[480+1] = 560;
		xpos[480+2] = 0;
		ypos[480+2] = 560;
		
		g.setColor(new Color(230,230,230));
		g.fillPolygon(xpos,ypos,480+3);
		g.setColor(new Color(200,200,200));
		g.setStroke(new BasicStroke(3));
		g.drawPolyline(xpos,ypos,480);
		
		for (int i=-20;i<480+20;i++) {
			int pos = (int)i;
			if (pos%(int)(sin(pos*0.1)*cos(pos*0.01)*8+30)==0 && cos(pos*0.25+sin(pos*0.2+0.2))*cos(pos*0.11)>0) {
				double h = getHeight(pos);
				if (Math.random() * 100 > 30) {
					g.drawImage(this.panel.getTree(),i,(int)h-20,null);
				} else {
					g.drawImage(this.panel.getSnowman(),i,(int)h-20,null);					
				}
			}
		}
		
		for (int i=-30;i<=480+30;i++) {
			int pos = (int)i;
			if (pos%90==0 && cos(pos*0.3+sin(pos*0.2))*cos(pos*0.1)>0) {
				g.drawImage(this.panel.getHouse(),(int)(pos-21),(int)(getHeight(pos)-this.panel.getHouse().getHeight() + 5),null);
			}
		}
	}
	
	public void renderHouseLightAndSled(Graphics2D g) {
		for (int i=15;i<=480+30;i++) {
			int pos = (int)i;
			if ((pos-45)%90==0 && cos(pos*0.3+sin(pos*0.2))*cos(pos*0.1)>0) {
				g.drawImage(this.panel.getHouseHit(),(int)(pos-21),(int)(getHeight(pos)-this.panel.getHouseHit().getHeight() + 5),null);
			}
		}
		if ((this.sled != null) && (this.nextSled <= 0)) {
			this.sled.render(g, 0, 0);
		}
		for (int i = this.presents.size() - 1; i >= 0; i--) {
			g.setColor(Color.RED);
			int x=(int)(presents.get(i).getX());
			int y =(int)(presents.get(i).getY()); 
			g.fillRect((int)(x)-2,(int)(y)-2,5,5);

			g.setColor(Color.YELLOW);
			g.fillRect(x,y-2,1,5);
			g.fillRect(x-2,y,5,1);
		}
	}
}
