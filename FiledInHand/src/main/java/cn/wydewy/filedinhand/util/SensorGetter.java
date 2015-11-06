package cn.wydewy.filedinhand.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by fred on 15/8/3.
 */
public class SensorGetter {


    public SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        float[] gravityValues = new float[3];
        float[] gyroscopeValues = new float[3];
        float[] orientationValues = new float[3];


        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                gravityValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                gyroscopeValues = event.values.clone();
            }

            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null, accelerometerValues,
                    magneticValues);


            SensorManager.getOrientation(R, values);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        ;


    };

}