package com.tianya.springcloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * @description
 *	自定义filter 拦截网关服务
 * @author TianwYam
 * @date 2022年3月2日下午6:40:19
 */
@Slf4j
@Component
public class AuthFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// 是否执行该过滤器 
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// 具体执行方法
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("当前执行方法：{},{}", request.getMethod(), 
				request.getRequestURL().toString());
		
		// 获取 token header
		String token = request.getHeader("token");
		if (StringUtils.hasText(token)) {
			// 对请求放行 继续路由
			ctx.setSendZuulResponse(true);
			ctx.setResponseStatusCode(200);
			return null ;
		}
		
		// 不对其路由
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(403);
		ctx.setResponseBody("权限不对，token不能为空");
		
		return null;
	}

	@Override
	public String filterType() {
		// 过滤器类型
		return "pre";
	}

	@Override
	public int filterOrder() {
		// 过滤器执行顺序，数字越小，优先级越高
		return 0;
	}

}
