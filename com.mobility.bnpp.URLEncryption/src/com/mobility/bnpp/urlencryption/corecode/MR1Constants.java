package com.mobility.bnpp.urlencryption.corecode;

/**
 * This class contains all the MR1 constants
 * 
 */
public class MR1Constants {

	public static final String MR1_TAG = "BNPP -- MR1";
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;

	public static final String ENGLISH = "English";
	public static final String FRENCH = "Francais";
	public static final String GERMAN = "Deustch";
	public static final String DUTCH = "Nederlands";

	public static final String SIM_CONNECTION_ERR = "SIM_CONNECTION_ERR";
	public static final String TRY_AGAIN_MSG="Please try again......";
	public static final int NO_AUTHMEAN_AVAILABLE = 1200;
	public static boolean IS_REACHED_MAINSCREEN=false;

	/**
	 * ContactUs constants
	 * 
	 * @author Jinal Tandel
	 * 
	 */
	// contact us start

	// constants for method type
	public static final int CONTACT_US_CCB = 501;
	public static final int CONTACT_US_CALLBACK = 502;
	public static final int CONTACT_US_SEND_MESSAGE = 503;
	public static final int CONTACT_US_MAKE_APPOINTMENT = 504;
	public static final int CONTACT_US_TWITTER = 505;

	// ccb Phone number
	public static final String CONTACT_US_CCB_NUM = "tel:003227626000";

	// constants for value passing between activities
	public static final String CONTACT_US_KEY_METHOD = "methods";

	// user state
	public static final String AUTHENTICATED_USER = "3";
	public static final String IDENTIFIED_USER = "2";
	public static final String NON_IDENTIFIED_USER = "1";

	// contact us popup size
	public static final int CONTACT_US_POPUP_SIZE = 530;
	public static final int CONTACT_US_PADDING = 10;
	public static final int CONTACT_US_ANIM_TIME = 200;
	


	//parameters to be sent in url
	public static final String WEB_MODEL = "webModel";
	

	//distinguis between webview type for common WebViewActivity
	public static final int CONTACT_US = 001;
	public static final int CLIENT_ENROLLMENT = 002;
	public static final int PRDCT_COST_COMP = 003;
	public static final int HELLO_PREPAID_CARD_PRDCT = 004;
	public static final int HELLO_CREDIT_CARD_PRDCT = 005;   //added by pallavi
	public static final int ONLINE_DEMAND_OF_PRODUCTS = 006;
	public static final int IN_APP_DEMO = 007;
	public static final int HELLO_DIRECT_RESERVE = 8;
	public static final int HELLO_Consumer_Loan = 9; 
	public static final int SIMPLE = 10; 
	public static final int ADVANTAGEOUS = 11; 
	
	//for twitter modal view
	public static final String IS_TWITTER = "twitter";
	
	// contact us end


	/**
	 * Client Enrolment -- Yuga Pradha
	 */

	public static final String CE_URL="CE_URL";
	public static final int LAN_NONE = -1;
	public static final int LAN_NL = 3;// DUTCH
	public static final int LAN_EN = 1;// ENGLISH
	public static final int LAN_FR = 2;// FRENCH
	public static final int LAN_DE = 0;// GERMAN
	/**************************************************************************************
	 * For Contracting Constants
	 * 
	 * @author 558254 / K.sundaramanikandan
	 **************************************************************************************/
	////////////SUBASINI - for password Strength indicator//////////////
		public static final int NEW_PASSWORD_SETTINGS=900;
		public static final int UPDATE_MIB_CONTRACT_SETTINGS=901;
		public static final int LOGIN_FLOW = 002;
		public static final int SETTING_FLOW = 001;
		public static final int  CONTRACTING_FLOW = 003 ;
		public static final int PRODUCT_ENROL_FLOW = 004 ;
		public static final int SECOND_TIME_UCR = 005;
		public static final int THOUSAND_FIVE_HUNDRED = 1500;
		public static final String CUSTOMER_NUMBER = "999999";
		public static final String CALL_MODE = "8";
	public static final int CONTRACTING_SMID = 202;
	public static final int CONTRACTING_TC = 203;
	public static final int CONTRACTING_SUCCESS = 204;
	public static final int CONTRACTING_SIGNATURE = 205;
	public static final String CONTRACTING_CUSTOMER_ID = "custID";
	public static final String CONTRACTING_CARD_NUMBER = "cardNumber";
	public static final String CONTRACTING_PCB_CONTRACT_NUMBER = "pcbContractNumber";
	public static final String CONTRACTING_BUNDLE = "bundle";
	public static final String CONTRACTING_ALIAS = "aliasname";
	public static final String CONTRACTING_ERROR_EMPTY_CREDENTIAL_MESSAGE = "Please enter credentials";
	public static final String CONTRACTING_ERROR_INVALID_DETAILS = "Card number/client number is not valid";
	public static final String CONTRACTING_ERROR_INVALID_IMAGE = "A video file has been selected. Please select an image";
	public static final String CONTRACTING_ERROR_NETWORK_NOT_AVAILABLE = "Network not available";
	public static final String CONTRACTING_RESPONSE_ENUMBER = "e-number";
	public static final String CONTRACTING_RESPONSE_WAS_RETURN_CODE = "was-return-code";
	public static final String LOGIN_ERROR_USERID_NOTEXISTS = "User id does not exist";
	public static final String LOGIN_ERROR_CARDID_NOTEXISTS = "Card number does not exist";
	public static final String LOGIN_ERROR_CARD_EXPIRED = "Card expired (W)";
	public static final String LOGIN_ERROR_CARD_NOT_ACTIVE = "Card not activated (I)";
	public static final String LOGIN_ERROR_CARD_BLOCKED = "Card blocked";
	public static final String LOGIN_ERROR_EXCEEDED_COMBINATION_IN_DEVICES = "Combination exists in 5 devices. Delete one to proceed further";
	public static final String LOGIN_APP_TYPE = "appType";
	public static final String CONTRACTING_MODEL = "model";
	public static final String CARDNUMBER = "pan";
	public static final String AUTHLEVEL = "2";
	public static final String AUNTHENTICATION_MEAN = "authenticationMean";
	public static final String CONTRACTING_MOBILE_TEXT = "(M-Contract)";
	public static final String CONTRACTING_NO_MOBILE_TEXT = "(No M-Contract)";
	public static final String LOGIN_AUTH_MEANS_SMS_1 = "1";
	public static final String LOGIN_AUTH_MEANS_CARD_2 = "2";
	public static final String LOGIN_AUTH_MEANS_PASSWORD_3 = "3";
	public static final String LOGIN_AUTH_MEANS_ARRAY_LIST = "autnMeansList";
	public static final String LOGIN_SELECTED_USER = "selectedUser";
	public static final String LOGIN_BUNDLE = "loginBundle";
	
	public static final String CONTRACTING_NO_MOBILE = "00";
	public static final String CONTRACTING_MOBILE = "02";
	public static final String PHONE_ADDRESS_TYPE="01";
	public static final String CONTRACTING = "contracting";
	public static final int CONTRACTING_0XFF = 0xff;
	public static final int CONTRACTING_0X100 = 0x100;
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int ELEVEN = 11;
	public static final int TWELVE = 12;
	public static final int THIRTEEN = 13;
	public static final int FOURTEEN = 14;
	public static final int FIFTEEN = 15;
	public static final int SIXTEEN = 16;
	public static final int SEVENTEEN = 17;
	public static final int EIGHTEEN = 18;
	public static final int NINETEEN = 19;
	public static final int TWENTY = 20;
	public static final int TWENTY_FOUR = 24;
	public static final int TWENTY_FIVE = 25;
	public static final int THIRTY = 30;
	public static final int FORTY = 40;
	public static final int SIXTY_SEVEN = 67;
	public static final int SIXTY_NINE= 69;
	public static final int FIFTY_FIVE  = 55;
	public static final int FIFTY_SIX = 56;
	public static final int NINETY = 90;
	public static final int FOURTY_TWO = 42;
	public static final int NINETY_SEVEN = 97;
	public static final int NINETY_EIGHT = 98;
	public static final int HUNDREDANDFORTY = 140;
	public static final int ONE_HUNDRED = 100;
	public static final int HUNDRED_AND_TWENTY_EIGHT = 128;
	public static final int THOUSANDTWENTYFOUR = 1024;
	public static final int THOUSAND = 1000;
	public static final int MAX_PASSWORD_LENGTH = 20;
	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int BUFFER_SIZE = 2048;
	public static final int HTTP_PORT = 80;
	public static final int HTTPS_PORT = 443;
	public static final int ZXING_SCAN_REQUEST = 65743;
	public static final int ONE_EIGHTY = 180;
	public static final int SIXTY = 60;
	public static final int FIFTY = 50;
	public static final int FOUR_HUNDRED = 400;
	public static final int SIX_HUNDRED = 600;
	public static final int EIGHT_HUNDRED = 800;
	public static final int THREE_HUNDRED = 300;
	
	
	//added by vijaya 
	public static final int FIVE_HUNDRED = 500;
	
	/*....ends   -Vijaya */
	public static final String OPT_OFF = "1";  // 1 means disable changed by sundar
	public static final String OPT_ON = "0";   // 0 means enable  changed by sundar
	public static final String BELGIUM_PHONE_START_ZERO = "04";
	public static final String BELGIUM_PHONE_START_DOUBLE_ZERO = "00324";
	public static final String BELGIUM_PHONE_START_PLUS = "+324";
	public static final String ANIMATE = "animate";
	public static final String USER_ID = "userID";
	public static final String LOGON_ID = "logonID";
	public static final String PASSWORD = "password";
	public static final String EMAIL_ID = "email";
	public static final String CONTACT_NUMBER = "phone";
	public static final String EMAIL_OPT = "email_opt";
	public static final String CONTACT_NUMBER_OPT = "phone_opt";
	public static final String PRE_CONTRACT_INFO = "preContractInfoPDF";
	public static final String TERMS_CONDITIONS = "termsConditionsPDF";
	public static final String M2_CHALLENGES = "M2code";
	public static final String M2_SIGN = "M2sign";
	public static final String SIGNATURE_TYPE_M2 = "M2";
	public static final String SECURITY_TYPE_M8 = "M8";
	public static final String ORDER_TYPE_72 = "72";
	public static final String JS_INTERFACE = "AndroidPrintDialog";
	public static final String CONTENT_TRANSFER_ENCODING = "base64";
	public static final String CLOSE_POST_MESSAGE_NAME = "cp-dialog-on-close";
	public static final String BLANK = "";
	// contracting ends

	// Product enrollment constants
	public static final int MOBILIO_REQUEST_CODE = 1;
	public static final int CREATE_NEW_ZENA = 2;
	public static final int DOWNGRADE_TO_ZENA = 3;
	public static final int ONLINE_SAVING_REQUEST_CODE = 4;
	public static final int PREMIUM_SAVING_REQUEST_CODE = 5;
	
	public static final int PRDCT_ENRL_CHECK_DOWNGRADE_VALIDATION = 301;
	public static final int PRDCT_ENRL_GET_PRODUCTS_FOR_OPENING = 302;
	public static final int PRDCT_ENRL_OPENING_ACCOUNT_VALIDATION = 303;
	public static final int PRDCT_ENRL_GET_DATA_FOR_SIGNATURE = 304;
	public static final int PRDCT_ENRL_ACTIVATE_PRODUCT_ENROLMENT = 305;
	public static final int PRDCT_ENRL_GET_ACCOUNTLIST = 306;
	public static final int PRDCT_ENRL_GET_PRODUCT_ATTRIBUTES=307;
	public static final int PRDCT_ENRL_MOBILIO_VALIDATIONS=308;
	public static final int PRDCT_ENRL_XML_CONTENT=309;
	public static final int PRDCT_ENRL_GET_P2P_ACCOUNT=310;
	public static final int PRDCT_ENRL_GET_P2P_SET_UP_DATA = 311;
	public static final int PRDCT_ENRL_UPDATE_APP_VERSION = 312;
	
	
	
	public static final int MEMO_VALIDATE_EDIT=401;
	public static final int MEMO_CONFIRM_EDIT=402;	
	//Added for MemoTransfer Edit
    public static final int DIALOG_FOR_MODIFY_MEMO=2001;
    
    public static final int  DIALOG_FOR_VALIDATE_DELETE_MEMO=2002;    
    public static final int DIALOG_FOR_DELETE_MEMO=2003;
    
	//Added by vijaya which acts as for checking whether it is for MemoTransfer edit
	public static final String MEMOTRANSFER_EDIT="MEMOTRANSFEREDIT";

	public static final String PE_LOGIN_DIRECT = "direct";
	public static boolean isPeFlowCompleted = false;
	public static final String PE_FLOW_STATUS = "peFlowStatus";
	public static final String ORDER_PENDING="ORDER PENDING";
	public static final String ORDER_UNAVAILABLE="UNAVAILABLE";

	// PE web service contstants
	public static final String FLAG_CURRENT_ACCOUNT="1";
	public static final String FLAG_SAVING_ACCOUNT="0";
	public static final String RESPONSE_LIST_ONE="1";
	public static final String RESPONSE_LIST_TWO="2";
	public static final String TYPE_PANO_ZENA_BANKING = "ZD";
	public static final String TYPE_PANO_EASY_BANKING = "ED";
	public static final String PRIVATE_USAGE = "PRI";
	public static final String PHISYCAL_PERSON = "PP";// customer type
	public static final String TYPE_REQUEST_ACTIVE = "A";

	public static final String ZENA_PRODUCT = "CDIGOF";
	public static final String PREMIUM_SAVING_PRODUCT = "SFID";
	public static final String ONLINE_SAVING_PRODUCT = "SNORM";//"SEXTPK";
	public static final String TYPE_FAMILY_CURRENT = "CUR";
	public static final String TYPE_FAMILY_SAVING = "SAV";
	public static final String TYPE_FAMILY_TERM = "TER";

	public static final String TYPE_PACKAGE_ZENA = "92";
	public static final String TYPE_PACKAGE_SERVICE = "72";
	// public static final String TYPE_ORDER_85 = "85";
	// public static final String TYPE_SECURITY = "HASHM2";
	public static final String FROM_SERVICE_WITH_MF = "N";
	public static final String FLAG_NORMAL_CALL = "1";
	public static final String RELATION_TYPE = "A09";
	public static final String TYPE_HODERSHIP="0";	
	public static final String PERSON_ACCOUNT_RELATION="01";
	public static final String HOLDER_ID="12345678  ";

	public static final String APP_TYPE_ZENA = "002";
	public static final String APP_TYPE_EASY = "001";
	public static final String APP_VERSION = "001";
	public static final String ACTIVATE_YES = "Y";
	public static final String ACTIVATE_NO = "N";
	public static final String TEMP_CONTRACTNO = "E0000001";
	public static final String KEY_AUTH = "authenticated";
	public static final String SECURITY_TYPE_DNHASHM2="DNHASHM2";
	public static final String SECURITY_TYPE = "08";
	public static final String ORDER_TYPE_85 = "85";
	public static final String SIGNATURE_TYPE_HASHM2 = "HASHM2";
	public static final String CHALLENGE = "challenge";
	public static final String CAME_FROM_PROFILE_LIST = "came_from_profile_list";
	public static final String CONTRACTING_NR = "contractnr";
	public static final String IS_SAVED_PROFILE = "IS_SAVED_PROFILE";
	public static final String DEVICE_REG_STATUS = "deviceRegistrationStatus";
	public static final String IS_PROFILE_LIST_LAUNCHED_FIRST = "is_profile_list_launched_first";
	public static final String IS_FIRST_TIME = "ISFIRSTTIME";

	// dont use this flag for any other purpose, we already used in MR1Y13 and renamed as ISFIRSTTIME in MR2Y13
	 // but the previous value went to live. If we are trying to get value based on new key then we will get NULL.
	 // To avoid this, we are changing that old key name(IS_FIRST_TIME) to new key name(ISFIRSTTIME) while doing 
	 // DB upgradation in MR2Y13 
	public static final String IS_FIRST_TIME_ZENA = "IS_FIRST_TIME"; 
	
	public static final String IS_MAIN_SCREEN_LAUNCHED_FIRST = "is_main_screen_launched_first";
	public static final String CAME_FROM_MAIN_SCREEN = "came_from_main_screen";
	public static final String CAME_FROM_OVERVIEW = "came_from_overview";
	public static final String IS_SESSION_TERMINATED = "is_session_terminated";
	public static final String COME_OUT_OF_APP = "come_out_of_app";
	public static final String PE_DIRECT = "direct";
	public static final String PE_PRODUCT = "product";
	public static final String PE_ACCOUNT_P2P = "accountP2P";
	public static final String PE_ACCOUNT_SETUP_DATA = "accountSetupData";
	public static final String PCC_URL = "PCC_URL";
	public static final String LIST_OF_CONTRACTS = "list_of_contracts";
	public static final String CMODEL = "cmodel";
	public static final String USERPROFILE_MODEL = "userprofile_model";
	public static final String IS_CONFIG_NEW_FEATURE = "IS_CONFIG_NEW_FEATURE";
	
	public static final String PE_MOREINFO="http://www.google.com";
	public static final int LAYOUT_LAND_TOP_MARGIN = 60;
	public static final int LAYOUT_LOGIN_LAND_TOP_MARGIN = 120;
	public static final int LAYOUT_LAND_BOTTOM_MARGIN = 20;
	public static final double LAYOUT_HEIGHT_ONE_THIRD = 0.30;
	public static final double LAYOUT_HEIGHT_ONE_FOURTH = 0.25;
	public static final double LAYOUT_HEIGHT_ONE_FIFTH = 0.20;
	public static final double LAYOUT_HEIGHT_ONE_HALF = 0.50;
	public static final int TRUNCATION_LIMIT = 17;
	public static final String MORE_INFO = "moreInfo";
	public static final String MORE_INFO_MOBILIO = "mobilio";
	public static final String MORE_INFO_PREMIUM = "premium";
	public static final String MORE_INFO_SAVINGS = "savings";
	public static final String MORE_INFO_P2P = "p2p";
	public static final String MORE_INFO_ZENA = "zena";
	public static final String MORE_INFO_CONTRACTING = "contracting";
	public static final String TROUBLE_SIGNIN_CONTRACTING="troublesignin";

	public static final String PACKAGE_TO_CHECK = "com.bnpp.easybanking.easybanking";
	public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.bnpp.easybanking&hl=en";
	public static final String P2P_SUSPENDED = "false";
	public static final String AUTH_ERROR_MIB_CODE = "authentication_error_mib";
	public static final String IS_AUTH_FAILED = "is_authentication_failed";
	public static final String DEVICE_NAME_VALUE = "test1111";
	public static final String IS_FROM_DIDA = "is_from_dida";
	public static final String DEVICE_FINGER_PRINT = "1111";
	public static final String IS_DEVICEID_EXPIRED = "isDeviceIdExpired";

	public static final String CARD_NR_START_DIGITS = "6703";
	public static final String IS_CLIENTNR_IS_CARDNR = "IsClientNrIsContractNr";
	public static final String IS_CAME_FROM_SELECT_CONTRACT="IsCameFromSelectContract";
	
	public static boolean OnAnimationIsRunning = false;
	public static final String IS_ONLINE = "online";	
	public static final  int TRANSPARENT = 0xFF000000;
	//For update password policy -- sundar	
		public static final String IS_PASSWORD_NEEDED = "1";		
	// text sizes used in the code
	public static final int TEXT_VERY_SMALL_9 = 9;
	public static final int TEXT_VERY_SMALL_10 = 10;
	public static final int TEXT_SMALL_11 = 11;
	public static final int TEXT_SMALL_12 = 12;
	public static final int TEXT_MEDIUM_13 = 13;
	public static final int TEXT_MEDIUM_14 = 14;
	public static final int TEXT_MEDIUM_15 = 15;
	public static final int TEXT_BIG_16 = 16;
	public static final int TEXT_BIG_17 = 17;
	public static final int TEXT_VERY_BIG_18 = 18;
	public static final int TEXT_VERY_BIG_19 = 19;
	public static final int TEXT_VERY_BIG_20 = 20;
	public static final int TEXT_LARGE_21 = 21;
	public static final int TEXT_LARGE_22 = 22;
	public static final int TEXT_LARGE_23 = 23;
	public static final String IS_BACK_DISABLED = "isBackDisable";
	public static final String IS_DB_UPGRADED = "IS_DB_UPGRADED";
	public static final String IS_APP_UPDATED = "IS_APP_UPDATED";
	public static final String IS_PROFILE_CHANGED = "is_profile_changed";
	public static final int PROFILE_M2_SIGNATURE=255;
	public static final int TRUSTED_BENEFICIARY_FLAG=256;
	public static final int UPDATE_TRUSTED_BENEFICIARY_FLAG=257;
	public static final int GSM_NULL=11;
	public static final int TRUSTED_M2_SIGNATURE_CANCELLED=12;
	public static final String ERROR_SEVERITY_S = "S";
	public static final String ERROR_SEVERITY_E = "E";
	
	
	public static enum YEAR {
	
		JANUARY(0),
		FEBRUARY(1),
		MARCH(2),
		APRIL(3),
		MAY(4),
		JUNE(5),
		JULY(6),
		AUGUST(7),
		SEPTEMBER(8),
		OCTOBER(9),
		NOVEMBER(10),
		DECEMBER(11);
		
		private final int value;
		private YEAR(int value) {
			this.value = value;
			
		}
		public int getValue() {
			return value;
		}
		
	}
	
	//Memo Transfer constants start; added by Bhumanyu
	public static final int MEMO_TRANSFER_VALIDATE_DELETE = 750;
	public static final int MEMO_TRANSFER_DELETE = 751;
	public static final int MEMO_TRANSFER_LIST= 752;
	public static final String MEMO_TRANSFER_MODEL = "memo_transfer_model";
	public static final String IS_MEMO_TRANSFER_DELETE = "is_memo_transfer_delete";
	public static final String SEPA_TRANSFER = "E";
	public static final String NON_SEPA_TRANSFER_DOMESTIC = "D";
	//Memo Transfer ends
	
	
	public static final int MONTHLY = 4;
	public static final int QUARTERLY = 6;
	public static final int HALF_YEARLY = 7;
	public static final int ANNUALLY = 8;
	
	
	public static final int SO_PERMANENT_ORDER_LIST = 800;
	
	//Standing Orders ends
}
