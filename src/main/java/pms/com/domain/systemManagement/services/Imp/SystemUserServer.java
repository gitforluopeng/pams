package pms.com.domain.systemManagement.services.Imp;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import pms.com.domain.systemManagement.model.MyShiroUserGroupRole;
import pms.com.domain.systemManagement.model.MyUser;
import pms.com.domain.systemManagement.model.OrganizeManagement;
import pms.com.domain.systemManagement.model.SystemUser;
import pms.com.domain.systemManagement.model.DAO.MyShiroUserGroupRoleDao;
import pms.com.domain.systemManagement.model.DAO.OrganizeDao;
import pms.com.domain.systemManagement.model.DAO.SystemUserDao;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.model.ShiroUserGroupRole;
import pms.com.system.shiro.model.DAO.ShiroRoleDao;
import pms.com.system.shiro.model.DAO.ShiroUserDao;
import pms.com.system.shiro.model.DAO.ShiroUserGroupRoleDao;
import pms.com.system.shiro.util.ShiroEncrypt;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.LoggerUtil;


@Service
public class SystemUserServer implements SystemUserServiceInter{
	
	@Resource
	private SqlSessionFactory sessionFactory;
	@Override
	public List<OrganizeManagement> getAllUnitList() {
		SqlSession session = sessionFactory.openSession();
		OrganizeDao organizeDao = session.getMapper(OrganizeDao.class);
		List<OrganizeManagement> organizeManagements=organizeDao.getAllUnitList();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return organizeManagements;
	}

	@Override
	public List<OrganizeManagement> getDeptByUnitId(Long unitId) {
		SqlSession session = sessionFactory.openSession();
		OrganizeDao organizeDao = session.getMapper(OrganizeDao.class);
		List<OrganizeManagement> organizeManagements=organizeDao.getDeptByUnitId(unitId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return organizeManagements;
	}

	@Override
	public Set<ShiroUserGroupRole> getShiroUserGroupRoleById(Long deptId) {
		SqlSession session = sessionFactory.openSession();
		ShiroUserGroupRoleDao shiroUserGroupRoleDao=session.getMapper(ShiroUserGroupRoleDao.class);
		Set<ShiroUserGroupRole> shiroUserGroupRoles=shiroUserGroupRoleDao.loadAllForShiroGroupId(deptId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return shiroUserGroupRoles;
	}

	@Override
	public Set<ShiroRole> getShiroRolesByIds(List<Long> shiroRoleIds) {
		SqlSession session = sessionFactory.openSession();
		ShiroRoleDao shiroRoleDao=session.getMapper(ShiroRoleDao.class);
		Set<ShiroRole> shiroRoles=shiroRoleDao.loadAllShiroRolesForIds(shiroRoleIds);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return shiroRoles;
	}

	@Override
	public int addSystemUser(ShiroUser shiroUser,String password, SystemUser systemUser, MyShiroUserGroupRole myShiroUserGroupRole) {
		int result=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date creatTime=null;
		try {
			creatTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		Random random = new Random();
		ShiroEncrypt.setSaltSource(password+random.nextInt(100));
		shiroUser.setSalt(ShiroEncrypt.getSaltSource());
		shiroUser.setPassword(ShiroEncrypt.getEncrptPassword(password));
		shiroUser.setCreateTime(creatTime);
		shiroUser.setUpdateTime(creatTime);
		SqlSession session = sessionFactory.openSession();
		ShiroUserDao shiroUserDao=session.getMapper(ShiroUserDao.class);
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		MyShiroUserGroupRoleDao myShiroUserGroupRoleDao=session.getMapper(MyShiroUserGroupRoleDao.class);
		if(shiroUserDao.loadForUsername(shiroUser.getUsername())!=null){
			return 0;
		}
		try {
			int status1=shiroUserDao.addShiroUser(shiroUser);
			if(status1<=0){
				session.rollback();
			}else {
				session.commit();
				status1=1;
			}
			systemUser.setShiroUserId(shiroUser.getId());
			systemUser.setCreateUser(UserUtil.getShiroUser().getId());
			systemUser.setCreatTime(creatTime);
			systemUser.setChangeUser(UserUtil.getShiroUser().getId());
			systemUser.setChangeTime(creatTime);
			systemUser.setLastLoginTime(creatTime);
			myShiroUserGroupRole.setShiroUserId(shiroUser.getId());
			int status2=systemUserDao.addSystemUser(systemUser);
			if (status2<=0) {
				session.rollback();
			}
			else {
				session.commit();
				status2=1;
			}
			int status3=myShiroUserGroupRoleDao.addShiroUserGroupRole(myShiroUserGroupRole);
			if (status3<=0) {
				session.rollback();
			}
			else {
				session.commit();
				status3=1;
			}
			if(status1==1&&status2==1&&status3==1){
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
	public List<MyUser> getUserForPage(int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPage(pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public int getUserCount() {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		int count;
		count=systemUserDao.getUserCount();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

	@Override
	public int updateUserIsLock(ShiroUser shiroUser) {
		int result=0;
		SqlSession session = sessionFactory.openSession();
		ShiroUserDao shiroUserDao=session.getMapper(ShiroUserDao.class);
		try {
			int status=shiroUserDao.updateUserIsLock(shiroUser);
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
	public int updateSystemUser(ShiroUser shiroUser, String unEditUsername, String password, SystemUser systemUser,
			MyShiroUserGroupRole myShiroUserGroupRole) {
		int result=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date updateTime=null;
		try {
			updateTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		Random random = new Random();
		ShiroEncrypt.setSaltSource(password+random.nextInt(100));
		shiroUser.setSalt(ShiroEncrypt.getSaltSource());
		shiroUser.setPassword(ShiroEncrypt.getEncrptPassword(password));
		shiroUser.setUpdateTime(updateTime);
		SqlSession session = sessionFactory.openSession();
		ShiroUserDao shiroUserDao=session.getMapper(ShiroUserDao.class);
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		MyShiroUserGroupRoleDao myShiroUserGroupRoleDao=session.getMapper(MyShiroUserGroupRoleDao.class);
		if (unEditUsername!=null) {
			if (!(unEditUsername.equals(shiroUser.getUsername()))) {
				if(shiroUserDao.loadForUsername(shiroUser.getUsername())!=null){
					return 0;
				}
			}
		}
		try {
			int status1=shiroUserDao.updateShiroUser(shiroUser);
			if(status1<=0){
				session.rollback();
			}else {
				status1=1;
			}
			systemUser.setChangeUser(UserUtil.getShiroUser().getId());
			systemUser.setChangeTime(updateTime);
			int status2=systemUserDao.updateSystemUser(systemUser);
			if (status2<=0) {
				session.rollback();
			}
			else {
				status2=1;
			}
			int status3=myShiroUserGroupRoleDao.updateShiroUserGroupRole(myShiroUserGroupRole);
			if (status3<=0) {
				session.rollback();
			}
			else {
				status3=1;
			}
			if(status1==1&&status2==1&&status3==1){
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
	public int deleteSystemUser(ShiroUser shiroUser, SystemUser systemUser, MyShiroUserGroupRole myShiroUserGroupRole) {
		int result=0;
		SqlSession session = sessionFactory.openSession();
		ShiroUserDao shiroUserDao=session.getMapper(ShiroUserDao.class);
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		MyShiroUserGroupRoleDao myShiroUserGroupRoleDao=session.getMapper(MyShiroUserGroupRoleDao.class);
		try {
			int status2=systemUserDao.deleteSystemUserById(systemUser);
			if (status2<=0) {
				session.rollback();
			}
			else {
				status2=1;
			}
			int status3=myShiroUserGroupRoleDao.deleteShiroUserGroupRoleById(myShiroUserGroupRole);
			if (status3<=0) {
				session.rollback();
			}
			else {
				status3=1;
			}
			int status1=shiroUserDao.deleteShiroUserById(shiroUser);
			if(status1<=0){
				session.rollback();
			}else {
				status1=1;
			}
			if(status1==1&&status2==1&&status3==1){
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
	public List<MyUser> getUserForPageByName(String uNameOrpNname, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByName(uNameOrpNname, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getUserForPageByUnitId(Long unitId, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByUnitId(unitId, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getUserForPageByDeptId(Long unitId, Long deptId, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByDeptId(unitId, deptId, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getUserForPageByNameAndUId(String uNameOrpNname, Long unitId, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByNameAndUId(uNameOrpNname, unitId, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getUserForPageByAllS(String uNameOrpNname, Long unitId, Long deptId, int pageIndex,
			int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByAllS(uNameOrpNname, unitId, deptId, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public int getUserCountByName(String uNameOrpNname) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByName(uNameOrpNname);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public int getUserCountByUnitId(Long unitId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByUnitId(unitId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public int getUserCountByDeptId(Long unitId, Long deptId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByDeptId(unitId, deptId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public int getUserCountByNameAndUId(String uNameOrpNname, Long unitId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByNameAndUId(uNameOrpNname, unitId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public int getUserCountByAllS(String uNameOrpNname, Long unitId, Long deptId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByAllS(uNameOrpNname, unitId, deptId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public List<MyUser> getUserForPageByRoleId(Long shiroRoleId, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByRoleId(shiroRoleId, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getUserForPageByUserType(String userType, int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserForPageByUserType(userType, pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getAllUserByRoleId(Long shiroRoleId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getAllUserByRoleId(shiroRoleId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public List<MyUser> getAllUserByUserType(String userType) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getAllUserByUserType(userType);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers;
	}

	@Override
	public int getAllUserCountByRoleId(Long shiroRoleId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getAllUserByRoleId(shiroRoleId);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public int getAllUserCountByUserType(String userType) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getAllUserByUserType(userType);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUsers.size();
	}

	@Override
	public MyUser getUserByShiroUserId(Long shiroUserId) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByShiroUserId(shiroUserId);
		MyUser myUser=null;
		if(!myUsers.isEmpty()){
			myUser=myUsers.get(0);
		}
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUser;
	}

	@Override
	public MyUser getUserByUserName(String userName) {
		SqlSession session = sessionFactory.openSession();
		SystemUserDao systemUserDao=session.getMapper(SystemUserDao.class);
		List<MyUser> myUsers=systemUserDao.getUserByUserName(userName);
		MyUser myUser=null;
		if(!myUsers.isEmpty()){
			myUser=myUsers.get(0);
		}
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return myUser;
	}

}
