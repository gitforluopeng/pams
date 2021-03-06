package pms.com.domain.systemManagement.model.DAO;

import pms.com.domain.systemManagement.model.MyShiroUserGroupRole;

public interface MyShiroUserGroupRoleDao {
	
	/**
	 * 
	 * Task : 添加用户组角色
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return 添加失败返回0，反之添加成功
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public int addShiroUserGroupRole(MyShiroUserGroupRole myShiroUserGroupRole);
	/**
	 * 
	 * Task : 更新用户角色
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return 更新失败返回0，反之更新成功
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int updateShiroUserGroupRole(MyShiroUserGroupRole myShiroUserGroupRole);
	/**
	 * 
	 * Task : 通过id删除MyShiroUserGroupRole对象
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public int deleteShiroUserGroupRoleById(MyShiroUserGroupRole myShiroUserGroupRole);
}
