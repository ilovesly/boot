package com.config;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import gzm.demo.springboot.boot_feature.entity.PropertyValue;




/**
 * 常量
 * @author zoujunpeng
 *
 */
public class ResultConstants{
	
	public static JSONObject sysytemPropertysJson;
	
	public static void setSysytemPropertysJson(JSONObject sysytemPropertysJson){
		ResultConstants.sysytemPropertysJson=sysytemPropertysJson;
	}
	/**
	 * 根据code获取value
	 * @param fieldName
	 * @param code
	 * @return
	 */
	public static String getPropertyValueByCode(String fieldName,String code){
		String rtnValue="";
		if(null!=sysytemPropertysJson){
			if(ResultConstants.hasText(fieldName)&&
					ResultConstants.hasText(code)){
				String strAry=sysytemPropertysJson.getString(fieldName);
				if(ResultConstants.hasText(strAry)){
					JSONArray jsonArray=JSONArray.parseArray(strAry);
					if(null!=jsonArray&&0<jsonArray.size()){
						int size=jsonArray.size();
						for(int i=0;i<size;i++){
							PropertyValue propertyValue=JSONObject.parseObject(jsonArray.getString(i), PropertyValue.class);
							if(propertyValue.getCode().equals(code.trim())){
								rtnValue=propertyValue.getValue();
								break;
							}
						}
					}
				}
			}
		}
		return rtnValue;
	}
	/**
	 * 根据value获取code
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static String getPropertyCodeByValue(String fieldName,String value){
		String rtnCode="";
		if(null!=sysytemPropertysJson){
			if(ResultConstants.hasText(fieldName)&&
					ResultConstants.hasText(value)){
				String strAry=sysytemPropertysJson.getString(fieldName);
				if(ResultConstants.hasText(strAry)){
					JSONArray jsonArray=JSONArray.parseArray(strAry);
					if(null!=jsonArray&&0<jsonArray.size()){
						int size=jsonArray.size();
						for(int i=0;i<size;i++){
							PropertyValue propertyValue=JSONObject.parseObject(jsonArray.getString(i), PropertyValue.class);
							if(propertyValue.getValue().equals(value.trim())){
								rtnCode=propertyValue.getCode();
								break;
							}
						}
					}
				}
				
			}
		}
		return rtnCode;
	}
	
	
	
	public static class CabinetUserType{
		/**
		 * 使用
		 */
		public static final String USE="USE";
		/**
		 * 闲置
		 */
		public static final String IDLE="IDLE";
		/**
		 * 占用
		 */
		public static final String LOCK="LOCK";
		/**
		 * 预占
		 */
		public static final String HOLD="HOLD";
		/**
		 * 测试
		 */
		public static final String TEST="TEST";
	}
	
	
	
	/**
	 * 资产库事务
	 */
	public static final String ASSET_TRANSACTION_MANAGER="assetTransactionManager";
	
	/**
	 * 资产管理事务
	 */
	public static final String RESOURCE_MNG_TRANSACTION_MANAGER="resourceMngTransactionManager";
	
	
	public static final Integer VISTOR_ROLE_ID=1;
	public static final String OK="0";
	public static final String ERROR="-1001";
	 public static final String STATUS_OK="1";
	 public static final String STATUS_FALSE="0";
	 public static final String ASTERISK="*";
	 public static final String TWO="2";
	 public static final String SESSION_USER="user";
	 public static final String SESSION_USERINFO="userInfo";
	 
	 public static final String STR_CREATE_TIME="strCreateTime";
	 public static final String STR_UPDATE_TIME="strUpdateTime";
	 
	 public static final String CREATE_TIME="createtime";
	 public static final String UPDATE_TIME="updatetime";
	 
	 public static final String FCYPAGE_PAGE="page";
	 public static final String FCYPAGE_ROWS="rows";
	 public static final String FCYPAGE_SORT="sort";
	 public static final String FCYPAGE_SORD="sord";
	 public static final String FCYPAGE_USERID="userId";
	 public static final String FCYPAGE_REQUESTTIME="requestTime";
	 public static final String FCYPAGE_ENCRYPT="encrypt";
	 public static final String FCYPAGE_USERNAME="userName";
	 public static final String FCYPAGE_SYSTEMDOMAIN="systemDomain";
	 public static final String FCYPAGE_SESSIONID="sessionId";
	 public static final String FCYPAGE_ROLEID="roleId";
	 public static final String FCYPAGE_DEPARTMENTID="departmentId";
	 public static final String FCYPAGE_SIGNATURE="signature";
	 public static final String FCYPAGE_SYSTEMID="systemId";
	 /**
	  * sessionId属性值
	  */
	 public static final String SESSIONID_ATTRIBUTE="sessionId_attr";
	 
	 public static final String ASC="asc";
	 public static final String DESC="desc";
	 public static final String RETURN_CODE="return_code";
	 public static final String RETURN_MSG="return_msg";
	 
	  /**
	  * 资源管理地址常量
	  */
	 public  static final String FILEUPLOADURL = "FileuploadURL";
	 public static final String ASSERTCHANGEURL="assetChangeURL";
	 
	 /**
	  * 错误请求
	  */
	 public static final String ERROR_REQUEST_EXCEPTION="400";
	 /**
	  * 没有权限
	  */
	 public static final String NO_AUTH_EXCEPTION="401";
	 
	 /**
	  * 资源不可用
	  */
	 public static final String NO_FORBIDDEN_EXCEPTION="403";
	 
	 /**
	  * 错误请求
	  */
	 public static final String ERROR_404_EXCEPTION="404";
	 
	 /**
	  * 方法不允许
	  */
	 public static final String METHOD_NOT_ALLOWED_EXCEPTION="405";
	 /**
	  * 系统不接受
	  */
	 public static final String NOT_ACCEPTABLE_EXCEPTION="406";
	 /**
	  * 不支持的媒体类型
	  */
	 public static final String UNSUPPORTED_MEDIA_TYPE="415";
	 
	 
	 /**
	  * 系统异常
	  */
	 public static final String SYSTEM_ERROR_EXCEPTION="500";
	 /**
	  * 运行时异常
	  */
	 public static final String RUN_EXCEPTION="-101000";
	 /**
	  * 空指针异常
	  */
	 public static final String NULL_EXCEPTION="-101001";
	 /**
	  * 数据类型转换异常
	  */
	 public static final String DATE_PARSER_EXCEPTION="-101002";
	 /**
	  * IO异常
	  */
	 public static final String IO_EXCEPTION="-101003";
	 /**
	  * 未知方法异常
	  */
	 public static final String UNKNOW_METHOD_EXCEPTION="-101004";
	 /**
	  * 数组越界异常
	  */
	 public static final String ARRAY_EXCEPTION="-101005";
	 /**
	  * 网络异常
	  */
	 public static final String NETWORK_EXCEPTION="-101006";
	 /**
	  * 参数数据类型不正确
	  */
	 public static final String PARAMS_DATA_ERROR_EXCEPTION="-101007";
	 
	 /**
	  * 请求超时
	  */
	 public static final String TIME_OUT="-101008";
	 
	 
	 
	 
	 
	 
	 /**
	  * 系统异常
	  */
	 public static final String ERROR_CODE_EXCEPTION="-102000";
	 
	 /**
	  * 参数不能为空
	  */
	 public static final String ERROR_CODE_NOPARMS="-102001";
	 /**
	  * 用户没有配置角色
	  */
	 public static final String ERROR_CODE_NOROLES="-102002";
	 /**
	  * 系统没有配置资源数据
	  */
	 public static final String ERROR_CODE_SYSTEMNORESOURCES="-102003";
	 /**
	  * 角色没有配置资源数据
	  */
	 public static final String ERROR_CODE_ROLESHASNORESOURCES="-102004";
	 /**
	  * 角色没有配置菜单数据
	  */
	 public static final String ERROR_CODE_ROLESHASNOMENUS="-102005";
	 
	 /**
	  * 参数格式错误
	  */
	 public static final String ERROR_CODE_PARAMSERROR="-102006";
	 /**
	  * 排序字段不正确
	  */  
	 public static final String ERROR_CODE_SORT="-102007";
	 /**
	  * 排序方式写法错误
	  */
	 public static final String ERROR_CODE_SORD="-102008";
	 /**
	  * 没有数据
	  */
	 public static final String ERROR_CODE_NODATA="-102009";
	 /**
	  * 签名不正确
	  */
	 public static final String ERROR_CODE_MD5="-102010";
	 /**
	  * 请求来源没有权限
	  */
	 public static final String ERROR_REQUEST_NOAUTH="-102011";
	 /**
	  * 用户不合法
	  */
	 public static final String ERROR_REQUEST_NOUSER="-102012";
	 /**
	  * 非法白名单
	  */
	 public static final String ERROR_REQUEST_NOWHITEIP="-102013";
	 /**
	  * 请求时间格式不正确
	  */
	 public static final String ERROR_REQUEST_REQUESTTIME="-102014";
	 /**
	  * sessionId不能为空
	  */
	 public static final String ERROR_REQUEST_SESSIONID="-102015";
	 /**
	  * sessionId不合法
	  */
	 public static final String ERROR_NO_SESSIONID="-102016";
	 
	 /**
	  * 非法字符
	  */
	 public static final String ERROR_ILLEGAL_PARAMERS="-102017";
	 
	 /**
	  * sessionId已存在，请更换
	  */
	 public static final String ERROR_EXIST_SESSIONID="-102018";
	 /**
	  * excel数据异常
	  */
	 public static final String ERROR_DOWNLOAD_EXCEL="-102019";
	 /**
	  * 数据重复
	  */
	 public static final String ERROR_DATA_REPEAT="-102020";
	 
	 /**
	  * 获取海康UUID异常
	  */
	 public static final String ERROR_REQUEST_HIKIUUID = "-1020201";
	 public static final String ERROR_REQUEST_TEMPORARYREGISTER = "-1020202";
	 public static final String ERROR_REQUEST_GETPESONINFO = "-1020203";
	 public static final String ERROR_REQUEST_HIKI="-1020204";
	 public static final String ERROR_HIKI_MEASSAGENOTICE="-1020205";
	 /**
	  * 非法白名单
	  */
	 public static final String FLOW_TURN_BACK="-275235";
	 
	 
	 /**
	  * 获取域名正则表达式
	  */
	 public static final String DOMAIN_URL_REGEX="(http://|https://)[\\s\\S]*?[/]";
	 public   static String DEFAULT_CHARSET="utf-8";
      public static final String INT="int";
      public static final String ID_FIELD_NAME="id";
      public static final String TYPE_FIELD_NAME="type";
      public static final String NOOPERATE_FIELD_NAME="nooperate";
      public static final String REALNAMEREG_FIELD_NAME="realnameabandonreg";
      public static final String SUCREG_FIELD_NAME="successreg";
      public static final String ALLOW_ALL_REQUEST="*.*.*";//允许所有的请求访问
      
      
      
      /**
  	 * 判断字段是否属于类
  	 * @param fieldName
  	 * @param classObj
  	 * @return
  	 */
  	public static boolean fieldInClass(String fieldName,Class<?> classObj){
  		try {
  			try{
  				Class<?> superClass=classObj.getSuperclass();
  	  			Field sltSuperField =superClass.getDeclaredField(fieldName);
  	  			if(sltSuperField!=null)
  					return true;
  			}catch(Exception e){
  			}
			Field sltField =classObj.getDeclaredField(fieldName);
			if(sltField!=null)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		} 
  	}
      
      
        
      /**
       * 获取对外接口返回的json格式数据
       * @param return_code
       * @param msg
       * @return
       */
      public static JSONObject getFcyRtnJson(String return_code,String msg){
  		if(!ResultConstants.hasText(msg))
  			msg="数据不合法，请重试！";
  		JSONObject jsonObject=new JSONObject();
  		jsonObject.put(ResultConstants.RETURN_CODE, return_code);
  		jsonObject.put(RETURN_MSG, msg);
  		return jsonObject;
  	}
      
      
      /**
       * 纠正排序特殊字段
       * @param sortName
       * @return
       */
       public static String getSortName(String sortName){
    	   if(!hasText(sortName))
    		   return "";
    	   else if(STR_CREATE_TIME.equals(sortName))
    		   return CREATE_TIME;
    	   else if(STR_UPDATE_TIME.equals(sortName))
    		   return UPDATE_TIME;
    	   else 
			return sortName;
       }
      
       
      
      
        /**
         * 随即获取数字字母组合
         * @param length 获取字符串组合的长度
         * @return
         */
		public static String getRandomCharAndNumr(Integer length) {  
		    String str = "";  
		    Random random = new Random();  
		    for (int i = 0; i < length; i++) {  
		        boolean b = random.nextBoolean();  
		        if (b) { // 字符串  
		            // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
		            str += (char) (65 + random.nextInt(26));// 取得大写字母  
		        } else { // 数字  
		            str += String.valueOf(random.nextInt(10));  
		        }  
		    }  
		    return str;  
		}  
		
		/**
		 * Url解码.
		 * 
		 * @param url
		 *            URL
		 * @param charSet
		 *            字符集
		 * @return 解码后的Url
		 * @throws UnsupportedEncodingException 
		 */
		public static String urlDecode(String url, String charSet) throws UnsupportedEncodingException {
			String decoded = url;
			decoded = URLDecoder.decode(url, charSet);
			return decoded;
		}
		
		   /**
	     * 判断字符串是否为空 null
	     * @param value
	     * @return
	     */
	    public static boolean hasText(String value){
	    	
	    	if(StringUtils.isBlank(value)||StringUtils.isEmpty(value))
	    		return false;
	    	else 
	    		return true;
	    		
	    }

	    /**
		 * Url编码(默认采用UTF-8编码).
		 * 
		 * @param url
		 *            URL
		 * @return 编码后的Url
	     * @throws UnsupportedEncodingException 
		 */
		public static String urlEncode(String url) throws UnsupportedEncodingException {
			return urlEncode(url, DEFAULT_CHARSET);
		}

		/**
		 * Url编码.
		 * 
		 * @param url
		 *            URL
		 * @param charSet
		 *            字符集
		 * @return 编码后的Url
		 * @throws UnsupportedEncodingException 
		 */
		public static String urlEncode(String url, String charSet) throws UnsupportedEncodingException {
			String encoded = url;
			encoded = URLEncoder.encode(url, charSet);
			return encoded;
		}
		
		/**
		 * 字符串开始字符为// 替换为/
		 * @param value
		 * @return
		 */
		public static String clearFstMoreUnderline(String value){
			if(!hasText(value))
				return value;
			if(value.length()>2){
				String splitValue=value.substring(0,2);
				String endValue=value.substring(2);
				if(splitValue.equals("//"))
					value="/"+endValue;
			}
			
			return value;
		}
		
		

		/**
		 * Url解码(默认采用UTF-8编码).
		 * 
		 * @param url
		 *            URL
		 * @return 解码后的Url
		 * @throws UnsupportedEncodingException 
		 */
		public static String urlDecode(String url) throws UnsupportedEncodingException {
			return urlDecode(url, DEFAULT_CHARSET);
		}

	    /**
	     * 清空session
	     * @param httpSession
	     */
	    public static void clearHttpSession(HttpSession httpSession){
            if(httpSession!=null){
            	Enumeration attributeNames = httpSession.getAttributeNames();  
    	        while(attributeNames.hasMoreElements()){  
    	            String attrName = attributeNames.nextElement().toString();  
    	            httpSession.setAttribute(attrName, null);  
    	            httpSession.removeAttribute(attrName);  
    	        } 
            }
	    }
	    /**
	     * 清空cookies
	     * @param request
	     */
	    public static void clearHttpCookies(HttpServletRequest request){
	    	Cookie[] cookies= request.getCookies();
	    	if(cookies!=null && cookies.length>0){
	    	    for(int i=0;i<cookies.length;i++){
	    	    	Cookie child=cookies[i];
	    	    	child.setValue(null);
	    	    }
	    	}
	    }
	    
	    /**
	     * 根据正则表达式获取对应字符
	     * @param regex
	     * @param str
	     * @return
	     */
	    public static List<String> getListDataByRegex(String regex,String str){
	    	
	    	List<String> lstData=new ArrayList<String>();
	    	Pattern p=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
			Matcher m=p.matcher(str);  
			while(m.find()){
				String value=m.group();
				lstData.add(value);
			}
			return lstData;
	    }
	    
	    
	    /**
	     * 根据参数获取json字符串
	     * @param params
	     * @return
	     */
	    public static String getJsonByParams(Object... params){
	    	JSONObject jsonObject=new JSONObject();
	    	if(params!=null ){
	    		for (int i = 0; i < params.length; i++) {
					String _param=String.valueOf(params[i]);
					String [] paramAry=_param.split(",");
					jsonObject.put(paramAry[0], paramAry.length>1?paramAry[1]:"");
				}
	    	}
	    	
	    	return jsonObject.toJSONString();
	    	
	    }
	    /**
	     * 获取请求报文转换成字符串
	     * @param request
	     * @return
	     * @throws IOException 
	     */
	    public static String getRequestBodyString(ServletRequest request) throws IOException {
	        StringBuilder sb = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = request.getInputStream();
	            reader = new BufferedReader(
	            		new InputStreamReader(inputStream,DEFAULT_CHARSET));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return sb.toString();
	    }
	    /**
		    * 错误信息响应
		    * @param httpServletResponse
		    * @param errorCode
		    * @param errorMsg
		    * @throws IOException
		    */
		    public static void errorResponse(HttpServletResponse httpServletResponse,String errorCode,String errorMsg) throws IOException{
		    	httpServletResponse.setCharacterEncoding("UTF-8");
		        httpServletResponse.setContentType("application/json;charset=utf-8");
		        httpServletResponse.getWriter().write("{\"return_code\":\""+errorCode+"\",\"return_msg\":\""+errorMsg+"\"}");
		        httpServletResponse.getWriter().flush();
		    }
	    /**
	     * 校验时间合法性
	     * @param request
	     * @return
	     */
	    public static String isRightRequestTime(HttpServletRequest request){
			String requestTime=request.getParameter(ResultConstants.FCYPAGE_REQUESTTIME);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				format.parse(requestTime);
			} catch (Exception e) {
				return ResultConstants.ERROR_REQUEST_REQUESTTIME;
			}
			
			return ResultConstants.OK;
		}
	    
	    public static String isRightRequestTime(String requestTime){
			//String requestTime=request.getParameter(ResultConstants.FCYPAGE_REQUESTTIME);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				format.parse(requestTime);
			} catch (Exception e) {
				return ResultConstants.ERROR_REQUEST_REQUESTTIME;
			}
			
			return ResultConstants.OK;
		}
	    
	    
	    
	    
		/**
		 * 获取请求字段集合
		 * @param httpServletRequest
		 * @return
		 * @throws IOException 
		 */
		public  static Map<String, String> getRequestParamers(HttpServletRequest httpServletRequest) throws IOException{
			Map<String, String> requestMaps=new HashMap<String, String>();
			String method=httpServletRequest.getMethod();
			if("get".equals(method.toLowerCase())){
				Enumeration enums=httpServletRequest.getParameterNames();
		    	  while(enums.hasMoreElements()){
		    		  String parameter=enums.nextElement().toString();
		    		  String value=httpServletRequest.getParameter(parameter);
		    		  requestMaps.put(parameter, value);
		    	}
			}else if ("post".equals(method.toLowerCase())) {
				ServletInputStream  servletInputStream=httpServletRequest.getInputStream();
		    	if(null==servletInputStream)//post没有传数据
		    		return null;
		    	String strPostData = IOUtils.toString(httpServletRequest.getInputStream(),
		    			ResultConstants.DEFAULT_CHARSET);
		    	JSONObject paramsjsonObj= JSONObject.parseObject(strPostData);
		    	if(null!=paramsjsonObj){
		    		Iterator<Entry<String, Object>> iterators=paramsjsonObj.entrySet().iterator();
		    		while (iterators.hasNext()) {
		    			Entry<String, Object> entry=iterators.next();
		    			String key=entry.getKey();
		    			String value=entry.getValue()!=null?entry.getValue().toString():"";
		    			requestMaps.put(key, value);
					}
		    	}
		    	/*requestMaps.put(ResultConstants.FCYPAGE_REQUESTTIME,
		    			paramsjsonObj.getString(ResultConstants.FCYPAGE_REQUESTTIME));
		    	requestMaps.put(ResultConstants.FCYPAGE_SESSIONID, 
		    			paramsjsonObj.getString(ResultConstants.FCYPAGE_SESSIONID));
		    	requestMaps.put(ResultConstants.FCYPAGE_SIGNATURE,
		    			paramsjsonObj.getString(ResultConstants.FCYPAGE_SIGNATURE));*/
			}
			return requestMaps;
		}
	    
	    
}