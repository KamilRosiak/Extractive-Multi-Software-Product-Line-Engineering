package org.apogames.sound;

/**
 * Der EThread ist im Prinzip mit "java.lang.Thread" vergleichbar bzw.
 * eigentlich nichts anderes. Entstanden ist EThread aufgrund diverser
 * Tatsachen "java.lang.Thread" betreffend.
 * 1. "java.lang.Thread" kann nicht erneut gestartet werden. Eine
 *    abgelaufene Instanz von "java.lang.Thread" belegt bei unsauberer
 *    Programmierung immer noch Speicher.
 * 2. Aufgrund von Punkt 1 laesst "java.lang.Thread" auch nicht im Sinne
 *    von pausieren unterbrechen. "interrupt()" von "java.lang.Thread"
 *    ermoeglicht statt dessen einen sauber beendeten "java.lang.Thread",
 *    was eigentlich dessen "stop()"-Methode von vorne herein haette tun
 *    muessen.
 * 3. "java.lang.Thread" kann zwar von unsignierten Applets gestartet
 *    jedoch nicht auf konventionellem Wege ("interrupt()") beendet
 *    werden.
 * 
 * Ein fuer einen EThread konzipiertes Runnable koennte nun folgenden
 * Grundaufbau haben:
 * 001 public void run()
 * 002 {
 * 003   while((EThread) runner).isRunning()) {
 * 004     if(!((EThread) runner).isPaused()) {
 * 005       //...running code goes here
 * 006     } else {
 * 007       //...paused code goes here
 * 008     }
 * 009     try {
 * 010       synchronized(runner) {
 * 011         runner.wait((long) timeout);
 * 012       }
 * 013     } catch(InterruptedException e) {
 * 014       ((EThread) runner).stop();
 * 015     }
 * 016   }
 * 017 }
 * @author 0x0badc0de
 */

public class ApoSoundThread {
	/*
	 * Interner Worker-Thread
	 */
	private Thread worker;

	/*
	 * Abzuarbeitendes Runnable
	 */
	private final Runnable runner;

	/*
	 * Status-Flaggen running => Thread wurde gestartet paused => Thread wurde
	 * pausiert
	 */
	private boolean running, paused;

	/**
	 * Neue Instanz eines EThreads. Ein Runnable ist unbedingt Vorraussetzung.
	 * 
	 * @param runner
	 */
	public ApoSoundThread(Runnable runner) {
		if (runner == null) {
			throw new NullPointerException("runner = null");
		}
		paused = true;
		this.runner = runner;
	}

	/**
	 * Startet einen EThread.
	 */
	public synchronized void start() {
		start(false);
	}

	/**
	 * Startet einen EThread pausiert.
	 */
	public synchronized void start(boolean sw) {
		pause(sw);
		if (worker == null) {
			running = true;
			worker = new Thread(runner) {
				/**
				 * Thread.run() musste ueberschrieben werden, damit EThread ein
				 * Ende des Runnables mitbekommt.
				 */
				@Override
				public void run() {
					runner.run();
					running = false;
					worker = null;
				}
			};
			worker.start();
			if (sw) {
				try {
					synchronized (worker) {
						worker.wait();
					}
				} catch (InterruptedException e) {
					running = false;
					worker = null;
				}
			}
		}
	}

	/**
	 * Pausiert einen EThread (sw = true) oder laesst ihn fortfahren (sw =
	 * false).
	 * 
	 * @param sw
	 */
	public synchronized void pause(boolean sw) {
		paused = sw;
		if (worker != null) {
			if (paused) {
				try {
					synchronized (worker) {
						worker.wait();
					}
				} catch (InterruptedException e) {
					running = false;
					worker = null;
				}
			} else {
				synchronized (worker) {
					worker.notify();
				}
			}
		}
	}

	/**
	 * Schaltet den Pause-Modus des EThreads um.
	 */
	public synchronized void pause() {
		pause(!paused);
	}

	/**
	 * Stopt einen EThread. Die Verwendung des Methodennamens "stop" soll die
	 * diesbezuegliche Verwirrung von "java.lang.Thread" beenden. Gestoppte
	 * EThreads koennen nicht fortgesetzt werden. Unterbrechen (interrupt)
	 * bedeutet "pausieren".
	 * 
	 * @see pause(boolean sw)
	 * @see pause()
	 */
	public synchronized void stop() {
		if (running) {
			running = false;
		}
	}

	/**
	 * Gibt zurueck, ob der EThread gestartet wurde und immernoch laeuft. Auch
	 * ein pausierter EThread liefet hier "true".
	 * 
	 * @return running flag
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Gibt zurueck ob ein EThread unterbrochen wurde. Das ist der Fall, wenn er
	 * pausiert oder noch nicht gestartet wurde.
	 * 
	 * @return paused flag
	 */
	public boolean isPaused() {
		return paused;
	}
}
