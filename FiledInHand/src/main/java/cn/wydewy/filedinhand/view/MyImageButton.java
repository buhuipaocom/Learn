package cn.wydewy.filedinhand.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by freddy on 15/7/11.
 */

/**
 * 自定义ImageButton 可以在ImageButton上面设置文字
 * 
 * @author SJR
 */
public class MyImageButton extends ImageButton {
	private String _text = "";
	private int _color = 0;
	private float _textsize = 0f;

	public MyImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(String text) {
		this._text = text;
	}

	public void setColor(int color) {
		this._color = color;
	}

	public void setTextSize(float textsize) {
		this._textsize = textsize;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setTextAlign(Align.CENTER);
		paint.setColor(_color);
		paint.setTextSize(_textsize);
		canvas.drawText(_text, canvas.getWidth() / 2,
				(canvas.getHeight() / 2) + 12, paint);
	}
}
