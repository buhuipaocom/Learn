package cn.wydewy.filedinhand.util;
import android.os.Environment;
import java.io.*;
/**
 * 保存.kml
 * @author weiyideweiyi8
 *
 */
public class SaveXml {
	String name;
	String notes;
	String detail ;
	String location;
	String filename;
	String directionis;
	String locationis;
	String kmllocation ;
    File sdCardDir = Environment.getExternalStorageDirectory();
    String SD=Environment.getExternalStorageDirectory().getPath();
	String  FieldinHand = SD+"/FieldinHand";
   	File FieldinHandData = new File(FieldinHand );
   	
   	public SaveXml(	String newname,String newnotes,String newdetail ,String newlocation ,String newkmllocation,String newdirectionis,String newlocationis,String newfilename){
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
			File saveFile = new File(FieldinHandData, (filename+".xml"));
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
