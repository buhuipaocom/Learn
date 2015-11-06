package cn.wydewy.yuejuba.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.wydewy.yuejuba.R;
import cn.wydewy.yuejuba.entity.TimeTableInfo;

import com.android.volley.RequestQueue;

public class Page1ThreeAdapter extends BaseAdapter {

	private Context context;
	private List<TimeTableInfo> list;

	private RequestQueue requestQueue;
//	private ImageLoader imageLoader;
	private LayoutInflater inflater;

	public Page1ThreeAdapter(Context context, List<TimeTableInfo> list,
			RequestQueue requestQueue) {
		this.context = context;
		this.list = list;
		this.requestQueue = requestQueue;

//		// 初始化Volley图片Loader
//		imageLoader = new ImageLoader(requestQueue, new BitmapCache());
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
			convertView = inflater.inflate(R.layout.timetable_item, null);

			holder = new ViewHolder();
//			holder.imageView = (ImageView) convertView
//					.findViewById(R.id.imageView);
			holder.nameText = (TextView) convertView
					.findViewById(R.id.nameText);
			holder.timeText = (TextView) convertView
					.findViewById(R.id.timeText);
			holder.teacherText = (TextView) convertView
					.findViewById(R.id.teacherText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TimeTableInfo item = list.get(position);
		holder.nameText.setText(item.getName());

//		// 利用Volley加载图片
//		ImageListener listener = ImageLoader.getImageListener(holder.imageView,
//				0, R.drawable.mz_img_error);
//		imageLoader.get(item.getImageUrl(), listener);
		return convertView;
	}

	class ViewHolder {
//		public ImageView imageView;
		public TextView nameText;
		public TextView timeText;
		public TextView teacherText;

	}

	/**
	 * 刷新列表
	 * 
	 * @param newList
	 */
	public void notifyDataSetChanged(List<TimeTableInfo> newList) {
		this.list = newList;
		notifyDataSetChanged();
	}

}
