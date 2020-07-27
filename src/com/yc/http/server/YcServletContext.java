package com.yc.http.server;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.yc.javax.servlet.Servlet;
import com.yc.javax.servlet.ServletContext;
//单例模式
public class YcServletContext implements ServletContext{
	private Map<String,Servlet> servlets=new Hashtable<String,Servlet>();  //hashtable线程安全
	private Map<String,Object> attributes=new HashMap<String,Object>();
	
	public static YcServletContext ycServletContext;
	//构造方法私有化
	private YcServletContext(){}
	//对外提供唯一实例方法
	public synchronized static YcServletContext getInstance(){
		if(ycServletContext==null){
			ycServletContext=new YcServletContext();
		}
		return ycServletContext;
	}
	

	@Override
	public Map<String, Servlet> getServlets() {
		
		return this.servlets;
	}

	@Override
	public Servlet getServlet(String servletName) {
		
		return this.servlets.get(servletName);
	}

	@Override
	public void setServlet(String key, Servlet servlet) {
		this.servlets.put(key, servlet);
		
	}

	@Override
	public void setAttribute(String key, Object obj) {
		this.attributes.put(key, obj);
		
	}

	@Override
	public Object getAttribute(String key) {
	
		return this.attributes.get(key);
	}

}
