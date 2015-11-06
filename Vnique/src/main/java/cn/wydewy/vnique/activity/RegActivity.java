package cn.wydewy.vnique.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.wydewy.vnique.R;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegActivity extends Activity implements OnClickListener {
	protected ImageView backImg;

	protected EditText usernameET;
	protected EditText passwordET;
	protected RelativeLayout regBtn;

	protected RequestQueue queue;

	protected RegHandler handler;

	protected SharedPreferences preferences;
	private static final int REG_OK = 0;
	protected static final int REG_ERROR = 1;
	protected static final int NETWORK_ERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		backImg.setOnClickListener(this);
		regBtn.setOnClickListener(this);
		queue = Volley.newRequestQueue(this);
		handler = new RegHandler();
	}

	private void initView() {
		// TODO Auto-generated method stub
		backImg = (ImageView) findViewById(R.id.backImg);
		regBtn = (RelativeLayout) findViewById(R.id.reg);

		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
	}

	protected void gotoReg(String listUrl, final Map<String, String> map) {
		// TODO Auto-generated method stub
		StringRequest stringRequest = new StringRequest(Method.POST, listUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						if ("1".equals(response)) {
							Log.i("TAG", "注册成功！");
							Toast.makeText(getApplicationContext(), "注册成功",
									Toast.LENGTH_SHORT).show();
							handler.sendMessage(handler.obtainMessage(REG_OK));

						} else if ("0".equals(response)) {
							Log.i("TAG", "注册失败！");
							Toast.makeText(getApplicationContext(), "注册失败！",
									Toast.LENGTH_SHORT).show();
							handler.sendMessage(handler
									.obtainMessage(REG_ERROR));

						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("TAG", "网络错误，注册失败！");
						Toast.makeText(getApplicationContext(), "网络错误，注册失败",
								Toast.LENGTH_SHORT).show();
						handler.sendMessage(handler
								.obtainMessage(NETWORK_ERROR));
					}

				}) {
			protected Map<String, String> getParams() {
				// 在这里设置需要post的参数
				return map;
			}
		};
		queue.add(stringRequest);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onViewClick(v);
	}

	protected void onViewClick(View view) {

	}

	private class RegHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case REG_OK:
				doReg();
				break;
			}
		}

	}

	protected void doReg() {
		// TODO Auto-generated method stub
		if (preferences != null) {
			Editor editor = preferences.edit();
			editor.putBoolean("isLogin", false);
			editor.commit();
		}
	}
}
