package com.yc.http.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yc.javax.servlet.http.HttpServletResponse;

public class YcHttpServletResponse implements HttpServletResponse {
	private OutputStream oos;
	private YcHttpServletRequest request;
	private String contentType;
	
	public YcHttpServletResponse(YcHttpServletRequest request,OutputStream oos){
		this.oos=oos;
		this.request=request;
	}
	
	/*
	 * 从request中取出uri 2.判断是否在本地有个这文件 没有就404
	 * 以输出流将文件写到客户端，要加入响应的协议
	 * 
	 * 	 
	 */
	
	public void sendRendirect(){
		String responseprotocal=null;//响应协议头
		byte[] fileContent=null;//响应的内容
		String uri=request.getRequestURI();//请求的资源的地址     /res/aaa/index.html
		File f=new File(request.getRealPath(), uri);       //请求的文件的绝对路径
		if(!f.exists()){
			fileContent=readFile(new File(request.getRealPath(),"/404.html"));
			responseprotocal=gen404(fileContent.length);
		}else{
			fileContent=readFile(f);
			responseprotocal=gen200(fileContent.length);
		}
		try {
			oos.write(responseprotocal.getBytes());
			oos.flush();
			oos.write(fileContent);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		   if(oos!=null){
			   try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		}
		
	}
	
	private byte[] readFile(File f){
		FileInputStream fis=null;
		//字节数组输出流 ：读取到字节数组后，存到内存
		ByteArrayOutputStream boas=new ByteArrayOutputStream();
		try{
			//读取这个文件
			fis=new FileInputStream(f);
			byte [] bs=new byte[1024];
			int length=-1;
			while((length=fis.read(bs, 0, bs.length))!=-1){
				boas.write(bs, 0, length);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return boas.toByteArray();//一次性地从内存中读取所有的字节数组返回
	}
	
	
	private String gen200(long bodylength){
		String uri=this.request.getRequestURI();//取出要访问的文件的地址
		int index=uri.lastIndexOf(".");
		if(index>=0){
			index=index+1;
		}
		String fileExtension=uri.substring(index);//文件的后缀名
		String protocal200="";
		if("JPG".equalsIgnoreCase(fileExtension) || "JPEG".equalsIgnoreCase(fileExtension)){
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: image/JPEG\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}else if("PNG".equalsIgnoreCase(fileExtension)){
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: image/PNG\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}else if("json".equalsIgnoreCase(fileExtension)){
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}else if("css".equalsIgnoreCase(fileExtension)){
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: text/css\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}else if("js".equalsIgnoreCase(fileExtension)){
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: text/javascript\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}else{
			contentType="text/html";
			protocal200="HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: "+bodylength+"\r\n\r\n";
		}
		return protocal200;
	}
	
	
	//404响应
	private String gen404(long bodylength){
		String protocal404="HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: "+bodylength+"\r\n\r\n";
		return protocal404;
	}

	@Override
	public PrintWriter getWriter() {
		//oos字节流    writer字符流
		PrintWriter pw=new PrintWriter(this.oos);
		return pw;
	}

	@Override
	public String getContentType() {
		
		return this.contentType;
	}

	
	

}
