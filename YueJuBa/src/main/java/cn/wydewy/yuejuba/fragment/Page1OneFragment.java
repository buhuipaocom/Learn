package cn.wydewy.yuejuba.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cn.wydewy.vnique.view.VniqueListView;
import cn.wydewy.vnique.view.VniqueListView.ILoadListener;
import cn.wydewy.vnique.view.VniqueListView.IReflashListener;
import cn.wydewy.yuejuba.R;
import cn.wydewy.yuejuba.adapter.Page1OneAdapter;
import cn.wydewy.yuejuba.adapter.Page1TwoAdapter;
import cn.wydewy.yuejuba.entity.Dynamic;
import cn.wydewy.yuejuba.entity.TeacherInfo;
import cn.wydewy.yuejuba.view.CommunityListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Page1OneFragment extends Fragment implements IReflashListener,
		ILoadListener {

	private CommunityListView lv;
	private Page1TwoAdapter adapter;
	private RequestQueue requestQueue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 初始化VolleyRequestQueue对象,这个对象是Volley访问网络的直接入口
		requestQueue = Volley.newRequestQueue(getActivity());

		LinearLayout listLayout = (LinearLayout) inflater.inflate(
				R.layout.page1_fragment_listview, container, false);
		lv = (CommunityListView) listLayout.findViewById(R.id.lv);

		initList();
		return listLayout;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO 取消所有未执行完的网络请求
		requestQueue.cancelAll(this);
	}

	/**
	 * 初始化List
	 */
	private void initList() {
		// 初始化数据
		List<Dynamic> list = new ArrayList<Dynamic>();
		for (int i = 1; i <= 20; i++) {
			Dynamic item = new Dynamic();
			list.add(item);
		}
		// 绑定数据
		adapter = new Page1TwoAdapter(getActivity(),list,R.layout.page1_list_item);
		lv.setAdapter(adapter);
		lv.setILoadListener(this);
		lv.setIReflashListener(this);
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		lv.loadComplete();
	}

	@Override
	public void onReflash() {
		// TODO Auto-generated method stub
		lv.reflashComplete();
	}
}
