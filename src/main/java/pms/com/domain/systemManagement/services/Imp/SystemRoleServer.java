package pms.com.domain.systemManagement.services.Imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.systemManagement.services.SystemRoleServiceInter;
import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroResourcesRoles;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.DAO.ShiroResourcesDao;
import pms.com.system.shiro.model.DAO.ShiroResourcesRolesDao;
import pms.com.system.shiro.model.DAO.ShiroRoleDao;
import pms.com.utils.LoggerUtil;
@Service
public class SystemRoleServer implements SystemRoleServiceInter{
	
	@Resource
	private SqlSessionFactory sessionFactory;
	
	@Override
	public List<ShiroRole> getAllRoleForPage(int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		List<ShiroRole> shiroRoles=shiroRoleDao.getAllRoleForPage(pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return shiroRoles;
	}

	@Override
	public int getAllRoleCount() {
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		int count=shiroRoleDao.getAllRoleCount();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

	@Override
	public int addRole(ShiroRole shiroRole) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		if (!(shiroRoleDao.getRoleByName(shiroRole).isEmpty())) {
			return 0;
		}
		try {
			int status=shiroRoleDao.addRole(shiroRole);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}

	@Override
	public int deleteRole(ShiroRole shiroRole) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		if ("admin".equals(shiroRole.getName())||"user".equals(shiroRole.getName())) {
			return 0;
		}
		try {
			int status=shiroRoleDao.deleteRole(shiroRole);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}

	@Override
	public int updateRole(ShiroRole shiroRole, String unEditName) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		if (unEditName!=null) {
			if ("admin".equals(unEditName)||"user".equals(unEditName)) {
				return -2;
			}
			if (!(unEditName.equals(shiroRole.getName()))){
				if (!(shiroRoleDao.getRoleByName(shiroRole).isEmpty())) {
					return 0;
				}
			}
		}
		try {
			int status=shiroRoleDao.updateRole(shiroRole);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}

	@Override
	public int updateRoleIsUse(ShiroRole shiroRole) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		if ("admin".equals(shiroRole.getName())||"user".equals(shiroRole.getName())) {
			return 0;
		}
		try {
			int status=shiroRoleDao.updateRoleIsUse(shiroRole);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}

	@Override
	public List<ShiroResources> loadAllShiroResources() {
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesDao shiroResourcesDao=session.getMapper(ShiroResourcesDao.class);
		List<ShiroResources> shiroResources= shiroResourcesDao.loadAllShiroResourcesByRoleId();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return shiroResources;
	}

	@Override
	public List<ShiroResources> loadAllByShiroRoleId(Long shiroRoleId) {
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesDao shiroResourcesDao=session.getMapper(ShiroResourcesDao.class);
		List<ShiroResources> shiroResources= shiroResourcesDao.loadAllByShiroRoleId(shiroRoleId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return shiroResources;
	}

	@Override
	public int addResouceRole(ShiroResourcesRoles shiroResourcesRoles) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesRolesDao shiroResourcesRolesDao=session.getMapper(ShiroResourcesRolesDao.class);
		if (!shiroResourcesRolesDao.getShiroResourcesRolesByRoleId(shiroResourcesRoles).isEmpty()) {
			return 0;
		}
		try {
			int status=shiroResourcesRolesDao.addResouceRole(shiroResourcesRoles);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}

	@Override
	public int deleteResouceRole(ShiroResourcesRoles shiroResourcesRoles) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesRolesDao shiroResourcesRolesDao=session.getMapper(ShiroResourcesRolesDao.class);
		try {
			int status=shiroResourcesRolesDao.deleteResouceRole(shiroResourcesRoles);
			if (status<=0) {
				session.rollback();
			}
			else {
				session.commit();
				result=1;
			}
		} catch (Exception e) {
			session.rollback();
			LoggerUtil.consleLogger.error(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				LoggerUtil.consleLogger.error(e);
			}
		}
		return result;
	}
}
