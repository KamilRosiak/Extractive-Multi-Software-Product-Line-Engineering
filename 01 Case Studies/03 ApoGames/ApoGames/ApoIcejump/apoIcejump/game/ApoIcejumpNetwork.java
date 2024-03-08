package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpNetwork extends ApoIcejumpModel implements Runnable {
	
	public static final String MENU = "menu";
	public static final String START = "start";
	public static final String SERVER = "start Server";
	public static final String CLIENT = "connect";
	
	private int time;
	
	private boolean bStart;
	
	private float changeY;
	
	private BufferedImage iBackgroundForNetwork;
	
	private String myLocalIP;
	
	private String myRouterIP;
	
	public ApoIcejumpNetwork(ApoIcejumpPanel game) {
		super(game);
	}
	
	public void start(boolean bFirst) {
		if (!bFirst) {
			this.bStart = false;
			this.changeY = ApoIcejumpConstants.GAME_HEIGHT;
			this.makeTheFish();
		} else {
			this.bStart = true;
			this.changeY = 0;
			this.time = (int)(Math.random() * 1000 + 500);
		}
		if (this.iBackgroundForNetwork == null) {
			this.iBackgroundForNetwork = this.makeAndGetBackgroundForNetwork(ApoIcejumpConstants.GAME_WIDTH/2 + 60, ApoIcejumpConstants.GAME_HEIGHT/2 + 80);
		}

		this.makeButtons();
		this.getGame().makeNewParticle();
		this.myLocalIP = "";
		this.myRouterIP = "";
		Thread t = new Thread(this);
		t.start();
	}
	
	private String getRouterIP() {
		String line = "";
		BufferedReader buffer = null;
		try {
			URL url = new URL("http://whatismyip.com/automation/n09230945.asp");
			InputStreamReader in = new InputStreamReader(url.openStream());
			buffer = new BufferedReader(in);

			line = buffer.readLine();
		} catch (IOException e) {
			line = "";
		} finally {
			try {
				if (buffer != null) {
					buffer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (line.length() > 0) {
			line = "router IP: "+line;
		}
		return line;
	}
	
	private String getLocalIP() {
		String ip = "local IP: ";
		try {
			InetAddress myIP = InetAddress.getLocalHost();
			ip = ip + myIP.getHostAddress();
		} catch (UnknownHostException e) {
			ip = ip + "cant find ip?";
		}
		return ip;
	}
	
	private BufferedImage makeAndGetBackgroundForNetwork(int width, int height) {
		BufferedImage iBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		
		g.dispose();
		
		return iBackground;
	}
	
	private void makeTheFish() {
		this.getGame().makeFish((int)(Math.random() * 20 + 25), false);
		this.time = (int)(Math.random() * 3000 + 500);
	}
	
	private void makeButtons() {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].init();
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	private void enterMenu() {
		
		this.getGame().setMenu(true, true);
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.enterMenu();
		} else if (button == KeyEvent.VK_ENTER) {

		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoIcejumpAnalysis.MENU)) {
			this.enterMenu();
		} else if (function.equals(ApoIcejumpAnalysis.START)) {
			this.getGame().setBNetwork(true);
			this.getGame().setGame();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	public void run() {
		this.myLocalIP = this.getLocalIP();
		this.myRouterIP = this.getRouterIP();
	}

	@Override
	public void think(int delta) {
		if (!this.bStart) {
			this.getGame().thinkFish(delta);
			this.changeY -= delta * ApoIcejumpConstants.CHANGE_MENU_TO_GAME;
			if (this.changeY <= 0) {
				this.changeY = 0;
				this.bStart = true;
			}
			this.getGame().thinkBird(delta);
			this.getGame().thinkWaves(delta);
			this.getGame().thinkBlocks(delta);
		}
		this.time -= delta;
		if (this.time <= 0) {
			this.time = (int)(Math.random() * 1000 + 200);
			int y = (int)(Math.random() * ApoIcejumpConstants.GAME_HEIGHT);
			if (Math.random() * 100 > 50) {
				this.getGame().makeFish(true, y);
			} else {
				this.getGame().makeFish(false, y);
			}
		}
		this.getGame().thinkFish(delta);
		this.thinkButtons(delta);
	}
	
	private void thinkButtons(int delta) {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			ApoButton button = this.getGame().getButtons()[i];
			if (button.isBVisible()) {
				button.setY(button.getY() + button.getVecY() * delta);
				if (Math.abs(button.getStartY() - button.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
					button.setVecY(-button.getVecY());
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (!this.bStart) {
			this.getGame().renderBackground(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderBird(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderParticle(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWavesWithBlocks(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWater(g, 0, (int)this.changeY);
			this.getGame().renderFish(g, 0, (int)this.changeY);
			
			this.renderNetwork(g, 0, (int)this.changeY);
			
			this.getGame().renderButtons(g, 0, (int)this.changeY);			
		} else {
			this.getGame().renderWater(g, 0, 0);
			this.getGame().renderFish(g, 0, 0);
			
			this.renderNetwork(g, 0, 0);
			
			this.getGame().renderButtons(g);
		}
	}
	
	private void renderNetwork(Graphics2D g, int changeX, int changeY) {
		int x = changeX + ApoIcejumpConstants.GAME_WIDTH/2 - this.iBackgroundForNetwork.getWidth()/2;
		int y = changeY + ApoIcejumpConstants.GAME_HEIGHT/2 - this.iBackgroundForNetwork.getHeight()/2;
		
		g.drawImage(this.iBackgroundForNetwork, x, y, null);
		
		g.setColor(Color.BLACK);
		g.setFont(ApoIcejumpConstants.FONT_PLAYER);
		String s = "Networkmode";
		this.drawString(g, s, changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 30));

		g.setFont(ApoIcejumpConstants.FONT_PLAYER_STATISTICS);
		if (this.myRouterIP.length() > 0) {
			this.drawString(g, this.myRouterIP, changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 70));
		}
		this.drawString(g, this.myLocalIP, changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 2, (int)(y + 95));
		
		g.drawRect(x + 5, y + 110, this.iBackgroundForNetwork.getWidth() - 10, 135);
		g.drawLine(ApoIcejumpConstants.GAME_WIDTH/2, y + 115, ApoIcejumpConstants.GAME_WIDTH/2, y + 240);
	}
	
	private void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}

	public static void main(String[] args) {
	}
}
