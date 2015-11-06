package cn.wydewy.filedinhand.util;

import android.os.Environment;

public class Constant {
	public static final int LEVEL_RATE = 1;
	public static final int COMPASS_RATE = 20;
	public static final int VALUE_RATE = 600;
	public static final int GPS_RATE = 5000;

	public final static float MAX_ROATE_DEGREE = 1.0f;

	public static final String HOST = "http://139.162.25.140:8000";// 一定记得加http://
	public static final String SEND_EMAIL = "/api/send_mail/?format=json";
	public static final String CHECK_CODE = "/api/check_code/?format=json";
	public static final String REGISTER = "/api/register/?format=json";
	public static final String LOGIN = "/api/login/?format=json";
	public static final String LOGOUT = "/api/logout/?format=json&access_token=";
	public static final String USER_INFO = "/api/user_info/?format=json&access_token=";

	public static final String LOGINFO = "filedInHand_logInfo";

	public static final int SEND_EMAIL_OK = 0;
	public static final int SEND_EMAIL_FAILED = 1;

	public static final int CHECK_CODE_OK = 2;
	public static final int CHECK_CODE_FAILED = 3;

	public static final int REGISTER_OK = 4;
	public static final int REGISTER_FAILED = 5;

	public static final int LOGIN_OK = 6;
	public static final int LOGIN_FAILED = 7;

	public static final int LOGOUT_OK = 8;
	public static final int LOGOUT_FAILED = 9;
	
	public static final int USER_INFO_OK = 10;
	public static final int USER_INFO_FAILED = 11;
	
	public static final int SERVER_ERROR = 20;


	public static final String URL_FILED_IN_HAND = Environment.getExternalStorageDirectory()
            + "/FiledInhand";
	public static final String IMAGES = URL_FILED_IN_HAND+"/Images";
}
