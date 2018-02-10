package pms.com.system.shiro.model.DAO;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import pms.com.system.shiro.model.ShiroRole;

public interface ShiroRoleDao {
	
	/**
	 * Task : 通过shiroRoleId 获取 权限集合
	 * @param shiroRoleIds 要获取的权限对象的id 集合
	 * @return 如果有则返回 set 集合，反之null
	 * date :2017年10月8日
	 * @author libo
	 */
	public Set<ShiroRole> loadAllShiroRolesForIds(List<Long> shiroRoleIds);
	
	/**
	 * Task : 加载所有shiroRole 对象 
	 * @return 如果加载成功返回set集合， 反之null
	 * date :2017年10月8日
	 * @author libo
	 */
	public Set<ShiroRole> loadAllShiroRoles();
	
	/**
	 * Task : 通过用户主键查询用户拥有的权限
	 * @param id 用户主键
	 * @return 
	 * date :2017年12月5日
	 * @author libo
	 */
	public Set<ShiroRole> loadUserRolesForId(Long id);
	/**
	 * 
	 * Task : 分页查询角色信息
	 * @param pageIndex 当前显示位置索引
	 * @param pageSize 分页大小
	 * @return List<ShiroRole>类型的集合对象
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroRole> getAllRoleForPage(@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	/**
	 * 
	 * Task : 得到所有角色信息总数
	 * @return 角色个数
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public Integer getAllRoleCount();
	/**
	 * 
	 * Task : 添加系统角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 添加失败返回0，反之添加成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int addRole(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 通过角色名称查询角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return List<ShiroRole>类型的集合对象
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroRole> getRoleByName(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 删除角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 删除失败返回0，反之删除成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int deleteRole(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 编辑角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 编辑失败返回0，反之编辑成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int updateRole(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 更改是否启用改角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int updateRoleIsUse(ShiroRole shiroRole);
	
}

