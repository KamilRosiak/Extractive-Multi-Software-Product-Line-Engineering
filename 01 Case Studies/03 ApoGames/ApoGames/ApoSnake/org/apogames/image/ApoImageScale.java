package org.apogames.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A utility to perform the scale2x and the scale3x algorithm on a Java Image
 * 
 * @author Kevin Glass and Dirk Aporius
 */
public class ApoImageScale {

	public static final int ORIGINAL_SCALE = 1;
	public static final int DOUBLE_SCALE = 2;
	public static final int TRIPPLE_SCALE = 3;
	
	/** source of the image */
	private String srcFile;
	
	private int scale = ORIGINAL_SCALE;

	/**
	 * Konstruktor
	 * @param srcFile = Pfadangabe, wo das Bild liegt
	 */
	public ApoImageScale(String srcFile) {
		this(srcFile, DOUBLE_SCALE);
	}
	
	/**
	 * Konstruktor
	 * @param srcFile = Pfadangabe, wo das Bild liegt
	 * @param b2X = booleanValue, welcher angibt, ob das Bild
	 * 				skaliert werden soll oder nicht
	 */
	public ApoImageScale(String srcFile, int scale) {
		this.srcFile = srcFile;
		this.scale = scale;
	}

	/**
	 * gibt einen int Wert zurück, welcher angibt wie das Bild
	 * skaliert werden soll
	 * Möglichkeiten sind
	 * ORIGINAL_SCALE = gleiche Größe, bloss als png gespeichert
	 * DOUBLE_SCALE = das Bild wird doppelt so groß wiedergegeben
	 * TRIPPLE_SCALE = das Bild wird dreifach so groß wiedergegeben
	 * @return int Wert, welcher angibt wie das Bild
	 * skaliert werden soll
	 * Möglichkeiten sind
	 * ORIGINAL_SCALE = gleiche Größe, bloss als png gespeichert
	 * DOUBLE_SCALE = das Bild wird doppelt so groß wiedergegeben
	 * TRIPPLE_SCALE = das Bild wird dreifach so groß wiedergegeben
	 */
	public int getScale() {
		return this.scale;
	}

	/**
	 * setzt den Wert, wie das Bild skaliert werden soll auf den
	 * übergebenen Wert
	 * @param scale = neuer Skalwert
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * schreibt das veränderte Bild in den Ordner, wo das Anfangbild ist
	 * @return true, falls alles geklappt hat sonst false
	 */
	public boolean writeScaledImage() {
		return this.writeScaledImage( this.srcFile );
	}
	
	/**
	 * schreibt das veränderte Bild in den 
	 * @param writeFile
	 * @return true, falls alles geklappt hat sonst false
	 */
	public boolean writeScaledImage( String writeFile ) {
		try {
			return writeScaledImage(ImageIO.read(new File(this.srcFile)), writeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * scaliert ein OriginalBild und speichert es in den übergebenen Ordner
	 * @param iOriginal
	 * @param writeFile
	 * @return true, falls alles geklappt hat sonst false
	 */
	public boolean writeScaledImage( BufferedImage iOriginal, String writeFile ) {
		try {
			BufferedImage src = iOriginal;
			BufferedImage out = null;
			if ( this.getScale() == DOUBLE_SCALE ) {
				out = this.getDoubleScaledImage( src );
			} else if ( this.getScale() == TRIPPLE_SCALE ) {
				
			} else {
				out = src;
			}

			String outFile = writeFile;
			if ( writeFile.lastIndexOf( "." ) != -1 ) {
				outFile = writeFile.substring( 0, writeFile.lastIndexOf( "." ) );
			}
			if ( this.getScale() == DOUBLE_SCALE ) {
				outFile += "2x.png";
			} else if ( this.getScale() == TRIPPLE_SCALE ) {
				outFile += "3x.png";
			} else {
				outFile += ".png";
			}
			ImageIO.write(out, "PNG", new File(outFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Retrieve the scaled image. Note this is the method that actually does the
	 * work so it may take some time to return
	 * 
	 * @return The newly scaled image
	 */
	public BufferedImage getDoubleScaledImage( BufferedImage srcImage ) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();

		int[] srcData = new int[width * height];
		srcImage.getRGB(0, 0, width, height, srcData, 0, width);

		ApoRawScale2x scaler = new ApoRawScale2x( srcData, width, height );

		BufferedImage image = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_ARGB_PRE);
		image.setRGB( 0, 0, width * 2, height * 2, scaler.getScaledData(), 0, width * 2 );

		return image;
	}
	
	/**
	 * Retrieve the scaled image. Note this is the method that actually does the
	 * work so it may take some time to return
	 * 
	 * @return The newly scaled image
	 */
	public BufferedImage getTrippleScaledImage( BufferedImage srcImage ) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();

		int[] srcData = new int[width * height];
		srcImage.getRGB(0, 0, width, height, srcData, 0, width);

		ApoRawScale3x scaler = new ApoRawScale3x( srcData, width, height );

		BufferedImage image = new BufferedImage(width * 3, height * 3, BufferedImage.TYPE_INT_ARGB_PRE);
		image.setRGB( 0, 0, width * 3, height * 3, scaler.getScaledData(), 0, width * 3);

		return image;
	}

}
