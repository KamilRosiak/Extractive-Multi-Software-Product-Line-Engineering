package net.apogames.apomono;

import net.gliblybits.bitsengine.sound.BitsSound;
import net.gliblybits.bitsengine.sound.BitsSoundFactory;

public class ApoMonoSoundPlayer {

	public static BitsSound SOUND_WIN;
	public static BitsSound SOUND_BUTTON;
	public static BitsSound SOUND_BUTTON_2;
	public static BitsSound SOUND_FOLD;
	public static BitsSound SOUND_GRAVITY;
	public static BitsSound SOUND_LOSE;
	public static BitsSound SOUND_DROP;
	public static BitsSound SOUND_PICK;
	
	public ApoMonoSoundPlayer() {		
		SOUND_WIN = BitsSoundFactory.getInstance().getSound(R.raw.win, BitsSound.TYPE_WAV, true);
		SOUND_BUTTON = BitsSoundFactory.getInstance().getSound(R.raw.button, BitsSound.TYPE_WAV, true);
		SOUND_BUTTON_2 = BitsSoundFactory.getInstance().getSound(R.raw.button_2, BitsSound.TYPE_WAV, true);
		SOUND_FOLD = BitsSoundFactory.getInstance().getSound(R.raw.fold, BitsSound.TYPE_WAV, true);
		SOUND_GRAVITY = BitsSoundFactory.getInstance().getSound(R.raw.gravitychange, BitsSound.TYPE_WAV, true);
		SOUND_LOSE = BitsSoundFactory.getInstance().getSound(R.raw.loose, BitsSound.TYPE_WAV, true);
		SOUND_DROP = BitsSoundFactory.getInstance().getSound(R.raw.drop, BitsSound.TYPE_WAV, true);
		SOUND_PICK = BitsSoundFactory.getInstance().getSound(R.raw.pick, BitsSound.TYPE_WAV, true);
		
		BitsSoundFactory.getInstance().loadAll();
	}
	
	public void playSound(BitsSound sound) {
		sound.play(1f, 1f, false);
	}
}
