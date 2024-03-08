package net.apogames.apoclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.apogames.apoclock.game.ApoClockPanel;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.utils.BitsLog;

public class ApoClock extends BitsApp {

	public static SharedPreferences settings;
	
	public static NetworkInfo ni;
	
	@Override
	protected void onCreateApp( ) 
	{
		BitsLog.setLogType(BitsLog.TYPE_NONE);
		
		BitsApp.sWantFullscreen = true;
		BitsApp.sOrientationMode = BitsApp.ORIENTATION_PORTRAIT;
		BitsApp.sGameWidth = 480;
		BitsApp.sGameHeight = 640;
		BitsApp.sWantTitleBar = false;
		BitsApp.sMaxCirclePoints = 180;
//		BitsApp.sMaxFPS = 60;
		BitsApp.sMaxUpdate = 100;
		BitsApp.sMaxTouchPointer = 3;
//		BitsApp.sSleepMode = BitsApp.SLEEP_MODE_OFF;
		
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		ApoClock.ni = cm.getActiveNetworkInfo();
		
		ApoClock.settings = this.getSharedPreferences(ApoClockConstants.PREFS_NAME, 0);
		
		BitsGame.getInstance().addScreen(new ApoClockPanel(1));
	}
	
	public static boolean isOnline() {
		if (ApoClock.ni == null) {
			return false;
		}
		return ApoClock.ni.isConnected();
	}

	@Override
	protected void onStopApp( ) {
	}

	@Override
	protected void onAddView() {
		
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onInitApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPauseApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResumeApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroyApp() {
		// TODO Auto-generated method stub
		
	}
}