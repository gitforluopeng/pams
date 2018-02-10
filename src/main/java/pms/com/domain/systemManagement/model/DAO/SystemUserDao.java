package pms.com.domain.systemManagement.model.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pms.com.domain.systemManagement.model.SystemUser;
import pms.com.domain.systemManagement.model.MyUser;
public interface SystemUserDao {
	
	/**
	 * 
	 * Task : 添加用户信息
	 * @param systemUser SystemUser类型的实体对象
	 * @return  添加失败返回0，反之添加成功
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public int addSystemUser(SystemUser systemUser);
	/**
	 * 
	 * Task : 分页查询用户信息
	 * @param pageIndex 当前显示位置索引
	 * @param pageSize 分页大小
	 * @return MyUser类型的实体对象
	 * date :2017年12月2日
	 * @author luopeng
	 */
	public List<MyUser> getUserForPage(@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByName(@Param(value = "uNameOrpNname") String uNameOrpNname,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByUnitId(@Param(value = "unitId") Long unitId,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByDeptId(@Param(value = "unitId") Long unitId,@Param(value = "deptId") Long deptId,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByNameAndUId(@Param(value = "uNameOrpNname") String uNameOrpNname,@Param(value = "unitId") Long unitId,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByAllS(@Param(value = "uNameOrpNname") String uNameOrpNname,@Param(value = "unitId") Long unitId,@Param(value = "deptId") Long deptId,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	//根据用类型查
	public List<MyUser> getUserForPageByRoleId(@Param(value = "shiroroleid") Long shiroRoleId,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	public List<MyUser> getUserForPageByUserType(@Param(value = "userType") String userType,@Param(value = "pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize);
	//不分页查
	public List<MyUser> getUserByName(@Param(value = "uNameOrpNname") String uNameOrpNname);
	public List<MyUser> getUserByUnitId(@Param(value = "unitId") Long unitId);
	public List<MyUser> getUserByDeptId(@Param(value = "unitId") Long unitId,@Param(value = "deptId") Long deptId);
	public List<MyUser> getUserByNameAndUId(@Param(value = "uNameOrpNname") String uNameOrpNname,@Param(value = "unitId") Long unitId);
	public List<MyUser> getUserByAllS(@Param(value = "uNameOrpNname") String uNameOrpNname,@Param(value = "unitId") Long unitId,@Param(value = "deptId") Long deptId);
	//根据用户类型查不分页
	public List<MyUser> getAllUserByRoleId(@Param(value = "shiroroleid") Long shiroRoleId);
	public List<MyUser> getAllUserByUserType(@Param(value = "userType") String userType);
	/**
	 * 
	 * Task : 查询用户总数
	 * @return 用户总数
	 * date :2017年12月2日
	 * @author luopeng
	 */
	public Integer getUserCount();
	
	/**
	 * 
	 * Task : 修改用户
	 * @param systemUser SystemUser类型的实体对象
	 * @return 修改失败返回0，反之修改成功
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int updateSystemUser(SystemUser systemUser);
	/**
	 * 
	 * Task : 通过shiroUserId查找SystemUser对象
	 * @param shiroUserId shiroUserId
	 * @return SystemUser类型的实体对象
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public SystemUser getSystemUserByShiroUserId(Long shiroUserId);
	/**
	 * 
	 * Task : 通过id删除SystemUser对象
	 * @param systemUser SystemUser类型的实体对象
	 * @return 失败返回0，反之成功
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public int deleteSystemUserById(SystemUser systemUser);
	/**
	 * 
	 * Task : 通过shiroUserId查询
	 * @param shiroUserId shiroUserId
	 * @return List<MyUser> 对象
	 * date :2017年12月13日
	 * @author luopeng
	 */
	public List<MyUser> getUserByShiroUserId(Long shiroUserId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: SystemUserDao.java
	 * Description: 根据用户名获得MyUser对象
	 * <date> - <desc>
	 * 2017年12月18日
	 */
	public List<MyUser> getUserByUserName(String userName);
}
