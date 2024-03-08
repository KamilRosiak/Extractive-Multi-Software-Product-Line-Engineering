package apoMario.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioEntity;
import apoMario.entity.ApoMarioPlayer;

/**
 * Klasse, die sich um das Replay kümmert und speichert und wiedergibt
 * @author Dirk Aporius
 *
 */
public class ApoMarioReplay {

	public static final int RIGHT_STAND_JUMP_NORMAL = 0;
	public static final int RIGHT_STAND_JUMP_FAST = 1;
	public static final int RIGHT_STAND_NOJUMP_NORMAL = 2;
	public static final int RIGHT_STAND_NOJUMP_FAST = 3;
	public static final int RIGHT_STAND_DUCK_NORMAL = 4;
	public static final int RIGHT_STAND_DUCK_FAST = 5;
	public static final int RIGHT_NORMAL_JUMP = 6;
	public static final int RIGHT_NORMAL_NOJUMP = 7;
	public static final int RIGHT_FAST_JUMP = 8;
	public static final int RIGHT_FAST_NOJUMP = 9;
	public static final int LEFT_STAND_JUMP_NORMAL = 10;
	public static final int LEFT_STAND_JUMP_FAST = 11;
	public static final int LEFT_STAND_NOJUMP_NORMAL = 12;
	public static final int LEFT_STAND_NOJUMP_FAST = 13;
	public static final int LEFT_STAND_DUCK_NORMAL = 14;
	public static final int LEFT_STAND_DUCK_FAST = 15;
	public static final int LEFT_NORMAL_JUMP = 16;
	public static final int LEFT_NORMAL_NOJUMP = 17;
	public static final int LEFT_FAST_JUMP = 18;
	public static final int LEFT_FAST_NOJUMP = 19;

	private int width;
	private int difficulty;
	private long random;
	private String levelString;

	private ApoMarioReplayHelp playerOne;
	private ApoMarioReplayHelp playerTwo;

	public ApoMarioReplay() {
		this.init();
	}

	public void init() {
		this.playerOne = new ApoMarioReplayHelp();
		this.playerTwo = new ApoMarioReplayHelp();
	}
	
	public synchronized ApoMarioReplay getClone() {
		ApoMarioReplay replay = new ApoMarioReplay();
		
		replay.setLevel(this.width, this.difficulty, this.random, this.levelString);
		replay.setPlayerOne(this.playerOne.getClone());
		replay.setPlayerTwo(this.playerTwo.getClone());
		
		return replay;
	}

	public void setPlayerOne(ApoMarioReplayHelp playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(ApoMarioReplayHelp playerTwo) {
		this.playerTwo = playerTwo;
	}

	public int getWidth() {
		return this.width;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public long getRandom() {
		return this.random;
	}

	public String getLevelString() {
		return this.levelString;
	}

	public void setLevel(int width, int difficulty, long random, String levelString) {
		this.width = width;
		this.difficulty = difficulty;
		this.random = random;
		this.levelString = levelString;
	}

	public ApoMarioReplayHelp getPlayerOne() {
		return this.playerOne;
	}

	public ApoMarioReplayHelp getPlayerTwo() {
		return this.playerTwo;
	}

	public synchronized void setPlayerStep(ApoMarioPlayer player) {
		int step = ApoMarioReplay.RIGHT_STAND_JUMP_NORMAL;
		if (player.getGoalVecX() == 0) {
			if (player.isBDown()) {
				if (player.isBSpeed()) {
					step = ApoMarioReplay.RIGHT_STAND_DUCK_FAST;
				} else {
					step = ApoMarioReplay.RIGHT_STAND_DUCK_NORMAL;	
				}
			} else if (player.isBJump()) {
				if (player.isBSpeed()) {
					step = ApoMarioReplay.RIGHT_STAND_JUMP_FAST;
				} else {
					step = ApoMarioReplay.RIGHT_STAND_JUMP_NORMAL;	
				}
			} else {
				if (player.isBSpeed()) {
					step = ApoMarioReplay.RIGHT_STAND_NOJUMP_FAST;
				} else {
					step = ApoMarioReplay.RIGHT_STAND_NOJUMP_NORMAL;	
				}
			}
		} else {
			if (player.isBSpeed()) {
				if (player.isBJump()) {
					if (player.isBDown()) {
						step = ApoMarioReplay.RIGHT_STAND_DUCK_FAST;
					} else {
						step = ApoMarioReplay.RIGHT_FAST_JUMP;
					}
				} else {
					if (player.isBDown()) {
						step = ApoMarioReplay.RIGHT_STAND_DUCK_FAST;
					} else {
						step = ApoMarioReplay.RIGHT_FAST_NOJUMP;
					}
				}
			} else {
				if (player.isBJump()) {
					if (player.isBDown()) {
						step = ApoMarioReplay.RIGHT_STAND_DUCK_NORMAL;
					} else {
						step = ApoMarioReplay.RIGHT_NORMAL_JUMP;
					}
				} else {
					if (player.isBDown()) {
						step = ApoMarioReplay.RIGHT_STAND_DUCK_NORMAL;
					} else {
						step = ApoMarioReplay.RIGHT_NORMAL_NOJUMP;
					}
				}
			}
		}
		if (player.getDirection() == ApoMarioEntity.LEFT) {
			step += ApoMarioReplay.LEFT_STAND_JUMP_NORMAL;
		}
		if (player.getPlayer() == 0) {
			this.playerOne.addNextStep(step);
			if ((this.playerOne.getName() == null) || (this.playerOne.getName().length() < 1)) {
				if (player.getAi() == null) {
					this.playerOne.setAuthor("you");
					this.playerOne.setName("human");
				} else {
					this.playerOne.setAuthor(player.getAi().getAuthor());
					this.playerOne.setName(player.getAi().getTeamName());
				}
			}
		} else {
			this.playerTwo.addNextStep(step);
			if ((this.playerTwo.getName() == null) || (this.playerTwo.getName().length() < 1)) {
				if (player.getAi() != null) {
					this.playerTwo.setAuthor(player.getAi().getAuthor());
					this.playerTwo.setName(player.getAi().getTeamName());
				} else {
					this.playerTwo.setAuthor("");
					this.playerTwo.setName("");
				}
			}
		}
	}
	
	public synchronized int setStep(ApoMarioPlayer playerOne, ApoMarioPlayer playerTwo, int step) {
		int value = -1;
		if ((this.playerOne == null) || (this.playerOne.getReplayStep(step) == -1)) {
			return value;
		} else {
			value = this.playerOne.getReplayStep(step);
			this.setPlayerStep(playerOne, value);
			if (this.playerTwo != null) {
				if ((!playerTwo.isBVisible()) && (this.playerTwo.getMaxSteps() == this.playerOne.getMaxSteps())) {
					playerTwo.setBVisible(true);
				} else if ((playerTwo.isBVisible()) && (Math.abs(this.playerTwo.getMaxSteps() - this.playerOne.getMaxSteps()) >= 2)) {
					playerTwo.setBVisible(false);					
				}
				this.setPlayerStep(playerTwo, this.playerTwo.getReplayStep(step));
			} else {
				if (playerTwo.isBVisible()) {
					playerTwo.setBVisible(false);
				}
			}
		}
		return value;
	}
	
	private synchronized void setPlayerStep(ApoMarioPlayer player, int step) {
		if (ApoMarioReplay.RIGHT_FAST_JUMP == step) {
			player.setBSpeed(true);
			player.setBNextJump(true);
			player.setBDown(false);
			player.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
		} else if (ApoMarioReplay.RIGHT_FAST_NOJUMP == step) {
			player.setBSpeed(true);
			player.setBNextJump(false);
			player.setBDown(false);
			player.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
		} else if (ApoMarioReplay.RIGHT_NORMAL_JUMP == step) {
			player.setBNextJump(true);
			player.setBSpeed(false);
			player.setBDown(false);
			player.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
		} else if (ApoMarioReplay.RIGHT_NORMAL_NOJUMP == step) {
			player.setBSpeed(false);
			player.setBNextJump(false);
			player.setBDown(false);
			player.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
		} else if (	(ApoMarioReplay.RIGHT_STAND_DUCK_NORMAL == step) ||
					(ApoMarioReplay.LEFT_STAND_DUCK_NORMAL == step)) {
			player.setBDown(true);
			player.setBSpeed(false);
			player.setBNextJump(false);
			player.setGoalVecX(0);
		} else if (	(ApoMarioReplay.RIGHT_STAND_DUCK_FAST == step) ||
					(ApoMarioReplay.LEFT_STAND_DUCK_FAST == step)) {
			player.setBDown(true);
			player.setBSpeed(true);
			player.setBNextJump(false);
			player.setGoalVecX(0);
		} else if (	(ApoMarioReplay.RIGHT_STAND_JUMP_FAST == step) ||
					(ApoMarioReplay.LEFT_STAND_JUMP_FAST == step)) {
			player.setBNextJump(true);
			player.setBSpeed(true);
			player.setBDown(false);
			player.setGoalVecX(0);
		} else if (	(ApoMarioReplay.RIGHT_STAND_JUMP_NORMAL == step) ||
					(ApoMarioReplay.LEFT_STAND_JUMP_NORMAL == step)) {
			player.setBNextJump(true);
			player.setBSpeed(false);
			player.setBDown(false);
			player.setGoalVecX(0);
		} else if (	(ApoMarioReplay.RIGHT_STAND_NOJUMP_FAST == step) ||
					(ApoMarioReplay.LEFT_STAND_NOJUMP_FAST == step)) {
			player.setBSpeed(true);
			player.setBDown(false);
			player.setBNextJump(false);
			player.setGoalVecX(0);
		} else if (	(ApoMarioReplay.RIGHT_STAND_NOJUMP_NORMAL == step) ||
					(ApoMarioReplay.LEFT_STAND_NOJUMP_NORMAL == step)) {
			player.setBSpeed(false);
			player.setBDown(false);
			player.setBNextJump(false);
			player.setGoalVecX(0);
		} else if (ApoMarioReplay.LEFT_FAST_JUMP == step) {
			player.setBSpeed(true);
			player.setBNextJump(true);
			player.setBDown(false);
			player.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
		} else if (ApoMarioReplay.LEFT_FAST_NOJUMP == step) {
			player.setBSpeed(true);
			player.setBDown(false);
			player.setBNextJump(false);
			player.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
		} else if (ApoMarioReplay.LEFT_NORMAL_JUMP == step) {
			player.setBNextJump(true);
			player.setBSpeed(false);
			player.setBDown(false);
			player.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
		} else if (ApoMarioReplay.LEFT_NORMAL_NOJUMP == step) {
			player.setBSpeed(false);
			player.setBDown(false);
			player.setBNextJump(false);
			player.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
		}
	}
	
	public void load(String fileName) {
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			this.loadLevel(read);
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	private void loadLevel(BufferedReader reader) throws IOException {
		this.width = Integer.valueOf(reader.readLine());
		this.difficulty = Integer.valueOf(reader.readLine());
		this.random = Long.valueOf(reader.readLine());
		if (this.random == -1) {
			this.levelString = reader.readLine();
		} else {
			this.levelString = null;
		}
		
		//Player One
		String name = reader.readLine();
		String author = reader.readLine();
		String replay = reader.readLine();
		this.playerOne = new ApoMarioReplayHelp(name, author);
		this.playerOne.setReplay(replay);
		
		this.playerTwo = new ApoMarioReplayHelp();
		//Player Two
		name = reader.readLine();
		author = reader.readLine();
		replay = reader.readLine();
		this.playerTwo = new ApoMarioReplayHelp(name, author);
		this.playerTwo.setReplay(replay);
	}

	public void save(String fileName) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName))); 
			this.saveLevel(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	private void saveLevel(BufferedWriter writer) throws IOException {
		writer.write(this.width+"\n");
		writer.write(this.difficulty+"\n");
		writer.write(this.random+"\n");
		if (this.random == -1) {
			if (this.levelString != null) {
				writer.write(this.levelString+"\n");
			}
		}
		
		//Player One
		writer.write(this.playerOne.getName()+"\n");
		writer.write(this.playerOne.getAuthor()+"\n");
		this.playerOne.setReplay(this.playerOne.getReplay());
		writer.write(this.playerOne.getReplay()+"\n");
		
		//Player Two
		if (this.playerTwo != null) {
			writer.write(this.playerTwo.getName()+"\n");
			writer.write(this.playerTwo.getAuthor()+"\n");
			this.playerTwo.setReplay(this.playerTwo.getReplay());
			writer.write(this.playerTwo.getReplay()+"\n");
		}
	}
	
	
}
