package pms.com.system.shiro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import pms.com.system.shiro.exception.LoginUserIsLockException;
import pms.com.system.shiro.exception.LoginUserNotFountException;
import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.services.ShiroRoleServiceInter;
import pms.com.system.shiro.services.ShiroUserServiceInter;
import pms.com.utils.LoggerUtil;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private EhCacheManager cacheManager;
	public static String SHIRO_USER_CACHE_NAME = "shiroUserCache";
	public static String SHIRO_USER_ROLE_CACHE_NAME = "shiroUserRoleCache";
	public static String SHIRO_USER_RESOURCES_CACHE_NAME = "shiroUserResoucesCache";
	@Autowired
	private ShiroUserServiceInter shiroUserService;
	@Autowired
	private ShiroRoleServiceInter shiroRoleService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		Cache<String, Set<ShiroRole>> userRoleCache = cacheManager.getCache(SHIRO_USER_ROLE_CACHE_NAME);
		Cache<String, List<ShiroResources>> userResourcesCache = cacheManager.getCache(SHIRO_USER_RESOURCES_CACHE_NAME);
		Set<ShiroRole> shiroRoles = userRoleCache.get(shiroUser.getUsername());
		List<ShiroResources> shiroResources = null;
		List<Long> shiroRoleIds = new ArrayList<Long>();
		shiroRoleIds.sort(new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return Long.compare(o1, o2);
			}
		});
		
		if(shiroRoles == null){
			shiroRoles = shiroRoleService.loadUserRoles(shiroUser.getId());
			LoggerUtil.consleLogger.debug("[UserRealm ----> doGetAuthorizationInfo]: role ids"+"ALL_"+shiroRoleIds.toString());
		}
		
		for(ShiroRole role: shiroRoles){
			shiroRoleIds.add(role.getId());
		}
		
		shiroResources = userResourcesCache.get("ALL_"+shiroRoleIds.toString());
		if(shiroResources == null && !shiroRoleIds.isEmpty()){
			shiroResources = shiroUserService.loadUserAllResourecsForShiroRoleIds(shiroRoleIds);
		}
		userRoleCache.put(shiroUser.getUsername(), shiroRoles);
		if(shiroResources == null){
			shiroResources = new LinkedList<ShiroResources>();
		}
		userResourcesCache.put("ALL_"+shiroRoleIds.toString(), shiroResources);
		
		for(ShiroRole role: shiroRoles){
			info.addRole(role.getName());
		}
		for(ShiroResources shiroResource: shiroResources){
			info.addStringPermission(shiroResource.getUrl());
		}
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		
		UsernamePasswordToken tokenUser = (UsernamePasswordToken) authcToken;
		
		if(StringUtils.isEmpty(tokenUser.getUsername())){
			throw new LoginUserNotFountException();
		}
		Cache<String,ShiroUser> shiroUserCache = cacheManager.getCache(SHIRO_USER_CACHE_NAME);
		ShiroUser shiroUser = shiroUserCache.get(tokenUser.getUsername());
		if(shiroUser == null){
			shiroUser = shiroUserService.loadForUsername(tokenUser.getUsername());
		}
		
		if(shiroUser == null || StringUtils.isEmpty(shiroUser.getUsername())){
			throw new LoginUserNotFountException();
		}
		
		if(shiroUser.accountLockStatus){
			throw new LoginUserIsLockException();
		}
		String userPwd = shiroUser.getPassword();
		String password = new String(tokenUser.getPassword());
		
		if(userPwd.equals(password)){
			LoggerUtil.loginFileLogger.info("[UserRealm ----> doGetAuthenticationInfo]"
					+ " 用户登陆: 用户名="+tokenUser.getUsername()+", host="+tokenUser.getHost());
			if(shiroUser.isAccountLockStatus()) throw new LoginUserIsLockException();
			shiroUserCache.put(shiroUser.getUsername(), shiroUser);
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
					shiroUser, tokenUser.getPassword(), ByteSource.Util.bytes(shiroUser.getSalt()),getName());
			return info;
		}else {
			throw new LoginUserNotFountException();
		}
		
		
	}
	
	
}
