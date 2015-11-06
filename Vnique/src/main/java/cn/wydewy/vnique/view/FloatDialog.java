package cn.wydewy.vnique.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.wydewy.vnique.R;

/**
 * @文件名称: FloatDialog.java
 * @功能描述: 自定义dialog
 * @版本信息: Copyright (c)2015
 * @开发人员: wydewy
 * @版本日志: 1.0
 * @创建时间: 2015年8月27日 下午1:45:38
 */
public class FloatDialog extends LinearLayout {

	private TextView leftTextView, rightTextView;

	private String leftText;
	private int leftTextColor;
	private Drawable leftTextBackground;

	private String rightText;
	private int rightTextColor;
	private Drawable rightTextBackground;

	private int background;

	private LayoutParams leftParams, rightParams;

	public FloatDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 通过属性获得style
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FloatDialog);
		// 获得属性数目
		int n = a.getIndexCount();
		// 一个一个设置属性值，各个属性值在xml布局中已经设置了！
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.FloatDialog_background:
				background = a.getColor(attr, 0xFF000000);
				break;
			case R.styleable.FloatDialog_left_text:
				leftText = a.getString(attr);
				break;
			case R.styleable.FloatDialog_left_text_color:
				leftTextColor = a.getColor(attr, 0xFFFFFFFF);
				break;
			case R.styleable.FloatDialog_left_background:
				leftTextBackground = a.getDrawable(attr);
				break;
			case R.styleable.FloatDialog_right_text:
				rightText = a.getString(attr);
				break;
			case R.styleable.FloatDialog_right_text_color:
				rightTextColor = a.getColor(attr, 0xFFFFFFFF);
				break;
			case R.styleable.FloatDialog_right_background:
				rightTextBackground = a.getDrawable(attr);
				break;
			}

		}
		a.recycle();

		initView(context);
	}

	@SuppressLint("NewApi")
	private void initView(Context context) {
		// TODO Auto-generated method stub
		leftTextView = new TextView(context);
		leftTextView.setText(leftText);
		leftTextView.setTextColor(leftTextColor);
		leftTextView.setBackground(leftTextBackground);
		leftTextView.setPadding(5, 5, 0, 0);
		leftTextView.setGravity(Gravity.CENTER_HORIZONTAL);

		rightTextView = new TextView(context);
		rightTextView.setText(rightText);
		rightTextView.setTextColor(rightTextColor);
		rightTextView.setBackground(rightTextBackground);
		rightTextView.setPadding(5, 5, 0, 0);
		rightTextView.setGravity(Gravity.CENTER_HORIZONTAL);

		setBackgroundColor(background);
		setOrientation(LinearLayout.HORIZONTAL);
		leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(leftTextView, leftParams);
		rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(rightTextView, rightParams);

	}

	public FloatDialog(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

}