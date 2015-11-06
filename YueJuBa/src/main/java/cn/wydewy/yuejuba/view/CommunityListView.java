package cn.wydewy.yuejuba.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import cn.wydewy.vnique.util.ViewUtil;
import cn.wydewy.vnique.view.VniqueListView;
import cn.wydewy.yuejuba.R;


/**
 * Created by weiyideweiyi8 on 2015/10/26.
 */
public class CommunityListView extends VniqueListView{
    // -----------顶部链接相关-------------
    private  View linkLeader;// 顶部布局文件；
    private int linkLeaderHeight;// 顶部布局文件的高度；
    public CommunityListView(Context context) {
        this(context, null);
    }

    public CommunityListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommunityListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 顶部链接布局
        linkLeader = inflater.inflate(R.layout.page1_header, null);
//        ViewUtil.measureView(linkLeader);
//        linkLeaderHeight = linkLeader.getMeasuredHeight();
//        Log.i("tag", "headerHeight = " + linkLeaderHeight);
//        topPadding(linkLeaderHeight);
        this.addHeaderView(linkLeader);
    }
}
