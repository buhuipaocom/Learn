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
import cn.wydewy.vnique.R;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LogoutActivity extends Activity implements OnClickListener {
	protected ImageView backImg;

	protected EditText usernameET;
	protected EditText passwordET;
	protected RelativeLayout logoutBtn;

	protected RequestQueue queue;

	protected logoutHandler handler;
	protected SharedPreferences preferences;
	protected static final int LOGOUT_OK = 0;
	protected static final int LOGOUT_ERROR = 1;
	protected static final int NETWORK_ERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout);
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		backImg.setOnClickListener(this);
		logoutBtn.setOnClickListener(this);

		queue = Volley.newRequestQueue(this);

		handler = new logoutHandler();
	}

	private void initView() {
		// TODO Auto-generated method stub
		backImg = (ImageView) findViewById(R.id.backImg);

		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);

		logoutBtn = (RelativeLayout) findViewById(R.id.logout);

		getIntentDates();

		getHistoryDates();
	}

	private void getHistoryDates() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		usernameET.setText(preferences.getString("username", ""));
		passwordET.setText(preferences.getString("password", ""));
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

	protected void gotoLogout(String listUrl, final Map<String, String> map) {
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

	private class logoutHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGOUT_OK:
				dologout();
				break;
			}
		}

	}

	protected void dologout() {
		// TODO Auto-generated method stub
		if (preferences != null) {
			Editor editor = preferences.edit();
			editor.putBoolean("islogout", true);
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
