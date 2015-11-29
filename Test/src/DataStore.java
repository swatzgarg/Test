import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

	private ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	private boolean fInTwoPhaseCommit = false;
	private long lockStartTime;
	//private long lockCurrentTime;
	
	public DataStore(){
		LockManager lockManager = new LockManager();
		lockManager.start();
	}
	
	public class LockManager extends Thread {
		public void run(){
			while(true){
				if(fInTwoPhaseCommit && System.currentTimeMillis() - lockStartTime >= 10000){
					endTwoPhaseCommit();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public synchronized void startTwoPhaseCommit() {
		while(this.fInTwoPhaseCommit);
		this.fInTwoPhaseCommit = true;
		lockStartTime = System.currentTimeMillis();
	}

	public synchronized void endTwoPhaseCommit() {
		this.fInTwoPhaseCommit = false;
	}
	
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
	
	public void put(String key, String value) {
		data.put(key, value);
	}
	
	public void remove(String key) {
		data.remove(key);
	}
	
	public String get(String key) {
		return data.get(key);
	}
	
}
