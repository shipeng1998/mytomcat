package com.yc.javax.servlet;

/**
 * Servlet接口来定义生命周期的回调方法
 * @author 石鹏
 *
 */
public interface Servlet {
	
	/**
	 * 开始
	 */
	public void init();
	
	/**
	 * 销毁
	 */
	public void destroy();
	
	public void service( ServletRequest request,ServletResponse response);

}
