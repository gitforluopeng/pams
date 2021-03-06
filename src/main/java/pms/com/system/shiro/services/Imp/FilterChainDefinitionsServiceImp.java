package pms.com.system.shiro.services.Imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.services.ShiroResourceServiceInter;
import pms.com.system.shiro.services.ShiroRoleServiceInter;
import pms.com.system.shiro.services.abstractInter.AbstractFilterChainDefinitionsService;
import pms.com.utils.LoggerUtil;

public class FilterChainDefinitionsServiceImp extends AbstractFilterChainDefinitionsService{
	
	@Autowired
	private ShiroResourceServiceInter shiroResourceService;
	@Autowired
	private ShiroRoleServiceInter shiroRoleService;
	@Autowired
	private EhCacheManager cacheManager;
	private String shiroResourcesCacheName;
	
	public static String ehcacheCacheName;
	public static String ALL_ROLES_CACHE_KEY = "all_roles";
	public static String ALL_RESOURCE_CACHE_KEY = "ALL_CACHE_RESOURCES";
	public static String ANON_MENU_CACHE_KEY = "ANON_MENU";
	public static String ROLE_MENU_CACHE_KEY = "ROLE_MENU";
	
	@Override
	public Map<String, String> loadJDBCResoures() {
		
		LoggerUtil.consleLogger.info("[FilterChainDefinitionsServiceImp ----> loadJDBCResoures] 开始加载权限");
		Map<String, String> section = new HashMap<String, String>();
		Map<Long, ShiroRole> roleIdMap = new HashMap<Long, ShiroRole>();
		List<ShiroResources> shiroResources = shiroResourceService.loadAllShiroResources();
		Set<ShiroRole> shiroRoles = shiroRoleService.loadAllShiroRoles();
		Cache<String, Set<ShiroRole>> cache = cacheManager.getCache(ehcacheCacheName);
		Cache<String, List<ShiroResources>> shiroReourcesCache = cacheManager.getCache(shiroResourcesCacheName);
		
		cache.put(ALL_ROLES_CACHE_KEY, shiroRoles);
		shiroReourcesCache.put(ALL_RESOURCE_CACHE_KEY, shiroResources);
		
		List<ShiroResources> anonMenus = new ArrayList<ShiroResources>();
		List<ShiroResources> roleMenus = new ArrayList<ShiroResources>();
		
		for(ShiroRole role: shiroRoles){
			roleIdMap.put(role.getId(), role);
		}
		
		if(shiroResources == null || shiroResources.isEmpty()){
			return section;
		}
		
		for(ShiroResources shiroResource: shiroResources){
			
			ShiroRole shiroRole = roleIdMap.get(shiroResource.getShiroRoleId());
			
			if(shiroRole != null && shiroRole.getName() != null && shiroResource.getUrl() != null){
				String roles = section.get(shiroResource.getUrl());
				if(roles != null){
					roles = roles.replace("]", ",");
				}else{
					roles = "roles[";
				}
				if(shiroResource.getType() == 1) {
					roleMenus.add(shiroResource);
				}
				section.put(shiroResource.getUrl(),roles+shiroRole.getName()+"]");
				LoggerUtil.consleLogger.info("[FilterChainDefinitionsServiceImp ----> loadJDBCResoures] 权限："+shiroResource.getUrl()+" : "
						+section.get(shiroResource.getUrl()));
			}else if(shiroResource.getUrl() != null){
				section.put(shiroResource.getUrl(), "anon");
				if(shiroResource.getType() == 1) {
					anonMenus.add(shiroResource);
				}
				LoggerUtil.consleLogger.info("[FilterChainDefinitionsServiceImp ----> loadJDBCResoures] 权限："+shiroResource.getUrl()+" : "
						+section.get(shiroResource.getUrl()));
			}else{
				if(shiroResource != null && shiroResource.getType() == 1 && shiroResource.getShiroRoleId() == null){
					anonMenus.add(shiroResource);
				}
			}
		}
		
		shiroReourcesCache.put(ANON_MENU_CACHE_KEY, anonMenus);
		shiroReourcesCache.put(ROLE_MENU_CACHE_KEY, roleMenus);
		
		return section;
	}

	public static String getEhcacheCacheName() {
		return ehcacheCacheName;
	}

	public static void setEhcacheCacheName(String ehcacheCacheName) {
		FilterChainDefinitionsServiceImp.ehcacheCacheName = ehcacheCacheName;
	}

	public String getShiroResourcesCacheName() {
		return shiroResourcesCacheName;
	}

	public void setShiroResourcesCacheName(String shiroResourcesCacheName) {
		this.shiroResourcesCacheName = shiroResourcesCacheName;
	}
}
