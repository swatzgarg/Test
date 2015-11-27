import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

	private ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	private boolean fInTwoPhaseCommit = false;
	
	public synchronized void startTwoPhaseCommit() {
		while(this.fInTwoPhaseCommit);
		this.fInTwoPhaseCommit = true;
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
