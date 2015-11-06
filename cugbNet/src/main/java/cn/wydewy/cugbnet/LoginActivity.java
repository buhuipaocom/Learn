package cn.wydewy.cugbnet;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.wydewy.cugbnet.R.id;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

@SuppressLint("NewApi")
public class LoginActivity extends Activity implements OnClickListener,
        OnCheckedChangeListener {


    private static final int LOGOUTV6_OK = 10;
    private EditText hostEditText;
    private EditText hostV6EditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button loginV6Button;
    private TextView resultTextView;
    private CheckBox rememberCheckBox;
    private CheckBox autoLoginCheckBox;

    private LinearLayout content;

    private RequestQueue queue;

    private LoginHandler handler;
    protected String username;
    protected String password;
    private static final int LOGIN_OK = 0;
    private static final int PASSWORD_ERROR = 1;
    private static final int USERNAME_ERROR = 2;
    private static final int NETWORK_ERROR = 3;
    public static final int LOGOUT_OK = 4;
    protected static final int OUT_OF_LINE = 5;
    private static final int LOGIN_V6_OK = 6;

    private CugbNetApplication application;
    private String host;
    // private ServiceConnection conn;
    private boolean flag;
    private ReconnectReceiver reconnectReceiver;
    protected int h;
    protected int m;
    protected int s;
    protected float liuliang;
    protected int time;
    private boolean isUpdating;

    public static final String RECONNECT_ACTION = "cn.wydewy.cugbnet.action.reconnect";
    public static final String UPDATE_INFO_ACTION = "cn.wydewy.cugbnet.action.updateInfo";
    public static final int UPDATE_INFO = 7;
    public static final int UPDATE_WAIT = 8;
    protected static final int RELOGIN =9;
    private boolean isLoginV6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("宽带认证客户端（未连接）");
        queue = Volley.newRequestQueue(getApplicationContext());
        application = (CugbNetApplication) getApplication();
        handler = new LoginHandler();
        isUpdating = false;
        initView();
        initEvent();

    }

    private void initEvent() {
        // TODO Auto-generated method stub
        loginButton.setOnClickListener(this);
        loginV6Button.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECONNECT_ACTION);
        intentFilter.addAction(UPDATE_INFO_ACTION);
        reconnectReceiver = new ReconnectReceiver();
        registerReceiver(reconnectReceiver, intentFilter);
    }

    private void initView() {
        // TODO Auto-generated method stub
        hostEditText = (EditText) findViewById(R.id.hostEditText);
        hostV6EditText = (EditText) findViewById(R.id.hostV6EditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        content = (LinearLayout) findViewById(R.id.content);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginV6Button = (Button) findViewById(R.id.loginV6Button);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        rememberCheckBox = (CheckBox) findViewById(R.id.rem);
        autoLoginCheckBox = (CheckBox) findViewById(R.id.auto);
        autoLoginCheckBox.setOnCheckedChangeListener(this);
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        if (preferences.getBoolean("isRemember", false)) {
            rememberCheckBox.setChecked(true);
            usernameEditText.setText(preferences.getString("username", ""));
            passwordEditText.setText(preferences.getString("password", ""));
        }
    }

    private void startService() {
        Intent intent = new Intent(this, ReConnectService.class);
        Log.i("TAG", "startService()");
        startService(intent);
    }

    private void stopService() {
        if (flag == true) {
            Log.i("TAG", "stopService() flag");
            stopService(new Intent(this, ReConnectService.class));
            flag = false;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            virtualUpdate();
            reConnect();
            doLogin();
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            application.isLogin = true;
            updateInfo();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // unBindService();
        if (reconnectReceiver != null) {
            unregisterReceiver(reconnectReceiver);
        }
    }

    private void reConnect() {
        // TODO Auto-generated method stub
        host = hostEditText.getText().toString();
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        String url = "http://" + host + "/cgi-bin/do_login";
        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        if ("mode_error".equals(response)) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "模式错误！",
                                    Toast.LENGTH_SHORT).show();
                        } else if (response.contains("密码错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "密码错误~",
                                    Toast.LENGTH_SHORT).show();
                            handler.sendMessage(handler
                                    .obtainMessage(PASSWORD_ERROR));
                        } else if (response.contains("用户名错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "用户名错误~",
                                    Toast.LENGTH_SHORT).show();
                            handler.sendMessage(handler
                                    .obtainMessage(USERNAME_ERROR));
                        } else {
                            Log.i("TAG", response + "登录成功！" + password);
                            doLogin();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误，response+登录失败！");
                Toast.makeText(getApplicationContext(), "网络错误，登录失败",
                        Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler
                        .obtainMessage(NETWORK_ERROR));
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

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.loginV6Button:
                goTologinipV6(hostV6EditText.getText().toString());
                break;
            case R.id.loginButton:
                host = hostEditText.getText().toString();
                goTologin();
                break;
            default:
                break;
        }
    }

    private void goTologinipV6(String host) {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
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

        if (!isLoginV6) {
            loginV6();
        } else {
            forceLogoutV6();
        }
    }

    private void forceLogoutV6() {
        String url = "http://" + hostV6EditText.getText().toString() + "/cgi-bin/force_logout";
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response + "注销成功！" + password);
                        if ("连接已断开".equals(response)) {
                            Log.i("TAG", response + "注销成功！" + password);
                            application.isLogin = false;
                            handler.sendMessage(handler
                                    .obtainMessage(LOGOUTV6_OK));
                        } else if ("您不在线上".equals(response)) {
                            Log.i("TAG", response + "您不在线上！" + password);
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误，response+注销失败！");
                Toast.makeText(getApplicationContext(), "网络错误，注销失败",
                        Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler
                        .obtainMessage(NETWORK_ERROR));
            }

        }) {
        };
        queue.add(stringRequest);
    }

    private void loginV6() {
        String url = "http://" + hostV6EditText.getText().toString() + "/cgi-bin/do_login";
        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        if ("mode_error".equals(response)) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "模式错误！",
                                    Toast.LENGTH_SHORT).show();
                        } else if (response.contains("密码错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "密码错误~",
                                    Toast.LENGTH_SHORT).show();
                        } else if (response.contains("用户名错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "用户名错误~",
                                    Toast.LENGTH_SHORT).show();
                        } else if (response.contains("帐号的在线人数已达上限。")) {
                            Log.i("TAG", response + "帐号的在线人数已达上限。" + password);
                            Toast.makeText(getApplicationContext(),
                                    "帐号的在线人数已达上限。", Toast.LENGTH_SHORT).show();

                        } else if (response.contains("IP未下线")) {
                            Log.i("TAG", response + "IP未下线，请2分钟后再试。" + password);
                            Toast.makeText(getApplicationContext(),
                                    "IP未下线，请2分钟后再试。", Toast.LENGTH_SHORT)
                                    .show();

                        } else if (response.contains("登录成功。")) {
                            Log.i("TAG", response + "登录成功。" + password);
                            Toast.makeText(getApplicationContext(), "登录成功。",
                                    Toast.LENGTH_SHORT).show();
                            application.isLogin = true;
                            handler.sendMessage(handler.obtainMessage(LOGIN_V6_OK));
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误，response+登录失败！");
                Toast.makeText(getApplicationContext(), "网络错误，登录失败",
                        Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler
                        .obtainMessage(NETWORK_ERROR));
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

    public void goTologin() {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
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
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (!application.isLogin && !isLogin) {
            if (rememberCheckBox.isChecked()) {
                application.isRemember = true;
                Editor editor = preferences.edit();
                editor.putBoolean("isRemember", true);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.commit();
            }
            login();
        } else {
            forceLogout();
        }
    }

    public void login() {
        String url = "http://" + host + "/cgi-bin/do_login";
        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        if ("mode_error".equals(response)) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "模式错误！",
                                    Toast.LENGTH_SHORT).show();
                        } else if (response.contains("密码错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "密码错误~",
                                    Toast.LENGTH_SHORT).show();
                            handler.sendMessage(handler
                                    .obtainMessage(PASSWORD_ERROR));
                        } else if (response.contains("用户名错误")) {
                            Log.i("TAG", response + "登录失败！" + password);
                            Toast.makeText(getApplicationContext(), "用户名错误~",
                                    Toast.LENGTH_SHORT).show();
                            handler.sendMessage(handler
                                    .obtainMessage(USERNAME_ERROR));
                        } else if (response.contains("帐号的在线人数已达上限。")) {
                            Log.i("TAG", response + "帐号的在线人数已达上限。" + password);
                            Toast.makeText(getApplicationContext(),
                                    "帐号的在线人数已达上限。", Toast.LENGTH_SHORT).show();

                        } else if (response.contains("IP未下线")) {
                            Log.i("TAG", response + "IP未下线，请2分钟后再试。" + password);
                            Toast.makeText(getApplicationContext(),
                                    "IP未下线，请2分钟后再试。", Toast.LENGTH_SHORT)
                                    .show();
                            if (forceLogout()) {
                                handler.sendMessage(handler
                                        .obtainMessage(RELOGIN));
                            }

                        } else if (response.contains("登录成功。")) {
                            Log.i("TAG", response + "登录成功。" + password);
                            Toast.makeText(getApplicationContext(), "登录成功。",
                                    Toast.LENGTH_SHORT).show();
                            application.isLogin = true;
                            handler.sendMessage(handler.obtainMessage(LOGIN_OK));
                            // 还是只启动一次好
                            startService();
                            isUpdating = false;
                            virtualUpdate();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误，response+登录失败！");
                Toast.makeText(getApplicationContext(), "网络错误，登录失败",
                        Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler
                        .obtainMessage(NETWORK_ERROR));
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
                            handler.sendMessage(handler
                                    .obtainMessage(LOGOUT_OK));
                        } else if ("您不在线上".equals(response)) {
                            Log.i("TAG", response + "您不在线上！" + password);
                            handler.sendMessage(handler
                                    .obtainMessage(OUT_OF_LINE));
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误，response+注销失败！");
                Toast.makeText(getApplicationContext(), "网络错误，注销失败",
                        Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler
                        .obtainMessage(NETWORK_ERROR));
            }

        }) {
        };
        queue.add(stringRequest);
        return !application.isLogin;
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
                case LOGIN_V6_OK:
                    doLoginV6();
                    break;
                case PASSWORD_ERROR:
                    doPasswordError();
                    break;
                case USERNAME_ERROR:
                    doUsernameError();
                    break;
                case NETWORK_ERROR:
                    doNetworkError();
                    break;
                case LOGOUT_OK:
                    doLogoutOk();
                    break;
                case LOGOUTV6_OK:
                    doLogoutV6Ok();
                    break;
                case OUT_OF_LINE:
                    doOutOfLine();
                    break;
                case UPDATE_INFO:
                    resultTextView.setText("剩余流量：" + liuliang
                            + "kb\r\n剩余时长：不限制\r\n在线时长：" + h + "小时" + m + "分钟" + s
                            + "秒");
                    break;
                case UPDATE_WAIT:
                    resultTextView.setText("正在加载...");
                    break;
                case RELOGIN:
                    login();
                    break;
            }
        }

    }

    private void doLogoutV6Ok() {
        isLoginV6 = false;
        loginV6Button.setText("登录ipV6");
        loginV6Button.setTextColor(Color.BLUE);
        hostV6EditText.setEnabled(true);
    }

    private void doLoginV6() {
        isLoginV6 = true;
        loginV6Button.setText("注销");
        loginV6Button.setTextColor(Color.RED);
        hostV6EditText.setEnabled(false);
        resultTextView.setText("ipv6已经登录");
        setTitle("宽带认证客户端（网关ipv6-已连接）");
    }

    private void doLogin() {
        // TODO Auto-generated method stub
        application.isLogin = true;
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("host", host);
        editor.putString("username", username);
        editor.putString("password", password);
        ;
        editor.commit();
        loginButton.setText("注销");
        loginButton.setTextColor(Color.RED);
        setTitle("宽带认证客户端（网关-已连接）");

        setContentClickable(false);

        save();

    }

    private void virtualUpdate() {
        // TODO Auto-generated method stub
        if (!isUpdating) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    handler.postDelayed(this, 1000);// 设置延迟时间，此处是1秒
                    // 需要执行的代码
                    if (application.isLogin) {
                        time = 3600 * h + 60 * m + s;
                        if (time == 0) {
                            handler.sendMessage(handler
                                    .obtainMessage(UPDATE_WAIT));
                        } else {
                            time = time + 1;
                            h = time / 3600;
                            m = (time % 3600) / 60;
                            s = (time % 3600) % 60;
                            handler.sendMessage(handler
                                    .obtainMessage(UPDATE_INFO));
                        }

                    } else {
                        handler.sendMessage(handler.obtainMessage(LOGOUT_OK));
                    }
                }
            });
            isUpdating = true;
        }

    }

    private void save() {
        String url = "http://www.wydewy.cn/xyw/save.php";
        // TODO Auto-generated method stub
        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response + "保存成功！" + password);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", error.toString() + "保存失败！" + password);
            }

        }) {
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };
        queue.add(stringRequest);
    }

    public void doOutOfLine() {
        // TODO Auto-generated method stub
        doLogoutOk();
    }

    public void doLogoutOk() {
        // TODO Auto-generated method stub
        resultTextView.setText("准备就绪");
        loginButton.setText("登录");
        loginButton.setTextColor(Color.GREEN);
        application.isLogin = false;
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
        setTitle("宽带认证客户端（网关-未连接）");
        setContentClickable(true);
        stopService();
    }

    private void setContentClickable(boolean b) {
        // TODO Auto-generated method stub
        hostEditText.setEnabled(b);
        usernameEditText.setEnabled(b);
        passwordEditText.setEnabled(b);
        rememberCheckBox.setClickable(b);
    }

    public void doNetworkError() {
        // TODO Auto-generated method stub
        application.isLogin = false;
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
        resultTextView.setText("请先连接cugb");
        setContentClickable(true);
    }

    public void doPasswordError() {
        // TODO Auto-generated method stub
        application.isLogin = false;
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
        resultTextView.setText("密码错误");
        setContentClickable(true);
    }

    private void doUsernameError() {
        // TODO Auto-generated method stub
        application.isLogin = false;
        SharedPreferences preferences = getSharedPreferences("logInfo",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
        setContentClickable(true);
        resultTextView.setText("用户名错误");
    }

    private class ReconnectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (RECONNECT_ACTION.equals(action)) {
                SharedPreferences preferences = getSharedPreferences("logInfo",
                        Context.MODE_PRIVATE);
                if (preferences.getBoolean("isLogin", false)) {
                    reConnect();
                }

            }
            if (UPDATE_INFO_ACTION.equals(action)) {
                updateInfo();
            }
        }

    }

    private void updateInfo() {
        // TODO Auto-generated method stub
        String url = "http://" + host + "/cgi-bin/keeplive";
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        if (!response.contains("error")) {
                            String[] dates = response.split(",");
                            time = Integer.parseInt(dates[0]);
                            h = time / 3600;
                            m = (time % 3600) / 60;
                            s = (time % 3600) % 60;
                            // String liuliang = dates[3];
                            liuliang = Float.parseFloat(dates[3]) / 1024;
                            resultTextView.setText("剩余流量：" + liuliang
                                    + "kb\r\n剩余时长：不限制\r\n在线时长：" + h + "小时" + m
                                    + "分钟" + s + "秒");
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("TAG", "网络错误,error" + error);
            }

        }) {
        };
        queue.add(stringRequest);
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean b) {
        // TODO Auto-generated method stub
        switch (button.getId()) {
            case R.id.auto:
                if (b) {
                    host = hostEditText.getText().toString();
                    username = usernameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    login();
                }
                break;

            default:
                break;
        }
    }

}
