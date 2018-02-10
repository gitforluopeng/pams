package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroResources implements Serializable{

	/**
	 * Task : 
	 * date :2017年10月8日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String url;
	private Integer showOrder;
	private Long parentId;
	private Long shiroRoleId;
	private Long type;
	
	public ShiroResources() {
		super();
	}

	public ShiroResources(String name, String url, Integer showOrder, Long parentId, Long shiroRoleId) {
		super();
		this.name = name;
		this.url = url;
		this.showOrder = showOrder;
		this.parentId = parentId;
		this.shiroRoleId = shiroRoleId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getShiroRoleId() {
		return shiroRoleId;
	}
	public void setShiroRoleId(Long shiroRoleId) {
		this.shiroRoleId = shiroRoleId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((shiroRoleId == null) ? 0 : shiroRoleId.hashCode());
		result = prime * result + ((showOrder == null) ? 0 : showOrder.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroResources other = (ShiroResources) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (shiroRoleId == null) {
			if (other.shiroRoleId != null)
				return false;
		} else if (!shiroRoleId.equals(other.shiroRoleId))
			return false;
		if (showOrder == null) {
			if (other.showOrder != null)
				return false;
		} else if (!showOrder.equals(other.showOrder))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	
}
