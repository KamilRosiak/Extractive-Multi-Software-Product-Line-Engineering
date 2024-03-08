package apoSimple.game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntityMenu;

public class ApoSimpleOptions  extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "options_menu";
	public static final String MUSIC = "music";
	public static final String SOUND = "sound";
	public static final String GERMAN = "german";
	public static final String ENGLISH = "english";
	public static final String SHEEP = "sheep";
	public static final String CLASSIC = "classic";

	private BufferedImage iBackground;
	
	public ApoSimpleOptions(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/options.png", false);
		}
		if (this.getGame().getTextField() != null) {
			this.getGame().getTextField().setX(140);
			this.getGame().getTextField().setY(27);
			this.getGame().getTextField().setBVisible(true);
		}
		
		if (super.getMenuSheep().size() <= 0) {
			int width = ApoSimpleConstants.ENTITY_WIDTH;
			int minX = 0;
			int minY = 338;
			int maxWidth = ApoSimpleConstants.GAME_WIDTH - minX + width;
			int maxHeight = ApoSimpleConstants.GAME_HEIGHT - minY - width;
			for (int i = 10; i < maxWidth; i+= width + 10) {
				for (int j = 10; j < maxHeight; j+= width + 10) {
					super.getMenuSheep().add(new ApoSimpleEntityMenu(minX + i, minY + j, width, width, new Rectangle2D.Float(minX, minY, maxWidth, maxHeight)));					
				}
			}
			for (int i = 0; i < 1000; i++) {
				super.thinkSheep(ApoSimpleConstants.WAIT_TIME_THINK);
			}
		}
		((ApoSimpleOptionsButton)(this.getGame().getButtons()[49])).setBActive(this.getGame().isbSoundMusic());
		((ApoSimpleOptionsButton)(this.getGame().getButtons()[50])).setBActive(this.getGame().isbSoundEffects());
		
		if (ApoSimpleConstants.REGION.equals("de")) {
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[61])).setBActive(true);
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[62])).setBActive(false);
		} else {
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[61])).setBActive(false);
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[62])).setBActive(true);	
		}
		
		((ApoSimpleOptionsButton)(this.getGame().getButtons()[63])).setBActive(!this.getGame().isClassicMode());
		((ApoSimpleOptionsButton)(this.getGame().getButtons()[64])).setBActive(this.getGame().isClassicMode());
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		} else {
			this.getGame().keyButtonReleasedTextField(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.getGame().getTextField() != null) {
			this.getGame().getTextField().setBSelect(false);
		}
		if (function.equals(ApoSimpleOptions.MENU)) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		} else if (function.equals(ApoSimpleOptions.MUSIC)) {
			boolean bMusic = !((ApoSimpleOptionsButton)(this.getGame().getButtons()[49])).isBActive();
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[49])).setBActive(bMusic);
			this.getGame().setbSoundMusic(bMusic);
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		} else if (function.equals(ApoSimpleOptions.SOUND)) {
			boolean bSound = !((ApoSimpleOptionsButton)(this.getGame().getButtons()[50])).isBActive();
			((ApoSimpleOptionsButton)(this.getGame().getButtons()[50])).setBActive(bSound);
			this.getGame().setbSoundEffects(bSound);
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		} else if (function.equals(ApoSimpleOptions.GERMAN)) {
			if (!ApoSimpleConstants.REGION.equals("de")) {
				boolean bSound = !((ApoSimpleOptionsButton)(this.getGame().getButtons()[61])).isBActive();
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[61])).setBActive(bSound);
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[62])).setBActive(!bSound);
				ApoSimpleConstants.REGION = "de";
				ApoSimpleConstants.setLanguage(ApoSimpleConstants.REGION);
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);	
			}
		} else if (function.equals(ApoSimpleOptions.ENGLISH)) {
			if (!ApoSimpleConstants.REGION.equals("en")) {
				boolean bSound = !((ApoSimpleOptionsButton)(this.getGame().getButtons()[62])).isBActive();
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[62])).setBActive(bSound);
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[61])).setBActive(!bSound);
				ApoSimpleConstants.REGION = "en";
				ApoSimpleConstants.setLanguage(ApoSimpleConstants.REGION);
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);	
			}
		} else if (function.equals(ApoSimpleOptions.SHEEP)) {
			if (this.getGame().isClassicMode()) {
				this.getGame().setClassicMode(!this.getGame().isClassicMode());
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[63])).setBActive(!this.getGame().isClassicMode());
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[64])).setBActive(this.getGame().isClassicMode());
				ApoSimplePanel.level_save = "";
			}
		} else if (function.equals(ApoSimpleOptions.CLASSIC)) {
			if (!this.getGame().isClassicMode()) {
				this.getGame().setClassicMode(!this.getGame().isClassicMode());
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[63])).setBActive(!this.getGame().isClassicMode());
				((ApoSimpleOptionsButton)(this.getGame().getButtons()[64])).setBActive(this.getGame().isClassicMode());
				ApoSimplePanel.level_save = "";
			}
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBVisible())) {
			this.getGame().getTextField().mouseReleased(x, y);
			if (this.getGame().getTextField().intersects(x, y)) {
				this.getGame().getTextField().setBSelect(true);
			} else {
				if (this.getGame().getTextField().isBSelect()) {
					if (ApoConstants.B_APPLET) {
						try {
							ApoHelp.saveData(new URL("http://www.apo-games.de/apoSimple/"), "name", String.valueOf(this.getGame().getTextField().getCurString()));
						} catch (MalformedURLException e) {
						}
					}
				}
				this.getGame().getTextField().setBSelect(false);
			}
		}
	}
	
	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBVisible())) {
			if (this.getGame().getTextField().mousePressed(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int mouseX, int mouseY) {
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBVisible())) {
			if (this.getGame().getTextField().getMove(mouseX, mouseY)) {
				this.getGame().render();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseDragged(int x, int y) {
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBVisible())) {
			if (this.getGame().getTextField().mouseDragged(x, y)) {
				return true;
			}
		}
		return this.mouseMoved(x, y);
	}

	@Override
	public void think(int delta) {
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBSelect())) {
			this.getGame().getTextField().think(delta);
			this.getGame().render();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().isBVisible())) {
			this.getGame().getTextField().render(g, 0, 0);
		}
	}
	
	public void drawOverlay(Graphics2D g) {	
		super.getGame().renderCoins(g, 0, 0);
		
		String[] strings = new String[] {
				"In the classic mode there are only white sheep in the endless mode.",
				"NO bushes, no double sheep, black sheep or dogs will be added later.",
				"But you CAN'T upload your score."
		};
		if (ApoSimpleConstants.REGION.equals("de")) {
			strings = new String[] {
					"Im Klassikmodus bestehen alle Levels im Endlosmodus nur aus weißen Schafen.",
					"Es werden also KEINE Büsche, schwarze Schafe etc später mit eingefügt.",
					"Aber du kannst deinen Punktestand dabei nicht hochladen."
			};
		}
		this.getGame().renderOver(g, strings, 64);

		strings = new String[] {
				"In the sheeptastic mode in the endless mode you will play with all the tiles.",
				"Bushes, double sheep, black sheep or dogs will be added later.",
				"And the best ... you CAN upload your score."
		};
		if (ApoSimpleConstants.REGION.equals("de")) {
			strings = new String[] {
					"Im Sheeptasticmodus kommen im Endlosmodus alle Sachen drin vor. ",
					"Es werden Büsche, schwarze Schafe etc. später mit eingefügt.",
					"Und das Beste: Du kannst deinen Punktestand zum Schluß hochladen."
			};
		}
		this.getGame().renderOver(g, strings, 63);
	}

}