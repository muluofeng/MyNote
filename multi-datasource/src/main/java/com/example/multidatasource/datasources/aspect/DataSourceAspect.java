package com.example.multidatasource.datasources.aspect;

import com.example.multidatasource.datasources.DataSourceNames;
import com.example.multidatasource.datasources.DynamicDataSource;
import com.example.multidatasource.datasources.annotation.DataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("@annotation(com.example.multidatasource.datasources.annotation.DataSource)")
	public void dataSourcePointCut() {

	}

	/**
	 *    spring 的环绕通知和前置通知，后置通知有着很大的区别，主要有两个重要的区别：
	 *
	 * 1） 目标方法的调用由环绕通知决定，即你可以决定是否调用目标方法，而前置和后置通知是不能决定的，
	 *    他们只是在方法的调用前后执行通知而已，即目标方法肯定是要执行的。
	 * @param point
	 * @return
	 * @throws Throwable
	 */

	@Around("dataSourcePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		DataSource ds = method.getAnnotation(DataSource.class);
		if (ds == null) {
			DynamicDataSource.setDataSource(DataSourceNames.FIRST);
			logger.debug("set datasource is " + DataSourceNames.FIRST);
		} else {
			DynamicDataSource.setDataSource(ds.name());
			logger.debug("set datasource is " + ds.name());
		}

		try {
			return point.proceed();
		} finally {
			DynamicDataSource.clearDataSource();
			logger.debug("clean datasource");
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
