Compile the Server , Client and KVStore
>javac RPCServer.java RPCClient.java KVStore.java KVStoreImpl.java KeyNotFoundException.java

To run server:
 >java -Djava.rmi.server.hostname=<IPAddress> RPCServer 9999

To run the client :
 >java RPCClient <IPAddress>

 <IPAddress> is the IP address of the server machine.