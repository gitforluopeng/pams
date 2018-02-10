package pms.com.system.shiro.services.abstractInter;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import pms.com.system.shiro.services.FilterChainDefinitionsService;

public abstract class AbstractFilterChainDefinitionsService implements FilterChainDefinitionsService{
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	private String definitions;

	@Override
	@PostConstruct
	public void init() {
		shiroFilterFactoryBean.setFilterChainDefinitionMap(loadSection());
	}
	
	@Override
	public void updateResoures(){
		synchronized(shiroFilterFactoryBean){
			AbstractShiroFilter shiroFilter = null;
			try {
				shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
			PathMatchingFilterChainResolver pathMatcher = (PathMatchingFilterChainResolver) shiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) pathMatcher
					.getFilterChainManager();
			filterChainManager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			
			shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
			Map<String, String> sections = loadSection();
			sections.put("/backstage/user/login_view", "anon");
			shiroFilterFactoryBean.setFilterChainDefinitionMap(sections);
			for(Map.Entry<String, String> entry: sections.entrySet()){
				String url = entry.getKey();
				String role = entry.getValue();
				role = role.trim().replace(" ", "");
				filterChainManager.createChain(url, role);
			}
			
		}
	}
	
	private Section loadSection(){
		Ini ini = new Ini();
		ini.load(definitions);
		Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		Map<String, String> jdbcSection = loadJDBCResoures();
		if(jdbcSection != null && !jdbcSection.isEmpty()){
			section.putAll(jdbcSection);
		}
		return section;
	}

	public String getDefinitions() {
		return definitions;
	}

	public void setDefinitions(String definitions) {
		this.definitions = definitions;
	}
	
}
