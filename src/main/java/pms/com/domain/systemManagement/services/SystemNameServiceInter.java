package pms.com.domain.systemManagement.services;

import java.util.List;

import pms.com.domain.systemManagement.model.SystemName;

public interface SystemNameServiceInter {
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
	public int updateSystemName(SystemName systemName,String unEditName);
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
	public List<SystemName> getAllSystemName(int pageIndex, int pageSize);
	/**
	 * 
	 * Task : 得到系统名称总数
	 * @return 系统名称总数
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public int getSystemNameCount();
	/**
	 * 
	 * Task : 查询已经启用的系统名称
	 * @param systemName SystemName类型的实体对象
	 * @return List<SystemName>类型的实体对象集合
	 * date :2017年12月5日
	 * @author luopeng
	 */
	public SystemName getSystemNameByIsUse();
}
