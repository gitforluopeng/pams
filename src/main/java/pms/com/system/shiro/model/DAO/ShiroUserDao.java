package pms.com.system.shiro.model.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pms.com.system.shiro.model.ShiroUser;

public interface ShiroUserDao {
	
	
	public ShiroUser loadForUsername(String username);
	/**
	 * 
	 * Task : 添加用户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @return 添加失败返回0,反之添加成功
	 * date :2017年11月29日
	 * @author luopeng
	 */
	public int addShiroUser(ShiroUser shiroUser);
	/**
	 * 
	 * Task : 是否锁定账户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @return 修改失败返回0，反之修改成功
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int updateUserIsLock(ShiroUser shiroUser);
	/**
	 * 
	 * Task : 更新用户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @return 更新失败返回0，反之更新成功
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int updateShiroUser(ShiroUser shiroUser);
	/**
	 * 
	 * Task : 根据id查找shiroUser对象
	 * @param shiroUserId  id
	 * @return  ShiroUser类型的实体对象
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public List<ShiroUser> getShiroUserById(@Param(value = "shiroUserGropId") Long shiroUserGropId,@Param(value = "pageIndex") Integer pageIndex,@Param(value = "pageSize") Integer pageSize);
	/**
	 * 
	 * Task : 通过id删除ShiroUser对象
	 * @param shiroUser ShiroUser类型的实体对象
	 * @return 删除失败返回0，反之删除成功
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public int deleteShiroUserById(ShiroUser shiroUser);
}
