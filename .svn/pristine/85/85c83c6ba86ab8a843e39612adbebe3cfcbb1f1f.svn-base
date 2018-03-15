package pms.com.domain.analyze.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pms.com.utils.MapUtil;
@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"handler"})
public class EduCount implements Serializable{
	private String name;//学历名称
	private Integer value;//个数
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * set the value to Map
	 */
	public Map<String,Object> toMap() {
		return MapUtil.ObjToMap(this);
	}
}
