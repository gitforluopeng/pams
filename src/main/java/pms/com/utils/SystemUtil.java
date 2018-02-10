package pms.com.utils;

public class SystemUtil {
	
	/**
	 * Task : 获取系统文件绝对路径，以项目根为根路径
	 * @return
	 * date :2017年11月14日
	 * @author libo
	 */
	public static String getSystemFileAbsoultePath() {
		String basePath = SystemUtil.class.getResource("/").getPath();
		basePath = basePath.substring(1, basePath.indexOf("WEB-INF/"));
		return basePath;
	}
	
}
