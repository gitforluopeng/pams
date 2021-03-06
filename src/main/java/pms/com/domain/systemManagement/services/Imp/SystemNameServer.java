package pms.com.domain.systemManagement.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import pms.com.domain.systemManagement.model.SystemName;
import pms.com.domain.systemManagement.model.DAO.SystemNameDao;
import pms.com.domain.systemManagement.services.SystemNameServiceInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.LoggerUtil;
@Service
public class SystemNameServer implements SystemNameServiceInter {
	
	@Resource
	private SqlSessionFactory sessionFactory;
	@Override
	public int addSystemName(SystemName systemName) {
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		int result=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date creatTime=null;
		try {
			creatTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		if (!(systemNamedao.getSystemNameByName(systemName).isEmpty())) {
			return 0;
		}
		systemName.setCreateUser(UserUtil.getUserName());
		systemName.setCreateTime(creatTime);
		systemName.setChangeUser(UserUtil.getUserName());
		systemName.setChangeTime(creatTime);
		try {
			int status=systemNamedao.addSystemName(systemName);
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
	public int updateSystemName(SystemName systemName,String unEditName) {
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		int result=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date changeTime=null;
		try {
			changeTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		if (unEditName!=null) {
			if (!(unEditName.equals(systemName.getName()))) {
				if (!(systemNamedao.getSystemNameByName(systemName).isEmpty())) {
					return 0;
				}
			}
		}
		systemName.setChangeUser(UserUtil.getUserName());
		systemName.setChangeTime(changeTime);
		try {
			int status=systemNamedao.updateSystemName(systemName);
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
	public int updateSystemIsUse(SystemName systemName) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date changeTime=null;
		try {
			changeTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		if (systemName.getIsUse()) {
			List<SystemName> systemNames=systemNamedao.getSystemNameByIsUse(new SystemName(true));
			if (!(systemNames.isEmpty())) {
				return 0;
			}
		}
		try {
			systemName.setChangeUser(UserUtil.getUserName());
			systemName.setChangeTime(changeTime);
			int status=systemNamedao.updateSystemIsUse(systemName);
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
	public int deleteSystemName(SystemName systemName) {
		int result=-1;
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		if (systemName.getIsUse()) {
			return 0;
		}
		try {
			int status=systemNamedao.deleteSystemName(systemName);
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
	public List<SystemName> getAllSystemName(int pageIndex, int pageSize) {
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		List<SystemName> systemNames= systemNamedao.getAllSystemName(pageIndex*pageSize, pageSize);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return systemNames;
	}

	@Override
	public SystemName getSystemNameByIsUse() {
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		List<SystemName> systemNames=systemNamedao.getSystemNameByIsUse(new SystemName(true));
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		if (systemNames.isEmpty()) {
			return null;
		}
		return systemNames.get(0);
	}

	@Override
	public int getSystemNameCount() {
		SqlSession session = sessionFactory.openSession();
		SystemNameDao systemNamedao = session.getMapper(SystemNameDao.class);
		int count;
		count=systemNamedao.getSystemNameCount();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
