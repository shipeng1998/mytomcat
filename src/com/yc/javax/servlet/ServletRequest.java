package com.yc.javax.servlet;

import java.util.Map;

public interface ServletRequest {
	
	public String getRealPath();
	
	public void setAttribute(String key,Object value);
	
	public Object getAttribute(String key);
	
	
	
	
	public String getParameter(String key);
	
	public Map<String,String> getParameterMap();
	
	
	//parse 从语法s
	public void parse();
	
	public String getServerName();
	
	public int getServerPort();
	
	
	

}
