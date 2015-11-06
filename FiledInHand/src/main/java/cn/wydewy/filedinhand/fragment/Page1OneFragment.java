package cn.wydewy.filedinhand.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cn.wydewy.filedinhand.FIHApplication;
import cn.wydewy.filedinhand.R;
import cn.wydewy.filedinhand.entity.BackgroundMusic;
import cn.wydewy.filedinhand.entity.Picture;
import cn.wydewy.filedinhand.entity.Point;
import cn.wydewy.filedinhand.util.Constant;
import cn.wydewy.filedinhand.util.LowPass;
import cn.wydewy.vnique.view.CompassView;
import cn.wydewy.vnique.view.LevelplainView;

/**
 * @author weiyideweiyi8 Created by wydewy on 15/9/19.
 */
public class Page1OneFragment extends Fragment implements OnClickListener,
        OnLongClickListener, OnTouchListener, OnCheckedChangeListener {
    private static final int SAVE_PIC_OK = 0;
    private static final int SAVE_PIC_CANCEL = 1;
    // 其他（包括不明的）
    private Handler handler;
    float camera_direction = 0;
    float screen_direction = 0;
    float cdvalue = 0;
    float scvalue = 0;
    float cpvalue = 0;
    float ptvalue = 0;
    float rlvalue = 0;
    protected float trueDirection;
    protected float r;
    protected boolean mdable;
    protected float md;

    private long timeMillis = System.currentTimeMillis();
    // dates
    private TextView lgTV;// 显示经度
    private TextView laTV;// 显示纬度
    private TextView sdisTV;// 显示倾向
    private TextView daisTV;// 记录倾角
    private TextView numTV;// 记录保存的数目
    private TextView clearnumTV;// 数目清零

    private String lgt;// 经度数
    private String lat;// 纬度数
    private String alt;// 海拔数（暂无）
    private int num;

    public String prelg;
    public String prela;
    public String preal;
    // centerView
    private View compassLayout;//整个罗盘
    private CompassView compassV;// 罗盘外周
    private CompassView compass1V;
    private LevelplainView levelplainV;// 罗盘中心
    private TextView compassdirectionTV;// 罗盘角度
    // inputter
    private TextView tipTV;// 提示语句
    private Switch mdSW;// 磁北、真北选择
    private CheckBox calibrationCK;// 检测罗盘
    private EditText pointnameET;// 输入点名
    private EditText notesET;// 输入点信息
    private TextView saveDataTV;// 保存按钮

    // 校准状态和正常状态时候的两个布局
    private LinearLayout viewer;
    private FrameLayout beginner;

    // 传感器相关
    private SensorManager sensorManager;
    protected float mx;
    protected float my;
    protected float mz;
    protected float magnetic_steady_limit;
    protected boolean compass_calibration_needed;
    protected boolean auto_check_calibration;
    protected boolean gable;
    protected float x;
    protected float y;
    protected float z;
    protected float xx;
    protected float yy;
    protected float zz;
    protected float ax;
    protected float ay;
    protected float az;
    protected float gx;
    protected float gz;
    protected float gy;
    protected float grx;
    protected float gry;
    protected float grz;
    protected float degree;
    protected float rotateDegree;
    protected float pitch;
    protected float roll;

    // 罗盘相关
    private boolean compassStop = false;
    private float dipangle = 0;
    private float mDirection = 0f;// （不懂）
    // 定位相关
    private LocationManager locationManager;
    private String provider;
    private AccelerateInterpolator mInterpolator;

    private float mTargetDirection = 0;
    float latitude = 0;
    float longitude = 0;
    float altitude = 0;
    private GeomagneticField geoField;
    private boolean located;
    private String kmllocation = "Not,Located,yet";
    private String position = "Not,Located,yet";

    // 拍照相关
    private SurfaceView surfaceView;// 显示相机的
    private Button takePicBtn;// 照相按钮

    private View cameraCompassLayout;
    private CompassView cameraVCompassV;// 罗盘外周
    private LevelplainView cameraVLevelplainV;// 罗盘中心
    private TextView cameraVCompassdirectionTV;// 罗盘角度

    private ImageView lightImg;// 选择闪光灯模式的按钮
    private TextView lightTV;// 显示闪光灯模式的文字
    private TextView cancelTakePic;// 退出照相按钮
    private TextView whaterTV;// 显示和隐藏水印

    private View cameraV;// 照相机视图
    private TextView takePhotoBtn;// 进入照相文字按钮

    private TextView kuaimen;// 快门文字按钮

    private Camera camera;
    private Camera.Parameters parameters = null;

    private static final int NONE = 0;// 原始
    private static final int DRAG = 1;// 拖动
    private static final int ZOOM = 2;// 放大
    private static final CharSequence LIGHT_MODE_OFF = "off";
    private static final CharSequence LIGHT_MODE_ON = "on";
    private static final CharSequence LIGHT_MODE_AUTO = "auto";

    private int mStatus = NONE;
    private int MAX_ZOOM_VALUE = 10;// 虚伪常量，可以设置
    private static final int MAX_QUALITY_VALUE = 200;
    private int MIN_QUALITY_VALUE = 100;// 虚伪常量，可以设置
    private float mStartDistance = 0f;

    private int lightMode;
    private int zoomValue;// 记录焦距
    private int currentCamera;// 记录当前相机
    private int currentCameraQuality = 100;// 记录当前照片质量
    private LinearLayout layout;
    private boolean whaterShow = true;

    private int scwidth;
    private int scheight;
    private boolean kuaimenSlected;


    private FIHApplication application;
    //数据
    /**
     * 点名
     */
    private String name;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.page1_fragment1,
                container, false);
        initDates();
        initView();
        initEvent();
        return layout;
    }

    private void initDates() {
        // TODO Auto-generated method stub
        application = (FIHApplication) getActivity().getApplication();
    }

    /**
     * 事件初始化
     */
    private void initEvent() {
        // TODO Auto-generated method stub
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SAVE_PIC_OK:
                        Toast.makeText(getActivity(),"图片保存成功",Toast.LENGTH_LONG).show();
                        break;
                    case SAVE_PIC_CANCEL:
                        Toast.makeText(getActivity(),"图片没有保存",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        mInterpolator = new AccelerateInterpolator();
        // 罗盘相关
        compassV.setOnLongClickListener(this);
        compassV.setOnClickListener(this);
        compass1V.setOnClickListener(this);
        compassLayout.setOnClickListener(this);
        changeCompassMode(0);
        // 输入数据保存数据相关
        clearnumTV.setOnClickListener(this);
        saveDataTV.setOnClickListener(this);
        mdSW.setOnCheckedChangeListener(this);

        // 照相相关
        takePhotoBtn.setOnClickListener(this);
        cancelTakePic.setOnClickListener(this);
        cameraV.setVisibility(View.GONE);
        surfaceView.setOnTouchListener(this);
        surfaceView.setOnClickListener(this);
        lightImg.setOnClickListener(this);
        whaterTV.setOnClickListener(this);
        kuaimen.setOnClickListener(this);
        // 传感器、gps相关
        locationlisten();
        sensorlisten();
    }

    /**
     * 视图初始化
     */
    private void initView() {
        // TODO Auto-generated method stub

        // 两个布局
        beginner = (FrameLayout) layout.findViewById(R.id.beginner);
        viewer = (LinearLayout) layout.findViewById(R.id.viewer);

        // dates
        lgTV = (TextView) layout.findViewById(R.id.lgTV);
        laTV = (TextView) layout.findViewById(R.id.laTV);
        sdisTV = (TextView) layout.findViewById(R.id.sdisTV);
        daisTV = (TextView) layout.findViewById(R.id.daisTV);
        numTV = (TextView) layout.findViewById(R.id.numTV);
        clearnumTV = (TextView) layout.findViewById(R.id.clearnumTV);
        // centerView
        compassV = (CompassView) layout.findViewById(R.id.compassV);// 罗盘外周
        compass1V = (CompassView) layout.findViewById(R.id.compass1V);// 罗盘外周
        compassLayout = layout.findViewById(R.id.compassLayout);//整个罗盘
        compassLayout.requestFocus();
        levelplainV = (LevelplainView) layout.findViewById(R.id.levelplainV);// 罗盘中心
        compassdirectionTV = (TextView) layout
                .findViewById(R.id.compassdirectionTV);// 罗盘角度

        cameraVCompassV = (CompassView) layout
                .findViewById(R.id.cameraV_compassV);// 相机罗盘外周

        cameraVLevelplainV = (LevelplainView) layout
                .findViewById(R.id.cameraV_levelplainV);// 相机罗盘中心
        cameraVCompassdirectionTV = (TextView) layout
                .findViewById(R.id.cameraV_compassdirectionTV);// 相机罗盘角度

        // inputter
        mdSW = (Switch) layout.findViewById(R.id.mdSW);// 磁北、真北选择
        calibrationCK = (CheckBox) layout.findViewById(R.id.calibrationCK);// 检测罗盘
        pointnameET = (EditText) layout.findViewById(R.id.pointnameET);// 输入点名
        pointnameET.clearFocus();
        notesET = (EditText) layout.findViewById(R.id.notesET);// 输入点信息
        notesET.clearFocus();
        saveDataTV = (TextView) layout.findViewById(R.id.saveDataTV);// 保存按钮

        // 拍照

        takePhotoBtn = (TextView) layout.findViewById(R.id.photoTV);
        cameraCompassLayout = layout.findViewById(R.id.cameraV_compassLayout);
        surfaceView = (SurfaceView) layout.findViewById(R.id.surfaceView);
        cancelTakePic = (TextView) layout.findViewById(R.id.cancelTakePic);
        cameraV = (RelativeLayout) layout.findViewById(R.id.cameraV);
        takePicBtn = (Button) layout.findViewById(R.id.takePicBtn);
        lightImg = (ImageView) layout.findViewById(R.id.lightImg);
        lightTV = (TextView) layout.findViewById(R.id.lightTV);
        whaterTV = (TextView) layout.findViewById(R.id.whaterTV);
        kuaimen = (TextView) layout.findViewById(R.id.kuaimenTV);
    }

    private void initCamera() {
        // 初始化视图时候不能直接初始化surfaceView,因为它为GONE
        surfaceView.getHolder()
                .setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144); // 设置Surface分辨率
        surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
        surfaceView.getHolder().addCallback(new SurfaceCallback());// 为SurfaceView的句柄添加一个回调函数

        takePicBtn.setOnClickListener(this);

        if (kuaimenSlected) {
            kuaimen.setText("关快门");
        } else {
            kuaimen.setText("开快门");
        }
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (application.currentPage == 1) {
            if (compassStop) {
                Toast.makeText(getActivity(),
                        getActivity().getResources().getString(R.string.tip2),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),
                        getActivity().getResources().getString(R.string.tip1),
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.clearnumTV:
                if ("".equals(name)) {
                    Toast.makeText(getActivity(), "还未有数据~", Toast.LENGTH_SHORT).show();
                } else {
                    if (name.equals(pointnameET.getText().toString())) {
                        Toast.makeText(getActivity(), "清除次数", Toast.LENGTH_SHORT).show();
                        num = 0;
                        numTV.setText(getActivity().getResources()
                                .getString(R.string.numis) + ":" + num);
                    } else {
                        Toast.makeText(getActivity(), "该点还未记录~~", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.saveDataTV:
                if (saveData()) {
                    Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                    num += 1;
                    numTV.setText(getActivity().getResources()
                            .getString(R.string.numis) + ":" + num);
                }
                break;
            case R.id.compassV:
                controlCompass();
                break;
            case R.id.compass1V:
                controlCompass();
                break;
            case R.id.compassLayout:
                controlCompass();
            case R.id.cameraV_compassV:
                break;
            case R.id.photoTV:
                enterCameraV();
                break;
            case R.id.takePicBtn:
                takePhoto();
                break;
            case R.id.cancelTakePic:
                viewer.setVisibility(View.VISIBLE);
                cameraV.setVisibility(View.GONE);
                break;
            case R.id.lightImg:
                changeLightMode();
                break;
            case R.id.whaterTV:
                if (whaterShow) {
                    cameraCompassLayout.setVisibility(View.GONE);
                    whaterTV.setText("显示水印");
                    whaterShow = false;
                } else {
                    cameraCompassLayout.setVisibility(View.VISIBLE);
                    whaterTV.setText("隐藏水印");
                    whaterShow = true;
                }
                break;
            case R.id.kuaimenTV:
                if (kuaimenSlected) {
                    kuaimen.setText("开快门");
                    kuaimenSlected = false;
                } else {
                    kuaimen.setText("关快门");
                    kuaimenSlected = true;
                }
                break;
        }
    }

    private void controlCompass() {
        compassStop = !compassStop;
        if (compassStop) {
            Toast.makeText(getActivity(), "长按罗盘进入拍照记录", Toast.LENGTH_SHORT)
                    .show();
        }
        handler.postDelayed(mCompassViewUpdater, Constant.COMPASS_RATE);
        handler.postDelayed(statusUpdater, Constant.VALUE_RATE);
    }

    private boolean saveData() {
        name = pointnameET.getText().toString();
        String notes = notesET.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(getActivity(), "请输入点名！", Toast.LENGTH_LONG).show();
            return false;
        }
        String detail = (getResources().getString(R.string.cdis)) + getdirectionString(cdvalue + md) + "\n" + (getResources().getString(R.string.scis))
                + getdirectionString(scvalue + md) + "\n" + (getResources().getString(R.string.mdis))
                + getdirectionString(md) + "\n" + (getResources().getString(R.string.dais))
                + getdirectionString(dipangle) + "\n" + (getResources().getString(R.string.ptis))
                + getdirectionString(ptvalue) + "\n" + (getResources().getString(R.string.rlis))
                + getdirectionString(rlvalue) + "\n";
        String location = position;
//		String kmllocation = kmllocation;
//		String filename;
        Point point = new Point();
        point.setName(name);
        point.setNotes(notes);
        point.setDetail(detail);
        point.setLocation(location);
        point.setKmllocation(kmllocation);

        return point.save();
    }

    private void changeLightMode() {
        // TODO Auto-generated method stub
        if (lightMode < 2) {
            lightMode += 1;
        } else {
            lightMode = 0;
        }
        switch (lightMode) {
            case 0:
                lightTV.setText(LIGHT_MODE_OFF);
                Toast.makeText(getActivity(), "闪光灯关闭", Toast.LENGTH_SHORT).show();
                setLightOff();
                break;

            case 1:
                lightTV.setText(LIGHT_MODE_ON);
                Toast.makeText(getActivity(), "闪光灯打开", Toast.LENGTH_SHORT).show();
                setLightOn();
                break;
            case 2:
                lightTV.setText(LIGHT_MODE_AUTO);
                Toast.makeText(getActivity(), "闪光灯自动模式", Toast.LENGTH_SHORT).show();
                setLightAuto();
                break;
        }
    }

    /**
     * 长按事件
     */
    @Override
    public boolean onLongClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.compassV:
                enterCameraV();
                break;

            default:
                break;
        }
        return true;
    }

    public void enterCameraV() {
        initCamera();
        viewer.setVisibility(View.GONE);
        cameraV.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "切换到相机", Toast.LENGTH_SHORT).show();
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mStatus = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                float distance = spacing(event);
                if (distance > 10f) {
                    mStatus = ZOOM;
                    mStartDistance = distance;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (mStatus == DRAG) {
                    // dragAction(event);
                } else if (mStatus == ZOOM) {

                    if (event.getPointerCount() == 1)
                        return true;
                    zoomAcition(event);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mStatus = NONE;
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 放大（改变焦点）
     *
     * @param event
     */
    private void zoomAcition(MotionEvent event) {
        // TODO Auto-generated method stub

        float newDist = spacing(event);
        float scale = newDist / mStartDistance;
        mStartDistance = newDist;
        if (scale > 1) {
            if (zoomValue < MAX_ZOOM_VALUE) {
                zoomValue += 1;
            }
        } else {
            if (zoomValue > 0) {
                zoomValue -= 1;
            }
        }
        if (currentCameraQuality <= MAX_QUALITY_VALUE) {
            currentCameraQuality = MIN_QUALITY_VALUE + 3 * zoomValue;
        }
        parameters.setJpegQuality(currentCameraQuality); // 设置照片质量
        parameters.setZoom(zoomValue);
        camera.setParameters(parameters);
    }

    // 传感器相关

    /**
     * 传感器监听 wydewy
     */
    public void sensorlisten() {
        sensorManager = (SensorManager) getActivity().getSystemService(
                Context.SENSOR_SERVICE);

        Sensor magneticSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gravitySensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor gyroscopeSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(listener, magneticSensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, gravitySensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_GAME);

        if (gravitySensor == null) {
            Toast toast = Toast.makeText(getActivity(), getResources()
                    .getString(R.string.nogravity), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    /**
     * 检测传感器状态变化的监听器
     */
    public SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        float[] gravityValues = new float[3];
        float[] gyroscopeValues = new float[3];

        float[] TempValues = new float[3];
        float[] SwapValues = new float[3];

        public void onSensorCreated(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values.clone();

            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                magneticValues = event.values.clone();

            } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {

                gravityValues = event.values.clone();

                gable = true;

            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                gyroscopeValues = event.values.clone();
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                TempValues = event.values.clone();

                accelerometerValues = LowPass.filter(accelerometerValues,
                        TempValues);

            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                TempValues = event.values.clone();

                SwapValues = magneticValues;

                magneticValues = LowPass.filter(magneticValues, TempValues);

                float tmx = TempValues[0];
                float tmy = TempValues[1];
                float tmz = TempValues[2];
                // Log.e("located mdview", "tmx"+tmx+ " tmy"+tmy + " tmz"
                // +tmz+" ");

                mx = SwapValues[0];
                my = SwapValues[1];
                mz = SwapValues[2];
                // Log.e("located mdview", "mx"+mx+ " my"+my + " mz" +mz+" ");

                float dx = Math.abs(mx - tmx);
                float dy = Math.abs(my - tmy);
                float dz = Math.abs(mz - tmz);

                float temp = 0;
                float max = 0;

                if (temp <= dx) {
                    temp = dx;
                }
                if (temp <= dy) {
                    temp = dy;
                }
                if (temp <= dz) {
                    temp = dz;
                }
                if (magnetic_steady_limit == 0 || magnetic_steady_limit >= temp) {
                    magnetic_steady_limit = temp;
                }
                if (max <= temp) {
                    max = temp;
                }

                // If the sensor data is unreliable return
                if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    compass_calibration_needed = true;
                }
                if (event.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    compass_calibration_needed = false;
                }

                if (auto_check_calibration) {
                    if (compass_calibration_needed) {

                        compass_calibration();

                    } else {
                        finish_compass_calibration();
                    }
                }

                // Log.e("located mdview",
                // "magnetic_steady_limit "+magnetic_steady_limit+" max" +max);

            } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {

                gravityValues = event.values.clone();

                gable = true;

            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                gyroscopeValues = event.values.clone();
            }

            float[] R = new float[9];
            float[] values = new float[3];

            SensorManager.getRotationMatrix(R, null, accelerometerValues,
                    magneticValues);

            SensorManager.getOrientation(R, values);

            x = (float) Math.toDegrees(values[0]);
            y = (float) Math.toDegrees(values[1]);
            z = (float) Math.toDegrees(values[2]);

            xx = values[0];
            yy = values[1];
            zz = values[2];

            // Log.e("located mdview", "x"+x+ " y"+y + " z" +z+" ");

            ax = accelerometerValues[0];
            ay = accelerometerValues[1];
            az = accelerometerValues[2];
            // Log.e("located mdview", "ax"+ax+ " ay"+ay + " az" +az+" ");

            mx = magneticValues[0];
            my = magneticValues[1];
            mz = magneticValues[2];
            // Log.e("located mdview", "mx"+mx+ " my"+my + " mz" +mz+" ");

            gx = gravityValues[0];
            gy = gravityValues[1];
            gz = gravityValues[2];

            // Log.e("located mdview", "gx"+gx+ " gy"+gy + " gz" +gz+" ");

            grx = gyroscopeValues[0];
            gry = gyroscopeValues[1];
            grz = gyroscopeValues[2];

            degree = event.values[0];
            rotateDegree = -(float) Math.toDegrees(values[0]);

            pitch = -y;

            // if(gz>=0){
            // pitch = -y;
            // }
            // if(gz<0){
            // pitch=180+y;
            // }

            roll = -z;

            // if(gz<=0){
            // if(roll<0){
            // roll=-(180+roll);
            // }else if(roll>0){
            // roll=180-roll;
            // }
            // }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };

    /**
     * 罗盘校正
     */
    private void compass_calibration() {
        viewer.setVisibility(View.GONE);
        beginner.setVisibility(View.VISIBLE);
    }

    /**
     * 罗盘校正完成
     */
    private void finish_compass_calibration() {
        viewer.setVisibility(View.VISIBLE);
        viewer.setVisibility(View.GONE);
    }

    /**
     * 数据的刷新线程
     */
    protected Runnable statusUpdater = new Runnable() {
        @Override
        public void run() {

            if (lgt == null || prelg == null) {
                laTV.setText(getActivity().getResources().getString(
                        R.string.lais));
            } else {
                lgTV.setText(lgt + prelg);
            }
            if (lat == null || prela == null) {
                laTV.setText(getActivity().getResources().getString(
                        R.string.lgis));
            } else {
                laTV.setText(lat + prela);
            }

            // al.setText(alt);
            Log.i("TAG", "lgt:" + lgt + "prelg:" + prelg);
            Log.i("TAG", "lat:" + lat + "prela:" + prela);

            Log.i("TAG", "position:" + position);

            trueDirection = (float) (x + Math.toDegrees(Math.atan(Math.sin(Math
                    .toRadians(roll)) * Math.tan(Math.toRadians(90 - pitch)))));

            if (trueDirection > 360) {
                trueDirection = trueDirection - 360;
            } else if (trueDirection < 0) {
                trueDirection = trueDirection + 360;
            }

            if (Math.abs(gx) <= gy) {
                r = x;
            } else if (Math.abs(gx) <= -gy) {
                r = x + 180;
            } else if (Math.abs(gy) < gx) {
                r = x + 90;
            } else if (Math.abs(gy) < -gx) {
                r = x + 270;
            }
            if (gz < 0) {
                r = r + 180;
            }

            camera_direction = r;
            screen_direction = r + 180;

            if (camera_direction > 360) {
                camera_direction = camera_direction - 360;
            } else if (camera_direction < 0) {
                camera_direction = camera_direction + 360;
            }
            if (screen_direction > 360) {
                screen_direction = screen_direction - 360;
            } else if (screen_direction < 0) {
                screen_direction = screen_direction + 360;
            }
            cdvalue = camera_direction;

            ptvalue = pitch;
            rlvalue = roll;
            cpvalue = x;
            scvalue = screen_direction;

            float cmpv = (float) (Math.floor(x + 0.5));

            if (mdable) {
                cmpv += md;
            }

            float diptmp = (float) Math.asin(gz
                    / Math.sqrt(gx * gx + gy * gy + gz * gz));

            dipangle = (float) (90 - Math.toDegrees(diptmp));

            if (cmpv < 0) {
                cmpv += 360;
            }

            rotatecompass(cmpv);

            if (scvalue >= 360) {
                scvalue -= 360;
            }
            float v = (float) Math.floor(scvalue + 0.5);
            //
            int sdv = (int) (v);

            String cmpt = String.valueOf((int) Math.floor(cmpv + 0.5))
                    + getString(R.string.du);

            String sdt = String.valueOf(sdv) + getString(R.string.du);

            String dipt = getdirectionString(dipangle);

            if ((Math.abs(y) - Math.abs(z)) <= 2
                    && (Math.abs(y) + Math.abs(z)) <= 5) {
                sdt = "Up";
            }

            if (!compassStop) {

                sdisTV.setText("倾向:" + sdt);// 显示倾向
                daisTV.setText("倾角:" + dipt);// 显示倾角
                compassdirectionTV.setText(cmpt);// 显示罗盘角度（在罗盘中下部分）
                cameraVCompassdirectionTV.setText(cmpt);// 显示相机罗盘角度（在罗盘中下部分）
                handler.postDelayed(statusUpdater, Constant.VALUE_RATE);
            }
        }
    };

    // 定位相关

    /**
     * 定位服务监听 wydewy
     */
    public void locationlisten() {
        locationManager = (LocationManager) getActivity().getSystemService(
                Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;

        } else {

            located = false;
            String notlocated = getResources().getString(R.string.location);

            Toast toast = Toast.makeText(getActivity(), notlocated,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            located = true;
            showLocation(location);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                Constant.GPS_RATE, 1, locationListener);
        try {
            position = showLocation(location);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {

            showLocation(location);
        }
    };

    /**
     * 展示经纬度，在locationListener中调用
     *
     * @param location
     * @return
     */
    private String showLocation(Location location) {
        latitude = (float) location.getLatitude();
        longitude = (float) location.getLongitude();
        altitude = (float) location.getAltitude();

        geoField = new GeomagneticField(latitude, longitude, altitude,
                timeMillis);

        if (located) {
            md = geoField.getDeclination();// 磁偏角
        }
        Log.e("magnetic declination", md + " md");

        java.text.DecimalFormat df = new java.text.DecimalFormat("####.##");

        if (location.getLongitude() >= 0) {
            prelg = "E";
        } else {
            prelg = "W";
        }

        if (location.getLatitude() >= 0) {
            prela = "N";
        } else {
            prela = "S";
        }

        lgt = getlocationString((float) location.getLongitude());
        lat = getlocationString((float) location.getLatitude());

        alt = df.format((float) location.getAltitude()) + "米";

        kmllocation = lgt + "\n" + lat + "\n" + alt + "\n";

        String result = location.getLongitude() + "," + location.getLatitude()
                + "," + location.getAltitude();
        return result;
    }

    // 罗盘相关

    /**
     * 罗盘旋转
     *
     * @param angle
     */
    protected void rotatecompass(float angle) {
        float direction = (float) (angle * -1.0f);
        mTargetDirection = (float) normalizeDegree(direction);
    }

    /**
     * 罗盘中心的刷新线程
     */
    protected Runnable levelplainUpdater = new Runnable() {
        @Override
        public void run() {
            levelplainV.updatepoint(gx, gy, gz);
            cameraVLevelplainV.updatepoint(gx, gy, gz);
            handler.postDelayed(levelplainUpdater, Constant.LEVEL_RATE);
        }
    };

    /**
     * 罗盘旋转状态更新线程
     */
    protected Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            if (compassV != null && !compassStop) {
                if (mDirection != mTargetDirection) {

                    // calculate the short routine
                    float to = mTargetDirection;
                    if (to - mDirection > 180) {
                        to -= 360;
                    } else if (to - mDirection < -180) {
                        to += 360;
                    }

                    // limit the max speed to MAX_ROTATE_DEGREE
                    float distance = to - mDirection;
                    if (Math.abs(distance) > Constant.MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? Constant.MAX_ROATE_DEGREE
                                : (-1.0f * Constant.MAX_ROATE_DEGREE);
                    }

                    // need to slow down if the distance is short
                    mDirection = (float) normalizeDegree(mDirection
                            + ((to - mDirection) * mInterpolator
                            .getInterpolation(Math.abs(distance) > Constant.MAX_ROATE_DEGREE ? 0.4f
                                    : 0.3f)));

                    compassV.updateDirection(mDirection);// 设置罗盘方向

                    levelplainV.updatepoint(gx, gy, gz);// 设置罗盘点

                    cameraVCompassV.updateDirection(mDirection);// 设置相机罗盘方向

                    cameraVLevelplainV.updatepoint(gx, gy, gz);// 设置相机罗盘点
                }

                handler.postDelayed(mCompassViewUpdater, Constant.COMPASS_RATE);
            }

        }

    };

    // 数据处理相关
    private float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    private String getlocationString(float input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        int miao = (((int) ((input - du) * 3600))) % 60;
        return String.valueOf(du) + getString(R.string.du)
                + String.valueOf(fen) + getString(R.string.fen)
                + String.valueOf(miao) + getString(R.string.miao);
    }

    private String getdirectionString(float input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        return String.valueOf(du) + getString(R.string.du)
                + String.valueOf(fen) + getString(R.string.fen);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    public void changeCompassMode(int mode) {
        Toast.makeText(getActivity(), "changeCompassMode" + mode, Toast.LENGTH_LONG).show();
        if (mode == 0) {
//			Toast.makeText(getActivity(),mode+"",Toast.LENGTH_LONG).show();
            compassV.setVisibility(View.VISIBLE);
            compass1V.setVisibility(View.GONE);
        } else if (mode == 1) {
//			Toast.makeText(getActivity(),mode+"",Toast.LENGTH_LONG).show();
            compass1V.setVisibility(View.VISIBLE);
            compassV.setVisibility(View.GONE);
        }
    }

    // 数据结尾

    // 拍照相关
    private class SurfaceCallback implements Callback {

        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            if (camera != null) {
                parameters = camera.getParameters(); // 获取各项参数
                parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
                parameters.setPreviewSize(width, height); // 设置预览大小
                parameters.setPreviewFrameRate(5); // 设置每秒显示4帧

                WindowManager wm = (WindowManager) getActivity()
                        .getSystemService(Context.WINDOW_SERVICE);

                scwidth = wm.getDefaultDisplay().getWidth();
                scheight = wm.getDefaultDisplay().getHeight();

                parameters.setPictureSize(scwidth, scheight); // 设置保存的图片尺寸

                parameters.setJpegQuality(currentCameraQuality); // 设置照片质量
                parameters.set("orientation", "portrait");
                parameters.set("rotation", 90);
            }

        }

        // 开始拍照时调用该方法
        @SuppressLint("NewApi")
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(); // 打开摄像头
                camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
                camera.setDisplayOrientation(getPreviewDegree(getActivity()));
                camera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 停止拍照时调用该方法
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.stopPreview();
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }

    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        // TODO Auto-generated method stub

        if (camera != null) {
            // 拍照
            if (kuaimenSlected) {
                camera.takePicture(new MyShutterCallback(), null,
                        new MyPictureCallback());// 将拍摄到的照片给自定义的对象
            } else {
                camera.takePicture(null, null, new MyPictureCallback());// 将拍摄到的照片给自定义的对象
            }

        }
    }

    /*
     * 拍照后保存图片时候的回调函数
     */
    private final class MyPictureCallback implements PictureCallback {
        private boolean isSaved;
        private Bitmap bm;
        @Override
        public void onPictureTaken(final byte[] data, final Camera camera) {
             bm = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            if (bm != null) {
                if (bm.getHeight() < bm.getWidth()) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(90);
                    synchronized (this){
                        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                    }

                }
            }

            if (whaterShow) {
                Bitmap whater = zoomImage(
                        createViewBitmap(cameraCompassLayout),
                        bm.getWidth() / 2, bm.getWidth() / 2);
                if(bm!=null){
                    bm = createWaterMarkBitmap(bm, whater, "");
                }else{
                    return;
                }
            }
            final Context context = getActivity();
            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle("保存图片")
                    .setMessage("是否保存图片")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            LayoutInflater factory = LayoutInflater.from(context);
                            final View DialogView = factory.inflate(
                                    R.layout.dialog, null);
                            final EditText pointnameET = (EditText) DialogView
                                    .findViewById(R.id.pointName);

                            AlertDialog dlg = new AlertDialog.Builder(context)
                                    .setTitle("输入点名")
                                    .setView(DialogView)
                                    .setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int whichButton) {
                                                    final ProgressDialog m_Dialog = ProgressDialog
                                                            .show(context,
                                                                    "请等待...",
                                                                    "正在为你保存图片...",
                                                                    true);
                                                    final String pointname = pointnameET.getText().toString();
                                                    if("".equals(pointname)){
                                                        Toast.makeText(context,"没有输入点名，请随后添加",Toast.LENGTH_LONG).show();
                                                    }
                                                    m_Dialog.show();


                                                    new Thread() {
                                                        public void run() {
                                                            try {
                                                                //保存图片
                                                                if(bm!=null){
                                                                    saveFile(bm,pointname);
                                                                    isSaved=true;
                                                                    handler.sendMessage(handler.obtainMessage(SAVE_PIC_OK));
                                                                }
                                                                camera.startPreview();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            } finally {
                                                                m_Dialog.dismiss();
                                                                camera.startPreview();
                                                            }
                                                        }
                                                    }.start();
                                                }
                                            })
                                    .setNegativeButton("取消",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int whichButton) {
                                                    // context.finish();
                                                    handler.sendMessage(handler.obtainMessage(SAVE_PIC_CANCEL));
                                                    camera.startPreview();
                                                }
                                            }).create();
                            dlg.show();
                        }
                    })
                    .setNeutralButton("退出", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // context.finish();
                            Log.i("TAG", "图片未保存取消");
                            handler.sendMessage(handler.obtainMessage(SAVE_PIC_CANCEL));
                            camera.startPreview();
                        }
                    }).create();
            dialog.show();
            camera.startPreview();

        }
    }

    /*
     * 拍照后保存图片时候的回调函数
     */
    private final class MyShutterCallback implements ShutterCallback {

        @Override
        public void onShutter() {
            if (kuaimenSlected) {
                new BackgroundMusic(getActivity()).playBackgroundMusic(
                        "camera_click.ogg", false);
            }

        }

    }

    /**
     * 进行添加水印图片和文字
     *
     * @param src
     * @param waterMak
     * @return
     */
    public  Bitmap createWaterMarkBitmap(Bitmap src, Bitmap waterMak, String title) {
        if (src == null) {
            return src;
        }
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = waterMak.getWidth();
        int wh = waterMak.getHeight();
        Log.i("jiangqq", "w = " + w + ",h = " + h + ",ww = " + ww + ",wh = "
                + wh);
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);
        // 在src的右下角添加水印
        Paint paint = new Paint();
        // paint.setAlpha(100);
        mCanvas.drawBitmap(waterMak, 0, 0, paint);
        // 开始加入文字
        if (null != title) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.RED);
            textPaint.setTextSize(16);
            String familyName = "宋体";
            Typeface typeface = Typeface.create(familyName,
                    Typeface.BOLD_ITALIC);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Align.CENTER);
            mCanvas.drawText(title, w / 2, 25, textPaint);

        }
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.restore();
        return newBitmap;
    }

    /*
     * 将View转化成Bitmap图像
     */
    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        int width = bgimage.getWidth();
        int height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 自动闪光灯
     */
    private void setLightAuto() {
        if (null == camera) {
            camera = Camera.open();
        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        camera.setParameters(parameters);
    }

    /**
     * 开闪光灯
     */
    private void setLightOn() {
        if (null == camera) {
            camera = Camera.open();
        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }

    /**
     * 关闭闪光灯
     */
    private void setLightOff() {
        if (null == camera) {
            camera = Camera.open();
        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }

    /**
     * 切换摄像头
     */
    private void changeCamera() {
        if (camera != null) {
            camera.release(); // 释放照相机
            camera = null;
        }

        if (currentCamera == 1) {
            camera = Camera.open(2);
        } else {
            camera = Camera.open(1);
        }
    }

    /**
     * 将拍下来的照片存放在SD卡中
     *
     * @param data
     * @throws IOException
     */
    public static void saveToSDCard(byte[] data) throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
        String filename = format.format(date) + ".jpg";
        File fileFolder = new File(Environment.getExternalStorageDirectory()
                + "/FiledInhand/Images");
        if (!fileFolder.exists()) { // 如果目录不存在，则创建一个目录
            fileFolder.mkdir();
        }
        File jpgFile = new File(fileFolder, filename);
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
        outputStream.write(data); // 写入sd卡中
        outputStream.close(); // 关闭输出流
    }


    /**
     * 把图片村保存在相应的文件当中
     * @param pBitmap
     * @param name//点名
     */
    public static void saveFile(Bitmap pBitmap,String name) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
        String filename = name+"_"+format.format(date) + ".jpg";
        String dir = Environment.getExternalStorageDirectory()
                + "/FiledInhand/Images/"+name+"/";
        Picture picture = new Picture();
        picture.setPointName(name);
        picture.setDir(dir);
        picture.save();


        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePathName = file.getAbsolutePath() + "/" + filename;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePathName);
            pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            Log.i("jiangqq", "保存图片到sdcard卡成功.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    pBitmap.recycle();
                    pBitmap = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Activity恢复相关
    public void onPause() {
        super.onPause();
        compassStop = true;
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    public void onResume() {
        super.onResume();

        if (sensorManager != null) {
            sensorlisten();
        }
        handler.postDelayed(mCompassViewUpdater, Constant.COMPASS_RATE);
        handler.postDelayed(statusUpdater, Constant.VALUE_RATE);
    }

    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        switch (buttonView.getId()) {
            case R.id.mdSW:
                if (isChecked) {

                    mdable = true;

                    Toast toast = Toast.makeText(getActivity(), getResources()
                                    .getString(R.string.true_north)
                                    + ""
                                    + getResources().getString(R.string.mdis) + md,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    mdable = false;
                    Toast toast = Toast.makeText(getActivity(), getResources()
                            .getString(R.string.mag_north), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                break;

            case R.id.calibrationCK:
                auto_check_calibration = isChecked;
                break;
        }
    }

}
