package gzm.demo.springboot.boot_feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import gzm.demo.springboot.boot_feature.entity.User;
import gzm.demo.springboot.boot_feature.entity.dcom.CabinetPropertyBean;

@Mapper
public interface CabinetDao {
	
	// 查詢机柜用电和值
	public CabinetPropertyBean getCabPowerByArea(String areaCode) throws Exception;

}
