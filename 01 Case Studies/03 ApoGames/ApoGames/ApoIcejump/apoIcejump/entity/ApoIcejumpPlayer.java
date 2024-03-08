package apoIcejump.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoAnimation;
import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.ApoIcejumpImages;
import apoIcejump.ai.ApoIcejumpAI;
import apoIcejump.ai.ApoIcejumpAILevel;
import apoIcejump.game.ApoIcejumpPanel;

public class ApoIcejumpPlayer extends ApoIcejumpEntity {

	private final int GOODIE_FIRE_TIME = 8000;
	private final int GOODIE_SLOWER_TIME = 8500;
	private final int GOODIE_FASTER_TIME = 7500;
	private final int GOODIE_HIGHER_JUMPS = 7;
	
	private Color myOldColor;
	
	private Color myColor;
	
	private ApoIcejumpAI ai;
	
	private BufferedImage iMyPlayer;
	private BufferedImage iSpeechBubble;
	
	private int ridiculeTime, nextRidiculeTime;
	
	private String ridicule;
	
	private int onFire;
	
	private int onSlower;
	
	private int onHigher;

	private int onFaster;
	
	private ApoAnimation fireAnimation;
	
	private ApoEntity slowerEntity;
	
	private float goalVecX;
	
	private boolean bLeft;
	
	private String enemyString;
	
	public ApoIcejumpPlayer(BufferedImage background, float x, float y, float width, float height, Color myColor, BufferedImage iSpeechBubble) {
		super(background, x, y, width, height);
		
		this.myOldColor = myColor;
		this.myColor = myColor;
		this.iMyPlayer = makeAndGetPlayer(this.myColor);
		this.iSpeechBubble = iSpeechBubble;
	}
	
	public void init() {
		super.init();
		
		this.nextRidiculeTime = (int)(Math.random() * 7000) + 5000;
		this.ridiculeTime = -1;
		this.enemyString = null;
		
		this.noGoodies();
	}
	
	public void setVecX(float goalVecX) {
		if (ApoIcejumpConstants.ACCELERATION) {
			this.goalVecX = goalVecX;
		} else {
			if (goalVecX < 0) {
				this.bLeft = true;
			} else if (goalVecX > 0) {
				this.bLeft = false;
			}
			super.setVecX(goalVecX);
		}
	}
	
	public void setRealVecX(float vecX) {
		super.setVecX(vecX);
	}
	
	public void noGoodies() {
		this.onFire = -1;
		this.onSlower = -1;
		this.onHigher = -1;
		this.onFaster = -1;	
	}
	
	public void setRidiculeTime(int ridiculeTime) {
		this.ridiculeTime = ridiculeTime;
	}

	public ApoIcejumpAI getAI() {
		return ai;
	}

	public void setAI(ApoIcejumpAI ai, ApoIcejumpImages images) {
		this.ai = ai;
		if ((this.ai != null) && (this.ai.getColor() != null)) {
			this.myColor = this.ai.getColor();
		} else {
			this.myColor = this.myOldColor;
		}
		if ((this.ai != null) && (this.ai.getImage() != null)) {
			String imageString = this.ai.getImage();
			BufferedImage iPlayer;
			try {
				iPlayer = images.getImage("images/"+imageString, false);
				if (!ApoConstants.B_APPLET) {
					if (iPlayer == null) {
						iPlayer = images.getImage(System.getProperty("user.dir") + File.separator + imageString, true);
					}
				}
			} catch (Throwable th) {
				iPlayer = null;
			}
			if (iPlayer == null) {
				this.iMyPlayer = makeAndGetPlayer(this.myColor);
			} else {
				this.iMyPlayer = iPlayer;
			}
		} else {
			this.iMyPlayer = makeAndGetPlayer(this.myColor);
		}
	}
	
	private BufferedImage makeAndGetPlayer(Color color) {
		BufferedImage iPlayer = new BufferedImage((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iPlayer.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(color);
		g.fillRoundRect((int)(0), (int)(0), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);
		g.setColor(Color.BLACK);
		g.drawRoundRect((int)(0), (int)(0), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(3));
		g.drawLine((int)(0 + this.getWidth()/2 - 4), (int)(0 + this.getHeight()/2 - 5), (int)(0 + this.getWidth()/2 - 4), (int)(0 + this.getHeight()/2));
		g.drawLine((int)(0 + this.getWidth()/2 + 3), (int)(0 + this.getHeight()/2 - 5), (int)(0 + this.getWidth()/2 + 3), (int)(0 + this.getHeight()/2));
		g.setStroke(stroke);
		
		g.dispose();
		return iPlayer;
	}
	
	public String getAuthor() {
		if ((this.ai != null) && (this.ai.getAuthor() != null)) {
			return this.ai.getAuthor();
		}
		return "You";
	}
	
	public String getName() {
		if ((this.ai != null) && (this.ai.getName() != null)) {
			return this.ai.getName();
		}
		return "Human";
	}
	
	public String getRidiculeSpeech() {
		String ridicule = null;
		if ((this.ai != null) && (this.ai.getRidicule() != null)) {
			int size = this.ai.getRidicule().length;
			int random = (int)(Math.random() * size);
			if (random >= size) {
				random = size - 1;
			}
			ridicule = this.ai.getRidicule()[random];
		}
		if (ridicule == null) {
			int size = ApoIcejumpConstants.RIDICULE.length;
			int random = (int)(Math.random() * size);
			if (random >= size) {
				random = size - 1;
			}
			ridicule = ApoIcejumpConstants.RIDICULE[random];
		}
		if (this.enemyString != null) {
			ridicule = ridicule.replace("#enemy", this.enemyString);
		}
		return ridicule;
	}
	
	public void setOnHigher(int onHigher) {
		this.onHigher = onHigher;
	}

	public int getOnHigher() {
		return this.onHigher;
	}
	
	public int getOnFaster() {
		return this.onFaster;
	}
	
	public int getOnSlower() {
		return this.onSlower;
	}
	
	public int getOnFire() {
		return this.onFire;
	}

	public void thinkAI(int delta, ApoIcejumpPanel panel) {
		if (this.ai != null) {
			try {
				this.ai.think(new ApoIcejumpAILevel(panel, this));
			} catch (Exception ex) {
				System.out.println("Exception by player: "+this.getName()+"");
				super.setBVisible(false);
			}
		}
	}
	
	public void think(int delta, ApoIcejumpPanel panel) {
		super.think(delta);
		if (super.isBVisible()) {
			this.thinkPlayerCollision(delta, panel);

			super.setVecY(super.getVecY() + ApoIcejumpConstants.PLAYER_DECREASE_Y * delta);
			if (super.getVecY() > ApoIcejumpConstants.PLAYER_MAX_VEC_Y) {
				super.setVecY(ApoIcejumpConstants.PLAYER_MAX_VEC_Y);
			}
			float speed = 1;
			if (this.onSlower > 0) {
				speed = 0.7f;
			} else if (this.onFaster > 0) {
				speed = 1.3f;
			}
			if (ApoIcejumpConstants.ACCELERATION) {
				if (this.goalVecX < super.getVecX()) {
					if ((this.goalVecX < 0) && (super.getVecX() > 0)) {
						super.setVecX(this.getVecX() - ApoIcejumpConstants.PLAYER_DECREASE_X);
					} else {
						super.setVecX(this.getVecX() - ApoIcejumpConstants.PLAYER_ACCELERATION_X);						
					}
					if (super.getVecX() <= this.goalVecX) {
						super.setVecX(this.goalVecX);
					}
				} else if (this.goalVecX > super.getVecX()) {
					if ((this.goalVecX > 0) && (super.getVecX() < 0)) {
						super.setVecX(this.getVecX() + ApoIcejumpConstants.PLAYER_DECREASE_X);
					} else {
						super.setVecX(this.getVecX() + ApoIcejumpConstants.PLAYER_ACCELERATION_X);						
					}
					if (super.getVecX() >= this.goalVecX) {
						super.setVecX(this.goalVecX);
					}
				}
			}
			super.setX(super.getX() + super.getVecX() * delta * speed);
			super.setY(super.getY() + super.getVecY() * delta);
			if (super.getX() < 0) {
				super.setX(0);
			} else if (super.getX() + super.getWidth() > ApoIcejumpConstants.GAME_WIDTH) {
				super.setX(ApoIcejumpConstants.GAME_WIDTH - super.getWidth());
			}
			if (super.getY() > ApoIcejumpConstants.GAME_HEIGHT - ApoIcejumpConstants.WATER_HEIGHT) {
				super.setBVisible(false);
			}
			
			this.thinkBlockCollision(delta, panel);
			this.thinkBirdCollision(delta, panel);
			this.thinkGoodieCollision(delta, panel);
			
			if (this.onFire > 0) {
				this.onFire -= delta;
				this.fireAnimation.think(delta);
			}
			if (this.onSlower > 0) {
				this.onSlower -= delta;
				this.slowerEntity.think(delta);
			}
			if (this.onFaster > 0) {
				this.onFaster -= delta;
			}
			if (this.ridiculeTime > 0) {
				this.ridiculeTime -= delta;
			} else {
				this.nextRidiculeTime -= delta;
				if (nextRidiculeTime <= 0) {
					this.nextRidiculeTime = (int)(Math.random() * 6000) + 5000;
					this.ridiculeTime = 4000;
					this.ridicule = this.getRidiculeSpeech();
				}
			}
		}		
	}
	
	private void thinkPlayerCollision(int delta, ApoIcejumpPanel panel) {
		for (int i = 0; i < panel.getPlayers().length; i++) {
			if (!this.equals(panel.getPlayers()[i])) {
				ApoIcejumpPlayer enemy = panel.getPlayers()[i];
				if (this.enemyString == null) {
					this.enemyString = enemy.getName();
				}
				if (this.intersects(enemy)) {
					if (this.onFire > 0) {
						enemy.setOnFire(panel, this.onFire);
					}
					if (enemy.getOnFire() > 0) {
						this.setOnFire(panel, enemy.getOnFire());
					}
					if (this.onSlower > 0) {
						enemy.setOnSlower(panel, this.onSlower);
					}
					if (enemy.getOnSlower() > 0) {
						this.setOnSlower(panel, enemy.getOnSlower());
					}
					if (this.isAboveEntity(enemy)) {
						this.setY(enemy.getY() - this.getHeight());
						float moreHeight = 1;
						if (this.onHigher > 0) {
							this.onHigher -= 1;
							moreHeight = 1.4f;
						}
						this.setVecY(ApoIcejumpConstants.PLAYER_HIT_PLAYER_VEC_Y * moreHeight);
						if (enemy.getVecY() < 0) {
							enemy.setVecY(0);
						}
					} else if (this.isDownEntity(enemy)) {
						enemy.setY(this.getY() - enemy.getHeight());
						float moreHeight = 1;
						if (enemy.getOnHigher() > 0) {
							enemy.setOnHigher(enemy.getOnHigher() - 1);
							moreHeight = 1.4f;
						}
						enemy.setVecY(ApoIcejumpConstants.PLAYER_HIT_PLAYER_VEC_Y * moreHeight);
						if (this.getVecY() < 0) {
							this.setVecY(0);
						}
					} else {
						if (ApoIcejumpConstants.ACCELERATION) {
							float vecX = enemy.getVecX();
							enemy.setRealVecX(this.getVecX());
							this.setRealVecX(vecX);
						}
						if (this.getX() < enemy.getX()) {
							enemy.setX(this.getX() + enemy.getWidth());
						} else {
							enemy.setX(this.getX() - this.getWidth());
						}
					}
				} else if (this.getY() < enemy.getY()) {
					if (enemy.intersects(this.getX() - 3, 0, this.getWidth() + 6, ApoIcejumpConstants.GAME_HEIGHT)) {
						if (Math.abs(this.getVecX()) > ApoIcejumpConstants.PLAYER_MAX_VEC_X_OVER_ENEMY) {
							if (this.getVecX() < 0) {
								this.setVecX(-ApoIcejumpConstants.PLAYER_MAX_VEC_X_OVER_ENEMY);								
							} else {
								this.setVecX(ApoIcejumpConstants.PLAYER_MAX_VEC_X_OVER_ENEMY);
							}
						}
					}
				}
			}
		}
	}
	
	private void thinkBlockCollision(int delta, ApoIcejumpPanel panel) {
		if (!this.blockCheck(panel.getBackBlocks(), panel.getTime())) {
			this.blockCheck(panel.getFrontBlocks(), panel.getTime());
		}
	}
	
	private boolean blockCheck(ArrayList<ApoIcejumpBlock> blocks, int time) {
		for (int i = 0; i < blocks.size(); i++) {
			ApoIcejumpBlock block = blocks.get(i);
			if (this.isAboveEntity(block)) {
				if ((this.onFire <= 0) && (time <= ApoIcejumpConstants.GAME_SUDDEN_DEATH_TIME)) {
					block.setHits(block.getHits() - 1);
				} else {
					block.setHits(0);
				}
				this.setY(block.getY() - this.getHeight());
				float difference = block.getX() + block.getWidth()/2 - this.getX() - this.getWidth()/2;
				block.setVecX(difference * ApoIcejumpConstants.ICEBLOCK_HIT_VEC_X);
				float moreHeight = 1;
				if (this.onHigher > 0) {
					this.onHigher -= 1;
					moreHeight = 1.5f;
				}
				if (Math.abs(difference) < this.getWidth()/4) {
					this.setVecY(ApoIcejumpConstants.PLAYER_HIT_BLOCK_VEC_Y * moreHeight);
				} else {
					this.setVecY(ApoIcejumpConstants.PLAYER_HIT_BLOCK_VEC_Y * moreHeight + (Math.abs(difference) - this.getWidth()/4) * ApoIcejumpConstants.PLAYER_HIT_BLOCK_DIFFERENCE_VEC_Y);
				}
				return true;
			}
		}
		return false;
	}
	
	private void thinkBirdCollision(int delta, ApoIcejumpPanel panel) {
		for (int i = 0; i < panel.getBirds().size(); i++) {
			if (this.intersects(panel.getBirds().get(i))) {
				if (this.onFire > 0) {
					BufferedImage iFire = panel.getIFirePlayer();
					panel.getBirds().get(i).setFireAnimation(new ApoAnimation(iFire, 0, 0, ApoIcejumpConstants.PLAYER_FIRE_TILES, ApoIcejumpConstants.PLAYER_FIRE_TIME));
				}
				if (this.onSlower > 0) {
					panel.getBirds().get(i).setIceBlock(new ApoEntity(null, 0, 0, (int)panel.getBirds().get(i).getRec().getWidth(), (int)panel.getBirds().get(i).getRec().getHeight() + 10));						
				}
				if (this.isAboveEntity(panel.getBirds().get(i))) {
					this.setY((float)(panel.getBirds().get(i).getRec().getY() - this.getHeight()));
					float moreHeight = 1;
					if (this.onHigher > 0) {
						this.onHigher -= 1;
						moreHeight = 1.5f;
					}
					this.setVecY(ApoIcejumpConstants.PLAYER_HIT_BIRD_VEC_Y * moreHeight);
				} else if (this.isDownEntity(panel.getBirds().get(i))) {
					this.setY((float)(panel.getBirds().get(i).getRec().getY() + panel.getBirds().get(i).getRec().getHeight()));
					if (this.getVecY() < 0) {
						this.setVecY(0);
					}
				} else {
					if (this.getX() < panel.getBirds().get(i).getX()) {
						this.setX(panel.getBirds().get(i).getX() - this.getWidth());
					} else {
						this.setX(panel.getBirds().get(i).getX() + panel.getBirds().get(i).getWidth());
					}
				}
			}
		}
	}
	
	private void thinkGoodieCollision(int delta, ApoIcejumpPanel panel) {
		for (int i = 0; i < panel.getGoodies().size(); i++) {
			if (panel.getGoodies().get(i).isBVisible()) {
				if (this.intersects(panel.getGoodies().get(i))) {
					panel.getGoodies().get(i).setBVisible(false);
					this.setGoodie(panel.getGoodies().get(i).getGoodie(), panel);
				}
			}
		}
	}

	protected void setOnHigher(ApoIcejumpPanel panel, int higher) {
		if (this.onHigher < higher) {
			this.onHigher = higher;
		}
		this.onFire = 0;
		this.onSlower = 0;
		this.onFaster = -1;
	}
	
	protected void setOnFaster(ApoIcejumpPanel panel, int fasterTime) {
		if (this.onFaster < fasterTime) {
			this.onFaster = fasterTime;
		}
		this.onFire = 0;
		this.onSlower = 0;
		this.onHigher = -1;
	}
	
	protected void setOnSlower(ApoIcejumpPanel panel, int slowerTime) {
		if (this.onSlower < slowerTime) {
			this.onSlower = slowerTime;
		}
		this.onFire = 0;
		this.onHigher = -1;
		this.onFaster = -1;
		if (this.slowerEntity == null) {
			BufferedImage iIce = panel.getIIce();
			this.slowerEntity= new ApoEntity(iIce, 0, 0, iIce.getWidth(), iIce.getHeight());
		}		
	}
	
	protected void setOnFire(ApoIcejumpPanel panel, int fireTime) {
		if (this.onFire < fireTime) {
			this.onFire = fireTime;
		}
		this.onSlower = 0;
		this.onHigher = -1;
		this.onFaster = -1;
		if (this.fireAnimation == null) {
			BufferedImage iFire = panel.getIFirePlayer();
			this.fireAnimation = new ApoAnimation(iFire, 0, 0, ApoIcejumpConstants.PLAYER_FIRE_TILES, ApoIcejumpConstants.PLAYER_FIRE_TIME);
		}		
	}
	
	private void setGoodie(int goodie, ApoIcejumpPanel panel) {
		if (goodie == ApoIcejumpConstants.GOODIE_ICE_LITTLE) {
			panel.makeBlocksInGame(3);
		} else if (goodie == ApoIcejumpConstants.GOODIE_ICE_BIG) {
			panel.makeBlocksInGame(6);
		} else if (goodie == ApoIcejumpConstants.GOODIE_FIRE) {
			this.setOnFire(panel, this.GOODIE_FIRE_TIME);
		} else if (goodie == ApoIcejumpConstants.GOODIE_SLOWER) {
			this.setOnSlower(panel, this.GOODIE_SLOWER_TIME);
		} else if (goodie == ApoIcejumpConstants.GOODIE_HIGHER) {
			this.setOnHigher(panel, this.GOODIE_HIGHER_JUMPS);
		} else if (goodie == ApoIcejumpConstants.GOODIE_FASTER) {
			this.setOnFaster(panel, this.GOODIE_FASTER_TIME);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.getIBackground() == null) {
			this.renderPlayer(g, (int)(this.getX()), (int)(this.getY()), changeX, changeY);
		} else {
			super.render(g, changeX, changeY);
		}
		if (this.ridiculeTime > 0) {
			g.drawImage(this.iSpeechBubble, (int)(this.getX() + this.getWidth()/2 - this.iSpeechBubble.getWidth()/2 + changeX), (int)(this.getY() + changeY - this.iSpeechBubble.getHeight()), null);
			this.drawSpeech(g, this.ridicule, (int)(this.getX() + this.getWidth()/2 - this.iSpeechBubble.getWidth()/2 + changeX + 11), (int)(this.getY() + changeY - this.iSpeechBubble.getHeight() + 28), 104);
		}
	}
	
	public void renderPlayer(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (this.onFire > 0) {
			if (this.fireAnimation != null) {
				this.fireAnimation.render(g, (int)(x + changeX + this.getWidth()/2 - this.fireAnimation.getWidth()/2), (int)(y + changeY + this.getHeight() - this.fireAnimation.getHeight() + 1));
			}
		}
		boolean bRender = false;
		if ((this.ai != null) && (this.ai.shouldOwnRender())) {
			g.translate((int)(x + changeX), (int)(y + changeY));
			Shape shape = g.getClip();
			g.setClip(0, 0, (int)this.getWidth(), (int)this.getHeight());
			if (this.ai.renderPlayer(g)) {
				bRender = true;
			}
			g.setClip(shape);
			g.translate(-(int)(x + changeX), -(int)(y + changeY));
		}
		if (!bRender) {
			if (this.iMyPlayer == null) {
				g.setColor(this.myColor);
				g.fillRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(3));
				g.drawLine((int)(x + changeX + this.getWidth()/2 - 5), (int)(y + changeY + this.getHeight()/2 - 5), (int)(x + changeX + this.getWidth()/2 - 5), (int)(y + changeY + this.getHeight()/2));
				g.drawLine((int)(x + changeX + this.getWidth()/2 + 2), (int)(y + changeY + this.getHeight()/2 - 5), (int)(x + changeX + this.getWidth()/2 + 2), (int)(y + changeY + this.getHeight()/2));
				g.setStroke(stroke);
			} else {
				if (!this.bLeft) {
					g.drawImage(this.iMyPlayer, (int)(x + changeX), (int)(y + changeY), null);
				} else {
					g.drawImage(this.iMyPlayer, (int)(x + changeX), (int)(y + changeY), (int)(x + changeX) + this.iMyPlayer.getWidth(), (int)(y + changeY) + this.iMyPlayer.getHeight(), this.iMyPlayer.getWidth(), 0, 0, this.iMyPlayer.getHeight(), null);				
				}
			}
		}
		if (this.getY() < 0) {
			g.setColor(this.myColor);
			g.fillRoundRect((int)(x + this.getWidth()/2 + changeX - 3), changeY, 5, 5, 3, 3);
		}
		if (this.onFire > 0) {
			g.setColor(Color.RED);
			g.drawRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);			
		}
		if (this.onSlower > 0) {
			g.setColor(Color.BLUE);
			g.drawRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);			
			if (this.slowerEntity != null) {
				this.slowerEntity.render(g, (int)(x + changeX + this.getWidth()/2 - this.slowerEntity.getWidth()/2), (int)(y + changeY + this.getHeight() + 1));
			}
		}
		if (this.onHigher > 0) {
			g.setColor(Color.YELLOW);
			g.drawRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);						
		}
		if (this.onFaster > 0) {
			g.setColor(Color.GREEN);
			g.drawRoundRect((int)(x + changeX), (int)(y + changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 11, 11);						
		}
	}
	
	private void drawSpeech(Graphics2D g, String s, int x, int y, int width) {
		g.setColor(Color.BLACK);
		g.setFont(ApoIcejumpConstants.FONT_PLAYER_STATISTICS);
		
		int curHeight = 0;
		int[] maxLength = new int[] {width, width, width, width};
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s);
		int cur = 0;
		int w = g.getFontMetrics().stringWidth(strings.get(cur));
		if (w > maxLength[curHeight]) {
			int curPos = strings.get(cur).indexOf(" ");
			while ((curPos > -1) && (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, curPos)) < maxLength[curHeight])) {
				int nextPos = strings.get(cur).indexOf(" ", curPos + 1);
				if (nextPos != -1) {
					if (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, nextPos)) >= maxLength[curHeight]) {
						String curString = strings.get(cur);
						strings.set(cur, curString.substring(0, curPos));
						cur++;
						if (curHeight == 0) {
							curHeight++;
						}
						strings.add(curString.substring(curPos + 1));
						curPos = strings.get(cur).indexOf(" ");
					} else {
						curPos = nextPos;
					}
				} else {
					String curString = strings.get(cur);
					if (g.getFontMetrics().stringWidth( curString ) > maxLength[curHeight]) {
						strings.set( cur, curString.substring(0, curPos) );
						cur++;
						strings.add( curString.substring(curPos + 1));
					}
					break;
				}
			}
		}
		int h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
		for ( int i = 0; i < strings.size(); i++ ) {
			w = g.getFontMetrics().stringWidth(strings.get(i));
			g.drawString(strings.get(i), x + width/2 - w/2, y);
			y += h;
		}
	}

}
