package apoSoccer.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse, die den Live-Ticker repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTicker {
	
	/** eine ArrayList mit Strings für die Heimmannschaft */
	private ArrayList<String> homeTeam;
	/** eine ArrayList mit Strings für die Visitormannschaft */
	private ArrayList<String> visitorTeam;
	
	/** Eine ArrayList mit String des Live-Tickers */
	private ArrayList<String> ticker;
	/** Ein Bild, was die Nachrichten enthält */
	private BufferedImage iTicker;
	
	/** Konstruktor */
	public ApoSoccerTicker() {
		this.init();
	}
	
	/**
	 * wird beim Spielstart aufgerufen und leert den Ticker
	 */
	public void init() {
		this.ticker = new ArrayList<String>();
		this.homeTeam = new ArrayList<String>();
		this.visitorTeam = new ArrayList<String>();
		this.iTicker = null;
	}
	
	public void setMenuText() {
		this.addTicker("Programmierwettbewerb 2008/2009 an der Fakultät für Informatik der Otto-von-Guericke Universität Magdeburg");
		this.addTicker("----------------------------------- ===== ApoSoccer ===== ----------------------------------- = by Dirk 'Apo' Aporius = -----------------------------------");
	}
	
	/**
	 * speichert die Namen der Spieler und den Teamnamen für beide Mannschaften
	 * @param homeTeamName
	 * @param homeNames
	 * @param visitorTeamName
	 * @param visitorNames
	 * @return immer TRUE
	 */
	public boolean setNames(String homeTeamName, String[] homeNames, String visitorTeamName, String[] visitorNames) {
		this.homeTeam = new ArrayList<String>();
		for (int i = 0; i < homeNames.length; i++) {
			this.homeTeam.add(homeNames[i]);
		}
		this.homeTeam.add(homeTeamName);
		
		this.visitorTeam = new ArrayList<String>();
		for (int i = 0; i < visitorNames.length; i++) {
			this.visitorTeam.add(visitorNames[i]);
		}
		this.visitorTeam.add(visitorTeamName);
		
		return true;
	}
	
	/**
	 * fügt eine Nachricht zum Live-Ticker hinzu
	 * @param ticker : neue Live-Ticker Nachricht
	 * @return immer TRUE
	 */
	public boolean addTicker(String ticker) {
		if (this.ticker.size() == 0) {
			this.ticker.add(ticker);
		} else {
			this.ticker.add(0, ticker);
		}
		this.drawTicker();
		return true;
	}
	
	private boolean drawTicker() {
		if (!ApoSoccerSimulate.B_SIMULATE) {
			this.iTicker = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.TICKER_WIDTH, ApoSoccerConstants.TICKER_HEIGHT, Transparency.BITMASK );
			Graphics2D g = (Graphics2D)this.iTicker.getGraphics();
			//this.renderImage(g);
			this.drawSpeech(g, this.ticker);
			g.dispose();
		}
		return true;
	}
	
	/**
	 * malt das Bild des Tickers auf der Untergrund
	 * @param g : Das Grapic2DObject
	 */
	public void render(Graphics2D g) {
		if (this.iTicker != null) {
			g.drawImage(this.iTicker, ApoSoccerConstants.TICKER_X, ApoSoccerConstants.TICKER_Y, null);
		}
	}
	
	/**
	 * sucht in dem line-String nach dem Namen aus der übergebenen ArrayList
	 * Es wird ein Int Array erstellt und zurückgegeben
	 * wenn ein Name in der Line vorkommt, wird der int Wert auf diese Stelle gesetzt
	 * @param names : ArrayList aus Strings mit den gesuchten Namen
	 * @param line : komplette Zeile
	 * @return ein Int Array welches angibt, wo was vorkommt
	 */
	private ArrayList<ApoSoccerTickerName> isNameIn(ArrayList<String> names, String line) {
		ArrayList<ApoSoccerTickerName> searchNames = new ArrayList<ApoSoccerTickerName>();
		for (int i = 0; i < names.size(); i++) {
			int position = 0;
			while (line.indexOf(names.get(i),position) >= 0) {
				searchNames.add(new ApoSoccerTickerName(names.get(i), line.indexOf(names.get(i),position)));
				position = line.indexOf(names.get(i),position) + 1;
			}
		}
		return searchNames;
	}
	
	/**
	 * ordnet die gefundenen Namen nach Positionen
	 * @param home : Int Array mit den Positionen der HeimNamen
	 * @param visitor : Int Array mit den Positionen der VisitorNamen
	 * @return eine ArrayList geordnet
	 */
	private ArrayList<ApoSoccerTickerHelp> getTickerHelp(ArrayList<ApoSoccerTickerName> home, ArrayList<ApoSoccerTickerName> visitor) {
		ArrayList<ApoSoccerTickerHelp> tickerHelp = new ArrayList<ApoSoccerTickerHelp>();
		for (int i = 0; i < home.size(); i++) {
			if (tickerHelp.size() <= 0) {
				tickerHelp.add(new ApoSoccerTickerHelp(home.get(i).getName(), home.get(i).getPosition(), Color.red));
			} else {
				for (int j = 0; j < tickerHelp.size(); j++) {
					if (tickerHelp.get(j).getPosition() == home.get(i).getPosition()) {
						break;
					}
					if (tickerHelp.get(j).getPosition() > home.get(i).getPosition()) {
						tickerHelp.add(j, new ApoSoccerTickerHelp(home.get(i).getName(), home.get(i).getPosition(), Color.red));
						break;
					} else if (j == tickerHelp.size() - 1) {
						tickerHelp.add(new ApoSoccerTickerHelp(home.get(i).getName(), home.get(i).getPosition(), Color.red));
						break;
					}
				}
			}
		}
		for (int i = 0; i < visitor.size(); i++) {
			if (tickerHelp.size() <= 0) {
				tickerHelp.add(new ApoSoccerTickerHelp(visitor.get(i).getName(), visitor.get(i).getPosition(), Color.blue));
			} else {
				for (int j = 0; j < tickerHelp.size(); j++) {
					if (tickerHelp.get(j).getPosition() == visitor.get(i).getPosition()) {
						break;
					}
					if (tickerHelp.get(j).getPosition() > visitor.get(i).getPosition()) {
						tickerHelp.add(j, new ApoSoccerTickerHelp(visitor.get(i).getName(), visitor.get(i).getPosition(), Color.blue));
						break;
					} else if (j == tickerHelp.size() - 1) {
						tickerHelp.add(new ApoSoccerTickerHelp(visitor.get(i).getName(), visitor.get(i).getPosition(), Color.blue));
						break;
					}
				}
			}
		}
		return tickerHelp;
	}
	
	private void drawSpeech(Graphics2D g, ArrayList<String> allStrings) {
		g.setColor(Color.black);
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
		
		int curHeight = 0;
		int[] maxLength = new int[] {ApoSoccerConstants.TICKER_WIDTH - 10};
		int x = 5;
		int y = 35;
		
		for (int stringI = 0; stringI < allStrings.size(); stringI++) {
			ArrayList<String> strings = new ArrayList<String>();
			strings.add(allStrings.get(stringI));
			int cur = 0;
			ArrayList<ApoSoccerTickerName> searchHomeName = this.isNameIn(this.homeTeam, allStrings.get(stringI));
			ArrayList<ApoSoccerTickerName> searchVisitorName = this.isNameIn(this.visitorTeam, allStrings.get(stringI));
			ArrayList<ApoSoccerTickerHelp> tickerHelp = this.getTickerHelp(searchHomeName, searchVisitorName);
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
							curHeight++;
							if (curHeight >= maxLength.length) {
								curHeight--;
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
			/*
			for (int i = 0; i < tickerHelp.size(); i++) {
				System.out.println(tickerHelp.get(i).getName());
			}
			System.out.println();*/
			for ( int i = 0; i < strings.size(); i++ ) {
				//position += this.drawStringOnLine(g, strings.get(i), x, y, position, tickerHelp);
				this.drawSpeechLine(g, allStrings.get(stringI), strings.get(i), x, y, tickerHelp);
				//position += strings.get(i).length() + 1;
				//System.out.println("String = "+strings.get(i)+" complete = "+allStrings.get(stringI));
				if (i < strings.size() - 1) {
					y += 15;
				}
				if (y > ApoSoccerConstants.TICKER_HEIGHT - 5) {
					break;
				}
			}
			y += 20;
			if (y > ApoSoccerConstants.TICKER_HEIGHT - 5) {
				break;
			}
		}
	}
	
	/**
	 * schaut ob ijn dieser Zeile ein Name vorkommt der andersfarbig makiert werden muss
	 * falls ja dann makiert er diesen
	 * @param g : Das Graphics2D Objekt
	 * @param line : Die Zeile
	 * @param x : X-Wert wo es hingemalt werden soll
	 * @param y : Y-Wert, wo es hingemalt werden soll
	 * @param startPosition : Position des Strang in der gesamten Nachricht
	 * @param tickerHelp : ArrayList mit den Objekten, welcher Name makiert werden soll und wo diese stehen
	 */
	private void drawSpeechLine(Graphics2D g, String holeString, String line, int x, int y, ArrayList<ApoSoccerTickerHelp> tickerHelp) {
		//System.out.println("Die Line lautet: "+line);
		int curX = x;
		try {
			curX = x;
			int curPosition = 0;
			int startPosition = holeString.indexOf(line);
			int endPosition = startPosition + line.length();
			for (int i = 0; i < tickerHelp.size(); i++) {
				g.setColor(Color.black);
				if ((tickerHelp.get(i).getPosition() > endPosition) ||
					((i == tickerHelp.size() - 1) && (tickerHelp.get(i).getMaxPosition() <= curPosition))) {
					String curLine = line.substring(curPosition);
					g.setColor(Color.black);
					g.drawString(curLine, curX, y);
					curX += g.getFontMetrics().stringWidth(curLine);
					break;
				}
				if (tickerHelp.get(i).getMaxPosition() < startPosition) {
					continue;
				}
				//System.out.println("Position: "+tickerHelp.get(i).getPosition()+" startPosition: "+startPosition+" maxPos: "+tickerHelp.get(i).getMaxPosition()+" endPosition: "+endPosition);
				if ((tickerHelp.get(i).getPosition() > startPosition + curPosition) && (tickerHelp.get(i).getMaxPosition() <= endPosition)) {
					//System.out.println("Ole bei "+line);
					g.setColor(Color.black);
					int oldCurPosition = curPosition;
					curPosition = tickerHelp.get(i).getPosition() - startPosition;
					String curLine = line.substring(oldCurPosition, curPosition);
					g.drawString(curLine, curX, y);
					curX += g.getFontMetrics().stringWidth(curLine);
				
					oldCurPosition = curPosition;
					curPosition = tickerHelp.get(i).getMaxPosition() - startPosition;
					g.setColor(tickerHelp.get(i).getColor());
					curLine = line.substring(oldCurPosition, curPosition);
					g.drawString(curLine, curX, y);
					curX += g.getFontMetrics().stringWidth(curLine);
			
					//System.out.println("Ole");
					
					if (i + 1 < tickerHelp.size()) {
						g.setColor(Color.black);
						if (tickerHelp.get(i + 1).getPosition() <= endPosition) {
							oldCurPosition = curPosition;
							curPosition = tickerHelp.get(i + 1).getPosition() - startPosition;
							g.setColor(tickerHelp.get(i).getColor());
							curLine = line.substring(oldCurPosition, curPosition);
							g.drawString(curLine, curX, y);
							curX += g.getFontMetrics().stringWidth(curLine);
						} else if (curPosition + 1 < line.length()){
							curLine = line.substring(curPosition);
							g.drawString(curLine, curX, y);
							return;
						} else {
							return;
						}
					} else {
						curLine = line.substring(curPosition);
						g.setColor(Color.black);
						g.drawString(curLine, curX, y);
						return;
					}
				} else if ((tickerHelp.get(i).getPosition() <= startPosition + curPosition) && (tickerHelp.get(i).getMaxPosition() <= endPosition) && (tickerHelp.get(i).getMaxPosition() >= startPosition + curPosition)) {
					//System.out.println("Position: "+tickerHelp.get(i).getPosition()+" startPosition: "+startPosition+" maxPos: "+tickerHelp.get(i).getMaxPosition()+" endPosition: "+endPosition+" line = "+line);
					int oldCurPosition = curPosition;
					curPosition = tickerHelp.get(i).getMaxPosition() - startPosition;
					g.setColor(tickerHelp.get(i).getColor());
					String curLine = line.substring(oldCurPosition, curPosition);
					g.drawString(curLine, curX, y);
					curX += g.getFontMetrics().stringWidth(curLine);
					if (i + 1 < tickerHelp.size()) {
						g.setColor(Color.black);
						if (tickerHelp.get(i + 1).getPosition() < endPosition) {
							oldCurPosition = curPosition;
							curPosition = tickerHelp.get(i + 1).getPosition() - startPosition;
							curLine = line.substring(oldCurPosition, curPosition);
							g.drawString(curLine, curX, y);
							curX += g.getFontMetrics().stringWidth(curLine);
							continue;
						} else {
							if (curPosition < line.length()){
								curLine = line.substring(curPosition);
								g.drawString(curLine, curX, y);
							}
							return;
						}
					} else {
						g.setColor(Color.black);
						if (curPosition <= line.length()){
							curLine = line.substring(curPosition);
							g.drawString(curLine, curX, y);
							return;
						} else {
							return;
						}
					}
				} else if ((tickerHelp.get(i).getPosition() >= startPosition + curPosition) && (tickerHelp.get(i).getMaxPosition() > endPosition) && (tickerHelp.get(i).getPosition() < endPosition)) {
					int oldCurPosition = curPosition;
					curPosition = tickerHelp.get(i).getPosition() - startPosition;
					g.setColor(Color.black);
					String curLine = line.substring(oldCurPosition, curPosition);
					g.drawString(curLine, curX, y);
					curX += g.getFontMetrics().stringWidth(curLine);
				
					oldCurPosition = curPosition;
					curPosition = tickerHelp.get(i).getMaxPosition() - startPosition;
					g.setColor(tickerHelp.get(i).getColor());
					if (curPosition < line.length()) {
						curLine = line.substring(oldCurPosition, curPosition);
						//System.out.println("kleiner line "+curLine);
						g.drawString(curLine, curX, y);
					} else {
						curLine = line.substring(oldCurPosition);
						//System.out.println("größer line "+curLine);
						g.drawString(curLine, curX, y);
						return;
					}
					curX += g.getFontMetrics().stringWidth(curLine);
				}
			}
			if (curPosition > line.length()) {
				return;
			}
			if (curX <= x){
				String curLine = line.substring(curPosition);
				if ((tickerHelp.size() > 0) && (tickerHelp.get(tickerHelp.size() - 1).getName().indexOf(curLine) >= 0)) {
					g.setColor(tickerHelp.get(tickerHelp.size() - 1).getColor());
				} else {
					g.setColor(Color.black);	
				}
				g.drawString(curLine, curX, y);
				curX += g.getFontMetrics().stringWidth(curLine);
			}
		} catch (Exception ex) {
			if (curX > 5) {
				g.drawString("Exception", curX, y);
			}
		}
	}
}
