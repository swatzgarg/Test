import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Simple in-memory Key Value store
 * The functions are synchronized to enable multithreaded access.
 */
public class KVStoreImpl implements KVStore{
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	@Override
	public void put(String key, String value) throws SecurityException {
		ServerToServer stos = new ServerToServer("put",key,value);
	
		try{
			stos.sendToPeers();
		}catch(KeyNotFoundException e){
			
		}catch(NotBoundException e){
			
		}catch(IOException e){
			
		}
		
		data.put(key, value);
	}

	@Override
	public synchronized void delete(String key) throws KeyNotFoundException {
		if(data.containsKey(key)){
			
			ServerToServer stos = new ServerToServer("remove",key);
			
			try{
				stos.sendToPeers();
			}catch(KeyNotFoundException e){
				
			}catch(NotBoundException e){
				
			}catch(IOException e){
				
			}
			data.remove(key);
		} else {
			throw new KeyNotFoundException();
		}				
	}

	@Override
	public synchronized String get(String key) throws KeyNotFoundException {
		if(data.containsKey(key)){
			return data.get(key);
		} else {
			throw new KeyNotFoundException();
		}
	}
	
	public void abort(){
		
	}
	
	public void go(String instruction,String key,String value){
		if(instruction.equals("put")){
			data.put(key,value);
		}
		else{
			data.remove(key);
		}
	}
	
	public String receiveVoteRequest(){
		return "yes";
	}

}
