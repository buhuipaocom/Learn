package cn.wydewy.yuejuba;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wydewy.vnique.util.DensityUtil;
import cn.wydewy.vnique.view.ChangeColorIconWithText;
import cn.wydewy.vnique.view.VniqueViewPager;
import cn.wydewy.yuejuba.fragment.Page1Four2Fragment;
import cn.wydewy.yuejuba.fragment.Page1FourFragment;
import cn.wydewy.yuejuba.fragment.Page1One2Fragment;
import cn.wydewy.yuejuba.fragment.Page1OneFragment;
import cn.wydewy.yuejuba.fragment.Page1Three2Fragment;
import cn.wydewy.yuejuba.fragment.Page1ThreeFragment;
import cn.wydewy.yuejuba.fragment.Page1Two2Fragment;
import cn.wydewy.yuejuba.fragment.Page1TwoFragment;
import cn.wydewy.yuejuba.fragment.Page2OneFragment;
import cn.wydewy.yuejuba.fragment.Page2ThreeFragment;
import cn.wydewy.yuejuba.fragment.Page2TwoFragment;
import cn.wydewy.yuejuba.fragment.Page3OneFragment;
import cn.wydewy.yuejuba.fragment.Page3TwoFragment;
import cn.wydewy.yuejuba.util.Constant;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener,
        OnPageChangeListener {
    private static final int PAGE1_PAGE_NUM = 8;
    private static final int PAGE2_PAGE_NUM = 2;
    private static final int PAGE3_PAGE_NUM = 1;
    private static final int PAGE4_PAGE_NUM = 6;
    private static final int NO_EDGE = 0;
    private static final int RIGHT_EDGE = 1;
    private static final int LEFT_EDGE = 2;
    private double EDGE;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

    // 第一页
    private VniqueViewPager page1ViewPager;
    private View page1TopAll;
    private List<Fragment> page1Tabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter page1Adapter;
    private List<ChangeColorIconWithText> page1TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
    private View page1Top1;
    private View page1Top2;
    private ImageView moreRight;
    private ImageView moreLeft;
    // 第二页
    private VniqueViewPager page2ViewPager;
    private List<Fragment> page2Tabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter page2Adapter;
    private List<ChangeColorIconWithText> page2TopTabIndicators = new ArrayList<ChangeColorIconWithText>();
    // 第三页
    private VniqueViewPager page3ViewPager;
    private List<Fragment> page3Tabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter page3Adapter;
    private List<ChangeColorIconWithText> page3TopTabIndicators = new ArrayList<ChangeColorIconWithText>();

    // 第四页
    private ImageView faceIV;
    private TextView nameTV;

    // 共四页
    private LinearLayout page1;
    private LinearLayout page2;
    private LinearLayout page3;
    private LinearLayout page4;

    private int currentPage = 1;

    private LinearLayout mPage1TabLineIv;
    private LinearLayout mPage2TabLineIv;
    private LinearLayout mPage3TabLineIv;


    private int screenWidth;

    // 全局相关
    private YueJuBaApplication application;
    private boolean isLogin;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDates();
        initView();
        initEvent();
    }

    private void initEvent() {
        // TODO Auto-generated method stub
        page1ViewPager.setOnPageChangeListener(this);
        page2ViewPager.setOnPageChangeListener(this);
        page3ViewPager.setOnPageChangeListener(this);

        faceIV.setOnClickListener(this);
    }

    /**
     * 初始化数据，包括fragment的内容
     */
    private void initDates() {

        EDGE = DensityUtil.dip2px(this, 40);

        // 全局数据
        application = (YueJuBaApplication) getApplication();
        isLogin = application.isLogin;
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        // 第一页
        Page1OneFragment page1OneFragment = new Page1OneFragment();
        Page1TwoFragment page1TwoFragment = new Page1TwoFragment();
        Page1ThreeFragment page1ThreeFragment = new Page1ThreeFragment();
        Page1FourFragment page1FourFragment = new Page1FourFragment();

        page1Tabs.add(page1OneFragment);
        page1Tabs.add(page1TwoFragment);
        page1Tabs.add(page1ThreeFragment);
        page1Tabs.add(page1FourFragment);

        Page1One2Fragment page1One2Fragment = new Page1One2Fragment();
        Page1Two2Fragment page1Two2Fragment = new Page1Two2Fragment();
        Page1Three2Fragment page1Three2Fragment = new Page1Three2Fragment();
        Page1Four2Fragment page1Four2Fragment = new Page1Four2Fragment();

        page1Tabs.add(page1One2Fragment);
        page1Tabs.add(page1Two2Fragment);
        page1Tabs.add(page1Three2Fragment);
        page1Tabs.add(page1Four2Fragment);


        // 第二页
        Page2OneFragment page2OneFragment = new Page2OneFragment();
        Page2TwoFragment page2TwoFragment = new Page2TwoFragment();

        page2Tabs.add(page2OneFragment);
        page2Tabs.add(page2TwoFragment);

        // 第三页
        Page3OneFragment page3OneFragment = new Page3OneFragment();
        page3Tabs.add(page3OneFragment);
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

    }

    /**
     * 初始化视图
     */
    private void initView() {

        mPage1TabLineIv = (LinearLayout) findViewById(R.id.mPage1TabLineIv);
        mPage2TabLineIv = (LinearLayout) findViewById(R.id.mPage2TabLineIv);
        mPage2TabLineIv.setVisibility(View.GONE);
//        mPage3TabLineIv = (LinearLayout) findViewById(R.id.mPage3TabLineIv);


        page1TopAll = findViewById(R.id.page1_top_all);
        page1Top1 = findViewById(R.id.page1_top_1);
        page1Top2 = findViewById(R.id.page1_top_2);
        moreRight = (ImageView) findViewById(R.id.more_right);
        moreLeft = (ImageView) findViewById(R.id.more_left);
        moreRight.setOnClickListener(this);
        moreLeft.setOnClickListener(this);
        page1Top1.setVisibility(View.VISIBLE);
        page1Top2.setVisibility(View.GONE);


        initTopTabLineWidth(PAGE1_PAGE_NUM, mPage1TabLineIv, true, RIGHT_EDGE);// 第一页的下划线
        initTopTabLineWidth(PAGE2_PAGE_NUM, mPage2TabLineIv, false, NO_EDGE);// 第二页的下划线
//        initTopTabLineWidth(PAGE3_PAGE_NUM, mPage3TabLineIv, false, NO_EDGE);// 第三页的下划线
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
        faceIV = (ImageView) findViewById(R.id.faceImg);
        nameTV = (TextView) findViewById(R.id.nameTV);

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

        ChangeColorIconWithText page1One2 = (ChangeColorIconWithText) findViewById(R.id.id_page1_one2);
        page1TopTabIndicators.add(page1One2);
        ChangeColorIconWithText page1Two2 = (ChangeColorIconWithText) findViewById(R.id.id_page1_two2);
        page1TopTabIndicators.add(page1Two2);
        ChangeColorIconWithText page1Three2 = (ChangeColorIconWithText) findViewById(R.id.id_page1_three2);
        page1TopTabIndicators.add(page1Three2);
        ChangeColorIconWithText page1Four2 = (ChangeColorIconWithText) findViewById(R.id.id_page1_four2);
        page1TopTabIndicators.add(page1Four2);

        page1One2.setOnClickListener(this);
        page1Two2.setOnClickListener(this);
        page1Three2.setOnClickListener(this);
        page1Four2.setOnClickListener(this);

//        ViewUtil.measureView(moreLeft);
//       EDGE = moreLeft.getMeasuredWidth();

        page1One.setIconAlpha(1.0f);
        // 第二页的top
        ChangeColorIconWithText page2One = (ChangeColorIconWithText) findViewById(R.id.id_page2_one);
        page2TopTabIndicators.add(page2One);
        ChangeColorIconWithText page2Two = (ChangeColorIconWithText) findViewById(R.id.id_page2_two);
        page2TopTabIndicators.add(page2Two);

        page2One.setOnClickListener(this);
        page2Two.setOnClickListener(this);
        page2One.setIconAlpha(1.0f);
        // 第三页的top
      /*  ChangeColorIconWithText page3One = (ChangeColorIconWithText) findViewById(R.id.id_page3_one);
        page3TopTabIndicators.add(page3One);
        page3One.setOnClickListener(this);
        page3One.setIconAlpha(1.0f);*/

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // 传值
        getIntentDates();
        getHistoryDates();
    }

    private void getHistoryDates() {
        // TODO Auto-generated method stub
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (preferences.getBoolean("isLogin", false)) {
            nameTV.setText(preferences.getString("username", "您没有登陆呢~"));
        }

    }

    private void getIntentDates() {
        // TODO Auto-generated method stub
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        String username = bundle.getString("username");
        nameTV.setText(username);
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
                mTabIndicators.get(0).setIconAlpha(1.0f);
                page1.setVisibility(View.VISIBLE);
                break;
            case R.id.id_indicator_two:
                resetOtherTabs();
                currentPage = 2;
                mTabIndicators.get(1).setIconAlpha(1.0f);
                page2.setVisibility(View.VISIBLE);
                break;
            case R.id.id_indicator_three:
                resetOtherTabs();
                currentPage = 3;
                mTabIndicators.get(2).setIconAlpha(1.0f);
                page3.setVisibility(View.VISIBLE);
                break;
            case R.id.id_indicator_four:
                resetOtherTabs();
                currentPage = 4;
                mTabIndicators.get(3).setIconAlpha(1.0f);
                page4.setVisibility(View.VISIBLE);
                break;

            // 第一页顶部
            case R.id.id_page1_one:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(0).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(0, false);
                setTabLineIvIndex(0, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                break;
            case R.id.id_page1_two:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(1).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(1, false);
                setTabLineIvIndex(1, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                break;
            case R.id.id_page1_three:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(2).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(2, false);
                setTabLineIvIndex(2, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                break;
            case R.id.id_page1_four:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(3).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(3, false);
                setTabLineIvIndex(3, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                break;


            case R.id.id_page1_one2:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(4).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(4, false);
                setTabLineIvIndex(0, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                break;
            case R.id.id_page1_two2:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(5).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(5, false);
                setTabLineIvIndex(1, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                break;
            case R.id.id_page1_three2:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(6).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(6, false);
                setTabLineIvIndex(2, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                break;
            case R.id.id_page1_four2:
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(7).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(7, false);
                setTabLineIvIndex(3, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                break;

            case R.id.more_right:
                page1Top1.setVisibility(View.GONE);
                page1Top2.setVisibility(View.VISIBLE);
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(PAGE1_PAGE_NUM/2).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(PAGE1_PAGE_NUM/2, false);
                setTabLineIvIndex(0, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                break;
            case R.id.more_left:
                page1Top1.setVisibility(View.VISIBLE);
                page1Top2.setVisibility(View.GONE);
                resetOtherPage1Tabs();
                page1TopTabIndicators.get(PAGE1_PAGE_NUM/2-1).setIconAlpha(1.0f);
                page1ViewPager.setCurrentItem(PAGE1_PAGE_NUM/2-1, false);
                setTabLineIvIndex(PAGE1_PAGE_NUM/2-1, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                break;

            // 第二页顶部
            case R.id.id_page2_one:
                resetOtherpage2Tabs();
                page2TopTabIndicators.get(0).setIconAlpha(1.0f);
                page2TopTabIndicators.get(0).setBackgroundColor(Color.parseColor(Constant.MIAN_COLOR));
                page2ViewPager.setCurrentItem(0, false);
                setTabLineIvIndex(0, PAGE2_PAGE_NUM, mPage2TabLineIv, false, NO_EDGE);
                break;
            case R.id.id_page2_two:
                resetOtherpage2Tabs();
                page2TopTabIndicators.get(1).setIconAlpha(1.0f);
                page2TopTabIndicators.get(1).setBackgroundColor(Color.parseColor(Constant.MIAN_COLOR));
                page2ViewPager.setCurrentItem(1, false);
                setTabLineIvIndex(1, PAGE2_PAGE_NUM, mPage2TabLineIv, false, NO_EDGE);
                break;

            // 第三页顶部


            // 第四页
            case R.id.faceImg:
                if (preferences.getBoolean("isLogin", false)) {
                    isLogin = true;
                    application.isLogin = isLogin;
                } else {
                    isLogin = false;
                    application.isLogin = isLogin;
                    gotoLogin();
                }
                break;
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, YJBLoginActivity.class);
        startActivity(intent);
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
     */
    private void resetOtherpage2Tabs() {
        // TODO Auto-generated method stub
        for (int i = 0; i < page2TopTabIndicators.size(); i++) {
            page2TopTabIndicators.get(i).setIconAlpha(0);
            page2TopTabIndicators.get(i).setBackgroundColor(Color.WHITE);
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
     * @param pageNum
     * @param mTabLineIv
     * @param hasEdge
     */
    private void initTopTabLineWidth(int pageNum, View mTabLineIv, boolean hasEdge, int direction) {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        if (hasEdge) {
            if (direction == RIGHT_EDGE) {
                lp.width = (int) ((screenWidth - EDGE) / pageNum * 2);
            } else if (direction == LEFT_EDGE) {
                lp.width = (int) ((screenWidth - EDGE) / pageNum * 2);
            } else if (direction == NO_EDGE) {
                lp.width = screenWidth / pageNum;
            }

        } else {
            lp.width = screenWidth / pageNum;
        }

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
                    int cp = page1ViewPager.getCurrentItem();
                    if (cp < PAGE1_PAGE_NUM/2) {
                        setTabLineIvParamss(position, positionOffset, PAGE1_PAGE_NUM,
                                cp, mPage1TabLineIv, RIGHT_EDGE);
                        page1Top2.setVisibility(View.GONE);
                        page1Top1.setVisibility(View.VISIBLE);
                    } else {
                        setTabLineIvParamss(position - PAGE1_PAGE_NUM / 2, positionOffset, PAGE1_PAGE_NUM,
                                cp - PAGE1_PAGE_NUM / 2, mPage1TabLineIv, LEFT_EDGE);
                        page1Top1.setVisibility(View.GONE);
                        page1Top2.setVisibility(View.VISIBLE);
                    }

                    break;
                case 2:
                  /*  left = page2TopTabIndicators.get(position);
                    right = page2TopTabIndicators.get(position + 1);
                    setTabLineIvParamss(position, positionOffset, PAGE2_PAGE_NUM,
                            page2ViewPager.getCurrentItem(), mPage2TabLineIv, NO_EDGE);*/
                    break;
                case 3:
                 /*   left = page3TopTabIndicators.get(position);
                    right = page3TopTabIndicators.get(position + 1);
                    setTabLineIvParamss(position, positionOffset, PAGE3_PAGE_NUM,
                            page3ViewPager.getCurrentItem(), mPage3TabLineIv, NO_EDGE);*/
                    break;
                case 4:
                    break;

            }
            if (left != null && right != null) {
                left.setIconAlpha(1 - positionOffset);
                right.setIconAlpha(positionOffset);
            }

        }

    }

    /**
     * 动态设置下划线的位置(处理滑动)
     *
     * @param position
     * @param positionOffset
     * @param mTabLineIv
     * @param currentIndex
     */
    public void setTabLineIvParamss(int position, float positionOffset,
                                    int pageNum, int currentIndex, View mTabLineIv, int direction) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        double oneWidth = screenWidth * 1.0 / pageNum;

        if (direction == RIGHT_EDGE) {
            oneWidth = (screenWidth - EDGE) * 1.0 / pageNum * 2;
            if (currentIndex == position)// 左滑
            {
                lp.leftMargin = (int) (positionOffset
                        * oneWidth + currentIndex
                        * oneWidth);

            } else if (currentIndex != position) // 右滑
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * oneWidth + currentIndex
                        * oneWidth);
            }
        } else if (direction == LEFT_EDGE) {
            oneWidth = (screenWidth - EDGE) * 1.0 / pageNum * 2;
            if (currentIndex == position)// 左滑
            {
                lp.leftMargin = (int) (positionOffset
                        * oneWidth + currentIndex
                        * oneWidth)+(int)EDGE;

            } else if (currentIndex != position) // 右滑
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * oneWidth + currentIndex
                        * oneWidth)+(int)EDGE;
            }
        } else if (direction == NO_EDGE) {
            if (currentIndex == position)// 左滑
            {
                lp.leftMargin = (int) (positionOffset
                        * oneWidth + currentIndex
                        * oneWidth);

            } else if (currentIndex != position) // 右滑
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * oneWidth + currentIndex
                        * oneWidth);
            }
        }

        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 设置下划线的index
     *
     * @param index
     * @param pageNum
     * @param mTabLineIv
     * @param hasEdge
     * @param direction
     */
    public void setTabLineIvIndex(int index, int pageNum, View mTabLineIv, boolean hasEdge, int direction) {

        double oneWidth = screenWidth * 1.0 / pageNum;
        if (hasEdge) {
            if (direction == RIGHT_EDGE) {
                oneWidth = (screenWidth - EDGE) * 1.0 / pageNum ;
            } else if (direction == LEFT_EDGE) {
                oneWidth = (screenWidth - EDGE) * 1.0 / pageNum ;
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();
                lp.leftMargin = (int) (index * (oneWidth) + EDGE);
                mTabLineIv.setLayoutParams(lp);
                return;
            } else if (direction == NO_EDGE) {
                oneWidth = screenWidth * 1.0;
            }

        }

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.leftMargin = (int) (index * (oneWidth));
        mTabLineIv.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        switch (currentPage) {
            case 1:
                int cp = page1ViewPager.getCurrentItem();
                if (position == PAGE1_PAGE_NUM/2-1) {
                    setTabLineIvIndex(position, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, RIGHT_EDGE);
                    page1Top2.setVisibility(View.GONE);
                    page1Top1.setVisibility(View.VISIBLE);
                } else  if (position == PAGE1_PAGE_NUM/2){
                    setTabLineIvIndex(position-PAGE1_PAGE_NUM/2, PAGE1_PAGE_NUM/2, mPage1TabLineIv, true, LEFT_EDGE);
                    page1Top1.setVisibility(View.GONE);
                    page1Top2.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                resetOtherpage2Tabs();
                page2TopTabIndicators.get(position).setBackgroundColor(Color.parseColor(Constant.MIAN_COLOR));
                page2TopTabIndicators.get(position).setIconAlpha(1.0f);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }
}
