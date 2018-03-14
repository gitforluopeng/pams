package pms.com.domain.salaryManage.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.salaryManage.model.Salary;
import pms.com.domain.salaryManage.model.DAO.SalaryDao;
import pms.com.domain.salaryManage.services.SalaryServiceInter;
import pms.com.domain.systemManagement.services.Imp.SystemUserServer;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.DateUtil;
import pms.com.utils.LoggerUtil;
@Service
public class SalaryServer implements SalaryServiceInter {
	
	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	
	@Override
	public int addSalary(Salary salary) {
		SqlSession session = sessionFactory.openSession();
		SalaryDao salaryDao = session.getMapper(SalaryDao.class);
		String payTime=salary.getPayTime();
		Integer year=DateUtil.getYear(payTime);
		Integer month=DateUtil.getMonth(payTime);
		salary.setYear(year);
		salary.setMonth(month);
		if(!salaryDao.getSalarysByTime(salary).isEmpty()){
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
		salary.setCreateUserId(createUserId);
		salary.setCreateUserName(createUserName);
		salary.setCreatePersonName(createPersonName);
		salary.setCreateTime(createTime);
		try {
			int status=salaryDao.addSalary(salary);
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
	public int updateSalary(Salary salary) {
		SqlSession session = sessionFactory.openSession();
		SalaryDao salaryDao = session.getMapper(SalaryDao.class);
		int result=-1;
		try {
			int status=salaryDao.updateSalary(salary);
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
	public int deleteSalary(Salary salary) {
		SqlSession session = sessionFactory.openSession();
		SalaryDao salaryDao = session.getMapper(SalaryDao.class);
		int result=-1;
		try {
			int status=salaryDao.deleteSalary(salary);
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
	public List<Salary> getSalarys(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		SalaryDao salaryDao = session.getMapper(SalaryDao.class);
		List<Salary> salaries = salaryDao.getSalarys(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return salaries;
	}

	@Override
	public Integer getSalarysCount(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		SalaryDao salaryDao = session.getMapper(SalaryDao.class);
		int count;
		count=salaryDao.getSalarysCount(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
