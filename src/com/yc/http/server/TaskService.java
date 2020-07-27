package com.yc.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import threadpool.Taskable;

public class TaskService implements Taskable {
	private Socket socket;
	private InputStream iis;
	private OutputStream oos;
	private boolean flag;

	public TaskService(Socket socket){
		this.socket=socket;
		try {
			this.iis=this.socket.getInputStream();
			this.oos=this.socket.getOutputStream();
			flag=true;
		} catch (IOException e) {
			YcConstants.logger.error("failed to get stream ",e);
			
			flag=false;
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object doTask() {
		if(flag){
			//包装一个HttpServletRequest对象，从iis中读取到数据，解析请求信息，保存信息
			YcHttpServletRequest request=new YcHttpServletRequest(this.iis);
			//包装一个HttpServletResponse对象 ，从request中取文件资源，构建响应头，会给客户端
			YcHttpServletResponse response=new YcHttpServletResponse(request, this.oos);
			//判断是静态资源还是动态
			System.out.println("---------"+request.getRequestURI());
			Processor processor=null;
			if(request.getRequestURI().endsWith(".do")){
				processor=new DynamicProcessor();
			}else{
				processor=new StaticProcessor();
			}
			processor.process(request, response);
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			YcConstants.logger.error("failed to close connection to client ",e);
		}
		
		return null;
	}
}
