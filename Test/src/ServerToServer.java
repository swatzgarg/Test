import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerToServer {

	String s01 = "52.23.244.58";
	String s02 = "54.173.32.101";
	private KVStore store;
	String instruction;
	String key;
	String value = "null";
	
	public ServerToServer(String instruction,String key,String value){
		this.instruction = instruction;
		this.key = key;
		this.value = value;
	}
	
	public ServerToServer(String instruction,String key){
		this.instruction = instruction;
		this.key = key;
	}
	
	
	public void sendVoteRequest() throws RemoteException, KeyNotFoundException{
		String response = store.receiveVoteRequest();
		if(response.equals("yes")){
			store.go(instruction,key,value);
		}
		else{
			store.abort();
		}
	} 
	
	public void start(String hostname) throws NotBoundException, SecurityException, IOException, KeyNotFoundException {		
		Registry registry = LocateRegistry.getRegistry(hostname);
		store = (KVStore) registry.lookup(KVStore.nameRes);
		
		sendVoteRequest();
	}

	public void sendToPeers() throws SecurityException, NotBoundException, IOException, KeyNotFoundException{
		//start(s01);
		start(s02);
	}
	
}
