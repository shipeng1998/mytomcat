�� ��һ:   http���������ṩ��̬��Դ����.        

     ���󲿷�:   
          3)ͼƬ
          	GET /xxx/xxx.jpg HTTP/1.1
			Referer: http://localhost:8080/wowotuanStatic/index.html
			Sec-Fetch-Dest: image
			User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.16
    
    ��Ӧ����:
          3)ͼƬ:
           HTTP/1.1 200 OK
           Accept-Ranges: bytes
			Content-Length: 92174      **
			Content-Type: image/jpeg   **
			Date: Sat, 11 Apr 2020 02:22:41 GMT
			ETag: W/"92174-1586571146000"
			Last-Modified: Sat, 11 Apr 2020 02:12:26 GMT
			Server: Apache-Coyote/1.1
			
			��Ӧʵ��
					
			
����������:
1. ���տͻ��˵������������������ļ��������·��. 
2. ��������ļ��Ƿ���ڣ�  ������-> 404ҳ��
          ���� ->  
                1����ȡ�����Դ
                2) ������ӦЭ��     Content-Type:  �����������Ӧ�е�  Content-Type������ʹ��ʲô��������������
                          text/html:  html  -> html��Ⱦ
                          css   :    css����
                          js:     js����
                          ͼƬ:   ͼƬ����. 
                          
                        Content-Length                      

�õ��ļ���:
			1. ServerSocket  ->  Socket  
			2. ���߳�
			3. log4j
			4. dom����


KittyServer:
	xml�Ľ����˿�; 
   ServerSocket ss=new ServerSocket(  �˿�) ;

    Socket s=ss.accept();
    Thread t=new Thread(  new ����(  s )  );
    t.start();
    
    
ע�������:
  1. HttpServletRequest���е�  private String readFromInputStream()������Ҫһ�ζ�ȡ���е�����ͷ����. 