package com.yc.http.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.http.HttpServletRequest;

public class YcHttpServletRequest implements HttpServletRequest{
	private String method; //请求方法
	private String protocal;//协议版本
	private String serverName;//服务名
	private int serverPort;//端口
	private String requestURI;//资源的地址
	private String requestURL;//绝对路径
	private String contextPath;//项目上下文路径
	private String realPath=System.getProperty("user.dir")+"\\webapps";//user.dir 取到时当前class的路径
	private InputStream iis;
	
	private String queryString;
	
	public YcHttpServletRequest(InputStream iis){
		this.iis=iis;
		parse();
	}
	
	public void parse(){
		String requestInfoString=readFromInputstream();//从输入流读取请求头
		if(requestInfoString==null || "".equals(requestInfoString)){
			return;
		}
		//解析requestinfo字符串  method URI
		parseRequestInfoString(requestInfoString);
	}
	
	private void parseRequestInfoString(String requestInfoString){
		StringTokenizer st=new StringTokenizer(requestInfoString);//分割
		
		// GET /res/aaa/index.html HTTP/1.1
		if(st.hasMoreTokens()){
			this.method=st.nextToken();
			this.requestURI=st.nextToken();///res/aaa/index.html
			this.protocal=st.nextToken();
			this.contextPath="/"+ this.requestURI.split("/")[1];//contextPath上下文路径/res
		}
		
		//TODO: 后面的键值对 实体 暂时不管
		parseParameter(requestInfoString);
	}
	
	
	private void parseParameter(String protocal){
		//判断是否有问号，有则要取前面当作requestURI
		if(requestURI.indexOf("?")>=0){
			this.queryString=requestURI.substring(requestURI.indexOf("?")+1);
			this.requestURI=requestURI.substring(0,requestURI.indexOf("?"));
			
			//从queryString解析出参数name=a
			String[] ss=this.queryString.split("&");
			if(ss!=null&&ss.length>0){
				for(String s:ss){
					String[] paire=s.split("=");
					if(paire!=null&&paire.length>0){
						this.parameters.put(paire[0], paire[1]);
					}
				}
			}
		}
		if(this.method.equals("POST")){
			//POST实体的参数
			String ps=protocal.substring(protocal.indexOf("\r\n\r\n")+4);
			if(ps!=null && ps.length()>0){
				String[] ss=ps.split("&");
				if(ss!=null&&ss.length>0){
					for(String s:ss){
						String[] paire=s.split("=");
						if(paire!=null&&paire.length>0){
							this.parameters.put(paire[0], paire[1]);
						}
					}
				}
			}
		}
	}
	
	
	
	public String getQueryString() {
		return queryString;
	}

	//从输入流读取请数据
	public String readFromInputstream(){
		//1.从input 中读出所有的内容（http请求协议--》protocal）
		String protocal=null;
		//从 iis流中取protocal
		StringBuffer sb=new StringBuffer(1024*10);//10k
		int length=-1;
		byte [] bs=new byte[1024 *10];
		
		try {
			length=this.iis.read(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			length=-1;
		}
		for(int j=0;j<length;j++){
			sb.append((char)bs[j]);
		}
		protocal=sb.toString();
		return protocal;
		
		
		
	}

	public String getMethod() {
		return method;
	}

	public String getProtocal() {
		return protocal;
	}

	public String getServerName() {
		return serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getRealPath() {
		return realPath;
	}

	//父接口的参数   request.getAttribute("")
	private Map<String,Object> attribute=new HashMap<String, Object>();
	
	
	@Override
	public void setAttribute(String key, Object value) {
		attribute.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		
		return attribute.get(key);
	}
	
	private Map<String,String> parameters=new HashMap<String, String>();

	@Override
	public String getParameter(String key) {
		return parameters.get(key);
	}

	@Override
	public Map<String, String> getParameterMap() {
		
		return this.parameters;
	}

	@Override
	public ServletContext getServletContext() {
		
		return YcServletContext.getInstance();
	}

	

	

	

}
