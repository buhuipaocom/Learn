package cn.wydewy.filedinhand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PickerActivity extends Activity {

	private WebView webView;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pivker);
		init();
	}

	private void init() {
		String url = "http://lbs.amap.com/console/show/picker";

		webView = (WebView) findViewById(R.id.pickerWV);
		webView.loadUrl(url);
		// 覆盖webView默认用本地浏览器加载
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		// 获得焦点
		// webView.requestFocus();

		// 开启javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// 优先使用缓存加载
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				// newnewProgress 为1-100的整数
				if (newProgress == 100) {
					// 加载完成
					closeDialog();
				} else {
					// 加载中
					openDialog(newProgress);
				}
			}

			private void closeDialog() {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
					dialog = null;
				}
				dialog = null;
			}

			private void openDialog(int newProgress) {
				if (dialog == null) {
					dialog = new ProgressDialog(PickerActivity.this);
					dialog.setTitle("正在加载");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();
				} else {
					dialog.setProgress(newProgress);
				}

			}
		});

		// webView.loadUrl("file:///android_asset/wydewy.html");

		// Uri uri = Uri.parse(name);
		// Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		// startActivity(intent);
	}

	// 改写物理按键--返回按键的逻辑
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (webView.canGoBack()) {
				webView.goBack();
				return true;
			} else {
				System.exit(0);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
