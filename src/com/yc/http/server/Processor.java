package com.yc.http.server;

import com.yc.javax.servlet.ServletRequest;
import com.yc.javax.servlet.ServletResponse;

/**
 * 资源处理器:处理静态或动态的资源
 * @author 石鹏
 *
 */
public interface Processor {
	/**
	 * 
	 * @param request:请求对象 解析请求头 得到 uri，method(http),parse,parameter
	 * @param response 输出
	 */
	public void process(ServletRequest request,ServletResponse response);
	

}
