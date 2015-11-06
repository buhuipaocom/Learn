package cn.wydewy.yuejuba.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wydewy.vnique.util.CommonAdapter;
import cn.wydewy.vnique.util.ViewHolder;
import cn.wydewy.yuejuba.R;
import cn.wydewy.yuejuba.entity.TeacherInfo;
import cn.wydewy.yuejuba.util.BitmapCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class Page1TwoAdapter extends CommonAdapter {


	public Page1TwoAdapter(Context context, List mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Object item, int position) {


	}
}
