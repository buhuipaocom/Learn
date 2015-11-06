package cn.wydewy.cugbnet;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ReConnectService extends Service {
	private Handler handler;
	private CugbNetApplication application;
	private boolean isUpdating = false;
	private RequestQueue queue;
	private String host;
	protected String password;
	protected String username;
	private SharedPreferences preferences;
	public long reconnectTiem = 60000;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		queue = Volley.newRequestQueue(getApplicationContext());
		preferences = getSharedPreferences("logInfo", Context.MODE_PRIVATE);
		if (preferences != null) {
			host = preferences.getString("host", "");
			username = preferences.getString("username", "");
			password = preferences.getString("password", "");
		}

	}

	@SuppressLint("HandlerLeak")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		application = (CugbNetApplication) getApplication();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (application != null && application.isAppRunning()) {
						Intent action = new Intent();
						action.setAction(LoginActivity.RECONNECT_ACTION);
						sendBroadcast(action);
						Log.i("TAG", "重连");
					} else {
						login();
						Toast.makeText(getApplicationContext(),
								"客户端程序已经被杀，进行自动连接。确保登录，请重新启动程序，这样程序将自动帮你登陆!",
								Toast.LENGTH_SHORT).show();
						reconnectTiem = 600000;
					}

					break;
				default:
					break;
				}
			}
		};
		new Thread(new MyThread()).start();
		updateInfo();
		return START_STICKY;
	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					if (application != null && application.isLogin) {
						Thread.sleep(reconnectTiem);// 线程暂停60秒，单位毫秒
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);// 发送消息
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void updateInfo() {
		// TODO Auto-generated method stub
		if (!isUpdating) {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.postDelayed(this, 20000);// 设置延迟时间，此处是20秒
					// 需要执行的代码
					if (application.isLogin) {
						Intent action = new Intent();
						action.setAction(LoginActivity.UPDATE_INFO_ACTION);
						sendBroadcast(action);
					}
				}
			}, 1200);
			isUpdating = true;
		}

	}

	private void login() {
		String url = "http://" + host + "/cgi-bin/do_login";
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						if ("mode_error".equals(response)) {
							Log.i("TAG", response + "登录失败！" + password);
						} else if (response.contains("密码错误")) {
							Log.i("TAG", response + "登录失败！" + password);
						} else if (response.contains("用户名错误")) {
							Log.i("TAG", response + "登录失败！" + password);
							Toast.makeText(getApplicationContext(), "用户名错误~",
									Toast.LENGTH_SHORT).show();
						} else if (response.contains("帐号的在线人数已达上限。")) {
							Log.i("TAG", response + "帐号的在线人数已达上限。" + password);

						} else if (response.contains("IP未下线")) {
							Log.i("TAG", response + "IP未下线，请2分钟后再试。" + password);
							if (forceLogout()) {
							}

						} else if (response.contains("登录成功。")) {
							Log.i("TAG", response + "登录成功。" + password);
							application.isLogin = true;
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("TAG", "网络错误，response+登录失败！");
						Toast.makeText(getApplicationContext(), "网络错误，登录失败",
								Toast.LENGTH_SHORT).show();
					}

				}) {
			protected Map<String, String> getParams() {
				// 在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username);
				map.put("password", password);
				map.put("is_pad", "1");
				return map;
			}
		};
		queue.add(stringRequest);
	}

	public boolean forceLogout() {
		application.isLogin = true;
		String url = "http://" + host + "/cgi-bin/do_logout?action=logout";
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.i("TAG", response + "注销成功！" + password);
						if ("连接已断开".equals(response)) {
							Log.i("TAG", response + "注销成功！" + password);
							application.isLogin = false;
						} else if ("您不在线上".equals(response)) {
							Log.i("TAG", response + "您不在线上！" + password);
						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("TAG", "网络错误，response+注销失败！");
					}

				}) {
		};
		queue.add(stringRequest);
		return !application.isLogin;
	}
}
