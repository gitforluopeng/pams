package pms.com.domain.systemManagement.model.DAO;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import pms.com.domain.systemManagement.model.OrganizeManagement;

public interface OrganizeDao {
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 分页获取所有单位
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getAllUnit
	(@Param(value = "offerIndex") int offerIndex, @Param(value = "pageSize") int pageSize);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过单位id获取所属部门数量
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
	 * FileName: OrganizeDao.java
	 * Description: 获取单位有多少个
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
	 * FileName: OrganizeDao.java
	 * Description: 通过id修改状态值
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public int changeStatusByUnitId(@Param("unitId")Long unitId,@Param("status")boolean status);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取所有单位
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getAllUnitList();
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取所属单位下所有部门
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getDeptByUnitId(Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 更改指定记录的状态值
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public int updateStatus(@Param("id")Long id,@Param("status")boolean status);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取所属单位的所有部门(分页)
	 * <date> - <desc>
	 * 2017年11月30日
	 */
	public List<OrganizeManagement> getAllDept
	(@Param(value = "unitId")Long unitId,@Param(value = "offerIndex") int offerIndex, @Param(value = "pageSize") int pageSize);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 查询所属部门的用户个数
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
	 * FileName: OrganizeDao.java
	 * Description: 通过部门id删除用户组角色关联表数据
	 * <date> - <desc>
	 * 2017年12月3日
	 */
	public int delGroupRolesByDeptId(Long deptId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过部门id删除用户组表数据
	 * <date> - <desc>
	 * 2017年12月3日
	 */
	public int delGroupByDeptId(Long deptId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 插入部门信息
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public Long insertDept(OrganizeManagement organizeManagement);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过单位名称获取部门名称
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public int isRepeatDeptName(@Param("unitId")Long unitId,@Param("deptName")String deptName);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过id获得组织机构
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public List<OrganizeManagement> getManagementById(Long id);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过父id获取单位名称
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public String getUnitNameByparentId(Long parentId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 保存编辑信息
	 * <date> - <desc>
	 * 2017年12月4日
	 */
	public int updateDept(@Param("deptName")String deptName,@Param("description")String description,
			@Param("deptId")Long deptId,@Param("modfiyPeople")String modfiyPeople,@Param("modfiyDate")Date modfiyDate);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
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
	 * FileName: OrganizeDao.java
	 * Description: 通过单位id将所属下级是否启用修改为一致
	 * <date> - <desc>
	 * 2017年12月6日
	 */
	public int setDeptsByParentId(@Param("parentId")Long parentId,@Param("status")boolean status);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过组织机构id获取角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<String> getRolesByOrganizeId(@Param("organizeId")Long organizeId,
			@Param("off")Integer off, @Param("limit")Integer limit);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取所有角色
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
	 * FileName: OrganizeDao.java
	 * Description: 获取组织机构的角色个数 分页使用
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int getCountForOrganizeRole(Long organizeId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过用户组id获取角色list
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<String> getRolesByUnitId(Long UnitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 删除组织机构角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int deleteRole(@Param("organizeId")Long organizeId,@Param("rolename")String rolename);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取单位的所有部门
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<OrganizeManagement> getAllDeptByUnitIdList(Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取部门下所有成员id
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public List<Long> getUserIdByDeptId(Long deptId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 获取用户id的角色名
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public String getUserRoleName(Long userId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过角色名获取角色id
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public Long getRoleId(String rolename);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 为组织机构添加角色
	 * <date> - <desc>
	 * 2017年12月7日
	 */
	public int insertRole(@Param("organizeId")Long organizeId,@Param("roleId")Long roleId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过单位id查询所属部门名称
	 * <date> - <desc>
	 * 2017年12月17日
	 */
	public List<String> getDeptNameByUnitId(Long unitId);
	/**
	 * 
	 *
	 * 版权信息：四川众鹏科技有限公司
	 * Copyright (C) 2014 SCZP Information Technology Co., Ltd. All Rights Reserved
	 *
	 * FileName: OrganizeDao.java
	 * Description: 通过单位名称查询单位条数
	 * <date> - <desc>
	 * 2017年12月18日
	 */
	public int getCountByUnitName(String unitName);
	
	public int updateUnit(@Param("unitName")String unitName,@Param("description")String description,
			@Param("unitId")Long unitId,@Param("modfiyPeople")String modfiyPeople,@Param("modfiyDate")Date modfiyDate);
	public List<OrganizeManagement> getAllUnitNopage();
}
