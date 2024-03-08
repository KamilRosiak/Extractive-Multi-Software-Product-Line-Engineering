package apoSoccer.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.game.ApoSoccerGame;

/**
 * Klasse um eine txt-Datei einzulesen und das Spiel ohne Frame zu starten und das replay,
 * wenn gewünscht zu speichern
 * @author Dirk Aporius
 *
 */
public class ApoSoccerAnalysis {

	private boolean B_WITH_REPLAY = false;
	
	private int GAMES = 1;
	
	private ApoSoccerGame game;
	
	private ArrayList<ApoSoccerAnalysisHelp> analysis;
	
	private ArrayList<ApoSoccerAnalysisTable> table;
	
	private String fileString;
	
	private ApoSoccerAnalysisFrame frame;
	
	private boolean bBreak;
	
	public ApoSoccerAnalysis(ApoSoccerAnalysisFrame frame, ApoSoccerGame game, String fileString) {
		this.fileString = fileString;
		this.frame = frame;
		
		this.game = game;
		if (this.game == null) {
			this.game = new ApoSoccerGame(true, true, ApoSoccerConstants.WAIT_TIME_THINK, ApoSoccerConstants.WAIT_TIME_RENDER);
			this.game.setSize( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT );
			this.game.init();
			this.game.debugChange();
		}
	}
	
	public void setFileString(String fileString) {
		this.fileString = fileString;
	}
	
	public boolean isBBreak() {
		return this.bBreak;
	}

	public void setBBreak(boolean bBreak) {
		this.bBreak = bBreak;
	}

	public boolean readFileAndMakeAnalysis() {
		this.bBreak = false;
		this.analysis = new ArrayList<ApoSoccerAnalysisHelp>();
		Scanner scan = null;
		try {
			scan = new Scanner(new File(this.fileString));
			ArrayList<ApoSoccerAnalysisGamesHelp> help = new ArrayList<ApoSoccerAnalysisGamesHelp>();
			try {
				this.GAMES = Integer.valueOf(this.frame.getSaisonsField().getText());
			} catch (NumberFormatException ex) {
				this.GAMES = 1;
			}
			if (this.frame.getReplaySave().isSelected()) {
				this.B_WITH_REPLAY = true;
			} else {
				this.B_WITH_REPLAY = false;
			}
			while (scan.hasNext()) {
				String line = scan.nextLine();
				System.out.println(line);
				String[] seperator = line.split(";");
				if ((seperator != null) && (seperator.length >= 3)) {
						help.add(new ApoSoccerAnalysisGamesHelp(seperator[0], seperator[1], seperator[2]));
						//this.analysis.add(new ApoSoccerAnalysisHelp(seperator[0], seperator[1], seperator[2], seperator[3]));
				}
			}
			
			this.analysis = new ApoSoccerAnalysisGames().getCompleteList(help);
			int size = this.analysis.size();
			for (int i = 1; i < this.GAMES; i++) {
				for (int j = 0; j < size; j++) {
					this.analysis.add(new ApoSoccerAnalysisHelp(this.analysis.get(j).getHomeAIPath(), this.analysis.get(j).getHomeAIClass(), this.analysis.get(j).getHomeAIClassClass(), this.analysis.get(j).getVisitorAIPath(), this.analysis.get(j).getVisitorAIClass(), this.analysis.get(j).getVisitorAIClassClass()));
				}
			}
			this.frame.getTextAreaGames().append(help.size()+" KI's kämpfen in "+this.GAMES+" Saisons("+this.analysis.size()+" Partien insgesamt). Replayspeicherung ist "+this.B_WITH_REPLAY+"\n");
			
			for (int i = 0; i < this.analysis.size(); i++) {
				if (this.bBreak) {
					break;
				}
				this.game.loadPlayer(true, this.analysis.get(i).getHomeAIPath(), this.analysis.get(i).getHomeAIClass(), "." + this.analysis.get(i).getHomeAIClassClass());
				this.game.loadPlayer(false, this.analysis.get(i).getVisitorAIPath(), this.analysis.get(i).getVisitorAIClass(), "." + this.analysis.get(i).getVisitorAIClassClass());
				this.game.playGameWithoutRepaint();
				this.analysis.get(i).setScoreHome(this.game.getHomeTeam().getGoals());
				this.analysis.get(i).setScoreVisitor(this.game.getVisitorTeam().getGoals());
				this.frame.getTextAreaGames().append(this.analysis.get(i).toString()+"\n");
				if (this.B_WITH_REPLAY) {
					Calendar calendar = Calendar.getInstance();
					String file = ""+calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH)+""+calendar.get(Calendar.DAY_OF_MONTH)+""+calendar.get(Calendar.HOUR_OF_DAY)+""+calendar.get(Calendar.MINUTE)+""+calendar.get(Calendar.SECOND);
					this.game.getReplay().saveReplay(System.getProperty("user.dir") + File.separator +"replays"+File.separator+file+"_"+this.analysis.get(i).getHomeAIClass()+"-"+this.analysis.get(i).getVisitorAIClass()+"="+this.analysis.get(i).getScoreHome()+"_"+this.analysis.get(i).getScoreVisitor());
				}
				this.setTable(i+1);
			}
			if (!this.bBreak) {
				this.frame.getTextAreaGames().append("Fertig");
				this.setTable(this.analysis.size());
			} else {
				this.frame.getTextAreaGames().append("Abgebrochen");
				this.bBreak = false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			return false;
		}		
		return true;
	}
	
	private boolean setTable(int end) {
		ArrayList<ApoSoccerAnalysisTable> table = new ArrayList<ApoSoccerAnalysisTable>();
		for (int i = 0; i < end; i++) {
			String homeTeam = this.analysis.get(i).getHomeAIClass();
			if (table.size() <= 0) {
				table.add(new ApoSoccerAnalysisTable(homeTeam));
				table.get(table.size() - 1).addScore(this.analysis.get(i).getScoreHome(), this.analysis.get(i).getScoreVisitor());
			} else {
				for (int tab = 0; tab < table.size(); tab++) {
					if (homeTeam.equals(table.get(tab).getName())) {
						table.get(tab).addScore(this.analysis.get(i).getScoreHome(), this.analysis.get(i).getScoreVisitor());
						break;
					} else if (tab == table.size() - 1) {
						table.add(new ApoSoccerAnalysisTable(homeTeam));
						table.get(table.size() - 1).addScore(this.analysis.get(i).getScoreHome(), this.analysis.get(i).getScoreVisitor());						
						break;
					}
				}
			}
			
			String visitorTeam = this.analysis.get(i).getVisitorAIClass();
			if (table.size() <= 0) {
				table.add(new ApoSoccerAnalysisTable(visitorTeam));
				table.get(table.size() - 1).addScore(this.analysis.get(i).getScoreVisitor(), this.analysis.get(i).getScoreHome());
			} else {
				for (int tab = 0; tab < table.size(); tab++) {
					if (visitorTeam.equals(table.get(tab).getName())) {
						table.get(tab).addScore(this.analysis.get(i).getScoreVisitor(), this.analysis.get(i).getScoreHome());
						break;
					} else if (tab == table.size() - 1) {
						table.add(new ApoSoccerAnalysisTable(visitorTeam));
						table.get(table.size() - 1).addScore(this.analysis.get(i).getScoreVisitor(), this.analysis.get(i).getScoreHome());						
						break;
					}
				}
			}
		}
		
		this.table = new ArrayList<ApoSoccerAnalysisTable>();
		while (table.size() > 0) {
			int maxI = 0;
			for (int i = 1; i < table.size(); i++) {
				if (table.get(i).getPoints() > table.get(maxI).getPoints()) {
					maxI = i;
				} else if (table.get(i).getPoints() == table.get(maxI).getPoints()) {
					int difI = table.get(i).getOwnGoal() - table.get(i).getEnemyGoal();
					int difMaxI = table.get(maxI).getOwnGoal() - table.get(maxI).getEnemyGoal();
					if (difI > difMaxI) {
						maxI = i;
					} else if (difI == difMaxI) {
						if (table.get(i).getOwnGoal() > table.get(maxI).getOwnGoal()) {
							maxI = i;
						}
					}
				}
			}
			this.table.add(table.get(maxI).getCopy());
			table.remove(maxI);
		}
		this.frame.makeTable();
		
		return true;
	}

	public ArrayList<ApoSoccerAnalysisHelp> getAnalysis() {
		return this.analysis;
	}

	public ArrayList<ApoSoccerAnalysisTable> getTable() {
		return this.table;
	}

}
