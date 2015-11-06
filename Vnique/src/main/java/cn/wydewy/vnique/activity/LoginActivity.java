package cn.wydewy.vnique.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.wydewy.vnique.R;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends Activity implements OnClickListener {
	protected ImageView backImg;
	protected TextView regTV;

	protected EditText usernameET;
	protected EditText passwordET;
	protected RelativeLayout loginBtn;

	protected RequestQueue queue;

	protected LoginHandler handler;
	protected SharedPreferences preferences;
	protected static final int LOGIN_OK = 0;
	protected static final int LOGIN_ERROR = 1;
	protected static final int NETWORK_ERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		backImg.setOnClickListener(this);
		regTV.setOnClickListener(this);
		loginBtn.setOnClickListener(this);

		queue = Volley.newRequestQueue(this);

		handler = new LoginHandler();
	}

	private void initView() {
		// TODO Auto-generated method stub
		backImg = (ImageView) findViewById(R.id.backImg);
		regTV = (TextView) findViewById(R.id.regTV);

		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);

		loginBtn = (RelativeLayout) findViewById(R.id.login);

		getIntentDates();

		getHistoryDates();
	}

	private void getHistoryDates() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
	}

	private void getIntentDates() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		String username = bundle.getString("username");
		usernameET.setText(username);
		passwordET.requestFocus();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onViewClick(v);
	}

	protected void onViewClick(View view) {

	}

	protected void gotoLogin(String listUrl, final Map<String, String> map) {
		// TODO Auto-generated method stub
		StringRequest stringRequest = new StringRequest(Method.POST, listUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						doOnResponse(response);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						doOnErrorResponse(error);
					}

				}) {
			protected Map<String, String> getParams() {
				// 在这里设置需要post的参数
				return map;
			}
		};
		queue.add(stringRequest);
	}

	protected void doOnResponse(String response) {
	}

	protected void doOnErrorResponse(VolleyError error) {
	}

	private class LoginHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGIN_OK:
				doLogin();
				break;
			}
		}

	}

	protected void doLogin() {
		// TODO Auto-generated method stub
		if (preferences != null) {
			Editor editor = preferences.edit();
			editor.putBoolean("isLogin", true);
			editor.commit();
		}

	}

	/**
	 * 发送消息通知ui线程处理不同请求结果
	 * 
	 * @param what
	 */
	protected void sendOneMsg(int what) {
		handler.sendMessage(handler.obtainMessage(what));
	}
}
