package pms.com.system.shiro.services.Imp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.DAO.ShiroResourcesDao;
import pms.com.system.shiro.services.ShiroResourceServiceInter;

@Service
public class ShiroResourceService implements ShiroResourceServiceInter{
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Override
	public List<ShiroResources> loadAllShiroResources() {
		
		SqlSession session = sessionFactory.openSession();
		ShiroResourcesDao shiroResourcesDao = session.getMapper(ShiroResourcesDao.class);
		List<ShiroResources> shiroResources = shiroResourcesDao.loadAllShiroResources();
		session.close();
		
		return shiroResources;
	}

}
