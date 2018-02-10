package pms.com.domain.systemManagement.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"handler"})
public class SystemUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;//主键id自增长
	private Long shiroUserId;//shirouserid
	private String personName;//姓名
	private String userNumber;//用户编号
	private String userEmail;//邮箱
	private Long userPhone;//电话
	private String otherInfo;//备注
	private Long createUser;//创建用户id
	private Date creatTime;//创建时间
	private Long changeUser;//更改用户id
	private Date changeTime;//更改时间
	private Date lastLoginTime;//上次登录时间
	
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
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Long getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(Long changeUser) {
		this.changeUser = changeUser;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
