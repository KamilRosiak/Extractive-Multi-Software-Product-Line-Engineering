package apoSkunkman.debug;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanPlayer;
import apoSkunkman.game.ApoSkunkmanPanel;

/**
 * Das Debugframe zur Analyse
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanDebug extends JFrame {

	private static final long serialVersionUID = 1L;
	/** gibt die Breite des Frames zurück */
	public static final int FRAME_WIDTH = 800;
	/** gibt die Breite des Frames zurück */
	public static final int FRAME_HEIGHT = 600;
	/** die eigentliche Komponente */
	private JComponent component;
	/** das Spielobjekt */
	private final ApoSkunkmanPanel panel;
	/** Array mit JLabels der Spielerwerte */
	private JLabel[][] playerValues;
	/** Area für die Textausgabe */
	private JTextArea area;
	/** scrollPane für die Area */
	private JScrollPane scrollPane;

	public ApoSkunkmanDebug(final ApoSkunkmanPanel panel) {
		this.panel = panel;
		
		this.setTitle("=== ApoSkunkman DebugFrame ===");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		this.setLayout(null);
		this.component	= (JComponent)this.getContentPane();
		this.component.setPreferredSize(new Dimension(ApoSkunkmanDebug.FRAME_WIDTH, ApoSkunkmanDebug.FRAME_HEIGHT));
		this.component.setLayout(null);
		this.init();
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * initialisiert alle JLabels und die textarea auf dem Fenster
	 */
	private void init() {
		JLabel labelPlayerOne = new JLabel("Player One");
		labelPlayerOne.setBounds(150, 5, 150, 25);
		this.component.add(labelPlayerOne);
		
		JLabel labelPlayerTwo = new JLabel("Player Two");
		labelPlayerTwo.setBounds(300, 5, 100, 25);
		this.component.add(labelPlayerTwo);
		
		JLabel labelPlayerThree = new JLabel("Player Three");
		labelPlayerThree.setBounds(450, 5, 100, 25);
		this.component.add(labelPlayerThree);
		
		JLabel labelPlayerFour = new JLabel("Player Four");
		labelPlayerFour.setBounds(600, 5, 100, 25);
		this.component.add(labelPlayerFour);
		
		JLabel name = new JLabel("Name");
		name.setBounds(5, 35, 100, 25);
		this.component.add(name);
		
		JLabel position = new JLabel("Position");
		position.setBounds(5, 60, 100, 25);
		this.component.add(position);
		
		JLabel speed = new JLabel("Speed");
		speed.setBounds(5, 85, 100, 25);
		this.component.add(speed);
		
		JLabel maxSkunk = new JLabel("MaxSkunk");
		maxSkunk.setBounds(5, 110, 100, 25);
		this.component.add(maxSkunk);
		
		JLabel curSkunk = new JLabel("curSkunk");
		curSkunk.setBounds(5, 135, 100, 25);
		this.component.add(curSkunk);

		JLabel skunkWidth = new JLabel("skunkWidth");
		skunkWidth.setBounds(5, 160, 100, 25);
		this.component.add(skunkWidth);

		JLabel canLay = new JLabel("canLaySkunkman");
		canLay.setBounds(5, 185, 100, 25);
		this.component.add(canLay);

		JLabel velocity = new JLabel("Velocity");
		velocity.setBounds(5, 210, 100, 25);
		this.component.add(velocity);

		JLabel moving = new JLabel("isMoving");
		moving.setBounds(5, 235, 100, 25);
		this.component.add(moving);
		
		this.playerValues = new JLabel[4][9];
		for (int player = 0; player < this.playerValues.length; player++) {
			for (int values = 0; values < this.playerValues[0].length; values++) {
				this.playerValues[player][values] = new JLabel("");
				this.playerValues[player][values].setBounds(150 + player * 150, 35 + values * 25, 150, 25);
				this.component.add(this.playerValues[player][values]);
			}
		}
		
		this.area = new JTextArea();
		this.area.setLineWrap(true);
		this.area.setEditable(false);

		this.scrollPane = new JScrollPane(this.area);
		this.scrollPane.setWheelScrollingEnabled(true);
		int y = this.playerValues[0][this.playerValues[0].length - 1].getY() + this.playerValues[0][this.playerValues[0].length - 1].getHeight() + 5;
		this.scrollPane.setBounds(5, y, ApoSkunkmanDebug.FRAME_WIDTH - 2 * 5, ApoSkunkmanDebug.FRAME_HEIGHT - 5 - y);
		this.component.add(this.scrollPane);
	}
	
	/**
	 * aktualisiert das Debugframe mit den neuen Werten
	 */
	public void refresh() {	
		for (int i = 0; i < this.panel.getLevel().getMaxPlayers(); i++) {
			ApoSkunkmanPlayer player = this.panel.getLevel().getPlayers()[i];
			this.playerValues[i][0].setText(player.getPlayerName()+" ("+player.getPlayer()+")");
			this.playerValues[i][1].setText((player.getX()/(float)ApoSkunkmanConstants.TILE_SIZE)+", "+(player.getY()/(float)ApoSkunkmanConstants.TILE_SIZE));
			this.playerValues[i][2].setText(String.valueOf(player.getSpeed()*(float)ApoSkunkmanConstants.APPLICATION_SIZE));
			this.playerValues[i][3].setText(String.valueOf(player.getMaxSkunkman()));
			this.playerValues[i][4].setText(String.valueOf(player.getCurSkunkman()));
			this.playerValues[i][5].setText(String.valueOf(player.getCurWidth()));
			this.playerValues[i][6].setText(String.valueOf(player.canLaySkunkman()));
			this.playerValues[i][7].setText(String.valueOf(player.getVelocityX() * player.getSpeed() * (float)ApoSkunkmanConstants.APPLICATION_SIZE + ", " + player.getVelocityY() * player.getSpeed() * (float)ApoSkunkmanConstants.APPLICATION_SIZE));
			this.playerValues[i][8].setText(String.valueOf(player.isMoving()));
		}
	}
	
	/**
	 * fügt einen String zur Textarea hinzu
	 * @param addString : neue Message
	 */
	public void addString(String addString) {
		this.area.append(addString + "\n");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
	}
	
	/**
	 * löscht alle Werte und setzt die auf "Leer"
	 */
	public void clear() {
		this.area.setText("");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
		
		for (int player = 0; player < this.playerValues.length; player++) {
			for (int values = 0; values < this.playerValues[0].length; values++) {
				this.playerValues[player][values].setText(""); 
			}
		}
	}
}
