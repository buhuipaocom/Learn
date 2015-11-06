package cn.wydewy.cugbnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences preferences = getSharedPreferences("logInfo",
				Context.MODE_PRIVATE);
		boolean isLogin = preferences.getBoolean("isLogin", false);
		if (isLogin) {
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			MainActivity.this.finish();
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this,
							LoginActivity.class));
					MainActivity.this.finish();
				}
			}, 1000);
		}

	}
}
