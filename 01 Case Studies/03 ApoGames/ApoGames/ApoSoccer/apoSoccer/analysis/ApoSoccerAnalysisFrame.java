package apoSoccer.analysis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import org.apogames.ApoConstants;
import org.apogames.help.ApoFileFilter;

import apoSoccer.ApoSoccerImages;
import apoSoccer.game.ApoSoccerGame;

public class ApoSoccerAnalysisFrame extends JFrame implements ActionListener, ItemListener, Runnable {

	private static final long serialVersionUID = 1L;

	private final String[] COLUMN_NAMES = new String[] {
			"Place", "Name", "Games", "Points", "Dif", "Goals", "s", "u", "n"	
	};
	
	private final int FRAME_WIDTH = 640;
	private final int FRAME_HEIGHT = 480;
	
	private final int FRAME_HEIGHT_GAMES = 220;
	
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private ApoFileFilter fileFilter;
	
	private JTable table;
	
	private JButton buttonLoad, buttonStart, buttonStop;
	
	private JTextArea textAreaGames;
	
	private JTextField saisonsField;
	
	private JLabel saisonsLabel;
	
	private JCheckBox replaySave;
	
	private JScrollPane scrollPaneGames, scrollPaneTable;
	
	private ApoSoccerAnalysis analysis;
	
	private boolean bAnalysisTable;
	
	public static void main(String[] args) {
		ApoSoccerAnalysisFrame frame;
		if ((args == null) || (args.length <= 0)) {
			frame = new ApoSoccerAnalysisFrame(null, "auswertung.txt");
		} else {
			frame = new ApoSoccerAnalysisFrame(null, args[0]);
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public ApoSoccerAnalysisFrame(ApoSoccerGame game, String file) {
		this.setTitle("=== ApoSoccer Analysis ===");
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (bAnalysisTable) {
					stopEvent();
				}
				setVisible(false);
			}
		});
		
		super.setIconImage( new ApoSoccerImages().getLogo( 16, 16) );
		
		this.setResizable(false);
		this.setLayout(null);
		JComponent currentComponent	= (JComponent)this.getContentPane();
		currentComponent.setPreferredSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
		currentComponent.setLayout(null);
		this.init();
		this.analysis = new ApoSoccerAnalysis(this, game, file);
		this.pack();
		this.setVisible(true);
	}
	
	private void init() {
		if ( !ApoConstants.B_APPLET ) {
			this.fileChooser = new JFileChooser();
			this.fileFilter = new ApoFileFilter( "txt" );
			this.fileChooser.setCurrentDirectory( new File( System.getProperty("user.dir") + File.separator ) );
			this.fileChooser.setFileFilter( this.fileFilter );
		}
		
		this.buttonLoad = new JButton("load");
		this.buttonLoad.setSize(100, 20);
		this.buttonLoad.setLocation(5, 5);
		this.buttonLoad.addActionListener(this);
		this.add(this.buttonLoad);
		
		this.buttonStart = new JButton("start");
		this.buttonStart.setSize(100, 20);
		this.buttonStart.setLocation(110, 5);
		this.buttonStart.addActionListener(this);
		this.add(this.buttonStart);

		this.buttonStop = new JButton("stop");
		this.buttonStop.setSize(100, 20);
		this.buttonStop.setLocation(110, 5);
		this.buttonStop.addActionListener(this);
		this.buttonStop.setVisible(false);
		this.add(this.buttonStop);

		this.saisonsLabel = new JLabel("Saisons");
		this.saisonsLabel.setSize(60, 20);
		this.saisonsLabel.setLocation(320, 5);
		this.add(this.saisonsLabel);
		
		this.saisonsField = new JTextField("1");
		this.saisonsField.setSize(60, 20);
		this.saisonsField.setLocation(380, 5);
		this.add(this.saisonsField);
		
		this.replaySave = new JCheckBox("Replaysave");
		this.replaySave.setSize(100, 20);
		this.replaySave.setLocation(450, 5);
		this.replaySave.addItemListener(this);
		this.add(this.replaySave);
		
		this.setTable(new Object[1][this.COLUMN_NAMES.length]);
		//this.setTable(new Object[][] {{"1", "2", "3", "4", "5", "6", "7", "8", "9"}});
		
		this.textAreaGames = new JTextArea();
		this.textAreaGames.setLineWrap(true);
		
		this.scrollPaneGames = new JScrollPane(this.textAreaGames);
		this.scrollPaneGames.setSize(this.FRAME_WIDTH - 10, this.FRAME_HEIGHT_GAMES);
		this.scrollPaneGames.setLocation(5, 30);
		this.add(this.scrollPaneGames);
	}
	
	public JCheckBox getReplaySave() {
		return this.replaySave;
	}

	public JTextField getSaisonsField() {
		return saisonsField;
	}

	public JTextArea getTextAreaGames() {
		return this.textAreaGames;
	}
	
	private void setTable(Object[][] object) {
		if (this.table != null) {
			this.remove(this.scrollPaneTable);
		}
		
		this.table = new JTable(object, this.COLUMN_NAMES);
		this.table.setBorder(BorderFactory.createLineBorder(Color.black));
		
		TableColumn column = null;
		for (int i = 0; i < this.COLUMN_NAMES.length; i++) {
		    column = this.table.getColumnModel().getColumn(i);
		    if ((i == 0) || (i == 2) || (i == 3)){
		        column.setPreferredWidth(35);
		    } else if (i == 1) {
		        column.setPreferredWidth(210);
		    } else if (i >= 4) {
		        column.setPreferredWidth(35);
		    }
		}
		
		this.scrollPaneTable = new JScrollPane(this.table);
		this.scrollPaneTable.setSize(this.FRAME_WIDTH - 10, this.FRAME_HEIGHT_GAMES);
		this.scrollPaneTable.setLocation(5, 35 + this.FRAME_HEIGHT_GAMES);
		this.add(this.scrollPaneTable);		
	}
	
	private void stopEvent() {
		this.analysis.setBBreak(true);
		while (this.analysis.isBBreak()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		this.buttonStart.setVisible(true);
		this.buttonStop.setVisible(false);
		this.buttonLoad.setEnabled(true);
		this.replaySave.setEnabled(true);
		this.saisonsField.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (this.bAnalysisTable) {
			if (event.getSource().equals(this.buttonStop)) {
				this.stopEvent();
			}
		} else {
			if (event.getSource().equals(this.buttonLoad)) {
				String s = this.loadFile();
				this.analysis.setFileString(s);
			} else if (event.getSource().equals(this.buttonStart)) {
				this.bAnalysisTable = true;
				this.textAreaGames.setText("");
				Thread t = new Thread(this);
				t.start();
				this.buttonStart.setVisible(false);
				this.buttonStop.setVisible(true);
				this.buttonLoad.setEnabled(false);
				this.replaySave.setEnabled(false);
				this.saisonsField.setEnabled(false);
			}
		}
	}
	
	private String loadFile() {
		if ( this.fileChooser.showOpenDialog(this) == 0 ) {
			return this.fileChooser.getSelectedFile().getPath();
		} else {
			return null;
		}
	}
	
	private void makeAnalysisAndTable() {
		this.analysis.readFileAndMakeAnalysis();
		
		this.makeTable();

		this.bAnalysisTable = false;
		
		this.buttonStart.setVisible(true);
		this.buttonStop.setVisible(false);
		this.buttonLoad.setEnabled(true);
		this.replaySave.setEnabled(true);
		this.saisonsField.setEnabled(true);
	}

	public void makeTable() {
		ArrayList<ApoSoccerAnalysisTable> table = this.analysis.getTable();
		this.setTable(new Object[table.size()][this.COLUMN_NAMES.length]);
		for (int i = 0; i < table.size(); i++) {
			this.table.setValueAt((i+1), i, 0);
			this.table.setValueAt(table.get(i).getName(), i, 1);
			this.table.setValueAt(table.get(i).getGames(), i, 2);
			this.table.setValueAt(table.get(i).getPoints(), i, 3);
			this.table.setValueAt((table.get(i).getOwnGoal()-table.get(i).getEnemyGoal()), i, 4);
			this.table.setValueAt(table.get(i).getOwnGoal()+":"+table.get(i).getEnemyGoal(), i, 5);
			this.table.setValueAt(table.get(i).getWins(), i, 6);
			this.table.setValueAt(table.get(i).getTie(), i, 7);
			this.table.setValueAt(table.get(i).getLost(), i, 8);
		}
	}
	
	public void run() {
		this.makeAnalysisAndTable();
	}

	public boolean isBAnalysisTable() {
		return this.bAnalysisTable;
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(this.replaySave)) {
			
		}
	}
	
}
