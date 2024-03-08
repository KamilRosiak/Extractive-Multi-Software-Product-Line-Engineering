package apoRelax.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apogames.ApoConstants;

import apoRelax.level.ApoRelaxLevel;
import apoRelax.level.ApoRelaxLevelGeneration;

public class ApoRelaxRandomLevels {

	public ApoRelaxRandomLevels() {
	}
	
	public ApoRelaxLevel getRandomLevel() {
		try {
			if ((ApoConstants.B_APPLET) || (!ApoConstants.B_APPLET)) {
				return ApoRelaxLevelGeneration.getRandomLevel(ApoRelaxLevelGeneration.getRandomImage());
			} else {
				try	{
					String myUrl = "http://www.zufallsbild.com/";
					URL url = new URL(myUrl);
					Reader is = new InputStreamReader(url.openStream());
					BufferedReader in = new BufferedReader(is);
					String imageString = ".jpg";
					for (String s; (s = in.readLine()) != null;) {
						int index = s.indexOf(".jpg");
						if (index != -1) {
							while (index > 0) {
								char c = s.charAt(index - 1);
								if (c == 34) {
									index = -1;
									imageString = myUrl + imageString;
								} else {
									imageString = c + imageString;
								}
								index -= 1;
							}
							System.out.println(imageString);
							break;
						}
					}
					BufferedImage iImage = ImageIO.read(new URL(imageString));
					BufferedImage iLevel = ApoRelaxLevelGeneration.getImage(iImage);
					in.close();
					return ApoRelaxLevelGeneration.getRandomLevel(iLevel);
				} catch ( MalformedURLException e ) {
					System.out.println("MalformedURLException: " + e);
				} catch (IOException e) {
					System.out.println( "IOException: " + e );
				}		
				return null;
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
