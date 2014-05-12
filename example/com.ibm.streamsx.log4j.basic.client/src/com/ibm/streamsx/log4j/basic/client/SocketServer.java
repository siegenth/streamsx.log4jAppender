/***

#configuring the SOCKET appender
log4j.appender.SOCKET=org.apache.log4j.net.SocketAppender
log4j.appender.SOCKET.remoteHost=localhost
log4j.appender.SOCKET.port=1000


To see SocketAppender in action, we will need to first run the SocketServer program and have it
listen to the same port as specified in "socket.properties" (i.e., 1000). Make sure that SocketServer and
SocketAppender both talk to the same port. The host in this example is a machine named oemcomputer
where the ServerSocket program is running.
Start up the server program with the following command:
java com.apress.logging.log4j.SocketServer 1000
Now try the client program by passing it "socket.properties" as the configuration file.
java -Dlog4j.configuration=socket.properties
com.apress.logging.log4j.AdvancvedLogging
We will see the following messages arriving in the server-side console:
THE LOGGER NAME: com.apress.logging.log4j
THE MESSAGE: Deposited 100.5 new balance: 100.5
THE LOGGER NAME: com.apress.logging.log4j
THE MESSAGE: Withdrawn 80.0 new balance: 20.5
THE LOGGER NAME: com.apress.logging.log4j
 **/
package com.ibm.streamsx.log4j.basic.client;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.net.SocketAppender;
//
// run this application passing in a port 2020
//
public class SocketServer implements Runnable {
	private String portNumber = null;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inStream = null;
	private LoggingEvent event = null;

	/** Creates a new instance of SocketServer */
	public SocketServer(String portNumber) {
		this.portNumber = portNumber;
		try {
			// listen to the port specified
			serverSocket = new ServerSocket(Integer.parseInt(portNumber));
			socket = serverSocket.accept();
			// creating a ObjectInputStream from the socket input stream
			inStream = new ObjectInputStream(new BufferedInputStream(
					socket.getInputStream()));
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public void run() {
		try {
			while (true) {
				// cast back to the LoggingEvent object
				event = (LoggingEvent) inStream.readObject();
				// print the message and logger name in this logging event
				System.out.println("THE LOGGER NAME: " + event.getLoggerName());
				System.out.println("THE MESSAGE: "
						+ event.getMessage().toString());
				System.out.println("THE EVENT: "
						+ event.toString());
				
			}
		} catch (Exception e) {
			System.out.println("Error: here" + e.toString());
		}
	}

	public static void main(String args[]) {
		String port = args[0];
		new SocketServer(port);
	}
}
