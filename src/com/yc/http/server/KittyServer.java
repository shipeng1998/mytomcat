package com.yc.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import threadpool.ThreadPoolManger;

public class KittyServer {
	private ThreadPoolManger tpm;//线程池管理器
	
	public static void main(String[] args) throws Exception {
		KittyServer ks=new KittyServer();
		ks.startServer();
	}
	
	
	
	private synchronized void startServer() throws Exception{
		ServerSocket ss=null;
		//TODO: 修改解析 xml 为一个map，参数都可以从map中读取
		int port=parseServerXml();
		//TODO:线程池配置可以从xml读
		tpm=new ThreadPoolManger(10, null);
		
		try {
			ss=new ServerSocket(port);
			YcConstants.logger.debug("kitty server is starting,and listening to port"+ss.getLocalPort());
			
		} catch (IOException e) {
			YcConstants.logger.debug("kitty server is port "+port+"is aleready in use...");
			return;
		}
		
		while(true){
			try {
				Socket s=ss.accept();
				YcConstants.logger.debug("a client"+s.getInetAddress()+"is connecting to kittyserver");
				TaskService ts=new TaskService(s);
				//TODO:  判断是否采用了线程池，是则使用
				tpm.process(ts);
				
				
				//Thread t=new Thread(ts);
				//t.start();
			} catch (Exception e) {
				YcConstants.logger.debug("client is down ,cause:"+e.getMessage());
			}
		}
	}
	
	/*
	 * TODO: 可以改写xml 返回一个map<String,Object>
	 * {"port":"8083","conectionPool":{"num":"10","closeable":"true"}}
	 * 
	 */
	private int parseServerXml() throws Exception{
		List<Integer> list=new ArrayList<Integer>();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();//通过DocumentBuilderFactory创建xml解析器
		try{
			DocumentBuilder bulider=factory.newDocumentBuilder();//通过解释器创建一个可以加载并生成xml的DocumentBuilder
			Document doc=bulider.parse(YcConstants.SERVERCONFIG);//通过DocumentBuilder加载并生成一颗xml树，Document对象的实例
			NodeList nl=doc.getElementsByTagName("Connector");//通过document可以遍历这棵树，并读取相应的节点的内容
			for(int i=0;i<nl.getLength();i++){
				Element node=(Element)nl.item(i);
				list.add(Integer.parseInt(node.getAttribute("port")));
			}
		}catch(Exception e){
			throw e;
		}
		return list.get(0);
	}

}
