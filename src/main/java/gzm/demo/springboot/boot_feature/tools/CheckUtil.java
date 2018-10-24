package gzm.demo.springboot.boot_feature.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (str != null) {
			if (str.trim().isEmpty()) {
				flag = true;
			}
			if (str.equals("")) {
				flag = true;
			}
		} else if (str == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * * 166， 176，177，178 180，181，182，183，184，185，186，187，188，189 145，147
	 * 130，131，132，133，134，135，136，137，138，139
	 * 150，151，152，153，155，156，157，158，159 198，199 String regExp =
	 * "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
	 * 说明：13开头的后面跟0-9的任意8位数； 15开头的后面跟除了4以外的0-9的任意8位数； 18开头的后面跟0-9的任意8位数；
	 * 17开头的后面跟0-8的任意8位数，或者17[^9]； 147，145开头后面跟任意8位数； 166开头的后面跟任意8位数；
	 * 198，199,195开头后面跟任意8位数；
	 * 
	 * 
	 * 
	 * 三大运营商最新号段 移动号段134 135 136 137 138 139 147 150 151 157 152 158 159 178 182
	 * 183 184 187 188 联通号段：130 131 132 145 155 156 171 175 176 185 186 电信号段
	 * ：133 149 153 173 177 180 181 189 虚拟运行商 170 单个
	 */
	// public static boolean isMobilePhone(String mobileStr){
	// boolean flag = false;
	// if(mobileStr.length()==0){
	// return false;
	// }
	//
	//// String regEx =
	// "^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$";
	// String regEx =
	// "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[589])\\d{8}$";
	// Pattern p = Pattern.compile(regEx);
	// Matcher m = p.matcher(mobileStr);
	// flag = m.find();
	// return flag;
	// }

	public static boolean isInteger(String str) {
		if (str != null && !"".equals(str.trim())) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (isNum.matches()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// 校验身份证信息
	public static boolean isCard(String str) {
		str = str.replace(" ", "");
		boolean flag15 = false;
		boolean flag18 = false;
		String regEx15 = "^[1-9]\\d{7}((0\\[1-9])|(1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{2}([0-9]|x|X){1}$";
		String regEx18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\[1-9]))|((1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{3}([0-9]|x|X){1}$";

		Pattern p15 = Pattern.compile(regEx15);
		Matcher m15 = p15.matcher(str);
		flag15 = m15.find();
		Pattern p18 = Pattern.compile(regEx18);
		Matcher m18 = p18.matcher(str);
		flag18 = m18.find();

		if (flag18 || flag15) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查输入的机柜编号是否正确
	 */
	public static boolean checkCabinetNumber(String beginNumber, String endNumber) {
		boolean bool = false;
		beginNumber = beginNumber.replace(" ", "");
		endNumber = endNumber.replace(" ", "");
		if ("0".equals(beginNumber.substring(0, 1))) {// 去掉01数字前面的0
			beginNumber = beginNumber.substring(1, beginNumber.length());
		}
		if ("0".equals(endNumber.substring(0, 1))) {// 去掉01数字前面的0
			endNumber = endNumber.substring(1, endNumber.length());
		}
		try {
			int begin = Integer.valueOf(beginNumber);
			int end = Integer.valueOf(endNumber);
			if (begin <= end) {
				return bool = true;
			}
		} catch (Exception e) {
			return bool = false;
		}
		return false;
	}

	/**
	 * 隐藏并替换所有的身份证号码
	 * 
	 * @param contentStr
	 * @return
	 */
	public static String hideAllIdCardNum(String contentStr) {
		Pattern pattern = Pattern
				.compile("(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)");
		Matcher matcher = pattern.matcher(contentStr);
		StringBuffer sb = new StringBuffer();
		try {
			while (matcher.find()) {
				String idCardStr = matcher.group();
				int len = idCardStr.length();
				if (len >= 9) {
					idCardStr = idCardStr.replaceAll("(.{" + (len < 12 ? 3 : 6) + "})(.*)(.{4})", "$1" + "****" + "$3");
				}
				matcher.appendReplacement(sb, idCardStr);
			}
			matcher.appendTail(sb);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 对证件号做掩码
	 */
	public static String hideCardNum(String contentStr) {

		int len = contentStr.length();
		if (len >= 6) {
			contentStr = contentStr.replaceAll("(.{" + (3) + "})(.*)(.{4})", "$1" + "***********" + "$3");
		}
		return contentStr;
	}

	public static void main(String[] args) {
		// System.out.println(isMobilePhone("19571585246"));
		System.out.println(hideCardNum("34512327"));
	}

}
