import java.util.concurrent.ConcurrentHashMap;

public class KVStoreImpl implements KVStore{
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
	
	@Override
	public String put(String key, String value) {
		data.put(key, value);
		return "Put instruction successful";
	}

	@Override
	public String delete(String key) {
		if(data.containsKey(key)){
			data.remove(key);
			return "Delete instruction successful";
		}
		else{
			return "Delete instruction unsuccessful. Key does not exist";
		}
				
	}

	@Override
	public String get(String key) {
		if(data.containsKey(key)){
			return data.get(key);
		}
		else{
			return "Error: Get instruction unsuccessful. Key does not exist";
		}
	}

}
