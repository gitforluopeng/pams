package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroResourcesType implements Serializable{
	
	/**
	 * Task : 
	 * date :2017年10月11日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String typeName;
	
	public ShiroResourcesType() {
		super();
	}
	public ShiroResourcesType(String typeName) {
		super();
		this.typeName = typeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
