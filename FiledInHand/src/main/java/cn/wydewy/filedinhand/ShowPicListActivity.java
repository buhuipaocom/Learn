package cn.wydewy.filedinhand;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.widget.GridView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import cn.wydewy.filedinhand.adapter.PicListAdapter;

public class ShowPicListActivity extends Activity {
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    public static List<String> mAllImage = new LinkedList<String>();

    private GridView mGirdView;
    private PicListAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        setImageUrl(Environment.getExternalStorageDirectory()
                + "/FiledInhand/");
        intView();

    }

    protected void setImageUrl(String url) {
        this.url = url;
    }

    private void intView() {

        mGirdView = (GridView) findViewById(R.id.id_gridView);

        mImgDir = new File(url);
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        mAllImage = mImgs;
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new PicListAdapter(getApplicationContext(), mImgs,
                R.layout.pic_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
    }

}
