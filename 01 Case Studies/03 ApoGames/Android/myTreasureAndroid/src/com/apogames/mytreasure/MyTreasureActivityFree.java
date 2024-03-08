package com.apogames.mytreasure;

public class MyTreasureActivityFree extends MyTreasureActivity {


	@Override
	protected void onCreateApp() {
		MyTreasureConstants.FREE_VERSION = true;
		super.onCreateApp();
	}

}