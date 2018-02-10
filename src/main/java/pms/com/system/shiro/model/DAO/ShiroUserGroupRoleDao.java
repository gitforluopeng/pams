package pms.com.system.shiro.model.DAO;

import java.util.Set;

import pms.com.system.shiro.model.ShiroUserGroupRole;

public interface ShiroUserGroupRoleDao {
	
	public Set<ShiroUserGroupRole> loadAllForShiroGroupId(Long shiroGroupId);
	
}
