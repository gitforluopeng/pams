package pms.com.domain.systemManagement.model;

import java.util.Date;

public class OperationLog {
	private Long id;					//主键编号
	private Date operationDate;			//操作日期(分分秒秒)
	private String ip;					//操作ip
	private String operationRecord;		//操作记录
	private String operationUser;		//操作用户
	private String userName;			//用户姓名
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperationRecord() {
		return operationRecord;
	}
	public void setOperationRecord(String operationRecord) {
		this.operationRecord = operationRecord;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
