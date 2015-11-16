
/*
 * Simple in-memory Key Value store
 * The functions are synchronized to enable multithreaded access.
 */
public class KVStoreImpl implements KVStore{
	TwoPhaseCommitImpl twophimp;
	TPCCoordinator coordinator;

	public KVStoreImpl(TPCCoordinator coordinator,TwoPhaseCommitImpl peer){
		this.coordinator = coordinator;
		twophimp = peer;
	}

	@Override
	public void put(String key, String value) {

		try{
			coordinator.sendToPeers("put",key,value);
		}catch(Exception e){

		}

	}

	@Override
	public void delete(String key) throws KeyNotFoundException {

		try{
			coordinator.sendToPeers("remove",key,null);

		}catch(KeyNotFoundException e){
			throw e;
		}catch(Exception e){

		}			
	}

	public String get(String key) throws KeyNotFoundException {
		return twophimp.get(key);
	}

}
