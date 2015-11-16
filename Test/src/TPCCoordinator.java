import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class TPCCoordinator {
	String s01 = "52.23.244.58";
	String s02 = "54.173.32.101";
	int numberOfServers = 2;
	TwoPhaseCommit[] peerObjects;
	String[] hostnames;
	
	public void sendVoteRequest(String instruction,String key,String value) throws RemoteException, KeyNotFoundException{
		
		for(int i = 0; i < numberOfServers; i++){
			//System.out.println(peerObjects[i] + ":" + i);
			String response = peerObjects[i].receiveVoteRequest();
			
			if(response.equals("yes")){
				peerObjects[i].go(instruction,key,value);
				//go(instruction,key,value);
			}
			else{
				peerObjects[i].abort();
				//abort();
			}
		}

	} 

	public void sendToPeers(String instruction,String key,String value) throws SecurityException, NotBoundException, IOException, KeyNotFoundException{
		
		hostnames = new String[numberOfServers];
		hostnames[0] = "52.23.244.58";
		hostnames[1] = "54.173.32.101";
		
		peerObjects = new TwoPhaseCommit[numberOfServers];
		for(int i = 0; i < numberOfServers; i++){
			Registry registry = LocateRegistry.getRegistry(hostnames[i]);
			//System.out.println(hostnames[i] + " " + registry.list());
			peerObjects[i] = (TwoPhaseCommit) registry.lookup(TwoPhaseCommit.nameRes);
		}
		sendVoteRequest(instruction,key,value);
	}
}
