package gzm.demo.springboot.boot_feature.web;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import gzm.demo.springboot.boot_feature.dao.CabinetDao;
import gzm.demo.springboot.boot_feature.dao.CabinetStatusDao;
import gzm.demo.springboot.boot_feature.entity.User;
import gzm.demo.springboot.boot_feature.entity.dcom.CabinetPropertyBean;
import gzm.demo.springboot.boot_feature.entity.dcom.CabinetStatus;
import gzm.demo.springboot.boot_feature.estool.ElasticOperateTool;
import gzm.demo.springboot.boot_feature.tools.CheckUtil;
import gzm.demo.springboot.boot_feature.tools.DateUtil;

@RequestMapping("/dcomservice/resource")
@RestController
public class DcomController extends BaseController{
	// 服务端接口日志
	protected static final Logger logger = LoggerFactory.getLogger(DcomController.class);

	@Autowired
	private CabinetDao cabinetDao;

	@Autowired
	private CabinetStatusDao cabinetStatusDao;

	@Autowired
	private Environment env;

	// 索引库名
	private static String index = "fchy_dcom";

	// 类型名称
	private static String type = "tb_cabinet_status";

	// 获取机柜分区总功率
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/getCabPowerByArea", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getCabPowerByArea(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CabinetPropertyBean cabinetPropertyBean = new CabinetPropertyBean();
		try {
			// 合同号
			String areaCode = request.getParameter("areaCode");

			if (CheckUtil.isEmpty(areaCode)) {
				return getBusinessJson("分区编号不能为空");
			}

			cabinetPropertyBean = cabinetDao.getCabPowerByArea(areaCode);

			if (cabinetPropertyBean == null) {
				return getBusinessJson("指定分区数据不存在，请核实");
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("power", cabinetPropertyBean.getPower());
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.getCabPowerByArea error.", e);
			throw e;
		}
	}

	// 同步机柜状态表里的数据到es
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/copyCabStatusToEs", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject copyCabStatusToEs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CabinetStatus cabinetStatus = new CabinetStatus();
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));

			List<CabinetStatus> cabList = cabinetStatusDao.getStatusList(cabinetStatus);
			if (cabList.size() == 0) {
				jsonObject.put("return_code", "-1");
				jsonObject.put("return_msg", "no data");
				return jsonObject;
			}
			
			List <IndexRequestBuilder> requestLst = new ArrayList(); 
			XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("properties")
					 .startObject("cabinetNum").field("type", "string").field("analyzer", "ik").field("search_analyzer", "ik_smart").endObject()
					 .startObject("compartmentNum").field("type", "string").field("analyzer", "ik").field("search_analyzer", "ik_smart").endObject()
					 .startObject("computerNum").field("type", "string").field("analyzer", "ik").field("search_analyzer", "ik_smart").endObject()
					 .startObject("customerName").field("type", "string").field("analyzer", "ik").field("search_analyzer", "ik_smart").endObject()
					 .startObject("changeDate").field("type", "date").field("format","yyyy-MM-dd HH:mm:ss").field("analyzer", "ik").field("search_analyzer", "ik_smart").endObject()
					 .endObject().endObject();
			 
			 for (CabinetStatus record : cabList) {
				 HashMap<String, Comparable> cabMap = new HashMap<String, Comparable>();
				 cabMap.put("cabinetNum", record.getCabinetNum());
				 cabMap.put("compartmentNum", record.getCompartmentNum());
				 cabMap.put("computerNum", record.getComputerNum());
				 cabMap.put("customerName", record.getCustomerName());
				 cabMap.put("changeDate", record.getChangeDate());
				 IndexRequestBuilder responseRecord = transportClient.prepareIndex(index, type, String.valueOf(record.getId())).setSource(cabMap);
				 requestLst.add(responseRecord);
			 }
			tool.testBulk(transportClient, requestLst);
			transportClient.close();

			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.copyCabStatusToEs error.", e);
			throw e;
		}
	}
	
	// 从es查询机柜状态数据
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectCabStatusFromDB", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectCabStatusFromDB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		CabinetStatus cabinetStatus = new CabinetStatus();
		try {
			List<CabinetStatus> cabList = cabinetStatusDao.getStatusList(cabinetStatus);
			
			jsonObject.put("lst", cabList);
			jsonObject.put("size", cabList.size());		
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectCabStatusFromDB error.", e);
			throw e;
		}
	}
	
	// 从es查询机柜状态数据
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectCabStatusFromEs", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectCabStatusFromEs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		List<CabinetStatus> cabList = new ArrayList<CabinetStatus>();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));

			SearchHit[] searchHit = tool.allSearch(transportClient);
			for (SearchHit s : searchHit) {
				CabinetStatus cabinetStatus = new CabinetStatus();
				JSONObject jj = JSONObject.parseObject(s.getSourceAsString());
				String date = jj.get("changeDate").toString();
				date = date.replaceAll("T", " ");
				date = date.replaceAll("Z", " ");
				jj.put("changeDate", date);
				cabinetStatus = JSON.toJavaObject(jj, CabinetStatus.class);
				cabList.add(cabinetStatus);
			}
			jsonObject.put("lst", cabList);
			jsonObject.put("size", cabList.size());		
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectCabStatusFromEs error.", e);
			throw e;
		}
	}
	
	// 测试Es时间检索
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectEsByTime", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectEsByTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		// 查询时间
		String time = request.getParameter("time");
		
		if (CheckUtil.isEmpty(time)){
			getBusinessJson("时间不能为空");
		}
		
		Date seldate = DateUtil.stringToYMD(time);
		
		List<CabinetStatus> cabList = new ArrayList<CabinetStatus>();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));

			SearchHit[] searchHit = tool.testQueryByTime(transportClient , seldate);
			for (SearchHit s : searchHit) {
				CabinetStatus cabinetStatus = new CabinetStatus();
				JSONObject jj = JSONObject.parseObject(s.getSourceAsString());
				String date = jj.get("changeDate").toString();
				date = date.replaceAll("T", " ");
				date = date.replaceAll("Z", " ");
				jj.put("changeDate", date);
				cabinetStatus = JSON.toJavaObject(jj, CabinetStatus.class);
				cabList.add(cabinetStatus);
			}
			jsonObject.put("lst", cabList);
			jsonObject.put("size", cabList.size());		
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectEsByTime error.", e);
			throw e;
		}
	}
	
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectEsByAnaly", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectEsByAnaly(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		
		List<CabinetStatus> cabList = new ArrayList<CabinetStatus>();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));


			AnalyzeRequest analyzeRequest = new AnalyzeRequest("fchy_dcom").text("中国移动").analyzer("ik_smart");
			

	        List<AnalyzeResponse.AnalyzeToken> tokens = transportClient.admin().indices()
	                .analyze(analyzeRequest)
	                .actionGet()
	                .getTokens();
	        
	        for (AnalyzeResponse.AnalyzeToken token : tokens) {
	            System.out.println(token.getTerm());
	        }

				
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectEsByAnaly error.", e);
			throw e;
		}
	}

	// 获取机柜分区总功率
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/postForVue", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject postForVue(@RequestBody JSONObject jsonobject,HttpServletRequest request, HttpServletResponse response) throws Exception {
		CabinetPropertyBean cabinetPropertyBean = new CabinetPropertyBean();
		try {
			// 合同号
			String areaCode = jsonobject.getString("areaCode");

			cabinetPropertyBean = cabinetDao.getCabPowerByArea(areaCode);

			if (cabinetPropertyBean == null) {
				return getBusinessJson("指定分区数据不存在，请核实");
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("power", cabinetPropertyBean.getPower());
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.postForVue error.", e);
			throw e;
		}
	}
	
	// 根据分词进行模糊查询
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectCabStatusFromEsForMatch", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectCabStatusFromEsForMatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		List<CabinetStatus> cabList = new ArrayList<CabinetStatus>();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));

			SearchHit[] searchHit = tool.matchQuery(transportClient);
			for (SearchHit s : searchHit) {
				CabinetStatus cabinetStatus = new CabinetStatus();
				JSONObject jj = JSONObject.parseObject(s.getSourceAsString());
				String date = jj.get("changeDate").toString();
				date = date.replaceAll("T", " ");
				date = date.replaceAll("Z", " ");
				jj.put("changeDate", date);
				cabinetStatus = JSON.toJavaObject(jj, CabinetStatus.class);
				cabList.add(cabinetStatus);
			}
			jsonObject.put("lst", cabList);
			jsonObject.put("size", cabList.size());		
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectCabStatusFromEsForMatch error.", e);
			throw e;
		}
	}

	// 精确查询
	// 跨域处理
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "/selectCabStatusFromEsForTerm", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectCabStatusFromEsForTerm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ElasticOperateTool tool = new ElasticOperateTool();
		JSONObject jsonObject = new JSONObject();
		List<CabinetStatus> cabList = new ArrayList<CabinetStatus>();
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "GZM-ES")
					.build();

			TransportClient transportClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes1")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes2")), 9300))
					.addTransportAddress(new TransportAddress(
							InetAddress.getByName(env.getProperty("spring.data.elasticsearch.cluster-nodes3")), 9300));

			SearchHit[] searchHit = tool.termQuery(transportClient);
			for (SearchHit s : searchHit) {
				CabinetStatus cabinetStatus = new CabinetStatus();
				JSONObject jj = JSONObject.parseObject(s.getSourceAsString());
				String date = jj.get("changeDate").toString();
				date = date.replaceAll("T", " ");
				date = date.replaceAll("Z", " ");
				jj.put("changeDate", date);
				cabinetStatus = JSON.toJavaObject(jj, CabinetStatus.class);
				cabList.add(cabinetStatus);
			}
			jsonObject.put("lst", cabList);
			jsonObject.put("size", cabList.size());		
			jsonObject.put("return_code", "0");
			jsonObject.put("return_msg", "");
			return jsonObject;
		} catch (Exception e) {
			logger.error("ResourceController.selectCabStatusFromEsForTerm error.", e);
			throw e;
		}
	}

}
