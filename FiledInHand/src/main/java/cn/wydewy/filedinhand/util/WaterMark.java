package cn.wydewy.filedinhand.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class WaterMark {
    Drawable picture ;
    String word ="";


    File sdCardDir = Environment.getExternalStorageDirectory();
    String SD=Environment.getExternalStorageDirectory().getPath();
    String  FieldinHand = SD+"/FieldinHand";

    File FieldinHandData = new File(FieldinHand );


    public WaterMark(Drawable newpicture ,String newword){
        picture =newpicture;
        word=newword;
    }





    public static Bitmap mark(Bitmap src, String watermark) {

        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint p = new Paint();
        String familyName = "DroidSans";
        Typeface font = Typeface.create(familyName, Typeface.BOLD);
        p.setColor(Color.BLUE);
        p.setAntiAlias(true);
        p.setUnderlineText(true);
        p.setTypeface(font);
        p.setTextSize(44);

        canvas.drawText(watermark, (w/2-66) , 50, p);

        return result;

    }

    public static Bitmap resizePic(Bitmap source){


        Matrix matrix = new Matrix();
        float size =0.25f;

        matrix.postScale(size,size);
        Bitmap resizeBmp = Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
        return resizeBmp;
    }


    public static Bitmap	getSmallPic(String PhotoPath)	{
        //	Get	the	dimensions of the device
        int	targetW	=	720;
        int	targetH	=	1280;
        //	Get	the	dimensions	of	the	bitmap
        BitmapFactory.Options	bmOptions	=	new	BitmapFactory.Options();
        bmOptions.inJustDecodeBounds	=	true;

        BitmapFactory.decodeFile(PhotoPath, bmOptions);
        int	photoW	=	bmOptions.outWidth;
        int	photoH	=	bmOptions.outHeight;
        //	Determine	how	much	to	scale	down	the	image
        int	scaleFactor	=	Math.max(photoW / targetW, photoH / targetH);
        //	Decode	the	image	file	into	a	Bitmap	sized	to	fill	the	View
        bmOptions.inJustDecodeBounds	=	false;
        bmOptions.inSampleSize	=	scaleFactor;
        bmOptions.inPurgeable	=	true;
        Bitmap	bitmap	=	BitmapFactory.decodeFile(PhotoPath,	bmOptions);
        return bitmap;
    }


    public static Bitmap	getBigPic(String PhotoPath)	{
        //	Get	the	dimensions of the device
        int	targetW	=	720;
        int	targetH	=	1280;
        //	Get	the	dimensions	of	the	bitmap
        BitmapFactory.Options	bmOptions	=	new	BitmapFactory.Options();
        bmOptions.inJustDecodeBounds	=	true;

        BitmapFactory.decodeFile(PhotoPath,	bmOptions);
        int	photoW	=	bmOptions.outWidth;
        int	photoH	=	bmOptions.outHeight;
        //	Determine	how	much	to	scale	down	the	image
        int	scaleFactor	=	Math.min(photoW / targetW, photoH / targetH);
        //	Decode	the	image	file	into	a	Bitmap	sized	to	fill	the	View
        bmOptions.inJustDecodeBounds	=	false;
        bmOptions.inSampleSize	=	scaleFactor;
        bmOptions.inPurgeable	=	true;
        Bitmap	bitmap	=	BitmapFactory.decodeFile(PhotoPath,	bmOptions);
        return bitmap;
    }



    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public  static void write(byte[] bs) throws IOException{
        FileOutputStream out=new FileOutputStream(new File("/sdcard/test.png"));
        out.write(bs);
        out.flush();
        out.close();
    }




}
