package cn.wydewy.filedinhand.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.wydewy.filedinhand.FIHApplication;
import cn.wydewy.filedinhand.R;
import cn.wydewy.filedinhand.SignActivity;
import cn.wydewy.filedinhand.util.Constant;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Page4OneFragment extends Fragment implements OnClickListener {

	private ImageView faceIV;
	
	private View synch;
	private View logout;
	private TextView logoffTV;
	
	private TextView nameTV;

	private LinearLayout layout;
	private boolean isLogin;
	private FIHApplication application;
	private SharedPreferences preferences;

	// 请求
	private RequestQueue queue;
	private Handler handler;
	private ProgressDialog dialog;

	// 数据
	private String password;
	private String username;
	private String accessToken;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = FIHApplication.getInstance();
		isLogin = application.isLogin;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.page4_fragment1,
				container, false);
		initDate();
		initView();
		initEvent();
		return layout;
	}

	private void initDate() {
		// TODO Auto-generated method stub
		queue = Volley.newRequestQueue(getActivity());
		handler = new Handler() {
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case Constant.LOGOUT_OK:
					logoffTV.setText("登录");
					if (dialog != null & dialog.isShowing()) {
						dialog.dismiss();
					}
					nameTV.setText("我还没有登录呢~");
					isLogin = false;
					application.isLogin = isLogin;
					faceIV.setImageDrawable(getResources().getDrawable(
							R.drawable.sign_face_off));
					preferences = getActivity().getSharedPreferences(
							Constant.LOGINFO, Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("isLogin", false);
					editor.putString("accessToken", "");
					editor.putString("username", "");
					editor.putString("password", "");
					editor.commit();
					break;
				case Constant.LOGOUT_FAILED:
					Toast.makeText(getActivity(), "登出失败", Toast.LENGTH_SHORT)
							.show();
					break;
				case Constant.USER_INFO_OK:
					break;
				case Constant.USER_INFO_FAILED:
					break;
				case Constant.SERVER_ERROR:
					break;
				}
			}
		};
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		preferences = getActivity().getSharedPreferences(Constant.LOGINFO,
				Context.MODE_PRIVATE);
		if (preferences != null) {
			username = preferences.getString("username", "");
			accessToken = preferences.getString("accessToken", "");
			password = preferences.getString("password", "");
			if (accessToken != null && !"".equals(accessToken)
					&& username != null && !"".equals(username)) {
				nameTV.setText(username);
				isLogin = true;
				application.isLogin = isLogin;
				faceIV.setImageDrawable(getResources().getDrawable(
						R.drawable.sign_face_on));
				logoffTV.setText("注销");//改变按钮文字
			} else {
				nameTV.setText("我还没有登录呢~");
				isLogin = false;
				application.isLogin = isLogin;
				faceIV.setImageDrawable(getResources().getDrawable(
						R.drawable.sign_face_off));
				logoffTV.setText("登录");
			}

		}

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		faceIV.setOnClickListener(this);
		synch.setOnClickListener(this);
		logout.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		faceIV = (ImageView) layout.findViewById(R.id.faceIV);
		synch = layout.findViewById(R.id.synch);
		logout = layout.findViewById(R.id.logout);
		nameTV = (TextView) layout.findViewById(R.id.nameTV);
		logoffTV = (TextView) layout.findViewById(R.id.logoffTV);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.faceIV:
			if (isLogin) {
				goUserInfo();
			} else {
				goToLogin();
			}
			break;
		case R.id.synch:
			synchronize();
			break;
		case R.id.logout:
			if(isLogin){
				goLogoff();
			}else{
				goToLogin();
			}
			
			break;
		}
	}

	/**
	 * 进入用户信息展示界面
	 */
	private void goUserInfo() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "goUserInfo", Toast.LENGTH_SHORT).show();
		String url = Constant.HOST + Constant.USER_INFO + accessToken;
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + response);
						handler.sendMessage(handler
								.obtainMessage(Constant.USER_INFO_OK));
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("TAG", "error" + error.toString());
						// TODO Auto-generated method stub
						handler.sendMessage(handler
								.obtainMessage(Constant.USER_INFO_FAILED));
					}

				});
		queue.add(stringRequest);
	}

	/**
	 * 进入登出
	 */
	private void goLogoff() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog
				.show(getActivity(), "注销", "正在注销...", true, true);
		String url = Constant.HOST + Constant.LOGOUT + accessToken;
		Log.i("TAG", accessToken);
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("TAG", "response" + response);
						handler.sendMessage(handler
								.obtainMessage(Constant.LOGOUT_OK));
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("TAG", "error" + error.toString());
						// TODO Auto-generated method stub
						handler.sendMessage(handler
								.obtainMessage(Constant.LOGOUT_FAILED));
					}

				});
		queue.add(stringRequest);
	}

	/**
	 * 同步数据
	 */
	private void synchronize() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "synchronize", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 进入登录界面
	 * 
	 */
	private void goToLogin() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), SignActivity.class);
		getActivity().startActivity(intent);
	}
}
