import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RPCServer {

	/**
	 * Starts the server
	 * @param port
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 * @throws AlreadyBoundException 
	 */
	
	public void start(int port) throws RemoteException, MalformedURLException, AlreadyBoundException {
		//System.setSecurityManager(new RMISecurityManager());
		KVStore store = new KVStoreImpl();											//Creates object of type KVStore
		KVStore stub = (KVStore) UnicastRemoteObject.exportObject(store,port);//Creates stub of type KVStore and exports it on specified port
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(KVStore.nameRes, stub);	
	}
	
	public static void main(String args[]) throws AlreadyBoundException, RemoteException, MalformedURLException{
		if(args.length != 1){
			System.out.println("Please provide server port number as argument");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		RPCServer s = new RPCServer();
		s.start(port);
	}
}
