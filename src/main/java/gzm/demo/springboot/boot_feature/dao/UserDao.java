package gzm.demo.springboot.boot_feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import gzm.demo.springboot.boot_feature.entity.User;

@Mapper
public interface UserDao {

	// 查询
	List<User> selectUserBySyflg(User user) throws Exception;
	
	// 更新同步flag
	int updUserSyflg(List<User> userLst) throws Exception;
}
