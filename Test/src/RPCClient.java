import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RPCClient {

	{
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tc %4$s: %5$s%6$s%n");
	}
	
	private KVStore store;
	private final String logFileName="client.log";
	private Logger log;
		
	private void ConsoleUI(){
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String ans;
		String instruction;
		String key;
		String value;
		
		while(true){
			
			System.out.println("Please enter the instruction to be executed on the KVStore.Type exit to quit client: ");
			instruction = reader.nextLine();
			
			if(instruction.toUpperCase().equals("PUT")) {
				ans = "Instruction failed";
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				System.out.println("Please enter the value ");
				value = reader.nextLine();
				try {
					store.put(key,value);
				}
				catch(RemoteException e) {
					System.out.println("Error happened");
					log.log(Level.SEVERE, "Error Happened", e);
				}
			}
			else if(instruction.toUpperCase().equals("GET")) {
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				try {
					ans = store.get(key);
					System.out.println("Returned: " + ans);
				}
				catch (KeyNotFoundException e) {
					System.out.println("Key not found");
					log.log(Level.SEVERE, "Key not found", e);
				}
				catch(RemoteException e) {
					System.out.println("Error happened");
					log.log(Level.SEVERE, "Error Happened", e);
				}
			}
			else if(instruction.toUpperCase().equals("DELETE")){
				System.out.println("Please enter the key ");
				key = reader.nextLine();
				try {
					store.delete(key);
				}
				catch (KeyNotFoundException e) {
					System.out.println("Key not found");
					log.log(Level.SEVERE, "Key not found", e);
				}
				catch(RemoteException e) {
					System.out.println("Error happened");
					log.log(Level.SEVERE, "Error Happened", e);
				}
			}
			else if(instruction.toUpperCase().equals("EXIT")){
				reader.close();
				break;
			}
			else{
				System.out.println("Incorrect instruction. Please enter PUT, GET, DELETE or EXIT");
			}
		}
	}
	
	/**
	 * Starts the Client
	 * @param hostname
	 * @param port
	 * @throws NotBoundException
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public void start(String hostname, int port) throws NotBoundException, SecurityException, IOException {		
		Registry registry = LocateRegistry.getRegistry(hostname);
		store = (KVStore) registry.lookup(KVStore.nameRes);
	
		log = Logger.getLogger("client");
		log.setUseParentHandlers(false);		
		FileHandler handler = new FileHandler(logFileName, false);
		SimpleFormatter formatter = new SimpleFormatter();
		handler.setFormatter(formatter);
		log.addHandler(handler);
		
		ConsoleUI();
	}
	
	public static void main(String args[]) throws NotBoundException, SecurityException, IOException{
		if(args.length != 2) {
			System.out.println("Please provide server name and port number as arguments");
			return;
		}
		
		int port = Integer.parseInt(args[1]);
		String serverName = args[0];
		RPCClient client = new RPCClient();
				
		client.start(serverName,port);
	}
	
}
