package pms.com.domain.systemManagement.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"handler"})
public class MyUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long shiroUserId;
	private String userName;
	private String passWord;
	private boolean accountLockStatus;
	private Long systemUserId;
	private String personName;
	private String userNumber;
	private String education;//学历
	private String userEmail;
	private Long userPhone;
	private Long stationId;//岗位id
	private String otherInfo;
	private Date createTime;
	private Date lastLoginTime;
	private Long deptId;
	private String deptName;
	private Long unitId;
	private Long myRoleId;
	private Long shiroRoleId;
	private String userType;
	private String unitName;
	private String stationName;
	
	public Long getShiroUserId() {
		return shiroUserId;
	}
	public void setShiroUserId(Long shiroUserId) {
		this.shiroUserId = shiroUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public boolean isAccountLockStatus() {
		return accountLockStatus;
	}
	public void setAccountLockStatus(boolean accountLockStatus) {
		this.accountLockStatus = accountLockStatus;
	}
	public Long getSystemUserId() {
		return systemUserId;
	}
	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Long getMyRoleId() {
		return myRoleId;
	}
	public void setMyRoleId(Long myRoleId) {
		this.myRoleId = myRoleId;
	}
	public Long getShiroRoleId() {
		return shiroRoleId;
	}
	public void setShiroRoleId(Long shiroRoleId) {
		this.shiroRoleId = shiroRoleId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * @return the stationId
	 */
	public Long getStationId() {
		return stationId;
	}
	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
