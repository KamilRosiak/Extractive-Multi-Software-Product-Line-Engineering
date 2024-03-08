package apoIcejump;

import java.awt.image.BufferedImage;

public class ApoIcejumpImageContainer {

	public static BufferedImage iSnowflakeOne;
	public static BufferedImage iSnowflakeTwo;
	
	public static void init(ApoIcejumpImages images) {
		ApoIcejumpImageContainer.iSnowflakeOne = images.getImage("images/snowflake_1.gif", false);
		ApoIcejumpImageContainer.iSnowflakeTwo = images.getImage("images/snowflake_2.gif", false);
	}
}
