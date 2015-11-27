import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TPCCoordinator {
	private TwoPhaseCommit[] peerObjects;
	private String[] hostnames;
	
	public TPCCoordinator(String[] servers) {
		this.hostnames = servers;
		peerObjects = new TwoPhaseCommit[servers.length];
	}
	
	public void commit(String instruction,String key,String value) throws NotBoundException, AccessException, RemoteException {
		for (int i = 0; i < hostnames.length; i++) {
			Registry registry = LocateRegistry.getRegistry(hostnames[i]);
			peerObjects[i] = (TwoPhaseCommit) registry.lookup(TwoPhaseCommit.nameRes);
			}
	
		System.out.println("Starting voting");
		boolean fVoted = true;
		for (int i = 0; i < hostnames.length; i++) {
			try {
				ResponseTPC response = peerObjects[i].vote(instruction, key, value) ;
				if(response != ResponseTPC.ACK) {
					System.out.println(hostnames[i] + " returned reponse " + response );
					fVoted = false;
				}
			} catch (RemoteException e) {
				System.out.println("Exception while voting by " + hostnames[i]);
				fVoted = false;
			}
		}
		System.out.println("Ending  voting with result = " + fVoted);

		boolean fCommitted = true;
		if (fVoted) {
			System.out.println("Starting commiting.");
			for (int i = 0; i < hostnames.length; i++) {
				try {
					peerObjects[i].commit(instruction, key, value);
				} catch (RollbackException | RemoteException e) {
					fCommitted = false;
				}
			}
			System.out.println("Ending  commiting with result = " + fCommitted);	
		}
		if (!fVoted || !fCommitted) {
			System.out.println("Aborting the commit");
			for (int i = 0; i < hostnames.length; i++) {
				try {
					peerObjects[i].abort();
				} catch (RemoteException e) {
				}
			}
		}
	} 

}
