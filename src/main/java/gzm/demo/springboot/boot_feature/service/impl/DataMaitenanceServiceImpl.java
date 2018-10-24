package gzm.demo.springboot.boot_feature.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gzm.demo.springboot.boot_feature.dao.UserDao;
import gzm.demo.springboot.boot_feature.entity.User;
import gzm.demo.springboot.boot_feature.service.DataMaitenanceService;

@Service
public class DataMaitenanceServiceImpl implements DataMaitenanceService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> DbDataToEs() throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		user.setSyflg(0);
		
		List<User> lstUser = (List<User>) userDao.selectUserBySyflg(user);
		
		return lstUser;
	}

	@Override
	public int ToEsDataUp(List<User> userLst) throws Exception {
		// TODO Auto-generated method stub
		userDao.updUserSyflg(userLst);
		return 0;
	}

}
