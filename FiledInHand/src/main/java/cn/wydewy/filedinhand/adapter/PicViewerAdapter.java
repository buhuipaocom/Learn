package cn.wydewy.filedinhand.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

import cn.wydewy.vnique.util.CommonAdapter;
import cn.wydewy.vnique.util.ViewHolder;

/**
 * Created by weiyideweiyi8 on 2015/10/24.
 */
public class PicViewerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
