import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TwoPhaseCommit extends Remote {
	public static final String nameRes = "ReplicaServer";
	
	public ResponseTPC vote(String instruction,String key,String value) throws RemoteException;
	public void commit(String instruction,String key,String value) throws RemoteException, RollbackException;
	public void abort() throws RemoteException;
}
