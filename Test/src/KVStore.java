import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KVStore extends Remote {
	
	public static final String nameRes = "KVStore";
	
	public String put(String key, String value) throws RemoteException;
	public String delete(String key) throws RemoteException;
	public String get(String key) throws RemoteException;
}
