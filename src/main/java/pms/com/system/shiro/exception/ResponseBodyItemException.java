package pms.com.system.shiro.exception;

public class ResponseBodyItemException extends RuntimeException {

	/**
	 * Task : 
	 * date :2017年10月13日
	 * @author libo
	 */
	private static final long serialVersionUID = 7029172869131838202L;

	@Override
	public String getMessage() {
	
		return super.getMessage()+": this return type is not collection or map";
	}
	
	
}
