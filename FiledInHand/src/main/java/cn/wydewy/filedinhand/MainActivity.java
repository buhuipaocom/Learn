package cn.wydewy.filedinhand;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.wydewy.filedinhand.fragment.Page1OneFragment;
import cn.wydewy.filedinhand.fragment.Page2OneFragment;
import cn.wydewy.filedinhand.fragment.Page3OneFragment;
import cn.wydewy.filedinhand.fragment.Page3TwoFragment;
import cn.wydewy.filedinhand.fragment.Page4OneFragment;
import cn.wydewy.filedinhand.PictureListActivity;
import cn.wydewy.filedinhand.util.Constant;
import cn.wydewy.vnique.view.ChangeColorIconWithText;
import cn.wydewy.vnique.view.VniqueViewPager;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {

	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	// 第一页
	private VniqueViewPager page1ViewPager;
	private List<Fragment> page1Tabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter page1Adapter;
	private Page1OneFragment page1OneFragment;
	private List<ChangeColorIconWithText> page1TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
	private View page1TitleBar;
	private View page1TopBar;
	// 第二页
	private VniqueViewPager page2ViewPager;
	private List<Fragment> page2Tabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter page2Adapter;
	private List<ChangeColorIconWithText> page2TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
	private View page2TitleBar;
	private View page2TopBar;
	// 第三页
	private VniqueViewPager page3ViewPager;
	private List<Fragment> page3Tabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter page3Adapter;
	private Page3OneFragment page3OneFragment;
	private Page3TwoFragment page3TwoFragment;
	private List<ChangeColorIconWithText> page3TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
	private View page3TitleBar;
	private View page3TopBar;

	// 第四页
	private VniqueViewPager page4ViewPager;
	private List<Fragment> page4Tabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter page4Adapter;
	private List<ChangeColorIconWithText> page4TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
	private View page4TitleBar;
	private View page4TopBar;

	// 共四页
	private LinearLayout page1;
	private LinearLayout page2;
	private LinearLayout page3;
	private LinearLayout page4;

	private int currentPage = 1;

	private LinearLayout mPage1TabLineIv;
	private LinearLayout mPage2TabLineIv;
	private LinearLayout mPage3TabLineIv;
	private LinearLayout mPage4TabLineIv;

	private int screenWidth;

	// 全局相关
	private FIHApplication application;
	private boolean isLogin;
	private SharedPreferences preferences;
private Bundle savedInstanceState;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		setContentView(R.layout.activity_main);
		setOverflowButtonAlways();
		getActionBar().setDisplayShowHomeEnabled(false);// 不显示icon
		initDates();
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		page1ViewPager.setOnPageChangeListener(this);
		page2ViewPager.setOnPageChangeListener(this);
		page3ViewPager.setOnPageChangeListener(this);
		page4ViewPager.setOnPageChangeListener(this);

	}

	/**
	 * 初始化数据，包括fragment的内容
	 */
	private void initDates() {
		// 全局数据
		application = (FIHApplication) getApplication();
		isLogin = application.isLogin;
		preferences = getSharedPreferences("logInfo", Context.MODE_PRIVATE);

		// 第一页
		page1OneFragment = new Page1OneFragment();
		page1Tabs.add(page1OneFragment);

		// 第二页
		Page2OneFragment page2OneFragment = new Page2OneFragment();
//		Page2TwoFragment page2TwoFragment = new Page2TwoFragment();
//		Page2ThreeFragment page2ThreeFragment = new Page2ThreeFragment();
		page2Tabs.add(page2OneFragment);
//		page2Tabs.add(page2TwoFragment);
//		page2Tabs.add(page2ThreeFragment);

		// 第三页
		page3OneFragment = new Page3OneFragment();
		page3TwoFragment = new Page3TwoFragment();
		page3Tabs.add(page3OneFragment);
		page3Tabs.add(page3TwoFragment);

		// 第四
		Page4OneFragment page4OneFragment = new Page4OneFragment();
		page4Tabs.add(page4OneFragment);
		/**
		 * 设置viewPager的适配器,不同点position设置不同的fragment
		 */
		page1Adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return page1Tabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return page1Tabs.get(position);
			}
		};
		/**
		 * 设置viewPager的适配器,不同点position设置不同的fragment
		 */
		page2Adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return page2Tabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return page2Tabs.get(position);
			}
		};
		/**
		 * 设置viewPager的适配器,不同点position设置不同的fragment
		 */
		page3Adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return page3Tabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return page3Tabs.get(position);
			}
		};
		/**
		 * 设置viewPager的适配器,不同点position设置不同的fragment
		 */
		page4Adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return page4Tabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return page4Tabs.get(position);
			}
		};

	}


	/**
	 * 初始化视图
	 */
	private void initView() {

		mPage1TabLineIv = (LinearLayout) findViewById(R.id.mPage1TabLineIv);
		mPage2TabLineIv = (LinearLayout) findViewById(R.id.mPage2TabLineIv);
		mPage3TabLineIv = (LinearLayout) findViewById(R.id.mPage3TabLineIv);
		mPage4TabLineIv = (LinearLayout) findViewById(R.id.mPage4TabLineIv);
		initTopTabLineWidth(1, mPage1TabLineIv);// 第一页的下划线
		initTopTabLineWidth(1, mPage2TabLineIv);// 第二页的下划线
		initTopTabLineWidth(2, mPage3TabLineIv);// 第三页的下划线
		initTopTabLineWidth(1, mPage4TabLineIv);// 第四页的下划线

		page1TitleBar = findViewById(R.id.id_page1_title_bar);
		page1TopBar = findViewById(R.id.id_page1_top_bar);
		page2TitleBar = findViewById(R.id.id_page2_title_bar);
		page2TopBar = findViewById(R.id.id_page2_top_bar);
		page3TitleBar = findViewById(R.id.id_page3_title_bar);
		page3TopBar = findViewById(R.id.id_page3_top_bar);
		page4TitleBar = findViewById(R.id.id_page4_title_bar);
		page4TopBar = findViewById(R.id.id_page4_top_bar);

		showTitle(false, 1);
		showTitle(false, 2);
		showTitle(false, 3);
		showTitle(false, 4);

		showTopBar(false, 1);
		showTopBar(false, 2);
		showTopBar(true, 3);
		showTopBar(false, 4);

		// 第一页
		page1ViewPager = (VniqueViewPager) findViewById(R.id.id_page1_viewpager);
		page1ViewPager.setAdapter(page1Adapter);
		// 第二页
		page2ViewPager = (VniqueViewPager) findViewById(R.id.id_page2_viewpager);
		page2ViewPager.setAdapter(page2Adapter);
		// 第三页
		page3ViewPager = (VniqueViewPager) findViewById(R.id.id_page3_viewpager);
		page3ViewPager.setAdapter(page3Adapter);

		// 第四页
		page4ViewPager = (VniqueViewPager) findViewById(R.id.id_page4_viewpager);
		page4ViewPager.setAdapter(page4Adapter);

		// 四页
		page1 = (LinearLayout) findViewById(R.id.page1);
		page2 = (LinearLayout) findViewById(R.id.page2);
		page3 = (LinearLayout) findViewById(R.id.page3);
		page4 = (LinearLayout) findViewById(R.id.page4);

		// 底部
		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
		mTabIndicators.add(four);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		one.setIconAlpha(1.0f);
		// 第一页的top
		ChangeColorIconWithText page1One = (ChangeColorIconWithText) findViewById(R.id.id_page1_one);
		page1TopTabIndicators.add(page1One);
		ChangeColorIconWithText page1Two = (ChangeColorIconWithText) findViewById(R.id.id_page1_two);
		page1TopTabIndicators.add(page1Two);
		ChangeColorIconWithText page1Three = (ChangeColorIconWithText) findViewById(R.id.id_page1_three);
		page1TopTabIndicators.add(page1Three);
		ChangeColorIconWithText page1Four = (ChangeColorIconWithText) findViewById(R.id.id_page1_four);
		page1TopTabIndicators.add(page1Four);
		page1One.setOnClickListener(this);
		page1Two.setOnClickListener(this);
		page1Three.setOnClickListener(this);
		page1Four.setOnClickListener(this);
		page1One.setIconAlpha(1.0f);
		// 第二页的top
		ChangeColorIconWithText page2One = (ChangeColorIconWithText) findViewById(R.id.id_page1_one);
		page2TopTabIndicators.add(page1One);
		ChangeColorIconWithText page2Two = (ChangeColorIconWithText) findViewById(R.id.id_page2_two);
		page2TopTabIndicators.add(page1Two);
		ChangeColorIconWithText page2Three = (ChangeColorIconWithText) findViewById(R.id.id_page2_three);
		page2TopTabIndicators.add(page1Three);
		page2One.setOnClickListener(this);
		page2Two.setOnClickListener(this);
		page2Three.setOnClickListener(this);
		page2One.setIconAlpha(1.0f);
		// 第三页的top
		ChangeColorIconWithText page3One = (ChangeColorIconWithText) findViewById(R.id.id_page3_one);
		page3One.setText("本地");
		page3TopTabIndicators.add(page3One);
		ChangeColorIconWithText page3Two = (ChangeColorIconWithText) findViewById(R.id.id_page3_two);
		page3Two.setText("在线");
		page3TopTabIndicators.add(page3Two);
		page3One.setOnClickListener(this);
		page3Two.setOnClickListener(this);
		page3One.setIconAlpha(1.0f);

	}

	/**
	 * 控制自定义titlebar显示与隐藏
	 * 
	 * @param show
	 * @param page
	 */
	private void showTitle(boolean show, int page) {
		// TODO Auto-generated method stub
		if (!show) {
			switch (page) {
			case 1:
				page1TitleBar.setVisibility(View.GONE);
				break;
			case 2:
				page2TitleBar.setVisibility(View.GONE);
				break;
			case 3:
				page3TitleBar.setVisibility(View.GONE);
				break;
			case 4:
				page4TitleBar.setVisibility(View.GONE);
				break;
			}
		} else {
			switch (page) {
			case 1:
				page1TitleBar.setVisibility(View.VISIBLE);
				break;
			case 2:
				page2TitleBar.setVisibility(View.VISIBLE);
				break;
			case 3:
				page3TitleBar.setVisibility(View.VISIBLE);
				break;
			case 4:
				page4TitleBar.setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	/**
	 * 控制自定义topbar显示与隐藏
	 * 
	 * @param show
	 * @param page
	 */
	private void showTopBar(boolean show, int page) {
		// TODO Auto-generated method stub
		if (!show) {
			switch (page) {
			case 1:
				page1TopBar.setVisibility(View.GONE);
				break;
			case 2:
				page2TopBar.setVisibility(View.GONE);
				break;
			case 3:
				page3TopBar.setVisibility(View.GONE);
				break;
			case 4:
				page4TopBar.setVisibility(View.GONE);
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 传值
		getIntentDates();
		refresh();
	}

	/**
	 * 刷新界面
	 */
	private void refresh() {

	}

	/**
	 * 获得传回的数据
	 */
	private void getIntentDates() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		int page = bundle.getInt("page");
		if (!"".equals(page + "")) {
			currentPage = page;
			switch (currentPage) {
			case 1:
				resetOtherTabs();
				mTabIndicators.get(0).setIconAlpha(1.0f);
				page1.setVisibility(View.VISIBLE);
				break;
			case 2:
				resetOtherTabs();
				mTabIndicators.get(1).setIconAlpha(1.0f);
				page2.setVisibility(View.VISIBLE);
				break;
			case 3:
				resetOtherTabs();
				mTabIndicators.get(2).setIconAlpha(1.0f);
				page3.setVisibility(View.VISIBLE);
				break;
			case 4:
				resetOtherTabs();
				mTabIndicators.get(3).setIconAlpha(1.0f);
				page4.setVisibility(View.VISIBLE);
				break;

			}

		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		clickTab(view);
	}

	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v) {
		switch (v.getId()) {
		// 底部
		case R.id.id_indicator_one:
			resetOtherTabs();
			currentPage = 1;
			application.currentPage = 1;
			mTabIndicators.get(0).setIconAlpha(1.0f);
			page1.setVisibility(View.VISIBLE);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			currentPage = 2;
			application.currentPage = 2;
			mTabIndicators.get(1).setIconAlpha(1.0f);
			page2.setVisibility(View.VISIBLE);
			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			currentPage = 3;
			application.currentPage = 3;
			mTabIndicators.get(2).setIconAlpha(1.0f);
			page3.setVisibility(View.VISIBLE);
			break;
		case R.id.id_indicator_four:
			resetOtherTabs();
			currentPage = 4;
			application.currentPage = 4;
			mTabIndicators.get(3).setIconAlpha(1.0f);
			page4.setVisibility(View.VISIBLE);
			break;

		// 第一页顶部
		case R.id.id_page1_one:
			resetOtherPage1Tabs();
			page1TopTabIndicators.get(0).setIconAlpha(1.0f);
			page1ViewPager.setCurrentItem(0, false);
			setTabLineIvIndex(0, 4, mPage1TabLineIv);
			break;
		case R.id.id_page1_two:
			resetOtherPage1Tabs();
			page1TopTabIndicators.get(1).setIconAlpha(1.0f);
			page1ViewPager.setCurrentItem(1, false);
			setTabLineIvIndex(1, 4, mPage1TabLineIv);
			break;
		case R.id.id_page1_three:
			resetOtherPage1Tabs();
			page1TopTabIndicators.get(2).setIconAlpha(1.0f);
			page1ViewPager.setCurrentItem(2, false);
			setTabLineIvIndex(2, 4, mPage1TabLineIv);
			break;
		case R.id.id_page1_four:
			resetOtherPage1Tabs();
			page1TopTabIndicators.get(3).setIconAlpha(1.0f);
			page1ViewPager.setCurrentItem(3, false);
			setTabLineIvIndex(3, 4, mPage1TabLineIv);
			break;

		// 第二页顶部
		case R.id.id_page2_one:
			resetOtherpage2Tabs();
			page2TopTabIndicators.get(0).setIconAlpha(1.0f);
			page2ViewPager.setCurrentItem(0, false);
			setTabLineIvIndex(0, 3, mPage2TabLineIv);
			break;
		case R.id.id_page2_two:
			resetOtherpage2Tabs();
			page2TopTabIndicators.get(1).setIconAlpha(1.0f);
			page2ViewPager.setCurrentItem(1, false);
			setTabLineIvIndex(1, 3, mPage2TabLineIv);
			break;
		case R.id.id_page2_three:
			resetOtherpage2Tabs();
			page2TopTabIndicators.get(2).setIconAlpha(1.0f);
			page2ViewPager.setCurrentItem(2, false);
			setTabLineIvIndex(2, 3, mPage2TabLineIv);
			break;

		// 第三页顶部
		case R.id.id_page3_one:
			resetOtherpage3Tabs();
			page3TopTabIndicators.get(0).setIconAlpha(1.0f);
			page3ViewPager.setCurrentItem(0, false);
			setTabLineIvIndex(0, 2, mPage3TabLineIv);
			break;
		case R.id.id_page3_two:
			resetOtherpage3Tabs();
			page3TopTabIndicators.get(1).setIconAlpha(1.0f);
			page3ViewPager.setCurrentItem(1, false);
			setTabLineIvIndex(1, 2, mPage3TabLineIv);
			break;

		// 第四页
		}
	}

	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
		page1.setVisibility(View.GONE);
		page2.setVisibility(View.GONE);
		page3.setVisibility(View.GONE);
		page4.setVisibility(View.GONE);
	}

	/**
	 * 重置page1其他的顶部TabIndicator的颜色
	 */
	private void resetOtherPage1Tabs() {
		for (int i = 0; i < page1TopTabIndicators.size(); i++) {
			page1TopTabIndicators.get(i).setIconAlpha(0);
		}
	}

	/**
	 * 重置page2其他的顶部TabIndicator的颜色
	 * 
	 */
	private void resetOtherpage2Tabs() {
		// TODO Auto-generated method stub
		for (int i = 0; i < page2TopTabIndicators.size(); i++) {
			page2TopTabIndicators.get(i).setIconAlpha(0);
		}
	}

	/**
	 * 重置page3其他的顶部TabIndicator的颜色
	 */
	private void resetOtherpage3Tabs() {
		// TODO Auto-generated method stub
		for (int i = 0; i < page3TopTabIndicators.size(); i++) {
			page3TopTabIndicators.get(i).setIconAlpha(0);
		}
	}

	/**
	 * 设置滑动条的宽度为屏幕的1/pageNum(根据Tab的个数而定)
	 * 
	 * @param mTabLineIv
	 */
	private void initTopTabLineWidth(int pageNum, View mTabLineIv) {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / pageNum;
		mTabLineIv.setLayoutParams(lp);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// Log.e("TAG", "position = " + position + " ,positionOffset =  "
		// + positionOffset);

		if (positionOffset > 0) {
			ChangeColorIconWithText left = null;
			ChangeColorIconWithText right = null;
			switch (currentPage) {
			case 1:
				left = page1TopTabIndicators.get(position);
				right = page1TopTabIndicators.get(position + 1);
				resetOtherPage1Tabs();
				setTabLineIvParamss(position, positionOffset, 1,
						page1ViewPager.getCurrentItem(), mPage1TabLineIv);
				break;
			case 2:
				left = page2TopTabIndicators.get(position);
				right = page2TopTabIndicators.get(position + 1);
				setTabLineIvParamss(position, positionOffset, 1,
						page2ViewPager.getCurrentItem(), mPage2TabLineIv);
				break;
			case 3:
				left = page3TopTabIndicators.get(position);
				right = page3TopTabIndicators.get(position + 1);
				setTabLineIvParamss(position, positionOffset, 2,
						page3ViewPager.getCurrentItem(), mPage3TabLineIv);
				break;
			case 4:
				left = page4TopTabIndicators.get(position);
				right = page4TopTabIndicators.get(position + 1);
				setTabLineIvParamss(position, positionOffset, 1,
						page4ViewPager.getCurrentItem(), mPage4TabLineIv);
				break;

			}
			if (left != null && right != null) {
				left.setIconAlpha(1 - positionOffset);
				right.setIconAlpha(positionOffset);
			}

		}

	}

	/**
	 * 动态设置下划线的位置
	 * 
	 * @param position
	 * @param positionOffset
	 * @param pageNum
	 * @param mTabLineIv
	 * @param currentIndex
	 */
	public void setTabLineIvParamss(int position, float positionOffset,
			int pageNum, int currentIndex, View mTabLineIv) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		if (currentIndex == position)// 左滑
		{
			lp.leftMargin = (int) (positionOffset
					* (screenWidth * 1.0 / pageNum) + currentIndex
					* (screenWidth / pageNum));

		} else if (currentIndex != position) // 右滑
		{
			lp.leftMargin = (int) (-(1 - positionOffset)
					* (screenWidth * 1.0 / pageNum) + currentIndex
					* (screenWidth / pageNum));
		}
		mTabLineIv.setLayoutParams(lp);
	}

	/**
	 * 设置TabLine的当前位置
	 * 
	 * @param index
	 *            位置
	 * @param pageNum
	 *            页数
	 * @param mTabLineIv
	 */
	public void setTabLineIvIndex(int index, int pageNum, View mTabLineIv) {

		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.leftMargin = index * (screenWidth / pageNum);
		mTabLineIv.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	// Activity相关
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 总是在上面显示menu
	 */
	private void setOverflowButtonAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_introduction:
			Context mContext = this;
			AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

			alert.setTitle(getResources().getString(R.string.introduction));

			String target = getString(R.string.introductionText);

			String back = getString(R.string.back);
			String wholepath = "";

			wholepath = "file:///android_asset/" + target;
			WebView wv = new WebView(mContext);
			wv.loadUrl(wholepath);
			alert.setView(wv);
			alert.setPositiveButton(back,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Toast.makeText(getApplicationContext(),
							// "Success", Toast.LENGTH_SHORT).show();
						}
					});
			alert.show();
			break;
		case R.id.action_library://我的图库
			Intent intent = new Intent(this, PictureListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("name", "ALL");
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.action_changeMode:
			if(application.compassMode==0){
				application.compassMode = 1;
				page1OneFragment.changeCompassMode(1);
			}else if(application.compassMode==1){
				application.compassMode = 0;
				page1OneFragment.changeCompassMode(0);
			}

			break;
		case R.id.action_about_us:
			AlertDialog.Builder alert1 = new AlertDialog.Builder(this);

			alert1.setTitle(getResources().getString(R.string.about));

			String target1 = getString(R.string.aboutText);

			String back1 = getString(R.string.back);
			String wholepath1 = "";

			wholepath1 = "file:///android_asset/" + target1;
			WebView wv1 = new WebView(this);
			wv1.loadUrl(wholepath1);
			alert1.setView(wv1);
			alert1.setPositiveButton(back1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Toast.makeText(getApplicationContext(),
							// "Success", Toast.LENGTH_SHORT).show();
						}
					});
			alert1.show();
			break;
		case R.id.action_feedback:

			break;

		case R.id.action_settings:

			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {

				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.quit),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
