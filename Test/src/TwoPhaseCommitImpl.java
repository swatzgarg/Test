import java.util.concurrent.ConcurrentHashMap;


public class TwoPhaseCommitImpl implements TwoPhaseCommit{
	
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	public synchronized String get(String key) throws KeyNotFoundException {
		if(data.containsKey(key)){
			return data.get(key);
		} else {
			throw new KeyNotFoundException();
		}
	}
	
	public void abort(){
		
	}
	
	public synchronized void go(String instruction,String key,String value) throws KeyNotFoundException{
		if(instruction.equals("put")){
			//System.out.println("put(" + key + "," + value + ")");
			data.put(key,value);
		}
		else{
			//System.out.println("delete(" + key + ")");
			if(data.containsKey(key)){
				data.remove(key);
			} else {
				throw new KeyNotFoundException();
			}
			
		}
	}
	
	public String receiveVoteRequest(){
		return "yes";
	}
	
	
}
