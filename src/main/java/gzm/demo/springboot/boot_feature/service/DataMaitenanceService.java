package gzm.demo.springboot.boot_feature.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gzm.demo.springboot.boot_feature.entity.User;

// 数据同步
@Service
public interface DataMaitenanceService {
	
	// 获取需要同步的数据
	List<User> DbDataToEs() throws Exception;
	
	// 更新同步flag
    int ToEsDataUp(List<User> userLst) throws Exception;


}
