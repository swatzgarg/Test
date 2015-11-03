Compile the Server , Client and KVStore
>javac RPCServer.java RPCClient.java KVStore.java KVStoreImpl.java KeyNotFoundException.java

 You need to start three consoles, one for the server, one for the client, and one for the RMIRegistry.
 Start with the Registry. You must be in the directory that contains the classes you have written. From there, enter the following:
 > rmiregistry

 The registry will start running.
 In the second console run the server
 >java RPCServer 9999

 In the third console start the client
 >java RPCClient localhost 9999
