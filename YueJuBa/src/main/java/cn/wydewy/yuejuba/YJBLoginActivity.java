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
import cn.wydewy.vnique.R;
import cn.wydewy.vnique.activity.LoginActivity;
import cn.wydewy.yuejuba.util.Constant;

public class YJBLoginActivity extends LoginActivity {

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
		case R.id.login:
			login();
			break;
		case R.id.regTV:
			gotoReg();
			break;
		}
	}

	private void login() {
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
		gotoLogin(Constant.HOST + Constant.LOGIN, map);
	}

	@Override
	protected void doLogin() {
		super.doLogin();
		application.isLogin = true;

		if (preferences != null) {
			Editor editor = preferences.edit();
			editor.putString("username", username);
			editor.commit();
		}

		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("page", 4);
		bundle.putString("username", username);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}

	private void gotoReg() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, YJBRegActivity.class);
		startActivity(intent);
	}

	@Override
	protected void doOnResponse(String response) {
		// TODO Auto-generated method stub
		super.doOnResponse(response);
		if ("1".equals(response)) {
			Log.i("TAG", "登录成功！");
			Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT)
					.show();
			sendOneMsg(LOGIN_OK);
		} else if ("0".equals(response)) {
			Log.i("TAG", "登录失败！");
			Toast.makeText(getApplicationContext(), "登录失败！用户名或密码错误~",
					Toast.LENGTH_SHORT).show();
			sendOneMsg(LOGIN_ERROR);
		}

	}

	@Override
	protected void doOnErrorResponse(VolleyError arg0) {
		// TODO Auto-generated method stub
		super.doOnErrorResponse(arg0);
		Log.i("TAG", "网络错误，登录失败！");
		Toast.makeText(getApplicationContext(), "网络错误，登录失败", Toast.LENGTH_SHORT)
				.show();
		sendOneMsg(NETWORK_ERROR);
	}

}
