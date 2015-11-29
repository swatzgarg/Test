import java.rmi.RemoteException;

/*
 * This class implements the two phase commit protocol.
 * This supports rollback of the commit values
	 * Vote message clears the stored values.
	 * Commit message stores the last values
	 * Abort message applies back the stored values, if present.
 */
public class TwoPhaseCommitImpl implements TwoPhaseCommit{

	private DataStore data;
	private String key = null;
	private String value = null;
	
	public TwoPhaseCommitImpl(DataStore data) {
		this.data = data;
	}
	
	private synchronized void storeRollbackInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	private synchronized void rollback() {
		if(key != null && value != null)
			data.put(key, value);
	}

	@Override
	public ResponseTPC vote(String instruction, String key, String value) throws RemoteException {
		data.startTwoPhaseCommit();
		storeRollbackInfo(null, null);
		if (instruction.equals("put"))
			return ResponseTPC.ACK;
		if(data.containsKey(key)) {
			return ResponseTPC.ACK;
		}
		data.endTwoPhaseCommit();
		return ResponseTPC.NotReady;
	}

	@Override
	public void commit(String instruction,String key,String value) throws RemoteException, RollbackException {
		String oldValue = null;
		if(instruction.equals("put")){
			oldValue = data.put(key,value);
		}
		else{
			if(data.containsKey(key)){
				oldValue = data.remove(key);
			} else {
				throw new RollbackException();
			}			
		}
		storeRollbackInfo(key, oldValue);
		data.endTwoPhaseCommit();
	}
	
	@Override
	public void abort() throws RemoteException {
		rollback();
		data.endTwoPhaseCommit();
	}
	
	
}
