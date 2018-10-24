package gzm.demo.springboot.boot_feature.web;

import com.alibaba.fastjson.JSONObject;

import gzm.demo.springboot.boot_feature.tools.CheckUtil;

public class BaseController {
	/**
	 * 业务判断错误
	 * 
	 * @param msg
	 * @return
	 */
	public JSONObject getBusinessJson(String msg) {
		if (CheckUtil.isEmpty(msg))
			msg = "数据不合法，请重试！";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("return_code", "-1002");
		jsonObject.put("return_msg", msg);
		return jsonObject;
	}
}
