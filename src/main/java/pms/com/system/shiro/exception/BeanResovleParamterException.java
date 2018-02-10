package pms.com.system.shiro.exception;

public class BeanResovleParamterException extends RuntimeException {

	/**
	 * Task : 
	 * date :2017年10月17日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public BeanResovleParamterException(String message){
		this.message = message;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage()+": "+message;
	}
}
