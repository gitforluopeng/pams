package pms.com.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat DATE_FORMAT;
	
	static{
		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public static int getYear(Date date){
		String strDate = DATE_FORMAT.format(date);
		strDate = strDate.substring(0,4);
		return Integer.parseInt(strDate);
	}
	
	public static int getMonth(Date date){
		String strDate = DATE_FORMAT.format(date);
		strDate = strDate.substring(5,7);
		return Integer.parseInt(strDate);
	}
	
	public static Calendar getCalendar(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		return calendar;
	}
	
	public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Integer getYear(String date){
		String year= date.substring(0,4);
		return Integer.parseInt(year);
	}
	
	public static Integer getMonth(String date){
		String month=date.substring(5,7);
		return Integer.parseInt(month);
	}
}
