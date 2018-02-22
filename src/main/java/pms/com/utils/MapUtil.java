package pms.com.utils;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MapUtil {
	public static Map<String, Object> ObjToMap(Object obj) {
		Map<String, Object> map=new HashMap<String, Object>();
		Field[] fds = obj.getClass().getDeclaredFields();
		Field[] arg2 = fds;
		int arg3 = fds.length;

		for (int arg4 = 0; arg4 < arg3; ++arg4) {
			Field fd = arg2[arg4];

			try {
				String mthname = "get" + fd.getName().toUpperCase().substring(0, 1) + fd.getName().substring(1);
				Method mth = obj.getClass().getMethod(mthname, new Class[0]);
				Object val = mth.invoke(obj, new Object[0]);
				map.put(fd.getName(), val);
			} catch (Exception arg9) {
				;
			}
		}
		return map;
	}
}
