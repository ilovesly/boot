package gzm.demo.springboot.boot_feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import gzm.demo.springboot.boot_feature.entity.User;
import gzm.demo.springboot.boot_feature.entity.dcom.CabinetPropertyBean;
import gzm.demo.springboot.boot_feature.entity.dcom.CabinetStatus;

@Mapper
public interface CabinetStatusDao {
	
	// 查询所有的机柜状态数据
	public List<CabinetStatus> getStatusList(CabinetStatus cabinetStatus);

}
