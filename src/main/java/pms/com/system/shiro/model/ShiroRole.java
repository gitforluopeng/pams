package pms.com.system.shiro.model;

import java.io.Serializable;

public class ShiroRole implements Serializable{
	
	/**
	 * Task : 
	 * date :2017年10月6日
	 * @author libo
	 */
	private static final long serialVersionUID = -6818520376715130479L;
	public Long id;
	public String name;
	public boolean lockStatus;
	
	public ShiroRole() {
		super();
	}

	public ShiroRole(String name, boolean lockStatus) {
		super();
		this.name = name;
		this.lockStatus = lockStatus;
	}
	
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
	public boolean isLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(boolean lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("role:");
		builder.append("roleName="+name);
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
        ShiroRole other = (ShiroRole) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
	}
	
	
}
