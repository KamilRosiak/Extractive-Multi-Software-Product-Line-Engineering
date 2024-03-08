package com.apogames.mytreasure;

import net.gliblybits.bitsengine.sound.BitsSound;
import net.gliblybits.bitsengine.sound.BitsSoundFactory;

public class MyTreasureSoundPlayer {

	public static BitsSound SOUND_CLICK;
	public static BitsSound SOUND_WIN_1;
	public static BitsSound SOUND_WIN_2;
	public static BitsSound SOUND_WIN_3;
	
	public MyTreasureSoundPlayer() {		
		SOUND_CLICK = BitsSoundFactory.getInstance().getSound(R.raw.click_1, BitsSound.TYPE_OGG, true);
		SOUND_WIN_1 = BitsSoundFactory.getInstance().getSound(R.raw.coin_1, BitsSound.TYPE_OGG, true);
		SOUND_WIN_2 = BitsSoundFactory.getInstance().getSound(R.raw.coin_2, BitsSound.TYPE_OGG, true);
		SOUND_WIN_3 = BitsSoundFactory.getInstance().getSound(R.raw.coin_3, BitsSound.TYPE_OGG, true);
		
		BitsSoundFactory.getInstance().loadAll();
	}
	
	public void playSound(BitsSound sound) {
		sound.play(1f, 1f, false);
	}
}
