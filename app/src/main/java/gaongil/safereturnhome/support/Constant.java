package gaongil.safereturnhome.support;

public class Constant {

    public static final String TAG = "WITH_PROJECT";

	/********************************************************************************
	 * 								Network Response Code
	 *********************************************************************************/
	public static final int NETWORK_RESPONSE_CODE_OK = 200;
	public static final int NETWORK_RESPONSE_CODE_CREATION_NEW_DATA = 201;

	/********************************************************************************
	 * 										Intent Key
	 *********************************************************************************/
	public static final String INTENT_GROUP_SELECTED_CONTACTLIST = "selectedContactList";
	
	/********************************************************************************
	 * 										Constant
	 *********************************************************************************/
	public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	//SENDER_ID
    // Project number from the API Console, equals to SENDER_ID
	public static final String PROJECT_ID = "342931063456";
	
	public static final String NETWORK_ROOT_PATH = "http://192.168.0.39:8080";
	//public static final String NETWORK_ROOT_PATH = "http://10.73.43.227:8080";
	public static final int  SPLASH_WAIT_TIME = 1000;
    //private static final String NETWORK_ROOT_PATH = "http://localhost:8080";

    public static final String MESSAGE_PLAY_SERVICES_ERROR = "This device is not supported.";
    public static final float DRAWER_SLIDE_WIDTH_RATE = 0.8f;
	public static final String DATE_FORMAT = "a h:mm";
	public static final int PROFILE_IMAGE_RATE_BY_DEVICE_WIDTH = 4;
	public static final String PROFILE_IMAGE_NAME = "profile";
	
	public static final String BUNDLE_KEY_TIMEPICKER_HOUR = "timepickerHour";
	public static final String BUNDLE_KEY_TIMEPICKER_MINUTE = "timepickerMinute";
	public static final String TIME_ZONE_AM = "AM";
	public static final String TIME_ZONE_PM = "PM";
	public static final String TIME_PICKER = "timePicker";
	public static final int REQUEST_CODE_GROUPTOCONTACT = 1;
	public static final String TEXT_SEPERATOR_COMMA = ", ";
	public static final String IMAGE_EXTENSION = ".png";

    public static final String OTTO_KEY_HOUR = "hour";
    public static final String OTTO_KEY_MINUTE = "minute";
	public static final String BROADCAST_ACTION_TOKEN_GENERATE = "token.new";
}
