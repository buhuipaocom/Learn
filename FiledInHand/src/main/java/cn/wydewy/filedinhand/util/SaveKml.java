package cn.wydewy.filedinhand.util;
import android.os.Environment;
import java.io.*;
/**
 * 保存.kml
 * @author weiyideweiyi8
 *
 */
public class SaveKml {
	
	String name;//点名
	String notes;//笔记
	String detail ;//详情
	String location;//经纬度
	String filename;//保存的文件名和海拔
	String directionis;//方向
	String locationis;//？？
	String kmllocation ;//经度，纬度，和海拔
	
    File sdCardDir = Environment.getExternalStorageDirectory();
    String SD=Environment.getExternalStorageDirectory().getPath();
	String  FieldinHand = SD+"/FieldinHand";
   	File FieldinHandData = new File(FieldinHand );
   	
	public SaveKml(	String newname,String newnotes,String newdetail ,String newlocation ,String newkmllocation,String newdirectionis,String newlocationis,String newfilename){
   		name=newname;
   		notes=newnotes;
   		detail =newdetail;
   		location =newlocation;
   		filename =newfilename;
   		kmllocation=newkmllocation;
   		directionis=newdirectionis;
   		locationis=newlocationis;
   		
   	}
	
	public void makekml() throws IOException{

	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File saveFile = new File(FieldinHandData, (filename+".kml"));
	      FileOutputStream outStream1 = new FileOutputStream(saveFile);
		String outputtext =("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\t<kml xmlns=\"http://earth.google.com/kml/2.1\">\r\n \t<Placemark>\r\n  \t \t<name>"+
	      name
	      +
				"</name> \r\n   \t \t<description>"+
				notes+
				",\n\t"+
				directionis
				+",\n\t"+
				detail
				+",\n\t"+
				locationis+
				"\n\t"+
				kmllocation
	      +
				"</description>  \r\n  \t \t<Point>  \r\n \t \t \t<coordinates>"+
	      location 
	      +
				"</coordinates>   \r\n \t \t</Point> \r\n \t</Placemark>\r\n</kml>\r\n ");

	        outStream1.write(outputtext.getBytes());
	        outStream1.close();
	    	}
		}
}
