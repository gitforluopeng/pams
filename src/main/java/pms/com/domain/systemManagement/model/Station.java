package pms.com.domain.systemManagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pms.com.utils.MapUtil;

@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"handler"})
public class Station implements Serializable{
	private Long id;//主键id自增长
	private String stationName;//岗位名称
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
