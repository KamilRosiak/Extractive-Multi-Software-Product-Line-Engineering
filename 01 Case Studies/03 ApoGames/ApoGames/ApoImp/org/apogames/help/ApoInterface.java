package org.apogames.help;

import java.awt.Graphics2D;

public interface ApoInterface {

	public void init();
	
	public void think(int delta);
	
	public void render(Graphics2D g);
	
}
