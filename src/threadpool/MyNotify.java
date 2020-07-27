package threadpool;

public interface MyNotify {
	/**
	 * 通知接口：用于通知主线程，当前线程中的任务执行的情况
	 * @param result
	 */
	public void notifyResult(Object result);

}
