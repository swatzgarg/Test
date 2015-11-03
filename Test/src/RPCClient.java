import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RPCClient {
	KVStore store;
	
	public void getOperation() throws RemoteException{
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String ans;
		String instruction;
		String key;
		String value;
		
		while(true){
			
			System.out.println("Please enter the instruction to be executed on the KVStore.Type exit to quit client: ");
			instruction = reader.nextLine();
			
			if(instruction.toUpperCase().equals("PUT")){
				ans = "Instruction failed";
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				System.out.println("Please enter the value ");
				value = reader.nextLine();
				ans = store.put(key,value);
				System.out.println(ans);
			}
			else if(instruction.toUpperCase().equals("GET")){
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				ans = store.get(key);
				System.out.println("Returned: " + ans);
			}
			else if(instruction.toUpperCase().equals("DELETE")){
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				ans = store.delete(key);
				System.out.println(ans);
			}
			else if(instruction.toUpperCase().equals("EXIT") || instruction.toUpperCase().equals("QUIT")){
				reader.close();
				break;
			}
			else{
				System.out.println("Incorrect format/operation. Please enter only the instruction name Eg:PUT OR try only PUT/GET/DELETE");
			}
		}
	}
	
//	public void prefill() throws RemoteException{
//		//Prepopulating the hashtable
//		
//		store.put("New Delhi","India");
//		store.put("Texas","USA");
//		store.put("Washington","USA");
//		store.put("Rajasthan","India");
//		store.put("London","UK");
//		
//		getOperation();
//	}
	
	/**
	 * Starts the Client
	 * @param hostname
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException 
	 */

	public void start(String hostname, int port) throws RemoteException, NotBoundException, MalformedURLException {
		//System.setSecurityManager(new RMISecurityManager());
		
		Registry registry = LocateRegistry.getRegistry(hostname);
		store = (KVStore) registry.lookup(KVStore.nameRes);
		//prefill();	should not be in client.Will not support multithreading.Will not maintain state of kvstore
		getOperation();
	}
	
	public static void main(String args[]) throws UnknownHostException, RemoteException, NotBoundException, MalformedURLException{
		if(args.length != 2){
			System.out.println("Please provide server name and port number as arguments");
			return;
		}
		
		int port = Integer.parseInt(args[1]);
		RPCClient c = new RPCClient();
		c.start(args[0],port);
	}
	
}
