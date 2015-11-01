import java.rmi.Remote;

public interface KVStore extends Remote{
	public void Put(String key, String value);
	public String Delete(String key);
	public String Get(String key);
}
