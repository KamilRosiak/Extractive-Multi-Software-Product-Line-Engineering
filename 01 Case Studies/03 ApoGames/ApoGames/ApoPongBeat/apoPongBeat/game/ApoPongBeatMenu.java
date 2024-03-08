package apoPongBeat.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.apogames.help.ApoHelp;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.entity.ApoPongBeatBallBlink;
import apoPongBeat.entity.ApoPongBeatBallGravity;
import apoPongBeat.entity.ApoPongBeatBallMedium;
import apoPongBeat.entity.ApoPongBeatBallRebound;
import apoPongBeat.entity.ApoPongBeatBallSinusCreate;
import apoPongBeat.entity.ApoPongBeatParticle;
import apoPongBeat.entity.ApoPongBeatParticleString;

public class ApoPongBeatMenu extends ApoPongBeatModel {
	
	public static final String QUIT = "QUIT";
	public static final String START = "START";
	public static final String OPTIONS = "OPTIONS";
	public static final String ACHIEVEMENTS = "ACHIEVEMENTS";

	private int nextBallTime;
	
	private int nextParticleTime;
	
	private Color backgroundColor;
	
	private int nextBackgroundTime;
	
	private int nextBackgroundStringTime;
	
	private String nextBackgroundString;
	
	public ApoPongBeatMenu(ApoPongBeatPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getGame().setShouldRepaint(true);
		
		this.getGame().initGameStats();
		
		this.nextBallTime = 1000 + ApoHelp.getRandomValue(500, 0);
		this.nextParticleTime = 1000 + ApoHelp.getRandomValue(500, 0);
		this.nextBackgroundTime = 1000 + ApoHelp.getRandomValue(2500, 0);
		this.backgroundColor = Color.BLACK;
		this.nextBackgroundStringTime = 1000 + ApoHelp.getRandomValue(500, 0);
		this.nextBackgroundString = ApoPongBeatConstants.BACKGROUND_STRING[ApoHelp.getRandomValue(ApoPongBeatConstants.BACKGROUND_STRING.length, 0)];
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoPongBeatMenu.QUIT)) {
			System.exit(0);
		} else if (function.equals(ApoPongBeatMenu.START)) {
			this.getGame().startGame();
		} else if (function.equals(ApoPongBeatMenu.OPTIONS)) {
			this.getGame().setOptions();
		} else if (function.equals(ApoPongBeatMenu.ACHIEVEMENTS)) {
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		this.getGame().moveRacket(x, y);
	}
	
	@Override
	public void ballOnBorder(boolean left) {
		this.getGame().ballOnBorderGame(left);
	}
	
	@Override
	public void ballOnRacket(int points) {
		this.getGame().ballOnRacketGame(points);
	}

	@Override
	public void think(int delta) {
		this.nextBallTime -= delta;
		if (this.nextBallTime <= 0) {
			this.nextBallTime = 1500 + ApoHelp.getRandomValue(1000, 0);
			int random = ApoHelp.getRandomValue(100, 0);
			int width = 20;
			int y = ApoHelp.getRandomValue(ApoPongBeatConstants.MAX_Y - ApoPongBeatConstants.MIN_Y - width, ApoPongBeatConstants.MIN_Y);
			if (random < 10) {
				if (y + 3 * width >= ApoPongBeatConstants.MAX_Y) {
					y = ApoPongBeatConstants.MAX_Y - 3 * width;
				}
				float vecX = (float)(ApoPongBeatBallMedium.MY_VEC_X + Math.random() * 0.1f);
				this.getGame().getBalls().add(new ApoPongBeatBallMedium(y, width, width, vecX, false, false));
				this.getGame().getBalls().add(new ApoPongBeatBallMedium(y + 1 * width, width, width, vecX, false, false));
				this.getGame().getBalls().add(new ApoPongBeatBallMedium(y + 2 * width, width, width, vecX, false, false));
			} else if (random < 25) {
				new ApoPongBeatBallSinusCreate(this.getGame(), 10);
			}else if (random < 35) {
				float vecX = (float)(ApoPongBeatBallMedium.MY_VEC_X + Math.random() * 0.1f);
				boolean bAbove = false;
				if (ApoHelp.getRandomValue(100, 0) > 50) {
					bAbove = true;
				}
				int count = 3 - ApoHelp.getRandomValue(3, 0);
				this.getGame().getBalls().add(new ApoPongBeatBallRebound(y, width, width, vecX, bAbove, count));
			} else if (random < 45) {
				this.getGame().getBalls().add(new ApoPongBeatBallMedium(y, width, width, true));
			} else if (random < 65) {
				boolean bBoth = false;
				if (ApoHelp.getRandomValue(100, 0) > 50) {
					bBoth = true;
				}
				this.getGame().getBalls().add(new ApoPongBeatBallBlink(y, width, width, bBoth));
			} else if (random < 75) {
				boolean bAbove = false;
				if (ApoHelp.getRandomValue(100, 0) > 50) {
					bAbove = true;
				}
				this.getGame().getBalls().add(new ApoPongBeatBallGravity(y, width, width, 0, bAbove));
			} else {
				this.getGame().getBalls().add(new ApoPongBeatBallMedium(y, width, width, false));
			}
		}
		if (this.getGame().getMult() >= ApoPongBeatConstants.PARTICLE_LEVEL) {
			this.nextParticleTime -= delta;
			if (this.nextParticleTime <= 0) {
				int x = ApoHelp.getRandomValue(ApoPongBeatConstants.GAME_WIDTH, 0);
				int y = ApoHelp.getRandomValue(ApoPongBeatConstants.MAX_Y - ApoPongBeatConstants.MIN_Y, ApoPongBeatConstants.MIN_Y);
				int width = 5;
				Color color = new Color(ApoHelp.getRandomValue(255, 0), ApoHelp.getRandomValue(255, 0), ApoHelp.getRandomValue(255, 0), 100);
				ApoPongBeatParticle.makeParticle(x, y, width, color, this.getGame(), ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
				this.nextParticleTime = 300 + ApoHelp.getRandomValue(Math.max(500, 2000 - this.getGame().getMult() * 200), 0);
			}
		}
		if (this.getGame().getMult() >= ApoPongBeatConstants.PARTICLE_BACKGROUND_LEVEL) {
			this.nextBackgroundTime -= delta;
			if (this.nextBackgroundTime <= 0) {
				this.nextBackgroundTime = 1000 + ApoHelp.getRandomValue(Math.max(500, 4000 - this.getGame().getMult() * 200), 0);
				this.backgroundColor = new Color(ApoHelp.getRandomValue(200, 0), ApoHelp.getRandomValue(200, 0), ApoHelp.getRandomValue(200, 0));
			}
		}
		if (this.getGame().getMult() >= ApoPongBeatConstants.PARTICLE_STRING_LEVEL) {
			this.nextBackgroundStringTime -= delta;
			if (this.nextBackgroundStringTime <= 0) {
				this.nextBackgroundStringTime = 1000 + ApoHelp.getRandomValue(500, 0);
				this.nextBackgroundString = ApoPongBeatConstants.BACKGROUND_STRING[ApoHelp.getRandomValue(ApoPongBeatConstants.BACKGROUND_STRING.length, 0)];
				Color color = new Color(ApoHelp.getRandomValue(255, 0), ApoHelp.getRandomValue(255, 0), ApoHelp.getRandomValue(255, 0));
				int x = ApoHelp.getRandomValue(ApoPongBeatConstants.GAME_WIDTH, 0);
				int y = ApoHelp.getRandomValue(ApoPongBeatConstants.MAX_Y - ApoPongBeatConstants.MIN_Y, ApoPongBeatConstants.MIN_Y);
				this.getGame().getParticle().add(new ApoPongBeatParticleString(x, y, color, ApoPongBeatConstants.PARTICLE_BACKGROUND_STRING_TIME, this.nextBackgroundString, this.getGame().getMyFont(), false));

			}
		}
		
		this.getGame().thinkBalls(delta);
		this.getGame().thinkParticles(delta);
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.getGame().getMult() < ApoPongBeatConstants.PARTICLE_BACKGROUND_LEVEL) {
			this.getGame().renderBackground(g);
		} else {
			g.setColor(this.backgroundColor);
			g.fillRect(0, 0, ApoPongBeatConstants.GAME_WIDTH, ApoPongBeatConstants.GAME_HEIGHT);
		}
		
		this.getGame().renderParticles(g);
		if (this.getGame().getMyFont() != null) {
			g.setColor(Color.WHITE);
			g.setFont(this.getGame().getMyFont());
			this.getGame().renderString(g, "ApoPongBeat", ApoPongBeatConstants.GAME_WIDTH/2, 35);
		}
		this.getGame().renderStats(g);
		
		this.getGame().renderBalls(g);
		this.getGame().getRacket().render(g, 0, 0);
	}

}
