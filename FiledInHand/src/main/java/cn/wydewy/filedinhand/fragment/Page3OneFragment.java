package cn.wydewy.filedinhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.wydewy.filedinhand.PictureListActivity;
import cn.wydewy.filedinhand.PointDetailActivity;
import cn.wydewy.filedinhand.R;
import cn.wydewy.filedinhand.adapter.Page3OneAdapter;
import cn.wydewy.filedinhand.entity.Point;
import cn.wydewy.filedinhand.util.Constant;
import cn.wydewy.vnique.view.VniqueListView;

public class Page3OneFragment extends Fragment implements VniqueListView.IReflashListener, VniqueListView.ILoadListener, View.OnTouchListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private View lvP;
    private VniqueListView lv;
    private Page3OneAdapter adapter;
    private RequestQueue requestQueue;
    private List<Point> list;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getActivity());
        LinearLayout listLayout = (LinearLayout) inflater.inflate(
                R.layout.page3_fragment1, container, false);

        lv = (VniqueListView) listLayout.findViewById(R.id.lv);
        lvP = listLayout.findViewById(R.id.lvP);
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
        list = DataSupport.findAll(Point.class);

        // 绑定数据
        adapter = new Page3OneAdapter(getActivity(), list, requestQueue);
        lv.setAdapter(adapter);
        lv.setILoadListener(this);
        lv.setIReflashListener(this);
        lv.setOnItemClickListener(this);



//		lv.setOnCreateContextMenuListener(getActivity());
        //为 ListView 的所有 item 注册 ContextMenu
        registerForContextMenu(lv);
        lvP.setOnTouchListener(this);
    }


    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        list = DataSupport.findAll(Point.class);
        adapter.notifyDataSetChanged(list);
        lv.loadComplete();
    }

    @Override
    public void onReflash() {
        // TODO Auto-generated method stub
        list = DataSupport.findAll(Point.class);
        adapter.notifyDataSetChanged(list);
        lv.reflashComplete();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        list = DataSupport.findAll(Point.class);
        adapter.notifyDataSetChanged(list);
        return false;
    }


    private static final int ITEM1 = Menu.FIRST;
    private static final int ITEM2 = Menu.FIRST + 1;
    private static final int ITEM3 = Menu.FIRST + 2;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //添加菜单项
        menu.add(0, ITEM1, 0, "图片");
        menu.add(0, ITEM2, 0, "名称");
        menu.add(0, ITEM3, 0, "删除");
    }

    //菜单单击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //获取当前被选择的菜单项的信息
        //AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        //Log.i("braincol",String.valueOf(info.id));
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Point point = list.get(list.size() - menuInfo.position - 1 + 2);
        int id = point.getId();
        switch (item.getItemId()) {
            case ITEM1:
                //在这里添加处理代码
//				Toast.makeText(getActivity(),"查看图片", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), PictureListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", point.getName());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case ITEM2:
                //在这里添加处理代码
                Toast.makeText(getActivity(), "" + point.getName(), Toast.LENGTH_LONG).show();
                break;
            case ITEM3:
                //在这里添加处理删除的代码
//				Toast.makeText(getActivity(),id+"", Toast.LENGTH_LONG).show();
                DataSupport.delete(Point.class, id);
                list.remove(list.size() - menuInfo.position - 1 + 2);
                adapter.notifyDataSetChanged(list);
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Toast.makeText(getActivity(),position+"",Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return true;
    }

}
