package apoMario.analysis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import apoMario.ApoMarioConstants;
import apoMario.game.ApoMarioPanel;
import apoMario.level.ApoMarioLevel;

/**
 * AnalyseKlasse<br />
 * Extrafenster, um ein Spiel zu simulieren ohne die eigentliche grafische Ausgabe<br />
 * @author Dirk Aporius
 */
public class ApoMarioAnalysis extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;

	private ApoMarioPanel game;
	
	public final int FRAME_WIDTH = 850;
	public final int FRAME_HEIGHT = 480;
	
	private JTextField fieldRounds, fieldWidth, fieldDifficulty, fieldRandom;
	
	private JLabel labelAIOne, labelAITwo;
	
	private JButton buttonStart, buttonStop, buttonLoadAIOne, buttonLoadAITwo, unloadAITwo;
	
	private JRadioButton sameLevel;
	
	private JTextArea area;
	
	private JScrollPane scrollPane;
	
	private int value, width, difficulty;
	
	private boolean bStart;
	
	public static void main(String[] args) {
		new ApoMarioAnalysis();
	}
	
	public ApoMarioAnalysis() {
		this.setTitle("=== ApoMario Analysis ===");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.setLayout(null);
		JComponent currentComponent	= (JComponent)this.getContentPane();
		currentComponent.setPreferredSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
		currentComponent.setLayout(null);
		this.init();
		this.pack();
		this.setVisible(true);
	}
	
	public void init() {
		if (this.game == null) {
			this.game = new ApoMarioPanel();
			this.game.init();
			this.game.getLevel().changePlayerOneAI(0);
			this.game.setVisible(false);
		}
		int height = 20;
		this.area = new JTextArea();
		this.area.setLineWrap(true);
		this.area.setEditable(false);

		this.scrollPane = new JScrollPane(this.area);
		this.scrollPane.setWheelScrollingEnabled(true);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.setSize(this.FRAME_WIDTH - 10, this.FRAME_HEIGHT - 2 * 5 - 2 * height);
		this.scrollPane.setLocation(5, 2 * 5 + 2 * height);
		this.add(this.scrollPane);
		
		int width = 80;
		JLabel label = new JLabel("Rounds: ");
		label.setLocation(5, 5);
		label.setSize(width, 20);
		this.add(label);
		
		this.fieldRounds = new JTextField("1000");
		this.fieldRounds.setLocation(5 + 1*width, 5);
		this.fieldRounds.setSize(width, 20);
		this.add(this.fieldRounds);
		
		this.buttonStart = new JButton("Start");
		this.buttonStart.setLocation(2*5 + 2*width, 5);
		this.buttonStart.setSize(width, 20);
		this.buttonStart.addActionListener(this);
		this.buttonStart.setVisible(true);
		this.add(this.buttonStart);
		
		this.buttonStop = new JButton("Stop");
		this.buttonStop.setLocation(2*5 + 2*width, 5);
		this.buttonStop.setSize(width, 20);
		this.buttonStop.addActionListener(this);
		this.buttonStop.setVisible(false);
		this.add(this.buttonStop);
		
		label = new JLabel("width: ");
		label.setLocation(3*5 + 3*width, 5);
		label.setSize(width, 20);
		this.add(label);
		
		this.fieldWidth = new JTextField("100");
		this.fieldWidth.setLocation(4*5 + 4*width, 5);
		this.fieldWidth.setSize(width, 20);
		this.add(this.fieldWidth);

		label = new JLabel("difficulty: ");
		label.setLocation(5*5 + 5*width, 5);
		label.setSize(width, 20);
		this.add(label);
		
		this.fieldDifficulty = new JTextField("0");
		this.fieldDifficulty.setLocation(6*5 + 6*width, 5);
		this.fieldDifficulty.setSize(width, 20);
		this.add(this.fieldDifficulty);
		
		label = new JLabel("random: ");
		label.setLocation(7*5 + 7*width, 5);
		label.setSize(width, 20);
		this.add(label);
		
		this.fieldRandom = new JTextField(String.valueOf(System.nanoTime()));
		this.fieldRandom.setLocation(8*5 + 8*width, 5);
		this.fieldRandom.setSize(width * 2, 20);
		this.add(this.fieldRandom);
		
		String s = "human";
		if (this.game.getLevel().getPlayers().get(0).getAi() != null) {
			s = this.game.getLevel().getPlayers().get(0).getAi().getTeamName();
		}
		this.labelAIOne = new JLabel(s);
		this.labelAIOne.setLocation(5, 2 * 5 + 1 * height);
		this.labelAIOne.setSize(width, 20);
		this.add(this.labelAIOne);
		
		this.buttonLoadAIOne = new JButton("load");
		this.buttonLoadAIOne.setLocation(1*5 + 1*width, 2 * 5 + 1 * height);
		this.buttonLoadAIOne.setSize(width, 20);
		this.buttonLoadAIOne.addActionListener(this);
		this.buttonLoadAIOne.setVisible(true);
		this.add(this.buttonLoadAIOne);
		
		JLabel empty = new JLabel("");
		empty.setLocation(2*5 + 2*width, 2 * 5 + 1 * height);
		empty.setSize(width, 20);
		empty.setVisible(true);
		this.add(empty);
		
		s = "no AI";
		if (this.game.getLevel().getPlayers().get(1).getAi() != null) {
			s = this.game.getLevel().getPlayers().get(1).getAi().getTeamName();
		}
		this.labelAITwo = new JLabel(s);
		this.labelAITwo.setLocation(3 * 5 + 3 * width, 2 * 5 + 1 * height);
		this.labelAITwo.setSize(width, 20);
		this.add(this.labelAITwo);
		
		this.buttonLoadAITwo = new JButton("load");
		this.buttonLoadAITwo.setLocation(4*5 + 4*width, 2 * 5 + 1 * height);
		this.buttonLoadAITwo.setSize(width, 20);
		this.buttonLoadAITwo.addActionListener(this);
		this.buttonLoadAITwo.setVisible(true);
		this.add(this.buttonLoadAITwo);
		
		this.unloadAITwo = new JButton("unload");
		this.unloadAITwo.setLocation(5*5 + 5*width, 2 * 5 + 1 * height);
		this.unloadAITwo.setSize(width, 20);
		this.unloadAITwo.addActionListener(this);
		this.unloadAITwo.setVisible(true);
		this.add(this.unloadAITwo);
		
		this.sameLevel = new JRadioButton("same Level");
		this.sameLevel.setLocation(6*5 + 6*width, 2 * 5 + 1 * height);
		this.sameLevel.setSize(width * 2, 20);
		this.sameLevel.addActionListener(this);
		this.sameLevel.setVisible(true);
		this.add(this.sameLevel);
		
		this.bStart = false;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.buttonStart)) {
			int value = 0;
			try {
				value = Integer.valueOf(this.fieldRounds.getText());
			} catch (NumberFormatException ex) {
				value = 0;
			}
			if (value < 0) {
				value = 0;
			}
			this.fieldRounds.setText(String.valueOf(value));
			int width = 100;
			try {
				width = Integer.valueOf(this.fieldWidth.getText());
			} catch (NumberFormatException ex) {
				width = 100;
			}
			if (width < 100) {
				width = 100;
			} else if (width > 2000) {
				width = 2000;
			}
			this.fieldWidth.setText(String.valueOf(width));
			int difficulty = 0;
			try {
				difficulty = Integer.valueOf(this.fieldDifficulty.getText());
			} catch (NumberFormatException ex) {
				difficulty = 0;
			}
			if (difficulty < 0) {
				difficulty = 0;
			} else if (difficulty > 700) {
				difficulty = 700;
			}
			this.fieldDifficulty.setText(String.valueOf(difficulty));
			if (value > 0) {
				this.buttonStart.setVisible(false);
				this.buttonStop.setVisible(true);
				this.startAnalysis(value, width, difficulty);
			}
		} else if (e.getSource().equals(this.buttonStop)) {
			if (this.bStart) {
				this.bStart = false;
			}
		} else if (e.getSource().equals(this.buttonLoadAIOne)) {
			if (!this.bStart) {
				this.game.getLevel().loadPlayer(0, null);
				this.setLabel(this.labelAIOne, 0);
			}
		} else if (e.getSource().equals(this.buttonLoadAITwo)) {
			if (!this.bStart) {
				this.game.getLevel().loadPlayer(1, null);
				this.setLabel(this.labelAITwo, 1);
			}
		} else if (e.getSource().equals(this.unloadAITwo)) {
			if (!this.bStart) {
				this.game.getLevel().unloadPlayer(ApoMarioConstants.PLAYER_TWO);
				this.setLabel(this.labelAITwo, 1);
				this.repaint();
			}
		}
	}
	
	private void setLabel(JLabel label, int player) {
		String s = "no AI";
		if (this.game.getLevel().getPlayers().get(player).getAi() != null) {
			s = this.game.getLevel().getPlayers().get(player).getAi().getTeamName();
		}
		label.setText(s);
	}
	
	private void startAnalysis(int value, int width, int difficulty) {
		this.value = value;
		this.width = width;
		this.difficulty = difficulty;
		this.bStart = true;
		this.area.setText("");
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		int start = 0;
		int finishCount = 0;
		int finishCountTwo = 0;
		ApoMarioSimulate simulate = new ApoMarioSimulate(this.game, this.width, this.difficulty);
		int nanoTime = (int)System.nanoTime();
		try{
			nanoTime = (int)((long)Long.valueOf(this.fieldRandom.getText()));
		} catch (NumberFormatException ex) {
			nanoTime = (int)System.nanoTime();
		}
		ApoMarioLevel level = this.game.getLevel();
		this.game.getLevel().makeLevel(nanoTime, false, true, this.width, this.difficulty);
		while ((this.bStart) && (start < this.value)) {
			start += 1;
			if (this.game != null) {
				simulate.startSimulate(!this.sameLevel.isSelected());
			} else {
				break;
			}
			if (this.game.getLevel().getPlayers().get(1).getAi() == null) {
				float percent = (float)(finishCount)/(float)(start)*100;
				String s = start+": DIE! (derzeit zu "+percent+" % im Ziel)! Punkte: "+level.getPlayers().get(0).getPoints()+", random: "+level.getLevelInt();
				if ((!this.game.getLevel().getPlayers().get(0).isBDie()) && (this.game.getLevel().getTime() > 0)) {
					finishCount += 1;
					percent = (float)(finishCount)/(float)(start)*100;
					s = start+": FINISH! (derzeit zu "+percent+" % in Ziel)! Punkte: "+level.getPlayers().get(0).getPoints()+", random: "+level.getLevelInt();
				}
				this.appendText(String.valueOf(s)+"\n");
			} else {
				int pointsOne = level.getPlayers().get(0).getPoints();
				int pointsTwo = level.getPlayers().get(1).getPoints();
				if (pointsOne > pointsTwo) {
					finishCount += 1;
					String s = "Die "+level.getPlayers().get(0).getAi().getTeamName()+"(1)-KI hat die Runde "+start+" gewonnen (Points: "+pointsOne+":"+pointsTwo+" und Siegverhältnis: "+finishCount+":"+finishCountTwo+")! random: "+level.getLevelInt();
					this.appendText(String.valueOf(s)+"\n");
				} else {
					finishCountTwo += 1;
					String s = "Die "+level.getPlayers().get(1).getAi().getTeamName()+"(2)-KI hat die Runde "+start+" gewonnen (Points: "+pointsOne+":"+pointsTwo+" und Siegverhältnis: "+finishCount+":"+finishCountTwo+")! random: "+level.getLevelInt();
					this.appendText(String.valueOf(s)+"\n");
				}
			}
		}
		
		if (this.game.getLevel().getPlayers().get(1).getAi() == null) {
			float percent = (float)(finishCount)/(float)(start)*100;
			String s = "Die "+level.getPlayers().get(0).getAi().getTeamName()+"-KI ist bei "+start+" Runden "+finishCount+" ins Ziel gekommen (ergibt "+Math.round(percent)+" %), (Breite :"+level.getWidth()+"; Höhe: "+level.getDifficulty()+")";
			this.appendText("\n"+String.valueOf(s)+"\n");
		} else {
			String s = "";
			if (finishCount > finishCountTwo) {
				s = "Die "+level.getPlayers().get(0).getAi().getTeamName()+"(1)-KI hat insgesamt "+finishCount+" von "+start+" Runden gewonnen (Siegverhältnis: "+finishCount+":"+finishCountTwo+")! width: "+level.getWidth()+", difficulty: "+level.getDifficulty();
			} else if (finishCount < finishCountTwo) {
				s = "Die "+level.getPlayers().get(1).getAi().getTeamName()+"(2)-KI hat insgesamt "+finishCount+" von "+start+" Runden gewonnen (Siegverhältnis: "+finishCount+":"+finishCountTwo+")! width: "+level.getWidth()+", difficulty: "+level.getDifficulty();				
			} else {
				s = "Nach "+start+" Runden gab es einen Gleichstand bei Siegen (Siegverhältnis: "+finishCount+":"+finishCountTwo+")! width: "+level.getWidth()+", difficulty: "+level.getDifficulty();
			}
			this.appendText("\n"+String.valueOf(s)+"\n");
		}
		this.appendText("");
		this.bStart = false;
		this.buttonStart.setVisible(true);
		this.buttonStop.setVisible(false);
	}
	
	private void appendText(String s) {
		this.area.append(s);
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
	}
}
