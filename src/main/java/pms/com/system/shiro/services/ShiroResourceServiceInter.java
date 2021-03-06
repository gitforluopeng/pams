package pms.com.system.shiro.services;

import java.util.List;

import pms.com.system.shiro.model.ShiroResources;

public interface ShiroResourceServiceInter {
	
	/**
	 * Task : 加载所有的资源
	 * @return 如果有则返回list shiroResource集合，反之null
	 * date :2017年10月8日
	 * @author libo
	 */
	public List<ShiroResources> loadAllShiroResources();
	
}
