package cn.wydewy.yuejuba.fragment;

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
import cn.wydewy.yuejuba.adapter.Page1FourAdapter;

public class Page2ThreeFragment extends Fragment implements IReflashListener, ILoadListener {

	private VniqueListView lv;
	private Page1FourAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout listLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_with_listview, container, false);
		lv = (VniqueListView) listLayout.findViewById(R.id.lv);
		adapter = new Page1FourAdapter(getActivity());
		lv.setAdapter(adapter);
		lv.setILoadListener(this);
		lv.setIReflashListener(this);
		return listLayout;
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
