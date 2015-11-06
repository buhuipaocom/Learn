package cn.wydewy.filedinhand.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.wydewy.filedinhand.R;

public class Page2ThreeFragment extends Fragment implements OnClickListener {

	private RelativeLayout layout;
	private WebView webView;
	private ProgressDialog dialog;


	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.page2_fragment3,
				container, false);
		initView();
		initEvevt();
		return layout;
	}

	private void initEvevt() {
		// TODO Auto-generated method stub
	}

	private void initView() {
		// TODO Auto-generated method stub
		webView = (WebView) layout.findViewById(R.id.naviWV);

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
					dialog = new ProgressDialog(getActivity());
					dialog.setTitle("正在加载");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();
				} else {
					dialog.setProgress(newProgress);
				}

			}
		});
		
		
		webView.setVisibility(View.VISIBLE);
		String url = "http://lbs.amap.com/others/demo_list/js_demo.html";
		webView.loadUrl(url);
		// 覆盖webView默认用本地浏览器加载
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.naviTV:
			
			break;
		}
	}
}
