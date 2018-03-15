package pms.com.domain.analyze.model.DAO;

import java.util.List;
import java.util.Map;

import pms.com.domain.analyze.model.EduCount;

public interface EduCountDao {
	public List<EduCount> getEduCount();
	public Integer getAllCount();
	public List<EduCount> getAttendanceCount(Map<String, Object> map);
}
