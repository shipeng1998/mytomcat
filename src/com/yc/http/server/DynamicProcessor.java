package com.yc.http.server;

import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import com.yc.javax.servlet.Servlet;
import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.ServletRequest;
import com.yc.javax.servlet.ServletResponse;
import com.yc.javax.servlet.http.HttpServlet;
import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;

public class DynamicProcessor implements Processor {

	@Override
	public  void process(ServletRequest request, ServletResponse response) {
	try{
			//1.取出uri，从uri取出请求的servlet名字
		String uri=((HttpServletRequest)request).getRequestURI();
		String servletName=uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));//helloservlet
		Servlet servlet=null;
		ServletContext application=YcServletContext.getInstance();//单例
		//application中判断是否有这个servlet
		if(application.getServlet(servletName)!=null){
			servlet=application.getServlet(servletName);
		}else{
			//2.加载动态字节码     helloservlet.class文件
			URL[] urls=new URL[1];
			
				urls[0]=new URL("file", null,YcConstants.KITTYSERVER_BASEPATH);//
				URLClassLoader ucl=new URLClassLoader(urls); //classloader类加载器会自动扫描 urls数组中的指定的路径看是否有class
				//3. URL地址 --》file：\\\
				//4.Class c=ucl.loadClass(类的名字)
				Class c=ucl.loadClass(servletName); //在这个路径下，加载指定的servletName的 class字节码
				
				//5.以反射的形式 newInstance()创建servlet实例
				//6.c.newInstance() -->调用的构造方法
				Object o=c.newInstance();
				if(o!=null && o instanceof Servlet){
					 servlet=(Servlet)o;
					 application.setServlet(servletName, servlet);
					//生命周期的调用
					servlet.init();
					
				}
		}
		if(servlet!=null && servlet instanceof Servlet){
			//父类引用只能调用子类重写了父类的方法而不能调用子类特有的方法
			
			((HttpServlet)servlet).service((HttpServletRequest)request, (HttpServletResponse)response);
		}
		
			
			
			
		}catch(Exception e){
			String bodyentity=e.toString();
			String protocal=gen500(bodyentity.getBytes().length);
			PrintWriter pw=response.getWriter();
			pw.println(protocal);
			pw.println(bodyentity);
			pw.flush();
			
		}
		
	}
	
	private String gen500(long bodylength){
		String protocal500="HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: "+bodylength+"\r\n\r\n";
	return protocal500;
	}

}
