package pms.com.domain.attendanceManage.services.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.attendanceManage.model.Attendance;
import pms.com.domain.attendanceManage.model.DAO.AttendanceDao;
import pms.com.domain.attendanceManage.services.AttendanceServiceInter;
import pms.com.domain.systemManagement.services.Imp.SystemUserServer;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.DateUtil;
import pms.com.utils.LoggerUtil;
@Service
public class AttendanceServer implements AttendanceServiceInter {

	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	
	@Override
	public int addAttendance(Attendance attendance) {
		SqlSession session = sessionFactory.openSession();
		AttendanceDao attendanceDao = session.getMapper(AttendanceDao.class);
		Date attendanceTime=attendance.getAttendanceTime();
		Integer year = DateUtil.getYear(attendanceTime);
		Integer month=DateUtil.getMonth(attendanceTime);
		Integer day=DateUtil.getDay(attendanceTime);
		attendance.setYear(year);
		attendance.setMonth(month);
		attendance.setDay(day);
		if(!(attendanceDao.getAttendancesByYMD(attendance)).isEmpty()){
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
		attendance.setCreateUserId(createUserId);
		attendance.setCreateUserName(createUserName);
		attendance.setCreatePersonName(createPersonName);
		attendance.setCreateTime(createTime);
		try {
			int status=attendanceDao.addAttendance(attendance);
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
	public List<Attendance> getAttendances(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		AttendanceDao attendanceDao = session.getMapper(AttendanceDao.class);
		List<Attendance> attendances=attendanceDao.getAttendances(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return attendances;
	}

	@Override
	public Integer getAttendancesCount(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		AttendanceDao attendanceDao = session.getMapper(AttendanceDao.class);
		int count;
		count=attendanceDao.getAttendancesCount(map);
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return count;
	}

}
