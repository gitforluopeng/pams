package pms.com.system.shiro.model.DAO;

import java.util.List;

import pms.com.system.shiro.model.ShiroResourcesRoles;

public interface ShiroResourcesRolesDao {
	/**
	 * 
	 * Task :为角色添加资源 
	 * @param shiroResourcesRoles ShiroResourcesRoles类型的实体对象
	 * @return 添加失败返回0，反之成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int addResouceRole(ShiroResourcesRoles shiroResourcesRoles);
	/**
	 * 
	 * Task : 对角色删除资源
	 * @param shiroResourcesRoles ShiroResourcesRoles类型的实体对象
	 * @return 添加失败返回0，反之成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int deleteResouceRole(ShiroResourcesRoles shiroResourcesRoles);
	/**
	 * 
	 * Task : 查询角色是否已有该资源
	 * @param shiroResourcesRole ShiroResourcesRoles类型的实体对象
	 * @return ShiroResourcesRoles类型的实体对象集合
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroResourcesRoles> getShiroResourcesRolesByRoleId(ShiroResourcesRoles shiroResourcesRole);
}
