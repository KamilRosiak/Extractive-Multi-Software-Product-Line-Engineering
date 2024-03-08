package net.apogames.apomono;

import net.apogames.apomono.game.ApoMonoPanel;
import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.utils.BitsLog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class ApoMonoMusicPlayer implements OnCompletionListener {

	private final int[] MUSIC_INGAME = new int[] {
			R.raw.ingame_1,
			R.raw.ingame_2,
			R.raw.ingame_3
	};
	
	private final int MENU = R.raw.menu;
	
	private MediaPlayer mAudio = null;
	
	private int curFile = -1;
	
	private boolean bMenu = true;
	
	private final ApoMonoPanel panel;
	
	public ApoMonoMusicPlayer(final ApoMonoPanel panel) {
		this.panel = panel;
	}

	public void setMenu(boolean bMenu) {
		if (bMenu != this.bMenu) {
			this.bMenu = bMenu;
			this.curFile = -1;
			if (this.panel.isMusicOn()) {
				this.stop();
				this.load();
			}
		}
	}
	
	public final void load( ) {
		boolean bLoop = false;
		if (!this.bMenu) {
			int nextString = this.MUSIC_INGAME[(int)(Math.random() * this.MUSIC_INGAME.length)];
			while (nextString == this.curFile) {
				nextString = this.MUSIC_INGAME[(int)(Math.random() * this.MUSIC_INGAME.length)];
			}
			this.curFile = nextString;
		} else {
			this.curFile = MENU;
			bLoop = true;
		}
		
		BitsLog.d("player", ""+this.curFile);
		this.mAudio = MediaPlayer.create(BitsApp.sAppContext, this.curFile);
		this.mAudio.setOnCompletionListener(this);
		
		play(0.6f, bLoop);
	}
	
	public final void release() {
		if( this.mAudio != null ) {
			if( this.mAudio.isPlaying() == true || this.mAudio.isLooping() == true ) {
				this.mAudio.stop();
			}
			this.mAudio.release();
			this.mAudio = null;
		}
	}

	private final void play(final float volume, final boolean loop) {
		if (this.mAudio != null) {
			this.stop();
		}
		if( this.mAudio != null ) {
			this.mAudio.setLooping(loop);
			this.mAudio.setVolume(volume, volume);
			this.mAudio.start();
		}
	}
	
	public final void stop( ) {
		if( this.mAudio != null ) {
			if (this.mAudio.isPlaying() || this.mAudio.isLooping()) {
				this.mAudio.stop();
			}
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (!mp.isLooping()) {
			this.load();
		}
	}
}
