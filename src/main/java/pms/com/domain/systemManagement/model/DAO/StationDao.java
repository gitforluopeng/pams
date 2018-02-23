package pms.com.domain.systemManagement.model.DAO;

import java.util.List;
import java.util.Map;

import pms.com.domain.systemManagement.model.Station;

public interface StationDao {
	/**
	 * 
	 *
	 * Task:添加岗位
	 * @param station Station类型的对象
	 * @return 添加失败返回0，繁殖添加成功
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int addStation(Station station);
	/**
	 * 
	 *
	 * Task:编辑岗位
	 * @param station Station类型的对象
	 * @return 失败返回0，反之成功
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int updateStation(Station station);
	/**
	 * 
	 *
	 * Task:删除岗位
	 * @param station Station类型的对象
	 * @return 失败返回0，反之成功
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int deleteStation(Station station);
	/**
	 * 
	 *
	 * Task:条件查询岗位
	 * @param station Station类型的对象
	 * @return Station类型的集合对象
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public List<Station> getStation(Map<String, Object> map);
	/**
	 * 
	 *
	 * Task:条件查询得到个数
	 * @param station
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public Integer getStationCount(Map<String, Object> map);
	/**
	 * 
	 *
	 * Task:通过岗位名称查询岗位
	 * @param station Station类型的对象
	 * @return Station类型的集合对象
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public List<Station> getStationByName(Station station);
}
