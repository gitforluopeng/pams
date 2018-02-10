package pms.com.system.shiro.services.Imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.model.ShiroUserGroupRole;
import pms.com.system.shiro.model.DAO.ShiroResourcesDao;
import pms.com.system.shiro.model.DAO.ShiroRoleDao;
import pms.com.system.shiro.model.DAO.ShiroUserDao;
import pms.com.system.shiro.model.DAO.ShiroUserGroupRoleDao;
import pms.com.system.shiro.services.ShiroUserServiceInter;

@Service
public class ShiroUserService implements ShiroUserServiceInter {
	
	@Resource
	private SqlSessionFactory sessionFactory;
	
	@Override
	public ShiroUser loadForUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		SqlSession session = sessionFactory.openSession();
		ShiroUserDao shiroUserDao = session.getMapper(ShiroUserDao.class);
		ShiroUser shiroUser = shiroUserDao.loadForUsername(username);
		session.close();
		
		return shiroUser;
	}

	@Override
	public Set<ShiroRole> loadUserAllRolesForShiroGroupId(Long shiroGroupId) {
		if (shiroGroupId == null) {
			return null;
		}
		SqlSession session = sessionFactory.openSession();
		ShiroUserGroupRoleDao shiroUserGroupRoleDao = session
				.getMapper(ShiroUserGroupRoleDao.class);
		Set<ShiroUserGroupRole> groupRoles = shiroUserGroupRoleDao
				.loadAllForShiroGroupId(shiroGroupId);
		List<Long> shiroRoleIds = new ArrayList<Long>();
		for(ShiroUserGroupRole groupRole: groupRoles){
			shiroRoleIds.add(groupRole.getShiroRoleId());
		}
		ShiroRoleDao shiroRoleDao = session.getMapper(ShiroRoleDao.class);
		Set<ShiroRole> shiroRoles = shiroRoleDao.loadAllShiroRolesForIds(shiroRoleIds);
		session.close();
		return shiroRoles;
	}

	@Override
	public List<ShiroResources> loadUserAllResourecsForShiroRoleIds(List<Long> shiroRoleIds) {
		
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesDao shiroResourcesDao = session.getMapper(ShiroResourcesDao.class);
		List<ShiroResources> shiroResources = shiroResourcesDao.loadAllForShiroRoleIds(shiroRoleIds);
		session.close();
		
		return shiroResources;
	}

	@Override
	public List<ShiroResources> loadUserAllMenuResources(List<Long> shiroRoleIds) {
		
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesDao shiroResourcesDao = session.getMapper(ShiroResourcesDao.class);
		List<ShiroResources> shiroResources = shiroResourcesDao.loadMenus(shiroRoleIds);
		session.close();
		
		return shiroResources;
	}

}
