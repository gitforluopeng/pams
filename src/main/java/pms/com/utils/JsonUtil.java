package pms.com.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import pms.com.system.shiro.exception.BeanResovleParamterException;


public class JsonUtil{
	
    private static Log log = LogFactory.getLog(JsonUtil.class);
    

    public static String object2json(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer
                || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double
                || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2json(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2json((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2json((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2json((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2json((Set<?>) obj));
        } else {
            json.append(bean2json(obj));
        }
        return json.toString();
    }

    public static String bean2json(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = object2json(props[i].getName());
                    String value = object2json(props[i].getReadMethod().invoke(
                            bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static String set2json(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String string2json(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '/':
                sb.append("\\/");
                break;
            default:
                if (ch >= '\u0000' && ch <= '\u001F') {
                    String ss = Integer.toHexString(ch);
                    sb.append("\\u");
                    for (int k = 0; k < 4 - ss.length(); k++) {
                        sb.append('0');
                    }
                    sb.append(ss.toUpperCase());
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public static void writeResult(HttpServletResponse response, Object obj) {
        response.addHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        // response.setContentType("application/json");
        try {
            response.getWriter().write(obj.toString());
            log.debug("输出数据为：" + obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param response
     * @param json_obj
     */
    public static void jsonObjectResult(HttpServletResponse response,
            JSONObject json_obj) {
        response.setCharacterEncoding("utf-8");
        // response.setContentType("application/json");
        try {
            response.getWriter().write(json_obj.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    
    /**
     * Task : 转换json 为对应类对象
     * @param jsonStr json 字符串，必须为json数组
     * @param clazz 要转换为类的class
     * @return 如果成功返回 list 类集合， 反之为size为0
     * @throws ParseException
     * date :2017年10月20日
     * @author libo
     */
    public static <T> List<T> parseBean(String jsonStr, Class<T> clazz) throws ParseException{
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    	JSONArray params = new JSONArray(jsonStr);
    	
    	
		Field[] fields =  clazz.getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for(int j = 0; j < fields.length; j++){
			fieldMap.put(fields[j].getName(), fields[j]);
		}
		
		Method[] methods = clazz.getMethods();
		Map<String, Method> mapMethod = new HashMap<String, Method>();
		for(Method method: methods){
			mapMethod.put(method.getName(), method);
		}
		
		String clazzName = clazz.getSimpleName();
		clazzName = clazzName.substring(0,1).toLowerCase() + clazzName.substring(1,clazzName.length());
		List<T> ts = new ArrayList<T>();
		for(int j = 0; j < params.length(); j++){
			T object = null;
			try {
				object = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			JSONObject param = params.getJSONObject(j);
			for(int i = 0; i < fields.length; i++){
				String fieldName = fields[i].getName();
				String fieldTypeName = fields[i].getType().getName();
				if(!param.has(fieldName)) continue;
				
				String value = param.get(fieldName).toString();
				
				if(!StringUtils.isEmpty(value)){
					Object objValue = null;
					if(fieldTypeName.equals(Date.class.getName())){
						objValue = dateFormat.parse(value);
					}else if(fieldTypeName.equals(Double.class.getName())){
						objValue = Double.parseDouble(value);
					}else if(fieldTypeName.equals(Float.class.getName())){
						objValue = Float.class.getName();
					}else if(fieldTypeName.equals(Integer.class.getName())){
						objValue = Integer.parseInt(value);
					}else if(fieldTypeName.equals(Long.class.getName())){
						objValue = Long.parseLong(value);
					}else if(fieldTypeName.equals(Boolean.class.getName())){
						objValue = Boolean.parseBoolean(value);
					}else if(fieldTypeName.equals(String.class.getName())){
						objValue = value;
					}else {
						throw new BeanResovleParamterException("不支持的转化类型："+fieldTypeName+" : "+fieldName
								+ "目前只支持 Date, Double, Float, Integer, Long, Boolean, String");
					}
					String methodName = fields[i].getName();
					methodName = "set"+methodName.substring(0,1).toUpperCase() + methodName.substring(1,methodName.length());
					Method method = mapMethod.get(methodName);
					if(method == null) continue;
					try {
						method.invoke(object, objValue);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				
			}
			ts.add(object);
		}
    	return ts;
    }
}
