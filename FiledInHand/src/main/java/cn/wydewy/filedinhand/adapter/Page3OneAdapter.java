package cn.wydewy.filedinhand.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.List;

import cn.wydewy.filedinhand.PointDetailActivity;
import cn.wydewy.filedinhand.R;
import cn.wydewy.filedinhand.entity.Point;
import cn.wydewy.filedinhand.PictureListActivity;
import cn.wydewy.filedinhand.util.Constant;
import cn.wydewy.vnique.util.BitmapCache;

public class Page3OneAdapter extends BaseAdapter {

    private Context context;
    private List<Point> list;
    private Point currentItem;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;

    public Page3OneAdapter(Context context, List<Point> list,
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        currentItem = list.get(list.size()-position-1);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.point_item, null);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView);

            holder.nameText = (TextView) convertView
                    .findViewById(R.id.nameText);
            holder.notesText = (TextView) convertView
                    .findViewById(R.id.notesText);
            holder.detailText = (TextView) convertView
                    .findViewById(R.id.detailText);
            holder.locationText = (TextView) convertView
                    .findViewById(R.id.locationText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //写在外面可以包含所有情况
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = list.get(list.size()-position-1);
                Intent intent = new Intent(context, PictureListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", currentItem.getName());
                intent.putExtras(bundle);
//                    Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, currentItem.getName() + "", Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });


        holder.detailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = list.get(list.size()-position-1);
                Intent intent = new Intent(context, PointDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", currentItem.getId());
                intent.putExtras(bundle);
//                    Toast.makeText(context, item.getName() + "", Toast.LENGTH_LONG).show();
                context.startActivity(intent);
//                    Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
            }
        });

        if("".equals(currentItem.getName())){
            holder.nameText.setText("lost");
        }else{
            holder.nameText.setText(currentItem.getName());
        }

        if("".equals(currentItem.getNotes())){
            holder.notesText.setText("没有笔记");
        }else{
            holder.notesText.setText(currentItem.getNotes());
        }

        if("".equals(currentItem.getDetail())){
            holder.detailText.setText("nothing");
        }else{
            holder.detailText.setText(currentItem.getDetail());
        }

        if("".equals(currentItem.getKmllocation())){
            holder.locationText.setText("nothing");
        }else{
            holder.locationText.setText(currentItem.getKmllocation());
        }

        // 利用Volley加载图片
        ImageListener listener = ImageLoader.getImageListener(holder.imageView,
                0, R.drawable.mz_img_error);//显示未加载的图片
        if("".equals(currentItem.getImageUrl())){
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }else{
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }

        return convertView;
    }

    class ViewHolder {
        public ImageView imageView;
        public TextView nameText;
        public TextView notesText;
        public TextView detailText;
        public TextView locationText;

    }

    /**
     * 刷新列表
     *
     * @param newList
     */
    public void notifyDataSetChanged(List<Point> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

}
