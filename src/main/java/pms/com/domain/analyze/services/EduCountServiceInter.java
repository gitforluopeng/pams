package pms.com.domain.analyze.services;

import java.util.List;
import java.util.Map;

public interface EduCountServiceInter {
	public List<Map<String, Object>> getEduCountEcharData();
	public Map<String, Object> getAttCountEcharData(Map<String, Object> map);
}
