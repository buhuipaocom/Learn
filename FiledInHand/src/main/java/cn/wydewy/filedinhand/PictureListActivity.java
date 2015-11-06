package cn.wydewy.filedinhand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import cn.wydewy.filedinhand.bean.ImageFolder;
import cn.wydewy.filedinhand.imageloader.ListImageDirPopupWindow;
import cn.wydewy.filedinhand.imageloader.ListImageDirPopupWindow.OnImageDirSelected;
import cn.wydewy.filedinhand.imageloader.MyAdapter;
import cn.wydewy.filedinhand.util.Constant;

public class PictureListActivity extends Activity implements OnImageDirSelected {
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

    private GridView mGirdView;
    private MyAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    public static List<String> mAllImage = new LinkedList<String>();
    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFolder> mImageFolders = new ArrayList<ImageFolder>();

    private RelativeLayout mBottomLy;

    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;

    private int mScreenHeight;

    private cn.wydewy.filedinhand.imageloader.ListImageDirPopupWindow mListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
            data2View();

        }
    };
    public static String name;
    public static String url;
    private ImageFolder currentFolder;
    private String urlAll;

    /**
     * 为View绑定数据
     */
    private void data2View() {
       /* if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
     /*   mAdapter = new MyAdapter(getApplicationContext(), mImgs,
                R.layout.grid_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");*/
        if (currentFolder != null) {
            url= currentFolder.getDir();
            showPicListInFolder(currentFolder);
        }

    }

    ;

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {

        List<ImageFolder> imageFolders = new ArrayList<ImageFolder>();
        if (mImageFolders != null) {
            for (ImageFolder imageFolder : mImageFolders) {
                if (imageFolder.getDir() != null&&imageFolder!=null) {//容错
                    if (imageFolder.getDir().contains(urlAll)) {
                        imageFolders.add(imageFolder);
                        Log.e("imageFolders.add:", urlAll+" urlAll");
                        Log.e("imageFolders.add:", imageFolder.getDir()+" imageFolder.getDir()");
                        Log.e("imageFolders.add:", name +" name");
                        if (imageFolder.getDir().contains(name)) {
                            Log.e("imageFolders.add:", imageFolder.getDir());
                            Log.e("imageFolders.add:", name);
                            currentFolder = imageFolder;
                        }
                    }
                }
            }
        }
        if(currentFolder==null){
            currentFolder = imageFolders.get(0);
            if(!"null".equals(name)&&!"ALL".equals(name)){
                Toast.makeText(getApplicationContext(),"没有匹配到~"+name,
                        Toast.LENGTH_SHORT).show();
            }
            url = currentFolder.getDir();
        }

        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                imageFolders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_picture);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        urlAll = Constant.URL_FILED_IN_HAND;
        if (bundle != null) {
            name = bundle.getString("name", "");

        } else {
            name = "null";
        }

        Log.e("onCreate:", name);
        initView();
        getImages(name);
        initEvent();

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     *
     * @param url
     */
    private void getImages(final String url) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        Toast.makeText(PictureListActivity.this, mImageUri.toString(), Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PictureListActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                if (mCursor != null) {
                    Log.e("TAG", mCursor.getCount() + "");
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));

                        Log.e("TAG", path);
                        // 拿到第一张图片的路径
                        if (firstImage == null)
                            firstImage = path;
                        // 获取该图片的父路径名
                        File parentFile = new File(path).getParentFile();
                        if (parentFile == null)
                            continue;
                        String dirPath = parentFile.getAbsolutePath();
                        ImageFolder imageFolder = null;
                        // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                        if (mDirPaths.contains(dirPath)) {
                            continue;
                        } else {
                            mDirPaths.add(dirPath);
                            // 初始化imageFloder
                            imageFolder = new ImageFolder();
                            imageFolder.setDir(dirPath);
                            imageFolder.setFirstImagePath(path);
                        }

                        int picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                if (filename.endsWith(".jpg")
                                        || filename.endsWith(".png")
                                        || filename.endsWith(".jpeg"))
                                    return true;
                                return false;
                            }
                        }).length;
                        totalCount += picSize;

                        imageFolder.setCount(picSize);
                        mImageFolders.add(imageFolder);

                        if (picSize > mPicsSize) {
                            mPicsSize = picSize;
                            mImgDir = parentFile;
                        }
                    }
                    mCursor.close();
                }

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();


    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);

        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);

    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow
                        .setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void selected(ImageFolder folder) {

        showPicListInFolder(folder);
        currentFolder = folder;
        url = currentFolder.getDir();

    }

    private void showPicListInFolder(ImageFolder folder) {
        mImgDir = new File(folder.getDir());
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
        mAdapter = new MyAdapter(getApplicationContext(), mImgs,
                R.layout.grid_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mImageCount.setText(folder.getCount() + "张");
        mChooseDir.setText(folder.getName());
        mListImageDirPopupWindow.dismiss();
    }

}
