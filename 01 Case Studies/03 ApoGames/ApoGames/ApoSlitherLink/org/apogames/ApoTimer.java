package org.apogames;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Klasse, die sich um den Timer kümmert und somit dass es flüssig alles in einer
 * bestimmten Reihenfolge abläuft
 * @author Dirk Aporius
 *
 */
public class ApoTimer extends TimerTask
{
	private ApoThreadInterface	game;
	private boolean				bRunning;
	private boolean				bWait;
	private boolean				bRender;
	private long 				wait;
	private long				delta;
	private Timer				timer;
	
	public ApoTimer( ApoThreadInterface game, long wait ) {
		super();
		
		this.game		= game;
		this.wait		= wait;
		
		this.bRunning	= true;
		
		this.setBWait(true);
		this.setBRender(true);
	}

	/**
	 * gibt zurück, ob gedacht werden soll
	 * @return TRUE, es soll gedacht werden, sonst FALSE
	 */
	public boolean isBWait() {
		return this.bWait;
	}

	/**
	 * setzt den Wert, ob gedacht werden soll, auf den übergebenen
	 * @param bWait : TRUE, es soll gedacht werden, sonst FALSE
	 */
	public void setBWait(boolean bWait) {
		this.bWait = bWait;
	}

	/**
	 * gibt zurück, ob gemalt werden soll
	 * @return TRUE, es soll gemalt werden, sonst FALSE
	 */
	public boolean isBRender() {
		return bRender;
	}

	/**
	 * setzt den Wert, ob gedacht werden soll, auf den übergebenen
	 * @param bRender : TRUE, es soll gedacht werden, sonst FALSE
	 */
	public void setBRender(boolean bRender) {
		this.bRender = bRender;
	}

	/**
	 * gibt die Zeit zurück, die der Timer immer wartet
	 * @return gibt die Zeit zurück, die der Timer immer wartet
	 */
	public long getWait() {
		return this.wait;
	}

	/**
	 * setzt die Zeit, die der Timer immer wartet, auf den übergebenen Wert
	 * @param wait
	 */
	public void setWait(long wait) {
		this.wait = wait;
	}

	/**
	 * startet den Timer mit der wait Time
	 */
	public void start()
	{
		if ( this.timer == null )
		{
			this.timer		= new Timer();
			this.timer.scheduleAtFixedRate( this, this.wait, this.wait );
			this.delta = System.nanoTime();
		}
	}
	
	/**
	 * stoppt den Timer
	 */
	public void stop()
	{
		if ( this.timer != null )
		{
			this.setBRunning( false );
			this.timer.cancel();
			this.timer		= null;
		}
	}
	
	/**
	 * gibt zurück, ob es gerade gewollt ist den Timer zu nutzen
	 * @return TRUE, falls Spiel grad läuft sonst FALSE
	 */
	public boolean isBRunning()	{
		return this.bRunning;
	}

	/**
	 * setzt die boolean Variable, die dafür verantwortlich ist, ob
	 * die Run-Methode des Timer läuft, auf den übergebenen Wert
	 * @param bRunning
	 */
	public void setBRunning( boolean bRunning )	{
		this.bRunning = bRunning;
	}
	
	/**
	 * gibt ein PseudoFramePerSecond wieder
	 * @return gibt ein PseudoFramePerSecond wieder
	 */
	public int getFps() {
		return (int)this.getWait();
	}
	
	@Override
	public void run()
	{
		if ( this.isBRunning() )
		{
			long delta = System.nanoTime();
			while ( delta - this.delta >= this.wait * 1000000 ) {
				this.delta += this.wait * 1000000;
				if (( this.game.isBThink()) && (this.isBWait())) {
					this.game.think( (int)this.wait );
				}
			}
			if ((this.game.isBRepaint()) && (this.isBRender())) {
				this.game.render();
			}
		}
	}

}
