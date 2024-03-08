package apoSoccer.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;

import apoSoccer.ApoDynamicFilter;
import apoSoccer.game.ApoSoccerGame;

/**
 * Klasse, die die Sachen und Haare der SPieler umfärbt
 * @author Dirk Aporius
 *
 */
public class ApoPlayerCloth {

	private final Color[][] COLOR_CHANGE = new Color[][] {{new Color(246, 255, 0), new Color(205, 212, 0), new Color(163, 169, 0), new Color(122, 126, 0), new Color(226, 0, 32), new Color(191, 0, 27), new Color(156, 0, 22), new Color(121, 0, 17)},
														  {new Color(222, 221, 255), new Color(182, 178, 255), new Color(141, 135, 255), new Color(101, 95, 221), new Color(9, 116, 1), new Color(8, 98, 1), new Color(7, 80, 1), new Color(5, 62, 1)}};

	private final Color[] COLOR_ORIGINAL = new Color[] {new Color(60, 60, 61), new Color(40, 40, 41), new Color(20, 20, 21), new Color(0, 0, 1), new Color(1, 0, 0), new Color(21, 20, 20), new Color(41, 40, 40), new Color(61, 60, 60)};
	
	public ApoPlayerCloth() {
		
	}
	
	public BufferedImage getVisitorImage(BufferedImage iPlayer) {
		BufferedImage iPlayerNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iPlayer.getWidth(), iPlayer.getHeight(), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iPlayerNew.getGraphics();

		g.drawImage(iPlayer, 0, 0, null);
		
		for (int y = 0; y < iPlayerNew.getHeight(); y++) {
			for (int x = 0; x < iPlayerNew.getWidth(); x++) {
				Color c = new Color(iPlayerNew.getRGB(x, y));
				for (int color = 0; color < this.COLOR_CHANGE[0].length; color++) {
					if (c.equals(this.COLOR_CHANGE[0][color])) {
						iPlayerNew.setRGB(x, y, this.COLOR_CHANGE[1][color].getRGB());
					}
				}
			}
		}
		
		g.dispose();
		return iPlayerNew;
	}

	public BufferedImage getPlayerHair(ApoSoccerGame game, BufferedImage iHair, Color hair) {
		BufferedImage iHairNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iHair.getWidth(), iHair.getHeight(), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iHairNew.getGraphics();

		g.drawImage(iHair, 0, 0, null);
		
		for (int y = 0; y < iHairNew.getHeight(); y++) {
			for (int x = 0; x < iHairNew.getWidth(); x++) {
				Color c = new Color(iHair.getRGB(x, y));
				if (c.getRed() > 0) {
					iHairNew.setRGB(x, y, hair.getRGB());					
				}
			}
		}
		
		g.dispose();
		
		return iHairNew;
	}
	
	public BufferedImage getPlayerImage(ApoSoccerGame game, BufferedImage iPlayer, Color shirt, Color trouser) {
		BufferedImage iPlayerNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iPlayer.getWidth(), iPlayer.getHeight(), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iPlayerNew.getGraphics();

		g.drawImage(iPlayer, 0, 0, null);
		
		this.getChangedImage(iPlayerNew, shirt, 4, this.COLOR_ORIGINAL.length);
		this.getChangedImage(iPlayerNew, trouser, 0, 4);
		
		/**BufferedImage iShirt = this.getImage(game, iPlayerNew, shirt);
		for (int y = 0; y < iPlayerNew.getHeight(); y++) {
			for (int x = 0; x < iPlayerNew.getWidth(); x++) {
				Color c = new Color(iPlayerNew.getRGB(x, y));
				for (int color = 4; color < this.COLOR_CHANGE[0].length; color++) {
					if (c.equals(this.COLOR_CHANGE[0][color])) {
						iPlayerNew.setRGB(x, y, iShirt.getRGB(x, y));
					}
				}
			}
		}
		*/

		g.dispose();
		return iPlayerNew;
	}
	
	private void getChangedImage(BufferedImage iImageNew, Color changeColor, int start, int end) {
		for (int y = 0; y < iImageNew.getHeight(); y++) {
			for (int x = 0; x < iImageNew.getWidth(); x++) {
				Color c = new Color(iImageNew.getRGB(x, y));
				for (int color = start; color < end; color++) {
					if (c.equals(this.COLOR_ORIGINAL[color])) {
						int redMinus = changeColor.getRed() - this.COLOR_ORIGINAL[color].getRed();
						int greenMinus = changeColor.getGreen() - this.COLOR_ORIGINAL[color].getGreen();
						int blueMinus = changeColor.getBlue() - this.COLOR_ORIGINAL[color].getBlue();
						int alpha = changeColor.getAlpha();
						if (redMinus < 0) {
							redMinus = 0;
						}
						if (greenMinus < 0) {
							greenMinus = 0;
						}
						if (blueMinus < 0) {
							blueMinus = 0;
						}
						
						int redPlus = changeColor.getRed() + this.COLOR_ORIGINAL[color].getRed();
						int greenPlus = changeColor.getGreen() + this.COLOR_ORIGINAL[color].getGreen();
						int bluePlus = changeColor.getBlue() + this.COLOR_ORIGINAL[color].getBlue();
						if (redPlus > 254) {
							redPlus = 254;
						}
						if (greenPlus > 254) {
							greenPlus = 254;
						}
						if (bluePlus > 254) {
							bluePlus = 254;
						}
						
						int dif = changeColor.getRed() + changeColor.getGreen() + changeColor.getBlue();
						if (Math.abs(redPlus + greenPlus + bluePlus - dif) > Math.abs(redMinus + greenMinus + blueMinus - dif)) {
							iImageNew.setRGB(x, y, (new Color(redPlus, greenPlus, bluePlus, alpha)).getRGB());							
						} else {
							iImageNew.setRGB(x, y, (new Color(redMinus, greenMinus, blueMinus, alpha)).getRGB());
						}
						//System.out.println("r: "+red+" g: "+green+" b: "+blue);
					}
				}
			}
		}
	}
	
	protected BufferedImage getImage(ApoSoccerGame game, BufferedImage iImage, Color color) {
		BufferedImage iPlayerNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iImage.getWidth(), iImage.getHeight(), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iPlayerNew.getGraphics();
		
		g.drawImage(game.createImage(new FilteredImageSource (iImage.getSource(), new ApoDynamicFilter(1, color))), 0, 0, null);

		g.dispose();
		return iPlayerNew;
	}
}
