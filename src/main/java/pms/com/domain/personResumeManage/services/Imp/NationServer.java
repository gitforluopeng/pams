package pms.com.domain.personResumeManage.services.Imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.personResumeManage.model.Nation;
import pms.com.domain.personResumeManage.model.DAO.NationDao;
import pms.com.domain.personResumeManage.services.NationServiceInter;
import pms.com.utils.LoggerUtil;
@Service
public class NationServer implements NationServiceInter {
	@Resource
	private SqlSessionFactory sessionFactory;
	
	@Override
	public List<Nation> getNations() {
		SqlSession session = sessionFactory.openSession();
		NationDao nationDao=session.getMapper(NationDao.class);
		List<Nation> nations=nationDao.getNations();
		try {
			session.close();
		} catch (Exception e) {
			LoggerUtil.consleLogger.error(e);
		}
		return nations;
	}

}
