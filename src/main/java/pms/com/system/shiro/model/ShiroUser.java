package pms.com.system.shiro.model;

import java.io.Serializable;
import java.util.Date;

public class ShiroUser implements Serializable{
	
	/**
	 * Task : 
	 * date :2017年10月6日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;
	//主键
	public Long id;
	//用户名
	public String username;
	
	//用户锁定状态
	public boolean accountLockStatus;
	//用户密码
	public String password;
	//用户密码盐值
	public String salt;
	
	public Long groupId;
	
	public Date createTime;
	
	public Date updateTime;
	
	public ShiroUser() {
		super();
	}
	
	public ShiroUser(String username, boolean accountLockStatus, String password, String salt, Date createTime,
			Date updateTime) {
		super();
		this.username = username;
		this.accountLockStatus = accountLockStatus;
		this.password = password;
		this.salt = salt;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isAccountLockStatus() {
		return accountLockStatus;
	}
	public void setAccountLockStatus(boolean accountLockStatus) {
		this.accountLockStatus = accountLockStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("user:");
		builder.append("id="+id+"\r\n");
		builder.append("username="+getUsername()+"\r\n");
		builder.append("accountLockStatus="+isAccountLockStatus()+"\r\n");
		builder.append("createTime="+getCreateTime()+"\r\n");
		builder.append("updateTime="+getUpdateTime()+"\r\n");
		builder.append("groupId="+getGroupId()+"\r\n");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (int) ((id==null)?0:id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
	}
	
	
	
}
