package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroUserGroupRole implements Serializable{

	/**
	 * Task : 
	 * date :2017年10月8日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long shiroUserGroupId;
	private Long shiroRoleId;
	
	
	public ShiroUserGroupRole() {
		super();
	}
	
	public ShiroUserGroupRole(Long shiroUserGroupId, Long shiroRoleId) {
		super();
		this.shiroUserGroupId = shiroUserGroupId;
		this.shiroRoleId = shiroRoleId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShiroUserGroupId() {
		return shiroUserGroupId;
	}
	public void setShiroUserGroupId(Long shiroUserGroupId) {
		this.shiroUserGroupId = shiroUserGroupId;
	}
	public Long getShiroRoleId() {
		return shiroRoleId;
	}
	public void setShiroRoleId(Long shiroRoleId) {
		this.shiroRoleId = shiroRoleId;
	}
	
}
