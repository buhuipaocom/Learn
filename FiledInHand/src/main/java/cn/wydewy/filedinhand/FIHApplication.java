package cn.wydewy.filedinhand;

import org.litepal.LitePalApplication;

/**
 * Created by wydewy on 15/9/20.
 */
public class FIHApplication extends LitePalApplication {

	private static FIHApplication instance;
	public boolean isLogin = false;
	public int currentPage = 0;
	public int compassMode = 0;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}

	public static FIHApplication getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

}
