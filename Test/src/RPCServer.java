import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RPCServer {

	public void start(int port) throws RemoteException, AlreadyBoundException {
		KVStore store = new KVStoreImpl();
		KVStore stub = (KVStore) UnicastRemoteObject.exportObject(store, port);
		
		Registry registry = LocateRegistry.getRegistry();
		registry.bind("KVStore", stub);
	}
}
