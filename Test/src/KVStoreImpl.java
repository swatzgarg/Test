import java.io.IOException;
import java.rmi.NotBoundException;

/*
 * Simple in-memory Key Value store
 * The functions are synchronized to enable multithreaded access.
 */
public class KVStoreImpl implements KVStore{
	TwoPhaseCommitImpl twophimp;
	
	//ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	public KVStoreImpl(TwoPhaseCommitImpl peer){
		twophimp = peer;
	}
	
	@Override
	public void put(String key, String value) throws SecurityException {
	
		try{
			twophimp.sendToPeers("put",key,value);
			
		}catch(KeyNotFoundException e){
			
		}catch(NotBoundException e){
			
		}catch(IOException e){
			
		}
		
		//data.put(key, value);
	}

	@Override
	public void delete(String key) throws KeyNotFoundException {
			
			try{
				twophimp.sendToPeers("remove",key,null);
				
			}catch(KeyNotFoundException e){
				
			}catch(NotBoundException e){
				
			}catch(IOException e){
				
			}
			//data.remove(key);
						
	}

	public String get(String key) throws KeyNotFoundException {
		return twophimp.get(key);
	}

}
