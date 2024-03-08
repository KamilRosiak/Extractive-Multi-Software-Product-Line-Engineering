package apoImp.game.background;

public class ApoImpPresent {
	/**
	 * Luftwiederstand<br />
	 * neue Geschwindigkeit = neue Geschwindigkeit * DRAG
	 */
	public static final double DRAG = 0.99d/25;
	
	/**
	 * Erdanziehung<br />
	 * neue Position Y = alte position Y + Geschwindigkeit Y + GRAVITY
	 */
	public static final double GRAVITY = 1.5d/25d;
	private double x,y,vx,vy;
	private boolean disabled = false;
	private int timeOnGround;
	private boolean bVisible;
	
	protected ApoImpPresent(double x, double y, double vx, double vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bVisible = true;
		this.timeOnGround = 10000;
		
	}
	
	/**
	 * Wenn das Geschenk noch aktiv ist wird hier die neue Position und die neue Geschwindigkeit errechnet.
	 * Formel: <br>
	 * <pre>
	 *   x+=vx;
	 *   y+=vy + GRAVITY;
	 *   vx*=DRAG;
	 *   vy*=DRAG;
	 * </pre>
	 * In Worten: <br>
	 * Neue Position = Alte Position + Geschwindigkeit + Erdanziehung<br>
	 * Neue Geschwindigkeit = alte Geschwindigkeit *  DRAG<br>
	 * Diese Berechnung wird pro Zyklus errechnet. Diese Schrittweise Berechnung ist eine Ableitung der 
	 * Physikalischen Formeln mit der man die Beschleunigung eines Objektes berechnet sowie der 
	 * Formel mit die Geschwindigkeit eines mit Reibung rutschenden Gegenstandes berechnet. 
	 * Die iterative Näherung ist im Gegensatz zu exakten Formel wesentlich einfacher.
	 */
	protected void think (int delta) {
		if (this.disabled)  {
			this.timeOnGround -= delta;
			if (this.timeOnGround < 0) {
				this.bVisible = false;
			}
		} else {
			x += vx * delta;
			y += vy * delta + GRAVITY * delta;
			vx *= DRAG * delta;
			vy *= DRAG * delta;
		}
	}

	public boolean isVisible() {
		return this.bVisible;
	}

	/**
	 * Position des Geschenks
	 * @return x Komponente
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Ausschalten vom geschenk (fällt nicht weiter)
	 *
	 */
	public void disable() {
		disabled = true;
	}

	/**
	 * Position des Geschenks
	 * @return y Komponente
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * erzeugt einen String der die Position und Geschwindigkeit des Geschenks enthält
	 */
	public String toString() {
		return x+" "+y+" "+vx+" "+vy;
	}

	/**
	 * Liegt ein Geschenk am boden so wird es disabled
	 * @return true wenn es am Boden liegt
	 */
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}
