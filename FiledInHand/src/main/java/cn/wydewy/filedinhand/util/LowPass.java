package cn.wydewy.filedinhand.util;

/**
 * Created by fred on 15/7/27.
 */
public class LowPass {



    public static final float ALPHA = 0.0001f;


    public static float[] filter( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }


}
