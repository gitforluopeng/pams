package pms.com.domain.personResumeManage.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"handler"})
public class Nation implements Serializable{
	private String nationId;
	private String nationName;
	/**
	 * @return the nationId
	 */
	public String getNationId() {
		return nationId;
	}
	/**
	 * @param nationId the nationId to set
	 */
	public void setNationId(String nationId) {
		this.nationId = nationId;
	}
	/**
	 * @return the nationName
	 */
	public String getNationName() {
		return nationName;
	}
	/**
	 * @param nationName the nationName to set
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
}
