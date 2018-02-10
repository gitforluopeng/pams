package pms.com.system.shiro.services.Imp;

import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.DAO.ShiroRoleDao;
import pms.com.system.shiro.services.ShiroRoleServiceInter;

@Service
public class ShiroRoleService implements ShiroRoleServiceInter {
	
	@Autowired
	public SqlSessionFactory sessionFactory;
	
	@Override
	public Set<ShiroRole> loadAllShiroRoles() {
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao = session.getMapper(ShiroRoleDao.class);
		Set<ShiroRole> shiroRoles = shiroRoleDao.loadAllShiroRoles();
		session.close();
		return shiroRoles;
	}

	@Override
	public Set<ShiroRole> loadUserRoles(Long id) {
		
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao = session.getMapper(ShiroRoleDao.class);
		Set<ShiroRole> shiroRoles = shiroRoleDao.loadUserRolesForId(id);
		session.close();
		
		return shiroRoles;
	}

}
