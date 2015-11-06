package cn.wydewy.cugbnet;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class CugbNetApplication extends Application {
	public boolean isLogin = false;
	public boolean isRemember = false;
	public boolean isAutoLogin = false;

	public boolean isAppRunning() {
		ActivityManager am = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		String MY_PKG_NAME = "cn.wydewy.cugbnet";
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
					|| info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
				Log.i("TAG", info.topActivity.getPackageName()
						+ " info.baseActivity.getPackageName()="
						+ info.baseActivity.getPackageName());
				return true;
			}
		}
		return false;
	}
}
