package cn.wydewy.filedinhand;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.wydewy.vnique.util.ImageLoader;
import cn.wydewy.vnique.view.ZoomImageView;

public class ShowPicActivity extends Activity {

    private ViewPager mViewPager;
    private int currentPage;
    private List<String> mImageNames = PictureListActivity.mAllImage;
    //	private List<View> mViews = new ArrayList<View>();
    private View[] mViews = new View[mImageNames.size()];
    private boolean showDir = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        currentPage = getIntent().getIntExtra("page", 1);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        final LayoutInflater inflater = getLayoutInflater();

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = inflater.inflate(R.layout.show_pic, null);
                container.addView(view);
                ZoomImageView imageView = (ZoomImageView) view.findViewById(R.id.image_viewer);
                final ImageView image_set = (ImageView) view.findViewById(R.id.image_set);
                final TextView pic_name = (TextView) view.findViewById(R.id.pic_name);
                pic_name.setText(mImageNames.get(position) + "");
                String url = PictureListActivity.url +"/"+ mImageNames.get(position);
//                Toast.makeText(ShowPicActivity.this,name,Toast.LENGTH_LONG).show();
                imageView.setImageBitmap(ImageLoader.getDiskBitmap(url));
                image_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showDir) {
                            pic_name.setVisibility(View.GONE);
                            showDir = false;
                        } else {
                            pic_name.setVisibility(View.VISIBLE);
                            pic_name.setText(mImageNames.get(position) + "");
                            showDir = true;
                        }
                    }
                });
                mViews[position] = view;
//				mViews.add(imageView);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImageNames.size();
            }
        });
        mViewPager.setCurrentItem(currentPage);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
