package cn.wydewy.vnique.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
public class FloatDialog extends Dialog implements OnClickListener {
	private TextView leftTextView, rightTextView;
	private IDialogOnclickInterface dialogOnclickInterface;
	private Context context;

	public FloatDialog(Context context, int theme,
			IDialogOnclickInterface dialogOnclickInterface) {
		super(context, theme);
		this.context = context;
		this.dialogOnclickInterface = dialogOnclickInterface;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog);

		leftTextView = (TextView) findViewById(R.id.textview_one);
		rightTextView = (TextView) findViewById(R.id.textview_two);
		leftTextView.setOnClickListener(this);
		rightTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getLeft()) {
		case 1:
			dialogOnclickInterface.leftOnclick();
			break;
		case 2:
			dialogOnclickInterface.rightOnclick();
			break;
		default:
			break;
		}
	}

	public interface IDialogOnclickInterface {
		void leftOnclick();

		void rightOnclick();
	}
}