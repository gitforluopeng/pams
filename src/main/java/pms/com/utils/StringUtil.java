package pms.com.utils;

public class StringUtil {
    public static String addHMS(String value){
    	if (value!=null) {
			if(!value.contains(":")){
				return value+" 00:00:00";
			}
			else return value;
		}
    	return value;
    }
}
