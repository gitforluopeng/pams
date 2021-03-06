package pms.com.domain.trainingPlan.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.systemManagement.services.Imp.OrganizeServer;
import pms.com.domain.systemManagement.services.Imp.SystemUserServer;
import pms.com.domain.trainingPlan.model.TrainPlan;
import pms.com.domain.trainingPlan.model.DAO.TrainPlanDao;
import pms.com.domain.trainingPlan.services.TrainPlanServiceInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.LoggerUtil;
@Service
public class TrainPlanServer implements TrainPlanServiceInter {
	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	@Resource
	private OrganizeServer organizeServer;
	
	@Override
	public int addTrainPlan(TrainPlan trainPlan) {
		SqlSession session = sessionFactory.openSession();
		TrainPlanDao trainPlanDao=session.getMapper(TrainPlanDao.class);
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
		String unitName=organizeServer.getManagementById(trainPlan.getUnitId()).getName();
		String deptName=organizeServer.getManagementById(trainPlan.getDeptId()).getName();
		trainPlan.setUnitName(unitName);
		trainPlan.setDeptName(deptName);
		trainPlan.setCreateUserId(createUserId);
		trainPlan.setCreateUserName(createUserName);
		trainPlan.setCreatePersonName(createPersonName);
		trainPlan.setCreateTime(createTime);
		try {
			int status=trainPlanDao.addTrainPlan(trainPlan);
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
	public int updateTrainPlan(TrainPlan trainPlan) {
		SqlSession session = sessionFactory.openSession();
		TrainPlanDao trainPlanDao=session.getMapper(TrainPlanDao.class);
		int result=-1;
		try {
			int status=trainPlanDao.updateTrainPlan(trainPlan);
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
	public int deleteTrainPlan(TrainPlan trainPlan) {
		SqlSession session = sessionFactory.openSession();
		TrainPlanDao trainPlanDao=session.getMapper(TrainPlanDao.class);
		int result=-1;
		try {
			int status=trainPlanDao.deleteTrainPlan(trainPlan);
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
	public List<TrainPlan> getTrainPlans(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		TrainPlanDao trainPlanDao=session.getMapper(TrainPlanDao.class);
		List<TrainPlan> trainPlans=trainPlanDao.getTrainPlans(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return trainPlans;
	}

	@Override
	public Integer getTrainPlansCount(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		TrainPlanDao trainPlanDao=session.getMapper(TrainPlanDao.class);
		int count;
		count=trainPlanDao.getTrainPlansCount(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
