import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RPCClient {

	public void start(String hostname, int port) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(hostname, port);
		KVStore kvstore = (KVStore) registry.lookup(KVStore.nameRes);
		// test
		kvstore.Put("aa","bb");
		
		
	}
	
}
