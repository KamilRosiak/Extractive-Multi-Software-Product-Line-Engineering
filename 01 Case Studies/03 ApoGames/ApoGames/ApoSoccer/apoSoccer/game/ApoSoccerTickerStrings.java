package apoSoccer.game;

import org.apogames.help.ApoHelp;

/**
 * Klasse für den Ticker, der alle Strings und Phrasen für den Live-Ticker enthält und einen zufällig auswählt
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTickerStrings {

	public static final int HALF_TIME = 0;
	public static final int END_TIME = 1;
	public static final int GOAL_TIME = 2;
	
	private final String time = "!time";
	private final String goal = "!goal";
	private final String lastContact = "!lastContact";
	private final String goalTeam = "!teamGoal";
	private final String enemyTeam = "!enemyTeam";
	private final String scoreGoal = "!scoreGoal";
	private final String scoreEnemy = "!scoreEnemy";
	private final String difference = "!difference";
	
	private final String[] halftime = new String[] {
			"Zur Halbzeit steht es zwischen "+this.goalTeam+" und "+this.enemyTeam+" "+this.scoreGoal+":"+this.scoreEnemy+".",
			"Die Halbzeit wird präsentiert von Apo-Games. Mehr als nur Spiele.",
			"Die Halbzeit wird präsentiert von Apo-Games. Es steht "+this.scoreGoal+":"+this.scoreEnemy+" zwischen "+this.goalTeam+" und "+this.enemyTeam+".",
			"Das Spiel wird präsentiert von Apo-Games. Zur Halbzeit steht es "+this.scoreGoal+":"+this.scoreEnemy+" zwischen "+this.goalTeam+" und "+this.enemyTeam+".",
			"Nach 45 Minuten steht es zwischen "+this.goalTeam+" und "+this.enemyTeam+" "+this.scoreGoal+":"+this.scoreEnemy+".",
			"Der Spielstand nach 45 Minuten lautet "+this.scoreGoal+":"+this.scoreEnemy+".",
	};
	
	private final String[] endtime = new String[] {
			"Das Spiel zwischen "+this.goalTeam+" und "+this.enemyTeam+" endet "+this.scoreGoal+":"+this.scoreEnemy+".",
			"Ein schönes Spiel zwischen "+this.goalTeam+" und "+this.enemyTeam+" endet "+this.scoreGoal+":"+this.scoreEnemy+".",
			"Der Endstand lautet "+this.scoreGoal+":"+this.scoreEnemy+" zwischen "+this.goalTeam+" und "+this.enemyTeam+".",
			"Das Spiel ist vorbei. Der Endstand zwischen "+this.goalTeam+" und "+this.enemyTeam+" lautet "+this.scoreGoal+":"+this.scoreEnemy+".",
			"Aus und vorbei. Die Partie zwischen "+this.goalTeam+" und "+this.enemyTeam+" endet "+this.scoreGoal+":"+this.scoreEnemy+".",
	};
	
	private final String[] equals = new String[] {
		"Das Unendschieden in der "+this.time+" Minute durch "+this.goal+" für '"+this.goalTeam+"'.",
		"In der "+this.time+" Minute schiesst "+this.goal+" für '"+this.goalTeam+"' das Unendschieden zum "+this.scoreGoal+":"+this.scoreEnemy+".",
		""+this.goal+" schiesst in der "+this.time+" Minute das Unentschieden zum "+this.scoreGoal+":"+this.scoreEnemy+".",
		"TOOOOOOR für die "+this.goalTeam+" in der "+this.time+" Minute zum Unentschieden gegen die "+this.enemyTeam+".",
		""+this.time+" Minute und wir haben einen neuen Spielstand. "+this.goalTeam+" schafft durch "+this.goal+" das Unentschieden gegen "+this.enemyTeam+".",
	};
	
	private final String[] rueckstand = new String[] {
		"Tor für die "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+" gegen die "+this.enemyTeam+" in der "+this.time+" Minute. Beginnt nun die Aufholjagd?",
		this.goal+" erzielt für die "+this.goalTeam+" ein Tor zum "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute. Aber reicht es noch zum Aufholen?",
		this.goalTeam+" können durch "+this.goal+" den Rückstand verkürzen. Der neue Spielstand in der "+this.time+" Minute lautet nun "+this.scoreGoal+":"+this.scoreEnemy+".",
		this.goal+" verkürzt für die "+this.goalTeam+" zum "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute.",
	};
	
	private final String[] highRueckstand = new String[] {
		"Tor für die "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+" gegen die "+this.enemyTeam+" in der "+this.time+" Minute. Aber da ist auch nichts mehr zu machen ... oder?",
		this.goal+" erzielt für die "+this.goalTeam+" ein Tor zum "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute. Das kommt aber zu spät!",
		"Trotz Tor für "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute ist das Spiel doch schon gelaufen.",
		"Tor für "+this.goalTeam+" durch "+this.goal+". Doch der Spielstand lautet "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute. Nur ein Tropfen auf dem heissen Stein?",
		"In der "+this.time+" Minute gelingt "+this.goalTeam+" durch "+this.goal+" ein Tor zum "+this.scoreGoal+":"+this.scoreEnemy+". Aber das Spiel scheint dennoch entschieden zu sein.",
	};
	
	private final String[] moreLead = new String[] {
		"Die Führung wird durch "+this.goal+" für die "+this.goalTeam+" zum "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute ausgebaut.",
		this.goal+" erhöht die Führung auf "+this.scoreGoal+":"+this.scoreEnemy+" in der "+this.time+" Minute. Ob "+this.enemyTeam+" nocheinmal zurückkommen können?",
		"Ist das Spiel gelaufen? Nun sind es schon "+this.difference+" Tore Unterschied. Torschütze zum "+this.scoreGoal+":"+this.scoreEnemy+" war "+this.goal+" in der "+this.time+" Minute.",
		"War es das? "+this.difference+" Tore Unterschied sprechen eine deutliche Sprache. Torschütze zum "+this.scoreGoal+":"+this.scoreEnemy+" war "+this.goal+" für die "+this.goalTeam+" in der "+this.time+" Minute.",
		"Wieder ein Tor für "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+". Das Spiel scheint in der "+this.time+" Minute entschieden zu sein.",
	};
	
	private final String[] lead = new String[] {
		"Die Führung für die "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+" gegen die "+this.enemyTeam+"  in der "+this.time+" Minute.",
		this.goal+" erzielt für die "+this.goalTeam+" die Führung in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+". Zuletzt am Ball war "+this.lastContact+"!",
		"Der neue Führende in der Partie lautet "+this.goalTeam+" durch "+this.goal+" in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+"!",
		"Nun müssen sich "+this.enemyTeam+" richtig anstrengen, denn "+this.goalTeam+" sind durch "+this.goal+" in der "+this.time+" Minute in Führung gegangen!",
		this.goalTeam+" geht durch "+this.goal+" in Führung. Der neue Spielstand in der "+this.time+" Minute lautet "+this.scoreGoal+":"+this.scoreEnemy+".",
		"In der "+this.time+" Minute gehen "+this.goalTeam+" in Führung. Den Treffer zum "+this.scoreGoal+":"+this.scoreEnemy+" erzielte "+this.goal+".",
		"Super Tor und verdiente Führung für "+this.goalTeam+" durch "+this.goal+" in der "+this.time+" Minute. Der neue Spielstand lautet "+this.scoreGoal+":"+this.scoreEnemy+".",
	};
	
	private final String[] highLead = new String[] {
		"Langsam wird es langweilig. Die "+this.goalTeam+" bauen ihre Führung durch "+this.goal+" auf "+this.scoreGoal+":"+this.scoreEnemy+" gegen die "+this.enemyTeam+" in der "+this.time+" Minute aus.",
		"Jetzt sind es schon "+this.difference+" Tore Führung für die "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+". Zuletzt am Ball in der "+this.time+" Minute war "+this.lastContact+".",
		"Die "+this.enemyTeam+" blamieren sich fast wie Halle in Magdeburg. Nun sind es "+this.difference+" Tore Führung für die "+this.goalTeam+" durch "+this.goal+". Das Tor zum "+this.scoreGoal+":"+this.scoreEnemy+" fiel in der "+this.time+" Minute.",
		"Der neue Spielstand lautet "+this.scoreGoal+":"+this.scoreEnemy+". Der Torschütze in der "+this.time+" Minute für "+this.goalTeam+" war "+this.goal+".",
		"Und wieder ein Tor für "+this.goalTeam+" durch "+this.goal+". Der neue Spielstand in der "+this.time+" Minute lautet "+this.scoreGoal+":"+this.scoreEnemy+". Mal schauen wieviele Tore noch folgen.",
		this.difference+" Tore Führung für "+this.goalTeam+" zeigen wie eindeutig das Spiel ist. In der "+this.time+" Minute fiel das Tor von "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+".",
	};
	
	private final String[] normal = new String[] {
		"In der "+this.time+" Minute fällt das "+this.scoreGoal+":"+this.scoreEnemy+" für die "+this.goalTeam+" durch "+this.goal+"!",
		"TOOOOOOR in der "+this.time+" Minute für die "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+" gegen die "+this.enemyTeam+"!",
		"In der "+this.time+" Minute haben wir einen neuen Spielstand: "+this.goalTeam+" "+this.scoreGoal+" : "+this.enemyTeam+" "+this.scoreEnemy+". Torschütze war "+this.goal+"!",
		this.time+" Minute: Tor für "+this.goalTeam+" durch "+this.goal+" zum "+this.scoreGoal+":"+this.scoreEnemy+". Torschütze war "+this.goal+"!",
		"Traumtor durch "+this.goal+" in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+". Davon wollen wir mehr sehen!",
		this.goal+" stolpert den Ball in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+" ins Tor.",
		this.goal+" schiesst in der "+this.time+" Minute ein Tor zum "+this.scoreGoal+":"+this.scoreEnemy+" aus Sicht der "+this.goalTeam+"!",
		"Wunderbares Spiel der "+this.goalTeam+" mit perfekten Abschluss von "+this.goal+" in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+"!",
		"Was für ein Spieler! "+this.goal+" erzielt das Tor für die "+this.goalTeam+" in der "+this.time+" Minute zum "+this.scoreGoal+":"+this.scoreEnemy+".",
		"So einfach und schön kann Fußball sein. In der "+this.time+" Minute trifft "+this.goal+" für "+this.goalTeam+" zum "+this.scoreGoal+":"+this.scoreEnemy+".",
		"Schuss und Tor für "+this.goalTeam+" durch "+this.goal+" in der "+this.time+" Minute. Der neue Spielstand lautet "+this.scoreGoal+":"+this.scoreEnemy+".",
		this.time+" Minute und wir haben einen neuen Spielstand. "+this.goal+" schiesst ein Tor für "+this.goalTeam+" zum "+this.scoreGoal+":"+this.scoreEnemy+".",
	};
	
	public String getTickerMessage(int time, String goal, String lastContact, String goalTeam, String enemyTeam, int scoreGoal, int scoreEnemy, int what) {
		int difference = scoreGoal - scoreEnemy;
		String returnMessage = "";
	
		if (what == ApoSoccerTickerStrings.GOAL_TIME) {
			if (difference >= 5) {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 25) {
					int rand = ApoHelp.getRandomValue(this.highLead.length, 0);
					returnMessage = this.highLead[rand];
				} else if (random > 15){
					int rand = ApoHelp.getRandomValue(this.moreLead.length, 0);
					returnMessage = this.moreLead[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			} else if (difference >= 2) {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 20){
					int rand = ApoHelp.getRandomValue(this.moreLead.length, 0);
					returnMessage = this.moreLead[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			} else if (difference >= 1) {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 20){
					int rand = ApoHelp.getRandomValue(this.lead.length, 0);
					returnMessage = this.lead[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			} else if (difference == 0) {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 25){
					int rand = ApoHelp.getRandomValue(this.equals.length, 0);
					returnMessage = this.equals[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			} else if (difference >= -4) {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 25){
					int rand = ApoHelp.getRandomValue(this.rueckstand.length, 0);
					returnMessage = this.rueckstand[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			} else {
				int random = ApoHelp.getRandomValue(100, 1);
				if (random > 20) {
					int rand = ApoHelp.getRandomValue(this.highRueckstand.length, 0);
					returnMessage = this.highRueckstand[rand];
				} else if (random > 10){
					int rand = ApoHelp.getRandomValue(this.rueckstand.length, 0);
					returnMessage = this.rueckstand[rand];
				} else {
					int rand = ApoHelp.getRandomValue(this.normal.length, 0);
					returnMessage = this.normal[rand];
				}
			}
		} else if (what == ApoSoccerTickerStrings.HALF_TIME) {
			int rand = ApoHelp.getRandomValue(this.halftime.length, 0);
			returnMessage = this.halftime[rand];
		} else {
			int rand = ApoHelp.getRandomValue(this.endtime.length, 0);
			returnMessage = this.endtime[rand];
		}
		
		returnMessage = returnMessage.replaceAll(this.difference, ""+difference);
		returnMessage = returnMessage.replaceAll(this.time, ""+time);
		returnMessage = returnMessage.replaceAll(this.goal, ""+goal);
		returnMessage = returnMessage.replaceAll(this.lastContact, ""+lastContact);
		returnMessage = returnMessage.replaceAll(this.goalTeam, ""+goalTeam);
		returnMessage = returnMessage.replaceAll(this.enemyTeam, ""+enemyTeam);
		returnMessage = returnMessage.replaceAll(this.scoreGoal, ""+scoreGoal);
		returnMessage = returnMessage.replaceAll(this.scoreEnemy, ""+scoreEnemy);
		
		//System.out.println(returnMessage);
		
		return returnMessage;
	}
}
