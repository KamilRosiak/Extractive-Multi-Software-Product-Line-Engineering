package com.apogames.mytreasure;

import com.apogames.mytreasure.game.MyTreasurePanel;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.utils.BitsLog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.content.SharedPreferences;

public class MyTreasureActivity extends BitsApp {

	public static SharedPreferences settings;
	
	public static NetworkInfo ni;
	
	@Override
	protected void onCreateApp() {
		BitsLog.setLogType(BitsLog.TYPE_NONE);
		
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		MyTreasureActivity.ni = cm.getActiveNetworkInfo();
		
		MyTreasureActivity.settings = this.getSharedPreferences(MyTreasureConstants.PREFS_NAME, 0);

		BitsApp.sGameWidth = MyTreasureConstants.GAME_WIDTH;
		BitsApp.sGameHeight = MyTreasureConstants.GAME_HEIGHT;

		BitsApp.sMaxUpdate = 100;
//		BitsApp.sMaxFPS = 60;
		BitsApp.sOrientationMode = BitsApp.ORIENTATION_PORTRAIT;
		BitsApp.sWantTitleBar = false;
		BitsApp.sMaxCirclePoints = 180;
		BitsApp.sMaxTouchPointer = 3;
//		BitsApp.sSleepMode = BitsApp.SLEEP_MODE_OFF;
		
		BitsGame.getInstance().addScreen(new MyTreasurePanel(1));
	}

	public static boolean isOnline() {
		if (MyTreasureActivity.ni == null) {
			return false;
		}
		return MyTreasureActivity.ni.isConnected();
	}
	
	@Override
	protected void onStopApp() {
	}

	@Override
	protected void onAddView() {
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
	}

	@Override
	protected void onInitApp() {
	}

	@Override
	protected void onPauseApp() {
	}

	@Override
	protected void onResumeApp() {
	}

	@Override
	protected void onDestroyApp() {
	}

}
