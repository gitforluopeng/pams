package pms.com.domain.systemManagement.services.Imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.systemManagement.model.OperationLog;
import pms.com.domain.systemManagement.model.DAO.OperationLogDao;
import pms.com.domain.systemManagement.services.OperationLogInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.IpUtil;
@Service
public class OperationLogServer implements OperationLogInter {

	@Resource
	private SqlSessionFactory sessionFactory;
	@Resource
	private SystemUserServer systemUserServer;
	
	
	@Override
	public int insertOperationLog(String record,HttpServletRequest request) {
		OperationLog operationLog = new OperationLog();
		operationLog.setIp(IpUtil.getIp(request));
		operationLog.setOperationUser(UserUtil.getUserName());
		operationLog.setOperationDate(new Date());
		operationLog.setOperationRecord(record);
		String userName = systemUserServer.getUserByUserName(UserUtil.getUserName()).getPersonName();
		operationLog.setUserName(userName);
		SqlSession session = sessionFactory.openSession();
		OperationLogDao operationLogDao = session.getMapper(OperationLogDao.class);
		int result = 0;
		if(operationLog != null){
			try{
				int status = operationLogDao.insertOperationLog(operationLog);
				if(status > 0){
					result = 1;
					session.commit();
				}else{
					session.rollback();
				}
			}catch(Exception e){
				e.printStackTrace();
				session.rollback();
			}finally {
				session.close();
			}
		}
		return result;
	}

	@Override
	public List<OperationLog> getAlLogsForPage(Integer pageIndex, Integer pageSize) {
		SqlSession session = sessionFactory.openSession();
		OperationLogDao operationLogDao = session.getMapper(OperationLogDao.class);
		List<OperationLog> operationLogs = operationLogDao.getAlLogsForPage(pageIndex*pageSize, pageSize);
		session.close();
		return operationLogs;
	}

	@Override
	public int getLogsCount() {
		SqlSession session = sessionFactory.openSession();
		OperationLogDao operationLogDao = session.getMapper(OperationLogDao.class);
		int logCount = operationLogDao.getLogsCount();
		session.close();
		return logCount;
	}

	@Override
	public List<OperationLog> queryOperation(String username, String date, int i, Integer limit) {
		SqlSession session = sessionFactory.openSession();
		OperationLogDao operationLogDao = session.getMapper(OperationLogDao.class);
		List<OperationLog> operationLogs = 
				operationLogDao.queryOperationLogs(username+"%", date+"%", i*limit, limit);
		session.close();
		return operationLogs;
	}

	@Override
	public int queryCount(String username, String date) {
		SqlSession session = sessionFactory.openSession();
		OperationLogDao operationLogDao = session.getMapper(OperationLogDao.class);
		int count = operationLogDao.queryCount(username+"%", date+"%");
		session.close();
		return count;
	}

}
