package pms.com.domain.trainingPlan.services;

import java.util.List;
import java.util.Map;

import pms.com.domain.trainingPlan.model.TrainPlan;

public interface TrainPlanServiceInter {
	/**
	 * 
	 *
	 * Task:添加培训计划
	 * @param trainPlan
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int addTrainPlan(TrainPlan trainPlan);
	/**
	 * 
	 *
	 * Task:编辑培训计划
	 * @param trainPlan
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int updateTrainPlan(TrainPlan trainPlan);
	/**
	 * 
	 *
	 * Task:删除培训计划
	 * @param trainPlan
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public int deleteTrainPlan(TrainPlan trainPlan);
	/**
	 * 
	 *
	 * Task:条件查询培训计划
	 * @param map
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public List<TrainPlan> getTrainPlans(Map<String, Object> map);
	/**
	 * 
	 *
	 * Task:条件查询培训计划得到其个数
	 * @param map
	 * @return
	 * date: 2018年2月22日
	 * @author pengLuo
	 *
	 */
	public Integer getTrainPlansCount(Map<String, Object> map);
}
