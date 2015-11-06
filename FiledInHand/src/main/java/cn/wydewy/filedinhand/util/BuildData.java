package cn.wydewy.filedinhand.util;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

/**
 * 原来版本用来保存产状的，note by wydewy
 */
public class BuildData {

	private String filename;
	private String outputtext;

	File sdCardDir = Environment.getExternalStorageDirectory();
	String SD = Environment.getExternalStorageDirectory().getPath();
	String FieldinHand = SD + "/FieldinHand";
	File FieldinHandData = new File(FieldinHand);

	public BuildData(String newoutputtext, String newfilename) {
		outputtext = newoutputtext;
		filename = newfilename;
	}

	public void build() throws Exception {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File saveFile = new File(FieldinHandData, filename);
			FileOutputStream outStream1 = new FileOutputStream(saveFile);
			outStream1.write(outputtext.getBytes());
			outStream1.close();
		}
	}

}
