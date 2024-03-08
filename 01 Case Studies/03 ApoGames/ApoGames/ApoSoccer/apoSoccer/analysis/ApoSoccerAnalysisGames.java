package apoSoccer.analysis;

import java.util.ArrayList;

public class ApoSoccerAnalysisGames {

	public ApoSoccerAnalysisGames() {
	}
	
	public ArrayList<ApoSoccerAnalysisHelp> getCompleteList(ArrayList<ApoSoccerAnalysisGamesHelp> help) {
		ArrayList<ApoSoccerAnalysisHelp> analysis = new ArrayList<ApoSoccerAnalysisHelp>();
		
		for (int i = 0; i < help.size(); i++) {
			for (int j = i+1; j < help.size(); j++) {
				analysis.add(new ApoSoccerAnalysisHelp(help.get(i).getPath(), help.get(i).getTeam(), help.get(i).getClassName(), help.get(j).getPath(), help.get(j).getTeam(), help.get(j).getClassName()));
				analysis.add(new ApoSoccerAnalysisHelp(help.get(j).getPath(), help.get(j).getTeam(), help.get(j).getClassName(), help.get(i).getPath(), help.get(i).getTeam(), help.get(i).getClassName()));
			}
		}
		
		return analysis;
	}
}
