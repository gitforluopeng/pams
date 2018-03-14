package pms.com.domain.salaryManage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pms.com.utils.MapUtil;
@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"handler"})
public class Salary implements Serializable{
	private Long id;//主键id自增长
	private Long userId;//被考勤人id=shiroUserId
	private String userName;//=userName
	private String personName;//=
	private Long unitId;//单位id=
	private String unitName;//单位名称=
	private Long deptId;//部门id=
	private String deptName;//部门名称=
	private Long shouldPay;//应发工资
	private Long realPay;//实发工资
	private String deductions;//扣减项目
	private String payTime;//日期yyyy-MM
	private Integer year;
	private Integer month;
	private Long createUserId;//创建用户id
	private String createUserName;//创建用户名
	private String createPersonName;//创建用户姓名
	private Date createTime;//创建时间
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * @return the unitId
	 */
	public Long getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the shouldPay
	 */
	public Long getShouldPay() {
		return shouldPay;
	}
	/**
	 * @param shouldPay the shouldPay to set
	 */
	public void setShouldPay(Long shouldPay) {
		this.shouldPay = shouldPay;
	}
	/**
	 * @return the realPay
	 */
	public Long getRealPay() {
		return realPay;
	}
	/**
	 * @param realPay the realPay to set
	 */
	public void setRealPay(Long realPay) {
		this.realPay = realPay;
	}
	/**
	 * @return the deductions
	 */
	public String getDeductions() {
		return deductions;
	}
	/**
	 * @param deductions the deductions to set
	 */
	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}
	/**
	 * @return the payTime
	 */
	public String getPayTime() {
		return payTime;
	}
	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * @return the createPersonName
	 */
	public String getCreatePersonName() {
		return createPersonName;
	}
	/**
	 * @param createPersonName the createPersonName to set
	 */
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * set the value to Map
	 */
	public Map<String,Object> toMap() {
		return MapUtil.ObjToMap(this);
	}
}
