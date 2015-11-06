package cn.wydewy.filedinhand.imageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import cn.wydewy.filedinhand.ShowPicActivity;
import cn.wydewy.vnique.util.CommonAdapter;

import java.util.LinkedList;
import java.util.List;

import cn.wydewy.filedinhand.R;



public class MyAdapter extends CommonAdapter<String> {
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();
	/**
	 * 用户所有的图片，存储为图片的完整路径
	 */

	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
					 String dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
	}

	@Override
	public void convert(final cn.wydewy.vnique.util.ViewHolder helper, final String item, final int position) {
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);

		mSelect.setVisibility(View.GONE);
		mImageView.setColorFilter(null);
		//设置ImageView的点击事件
		mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContext != null) {
					Intent intent = new Intent(mContext, ShowPicActivity.class);
					intent.putExtra("page", position);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(intent);
				}

			}
		});
		mImageView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item)) {

					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setVisibility(View.GONE);
					mImageView.setColorFilter(null);
				} else
				// 未选择该图片
				{
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setVisibility(View.VISIBLE);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}
				return true;//阻止短点击事件
			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item)) {
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}
		/**
		 * 记录所有图片
		 */
//        mAllImage.add(mDirPath + "/" + item);
	}
}
