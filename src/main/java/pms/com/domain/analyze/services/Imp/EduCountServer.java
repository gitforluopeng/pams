package pms.com.domain.analyze.services.Imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import pms.com.domain.analyze.model.EduCount;
import pms.com.domain.analyze.model.DAO.EduCountDao;
import pms.com.domain.analyze.services.EduCountServiceInter;
@Service
public class EduCountServer implements EduCountServiceInter {
	
	@Resource
	private SqlSessionFactory sessionFactory;
	
	@Override
	public List<Map<String, Object>> getEduCountEcharData() {
		SqlSession session = sessionFactory.openSession();
		EduCountDao eduCountDao=session.getMapper(EduCountDao.class);
		List<EduCount> eduCounts=eduCountDao.getEduCount();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		if(!eduCounts.isEmpty()){
			for (EduCount eduCount : eduCounts) {
				Map<String, Object> map=eduCount.toMap();
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getAttCountEcharData(Map<String, Object> map) {
		SqlSession session = sessionFactory.openSession();
		EduCountDao eduCountDao=session.getMapper(EduCountDao.class);
		Map<String, Object> result=new HashMap<String, Object>();
		List<Integer> allPersonCount=new ArrayList<Integer>();
		List<String>  allAttName=new ArrayList<String>();
		List<EduCount> eduCounts=eduCountDao.getAttendanceCount(map);
		if (!eduCounts.isEmpty()) {
			for (EduCount eduCount : eduCounts) {
				allPersonCount.add(eduCount.getValue());
				allAttName.add(eduCount.getName());
			}
		}
		result.put("value", allPersonCount);
		result.put("name", allAttName);
		return result;
	}

}
