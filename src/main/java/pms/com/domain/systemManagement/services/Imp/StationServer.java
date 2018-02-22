package pms.com.domain.systemManagement.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.systemManagement.model.Station;
import pms.com.domain.systemManagement.model.DAO.StationDao;
import pms.com.domain.systemManagement.services.StationServiceInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.LoggerUtil;
@Service
public class StationServer implements StationServiceInter {
	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	@Override
	public int addStation(Station station) {
		SqlSession session = sessionFactory.openSession();
		StationDao stationDao=session.getMapper(StationDao.class);
		if(!(stationDao.getStationByName(station).isEmpty())){
		    return 0;
		}
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
		station.setCreateUserId(createUserId);
		station.setCreateUserName(createUserName);
		station.setCreatePersonName(createPersonName);
		station.setCreateTime(createTime);
		try {
			int status=stationDao.addStation(station);
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
	public int updateStation(Station station,String unEditName) {
		SqlSession session = sessionFactory.openSession();
		StationDao stationDao=session.getMapper(StationDao.class);
		if (unEditName!=null) {
			if (!(unEditName.equals(station.getStationName()))) {
				if(!(stationDao.getStationByName(station).isEmpty())){
				    return 0;
				}
			}
		}
		int result=-1;
		try {
			int status=stationDao.updateStation(station);
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
	public int deleteStation(Station station) {
		SqlSession session = sessionFactory.openSession();
		StationDao stationDao=session.getMapper(StationDao.class);
		int result=-1;
		try {
			int status=stationDao.deleteStation(station);
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
	public List<Station> getStation(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		StationDao stationDao=session.getMapper(StationDao.class);
		List<Station> stations=stationDao.getStation(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return stations;
	}

	@Override
	public int getStationCount(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		StationDao stationDao=session.getMapper(StationDao.class);
		int count;
		count=stationDao.getStationCount(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
