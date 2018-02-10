package pms.com.system.shiro.services;

import java.util.Map;

public interface FilterChainDefinitionsService {
	
	public void init();
	
	public void updateResoures();
	
	public abstract Map<String, String> loadJDBCResoures();
	
}
