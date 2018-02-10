package pms.com.domain.systemManagement.model;

import java.util.Date;

public class OrganizeManagement {
	private Long id;		//主键id
	private String name;	//单位/部门名称
	private int type;		//类型 :0为单位 1为部门
	private Long parentId;	//上级主键id
	private boolean status;	//是否启用
	private String createPeople;	//创建人
	private Date createTime;		//创建时间
	private String modfiyPeople;		//修改人
	private Date modfiyTime;			//修改时间
	private String description;			//职能描述
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getCreatePeople() {
		return createPeople;
	}
	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModfiyPeople() {
		return modfiyPeople;
	}
	public void setModfiyPeople(String modfiyPeople) {
		this.modfiyPeople = modfiyPeople;
	}
	public Date getModfiyTime() {
		return modfiyTime;
	}
	public void setModfiyTime(Date modfiyTime) {
		this.modfiyTime = modfiyTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
