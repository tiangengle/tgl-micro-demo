package com.tgl.eurekaclient.aspect;

import com.tgl.eurekaclient.utils.RemoteIPUtil;
import com.tgl.eurekaclient.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author tgl
 * @date 2020-05-06
*/
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = null;
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
            request = sra.getRequest();
        }
        return request;
    }

    @Pointcut("execution(public * com.tgl.eurekaclient.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String ip = RemoteIPUtil.getIp(request);
            ThreadLocalUtil.set(ip);
        } catch (Exception e) {
            log.error("DoBeforeError", e);
        }
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void doAfterReturning(Object result)  {
        try {
            HttpServletRequest request = getHttpServletRequest();
            // 获取ip
            String ip = RemoteIPUtil.getIp(request);
            // 记录下请求内容
            StringBuilder queryBuilder = new StringBuilder();
            String queryString = request.getQueryString();
            if (request.getMethod().equals("POST")) {
                StringBuilder stringBuilder = new StringBuilder();
                Map<String, String[]> params = request.getParameterMap();
                for (String key : params.keySet()) {
                    String[] values = params.get(key);
                    stringBuilder.append(key).append("=").append(Arrays.toString(values)).append(" ");
                }
                queryString = stringBuilder.toString();
            }
            queryBuilder.append(request.getMethod())
                                   .append(" ").append(ip)
                                   .append(" ").append(request.getRequestURL());
            if (StringUtils.isNotBlank(queryString)) {
                queryBuilder.append("?").append(queryString);
            }
            log.info("request:{},result:{}",queryBuilder.toString(),result);
        } catch (Exception e) {
            log.error("doAfterError", e);
        }
    }
}
