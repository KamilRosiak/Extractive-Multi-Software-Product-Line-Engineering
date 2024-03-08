package org.apogames;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apogames.help.ApoSplashScreen;

/**
 * abstrakte Mainklasse um eine Applikation zu erstellen
 * @author Dirk Aporius
 *
 */
public abstract class ApoMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static ApoMain main;
	
	private JComponent currentComponent;
	private ApoComponent component;
	
	/**
	 * Konstruktor
	 * @param splashUrl = String mit der Pfadangabe, wo das Bild für den SplashScreen liegt
	 */
	public ApoMain( String splashUrl ) {
		this( splashUrl, 0, 0 );
	}
	
	/**
	 * Konstruktor
	 * @param splashUrl = String mit der Pfadangabe, wo das Bild für den SplashScreen liegt
	 * @param width = Breite der Applikation
	 * @param height = Höhe der Applikation
	 */
	public ApoMain( String splashUrl, int width, int height ) {
		super();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.currentComponent	= (JComponent)this.getContentPane();
		//this.currentComponent.setPreferredSize(new Dimension(width,height));
		//this.currentComponent.setLayout(null);
		
		main = this;

		ApoSplashScreen splash = null;
		
		try {
			splash = new ApoSplashScreen(splashUrl);                       
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		this.init();
		
		splash.blendOut();
		
		this.initWindow();
	}
	
	/**
	 * wird immer aufgerufen wenn das Fenster in den Windowmodus geschaltet wird
	 */
	private void initWindow()
	{
		this.setUndecorated(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 *	entfernt die jetztige JComponent und schaltet auf das übergebene JComponent Objekt
	 */
	public void setComponent(ApoComponent component)
	{
		if ( this.component != null ) {
			this.component.stop();
			this.dispose();
			this.currentComponent	= (JComponent)this.getContentPane();
			//this.currentComponent.setPreferredSize(new Dimension(component.getWidth(),component.getHeight()));
			this.currentComponent.remove( this.component );
			this.component = component;
			this.currentComponent.add( this.component );
			super.validate();
			this.currentComponent.repaint();
			this.initWindow();
			this.component.init();
			this.component.start();
		} else {
			this.currentComponent	= (JComponent)this.getContentPane();
			//this.currentComponent.setPreferredSize(new Dimension(component.getWidth(),component.getHeight()));
			this.component = component;
			this.currentComponent.add( this.component );
			this.component.init();
			this.component.start();
		}
	}
	
	/**
	 * init Methode, die überschrieben werden muss
	 * und in der alles initialisiert werden sollte
	 * was man für das Programm braucht
	 */
	public abstract void init();

}
