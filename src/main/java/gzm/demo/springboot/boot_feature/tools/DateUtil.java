package gzm.demo.springboot.boot_feature.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {
	protected static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	static SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 

	public static String getNowTimeS() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		return time;
	}

	public static Date getNowTimeD() {
		Calendar c = Calendar.getInstance();
		Date nowTime = c.getTime();
		return nowTime;
	}

	public static Date getBeforeYearD() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		Date nowTime = c.getTime();
		return nowTime;
	}

	public static String getDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static String getDateToStringHms(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static String getNowDateS() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	
	public static Date getVailDate(Date date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		String []timeList = time.split(" ");
		String ymd = timeList[0]+" 23:59:59";
		Date date1 = format.parse(ymd);
		return date1;
	}
	
	public static Date stringToYMD(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date returnDate = sdf.parse(str);
		return returnDate;
	}
	
	public static boolean isValidDate(String date) {  
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			dateFormat.parse(date);
		} catch (Exception e) {
			logger.error("DateValidateTools isValidDate error.",e);
			/*try {
				dateFormat.parse(date);
				return true;
			} catch (ParseException e1) {
				logger.error("DateValidateTools isValidDate error.",e1);
			}*/
			return false; 
		}
		
		return true;
	} 
	
	public static String getNowTimeYmd() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}
	
	
	/**
	 * 增加时间天数
	 * qijing
	 */
	public static String addDateAndFormat(String date,int day){
		try {
			
			Date formatDate=dateFormat.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(formatDate);
			cal.add(Calendar.DATE, day);
			return dateFormat.format(cal.getTime());
		} catch (Exception e) {
			logger.error("DateValidateTools addDateAndFormat error.",e);
			return ""; 
		}
		
	}
	
	/**
     * 获取当天的开始时间
     * 例如：输入2017-06-26 输出2017-06-26 00:00:00
     */
    public  static String  getStartSeconds(Date startTime) throws Exception{ 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(startTime);
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return sdf.format(todayStart.getTime());  
    }  
	
	// 获取前一个月
	public static Date getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}
	
	// 计算计费时间数
    public  static int  getAccountDays(Date startDate, Date endDate) throws Exception{ 
    	int day = 0 ;
    	int endDay = 0; 
		Date date = new Date();
		date = getLastMonth();
		day = getDaysOfMonth(date);
		// 本月最后一天
		endDay = getDaysOfMonth(date);
		// 计费日期与开始时间为同一个月份
		if (date.getYear()== startDate.getYear()&&date.getMonth()==startDate.getMonth()){
			day = day - startDate.getDate() + 1;
		}
		if (endDate!=null){
			if (date.getYear()== endDate.getYear()&&date.getMonth()==endDate.getMonth()){
				day = day + endDate.getDate() - endDay; 
			}
		}

        return day;
    } 
    
    // 获取某月的天数
    public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }  
    
	// 获取月末日期
	public static Date getMonthLastday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);		
		return calendar.getTime();
	}
	
	// 获取月初日期
	public static Date getMonthFirstday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	// 获取前一天
	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

    public  static Date getStartSecondsDate(Date startTime) throws Exception{ 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	String s = sdf.format(startTime);
    	Date date = sdf.parse(s);
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR, 00);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
    
	// 获取后一个月
	public static Date getNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	
	// 获取前一个月
	public static Date getLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	// 获取es用时间
	public static String getDateForEs(Date date) {
		// 开始日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, - 8);
		date = cal.getTime();
		String time = getISO8601Timestamp(date);

		return time;
	}
	
    public static String getISO8601Timestamp(Date date){  
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");  
        df.setTimeZone(tz);  
        String nowAsISO = df.format(date);  
        return nowAsISO;  
    } 
}
