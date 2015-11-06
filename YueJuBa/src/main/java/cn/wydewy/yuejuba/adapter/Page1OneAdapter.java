package cn.wydewy.yuejuba.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.wydewy.yuejuba.R;
import cn.wydewy.yuejuba.entity.TeacherInfo;
import cn.wydewy.yuejuba.util.BitmapCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class Page1OneAdapter extends BaseAdapter {

	private Context context;
	private List<TeacherInfo> list;

	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private LayoutInflater inflater;

	public Page1OneAdapter(Context context, List<TeacherInfo> list,
			RequestQueue requestQueue) {
		this.context = context;
		this.list = list;
		this.requestQueue = requestQueue;

		// 初始化Volley图片Loader
		imageLoader = new ImageLoader(requestQueue, new BitmapCache());
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.teacher_item, null);

			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageView);
			holder.nameText = (TextView) convertView
					.findViewById(R.id.nameText);
			holder.priceText = (TextView) convertView
					.findViewById(R.id.priceText);
			holder.locationText = (TextView) convertView
					.findViewById(R.id.locationText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TeacherInfo item = list.get(position);
		holder.nameText.setText(item.getName());

		// 利用Volley加载图片
		ImageListener listener = ImageLoader.getImageListener(holder.imageView,
				0, R.drawable.mz_img_error);
		imageLoader.get(item.getImageUrl(), listener);
		return convertView;
	}

	class ViewHolder {
		public ImageView imageView;
		public TextView nameText;
		public TextView priceText;
		public TextView locationText;

	}

	/**
	 * 刷新列表
	 * 
	 * @param newList
	 */
	public void notifyDataSetChanged(List<TeacherInfo> newList) {
		this.list = newList;
		notifyDataSetChanged();
	}

}
