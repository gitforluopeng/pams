package pms.com.domain.systemManagement.services;

import java.util.Date;
import java.util.List;


import pms.com.domain.systemManagement.model.OrganizeManagement;
import pms.com.domain.systemManagement.model.SystemUser;
import pms.com.system.shiro.model.ShiroUser;

public interface OrganizeServiceInter {
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取所有单位服务
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getAllUnit(Integer pageIndex, Integer pageSize);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取当前单位的部门数量
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public int getDeptCountByUnitId(Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取单位记录条数
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public int getUnitCount();
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过id更改状态值
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public boolean updateStatus(Long id,boolean status);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 查询所属单位的所有部门
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getAllDeptByUnitId(Long unitId,Integer pageIndex, Integer pageSize);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取所属部门的用户个数
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public int getUsercountByDeptId(Long deptId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 删除部门信息通过部门id 返回0则失败 返回 1则成功 返回2则该部门下有系统成员不能删除
	 * <date> - <desc>
	 * 2017年12月3日
	 */
	public int deleteDeptByDeptId(Long deptId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过部门id分页查询shiroUserList对象
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public List<ShiroUser> getShiroUsersByDeptId(Long dept,Integer pageIndex, Integer pageSize);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过shiroUserId获得systemUser对象
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public SystemUser getSystemUserByShiroUserId(Long shiroUserId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 添加部门
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public int insertDept(OrganizeManagement organizeManagement,Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过id获取组织机构
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public OrganizeManagement getManagementById(Long id);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过父级id获取单位名称
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public String getUnitNameByParentId(Long parentId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过部门id编辑保存信息
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public int updateDept(String deptName,String remarks,Long deptId,String modfiyPeople,Date modfiyTime);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过id查询组织类型
	 * <date> - <desc>
	 * 2017年12月5日
	 */
	public int getTypeById(Long id);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取所有角色名称
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<String> getAllRoles();
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 分页获取组织机构的所有角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<String> getOrganizeRoles(Long id,Integer page, Integer limit);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取组织机构的角色个数
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int getCountForOrganizeRole(Long id);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 获取组织机构的所有角色名
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<String> getUnitRolesName(Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 删除组织机构角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int deleteRole(Long groupid,String rolename);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 新增组织机构角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int insertRole(Long groupId,String roleName);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeServiceInter.java
	 * Description: 通过单位id获取所属部门名称
	 * <date> - <desc>
	 * 2017年12月17日
	 */
	public List<String> getDeptNameByUnitId(Long unitId);
	
	public int insertUnit(OrganizeManagement organizeManagement);
	
	public int updateUnit(Long id,String unitName,String res,String modfiyPeople,Date modfiyDate);
	public List<OrganizeManagement> getAllUnit();
	public int delUnit(Long unitId);
}
