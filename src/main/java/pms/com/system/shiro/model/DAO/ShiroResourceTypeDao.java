package pms.com.system.shiro.model.DAO;

public interface ShiroResourceTypeDao {
	
	/**
	 * Task : 通过id查询
	 * @param id 类型id
	 * @return 如果没有，返回null
	 * date :2017年10月11日
	 * @author libo
	 */
	public ShiroResourceTypeDao loadForId(Long id);
	
}
