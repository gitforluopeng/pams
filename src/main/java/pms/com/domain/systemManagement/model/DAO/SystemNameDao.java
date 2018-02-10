package pms.com.domain.systemManagement.model.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pms.com.domain.systemManagement.model.SystemName;

public interface SystemNameDao {
	/**
	 * 
	 * Task : 添加系统名称
	 * @param systemName  SystemName类型的实体对象
	 * @return 添加失败返回0，反之添加成功
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public int addSystemName(SystemName systemName);
	/**
	 * 
	 * Task : 修改系统名称
	 * @param systemName SystemName类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public int updateSystemName(SystemName systemName);
	/**
	 * 
	 * Task : 修改是否启用
	 * @param systemName SystemName类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public int updateSystemIsUse(SystemName systemName);
	/**
	 * 
	 * Task : 删除系统名称
	 * @param systemName SystemName类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public int deleteSystemName(SystemName systemName);
	/**
	 * 
	 * Task : 查询所有系统名称
	 * @return List<SystemName>类型的实体对象集合
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public List<SystemName> getAllSystemName(@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	/**
	 * 
	 * Task : 得到系统名称总数
	 * @return 系统名称总数
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public Integer getSystemNameCount();
	/**
	 * 
	 * Task : 查询已经启用的体统名称
	 * @param systemName SystemName类型的实体对象
	 * @return List<SystemName>类型的实体对象集合
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public List<SystemName> getSystemNameByIsUse(SystemName systemName);
	/**
	 * 
	 * Task : 根据系统名称查询
	 * @param systemName SystemName类型的实体对象
	 * @return List<SystemName>类型的实体对象集合
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public List<SystemName> getSystemNameByName(SystemName systemName);
}
