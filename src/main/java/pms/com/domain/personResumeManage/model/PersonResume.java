package pms.com.domain.personResumeManage.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pms.com.utils.MapUtil;
@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"handler"})
public class PersonResume implements Serializable{
	private Long id;//主键id自增长
	private Long shiroUserId;//用户的id
	private String shiroUserName;//
	private Long chatUploadId;//文件id
	private String personName;//姓名
	private String sex;//性别
	private String nation;//名族
	private String address;//籍贯
	private String birthday;//yyyy-MM
	private String university;//毕业院校
	private String education;//学历
	private String professional;//专业
	private String expectJob;//面试职位
	private String professionalSkills;//专业技能
	private String workYear;//工作年限
	private Long phone;//电话
	private String email;//email
	private String workExperience;//工作经历
	private String showYourSelf;//简短的描述你自己
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
	 * @return the shiroUserId
	 */
	public Long getShiroUserId() {
		return shiroUserId;
	}
	/**
	 * @param shiroUserId the shiroUserId to set
	 */
	public void setShiroUserId(Long shiroUserId) {
		this.shiroUserId = shiroUserId;
	}
	/**
	 * @return the shiroUserName
	 */
	public String getShiroUserName() {
		return shiroUserName;
	}
	/**
	 * @param shiroUserName the shiroUserName to set
	 */
	public void setShiroUserName(String shiroUserName) {
		this.shiroUserName = shiroUserName;
	}
	/**
	 * @return the chatUploadId
	 */
	public Long getChatUploadId() {
		return chatUploadId;
	}
	/**
	 * @param chatUploadId the chatUploadId to set
	 */
	public void setChatUploadId(Long chatUploadId) {
		this.chatUploadId = chatUploadId;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the university
	 */
	public String getUniversity() {
		return university;
	}
	/**
	 * @param university the university to set
	 */
	public void setUniversity(String university) {
		this.university = university;
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
	 * @return the professional
	 */
	public String getProfessional() {
		return professional;
	}
	/**
	 * @param professional the professional to set
	 */
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	/**
	 * @return the expectJob
	 */
	public String getExpectJob() {
		return expectJob;
	}
	/**
	 * @param expectJob the expectJob to set
	 */
	public void setExpectJob(String expectJob) {
		this.expectJob = expectJob;
	}
	/**
	 * @return the professionalSkills
	 */
	public String getProfessionalSkills() {
		return professionalSkills;
	}
	/**
	 * @param professionalSkills the professionalSkills to set
	 */
	public void setProfessionalSkills(String professionalSkills) {
		this.professionalSkills = professionalSkills;
	}
	/**
	 * @return the workYear
	 */
	public String getWorkYear() {
		return workYear;
	}
	/**
	 * @param workYear the workYear to set
	 */
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	/**
	 * @return the phone
	 */
	public Long getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the workExperience
	 */
	public String getWorkExperience() {
		return workExperience;
	}
	/**
	 * @param workExperience the workExperience to set
	 */
	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}
	/**
	 * @return the showYourSelf
	 */
	public String getShowYourSelf() {
		return showYourSelf;
	}
	/**
	 * @param showYourSelf the showYourSelf to set
	 */
	public void setShowYourSelf(String showYourSelf) {
		this.showYourSelf = showYourSelf;
	}
	/**
	 * set the value to Map
	 */
	public Map<String,Object> toMap() {
		return MapUtil.ObjToMap(this);
	}
}