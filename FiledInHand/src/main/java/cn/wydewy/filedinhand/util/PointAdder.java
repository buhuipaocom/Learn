package cn.wydewy.filedinhand.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class PointAdder {
	String content;
	String filename;
	
	
    File sdCardDir = Environment.getExternalStorageDirectory();
    String SD=Environment.getExternalStorageDirectory().getPath();
    String  FieldinHand = SD+"/FieldinHand";
   	File FieldinHandData = new File(FieldinHand );
   	
   	public PointAdder(String newcontent,String newfilename){
   		content =newcontent;
   		filename =newfilename;
   	}
   	
	public void addpoint() {
		
		String wholefilename=FieldinHandData+"/"+filename;
	        BufferedWriter out = null;
	        try {
	            out = new BufferedWriter(new OutputStreamWriter(
	                    new FileOutputStream(wholefilename, true)));
	            out.write((content+"\r\n"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}
}
