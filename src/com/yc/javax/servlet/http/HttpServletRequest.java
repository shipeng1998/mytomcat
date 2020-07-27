package com.yc.javax.servlet.http;

import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.ServletRequest;

public interface HttpServletRequest extends ServletRequest{
	
	/**
	 * 
	 * 请求的方法
	 * @return
	 */
	public String getMethod();
	
	public String getRequestURI();
	
	//获取application的方法
	public ServletContext getServletContext();

}
