package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroResourcesRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;//主键id自增长
	private Long shiroResourcesId;
	private Long shiroRroleId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShiroResourcesId() {
		return shiroResourcesId;
	}
	public void setShiroResourcesId(Long shiroResourcesId) {
		this.shiroResourcesId = shiroResourcesId;
	}
	public Long getShiroRroleId() {
		return shiroRroleId;
	}
	public void setShiroRroleId(Long shiroRroleId) {
		this.shiroRroleId = shiroRroleId;
	}
	
}
