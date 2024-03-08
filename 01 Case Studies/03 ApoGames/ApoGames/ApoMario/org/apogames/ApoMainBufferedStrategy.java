package org.apogames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apogames.help.ApoSplashScreen;

/**
 * abstrakte Mainklasse um eine Applikation zu erstellen
 * @author Dirk Aporius
 *
 */
public abstract class ApoMainBufferedStrategy extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static ApoMainBufferedStrategy main;
	
	private ApoComponentBufferedStrategy component;
	
	/**
	 * Konstruktor
	 * @param splashUrl = String mit der Pfadangabe, wo das Bild für den SplashScreen liegt
	 */
	public ApoMainBufferedStrategy(String splashUrl) {
		this(splashUrl, 0, 0);
	}
	
	/**
	 * Konstruktor
	 * @param splashUrl = String mit der Pfadangabe, wo das Bild für den SplashScreen liegt
	 * @param width = Breite der Applikation
	 * @param height = Höhe der Applikation
	 */
	public ApoMainBufferedStrategy(String splashUrl, int width, int height) {
		super();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				windowClosingEvent();
				System.exit(0);
			}
		});
		
		main = this;

		ApoSplashScreen splash = null;
		
		try {
			splash = new ApoSplashScreen( splashUrl );                       
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		this.init();
		
		splash.blendOut();
		
		this.initWindow();
		
		if ( this.component != null ) {
			this.component.start();
		}
	}
	
	public void windowClosingEvent() {
		
	}
	
	/**
	 * wird immer aufgerufen wenn das Fenster in den Windowmodus geschaltet wird
	 */
	private void initWindow() {
		//this.setIgnoreRepaint(true);
		//this.setUndecorated(false);
		//this.setResizable(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 *	entfernt die jetztige JComponent und schaltet auf das übergebene JComponent Objekt
	 */
	public void setComponent(ApoComponentBufferedStrategy component) {
		if (this.component != null) {
			this.component.stop();
			this.dispose();
			this.getContentPane().remove(this.component);
			this.component = component;
			this.getContentPane().add(this.component);
			super.validate();
			this.initWindow();
			this.component.init();
			this.component.start();
		} else {
			this.component = component;
			//this.add(this.component);
			this.getContentPane().add(this.component);
			this.component.init();
		}
	}
	
	/**
	 * init Methode, die überschrieben werden muss
	 * und in der alles initialisiert werden sollte
	 * was man für das Programm braucht
	 */
	public abstract void init();

}