package cn.wydewy.yuejuba;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.wydewy.vnique.R;
import cn.wydewy.vnique.activity.RegActivity;
import cn.wydewy.yuejuba.util.Constant;

public class YJBRegActivity extends RegActivity {

	private String username;

	@Override
	protected void onViewClick(View v) {
		// TODO Auto-generated method stub
		super.onViewClick(v);
		switch (v.getId()) {
		case R.id.backImg:
			this.finish();
			break;

		case R.id.reg:
			reg();
			break;
		}
	}

	private void reg() {
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
		gotoReg(Constant.HOST + Constant.REG, map);
	}

	@Override
	protected void doReg() {
		
		Intent intent = new Intent(this, YJBLoginActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("username", username);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}
}
