package pms.com.domain.infosManagement.model.DAO;

import java.util.List;
import java.util.Map;

import pms.com.domain.infosManagement.model.Infos;

public interface InfosDao {
	public int addInfos(Infos infos);
	public int deleteInfos(Infos infos);
	public List<Infos> getInfos(Map<String, Object> map);
	public Integer getInfosCount(Map<String, Object> map);
}
