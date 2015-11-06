package cn.wydewy.yuejuba;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import cn.wydewy.yuejuba.R;
import cn.wydewy.vnique.activity.LogoutActivity;
import cn.wydewy.yuejuba.util.Constant;

public class YJBLogoutActivity extends LogoutActivity {

	private YueJuBaApplication application;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (YueJuBaApplication) getApplication();
	}

	@Override
	protected void onViewClick(View v) {
		// TODO Auto-generated method stub
		super.onViewClick(v);
		switch (v.getId()) {
		case R.id.backImg:
			this.finish();
			break;
		case R.id.logout:
			logout();
			break;
		}
	}

	private void logout() {
		username = usernameET.getText().toString();
		String password = passwordET.getText().toString();
		if (username == null || "".equals(username)) {
			Toast.makeText(getApplicationContext(), "用户名不能为空！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || "".equals(password)) {
			Toast.makeText(getApplicationContext(), "密码不能为空！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		gotoLogout(Constant.HOST + Constant.LOGOUT, map);
	}

	@Override
	protected void dologout() {
		super.dologout();
		application.isLogout = true;
		application.isLogin = false;

		if (preferences != null) {
			Editor editor = preferences.edit();
			editor.putString("username", "");
			editor.commit();
		}

		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("page", 4);
		bundle.putString("username", "");
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void doOnResponse(String response) {
		// TODO Auto-generated method stub
		super.doOnResponse(response);
		if ("1".equals(response)) {
			Log.i("TAG", "注销成功！");
			Toast.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT)
					.show();
			sendOneMsg(LOGOUT_OK);
		} else if ("0".equals(response)) {
			Log.i("TAG", "注销失败！");
			Toast.makeText(getApplicationContext(), "注销失败！用户名或密码错误~",
					Toast.LENGTH_SHORT).show();
			sendOneMsg(LOGOUT_ERROR);
		}
	}

	@Override
	protected void doOnErrorResponse(VolleyError arg0) {
		// TODO Auto-generated method stub
		super.doOnErrorResponse(arg0);
		Log.i("TAG", "网络错误，注销失败！");
		Toast.makeText(getApplicationContext(), "网络错误，注销失败", Toast.LENGTH_SHORT)
				.show();
		sendOneMsg(NETWORK_ERROR);
	}

}
