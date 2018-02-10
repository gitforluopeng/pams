package pms.com.system.shiro.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import pms.com.utils.Md5;

public class ShiroEncrypt {
	
	private static String saltSource="luopeng123";
	private static String hashAlgorithmName = "MD5";
	private static int hashIterations = 1024;	
	static{
		saltSource=Md5.getCode32(saltSource);
	}
	public static String getSaltSource() {
		return saltSource;
	}
	public static void setSaltSource(String saltSource) {
		ShiroEncrypt.saltSource = Md5.getCode32(saltSource);
	}
	public static String getHashAlgorithmName() {
		return hashAlgorithmName;
	}
	public static void setHashAlgorithmName(String hashAlgorithmName) {
		ShiroEncrypt.hashAlgorithmName = hashAlgorithmName;
	}
	public static int getHashIterations() {
		return hashIterations;
	}
	public static void setHashIterations(int hashIterations) {
		ShiroEncrypt.hashIterations = hashIterations;
	}
	public static ByteSource getMd5Hash(){
		return new Md5Hash(saltSource);
	}
	public static ByteSource getShiroByteSource(){
		return ByteSource.Util.bytes(saltSource);
	}
	public static Object getEncrptSimpleHash(String inputText){
		return new SimpleHash(hashAlgorithmName, inputText, getMd5Hash(), hashIterations);
	}
	public static String getEncrptPassword(String pwd){
		//String pwd = Md5.getCode32(password);
		pwd = Md5.getCode32(pwd+Md5.getCode32(saltSource));
		return pwd;
	}
}
