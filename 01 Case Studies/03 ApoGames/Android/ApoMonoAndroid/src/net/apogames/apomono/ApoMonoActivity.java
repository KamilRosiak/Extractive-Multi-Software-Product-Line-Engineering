package net.apogames.apomono;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import net.apogames.apomono.game.ApoMonoPanel;
import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.utils.BitsLog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.SharedPreferences;

public class ApoMonoActivity extends BitsApp {

	public static SharedPreferences settings;
	
	public static NetworkInfo ni;
	
	public static AdView adView = null;
	
	public static ApoMonoActivity activity;
	
	@Override
	protected void onCreateApp() {
		BitsLog.setLogType(BitsLog.TYPE_DEBUG);
		
		ApoMonoActivity.activity = this;
		
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		ApoMonoActivity.ni = cm.getActiveNetworkInfo();
		
		ApoMonoActivity.settings = this.getSharedPreferences(ApoMonoConstants.PREFS_NAME, 0);
		
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int height = dm.heightPixels;
		int width = dm.widthPixels;
		
		BitsApp.sGameWidth = ApoMonoConstants.GAME_WIDTH;
		BitsApp.sGameHeight = ApoMonoConstants.GAME_HEIGHT;
		
		float originalDisplay = (float)(width) / (float)(height);
		float gameDisplay = (float)(ApoMonoConstants.GAME_WIDTH) / (float)(ApoMonoConstants.GAME_HEIGHT);
		if (originalDisplay >= gameDisplay) {
			ApoMonoConstants.MAX = (float)(height) / (float)(ApoMonoConstants.GAME_HEIGHT);
		} else {
			ApoMonoConstants.MAX = (float)(width) / (float)(ApoMonoConstants.GAME_WIDTH);			
		}
		//BitsApp.sViewportWidth = (int)(ApoMonoConstants.GAME_WIDTH * ApoMonoConstants.MAX);//1280;//1920;//(int)(ApoMonoConstants.GAME_WIDTH * ApoMonoConstants.MAX);
		//BitsApp.sViewportHeight = (int)(ApoMonoConstants.GAME_HEIGHT * ApoMonoConstants.MAX);//800;//1080;//(int)(ApoMonoConstants.GAME_HEIGHT * ApoMonoConstants.MAX);
//		BitsApp.sWantFullscreen = false;
//		BitsApp.sMaxFPS = 60;
		BitsApp.sMaxUpdate = 100;
		BitsApp.sOrientationMode = BitsApp.ORIENTATION_LANDSCAPE;
		BitsApp.sWantTitleBar = false;
		BitsApp.sMaxCirclePoints = 180;
		BitsApp.sMaxTouchPointer = 3;
//		BitsApp.sSleepMode = BitsApp.SLEEP_MODE_OFF;

		BitsGame.getInstance().addScreen(new ApoMonoPanel(1));
	}
	
	public static boolean isOnline() {
		if (ApoMonoActivity.ni == null) {
			return false;
		}
		return ApoMonoActivity.ni.isConnected();
	}

	@Override
	protected void onAddView() {
		if (ApoMonoConstants.FREE_VERSION) {
			// Create the adView
		    adView = new AdView(this, AdSize.BANNER, "a1510eb01a099dc"); 
		    AdRequest request = new AdRequest();
			request.addTestDevice(AdRequest.TEST_EMULATOR);
			adView.loadAd(request);
			
			RelativeLayout.LayoutParams adParams =
	                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			this.addView(adView, adParams);
		}
	}

	@Override
	public void onAudioFocusChange(int arg0) {

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
	protected void onStopApp() {

	}

	@Override
	protected void onDestroyApp() {
		// TODO Auto-generated method stub
		
	}
}