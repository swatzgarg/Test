Compile the Server , Client and KVStore  ##This will create jar of client and server separately
> make

To run client from src folder
> java -cp ../bin/RPCClient.jar RPCClient <ServerIPaddress>

To run server from src folder
> java -cp ../bin/RPCServer.jar -Djava.rmi.server.hostname=<ServerIPaddress> RPCServer 9999