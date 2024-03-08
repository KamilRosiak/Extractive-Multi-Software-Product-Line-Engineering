package org.apogames;

/**
 * Hilfsklasse zur Speicherung der Displaykonfiguration
 * @author Dirk Aporius
 *
 */
public class ApoDisplayConfiguration {
	/** Breite des Frames */
    private final int width;
    /** Höhe des Frames */
    private final int height;
    /** Farbtiefe des Frames */
    private final int depth;
    /** boolean Variable die angibt, ob das Frame im Window gestartet werden soll oder in Fullscreen */
    private final boolean windowed;
    /** boolean Variable die angibt, ob das Frame ein Breitbild ist */
    private boolean widescreen;
    /** boolean Variable die angibt, ob es sich um ein Applet handelt oder nicht */
    private final boolean applet;
    
    public ApoDisplayConfiguration(int width, int height, int depth, boolean windowed, boolean applet) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.windowed = windowed;
        this.widescreen = false;
        this.applet = applet;
        
        double w = width;
        double h = height;
        if (Double.compare(w / h, 16.0 / 9.0) == 0) {
            this.widescreen = true;
        }
    }

    /**
     * gibt zurück, ob es ein Applet ist oder nicht
     * @return gibt zurück, ob es ein Applet ist oder nicht
     */
    public final boolean isApplet() {
		return this.applet;
	}

    /**
     * gibt zurück, ob das Frame ein Breitbild ist oder nicht
     * @return gibt zurück, ob das Frame ein Breitbild ist oder nicht
     */
	public final boolean isWidescreen() {
		return this.widescreen;
	}

	/**
	 * gibt die Breite des Frames zurück
	 * @return gibt die Breite des Frames zurück
	 */
	public final int getWidth() {
        return this.width;
    }

	/**
	 * gibt die Höhe des Frames zurück
	 * @return gibt die Höhe des Frames zurück
	 */
    public final int getHeight() {
        return this.height;
    }

    /**
     * gibt die Farbtiefe des Frames zurück
     * @return gibt die Farbtiefe des Frames zurück
     */
    public final int getDepth() {
        return this.depth;
    }

    /**
     * gibt zurück, ob das Frame im Fenster gestartet wird oder im Fullscreen
     * @return TRUE, Frame wird im Fenster gestartet, FALSE Frame im Fullscreen gestartet
     */
    public final boolean isWindowed() {
        return this.windowed;
    }
}
