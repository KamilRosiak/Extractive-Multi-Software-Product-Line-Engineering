package apoIcejump.game;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoEntity;
import org.apogames.help.ApoHelp;

import apoIcejump.ApoIcejumpClassLoader;
import apoIcejump.ApoIcejumpComponent;
import apoIcejump.ApoIcejumpConstants;
import apoIcejump.ApoIcejumpImageContainer;

import apoIcejump.ai.ApoIcejumpAI;
import apoIcejump.entity.ApoIcejumpBird;
import apoIcejump.entity.ApoIcejumpBlock;
import apoIcejump.entity.ApoIcejumpFish;
import apoIcejump.entity.ApoIcejumpGoodie;
import apoIcejump.entity.ApoIcejumpParticle;
import apoIcejump.entity.ApoIcejumpPlayer;
import apoIcejump.entity.ApoIcejumpWaves;

public class ApoIcejumpPanel extends ApoIcejumpComponent {

	private static final long serialVersionUID = 3446351722587948000L;

	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoIcejumpButtons buttons;
	
	private ApoIcejumpModel model;
	
	private ApoIcejumpMenu menu;
	
	private ApoIcejumpTutorial tutorial;
	
	private ApoIcejumpAnalysis analysis;

	private ApoIcejumpCredits credits;
	
	private ApoIcejumpNetwork network;

	private ApoIcejumpSimulate simulate;
	
	private ApoIcejumpGame game;
	
	private BufferedImage[] iKeyImages;
	
	private BufferedImage iBackground, iWater, iFish, iBird, iParticle, iSpeechBubble, 
						  iGoodieFire, iGoodieIceLittle, iGoodieIceBig, iGoodie, iFirePlayer,
						  iIce, iGoodieSlower, iGoodieSpring, iGoodieFaster;
	
	private ArrayList<ApoIcejumpFish> fish;
	
	private ArrayList<ApoIcejumpBird> bird;
	
	private ApoIcejumpPlayer[] players;
	
	private ApoIcejumpWaves[] waves;
	
	private ArrayList<ApoIcejumpBlock> backBlocks;

	private ArrayList<ApoIcejumpBlock> frontBlocks;
	
	private ArrayList<ApoIcejumpParticle> particles;

	private ArrayList<ApoIcejumpGoodie> goodies;
	
	private Random random;
	
	private ArrayList<ApoEntity> snowflakes;
	
	private int snowFlakesTime;
	
	private boolean bSnowShow;
	
	private BufferedImage iCountdown;
	
	private boolean bCountdown;
	
	private int countdownTime;
	
	public ApoIcejumpPanel() {
		super();
	}
	
	public void init() {
		super.init();
		super.setBFPS(false);
		
		this.bSnowShow = true;
		
		if (this.iBackground == null) {
			this.iBackground = this.makeAndGetBackground();
		}
		if (this.iWater == null) {
			this.iWater = this.makeAndGetWater();
		}
		if (this.iParticle == null) {
			this.iParticle = this.getImages().getImage("images/particle.png", false);
		}
		if (this.iSpeechBubble == null) {
			this.iSpeechBubble = this.getImages().getImage("images/speechbubble.png", false);
		}
		if (this.iGoodieFire == null) {
			this.iGoodieFire = this.getImages().getImage("images/goodie_fire.png", false);
		}
		if (this.iGoodieIceLittle == null) {
			this.iGoodieIceLittle = this.getImages().getImage("images/goodie_iceblock3.png", false);
		}
		if (this.iGoodieIceBig == null) {
			this.iGoodieIceBig = this.getImages().getImage("images/goodie_iceblock6.png", false);
		}
		if (this.iGoodieSlower == null) {
			this.iGoodieSlower = this.getImages().getImage("images/goodie_slower.png", false);
		}
		if (this.iGoodieFaster == null) {
			this.iGoodieFaster = this.getImages().getImage("images/goodie_faster.png", false);
		}
		if (this.iGoodie == null) {
			this.iGoodie = this.makeAndGetGoodie();
		}
		if (this.iFirePlayer == null) {
			this.iFirePlayer = this.getImages().getImage("images/firePlayer.png", false);
		}
		if (this.iIce == null) {
			this.iIce = this.getImages().getImage("images/icePlayer.png", false);
		}
		if (this.iGoodieSpring == null) {
			this.iGoodieSpring = this.getImages().getImage("images/spring.png", false);
		}
		if (this.iFish == null) {
			this.iFish = this.drawImageOnImage(this.getImages().getImage("images/fish.png", false));
		}
		if (this.iBird == null) {
			this.iBird = this.drawImageOnImage(this.getImages().getImage("images/bird.png", false));
		}
		if (this.iKeyImages == null) {
			this.iKeyImages = new BufferedImage[4];
			this.iKeyImages[0] = this.drawImageOnImage(this.getImages().getImage("images/button_left.gif", false));
			this.iKeyImages[1] = this.drawImageOnImage(this.getImages().getImage("images/button_right.gif", false));
			this.iKeyImages[2] = this.drawImageOnImage(this.getImages().getImage("images/button_a.gif", false));
			this.iKeyImages[3] = this.drawImageOnImage(this.getImages().getImage("images/button_d.gif", false));
		}
		if (this.players == null) {
			this.players = new ApoIcejumpPlayer[2];
			//Player One
			this.players[0] = new ApoIcejumpPlayer(null, ApoIcejumpConstants.GAME_WIDTH*1/4 - ApoIcejumpConstants.PLAYER_WIDTH/2, ApoIcejumpConstants.GAME_HEIGHT/2 - 50, ApoIcejumpConstants.PLAYER_WIDTH, ApoIcejumpConstants.PLAYER_HEIGHT, new Color(0, 0, 255, 200), this.iSpeechBubble);
			
			//Player Two
			this.players[1] = new ApoIcejumpPlayer(null, ApoIcejumpConstants.GAME_WIDTH*3/4 - ApoIcejumpConstants.PLAYER_WIDTH/2, ApoIcejumpConstants.GAME_HEIGHT/2 - 50, ApoIcejumpConstants.PLAYER_WIDTH, ApoIcejumpConstants.PLAYER_HEIGHT, new Color(255, 0, 0, 200), this.iSpeechBubble);
		}
		
		if (this.waves == null) {
			this.waves = new ApoIcejumpWaves[3];
			
			this.waves[2] = new ApoIcejumpWaves(this.makeAndGetWave(ApoIcejumpConstants.GAME_WIDTH + 160, 38, new Color(0, 182, 221)), 0, ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 28, ApoIcejumpConstants.GAME_WIDTH + 160, 38);
			this.waves[1] = new ApoIcejumpWaves(this.makeAndGetWave(ApoIcejumpConstants.GAME_WIDTH + 160, 38, new Color(0, 209, 254)), 0, ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 35, ApoIcejumpConstants.GAME_WIDTH + 160, 38);
			this.waves[0] = new ApoIcejumpWaves(this.makeAndGetWave(ApoIcejumpConstants.GAME_WIDTH + 160, 38, new Color(100, 228, 254)), 0, ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 42, ApoIcejumpConstants.GAME_WIDTH + 160, 38);

		}
		
		this.frontBlocks = new ArrayList<ApoIcejumpBlock>();
		this.backBlocks = new ArrayList<ApoIcejumpBlock>();
		
		this.fish = new ArrayList<ApoIcejumpFish>();
		this.bird = new ArrayList<ApoIcejumpBird>();
		this.goodies = new ArrayList<ApoIcejumpGoodie>();
		
		this.makeNewParticle();
		
		if (this.snowflakes == null) {
			this.snowflakes = new ArrayList<ApoEntity>();
			int time = 30000;
			for (int i = 0; i < time; i += ApoIcejumpConstants.WAIT_TIME_THINK) {
				this.thinkSnowflakes(ApoIcejumpConstants.WAIT_TIME_THINK);
			}
		}
		if (this.buttons == null) {
			this.buttons = new ApoIcejumpButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoIcejumpMenu(this);
		}
		if (this.tutorial == null) {
			this.tutorial = new ApoIcejumpTutorial(this);
		}
		if (this.analysis == null) {
			this.analysis = new ApoIcejumpAnalysis(this);
		}
		if (this.credits == null) {
			this.credits = new ApoIcejumpCredits(this);
		}
		if (this.network == null) {
			this.network = new ApoIcejumpNetwork(this);
		}
		if (this.simulate == null) {
			this.simulate = new ApoIcejumpSimulate(this);
		}
		if (this.game == null) {
			this.game = new ApoIcejumpGame(this);
		}
		
		this.setMenu(true, false);
		this.setAIForTutorial();
		
		this.loadProperties();
	}
	
	private void loadProperties() {
		ApoIcejumpConstants.loadProperties();
		
		super.setBFPS(ApoIcejumpConstants.FPS);
		this.loadPropertiesPlayer(ApoIcejumpConstants.PLAYER_ONE, ApoIcejumpConstants.AI_LEFT);
		this.loadPropertiesPlayer(ApoIcejumpConstants.PLAYER_TWO, ApoIcejumpConstants.AI_RIGHT);
	}
	
	private void loadPropertiesPlayer(int player, String playerPath) {
		if ((playerPath == null) || (playerPath.length() <= 0)) {
			return;
		}
		int playerInt = -1;
		try {
			playerInt = Integer.parseInt(playerPath);
		} catch (NumberFormatException ex) {
			playerInt = -1;
		}
		if (playerInt >= 0) {
			this.menu.loadPlayerAI(player, playerInt);
		} else {
			this.loadPlayer(player, playerPath);
		}
	}
	
	public boolean isBCountdown() {
		return this.bCountdown;
	}

	public void setBCountdown(boolean countdown) {
		this.bCountdown = countdown;
	}

	public int getCountdownTime() {
		return this.countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}

	private BufferedImage drawImageOnImage(BufferedImage iLoad) {
		BufferedImage iNew = new BufferedImage(iLoad.getWidth(), iLoad.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)iNew.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(iLoad, 0, 0, null);
		g.dispose();
		
		return iNew;
	}
	
	public BufferedImage makeAndGetCountdown(int width, int height) {
		BufferedImage iCountdown = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)iCountdown.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width - 1, height - 1);
		
		int whiteHeight = 20;
		int whiteWidth = 5;
		g.setColor(Color.WHITE);
		g.fillRect(whiteWidth, whiteHeight, width - 1 - 2 * whiteWidth, height - 1 - 2 * whiteHeight);
		
		int filmWidth = 7;
		int filmHeight = 14;
		int filmY = (whiteHeight - filmHeight) / 2;
		for (int i = 0; i < width / (2 * filmWidth); i++) {
			g.fillRoundRect(filmWidth + i * 2 * filmWidth, filmY, filmWidth, filmHeight, 8, 8);
			g.fillRoundRect(filmWidth + i * 2 * filmWidth, height - filmY - filmHeight, filmWidth, filmHeight, 8, 8);
		}
		g.setColor(Color.GRAY);
		g.drawLine(whiteWidth, height/2, width - whiteWidth, height/2);
		g.drawLine(width / 2, whiteHeight, width / 2, height - whiteHeight);
		int ovalRadius = height - 2 * whiteHeight - 4;
		g.drawOval((width - ovalRadius)/2, (height - ovalRadius)/2, ovalRadius, ovalRadius);
		ovalRadius = ovalRadius - 6;
		g.drawOval((width - ovalRadius)/2, (height - ovalRadius)/2, ovalRadius, ovalRadius);
		
		g.dispose();
		return iCountdown;
	}
	
	public BufferedImage[] getIKeyImages() {
		return this.iKeyImages;
	}

	public void setBNetwork(boolean bNetwork) {
		this.game.setNetwork(bNetwork);
	}
	
	public BufferedImage getIIce() {
		return this.iIce;
	}

	public BufferedImage getIFirePlayer() {
		return this.iFirePlayer;
	}

	protected void makeNewParticle() {
		this.particles = new ArrayList<ApoIcejumpParticle>();
		this.goodies = new ArrayList<ApoIcejumpGoodie>();
	}
	
	private BufferedImage makeAndGetBackground() {
		BufferedImage iBackground = new BufferedImage(ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Paint paint = g.getPaint();
		GradientPaint gradientPaint = new GradientPaint(iBackground.getWidth()/2, 0, new Color(100, 150, 200), iBackground.getWidth()/2, iBackground.getHeight(), new Color(160, 225, 254));
		g.setPaint(gradientPaint);
		g.fillRect(0, 0, iBackground.getWidth(), iBackground.getHeight());
		g.setPaint(paint);
		
		g.dispose();
		return iBackground;
	}
	
	private BufferedImage makeAndGetWater() {
		BufferedImage iWater = new BufferedImage(ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT + ApoIcejumpConstants.WATER_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)iWater.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(this.getImages().getImage("images/underwater.png", false), 0, 0, null);
		
		g.dispose();
		return iWater;
	}
	
	private BufferedImage makeAndGetGoodie() {
		BufferedImage iGoodie = new BufferedImage(ApoIcejumpConstants.GOODIE_WIDTH, ApoIcejumpConstants.GOODIE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)iGoodie.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Polygon p = new Polygon();
		p.addPoint(1, iGoodie.getHeight()/2);
		p.addPoint(iGoodie.getWidth()/2, 1);
		p.addPoint(iGoodie.getWidth() - 1, iGoodie.getHeight()/2);
		p.addPoint(iGoodie.getWidth()/2, iGoodie.getHeight() - 1);
		g.setColor(new Color(255, 255, 255, 120));
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
		
		g.dispose();
		return iGoodie;
	}
	
	protected BufferedImage makeAndGetWave(int width, int height, Color color) {
		BufferedImage iWave = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iWave.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(color);
		for( int i = 0; i < width; i++) {
            g.drawRect(i, (int)(Math.sin(Math.toRadians(i << 3)) * 6D) + 20, 1, height);
		}
		
		g.dispose();
		return iWave;
	}
	
	protected BufferedImage getFish(int fish, boolean bLeft) {
		BufferedImage iFish = this.iFish.getSubimage(0, fish * this.iFish.getHeight()/3, this.iFish.getWidth(), this.iFish.getHeight()/3);
		if (!bLeft) {
			BufferedImage iFishMirror = new BufferedImage(iFish.getWidth(), iFish.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = (Graphics2D)iFishMirror.getGraphics();
			g.drawImage(iFish, 0, 0, iFish.getWidth(), iFish.getHeight(), iFish.getWidth(), 0, 0, iFish.getHeight(), null);
			g.dispose();
			return iFishMirror;
		}
		return iFish;
	}
	
	protected void makeGoodie() {
		int x = this.random.nextInt(ApoIcejumpConstants.GAME_WIDTH - ApoIcejumpConstants.GOODIE_WIDTH);
		int y = - this.random.nextInt(30) + ApoIcejumpConstants.GAME_HEIGHT/2;
		int randomGoodie = this.random.nextInt(6);
		if (randomGoodie == ApoIcejumpConstants.GOODIE_FIRE) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieFire));
		} else if (randomGoodie == ApoIcejumpConstants.GOODIE_ICE_LITTLE) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieIceLittle));
		} else if (randomGoodie == ApoIcejumpConstants.GOODIE_ICE_BIG) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieIceBig));
		} else if (randomGoodie == ApoIcejumpConstants.GOODIE_SLOWER) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieSlower));
		} else if (randomGoodie == ApoIcejumpConstants.GOODIE_HIGHER) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieSpring));
		} else if (randomGoodie == ApoIcejumpConstants.GOODIE_FASTER) {
			this.goodies.add(new ApoIcejumpGoodie(this.iGoodie, x, y, randomGoodie, this.iGoodieFaster));
		}
	}
	
	protected void makeFish(boolean bLeft, int y) {
		int fish = (int)(Math.random() * 3);
		if (fish >= 3) {
			fish = 1;
		}
		BufferedImage iFish = this.getFish(fish, bLeft);
		if (bLeft) {
			this.fish.add(new ApoIcejumpFish(iFish, -(iFish.getWidth() / ApoIcejumpConstants.FISH_TILES), y, iFish.getWidth() / ApoIcejumpConstants.FISH_TILES, iFish.getHeight(), ApoIcejumpConstants.FISH_TILES, ApoIcejumpConstants.FISH_TIME, bLeft));
		} else {
			this.fish.add(new ApoIcejumpFish(iFish, ApoIcejumpConstants.GAME_WIDTH, y, iFish.getWidth() / ApoIcejumpConstants.FISH_TILES, iFish.getHeight(), ApoIcejumpConstants.FISH_TILES, ApoIcejumpConstants.FISH_TIME, bLeft));			
		}
	}
	
	protected void makeFish(int count, boolean bGame) {
		this.fish = new ArrayList<ApoIcejumpFish>();
		for (int i = 0; i < count; i++) {
			int y = (int)(Math.random() * ApoIcejumpConstants.GAME_HEIGHT);
			if (bGame) {
				y = ApoIcejumpConstants.GAME_HEIGHT - (int)(Math.random() * ApoIcejumpConstants.WATER_HEIGHT);
			}
			if (Math.random() * 100 > 50) {
				this.makeFish(true, y);
			} else {
				this.makeFish(false, y);
			}
		}
	}
	
	public int getTime() {
		return this.game.getTime();
	}
	
	protected BufferedImage getBird(int bird, boolean bLeft) {
		BufferedImage iBird = this.iBird.getSubimage(0, bird * this.iBird.getHeight()/2, this.iBird.getWidth(), this.iBird.getHeight()/2);
		if (!bLeft) {
			BufferedImage iBirdMirror = new BufferedImage(iBird.getWidth(), iBird.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = (Graphics2D)iBirdMirror.getGraphics();
			g.drawImage(iBird, 0, 0, iBird.getWidth(), iBird.getHeight(), iBird.getWidth(), 0, 0, iBird.getHeight(), null);
			g.dispose();
			return iBirdMirror;
		}
		return iBird;
	}
	
	private void makeBird(boolean bLeft, int y) {
		int bird = (int)(Math.random() * 2);
		if (bird >= 2) {
			bird = 1;
		}
		BufferedImage iBird = this.getBird(bird, bLeft);
		if (bLeft) {
			this.bird.add(new ApoIcejumpBird(iBird, -(iBird.getWidth() / ApoIcejumpConstants.BIRD_TILES), y, iBird.getWidth() / ApoIcejumpConstants.BIRD_TILES, iBird.getHeight(), ApoIcejumpConstants.BIRD_TILES, ApoIcejumpConstants.BIRD_TIME, bLeft));
		} else {
			this.bird.add(new ApoIcejumpBird(iBird, ApoIcejumpConstants.GAME_WIDTH, y, iBird.getWidth() / ApoIcejumpConstants.BIRD_TILES, iBird.getHeight(), ApoIcejumpConstants.BIRD_TILES, ApoIcejumpConstants.BIRD_TIME, bLeft));			
		}
	}
	
	protected void makeBird(int count, boolean bNew) {
		if (bNew) {
			this.bird = new ArrayList<ApoIcejumpBird>();
		}
		for (int i = 0; i < count; i++) {
			int y = (int)(Math.random() * (ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 160));
			if (Math.random() * 100 > 50) {
				this.makeBird(true, y);
			} else {
				this.makeBird(false, y);
			}
		}
	}
	
	protected void makeBlocks(long randomInt) {
		this.random = new Random(randomInt);
		
		int width = ApoIcejumpConstants.ICEBLOCK_WIDTH;
		int height = ApoIcejumpConstants.ICEBLOCK_HEIGHT;
		BufferedImage iBlock = this.makeAndGetIceblock(width, height);
		int count = this.random.nextInt(15) + 5;
		this.backBlocks = new ArrayList<ApoIcejumpBlock>();
		for (int i = 0; i < count; i++) {
			int x = this.random.nextInt(ApoIcejumpConstants.GAME_WIDTH - ApoIcejumpConstants.ICEBLOCK_WIDTH);
			int y = ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 32;
			float vecX = this.random.nextFloat() * 0.01f - 0.005f;
			if (Math.abs(vecX) < 0.001) {
				if (vecX < 0) {
					vecX = -0.001f;
				} else {
					vecX = 0.001f;
				}
			}
			float vecY = this.random.nextFloat() * 0.005f - 0.0025f;
			this.backBlocks.add(new ApoIcejumpBlock(iBlock, x, y, width, height, vecX, vecY));
		}
		count = this.random.nextInt(15) + 5;
		this.frontBlocks = new ArrayList<ApoIcejumpBlock>();
		for (int i = 0; i < count; i++) {
			int x = this.random.nextInt(ApoIcejumpConstants.GAME_WIDTH - ApoIcejumpConstants.ICEBLOCK_WIDTH);
			int y = ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 25;
			float vecX = this.random.nextFloat() * 0.01f - 0.005f;
			if (Math.abs(vecX) < 0.001) {
				if (vecX < 0) {
					vecX = -0.001f;
				} else {
					vecX = 0.001f;
				}
			}
			float vecY = this.random.nextFloat() * 0.005f - 0.0025f;
			if (Math.abs(vecY) < 0.0005) {
				if (vecY < 0) {
					vecY = -0.0005f;
				} else {
					vecY = 0.0005f;
				}
			}
			this.frontBlocks.add(new ApoIcejumpBlock(iBlock, x, y, width, height, vecX, vecY));
		}
	}
	
	public void makeBlocksInGame(int count) {
		int width = ApoIcejumpConstants.ICEBLOCK_WIDTH;
		int height = ApoIcejumpConstants.ICEBLOCK_HEIGHT;
		BufferedImage iBlock = this.makeAndGetIceblock(width, height);
		int back = this.random.nextInt(count) + 1;
		int front = count - back;
		for (int i = 0; i < back; i++) {
			int x = this.random.nextInt(ApoIcejumpConstants.GAME_WIDTH - ApoIcejumpConstants.ICEBLOCK_WIDTH);
			int y = ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 32;
			float vecX = this.random.nextFloat() * 0.01f - 0.005f;
			if (Math.abs(vecX) < 0.001) {
				if (vecX < 0) {
					vecX = -0.001f;
				} else {
					vecX = 0.001f;
				}
			}
			float vecY = this.random.nextFloat() * 0.005f - 0.0025f;
			this.backBlocks.add(new ApoIcejumpBlock(iBlock, x, y, width, height, vecX, vecY));
		}
		for (int i = 0; i < front; i++) {
			int x = this.random.nextInt(ApoIcejumpConstants.GAME_WIDTH - ApoIcejumpConstants.ICEBLOCK_WIDTH);
			int y = ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT - 25;
			float vecX = this.random.nextFloat() * 0.01f - 0.005f;
			if (Math.abs(vecX) < 0.001) {
				if (vecX < 0) {
					vecX = -0.001f;
				} else {
					vecX = 0.001f;
				}
			}
			float vecY = this.random.nextFloat() * 0.005f - 0.0025f;
			if (Math.abs(vecY) < 0.0005) {
				if (vecY < 0) {
					vecY = -0.0005f;
				} else {
					vecY = 0.0005f;
				}
			}
			this.frontBlocks.add(new ApoIcejumpBlock(iBlock, x, y, width, height, vecX, vecY));
		}
	}
	
	protected BufferedImage makeAndGetIceblock(int width, int height) {
		BufferedImage iBlock = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iBlock.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(255, 255, 255, 140));
		g.fillRoundRect(0, 0, iBlock.getWidth() - 1, iBlock.getHeight() - 1, 7, 7);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, iBlock.getWidth() - 1, iBlock.getHeight() - 1, 7, 7);
		
		g.dispose();
		return iBlock;
	}
	
	public void loadPlayer(int player, String s) {
		String res = null;
		String source = null;
		String playerName = null;
		String className = null;
		if (s == null) {
			if (this.getFileChooser().showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				res = this.getFileChooser().getSelectedFile().getPath();
				className = res.substring(res.lastIndexOf("."));
				res = res.substring( 0, res.lastIndexOf("."));
				source = res.substring( 0, res.lastIndexOf( File.separator ) ) + File.separator;
				res = res.substring( res.lastIndexOf( File.separator ) + 1 );
			} else {
				return;
			}
			if ((res == null) || (res.length() <= 0)) {
				return;    	
			}
			playerName	= res;
		} else {	
			res	= s;
	    	playerName = res.substring(res.lastIndexOf("\\") + 1, res.lastIndexOf("."));
			source = res.substring(0,res.lastIndexOf("\\") + 1);
			className = res.substring(res.lastIndexOf("."));
		}
		this.loadPlayer(player, source, playerName, className);
	}
	
	/**
	 * laed einen Spieler, wobei übergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehört
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer(int player, String path, String classname) {
		return this.loadPlayer(player, path, classname, ".class");
	}
	
	/**
	 * laed einen Spieler, wobei übergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehört
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer(int player, String path, String classname, String classN ) {
    	ApoIcejumpAI apoIcejumpAI = null;
//    	System.out.println(path+" "+classname+" "+classN);
		if ((classN != null) && (classN.length() > 0) && (classN.indexOf(".class") != -1)) {
			if (!ApoConstants.B_APPLET) {
				apoIcejumpAI = (ApoIcejumpAI)(new ApoIcejumpClassLoader(path, classname).getAI());
			}
		}
		try {
        	if (apoIcejumpAI != null) {
       			this.getPlayers()[player].setAI(apoIcejumpAI, this.getImages());
       			if (player == ApoIcejumpConstants.PLAYER_ONE) {
       				ApoIcejumpConstants.AI_LEFT = path + classname + classN;
       			} else if (player == ApoIcejumpConstants.PLAYER_TWO) {
       				ApoIcejumpConstants.AI_RIGHT = path + classname + classN;
       			}
       			ApoIcejumpConstants.saveProperties();
        	}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public final ApoIcejumpPlayer[] getPlayers() {
		return this.players;
	}

	public ArrayList<ApoIcejumpBlock> getBackBlocks() {
		return this.backBlocks;
	}

	public ArrayList<ApoIcejumpBlock> getFrontBlocks() {
		return this.frontBlocks;
	}

	public ArrayList<ApoIcejumpBird> getBirds() {
		return this.bird;
	}

	public ArrayList<ApoIcejumpGoodie> getGoodies() {
		return this.goodies;
	}

	public void setMenu(boolean bFirst, boolean bAnalysis) {
		this.model = this.menu;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_MENU);
		this.menu.start(bFirst, bAnalysis);
	}

	public void setGame() {
		this.setGame(-1);
	}
	
	public void setGame(long time) {
		this.model = this.game;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_GAME);
		this.game.start(false, time);
	}
	
	public void setTutorial() {
		this.model = this.tutorial;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_TUTORIAL);
		this.tutorial.start();
	}
	
	public void setAnalysis(boolean bFirst) {
		this.model = this.analysis;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_ANALYSIS);
		this.analysis.start(bFirst);
	}
	
	public void setNetwork(boolean bFirst) {
		this.model = this.network;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_NETWORK);
		this.network.start(bFirst);
	}
	
	public void setSimulate(boolean bFirst) {
		this.model = this.simulate;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_SIMULATE);
		this.simulate.start();
	}
	
	public void setCredits() {
		this.model = this.credits;
		
		this.setButtonVisible(ApoIcejumpConstants.BUTTON_CREDITS);
		this.credits.start();
	}
	
	private void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
			this.getButtons()[7].setBVisible(false);
			this.getButtons()[10].setBVisible(false);
			this.getButtons()[19].setBVisible(false);
			this.getButtons()[25].setBVisible(false);
			this.getButtons()[28].setBVisible(false);
		} else {
			this.getButtons()[19].setBVisible(false);
		}
	}

	public ApoIcejumpMenu getMenu() {
		return this.menu;
	}

	/**
	 * Methode zum Starten des Spiels
	 */
	public void startGame() {
		this.setGame();
	}
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (this.model != null) {
			this.model.keyPressed(e);
		}
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_F) {
			super.setBFPS(!super.isBFPS());
		} else if (button == KeyEvent.VK_G) {
			this.bSnowShow = !this.bSnowShow;
		}
		if (this.model != null) {
			this.model.keyButtonReleased(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (this.model != null) {
			this.model.mouseMoved(e);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (this.model != null) {
			this.model.mouseDragged(e);
		}
	}
	
	public void setAIForTutorial() {
		this.menu.loadPlayerAI(ApoIcejumpConstants.PLAYER_ONE, ApoIcejumpConstants.AI_HUMAN);
		this.menu.loadPlayerAI(ApoIcejumpConstants.PLAYER_TWO, ApoIcejumpConstants.AI_EASY);
	}
	
	@Override
	public void loadLevel(String s) {
	}

	@Override
	public void saveLevel(String s) {
		
	}
	
	public void think(int delta) {
		long t = System.nanoTime();
		super.think(delta);
		this.thinkSnowflakes(delta);
		if (this.model != null) {
			this.model.think(delta);
		}
		this.thinkTime = (int) (System.nanoTime() - t);
	}
	
	private void thinkSnowflakes(int delta) {
		for (int i = this.snowflakes.size() - 1; i >= 0; i--) {
			ApoEntity snowflake = this.snowflakes.get(i);
			snowflake.setX(snowflake.getX() + snowflake.getVecX() * delta);
			snowflake.setY(snowflake.getY() + snowflake.getVecY() * delta);
			if (snowflake.getX() + snowflake.getWidth() < 0) {
				this.snowflakes.remove(i);
			} else if (snowflake.getX() > ApoIcejumpConstants.GAME_WIDTH) {
				this.snowflakes.remove(i);
			} else if (snowflake.getY() > ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT) {
				this.snowflakes.remove(i);
			}
		}
		this.snowFlakesTime -= delta;
		if (this.snowFlakesTime <= 0) {
			this.snowFlakesTime = ApoHelp.getRandomValue(150, 50);
			BufferedImage iSnowflake = null;
			if (ApoHelp.getRandomValue(100, 0) > 50) {
				iSnowflake = ApoIcejumpImageContainer.iSnowflakeOne;
			} else {
				iSnowflake = ApoIcejumpImageContainer.iSnowflakeTwo;
			}
			int x = ApoHelp.getRandomValue(ApoIcejumpConstants.GAME_WIDTH + iSnowflake.getWidth(), -iSnowflake.getWidth());
			float vecX = (float)(Math.random() * 0.005);
			float vecY = 0.01f + (float)(Math.random() * 0.01);
			ApoEntity entity = new ApoEntity(iSnowflake, x, -10, iSnowflake.getWidth(), iSnowflake.getHeight());
			entity.setVecX(vecX);
			entity.setVecY(vecY);
			this.snowflakes.add(entity);
		}
	}

	protected void thinkFish(int delta) {
		for (int i = this.fish.size() - 1; i >= 0; i--) {
			this.fish.get(i).think(delta);
			if (!this.fish.get(i).isBVisible()) {
				this.fish.remove(i);
			}
		}
	}
	
	protected void thinkBird(int delta) {
		for (int i = this.bird.size() - 1; i >= 0; i--) {
			this.bird.get(i).think(delta);
			if (!this.bird.get(i).isBVisible()) {
				this.bird.remove(i);
			}
		}
	}
	
	protected void thinkParticles(int delta) {
		for (int i = this.particles.size() - 1; i >= 0; i--) {
			this.particles.get(i).think(delta);
			if (!this.particles.get(i).isBVisible()) {
				this.particles.remove(i);
			}
		}
	}
	
	protected void thinkGoodies(int delta) {
		for (int i = this.goodies.size() - 1; i >= 0; i--) {
			this.goodies.get(i).think(delta);
			if (!this.goodies.get(i).isBVisible()) {
				this.goodies.remove(i);
			}
		}
	}
	
	protected void thinkWaves(int delta) {
		for (int i = 0; i < this.waves.length; i++) {
			this.waves[i].think(delta);
		}
	}
	
	protected boolean thinkPlayers(int delta, boolean bTutorial) {
		boolean bVisible = false;
		for (int i = 0; i < this.players.length; i++) {
			try {
				this.players[i].thinkAI(delta, this);
			} catch (Throwable th) {
				this.players[i].setBVisible(false);
				System.out.println("Player: "+this.players[i].getName()+"["+(i+1)+"]"+"throws an Exception: "+th.getMessage());
			}
		}
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].think(delta, this);
			if (!bTutorial) {
				if (!this.players[i].isBVisible()) {
					this.setAnalysis(false);
					return true;
				}
			}
		}
		return bVisible;
	}
	
	protected void thinkBlocks(int delta) {
		for (int i = this.frontBlocks.size() - 1; i >= 0; i--) {
			this.frontBlocks.get(i).think(delta);
			if (!this.frontBlocks.get(i).isBVisible()) {
				this.particles.add(new ApoIcejumpParticle(this.iParticle, this.frontBlocks.get(i)));
				this.frontBlocks.remove(i);
			}
		}
		for (int i = this.backBlocks.size() - 1; i >= 0; i--) {
			this.backBlocks.get(i).think(delta);
			if (!this.backBlocks.get(i).isBVisible()) {
				this.particles.add(new ApoIcejumpParticle(this.iParticle, this.backBlocks.get(i)));
				this.backBlocks.remove(i);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		long t = System.nanoTime();
		g.setClip(0, 0, ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT);
		if (this.model != null) {
			this.model.render(g);
		}
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	protected void renderSnowflakes(Graphics2D g, int changeX, int changeY) {
		if (this.bSnowShow) {
			for (int i = this.snowflakes.size() - 1; i >= 0; i--) {
				this.snowflakes.get(i).render(g, changeX, changeY);
			}
		}
	}
	
	protected void renderBackground(Graphics2D g, int changeX, int changeY) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, changeX, changeY, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT);
		}
	}
	
	protected void renderWater(Graphics2D g, int changeX, int changeY) {
		if (this.iWater != null) {
			g.drawImage(this.iWater, changeX, changeY + ApoIcejumpConstants.GAME_HEIGHT - this.iWater.getHeight(), null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT);
		}
	}
	
	protected void renderFish(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.fish.size(); i++) {
			this.fish.get(i).render(g, changeX, changeY);
		}
	}
	
	protected void renderBird(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.bird.size(); i++) {
			this.bird.get(i).render(g, changeX, changeY);
		}
	}
	
	protected void renderParticle(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.particles.size(); i++) {
			this.particles.get(i).render(g, changeX, changeY);
		}
	}
	
	protected void renderGoodies(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.goodies.size(); i++) {
			this.goodies.get(i).render(g, changeX, changeY);
		}
	}
	
	protected void renderPlayer(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].render(g, changeX, changeY);
		}
	}
	
	protected void renderWaves(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.waves.length; i++) {
			this.waves[i].render(g, changeX, changeY);
		}
	}
	
	protected void renderWavesWithBlocks(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.waves.length; i++) {
			this.waves[i].render(g, changeX, changeY);
			if (i == 0) {
				for (int blocks = 0; blocks < this.backBlocks.size(); blocks++) {
					this.backBlocks.get(blocks).render(g, changeX, changeY);
				}
			} else if (i == 1) {
				for (int blocks = 0; blocks < this.frontBlocks.size(); blocks++) {
					this.frontBlocks.get(blocks).render(g, changeX, changeY);
				}
			}
		}
	}
	
	/**
	 * rendert die Anzeige f�r die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isBFPS()) {
			g.setColor(Color.RED);
			g.setFont(ApoIcejumpConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoIcejumpConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoIcejumpConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoIcejumpConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoIcejumpConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoIcejumpConstants.GAME_HEIGHT - 5);
		}
	}
	
	/**
	 * rendert den Countdown
	 * @param g : Graphicsobjekt
	 * @param changeX
	 * @param changeY
	 */
	public void renderCountdown(Graphics2D g, int changeX, int changeY) {
		if (this.bCountdown) {			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(ApoIcejumpConstants.FONT_COUNTDOWN);
			String countdown = String.valueOf((this.countdownTime / 1000) + 1);
			int w = g.getFontMetrics().stringWidth(countdown);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int width = 200;
			int height = 130;
			if (this.iCountdown == null) {
				this.iCountdown = this.makeAndGetCountdown(width, height);
			}
			g.drawImage(this.iCountdown, ApoIcejumpConstants.GAME_WIDTH/2 - this.iCountdown.getWidth()/2, ApoIcejumpConstants.GAME_HEIGHT/2 - this.iCountdown.getHeight()/2, null);

			Shape shape = g.getClip();
			g.setClip(new Rectangle2D.Float(ApoIcejumpConstants.GAME_WIDTH/2 - this.iCountdown.getWidth()/2, ApoIcejumpConstants.GAME_HEIGHT/2 - this.iCountdown.getHeight()/2, this.iCountdown.getWidth(), this.iCountdown.getHeight()));
			g.setColor(Color.GRAY);
			int percent = (int)((float)((float)this.countdownTime / (float)ApoIcejumpGame.COUNTDOWN) * 360f);
			int x = (int)(ApoIcejumpConstants.GAME_WIDTH/2 + width * Math.sin(Math.toRadians(180 + percent)));
			int y = (int)(ApoIcejumpConstants.GAME_HEIGHT/2 + width * Math.cos(Math.toRadians(180 + percent)));
			g.drawLine(ApoIcejumpConstants.GAME_WIDTH/2, ApoIcejumpConstants.GAME_HEIGHT/2, x, y);
			
			g.setClip(shape);
			g.setColor(Color.GRAY);
			g.drawString(countdown, ApoIcejumpConstants.GAME_WIDTH/2 - w/2, ApoIcejumpConstants.GAME_HEIGHT/2 + h/2);
		}
	}

}
