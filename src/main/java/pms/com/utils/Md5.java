package pms.com.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	
	/**
	 * Task : �ṩһ���ַ���ʹ��MD5�㷨���ܣ�����32λ�Ĵ�д�����ַ���
	 * @param value Ҫ���ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * date :2017��4��12��
	 * @author libo
	 */
	public static String getCode32(String value){
		StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String md5String = value;
			md5.update(md5String.getBytes("UTF-8"));
			byte[] md5Byte = md5.digest();
			
			int i;
			for(int index = 0; index < md5Byte.length; index++){
				i = md5Byte[index];
				if(i < 0){
					i += 256;
				}
				
				if(i < 16){
					buffer.append("0");
				}
				buffer.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (buffer.toString()).toUpperCase();
	}
	
}
