package cn.wydewy.filedinhand.fragment;

import cn.wydewy.filedinhand.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Page3TwoFragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout voteListLayout = (LinearLayout) inflater.inflate(
				R.layout.page3_fragment2, container, false);
		return voteListLayout;
	}
}
