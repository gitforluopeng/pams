package pms.com.domain.salaryManage.model.DAO;

import java.util.List;
import java.util.Map;

import pms.com.domain.salaryManage.model.Salary;

public interface SalaryDao {
	public int addSalary(Salary salary);
	public int updateSalary(Salary salary);
	public int deleteSalary(Salary salary);
	public List<Salary> getSalarys(Map<String, Object> map);
	public Integer getSalarysCount(Map<String, Object> map);
	public List<Salary> getSalarysByTime(Salary salary);
}
