package com.njwb.constant;
/**
 * 错误码常量类
 * @author Administrator
 *
 */
public class ErrorCode {
	/**
	 * 成功
	 */
	public static final String SUCCESS = "0000";
	
	public static final String SUCCESS_Account = "18";
	public static final String SUCCESS_Holiday = "16";
	public static final String SUCCESS_User = "1030";
	public static final String SUCCESS_Role= "1039";
	/**
	 * 失败（其他错误）
	 */
	public static final String EIDT_FAIL_Holiday= "0017";
	public static final String EIDT_FAIL_Account= "0022";
	public static final String ERROR = "9999";
	public static final String ERROR_Holiday= "12";
	public static final String ERROR_User = "24";
	public static final String ERROR_Role = "1037";
	
	public static final String ERROR_Account= "0019";
	/**
	 * 数据库查询结果太多
	 */
	public static final String DATABASE_MANY_RESULT = "9998";
	/**
	 * 登陆失败
	 */
	public static final String LOGIN_FAIL = "1001";
	
	/**
	 * 添加失败
	 */
	public static final String ADD_FAIL = "1002";
	public static final String ADD_FAIL_Holiday= "1013";
	public static final String ADD_FAIL_Account= "1020";
	public static final String ADD_FAIL_Role= "1038";
	public static final String ADD_FAIL_User = "1026";
	public static final String ADD_FAIL_User3 = "1031";
	public static final String ADD_FAIL_User2 = "1027";
	public static final String EDIT_FAIL_User = "1032";
	/**
	 * 添加时，数据库异常
	 */
	public static final String ADD_FAIL_DATABASE = "1003";
	public static final String ADD_FAIL_DATABASE_Holiday = "1014";
	public static final String ADD_FAIL_DATABASE_Account = "1021";
	public static final String ADD_FAIL_DATABASE_User = "1028";
	public static final String ADD_FAIL_DATABASE_Role = "1036";
	public static final String ADD_FAIL_DATABASE_Permissions = "1043";
	/**
	 * 重名
	 */
	public static final String ADD_FAIL_NAME = "1004";
	/**
	 * 删除失败
	 */
	public static final String DEL_FAIL_Holiday = "1011";
	public static final String DEL_FAIL_User = "1111";
	public static final String DEL_FAIL_Role= "1034";
	
	
	
	public static final String DEL_FAIL_Account = "1019";
	public static final String DEL_FAIL = "1005";
	public static final String EDL_FAIL= "1006";
	public static final String UPDATE_FAIL_Holiday= "1015";
	public static final String UPDATE_FAIL_Account= "1023";
	public static final String UPDATE_FAIL = "1005";
	public static final String UPDATE_FAIL_User= "1033";
	public static final String UPDATE_FAIL_Role= "1035";
	/**
	 * 验证码输入失败
	 */
	public static final String LOGIN_FAIL_CODE = "1010";

	public static final String SUCCESS_Permissions = "1040";

	public static final Object ERROR_Permissions = "1041";

	public static final Object ADD_FAIL_Permissions = "1042";

	public static final String UPDATE_FAIL_Permissions = "1044";

	public static final String DEL_FAIL_Permissions ="1045";

	public static final Object ADD_FAIL_Role2="1046";

	public static final Object ADD_FAIL_Permissions3 = "1047";

	public static final Object EDIT_FAIL_Pwd = "1048";

	public static final String SUCCESS_EDIT_PWD = "1049";

	public static final String UPDATE_FAIL_User2 = "1050";

	public static final String EDIT_FAIL_User2 = "1051";

	public static final String EDIT_FAIL_User3 = "1052";

	public static final Object EXIT = "1053";

	public static final String EXIT2 = "1055";

	public static final String DEL_FAIL2 = "1054";

	public static final Object ADD_FAIL_PER1 = "1057";

	public static final Object ADD_SUCCESS = "1056";

	public static final Object ADD_FAIL_Role3 = "1060";

	public static final Object ADD_FAIL_Role22 = "1061";

	public static final Object FAIL = "1062";



	
}
