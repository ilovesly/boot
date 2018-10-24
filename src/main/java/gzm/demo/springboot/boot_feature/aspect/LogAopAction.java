package gzm.demo.springboot.boot_feature.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.config.ResultConstants;

import gzm.demo.spring.springboot.boot_feature.constants.MdcConstant;
import gzm.demo.springboot.boot_feature.entity.Log;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

/**
 * Created by zhenmin.gu on 2018/08/06
 */
@Aspect
@Component
public class LogAopAction {
	private static final Logger logger = LoggerFactory.getLogger(LogAopAction.class);
	// 获取开始时间
	private long BEGIN_TIME;

	// 获取结束时间
	private long END_TIME;

	// 定义本次log实体
	private Log log = new Log();

	@Pointcut("execution(public * gzm.demo.springboot.boot_feature.web.*.*(..))")
	private void controllerAspect() {
	}

	/**
	 * 方法开始执行
	 */
	@Before("controllerAspect()")
	public void doBefore() {
		// String requestId = UUID.randomUUID().toString().replace("-", "");
		// MDC.put(MdcConstant.REQUEST_KEY, "请求编号： " + requestId + " ");
		// RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		// ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		// HttpServletRequest request = sra.getRequest();
		// StringBuilder sb =new StringBuilder();
		// BEGIN_TIME = new Date().getTime();
		//// uuid = CommonTools.getUUID();
		//// logger.info(request.getServletPath()+"开始");
		// sb.append("请求参数:");
		// try {
		// Map<String, String> requestMaps =
		// ResultConstants.getRequestParamers(request);
		// if (null != requestMaps && 0 < requestMaps.size()){
		// JSONObject jsonObject = new JSONObject();
		// Iterator<Entry<String,String>> iterators =
		// requestMaps.entrySet().iterator();
		// while (iterators.hasNext()){
		// Entry<String ,String> entry = iterators.next();
		// jsonObject.put(entry.getKey(),entry.getValue());
		// }
		// sb.append(jsonObject.toJSONString());
		//
		// }
		//// request.setAttribute("uuid", uuid);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// logger.info(sb.toString());
	}

	/**
	 * 方法结束执行
	 */
	@After("controllerAspect()")
	public void after() {
		END_TIME = new Date().getTime();
		log.setActionTime(END_TIME - BEGIN_TIME);
		logger.info("结束" + "  耗时 【" + log.getActionTime() + "】毫秒");
		MDC.remove(MdcConstant.REQUEST_KEY);
	}

	/**
	 * 方法结束执行后的操作
	 */
	@AfterReturning("controllerAspect()")
	public void doAfter() {

		if (log.getState() == 1 || log.getState() == -1) {
			// log.setActionTime(END_TIME-BEGIN_TIME);
			// logger.info("耗时 "+ log.getActionTime() + "秒");
			log.setGmtCreate(new Date(BEGIN_TIME));
		} else {
		}
	}

	/**
	 * 方法有异常时的操作
	 */
	@AfterThrowing("controllerAspect()")
	public void doAfterThrow() {
		// 删除SessionId
		logger.info("例外通知-----------------------------------");
		MDC.remove(MdcConstant.REQUEST_KEY);
	}

	/**
	 * 方法执行
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		// Object object = new Object();
		// //日志实体对象
		String requestId = UUID.randomUUID().toString().replace("-", "");
		MDC.put(MdcConstant.REQUEST_KEY, "请求编号： " + requestId + "  ");
		// RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		// ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		// HttpServletRequest request = sra.getRequest();
		StringBuilder sb = new StringBuilder();
		BEGIN_TIME = new Date().getTime();
		// uuid = CommonTools.getUUID();
		// logger.info(request.getServletPath()+"开始");
		sb.append("请求参数:");
		// try {
		// Map<String, String> requestMaps =
		// ResultConstants.getRequestParamers(request);
		// if (null != requestMaps && 0 < requestMaps.size()){
		// JSONObject jsonObject = new JSONObject();
		// Iterator<Entry<String,String>> iterators =
		// requestMaps.entrySet().iterator();
		// while (iterators.hasNext()){
		// Entry<String ,String> entry = iterators.next();
		// jsonObject.put(entry.getKey(),entry.getValue());
		// }
		// sb.append(jsonObject.toJSONString());
		//
		// }
		//// request.setAttribute("uuid", uuid);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// logger.info(sb.toString());

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		// 拦截的实体类，就是当前正在执行的controller
		Object target = pjp.getTarget();
		// 拦截的方法名称。当前正在执行的方法
		String methodName = pjp.getSignature().getName();
		// 拦截的方法参数
		Object[] args = pjp.getArgs();
		String method1 = request.getMethod();
		// post且有参数
		if (args.length == 3) {
			JSONObject jsonObject = new JSONObject();
			jsonObject = (JSONObject) args[0];
			logger.info(jsonObject.toJSONString());
		} else if ("get".equals(method1.toLowerCase())) {
			Map<String, String> requestMaps = new HashMap<String, String>();
			Enumeration enums = request.getParameterNames();
			JSONObject jsonObject = new JSONObject();
			while (enums.hasMoreElements()) {
				String parameter = enums.nextElement().toString();
				String value = request.getParameter(parameter);
				jsonObject.put(parameter, value);
//				requestMaps.put(parameter, value);
			}
			sb.append(jsonObject.toJSONString());
			logger.info(jsonObject.toJSONString());
//
//			if (null != requestMaps && 0 < requestMaps.size()) {
//				JSONObject jsonObject = new JSONObject();
//				Iterator<Entry<String, String>> iterators = requestMaps.entrySet().iterator();
//				while (iterators.hasNext()) {
//					Entry<String, String> entry = iterators.next();
//					jsonObject.put(entry.getKey(), entry.getValue());
//				}
//				sb.append(jsonObject.toJSONString());
//
//			}
		}

		// 拦截的放参数类型
		Signature sig = pjp.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Class[] parameterTypes = msig.getMethod().getParameterTypes();

		Object object = null;
		//
		 Method method = null;
		try {
			method = target.getClass().getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (null != method) {
			// 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
			if (method.isAnnotationPresent(SystemLog.class)) {
				SystemLog systemlog = method.getAnnotation(SystemLog.class);
				log.setModule(systemlog.module());
				log.setMethod(systemlog.methods());
				// log.setLoginIp(getIp(request));
				// log.setActionUrl(request.getRequestURI());

				try {
					object = pjp.proceed();
					log.setDescription("执行成功");
					log.setState((short) 1);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					log.setDescription("执行失败");
					log.setState((short) -1);
				}
			} else {// 没有包含注解
				object = pjp.proceed();
				log.setDescription("此操作不包含注解");
				log.setState((short) 0);
			}
		} else { // 不需要拦截直接执行
			object = pjp.proceed();
			log.setDescription("不需要拦截直接执行");
			log.setState((short) 0);
		}
		logger.info(request.getServletPath() + " 返回结果 " + JSONObject.toJSONString(object));
		return object;
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIp(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}