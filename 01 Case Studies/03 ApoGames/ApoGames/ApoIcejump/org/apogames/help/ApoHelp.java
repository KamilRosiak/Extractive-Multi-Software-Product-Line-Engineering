package org.apogames.help;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Hilfsklasse, die einige Methoden zur Verfügung stellt,
 * welche man immer mal wieder gebrauchen kann, aber in dafür keine
 * extra Klasse geschrieben werden muss
 * @author Dirk Aporius
 *
 */
public class ApoHelp {

	/**
	 * Konstruktor
	 */
	public ApoHelp() {
	}
	
	/**
	 * erste Methode um ein Bild zu drehen, bei Angabe des Winkels
	 * funktioniert nur perfekt bei Bildern, deren Maße width = height ist
	 * @param src = Bilder welches gedreht werden soll
	 * @param degrees = Winkelangabe
	 * @return gedrehtes Bild
	 */
	public static BufferedImage rotateImage(BufferedImage src, double degrees) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance( Math.toRadians(degrees), src.getWidth() / 2, src.getHeight() / 2);
		BufferedImage rotatedImage = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(src.getWidth(), src.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)rotatedImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setTransform( affineTransform );
		g.drawImage( src, 0, 0, null );
		g.dispose();
		return rotatedImage;
	}
	
	/**
	 * erste Methode um ein Bild zu drehen, bei Angabe des Winkels
	 * funktioniert bei allen Bildern
	 * @param inputImage = Bilder welches gedreht werden soll
	 * @param degrees = Winkelangabe
	 * @return gedrehtes Bild
	 */
	public static BufferedImage rotateImageMethodTwo(BufferedImage inputImage, double degrees ) {
		int x,y = 0;
		
		if ((degrees == 0)||(degrees == 180)||(degrees == 360)){
			x = inputImage.getWidth(null);
			y = inputImage.getHeight(null);
		} else {
			x = inputImage.getHeight(null);
			y = inputImage.getWidth(null);
		}
		BufferedImage sourceBI = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(x, y, BufferedImage.TRANSLUCENT);
		AffineTransform at = new AffineTransform();

		// rotate around image center
		at.rotate(Math.toRadians( degrees ), (sourceBI.getWidth() / 2), (sourceBI.getHeight() / 2) );

	    /*
	     * translate to make sure the rotation doesn't cut off any image data
	     */
		AffineTransform translationTransform;
		translationTransform = findTranslation( at, sourceBI, degrees );
		at.preConcatenate( translationTransform );


		Graphics2D g = (Graphics2D) sourceBI.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setTransform(at);
		g.drawImage(inputImage, 0, 0, null);

		return sourceBI;
	}

	/**
	 * find proper translations to keep rotated image correctly displayed
	 * @param at = AffineTransformObjekt
	 * @param bi = BufferedImage
	 * @param degrees = Winkel
	 * @return korrekte AffineTransform
	 */
	private static AffineTransform findTranslation(AffineTransform at, BufferedImage bi, double degrees ) {
		Point2D p2din, p2dout;
		double ytrans, xtrans = 0.0;

		AffineTransform tat = new AffineTransform();

		if(degrees == 180) {
			p2din = new Point2D.Double(0, bi.getHeight());
		} else {
			p2din = new Point2D.Double(0.0, 0.0);
		}

		p2dout = at.transform(p2din, null);

		if(degrees == 270) {
			xtrans = p2dout.getX();
			ytrans = xtrans;
		} else {
			ytrans = p2dout.getY();
			xtrans = ytrans;
		}

		tat.translate(-xtrans, -ytrans);

		return tat;
	}
	
	/**
	 * rundet einen übergebenen Wert auf die nachgefragte Stellen
	 * @param value = Der zu rundende float Wert
	 * @param adjust = wieviel Stellen nach dem Komma
	 * @return gerundete Zahl
	 */
	public static float round( float value, int adjust ) {
		BigDecimal bigDecimal = new BigDecimal( value );
		return ( bigDecimal.setScale( adjust, BigDecimal.ROUND_HALF_UP )).floatValue();
	}
	
	/**
	 * gibt den Winkel zwischen 2 Punkten zurück
	 * @param a = Punkt A
	 * @param b = Punkt B
	 * @return Winkel zwischen den 2 Punkten a und b
	 */
	public static double getAngleBetween2Points( Point a, Point b ) {
		return getAngleBetween2Points((float)a.getX(), (float)a.getY(), (float)b.getX(), (float)b.getY());
	}
		
	public static double getAngleBetween2Points( float x1, float y1, float x2, float y2 ) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double angle = 0.0d;
 
		if ( dx == 0.0 ) {
			if ( dy == 0.0 ) {
				angle = 0.0;
			} else if ( dy > 0.0 ) {
				angle = Math.PI / 2.0;
			} else {
				angle = (Math.PI * 3.0) / 2.0;
			}
		}
		else if( dy == 0.0 ) {
			if( dx > 0.0 ) {
				angle = 0.0;
			} else {
				angle = Math.PI;
			}
		} else {
			if ( dx < 0.0 ) {
				angle = Math.atan( dy/dx ) + Math.PI;
			} else if( dy < 0.0 ) {
				angle = Math.atan( dy/dx ) + ( 2*Math.PI );
			} else {
				angle = Math.atan( dy/dx );
			}
		}
		return ( angle * 180 ) / Math.PI;
    }
	
	public static ArrayList<ApoFloatPoint> getCircleCuts(float x1, float y1, float radius1, float x2, float y2, float radius2) {
		ArrayList<ApoFloatPoint> results = new ArrayList<ApoFloatPoint>();

		double resultX1 = -10000, resultX2 = -10000, resultY1 = -10000, resultY2 = -10000;

		// check for special cases:
		/*if ((y1 == y2) && (x2 != x1)) {
			System.out.println( "Check" );
			resultX1 = (radius1 * radius2 - radius2 * radius2) / (2 * x2 - 2 * x1);
			resultX2 = resultX1;
			double p1 = y1 * y1 - radius1 * radius1 + resultX1 * resultX1 - 2 * x1 * resultX1 + x1 * x1;
			resultY1 = y1 + Math.sqrt(y1 * y1 - p1);
			resultY2 = y1 - Math.sqrt(y1 * y1 - p1);
		} else if ((x2 == x1) && (y2 != y1)) {// x values identical
			resultY1 = (radius1 * radius1 - radius2 * radius2) / (2 * y2 - 2 * y1);
			resultY2 = resultY1;
			double q1 = x1 * x1 + resultY1 * resultY1 - 2 * y1 * resultY1 + y1 * y1	- radius1 * radius1;
			resultX1 = x1 + Math.sqrt(x1 * x1 - q1);
			resultX2 = x1 - Math.sqrt(x1 * x1 - q1);
		} else */if ((x2 == x1) && (y2 == y1)) {// centers identical
			return results;
		} else { // default case
			// ok let's calculate the constants
			double c1 = ((radius1 * radius1) - (radius2 * radius2) - (x1 * x1) + (x2 * x2) - (y1 * y1) + (y2 * y2)) / (2.0 * x2 - 2.0 * x1);
			double c2 = (y1 - y2) / (x2 - x1);
			double k1 = 1.0 + (1.0 / Math.pow(c2, 2.0));
			double k2 = 2.0 * x1 + (2.0 * y1) / (c2) + (2.0 * c1) / Math.pow(c2, 2.0);
			double k3 = x1 * x1 + (c1 * c1) / (c2 * c2) + (2.0 * y1 * c1) / (c2) + (y1 * y1) - (radius1 * radius1);
			System.out.println("resultX1: "+resultX1);
			resultX1 = ((k2 / k1) / 2.0) + Math.sqrt((Math.pow((k2 / k1), 2.0) / 4.0) - (k3 / k1));
			System.out.println("resultX1: "+resultX1);
			resultX2 = (k2 / k1) / 2.0 - Math.sqrt((Math.pow((k2 / k1), 2.0) / 4.0) - (k3) / (k1));
			resultY1 = 1.0 / (c2) * resultX1 - (c1 / c2);
			resultY2 = 1.0 / (c2) * resultX2 - (c1 / c2);
		}
		
		if ((resultX1 != -10000) && (resultY1 != -10000)) {
			System.out.println("resultX1: "+resultX1);
			results.add(new ApoFloatPoint((float)(resultX1), (float)(resultY1)));
		}
		if ((resultX2 != -10000) && (resultY2 != -10000)) {
			results.add(new ApoFloatPoint((float)(resultX2), (float)(resultY2)));
		}
		return results;
	}
	
	/**
	 * gibt den String mit der Zeitangabe zurück
	 * @return gibt den String mit der Zeitangabe zurück
	 */
	public static String getTimeToDraw(int time) {
		if (time <= 0)
			return "";
		String min = String.valueOf(time/1000/60);
		String sec = ""+((time/1000)%60);
		String msec = ""+((time/10)%100);
		if (sec.length()<2) sec = "0"+sec;
		if (msec.length()<2) msec = "0"+msec;
		String timeString = min+":"+sec+":"+msec;
		
		return timeString;
	}

	/**
	 * gibt einen Zufallswert zurück
	 * @return gibt einen Zufallswert zurück
	 */
	public static int getRandomValue(int value, int plus) {
		int random = (int)(Math.random()*value + plus);
		if (random >= value + plus) {
			random = plus;
		}
		return random;
	}

	/**
	 * gibt ein kopiertes gleiches Feld zurück
	 * @param copyArray : das zu kopierende Array
	 * @return gibt ein kopiertes gleiches Feld zurück
	 */
	public static byte[][] getCopy(byte[][] copyArray) {
		byte[][] array = new byte[copyArray.length][copyArray[0].length];
		for (int y = 0; y < copyArray.length; y++) {
			for (int x = 0; x < copyArray[0].length; x++) {
				array[y][x] = copyArray[y][x];
			}
		}
		return array;
	}
	
	/**
	 * gibt ein kopiertes gleiches Feld zurück
	 * @param copyArray : das zu kopierende Array
	 * @return gibt ein kopiertes gleiches Feld zurück
	 */
	public static int[][] getCopy(int[][] copyArray) {
		int[][] array = new int[copyArray.length][copyArray[0].length];
		for (int y = 0; y < copyArray.length; y++) {
			for (int x = 0; x < copyArray[0].length; x++) {
				array[y][x] = copyArray[y][x];
			}
		}
		return array;
	}
}
