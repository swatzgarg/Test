Compile the Server , Client and KVStore  ##Run all commands from the directory RPCProject
> make --directory=src

To run client
> java -cp bin/RPCClient.jar RPCClient <ServerIPaddresses>

To run server
> java -cp <PathTO.jar>/RPCServer.jar -Djava.rmi.server.hostname=<ServerIPaddress> RPCServer 9999