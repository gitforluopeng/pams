package pms.com.domain.systemManagement.services;

import java.util.List;
import java.util.Set;

import pms.com.domain.systemManagement.model.MyShiroUserGroupRole;
import pms.com.domain.systemManagement.model.MyUser;
import pms.com.domain.systemManagement.model.OrganizeManagement;
import pms.com.domain.systemManagement.model.SystemUser;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.model.ShiroUserGroupRole;

public interface SystemUserServiceInter {
	
	/**
	 * 
	 * Task : 加载所有单位
	 * @return List<OrganizeManagement>类型的实体对象集合
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public List<OrganizeManagement> getAllUnitList();
	/**
	 * 
	 * Task : 通过单位id查部门
	 * @param unitId 单位id
	 * @return List<OrganizeManagement>类型的实体对象集合
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public List<OrganizeManagement> getDeptByUnitId(Long unitId);
	/**
	 * 
	 * Task : 通过部门id查用户组角色
	 * @param deptId 部门id
	 * @return Set<ShiroUserGroupRole>类型的实体对象集合
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public Set<ShiroUserGroupRole> getShiroUserGroupRoleById(Long deptId);
	/**
	 * 
	 * Task : 通过shiroRoleId加载ShiroRole对象
	 * @param shiroRoleIds List<Long>类型的集合
	 * @return Set<ShiroRole>类型的实体对象集合
	 * date :2017年11月30日
	 * @author luopeng
	 */
	public Set<ShiroRole> getShiroRolesByIds(List<Long> shiroRoleIds);
	/**
	 * 
	 * Task : 添加用户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @param systemUser SystemUser类型的实体对象
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return  添加失败返回-1,，已有用户返回0,，添加成功返回1
	 * date :2017年12月1日
	 * @author luopeng
	 */
	public int addSystemUser(ShiroUser shiroUser,String password,SystemUser systemUser,MyShiroUserGroupRole myShiroUserGroupRole);
	/**
	 * 
	 * Task : 分页加载用户
	 * @param pageIndex 当前页
	 * @param pageSize  分页大小
	 * @return List<MyUser>类型的实体对象
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public List<MyUser> getUserForPage(int pageIndex,int pageSize);
	//按条件查询
	public List<MyUser> getUserForPageByName(String uNameOrpNname, int pageIndex, int pageSize);
	public List<MyUser> getUserForPageByUnitId(Long unitId, int pageIndex, int pageSize);
	public List<MyUser> getUserForPageByDeptId(Long unitId, Long deptId, int pageIndex, int pageSize);
	public List<MyUser> getUserForPageByNameAndUId(String uNameOrpNname, Long unitId, int pageIndex, int pageSize);
	public List<MyUser> getUserForPageByAllS(String uNameOrpNname, Long unitId, Long deptId, int pageIndex, int pageSize);
	
	//按用户类型查询  分页
	public List<MyUser> getUserForPageByRoleId(Long shiroRoleId, int pageIndex, int pageSize);
	public List<MyUser> getUserForPageByUserType(String userType, int pageIndex, int pageSize);
	//按用户类型查  不分页
	public List<MyUser> getAllUserByRoleId(Long shiroRoleId);
	public List<MyUser> getAllUserByUserType(String userType);
	
	/**
	 * 
	 * Task : 得到用户的总数
	 * @return 用户数目
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int getUserCount();

	public int getUserCountByName(String uNameOrpNname);
	public int getUserCountByUnitId(Long unitId);
	public int getUserCountByDeptId(Long unitId, Long deptId);
	public int getUserCountByNameAndUId( String uNameOrpNname, Long unitId);
	public int getUserCountByAllS(String uNameOrpNname, Long unitId, Long deptId);
	
	public int getAllUserCountByRoleId(Long shiroRoleId);
	public int getAllUserCountByUserType(String userType);
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
	 * Task : 修改用户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @param systemUser SystemUser类型的实体对象
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return 修改失败返回0,修改成功返回1
	 * date :2017年12月3日
	 * @author luopeng
	 */
	public int updateSystemUser(ShiroUser shiroUser,String unEditUsername,String password,SystemUser systemUser,MyShiroUserGroupRole myShiroUserGroupRole);
	/**
	 * 
	 * Task : 删除用户
	 * @param shiroUser ShiroUser类型的实体对象
	 * @param systemUser SystemUser类型的实体对象
	 * @param myShiroUserGroupRole MyShiroUserGroupRole类型的实体对象
	 * @return 失败返回0，成功返回1
	 * date :2017年12月4日
	 * @author luopeng
	 */
	public int deleteSystemUser(ShiroUser shiroUser,SystemUser systemUser,MyShiroUserGroupRole myShiroUserGroupRole);
	/**
	 * 
	 * Task : 通过shiroUserId查询
	 * @param shiroUserId shiroUserId
	 * @return List<MyUser> 对象
	 * date :2017年12月13日
	 * @author luopeng
	 */
	public MyUser getUserByShiroUserId(Long shiroUserId);
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
	public MyUser getUserByUserName(String userName);
	
}
