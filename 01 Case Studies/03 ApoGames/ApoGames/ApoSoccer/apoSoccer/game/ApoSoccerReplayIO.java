package apoSoccer.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.apogames.help.ApoFloatPoint;

/**
 * Klasse, die sich um das Laden und Speichern von Replays kümmert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerReplayIO {

	/** Objekt mit dem Replay */
	private ApoSoccerReplay replay;
	
	public ApoSoccerReplayIO() {
		
	}
	
	/**
	 * gibt das derzeitige Replay zurück
	 * @return gibt das derzeitige Replay zurück
	 */
	public ApoSoccerReplay getReplay() {
		return this.replay;
	}

	/**
	 * setzt das Replay auf den übergebenen Wert
	 * @param replay : neues Replayobjekt
	 */
	public void setReplay(ApoSoccerReplay replay) {
		this.replay = replay;
	}

	/**
	 * versucht ein Replay mit dem Pfad s zu laden
	 * @param s : Pfad zum Laden
	 * @return TRUE, wenn alles geladen werden konnte, sonst FALSE
	 */
	public boolean readLevel(String s) {
		RandomAccessFile data = null;
		try {
			data = new RandomAccessFile(s, "rw");
			this.replay.setHomeTeamName(data.readUTF());
			this.replay.setVisitorTeamName(data.readUTF());
			
			this.replay.setBall( this.readPlayer(data) );
			
			this.replay.setHomeDefenderOne( this.readPlayer(data) );
			this.replay.setHomeDefenderTwo( this.readPlayer(data) );
			this.replay.setHomeForward( this.readPlayer(data) );
			this.replay.setHomeGoalkeeper( this.readPlayer(data) );
			
			this.replay.setVisitorDefenderOne( this.readPlayer(data) );
			this.replay.setVisitorDefenderTwo( this.readPlayer(data) );
			this.replay.setVisitorForward( this.readPlayer(data) );
			this.replay.setVisitorGoalkeeper( this.readPlayer(data) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * versucht ein Level zu speichern in den übergeben Pfad 
	 * @param s : Pfad
	 * @return TRUE, wenn die DAtei gespeichert werden konnte, sonst FALSE
	 */
	public boolean writeLevel(String s) {
		RandomAccessFile data = null;
		try {
			data = new RandomAccessFile(s, "rw");
			data.writeUTF(this.replay.getHomeTeamName());;
			data.writeUTF(this.replay.getVisitorTeamName());
			
			this.writePlayer(this.replay.getBall(), data);
			
			this.writePlayer(this.replay.getHomeDefenderOne(), data);
			this.writePlayer(this.replay.getHomeDefenderTwo(), data);
			this.writePlayer(this.replay.getHomeForward(), data);
			this.writePlayer(this.replay.getHomeGoalkeeper(), data);
			
			this.writePlayer(this.replay.getVisitorDefenderOne(), data);
			this.writePlayer(this.replay.getVisitorDefenderTwo(), data);
			this.writePlayer(this.replay.getVisitorForward(), data);
			this.writePlayer(this.replay.getVisitorGoalkeeper(), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * lädt alle Daten zu einer Entity
	 * @param data : Objekt von dem geladen wird
	 * @return eine ArrayList mit ApoFloatPoints
	 * @throws IOException
	 */
	private ArrayList<ApoFloatPoint> readPlayer(RandomAccessFile data) throws IOException {
		ArrayList<ApoFloatPoint> player = new ArrayList<ApoFloatPoint>();
		int size = data.readInt();
		for (int i = 0; i < size; i++) {
			float x = data.readFloat();
			float y = data.readFloat();
			player.add(new ApoFloatPoint(x, y));
		}
		return player;
	}
	
	/**
	 * schreibt alle Daten zu einer Entity in die Datei
	 * @param player : Die Entity
	 * @param data : Objekt in das geschrieben wird
	 * @return immer TRUE
	 * @throws IOException
	 */
	private boolean writePlayer(ArrayList<ApoFloatPoint> player, RandomAccessFile data) throws IOException {
		data.writeInt(player.size());
		for (int i = 0; i < player.size(); i++) {
			data.writeFloat(player.get(i).x);
			data.writeFloat(player.get(i).y);
		}
		return true;
	}

}
