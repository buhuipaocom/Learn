package cn.wydewy.vnique.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @author weiyideweiyi8 
 * @描述 罗盘中心的小点
 */

public class LevelplainView extends ImageView {

	private float x;
	private float y;
	public float a = 180;
	public float b = 180;
	public float length = 360;
	private float r = 2;
	private Drawable levelplain;
	private Paint paint = new Paint();
	
	private int width;
	private int height;

	public LevelplainView(Context context) {
		super(context);
		paint.setColor(Color.BLACK);
		paint.setAlpha(150);
		levelplain = null;
	}

	public LevelplainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.BLACK);
		paint.setAlpha(150);
		levelplain = null;
	}

	public LevelplainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint.setColor(Color.BLACK);
		paint.setAlpha(150);
		levelplain = null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (levelplain == null) {
			levelplain = getDrawable();
			levelplain.setBounds(0, 0,width, height);
			x = getWidth();
			y = getHeight();
			r = (x / 16 + y / 16) / 2;
		}

		canvas.save();

		canvas.drawCircle(a * x / length, b * y / length, r, paint);
		levelplain.draw(canvas);
		canvas.restore();
	}

	public int getLevelplainViewWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLevelplainViewHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void updatepoint(double gx, double gy, double gz) {

		if (Math.abs(gx) <= 0.9 && Math.abs(gy) <= 0.9) {
			paint.setColor(Color.BLUE);
			paint.setAlpha(255);
		} else if (Math.abs(gx) <= 0.9 && Math.abs(gy) > 0.9) {
			paint.setColor(Color.RED);
			paint.setAlpha(255);
		} else if (Math.abs(gx) > 0.9 && Math.abs(gy) <= 0.9) {
			paint.setColor(Color.RED);
			paint.setAlpha(255);
		} else {
			paint.setColor(Color.BLACK);
			paint.setAlpha(150);
		}

		length = (float) ((float) 2 * Math.sqrt(gx * gx + gy * gy + gz * gz));

		a = (float) gx + length / 2;
		b = -(float) gy + length / 2;

		invalidate();
	}
}
