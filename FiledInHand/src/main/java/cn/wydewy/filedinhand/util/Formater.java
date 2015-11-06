package cn.wydewy.filedinhand.util;

import cn.wydewy.filedinhand.FIHApplication;
import cn.wydewy.filedinhand.R;

/**
 * Created by freddy on 15/8/4.
 */
public class Formater {



    public float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    public String getlocationString(float input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        int miao = (((int) ((input - du) * 3600))) % 60;
        return String.valueOf(du) + FIHApplication.getInstance().getString(R.string.du) + String.valueOf(fen) + FIHApplication.getInstance().getString(R.string.fen)
                + String.valueOf(miao) + FIHApplication.getInstance().getString(R.string.miao);
    }


    public String getdirectionString(float input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        return String.valueOf(du) + FIHApplication.getInstance().getString(R.string.du) + String.valueOf(fen) + FIHApplication.getInstance().getString(R.string.fen);
    }

}
