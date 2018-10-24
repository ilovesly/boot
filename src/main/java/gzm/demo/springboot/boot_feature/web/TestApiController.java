package gzm.demo.springboot.boot_feature.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RequestMapping("/fcy_device/dataout")
@RestController
public class TestApiController extends BaseController{
	
	// 服务端接口日志
	protected static final Logger logger = LoggerFactory.getLogger(TestApiController.class);
	
	// 获取机柜分区总功率
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/monthdata", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject monthdata(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("area", "");
			jsonObject.put("date", "");
			jsonObject.put("itEnergy", "0.37");
			jsonObject.put("itPower", "3.28");
			jsonObject.put("totalEnergy", "0.86");
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.monthdata error.", e);
			throw e;
		}
	}
}
