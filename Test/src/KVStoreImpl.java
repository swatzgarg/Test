import java.util.concurrent.ConcurrentHashMap;

public class KVStoreImpl implements KVStore{
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	@Override
	public void put(String key, String value) {
		data.put(key, value);
	}

	@Override
	public synchronized void delete(String key) throws KeyNotFoundException {
		if(data.containsKey(key)){
			data.remove(key);
		} else {
			throw new KeyNotFoundException();
		}
				
	}

	@Override
	public synchronized String get(String key) throws KeyNotFoundException {
		if(data.containsKey(key)){
			return data.get(key);
		} else {
			throw new KeyNotFoundException();
		}
	}

}
