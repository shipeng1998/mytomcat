package com.yc.javax.servlet;

import java.util.Map;

/**
 * application容器
 * @author 石鹏
 *
 */
public interface ServletContext {
	//获取所有的servlet
	public Map<String,Servlet> getServlets();
	
	public Servlet getServlet(String servletName);
	
	public void setServlet(String key,Servlet servlet);
	
	public void setAttribute(String key,Object obj);
	
	public Object getAttribute(String key);

}
