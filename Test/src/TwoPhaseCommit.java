import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TwoPhaseCommit extends Remote {
	public static final String nameRes = "ReplicaServer";
	
	public String receiveVoteRequest() throws RemoteException, KeyNotFoundException;
	public void go(String instruction,String key,String value) throws RemoteException, KeyNotFoundException;
	public void abort() throws RemoteException, KeyNotFoundException;
}
