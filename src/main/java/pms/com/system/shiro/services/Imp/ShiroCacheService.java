package pms.com.system.shiro.services.Imp;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.com.system.shiro.UserRealm;
import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.services.FilterChainDefinitionsService;
import pms.com.system.shiro.services.ShiroCacheServiceInter;
import pms.com.utils.LoggerUtil;

@Service
public class ShiroCacheService implements ShiroCacheServiceInter {
	
	@Autowired
	private EhCacheManager cacheManager;
	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsServices;
	
	@Override
	public void clearUserCache(ShiroUser shiroUser) {
		Cache<String, ShiroUser> cache = cacheManager
				.getCache(UserRealm.SHIRO_USER_CACHE_NAME);
		Cache<String, Set<ShiroRole>> roleCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_ROLE_CACHE_NAME);
		Cache<String, List<ShiroResources>> resourcesCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_RESOURCES_CACHE_NAME);
		
		synchronized (cacheManager) {
			clearUserRoleAndResoucesAndMenuCache(shiroUser.getUsername(), roleCache, resourcesCache);
			cache.remove(shiroUser.getUsername());
		}
	}
	
	private void clearUserRoleAndResoucesAndMenuCache(String username, Cache<String, Set<ShiroRole>> roleCache
			,Cache<String, List<ShiroResources>> resourcesCache){
		
		Set<ShiroRole> roles = roleCache.remove(username);
		if(roles != null){
			List<Long> roleIds = new LinkedList<Long>();
			for(ShiroRole r: roles){
				roleIds.add(r.id);
			}
			resourcesCache.remove(roleIds.toString());
			resourcesCache.remove("ALL_"+roleIds.toString());
		}
	}

	@Override
	public void clearSystemCache() {
		Cache<String, Set<ShiroRole>> roleCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_CACHE_NAME);
		Cache<String, List<ShiroResources>> resourcesCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_RESOURCES_CACHE_NAME);
		LoggerUtil.consleLogger.debug("[ShiroCacheService -----> clearSystemCache]: 开始清理系统缓存");
		synchronized (cacheManager) {
			roleCache.remove(FilterChainDefinitionsServiceImp.ALL_ROLES_CACHE_KEY);
			resourcesCache.remove(FilterChainDefinitionsServiceImp.ALL_RESOURCE_CACHE_KEY);
			resourcesCache.remove(FilterChainDefinitionsServiceImp.ANON_MENU_CACHE_KEY);
			resourcesCache.remove(FilterChainDefinitionsServiceImp.ROLE_MENU_CACHE_KEY);
			LoggerUtil.consleLogger.debug("[ShiroCacheService -----> clearSystemCache]: 系统缓存清理完成");
			LoggerUtil.consleLogger.debug("[ShiroCacheService -----> clearSystemCache]: 开始更新系统权限");
			filterChainDefinitionsServices.updateResoures();
			LoggerUtil.consleLogger.debug("[ShiroCacheService -----> clearSystemCache]: 系统权限更新成功");
		}
	}
	

}
