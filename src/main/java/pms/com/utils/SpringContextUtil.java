package pms.com.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		checkContext();
		return (T)context.getBean(name);
	}
	
	private static void checkContext(){
		if(context == null) 
			throw new IllegalStateException("[SpringContextUtil ----> checkContext] context is null");
	}

}
