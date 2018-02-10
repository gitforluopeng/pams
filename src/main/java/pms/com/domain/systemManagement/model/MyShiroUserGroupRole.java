package pms.com.domain.systemManagement.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"handler"})
public class MyShiroUserGroupRole implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;//主键id自增长
	private Long shiroUserId;//shiroUserId
	private Long shrioRoleId;//shiroRoleId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShiroUserId() {
		return shiroUserId;
	}
	public void setShiroUserId(Long shiroUserId) {
		this.shiroUserId = shiroUserId;
	}
	public Long getShrioRoleId() {
		return shrioRoleId;
	}
	public void setShrioRoleId(Long shrioRoleId) {
		this.shrioRoleId = shrioRoleId;
	}
	
}
