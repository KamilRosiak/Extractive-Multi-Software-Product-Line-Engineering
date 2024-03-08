package apoSkunkman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Hilfsklasse für alle Bilder, die geladen werden müssen und sie gleich auf die richtige Größe setzt
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanImageContainer {

	/** Bild der eigentlichen Spieltiles */
	public static BufferedImage iTileSkunk;
	/** Bild der den ganzen Goodies */
	public static BufferedImage iGoodieSkunk;
	/** Bild der den unterschiedlichen Flamen */
	public static BufferedImage iFlameSkunk;
	/** Bild der Animation des Stinktieres */
	public static BufferedImage iBombSkunk;
	/** Bild des eigentlichen HUDs des Spiels */
	public static BufferedImage iHudSkunk;
	/** Bild des Baumes, welcher z.B. für den Quitbutton benutzt wird */
	public static BufferedImage iHudTreeSkunk;
	/** Bild des ersten Spielers */
	public static BufferedImage iPlayerOneSkunk;
	/** Bild des zweiten Spielers */
	public static BufferedImage iPlayerTwoSkunk;
	/** Bild des dritten Spielers */
	public static BufferedImage iPlayerThreeSkunk;
	/** Bild des vierten Spielers */
	public static BufferedImage iPlayerFourSkunk;
	/** Bild der Anzeigt für die Zeit */
	public static BufferedImage iTimeSkunk;
	/** Bild des load-Buttons */
	public static BufferedImage iLoadSkunk;
	/** Bild des play-Buttons */
	public static BufferedImage iPlaySkunk;
	/** Bild des stop-Buttons */
	public static BufferedImage iStopSkunk;
	/** Bild des new-Buttons */
	public static BufferedImage iNewSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iPauseSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iQuitSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iEditorSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iGameSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iLoadTreeSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iSaveTreeSkunk;
	/** Bild des pause-Buttons */
	public static BufferedImage iGoalXSkunk;
	/** Bild des clear-Buttons */
	public static BufferedImage iClearSkunk;
	/** Bild des simulate-Buttons */
	public static BufferedImage iSimulateSkunk;
	/** Bild des help-Buttons */
	public static BufferedImage iHelpSkunk;
	
	/** Bild der eigentlichen Spieltiles */
	public static BufferedImage iTileAscii;
	/** Bild der den ganzen Goodies */
	public static BufferedImage iGoodieAscii;
	/** Bild der den unterschiedlichen Flamen */
	public static BufferedImage iFlameAscii;
	/** Bild der Animation des Stinktieres */
	public static BufferedImage iBombAscii;
	/** Bild des eigentlichen HUDs des Spiels */
	public static BufferedImage iHudAscii;
	/** Bild des Baumes, welcher z.B. für den Quitbutton benutzt wird */
	public static BufferedImage iHudTreeAscii;
	/** Bild des ersten Spielers */
	public static BufferedImage iPlayerOneAscii;
	/** Bild des zweiten Spielers */
	public static BufferedImage iPlayerTwoAscii;
	/** Bild des dritten Spielers */
	public static BufferedImage iPlayerThreeAscii;
	/** Bild des vierten Spielers */
	public static BufferedImage iPlayerFourAscii;
	/** Bild der Anzeigt für die Zeit */
	public static BufferedImage iTimeAscii;
	/** Bild des load-Buttons */
	public static BufferedImage iLoadAscii;
	/** Bild des play-Buttons */
	public static BufferedImage iPlayAscii;
	/** Bild des stop-Buttons */
	public static BufferedImage iStopAscii;
	/** Bild des new-Buttons */
	public static BufferedImage iNewAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iPauseAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iQuitAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iEditorAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iGameAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iLoadTreeAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iSaveTreeAscii;
	/** Bild des pause-Buttons */
	public static BufferedImage iGoalXAscii;
	/** Bild des clear-Buttons */
	public static BufferedImage iClearAscii;
	/** Bild des simulate-Buttons */
	public static BufferedImage iSimulateAscii;
	/** Bild des help-Buttons */
	public static BufferedImage iHelpAscii;
	
	/** Bild der eigentlichen Spieltiles */
	public static BufferedImage iTileAntje;
	/** Bild der den ganzen Goodies */
	public static BufferedImage iGoodieAntje;
	/** Bild der den unterschiedlichen Flamen */
	public static BufferedImage iFlameAntje;
	/** Bild der Animation des Stinktieres */
	public static BufferedImage iBombAntje;
	/** Bild des eigentlichen HUDs des Spiels */
	public static BufferedImage iHudAntje;
	/** Bild des Baumes, welcher z.B. für den Quitbutton benutzt wird */
	public static BufferedImage iHudTreeAntje;
	/** Bild des ersten Spielers */
	public static BufferedImage iPlayerOneAntje;
	/** Bild des zweiten Spielers */
	public static BufferedImage iPlayerTwoAntje;
	/** Bild des dritten Spielers */
	public static BufferedImage iPlayerThreeAntje;
	/** Bild des vierten Spielers */
	public static BufferedImage iPlayerFourAntje;
	/** Bild der Anzeigt für die Zeit */
	public static BufferedImage iTimeAntje;
	/** Bild des load-Buttons */
	public static BufferedImage iLoadAntje;
	/** Bild des play-Buttons */
	public static BufferedImage iPlayAntje;
	/** Bild des stop-Buttons */
	public static BufferedImage iStopAntje;
	/** Bild des new-Buttons */
	public static BufferedImage iNewAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iPauseAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iQuitAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iEditorAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iGameAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iLoadTreeAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iSaveTreeAntje;
	/** Bild des pause-Buttons */
	public static BufferedImage iGoalXAntje;
	/** Bild des clear-Buttons */
	public static BufferedImage iClearAntje;
	/** Bild des simulate-Buttons */
	public static BufferedImage iSimulateAntje;
	/** Bild des help-Buttons */
	public static BufferedImage iHelpAntje;

	
	/** Bild der eigentlichen Spieltiles */
	public static BufferedImage iTile;
	/** Bild der den ganzen Goodies */
	public static BufferedImage iGoodie;
	/** Bild der den unterschiedlichen Flamen */
	public static BufferedImage iFlame;
	/** Bild der Animation des Stinktieres */
	public static BufferedImage iBomb;
	/** Bild des eigentlichen HUDs des Spiels */
	public static BufferedImage iHud;
	/** Bild des Baumes, welcher z.B. für den Quitbutton benutzt wird */
	public static BufferedImage iHudTree;
	/** Bild des ersten Spielers */
	public static BufferedImage iPlayerOne;
	/** Bild des zweiten Spielers */
	public static BufferedImage iPlayerTwo;
	/** Bild des dritten Spielers */
	public static BufferedImage iPlayerThree;
	/** Bild des vierten Spielers */
	public static BufferedImage iPlayerFour;
	/** Bild der Anzeigt für die Zeit */
	public static BufferedImage iTime;
	/** Bild des load-Buttons */
	public static BufferedImage iLoad;
	/** Bild des play-Buttons */
	public static BufferedImage iPlay;
	/** Bild des stop-Buttons */
	public static BufferedImage iStop;
	/** Bild des new-Buttons */
	public static BufferedImage iNew;
	/** Bild des pause-Buttons */
	public static BufferedImage iPause;
	/** Bild des pause-Buttons */
	public static BufferedImage iQuit;
	/** Bild des pause-Buttons */
	public static BufferedImage iEditor;
	/** Bild des pause-Buttons */
	public static BufferedImage iGame;
	/** Bild des pause-Buttons */
	public static BufferedImage iLoadTree;
	/** Bild des pause-Buttons */
	public static BufferedImage iSaveTree;
	/** Bild des pause-Buttons */
	public static BufferedImage iGoalX;
	/** Bild des clear-Buttons */
	public static BufferedImage iClear;
	/** Bild des simulate-Buttons */
	public static BufferedImage iSimulate;
	/** Bild des help-Buttons */
	public static BufferedImage iHelp;
	
	/**
	 * statische Methode zum laden der Images
	 */
	public static final void setImages() {
		ApoSkunkmanImages images = new ApoSkunkmanImages();
		if (ApoSkunkmanImageContainer.iTileSkunk == null) {
			ApoSkunkmanImageContainer.iTileSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/level_org.png", ApoSkunkmanConstants.LOAD_EXTERN), false);
		}
		if (ApoSkunkmanImageContainer.iGoodieSkunk == null) {
			ApoSkunkmanImageContainer.iGoodieSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/goodie_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iFlameSkunk == null) {
			ApoSkunkmanImageContainer.iFlameSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/flame_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iBombSkunk == null) {
			ApoSkunkmanImageContainer.iBombSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/skunkman_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iHudSkunk == null) {
			ApoSkunkmanImageContainer.iHudSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/hud_background_org.png", ApoSkunkmanConstants.LOAD_EXTERN), false);
		}
		if (ApoSkunkmanImageContainer.iHudTreeSkunk == null) {
			ApoSkunkmanImageContainer.iHudTreeSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/hud_tree_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerOneSkunk == null) {
			ApoSkunkmanImageContainer.iPlayerOneSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_one_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerTwoSkunk == null) {
			ApoSkunkmanImageContainer.iPlayerTwoSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_two_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerThreeSkunk == null) {
			ApoSkunkmanImageContainer.iPlayerThreeSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_three_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerFourSkunk == null) {
			ApoSkunkmanImageContainer.iPlayerFourSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_four_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iTimeSkunk == null) {
			ApoSkunkmanImageContainer.iTimeSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/time_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iLoadSkunk == null) {
			ApoSkunkmanImageContainer.iLoadSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_load_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlaySkunk == null) {
			ApoSkunkmanImageContainer.iPlaySkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_play_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iNewSkunk == null) {
			ApoSkunkmanImageContainer.iNewSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_new_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPauseSkunk == null) {
			ApoSkunkmanImageContainer.iPauseSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_pause_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iStopSkunk == null) {
			ApoSkunkmanImageContainer.iStopSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_stop_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iQuitSkunk == null) {
			ApoSkunkmanImageContainer.iQuitSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_exit_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iEditorSkunk == null) {
			ApoSkunkmanImageContainer.iEditorSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iGameSkunk == null) {
			ApoSkunkmanImageContainer.iGameSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_game_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iLoadTreeSkunk == null) {
			ApoSkunkmanImageContainer.iLoadTreeSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor_load_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iSaveTreeSkunk == null) {
			ApoSkunkmanImageContainer.iSaveTreeSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor_save_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iClearSkunk == null) {
			ApoSkunkmanImageContainer.iClearSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_clear_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iGoalXSkunk == null) {
			ApoSkunkmanImageContainer.iGoalXSkunk = new BufferedImage(ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = ApoSkunkmanImageContainer.iGoalXSkunk.createGraphics();
			g.setStroke(new BasicStroke(2 * ApoSkunkmanConstants.APPLICATION_SIZE));
			g.setColor(Color.RED);
			g.drawLine(1, 1, ApoSkunkmanConstants.TILE_SIZE - 2, ApoSkunkmanConstants.TILE_SIZE - 2);
			g.drawLine(ApoSkunkmanConstants.TILE_SIZE - 2, 1, 1, ApoSkunkmanConstants.TILE_SIZE - 2);
			g.dispose();
		}
		if (ApoSkunkmanImageContainer.iSimulateSkunk == null) {
			ApoSkunkmanImageContainer.iSimulateSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_simulate2_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iHelpSkunk == null) {
			ApoSkunkmanImageContainer.iHelpSkunk = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_help2_org.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		
		ApoSkunkmanImageContainerAscii ascii = new ApoSkunkmanImageContainerAscii();
		if (ApoSkunkmanImageContainer.iTileAscii == null) {
			ApoSkunkmanImageContainer.iTileAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getTileAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iGoodieAscii == null) {
			ApoSkunkmanImageContainer.iGoodieAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getGoodieAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iFlameAscii == null) {
			ApoSkunkmanImageContainer.iFlameAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getFlameAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iBombAscii == null) {
			ApoSkunkmanImageContainer.iBombAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getSkunkmanAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iHudAscii == null) {
			ApoSkunkmanImageContainer.iHudAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getHudAscii(), true);
		}
		if (ApoSkunkmanImageContainer.iHudTreeAscii == null) {
			ApoSkunkmanImageContainer.iHudTreeAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getHudTreeAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iPlayerOneAscii == null) {
			ApoSkunkmanImageContainer.iPlayerOneAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getPlayerAscii(1), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerTwoAscii == null) {
			ApoSkunkmanImageContainer.iPlayerTwoAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getPlayerAscii(2), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerThreeAscii == null) {
			ApoSkunkmanImageContainer.iPlayerThreeAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getPlayerAscii(3), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerFourAscii == null) {
			ApoSkunkmanImageContainer.iPlayerFourAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getPlayerAscii(4), true);
		}
		if (ApoSkunkmanImageContainer.iTimeAscii == null) {
			ApoSkunkmanImageContainer.iTimeAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getHudTimeAscii(), false);
		}
		if (ApoSkunkmanImageContainer.iLoadAscii == null) {
			ApoSkunkmanImageContainer.iLoadAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("load"), false);
		}
		if (ApoSkunkmanImageContainer.iPlayAscii == null) {
			ApoSkunkmanImageContainer.iPlayAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("play"), false);
		}
		if (ApoSkunkmanImageContainer.iNewAscii == null) {
			ApoSkunkmanImageContainer.iNewAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("new"), false);
		}
		if (ApoSkunkmanImageContainer.iPauseAscii == null) {
			ApoSkunkmanImageContainer.iPauseAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("pause"), false);
		}
		if (ApoSkunkmanImageContainer.iStopAscii == null) {
			ApoSkunkmanImageContainer.iStopAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("stop"), false);
		}
		if (ApoSkunkmanImageContainer.iQuitAscii == null) {
			ApoSkunkmanImageContainer.iQuitAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithBigTree("quit"), false);
		}
		if (ApoSkunkmanImageContainer.iEditorAscii == null) {
			ApoSkunkmanImageContainer.iEditorAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithBigTree("editor"), false);
		}
		if (ApoSkunkmanImageContainer.iGameAscii == null) {
			ApoSkunkmanImageContainer.iGameAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithBigTree("game"), false);
		}
		if (ApoSkunkmanImageContainer.iLoadTreeAscii == null) {
			ApoSkunkmanImageContainer.iLoadTreeAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithBigTree("load"), false);
		}
		if (ApoSkunkmanImageContainer.iSaveTreeAscii == null) {
			ApoSkunkmanImageContainer.iSaveTreeAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithBigTree("save"), false);
		}
		if (ApoSkunkmanImageContainer.iClearAscii == null) {
			ApoSkunkmanImageContainer.iClearAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("clear"), false);
		}
		if (ApoSkunkmanImageContainer.iGoalXAscii == null) {
			ApoSkunkmanImageContainer.iGoalXAscii = ApoSkunkmanImageContainer.iGoalXSkunk;			
		}
		if (ApoSkunkmanImageContainer.iSimulateAscii == null) {
			ApoSkunkmanImageContainer.iSimulateAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("simulate"), false);
		}
		if (ApoSkunkmanImageContainer.iHelpAscii == null) {
			ApoSkunkmanImageContainer.iHelpAscii = ApoSkunkmanImageContainer.getCorrectSizeImage(ascii.getButtonWithLittleTree("help"), false);
		}
		
		if (ApoSkunkmanImageContainer.iTileAntje == null) {
			ApoSkunkmanImageContainer.iTileAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/level.png", ApoSkunkmanConstants.LOAD_EXTERN), false);
		}
		if (ApoSkunkmanImageContainer.iGoodieAntje == null) {
			ApoSkunkmanImageContainer.iGoodieAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/goodie.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iFlameAntje == null) {
			ApoSkunkmanImageContainer.iFlameAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/flame.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iBombAntje == null) {
			ApoSkunkmanImageContainer.iBombAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/skunkman.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iHudAntje == null) {
			ApoSkunkmanImageContainer.iHudAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/hud_background.png", ApoSkunkmanConstants.LOAD_EXTERN), false);
		}
		if (ApoSkunkmanImageContainer.iHudTreeAntje == null) {
			ApoSkunkmanImageContainer.iHudTreeAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/hud_tree.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerOneAntje == null) {
			ApoSkunkmanImageContainer.iPlayerOneAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_one.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerTwoAntje == null) {
			ApoSkunkmanImageContainer.iPlayerTwoAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_two.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerThreeAntje == null) {
			ApoSkunkmanImageContainer.iPlayerThreeAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_three.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayerFourAntje == null) {
			ApoSkunkmanImageContainer.iPlayerFourAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/player_four.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iTimeAntje == null) {
			ApoSkunkmanImageContainer.iTimeAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/time.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iLoadAntje == null) {
			ApoSkunkmanImageContainer.iLoadAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_load.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPlayAntje == null) {
			ApoSkunkmanImageContainer.iPlayAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_play.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iNewAntje == null) {
			ApoSkunkmanImageContainer.iNewAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_new.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iPauseAntje == null) {
			ApoSkunkmanImageContainer.iPauseAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_pause.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iStopAntje == null) {
			ApoSkunkmanImageContainer.iStopAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_stop.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iQuitAntje == null) {
			ApoSkunkmanImageContainer.iQuitAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_exit.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iEditorAntje == null) {
			ApoSkunkmanImageContainer.iEditorAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iGameAntje == null) {
			ApoSkunkmanImageContainer.iGameAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_game.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iLoadTreeAntje == null) {
			ApoSkunkmanImageContainer.iLoadTreeAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor_load.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iSaveTreeAntje == null) {
			ApoSkunkmanImageContainer.iSaveTreeAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_editor_save.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iClearAntje == null) {
			ApoSkunkmanImageContainer.iClearAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_clear.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iGoalXAntje == null) {
			ApoSkunkmanImageContainer.iGoalXAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/schatz.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iSimulateAntje == null) {
			ApoSkunkmanImageContainer.iSimulateAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_simulate2.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		if (ApoSkunkmanImageContainer.iHelpAntje == null) {
			ApoSkunkmanImageContainer.iHelpAntje = ApoSkunkmanImageContainer.getCorrectSizeImage(images.getImage("images/buttons/button_help2.png", ApoSkunkmanConstants.LOAD_EXTERN), true);
		}
		
		ApoSkunkmanImageContainer.setTileset(ApoSkunkmanConstants.LEVEL_TILESET);
	}
	
	
	
	/**
	 * statische Methode um aus einem geladenen Bild ein performantes Bild zu machen<br />
	 * Siehe auch http://www.jhlabs.com/ip/managed_images.html <br />
	 * Außerdem gibt es das Bild auch gleich in der richtigen Größe zurück
	 * @param iOriginal : Originalbild
	 * @param transparent : boolean Variable, die angibt, ob das Bild transparente Teile bestitzt
	 * @return das neue performante, skalierte Bild
	 */
	private static BufferedImage getCorrectSizeImage(BufferedImage iOriginal, boolean transparent) {
		int type = iOriginal.getType();
		if (transparent) {
			type = BufferedImage.TYPE_INT_ARGB_PRE;
		} else {
			type = BufferedImage.TYPE_INT_RGB;				
		}
		BufferedImage iNew;
		if (ApoSkunkmanConstants.APPLICATION_SIZE == 1) {
			try {
				iNew = new BufferedImage(iOriginal.getWidth()/2, iOriginal.getHeight()/2, type);
			} catch (Exception ex) {
				iNew = new BufferedImage(iOriginal.getWidth()/2, iOriginal.getHeight()/2, BufferedImage.TYPE_INT_RGB);
			}
			if (iNew != null) {
				Graphics2D g = iNew.createGraphics();
				
				g.drawImage(iOriginal.getScaledInstance(iNew.getWidth(), iNew.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
				
				g.dispose();
			}
		} else {
			try {
				iNew = new BufferedImage(iOriginal.getWidth(), iOriginal.getHeight(), type);
			} catch (Exception ex) {
				iNew = new BufferedImage(iOriginal.getWidth(), iOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
			}
			if (iNew != null) {
				Graphics2D g = iNew.createGraphics();
				
				g.drawImage(iOriginal, 0, 0, null);
				
				g.dispose();
			}
			
		}
		return iNew;
	}
	
	private static void setTileset(String tileSet) {
		if (tileSet.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			ApoSkunkmanImageContainer.iTile = ApoSkunkmanImageContainer.iTileAscii;
			ApoSkunkmanImageContainer.iGoodie = ApoSkunkmanImageContainer.iGoodieAscii;
			ApoSkunkmanImageContainer.iFlame = ApoSkunkmanImageContainer.iFlameAscii;
			ApoSkunkmanImageContainer.iBomb = ApoSkunkmanImageContainer.iBombAscii;
			ApoSkunkmanImageContainer.iHud = ApoSkunkmanImageContainer.iHudAscii;
			ApoSkunkmanImageContainer.iHudTree = ApoSkunkmanImageContainer.iHudTreeAscii;
			ApoSkunkmanImageContainer.iPlayerOne = ApoSkunkmanImageContainer.iPlayerOneAscii;
			ApoSkunkmanImageContainer.iPlayerTwo = ApoSkunkmanImageContainer.iPlayerTwoAscii;
			ApoSkunkmanImageContainer.iPlayerThree = ApoSkunkmanImageContainer.iPlayerThreeAscii;
			ApoSkunkmanImageContainer.iPlayerFour = ApoSkunkmanImageContainer.iPlayerFourAscii;
			ApoSkunkmanImageContainer.iTime = ApoSkunkmanImageContainer.iTimeAscii;
			ApoSkunkmanImageContainer.iLoad = ApoSkunkmanImageContainer.iLoadAscii;
			ApoSkunkmanImageContainer.iPlay = ApoSkunkmanImageContainer.iPlayAscii;
			ApoSkunkmanImageContainer.iNew = ApoSkunkmanImageContainer.iNewAscii;
			ApoSkunkmanImageContainer.iPause = ApoSkunkmanImageContainer.iPauseAscii;
			ApoSkunkmanImageContainer.iStop = ApoSkunkmanImageContainer.iStopAscii;
			ApoSkunkmanImageContainer.iQuit = ApoSkunkmanImageContainer.iQuitAscii;
			ApoSkunkmanImageContainer.iEditor = ApoSkunkmanImageContainer.iEditorAscii;
			ApoSkunkmanImageContainer.iGame = ApoSkunkmanImageContainer.iGameAscii;
			ApoSkunkmanImageContainer.iLoadTree = ApoSkunkmanImageContainer.iLoadTreeAscii;
			ApoSkunkmanImageContainer.iSaveTree = ApoSkunkmanImageContainer.iSaveTreeAscii;
			ApoSkunkmanImageContainer.iClear = ApoSkunkmanImageContainer.iClearAscii;
			ApoSkunkmanImageContainer.iGoalX = ApoSkunkmanImageContainer.iGoalXAscii;
			ApoSkunkmanImageContainer.iSimulate = ApoSkunkmanImageContainer.iSimulateAscii;
			ApoSkunkmanImageContainer.iHelp = ApoSkunkmanImageContainer.iHelpAscii;			
		} else if (tileSet.equals(ApoSkunkmanConstants.LEVEL_TILESETS[2])) {
			ApoSkunkmanImageContainer.iTile = ApoSkunkmanImageContainer.iTileAntje;
			ApoSkunkmanImageContainer.iGoodie = ApoSkunkmanImageContainer.iGoodieAntje;
			ApoSkunkmanImageContainer.iFlame = ApoSkunkmanImageContainer.iFlameAntje;
			ApoSkunkmanImageContainer.iBomb = ApoSkunkmanImageContainer.iBombAntje;
			ApoSkunkmanImageContainer.iHud = ApoSkunkmanImageContainer.iHudAntje;
			ApoSkunkmanImageContainer.iHudTree = ApoSkunkmanImageContainer.iHudTreeAntje;
			ApoSkunkmanImageContainer.iPlayerOne = ApoSkunkmanImageContainer.iPlayerOneAntje;
			ApoSkunkmanImageContainer.iPlayerTwo = ApoSkunkmanImageContainer.iPlayerTwoAntje;
			ApoSkunkmanImageContainer.iPlayerThree = ApoSkunkmanImageContainer.iPlayerThreeAntje;
			ApoSkunkmanImageContainer.iPlayerFour = ApoSkunkmanImageContainer.iPlayerFourAntje;
			ApoSkunkmanImageContainer.iTime = ApoSkunkmanImageContainer.iTimeAntje;
			ApoSkunkmanImageContainer.iLoad = ApoSkunkmanImageContainer.iLoadAntje;
			ApoSkunkmanImageContainer.iPlay = ApoSkunkmanImageContainer.iPlayAntje;
			ApoSkunkmanImageContainer.iNew = ApoSkunkmanImageContainer.iNewAntje;
			ApoSkunkmanImageContainer.iPause = ApoSkunkmanImageContainer.iPauseAntje;
			ApoSkunkmanImageContainer.iStop = ApoSkunkmanImageContainer.iStopAntje;
			ApoSkunkmanImageContainer.iQuit = ApoSkunkmanImageContainer.iQuitAntje;
			ApoSkunkmanImageContainer.iEditor = ApoSkunkmanImageContainer.iEditorAntje;
			ApoSkunkmanImageContainer.iGame = ApoSkunkmanImageContainer.iGameAntje;
			ApoSkunkmanImageContainer.iLoadTree = ApoSkunkmanImageContainer.iLoadTreeAntje;
			ApoSkunkmanImageContainer.iSaveTree = ApoSkunkmanImageContainer.iSaveTreeAntje;
			ApoSkunkmanImageContainer.iClear = ApoSkunkmanImageContainer.iClearAntje;
			ApoSkunkmanImageContainer.iGoalX = ApoSkunkmanImageContainer.iGoalXAntje;
			ApoSkunkmanImageContainer.iSimulate = ApoSkunkmanImageContainer.iSimulateAntje;
			ApoSkunkmanImageContainer.iHelp = ApoSkunkmanImageContainer.iHelpAntje;		
		} else {
			ApoSkunkmanImageContainer.iTile = ApoSkunkmanImageContainer.iTileSkunk;
			ApoSkunkmanImageContainer.iGoodie = ApoSkunkmanImageContainer.iGoodieSkunk;
			ApoSkunkmanImageContainer.iFlame = ApoSkunkmanImageContainer.iFlameSkunk;
			ApoSkunkmanImageContainer.iBomb = ApoSkunkmanImageContainer.iBombSkunk;
			ApoSkunkmanImageContainer.iHud = ApoSkunkmanImageContainer.iHudSkunk;
			ApoSkunkmanImageContainer.iHudTree = ApoSkunkmanImageContainer.iHudTreeSkunk;
			ApoSkunkmanImageContainer.iPlayerOne = ApoSkunkmanImageContainer.iPlayerOneSkunk;
			ApoSkunkmanImageContainer.iPlayerTwo = ApoSkunkmanImageContainer.iPlayerTwoSkunk;
			ApoSkunkmanImageContainer.iPlayerThree = ApoSkunkmanImageContainer.iPlayerThreeSkunk;
			ApoSkunkmanImageContainer.iPlayerFour = ApoSkunkmanImageContainer.iPlayerFourSkunk;
			ApoSkunkmanImageContainer.iTime = ApoSkunkmanImageContainer.iTimeSkunk;
			ApoSkunkmanImageContainer.iLoad = ApoSkunkmanImageContainer.iLoadSkunk;
			ApoSkunkmanImageContainer.iPlay = ApoSkunkmanImageContainer.iPlaySkunk;
			ApoSkunkmanImageContainer.iNew = ApoSkunkmanImageContainer.iNewSkunk;
			ApoSkunkmanImageContainer.iPause = ApoSkunkmanImageContainer.iPauseSkunk;
			ApoSkunkmanImageContainer.iStop = ApoSkunkmanImageContainer.iStopSkunk;
			ApoSkunkmanImageContainer.iQuit = ApoSkunkmanImageContainer.iQuitSkunk;
			ApoSkunkmanImageContainer.iEditor = ApoSkunkmanImageContainer.iEditorSkunk;
			ApoSkunkmanImageContainer.iGame = ApoSkunkmanImageContainer.iGameSkunk;
			ApoSkunkmanImageContainer.iLoadTree = ApoSkunkmanImageContainer.iLoadTreeSkunk;
			ApoSkunkmanImageContainer.iSaveTree = ApoSkunkmanImageContainer.iSaveTreeSkunk;
			ApoSkunkmanImageContainer.iClear = ApoSkunkmanImageContainer.iClearSkunk;
			ApoSkunkmanImageContainer.iGoalX = ApoSkunkmanImageContainer.iGoalXSkunk;
			ApoSkunkmanImageContainer.iSimulate = ApoSkunkmanImageContainer.iSimulateSkunk;
			ApoSkunkmanImageContainer.iHelp = ApoSkunkmanImageContainer.iHelpSkunk;
		}
	}
}
