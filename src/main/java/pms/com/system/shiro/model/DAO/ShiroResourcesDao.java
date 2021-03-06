package pms.com.system.shiro.model.DAO;

import java.util.List;

import pms.com.system.shiro.model.ShiroResources;

public interface ShiroResourcesDao {
	
	/**
	 * Task : 通过权限id 获取资源对象
	 * @param shiroRoleIds 权限id list 集合
	 * @return 如果有则返回 资源对象list集合，反之null
	 * date :2017年10月8日
	 * @author libo
	 */
	public List<ShiroResources> loadAllForShiroRoleIds(List<Long> shiroRoleIds);
	
	/**
	 * Task : 
	 * @return
	 * date :2017年10月8日
	 * @author libo
	 */
	public List<ShiroResources> loadAllShiroResources();
	
	/**
	 * Task : 通过权限id，获取该权限下的所有菜单
	 * @param shiroRoleIds 权限id list 集合
	 * @return 如果有则返回 资源对象list集合，反之null
	 * date :2017年10月8日
	 * @author libo
	 */
	public List<ShiroResources> loadMenus(List<Long> shiroRoleIds);
	
	/**
	 * Task : 加载角色所有资源
	 * @return
	 * date :2017年10月8日
	 * @author 罗鹏
	 */
	public List<ShiroResources> loadAllShiroResourcesByRoleId();
	/**
	 * Task : 通过权限id 获取资源对象
	 * @param shiroRoleIds 权限id list 集合
	 * @return 如果有则返回 资源对象list集合，反之null
	 * date :2017年10月8日
	 * @author 罗鹏
	 */
	public List<ShiroResources> loadAllByShiroRoleId(Long shiroRoleId);
	
}
