package apoMario.game;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import apoMario.ApoMarioConstants;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;
import apoMario.entity.ApoMarioEntity;
import apoMario.entity.ApoMarioPlayer;

/**
 * Klasse, die das Debugframe initialisiert und alle wichtige Daten beinhaltet
 * @author Dirk Aporius
 *
 */
public class ApoMarioDebugFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ApoMarioPanel game;
	
	private JLabel 	playerOneName, playerOnePoints, playerOneCoins, playerOneX, playerOneY, playerOneWidth, playerOneHeight, playerOneDirection, playerOneSprint, playerOneJump,
					playerOneVecX, playerOneVecY, playerOneType;
	
	private JLabel 	playerTwoName, playerTwoPoints, playerTwoCoins, playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight, playerTwoDirection, playerTwoSprint, playerTwoJump,
					playerTwoVecX, playerTwoVecY, playerTwoType;
	
	private JTextArea area;
	
	private JScrollPane scrollPane;
	
	private ApoMarioDebugPanel debugPanel;
	
	public ApoMarioDebugFrame(ApoMarioPanel game) {
		this.game = game;
		
		this.setTitle("=== ApoMario DebugFrame ===");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		this.setLayout(null);
		JComponent currentComponent	= (JComponent)this.getContentPane();
		currentComponent.setPreferredSize(new Dimension(850, 480));
		currentComponent.setLayout(null);
		this.init();
		this.pack();
		this.setVisible(true);
	}
	
	public void init() {
		this.area = new JTextArea();
		this.area.setLineWrap(true);
		this.area.setEditable(false);

		this.scrollPane = new JScrollPane(this.area);
		this.scrollPane.setWheelScrollingEnabled(true);
		this.scrollPane.setSize(830, 123);
		this.scrollPane.setLocation(5, 355);
		this.add(this.scrollPane);
		
		this.debugPanel = new ApoMarioDebugPanel();
		this.debugPanel.setBounds(300, 80, 300, 250);
		this.add(this.debugPanel);
		
		int nextWidth = 600;
		
		int width = 80;
		
		JLabel label = new JLabel("Player One:");
		label.setLocation(3 + width + 10, 5);
		label.setSize(nextWidth, 20);
		this.add(label);
		
		label = new JLabel("Player Two:");
		label.setLocation(3 + width + nextWidth + 10, 5);
		label.setSize(nextWidth, 20);
		this.add(label);
		
		label = new JLabel("Name:");
		label.setLocation(3, 30);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Points:");
		label.setLocation(3, 30 + 1 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Coins:");
		label.setLocation(3, 30 + 2 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("x-Value:");
		label.setLocation(3, 30 + 3 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("y-Value:");
		label.setLocation(3, 30 + 4 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Width:");
		label.setLocation(3, 30 + 5 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Height:");
		label.setLocation(3, 30 + 6 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Direction:");
		label.setLocation(3, 30 + 7 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Sprint:");
		label.setLocation(3, 30 + 8 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Jump:");
		label.setLocation(3, 30 + 9 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("VecX:");
		label.setLocation(3, 30 + 10 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("VecY:");
		label.setLocation(3, 30 + 11 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		label = new JLabel("Type:");
		label.setLocation(3, 30 + 12 * 25);
		label.setSize(width, 20);
		this.add(label);
		
		this.playerOneName = new JLabel("");
		this.playerOneName.setLocation(3 + width + 10, 30 + 0 * 25);
		this.playerOneName.setSize(nextWidth, 20);
		this.add(this.playerOneName);
		
		this.playerOnePoints = new JLabel("");
		this.playerOnePoints.setLocation(3 + width + 10, 30 + 1 * 25);
		this.playerOnePoints.setSize(nextWidth, 20);
		this.add(this.playerOnePoints);
		
		this.playerOneCoins = new JLabel("");
		this.playerOneCoins.setLocation(3 + width + 10, 30 + 2 * 25);
		this.playerOneCoins.setSize(nextWidth, 20);
		this.add(this.playerOneCoins);
		
		this.playerOneX = new JLabel("");
		this.playerOneX.setLocation(3 + width + 10, 30 + 3 * 25);
		this.playerOneX.setSize(nextWidth, 20);
		this.add(this.playerOneX);
		
		this.playerOneY = new JLabel("");
		this.playerOneY.setLocation(3 + width + 10, 30 + 4 * 25);
		this.playerOneY.setSize(nextWidth, 20);
		this.add(this.playerOneY);
		
		this.playerOneWidth = new JLabel("");
		this.playerOneWidth.setLocation(3 + width + 10, 30 + 5 * 25);
		this.playerOneWidth.setSize(nextWidth, 20);
		this.add(this.playerOneWidth);
		
		this.playerOneHeight = new JLabel("");
		this.playerOneHeight.setLocation(3 + width + 10, 30 + 6 * 25);
		this.playerOneHeight.setSize(nextWidth, 20);
		this.add(this.playerOneHeight);
		
		this.playerOneDirection = new JLabel("");
		this.playerOneDirection.setLocation(3 + width + 10, 30 + 7 * 25);
		this.playerOneDirection.setSize(nextWidth, 20);
		this.add(this.playerOneDirection);
		
		this.playerOneSprint = new JLabel("");
		this.playerOneSprint.setLocation(3 + width + 10, 30 + 8 * 25);
		this.playerOneSprint.setSize(nextWidth, 20);
		this.add(this.playerOneSprint);
		
		this.playerOneJump = new JLabel("");
		this.playerOneJump.setLocation(3 + width + 10, 30 + 9 * 25);
		this.playerOneJump.setSize(nextWidth, 20);
		this.add(this.playerOneJump);
		
		this.playerOneVecX = new JLabel("");
		this.playerOneVecX.setLocation(3 + width + 10, 30 + 10 * 25);
		this.playerOneVecX.setSize(nextWidth, 20);
		this.add(this.playerOneVecX);
		
		this.playerOneVecY = new JLabel("");
		this.playerOneVecY.setLocation(3 + width + 10, 30 + 11 * 25);
		this.playerOneVecY.setSize(nextWidth, 20);
		this.add(this.playerOneVecY);
		
		this.playerOneType = new JLabel("");
		this.playerOneType.setLocation(3 + width + 10, 30 + 12 * 25);
		this.playerOneType.setSize(nextWidth, 20);
		this.add(this.playerOneType);
		
		
		this.playerTwoName = new JLabel("");
		this.playerTwoName.setLocation(3 + width + nextWidth + 10, 30 + 0 * 25);
		this.playerTwoName.setSize(nextWidth, 20);
		this.add(this.playerTwoName);
		
		this.playerTwoPoints = new JLabel("");
		this.playerTwoPoints.setLocation(3 + width + nextWidth + 10, 30 + 1 * 25);
		this.playerTwoPoints.setSize(nextWidth, 20);
		this.add(this.playerTwoPoints);
		
		this.playerTwoCoins = new JLabel("");
		this.playerTwoCoins.setLocation(3 + width + nextWidth + 10, 30 + 2 * 25);
		this.playerTwoCoins.setSize(nextWidth, 20);
		this.add(this.playerTwoCoins);
		
		this.playerTwoX = new JLabel("");
		this.playerTwoX.setLocation(3 + width + nextWidth + 10, 30 + 3 * 25);
		this.playerTwoX.setSize(nextWidth, 20);
		this.add(this.playerTwoX);
		
		this.playerTwoY = new JLabel("");
		this.playerTwoY.setLocation(3 + width + nextWidth + 10, 30 + 4 * 25);
		this.playerTwoY.setSize(nextWidth, 20);
		this.add(this.playerTwoY);
		
		this.playerTwoWidth = new JLabel("");
		this.playerTwoWidth.setLocation(3 + width + nextWidth + 10, 30 + 5 * 25);
		this.playerTwoWidth.setSize(nextWidth, 20);
		this.add(this.playerTwoWidth);
		
		this.playerTwoHeight = new JLabel("");
		this.playerTwoHeight.setLocation(3 + width + nextWidth + 10, 30 + 6 * 25);
		this.playerTwoHeight.setSize(nextWidth, 20);
		this.add(this.playerTwoHeight);
		
		this.playerTwoDirection = new JLabel("");
		this.playerTwoDirection.setLocation(3 + width + nextWidth + 10, 30 + 7 * 25);
		this.playerTwoDirection.setSize(nextWidth, 20);
		this.add(this.playerTwoDirection);
		
		this.playerTwoSprint = new JLabel("");
		this.playerTwoSprint.setLocation(3 + width + nextWidth + 10, 30 + 8 * 25);
		this.playerTwoSprint.setSize(nextWidth, 20);
		this.add(this.playerTwoSprint);
		
		this.playerTwoJump = new JLabel("");
		this.playerTwoJump.setLocation(3 + width + nextWidth + 10, 30 + 9 * 25);
		this.playerTwoJump.setSize(nextWidth, 20);
		this.add(this.playerTwoJump);
		
		this.playerTwoVecX = new JLabel("");
		this.playerTwoVecX.setLocation(3 + width + nextWidth + 10, 30 + 10 * 25);
		this.playerTwoVecX.setSize(nextWidth, 20);
		this.add(this.playerTwoVecX);
		
		this.playerTwoVecY = new JLabel("");
		this.playerTwoVecY.setLocation(3 + width + nextWidth + 10, 30 + 11 * 25);
		this.playerTwoVecY.setSize(nextWidth, 20);
		this.add(this.playerTwoVecY);
		
		this.playerTwoType = new JLabel("");
		this.playerTwoType.setLocation(3 + width + nextWidth + 10, 30 + 12 * 25);
		this.playerTwoType.setSize(nextWidth, 20);
		this.add(this.playerTwoType);
	}
	
	public void refresh() {	
		int startX = (int)((this.game.getChangeX()) / ApoMarioConstants.TILE_SIZE);
		if (startX < 0) {
			startX = 0;
		}
		startX = startX * ApoMarioConstants.TILE_SIZE;
		
		ApoMarioPlayer player = this.game.getLevel().getPlayers().get(0);
		if (player.getAi() == null) {
			this.playerOneName.setText("human");
		} else {
			this.playerOneName.setText(player.getAi().getTeamName()+" ("+(player.getPlayer()+1)+")");
		}
		this.playerOnePoints.setText(String.valueOf(player.getPoints()));
		this.playerOneCoins.setText(String.valueOf(player.getCoins()));
		this.playerOneSprint.setText(String.valueOf(player.isBSpeed()));
		this.playerOneJump.setText(String.valueOf(player.isBJump()));
		this.playerOneX.setText(String.valueOf((player.getRec().x - startX) / ApoMarioConstants.TILE_SIZE));
		this.playerOneY.setText(String.valueOf((player.getRec().y - this.game.getChangeY()) / ApoMarioConstants.TILE_SIZE));
		this.playerOneWidth.setText(String.valueOf((player.getRec().width) / ApoMarioConstants.TILE_SIZE));
		this.playerOneHeight.setText(String.valueOf((player.getRec().height) / ApoMarioConstants.TILE_SIZE));
		if (player.getDirection() == ApoMarioEntity.LEFT) {
			this.playerOneDirection.setText("left");
		} else {
			this.playerOneDirection.setText("right");
		}
		float vecX = (player.getVecX()) / ApoMarioConstants.TILE_SIZE;
		this.playerOneVecX.setText(String.valueOf(vecX));
		float vecY = (player.getVecY()) / ApoMarioConstants.TILE_SIZE;
		this.playerOneVecY.setText(String.valueOf(vecY));
		if (player.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
			this.playerOneType.setText("little");
		} else if (player.getType() == ApoMarioConstants.PLAYER_TYPE_FIRE) {
			this.playerOneType.setText("fire");
		} else if (player.getType() == ApoMarioConstants.PLAYER_TYPE_BIG) {
			this.playerOneType.setText("big");
		}
		
		player = this.game.getLevel().getPlayers().get(1);
		if (player.getAi() != null) {
			this.playerTwoName.setText(player.getAi().getTeamName()+" ("+(player.getPlayer()+1)+")");
			this.playerTwoPoints.setText(String.valueOf(player.getPoints()));
			this.playerTwoCoins.setText(String.valueOf(player.getCoins()));
			this.playerTwoSprint.setText(String.valueOf(player.isBSpeed()));
			this.playerTwoJump.setText(String.valueOf(player.isBJump()));
			this.playerTwoX.setText(String.valueOf((player.getRec().x - startX) / ApoMarioConstants.TILE_SIZE));
			this.playerTwoY.setText(String.valueOf((player.getRec().y - this.game.getChangeY()) / ApoMarioConstants.TILE_SIZE));
			this.playerTwoWidth.setText(String.valueOf((player.getRec().width) / ApoMarioConstants.TILE_SIZE));
			this.playerTwoHeight.setText(String.valueOf((player.getRec().height) / ApoMarioConstants.TILE_SIZE));
			if (player.getDirection() == ApoMarioEntity.LEFT) {
				this.playerTwoDirection.setText("left");
			} else {
				this.playerTwoDirection.setText("right");
			}
			vecX = (player.getVecX()) / ApoMarioConstants.TILE_SIZE;
			this.playerTwoVecX.setText(String.valueOf(vecX));
			vecY = (player.getVecY()) / ApoMarioConstants.TILE_SIZE;
			this.playerTwoVecY.setText(String.valueOf(vecY));
			if (player.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
				this.playerTwoType.setText("little");
			} else if (player.getType() == ApoMarioConstants.PLAYER_TYPE_FIRE) {
				this.playerTwoType.setText("fire");
			} else if (player.getType() == ApoMarioConstants.PLAYER_TYPE_BIG) {
				this.playerTwoType.setText("big");
			}
		}
		this.debugPanel.setLevel(new ApoMarioAIPlayer(this.game.getLevel().getPlayers().get(this.game.getPlayerSelectCamera()), this.game.getLevel()), new ApoMarioAILevel(this.game.getLevel(), this.game.getPlayerSelectCamera()));
	}
	
	public void addString(String addString) {
		this.area.append(addString + "\n");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
	}
	
	public void clear() {
		this.area.setText("");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
		this.playerTwoName.setText("");
		this.playerTwoPoints.setText("");
		this.playerTwoCoins.setText("");
		this.playerTwoSprint.setText("");
		this.playerTwoJump.setText("");
		this.playerTwoX.setText("");
		this.playerTwoY.setText("");
		this.playerTwoWidth.setText("");
		this.playerTwoHeight.setText("");
		this.playerTwoDirection.setText("");
		this.playerTwoVecX.setText("");
		this.playerTwoVecY.setText("");
		this.playerTwoType.setText("");
	}
	
}
