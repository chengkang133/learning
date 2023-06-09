package com.itheima.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.itheima.pinda.base.R;
import com.itheima.pinda.common.adapter.IgnoreTokenConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础过滤器 统一抽取一些公共属性和方法。
 */
public abstract class BaseFilter extends ZuulFilter {
    // /api
    @Value("${server.servlet.context-path}")
    protected String zuulPrefix;

    // 判断当前请求uri是否需要忽略（直接放行）
    boolean isIgnoreToken() {
        // 动态获取当前请求的uri
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String uri = request.getRequestURI();
        uri = StrUtil.subSuf(uri, zuulPrefix.length());
        uri = StrUtil.subSuf(uri, uri.indexOf("/", 1));
        return IgnoreTokenConfig.isIgnoreToken(uri);
    }

    /**
     * 网关抛异常
     *
     * @param errMsg
     * @param errCode
     * @param httpStatusCode
     */
    void errorResponse(String errMsg, int errCode, int httpStatusCode) {
        RequestContext ctx = RequestContext.getCurrentContext();
        //设置响应状态码
        ctx.setResponseStatusCode(httpStatusCode);
        //设置响应头信息
        ctx.addZuulResponseHeader("Content-Type", "application/json;charset=utf-8");
        if (ctx.getResponseBody() == null) {
            //设置响应体
            ctx.setResponseBody(R.fail(errCode, errMsg).toString());
            //不进行路由，直接返回
            ctx.setSendZuulResponse(false);
        }
    }
}
