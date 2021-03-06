package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroUserGroup implements Serializable{

	/**
	 * Task : 
	 * date :2017年10月8日
	 * @author libo
	 */
	private static final long serialVersionUID = 4968468407967299925L;
	
	private Long id;
	private String name;
	
	public ShiroUserGroup() {
		super();
	}
	public ShiroUserGroup(String name) {
		super();
		this.name = name;
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
	
}
