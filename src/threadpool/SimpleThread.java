package threadpool;

public class SimpleThread extends Thread{
	private boolean runningFlag;//运行的状态false
	private Taskable task;//要执行的操作
	private int threadNumber;//线程的编号
	private MyNotify myNotify;//通知接口
	
	
	//标志runnningFlag 用以激活线程
	public boolean isRunning(){
		return runningFlag;
	}
	
	
	public synchronized void setRunning(boolean flag){
		runningFlag=flag;
		if(flag){
			this.notifyAll();
		}
		
	}
	public synchronized void setTask(Taskable task){
		this.task=task;
	}


	//提示哪个线程工作
	public SimpleThread(int threadNumber,MyNotify notify){
		runningFlag=false;
		this.threadNumber=threadNumber;
		System.out.println("Thread:"+threadNumber+"started");
		this.myNotify=notify;
	}
	
	public synchronized void run(){
		try{
			while(true){
				if(!runningFlag){//如果是false，等待调用
					this.wait();
				}else{
					System.out.println("--------执行"+threadNumber+"---->done");
					Object returnValue=this.task.doTask();
					if(myNotify!=null){
						myNotify.notifyResult(returnValue);
					}
					System.out.println("线程"+threadNumber+"线程已经重新准备");
					setRunning(false);
				}
			}
		}catch(InterruptedException e){
			System.out.println("异常：Interruput");
		}
	}
	

}
