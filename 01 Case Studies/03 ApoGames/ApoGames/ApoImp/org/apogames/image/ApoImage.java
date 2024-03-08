package org.apogames.image;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apogames.ApoConstants;

/**
 * Klasse, die ein BufferedImage lädt
 * @author Dirk Aporius
 *
 */
public class ApoImage extends Component {
	
	private static final long serialVersionUID = 1L;
	
	private Component component;
	
	/**
	 * Konstruktor
	 */
	public ApoImage() {
		this.component = this;
	}
	
	/**
	 * lädt das Bild aus dem übergbenen String und wartet bis es fertig geladen ist
	 * @param BildString = wo steckt das Bild
	 * @param bLoad = TRUE wenn extern laden, FALSE wenn Image in der JAR
	 * @return gibt das geladenen Bild zurück
	 */
	public BufferedImage getPic(final String pic, final boolean bLoad, final boolean bWithAlpha) {
		BufferedImage i;
		MediaTracker mt;
		mt = new MediaTracker(this.component);// damit alles mit einmal angezeigt wird
		
		try {
			i = getImage(pic, bLoad, bWithAlpha);
			if (i == null) {
				return null;
			}
		} catch (IllegalArgumentException ex) {
			return null;
		}
		mt.addImage(i, 0);

		try {
			// Wait until all pic are load,
			mt.waitForAll();
		} catch (InterruptedException e) {
			// nothing
		}
		return i;
	}
	
	/**
	 * lädt das Bild aus einem übergebenen String
	 * @param BildName = wo steckt das Bild
	 * @param bLoad
	 * @return das Bild
	 */
	private BufferedImage getImage(final String pic, final boolean bLoad, final boolean bWithAlpha)	{
		BufferedImage icon = null;
		
		BufferedImage img = null;
		
		if ( !bLoad ) {
			try	{
				img = ImageIO.read( this.getClass().getClassLoader().getResource(pic));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (NullPointerException ex) {
				return  null;
			}
		} else {
			try {
				if ( ApoConstants.B_APPLET ) {
					img = ImageIO.read(new URL(pic));
				} else {
					img = ImageIO.read(new File(pic));
				}
			} catch (MalformedURLException e) {
				System.out.println("Konnte das Bild "+pic+" nicht laden");
				return null;
			} catch (IOException e) {
				System.out.println("Konnte das Bild "+pic+" nicht laden");
				return null;
			}
		}
		if (img != null) {
			if (bWithAlpha) {
				icon = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			} else {
				icon = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);					
			}
			icon.createGraphics().drawImage(img,0,0,null);
			icon	= img;
		}
		return (icon);
	}
	
}
