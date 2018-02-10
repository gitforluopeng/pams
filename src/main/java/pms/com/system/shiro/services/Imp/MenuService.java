package pms.com.system.shiro.services.Imp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.com.system.shiro.UserRealm;
import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.services.MenuServiceInter;
import pms.com.system.shiro.services.ShiroRoleServiceInter;
import pms.com.system.shiro.services.ShiroUserServiceInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.system.shiro.vo.MenuVo;
import pms.com.utils.LoggerUtil;

@Service
public class MenuService implements MenuServiceInter {
	
	@Autowired
	private ShiroUserServiceInter shiroUserService;
	@Autowired
	private EhCacheManager cacheManager;
	@Autowired
	private ShiroRoleServiceInter shiroRoleService;

	@Override
	public Map<Integer, MenuVo> loadUserMenuForUserName() {
		ShiroUser shiroUser = UserUtil.getShiroUser();
		Cache<String, List<ShiroResources>> resourceCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_RESOURCES_CACHE_NAME);
		
		Set<ShiroRole> roles = shiroRoleService.loadUserRoles(shiroUser.getId());
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.sort(new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return Long.compare(o1, o2);
			}
		});
		for(ShiroRole role: roles){
			roleIds.add(role.getId());
		}
		LoggerUtil.consleLogger.debug("[MenuService ----> loadUserMenuForUserName]: roleids is "+roleIds.toString());
		List<ShiroResources> shiroResources = resourceCache.get(roleIds.toString());
		
		if(shiroResources == null || shiroResources.size() == 0){
			for(ShiroRole role: roles){
				if(role.getId() == 1) continue;
				roleIds.add(role.getId());
			}
			if(roleIds == null || roleIds.size() == 0) return new HashMap<Integer, MenuVo>();
			
			shiroResources = shiroUserService
					.loadUserAllMenuResources(roleIds);
			resourceCache.put(roleIds.toString(), shiroResources);
		
		}
		
		List<ShiroResources> cacheAnonResources = resourceCache.get(FilterChainDefinitionsServiceImp.ANON_MENU_CACHE_KEY);
		if(cacheAnonResources != null){
			shiroResources.addAll(cacheAnonResources);
		}
		for(ShiroResources resources: resourceCache.get(FilterChainDefinitionsServiceImp.ANON_MENU_CACHE_KEY)){
			System.out.println(resources.getName());
		}

		Map<Long, MenuVo> menus = new HashMap<Long, MenuVo>();
		for(ShiroResources resources: shiroResources){
			if(resources.getType() == 1){
				menus.put(resources.getId(), new MenuVo(resources));
			}
		}
		
		return kindOfMenus(menus);
	}
	
	private Map<Integer, MenuVo> kindOfMenus(Map<Long, MenuVo> menus){
		if(menus == null || menus.isEmpty()) return null;
		Map<Integer, MenuVo> kindOf = new HashMap<Integer, MenuVo>();
		for(Long id: menus.keySet()){
			MenuVo menuVo = menus.get(id);
			if(menuVo.isHasParent()){
				MenuVo parentMenuVo = menus.get(menuVo.getParentId());
				if(parentMenuVo == null) continue;
				List<MenuVo> menuVos = parentMenuVo.getChildMenuVo();
				if(menuVos == null){
					menuVos = new ArrayList<MenuVo>();
					parentMenuVo.setChildMenuVo(menuVos);
				}
				menuVos.add(menuVo);
			}else{
				kindOf.put(menuVo.getOrder(), menuVo);
			}
		}
		return kindOf;
	}

	@Override
	public Map<Integer, MenuVo> loadAllMenu() {
		
		Cache<String, List<ShiroResources>> resourceCache = cacheManager
				.getCache(UserRealm.SHIRO_USER_RESOURCES_CACHE_NAME);
		List<ShiroResources> shiroResources = resourceCache.get("admin");
		if(shiroResources == null || shiroResources.size() == 0){
			
			List<Long> roleIds = new ArrayList<Long>();
			roleIds.add(1l);
			if(roleIds == null || roleIds.size() == 0) return null;
			shiroResources = shiroUserService
					.loadUserAllMenuResources(roleIds);
			resourceCache.put("admin", shiroResources);
		}
		Map<Long, MenuVo> menus = new HashMap<Long, MenuVo>();
		for(ShiroResources resources: shiroResources){
			menus.put(resources.getId(), new MenuVo(resources));
		}
		
		return kindOfMenus(menus);
	}
	
}
