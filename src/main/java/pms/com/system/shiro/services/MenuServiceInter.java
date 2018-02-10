package pms.com.system.shiro.services;

import java.util.Map;

import pms.com.system.shiro.vo.MenuVo;

public interface MenuServiceInter {
	
	/**
	 * Task : 加载用户菜单
	 * @return 如果有返回map集合 key 为菜单显示顺序，value 为菜单对象
	 * date :2017年10月14日
	 * @author libo
	 */
	public Map<Integer, MenuVo> loadUserMenuForUserName();
	
	/**
	 * Task : 加载管理员菜单，先从cache中取，如果cache没有再从数据库查询
	 * @return 如果有返回map集合 key 为菜单显示顺序，value 为菜单对象
	 * date :2017年10月18日
	 * @author libo
	 */
	public Map<Integer, MenuVo> loadAllMenu();
	
}
