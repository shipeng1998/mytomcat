package threadpool;

import java.util.Vector;

public class ThreadPoolManger {
	private int initThreads;//初始线程数
	private Vector vector;//Vector 类可以实现可增长的对象数组，默认大小为10
	private MyNotify notify;//通知接口
	
	public void setInitThread(int threadCount){
		this.initThreads=threadCount;
	}
	
	public ThreadPoolManger(int threadCount,MyNotify notify){
		this.setInitThread(threadCount);
		this.notify=notify;
		System.out.println("线程池开始。。。");
		vector=new Vector();
		for(int i=1;i<=threadCount;i++){
			SimpleThread thread=new SimpleThread(i,this.notify);
			vector.addElement(thread);//将指定的组件添加到此向量的末尾，将其大小增加1
			thread.start();
		}
	}
	
	public void process(Taskable task){
		int i;
		
		for(i=0;i<vector.size();i++){
			SimpleThread currentThread= (SimpleThread) vector.elementAt(i);
			if(!currentThread.isRunning()){
				System.out.println("Thread"+(i+1)+"开始执行");
				currentThread.setTask(task);//设置任务
				currentThread.setRunning(true);//设置当前线程的状态 true
				return;
			}
		}
		//如果 i==vector.size()，表明线程池所有的线程都在运行中
		if(i>=vector.size()){
			//扩容
			int temp=vector.size();
			for(int j=temp+1;j<=10+temp;j++){
				SimpleThread thread=new SimpleThread(i,this.notify);
				vector.addElement(thread);//将指定的组件添加到此向量的末尾，将其大小增加1
				thread.start();
			}
			System.out.println("重新扩容10个线程");
			//创建完成之后重新执行process
			this.process(task);
		}
	}

}
