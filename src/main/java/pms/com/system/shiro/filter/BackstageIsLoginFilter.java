package pms.com.system.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

public class BackstageIsLoginFilter extends PathMatchingFilter implements InitializingBean{
	
	private String backstageLoginUrl;
	private String backstageSuccessUrl;
	private String backstageRoleErrorUrl;
	private String role;
	
	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(!SecurityUtils.getSubject().isAuthenticated()){
			WebUtils.issueRedirect(request, response, backstageLoginUrl);
			return false;
		}
		
		if(SecurityUtils.getSubject().hasRole(role)){
			return true;
		}else {
			WebUtils.issueRedirect(request, response, backstageRoleErrorUrl);
			return false;
		}
		
	}

	public String getBackstageLoginUrl() {
		return backstageLoginUrl;
	}

	public void setBackstageLoginUrl(String backstageLoginUrl) {
		this.backstageLoginUrl = backstageLoginUrl;
	}

	public String getBackstageSuccessUrl() {
		return backstageSuccessUrl;
	}

	public void setBackstageSuccessUrl(String backstageSuccessUrl) {
		this.backstageSuccessUrl = backstageSuccessUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBackstageRoleErrorUrl() {
		return backstageRoleErrorUrl;
	}

	public void setBackstageRoleErrorUrl(String backstageRoleErrorUrl) {
		this.backstageRoleErrorUrl = backstageRoleErrorUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(backstageLoginUrl) 
				|| StringUtils.isEmpty(backstageSuccessUrl)
				|| StringUtils.isEmpty(role)
				|| StringUtils.isEmpty(backstageRoleErrorUrl)) {
			throw new RuntimeException("[BackstageIsLoginFilter ----> init]: "
					+ "使用改bean 必须注入属性 backstageLoginUrl，backstageSuccessUrl,backstageRoleErrorUrl，role 都为string");
		}
	}
	
}
