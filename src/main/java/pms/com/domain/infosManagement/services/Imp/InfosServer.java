package pms.com.domain.infosManagement.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.infosManagement.model.Infos;
import pms.com.domain.infosManagement.model.DAO.InfosDao;
import pms.com.domain.infosManagement.services.InfosServiceInter;
import pms.com.domain.systemManagement.services.Imp.SystemUserServer;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.LoggerUtil;
@Service
public class InfosServer implements InfosServiceInter {
	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	
	@Override
	public int addInfos(Infos infos) {
		SqlSession session = sessionFactory.openSession();
		InfosDao infosDao=session.getMapper(InfosDao.class);
		int result=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow=sdf.format(new Date());
		Date createTime=null;
		try {
			createTime = sdf.parse(dateNow);
		} catch (ParseException e) {
			LoggerUtil.consleLogger.error(e);
		}
		Long createUserId=UserUtil.getShiroUser().getId();
		String createUserName=UserUtil.getUserName();
		String createPersonName=systemUserServer.getUserByUserName(createUserName).getPersonName();
		infos.setCreateUserId(createUserId);
		infos.setCreateUserName(createUserName);
		infos.setCreatePersonName(createPersonName);
		infos.setCreateTime(createTime);
		try {
			int status=infosDao.addInfos(infos);
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
	public int deleteInfos(Infos infos) {
		SqlSession session = sessionFactory.openSession();
		InfosDao infosDao=session.getMapper(InfosDao.class);
		int result=-1;
		try {
			int status=infosDao.deleteInfos(infos);
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
	public List<Infos> getInfos(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		InfosDao infosDao=session.getMapper(InfosDao.class);
		List<Infos> infos=infosDao.getInfos(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return infos;
	}

	@Override
	public Integer getInfosCount(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		InfosDao infosDao=session.getMapper(InfosDao.class);
		int count;
		count=infosDao.getInfosCount(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
