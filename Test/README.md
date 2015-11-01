Compile the Server , Client and Interface
>javac Server.java Client.java KVInterface.java

Use rmic to create the stub and skeleton class files.
 > rmic Server

 You need to start three consoles, one for the server, one for the client, and one for the RMIRegistry.
 Start with the Registry. You must be in the directory that contains the classes you have written. From there, enter the following:
 > rmiregistry

 The registry will start running.
 In the second console run the server
 >java Server

 In the third console start the client
 >java Client
