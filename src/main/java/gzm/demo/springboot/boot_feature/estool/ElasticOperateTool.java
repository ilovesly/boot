package gzm.demo.springboot.boot_feature.estool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gzm.demo.springboot.boot_feature.entity.Product;
import gzm.demo.springboot.boot_feature.entity.User;
import gzm.demo.springboot.boot_feature.tools.DateUtil;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.count.CountRequestBuilder;
//import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 使用Java API来操作es集群 Transport 代表了一个集群 我们客户端和集群通信是使用TransportClient
 */
public class ElasticOperateTool {

	@Autowired
	private Environment env;

	// 索引库名
	private static String index = "fchy_dcom";

	// 类型名称
	private static String type = "tb_cabinet_status";

	/**
	 * 注意：往es中添加数据有4种方式 1.JSON 2.Map 3.Java Bean 4.XContentBuilder
	 *
	 * 1.JSON方式
	 */
	// @Test
	// public void testAddJSON(TransportClient client) {
	// String source = "{\"name\":\"sqoop\", \"author\": \"apache\",
	// \"version\": \"1.4.6\"}";
	// IndexResponse response = client.prepareIndex(index, type,
	// "4").setSource(source).get();
	// System.out.println(response.getResult());
	// }

	/**
	 * 添加数据： 2.Map方式
	 */

	public void testAddMap(TransportClient client) {
		Map<String, Object> source = new HashMap<String, Object>();
		source.put("name", "flume");
		source.put("author", "Cloudera");
		source.put("version", "1.8.0");
		IndexResponse response = client.prepareIndex(index, type, "5").setSource(source).get();
		System.out.println(response.getResult());
	}

	/**
	 * 添加数据： 4.XContentBuilder方式
	 */

	public void testAddXContentBuilder(TransportClient client) throws IOException {
		XContentBuilder source = XContentFactory.jsonBuilder();
		source.startObject().field("name", "redis").field("author", "redis").field("version", "3.2.0")
				.field("url", "redis.cn").endObject();
		IndexResponse response = client.prepareIndex(index, type, "7").setSource(source).get();
		System.out.println(response.getResult());
	}

	/**
	 * 查询具体的索引信息
	 */

	public void testGet(TransportClient client) {
		GetResponse response = client.prepareGet(index, type, "6").get();
		Map<String, Object> map = response.getSource();
		/*
		 * for(Map.Entry<String, Object> me : map.entrySet()) {
		 * System.out.println(me.getKey() + "=" + me.getValue()); }
		 */
		// lambda表达式，jdk 1.8之后
		map.forEach((k, v) -> System.out.println(k + "=" + v));
		// map.keySet().forEach(key -> System.out.println(key + "xxx"));
	}

	/**
	 * 局部更新操作与curl的操作是一致的 curl -XPOST
	 * http://uplooking01:9200/bigdata/product/AWA184kojrSrzszxL-Zs/_update -d'
	 * {"doc":{"name":"sqoop", "author":"apache"}}'
	 *
	 * 做全局更新的时候，也不用prepareUpdate，而直接使用prepareIndex
	 */

	public void testUpdate(TransportClient client) throws Exception {
		/*
		 * String source = "{\"doc\":{\"url\": \"http://flume.apache.org\"}}";
		 * UpdateResponse response = client.prepareUpdate(index, type,
		 * "4").setSource(source.getBytes()).get();
		 */
		// 使用下面这种方式也是可以的
		String source = "{\"url\": \"http://flume.apache.org\"}";
		UpdateResponse response = client.prepareUpdate(index, type, "4").setDoc(source.getBytes()).get();
		System.out.println(response.getVersion());
	}

	/**
	 * 删除操作
	 */

	public void testDelete(TransportClient client) {
		DeleteResponse response = client.prepareDelete(index, type, "5").get();
		System.out.println(response.getVersion());
	}

	/**
	 * 批量操作
	 * 
	 * @throws IOException
	 */

	public void testBulk(TransportClient client, List<IndexRequestBuilder> responseLst) throws IOException {
		BulkRequestBuilder bulk = client.prepareBulk();

		for (int i = 0; i < responseLst.size(); i++) {
			bulk.add(responseLst.get(i));
		}
		BulkResponse bulkResponse = bulk.get();
		Iterator<BulkItemResponse> it = bulkResponse.iterator();
		while (it.hasNext()) {
			BulkItemResponse response = it.next();
		}
	}

	// 模糊查询、多参数查询
	public SearchHit[] vagueSearch(TransportClient client, User user) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.boolQuery()
						.must(QueryBuilders.wildcardQuery("address", "*" + user.getAddress() + "*"))
						.must(QueryBuilders.wildcardQuery("name", "*1*")).should(QueryBuilders.termQuery("sex", "0")))
				.setFrom(0).setSize(100)// 分页
				.addSort("id", SortOrder.ASC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		System.out.println(total);
		SearchHit[] searchHits = hits.getHits();
		return searchHits;

	}

	// 查询所有
	public SearchHit[] allSearch(TransportClient client) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()) // 查询所有
				.setFrom(0).setSize(10000).get();

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		return searchHits;
	}

	// 模糊查询、多参数查询
	public SearchHit[] mutilSearch(TransportClient client, User user) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.multiMatchQuery(user.getKeyWord(), "name", "address"))
				// .setQuery(QueryBuilders.wildcardQuery("name",
				// "*1*")).setQuery(QueryBuilders.termQuery("sex", "0"))
				.setFrom(2).setSize(5)// 分页
				.addSort("id", SortOrder.ASC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		// System.out.println(total);
		// int a = (int)total;
		// SearchHit[] searchHits = new SearchHit[a];
		SearchHit[] searchHits = hits.getHits();
		return searchHits;
		// for (SearchHit s : searchHits) {
		// System.out.println(s.getSourceAsString());
		// }
	}

	/**
	 * 组合查询 must(QueryBuilders) : AND mustNot(QueryBuilders): NOT should: : OR
	 */
	public SearchHit[] testQueryByTime(TransportClient client, Date date) {
		Date startDate = new Date();
		Date endDate = new Date();
		String start = "";
		String end = "";
		try {
			startDate = DateUtil.getStartSecondsDate(date);
			endDate = DateUtil.getVailDate(date);
			start = DateUtil.getDateForEs(startDate);
			end = DateUtil.getDateForEs(endDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("changeDate").from(start).to(end)))
				.setFrom(0).setSize(10000).get();
		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		SearchHit[] searchHits = hits.getHits();
		return searchHits;

	}
	
	// 模糊查询、多参数查询
	public SearchHit[] matchQuery(TransportClient client) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchQuery("customerName", "中国,鹅"))
				.setFrom(0).setSize(10000)
//				.addSort("id", SortOrder.ASC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		// System.out.println(total);
		// int a = (int)total;
		// SearchHit[] searchHits = new SearchHit[a];
		SearchHit[] searchHits = hits.getHits();
		return searchHits;
		// for (SearchHit s : searchHits) {
		// System.out.println(s.getSourceAsString());
		// }
	}
	
	public SearchHit[] termQuery(TransportClient client) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.termQuery("customerName", "中国,鹅"))
				.setFrom(0).setSize(10000)
//				.addSort("id", SortOrder.ASC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		// System.out.println(total);
		// int a = (int)total;
		// SearchHit[] searchHits = new SearchHit[a];
		SearchHit[] searchHits = hits.getHits();
		return searchHits;
		// for (SearchHit s : searchHits) {
		// System.out.println(s.getSourceAsString());
		// }
	}

}