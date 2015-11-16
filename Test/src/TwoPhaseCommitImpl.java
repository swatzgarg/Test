import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;


public class TwoPhaseCommitImpl implements TwoPhaseCommit{

	String s01 = "52.23.244.58";
	String s02 = "54.173.32.101";
	String instruction;
	String key;
	String value = "null";
	TwoPhaseCommit twoph;
	
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	public TwoPhaseCommitImpl(){
		
	}
	
	public TwoPhaseCommitImpl(String instruction,String key,String value){
		this.instruction = instruction;
		this.key = key;
		this.value = value;
	}
	
	public TwoPhaseCommitImpl(String instruction,String key){
		this.instruction = instruction;
		this.key = key;
	}
	
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
	
	public void sendVoteRequest() throws RemoteException, KeyNotFoundException{
		String response = twoph.receiveVoteRequest();
		if(response.equals("yes")){
			twoph.go(instruction,key,value);
			go(instruction,key,value);
		}
		else{
			twoph.abort();
			abort();
		}
	} 
	
	public void start(String hostname) throws NotBoundException, SecurityException, IOException, KeyNotFoundException {		
		Registry registry = LocateRegistry.getRegistry(hostname);
		twoph = (TwoPhaseCommit) registry.lookup(TwoPhaseCommit.nameRes);
		
		sendVoteRequest();
	}

	public void sendToPeers(String instruction,String key,String value) throws SecurityException, NotBoundException, IOException, KeyNotFoundException{
		//start(s01);
		this.instruction = instruction;
		this.key = key;
		this.value = value;
		start(s02);
	}
	
}
