package cn.wydewy.filedinhand;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.wydewy.filedinhand.util.Constant;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectPostRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SignActivity extends Activity implements OnClickListener {
	// 请求
	private RequestQueue queue;
	private Handler handler;
	// 其他
	private SharedPreferences preferences;
	private ProgressDialog dialog;

	// 视图
	private EditText checkCodeET;
	private EditText usernameET;
	private EditText passwordET;

	private TextView loginTV;
	private TextView signUpTV;
	// 标志
	private int status = 0;
	private boolean isReadySignUp = false;
	private boolean isCheckCode = false;
	private static boolean isSendEmail = false;

	// 数据
	private String email;
	private String code;
	private String password;
	protected String accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		initDate();
		initView();
		initEvevt();
	}

	private void initDate() {
		// TODO Auto-generated method stub
		queue = Volley.newRequestQueue(getApplicationContext());
		handler = new Handler() {
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}

				switch (msg.what) {
				case Constant.SEND_EMAIL_OK:
					status = 2;
					updateViewByStatus();
					isSendEmail = true;
					Toast.makeText(SignActivity.this, "邮件发送成功，请查看验证码：",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.CHECK_CODE_OK:
					status = 3;
					updateViewByStatus();
					isCheckCode = true;
					Toast.makeText(SignActivity.this, "验证成功，请输入密码完成注册",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.REGISTER_OK:
					status = 0;
					updateViewByStatus();
					Toast.makeText(SignActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.LOGIN_OK:
					Toast.makeText(SignActivity.this, "登录成功！",
							Toast.LENGTH_SHORT).show();
					preferences = getSharedPreferences(Constant.LOGINFO,
							Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("isLogin", true);
					editor.putString("accessToken", accessToken);
					editor.putString("username", email);
					editor.putString("password", password);
					if (editor.commit()) {
						SignActivity.this.finish();
					}
					break;
				case Constant.LOGOUT_OK:
					Toast.makeText(SignActivity.this, "注销成功！",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.SEND_EMAIL_FAILED:
					Toast.makeText(SignActivity.this, "邮件发送失败",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.CHECK_CODE_FAILED:
					Toast.makeText(SignActivity.this, "验证失败",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.REGISTER_FAILED:
					Toast.makeText(SignActivity.this, "注册失败",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.LOGIN_FAILED:
					Toast.makeText(SignActivity.this, "登录失败",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.LOGOUT_FAILED:
					Toast.makeText(SignActivity.this, "注销失败",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.SERVER_ERROR:
					Toast.makeText(SignActivity.this, "服务错误",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
	}

	private void initEvevt() {
		// TODO Auto-generated method stub
		loginTV.setOnClickListener(this);
		signUpTV.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		checkCodeET = (EditText) findViewById(R.id.checkCodeET);
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		loginTV = (TextView) findViewById(R.id.loginTV);
		signUpTV = (TextView) findViewById(R.id.signUpTV);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.loginTV:
			dialog = ProgressDialog.show(this, "登录", "正在登录...",
					true, true);
			login();
			break;
		case R.id.signUpTV:
			signUp();
			break;
		}
	}

	/**
	 * 注册逻辑判断
	 */
	private void signUp() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "signUp", Toast.LENGTH_SHORT)
				.show();
		if (isReadySignUp) {// 验证邮箱
			if (isSendEmail) {
				if (isCheckCode) {// 已经验证邮箱，进行注册
					register();
					dialog = ProgressDialog.show(this, "注册",
							"正在注册...", true, true);
				} else {// 验证邮箱验证码
					checkCode();
					dialog = ProgressDialog.show(this, "验证",
							"正在验证验证码...", true, true);
				}
			} else {
				sendEmail();// 发送邮件
				dialog = ProgressDialog.show(this, "邮箱验证",
						"正在验证邮箱...", true, true);
			}

		} else {// 去验证邮箱
			status = 1;
			updateViewByStatus();
		}
	}

	/**
	 * 发送邮件
	 */
	private void sendEmail() {
		// TODO Auto-generated method stub
		email = usernameET.getText().toString();
		Toast.makeText(getApplicationContext(), "发送邮件至：" + email,
				Toast.LENGTH_SHORT).show();
		String url = Constant.HOST + Constant.SEND_EMAIL;
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + response);
						handler.sendMessage(handler
								.obtainMessage(Constant.SEND_EMAIL_OK));
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + error.toString());
						handler.sendMessage(handler
								.obtainMessage(Constant.SEND_EMAIL_FAILED));
					}

				}) {

			protected Map<String, String> getParams() {
				Map<String, String> map = new HashMap<String, String>();
				// 在这里设置需要post的参数
				map.put("email", email);
				return map;
			}
		};
		queue.add(stringRequest);
	}

	/**
	 * 注册
	 */
	private void register() {
		// TODO Auto-generated method stub
		password = passwordET.getText().toString();
		String url = Constant.HOST + Constant.REGISTER;
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + response);
						handler.sendMessage(handler
								.obtainMessage(Constant.REGISTER_OK));
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("TAG", "error" + error.toString());
						// TODO Auto-generated method stub
						handler.sendMessage(handler
								.obtainMessage(Constant.REGISTER_FAILED));
					}

				}) {

			protected Map<String, String> getParams() {
				Map<String, String> map = new HashMap<String, String>();
				// 在这里设置需要post的参数
				map.put("username", email);
				map.put("password", password);
				return map;
			}
		};
		queue.add(stringRequest);
	}

	/**
	 * 验证邮箱
	 */
	private void checkCode() {
		// TODO Auto-generated method stub
		code = checkCodeET.getText().toString();
		String url = Constant.HOST + Constant.CHECK_CODE;
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + response);
						handler.sendMessage(handler
								.obtainMessage(Constant.CHECK_CODE_OK));
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("TAG", "error" + error.toString());
						// TODO Auto-generated method stub
						handler.sendMessage(handler
								.obtainMessage(Constant.CHECK_CODE_FAILED));
					}

				}) {

			protected Map<String, String> getParams() {
				Map<String, String> map = new HashMap<String, String>();
				// 在这里设置需要post的参数
				map.put("email", email);
				map.put("code", code);
				return map;
			}
		};
		queue.add(stringRequest);
	}

	/**
	 * 登录
	 */
	private void login() {
		// TODO Auto-generated method stub
		email = usernameET.getText().toString();
		password = passwordET.getText().toString();
		String url = Constant.HOST + Constant.LOGIN;
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", email);
		map.put("password", password);
		JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
				url, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject object) {
						// TODO Auto-generated method stub
						try {
							Log.i("TAG",object.toString() );
							accessToken = object.getString("access_token");
							if (accessToken != null) {
								handler.sendMessage(handler
										.obtainMessage(Constant.LOGIN_OK));
							} else {
								handler.sendMessage(handler
										.obtainMessage(Constant.SERVER_ERROR));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("TAG",error.toString() );
						handler.sendMessage(handler
								.obtainMessage(Constant.LOGIN_FAILED));
					}
				}, map);
		queue.add(jsonObjectPostRequest);
	}

	private void updateViewByStatus() {
		switch (status) {
		case 0:
			isReadySignUp = false;
			isSendEmail = false;
			isSendEmail = false;
			isCheckCode = false;
			signUpTV.setText("注册");
			usernameET.setVisibility(View.VISIBLE);
			passwordET.setVisibility(View.VISIBLE);
			loginTV.setVisibility(View.VISIBLE);
			signUpTV.setVisibility(View.VISIBLE);
			checkCodeET.setVisibility(View.GONE);
			break;
		case 1:
			isReadySignUp = true;
			isSendEmail = false;
			isSendEmail = false;
			isCheckCode = false;
			signUpTV.setText("验证邮箱");
			usernameET.setVisibility(View.VISIBLE);
			passwordET.setVisibility(View.GONE);
			loginTV.setVisibility(View.GONE);
			signUpTV.setVisibility(View.VISIBLE);
			checkCodeET.setVisibility(View.GONE);
			break;
		case 2:
			isReadySignUp = true;
			isReadySignUp = true;
			isSendEmail = true;
			isCheckCode = false;
			signUpTV.setText("验证");
			usernameET.setVisibility(View.GONE);
			passwordET.setVisibility(View.GONE);
			loginTV.setVisibility(View.GONE);
			signUpTV.setVisibility(View.VISIBLE);
			checkCodeET.setVisibility(View.VISIBLE);
			break;
		case 3:
			isReadySignUp = true;
			isReadySignUp = true;
			isSendEmail = true;
			isCheckCode = true;
			signUpTV.setText("注册");
			usernameET.setVisibility(View.VISIBLE);
			passwordET.setVisibility(View.VISIBLE);
			loginTV.setVisibility(View.GONE);
			signUpTV.setVisibility(View.VISIBLE);
			checkCodeET.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			status--;
			if (status < 0) {
				this.finish();
			} else {
				updateViewByStatus();
			}
			break;
		default:
			break;
		}
		return true;
	}
}
