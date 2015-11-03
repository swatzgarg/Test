import java.util.concurrent.ConcurrentHashMap;

public class KVStoreImpl implements KVStore{
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	@Override
	public void Put(String key, String value) {
		data.put(key, value);	
	}

	@Override
	public String Delete(String key) {
		return data.remove(key);		
	}

	@Override
	public String Get(String key) {
		return data.get(key);
	}

}
