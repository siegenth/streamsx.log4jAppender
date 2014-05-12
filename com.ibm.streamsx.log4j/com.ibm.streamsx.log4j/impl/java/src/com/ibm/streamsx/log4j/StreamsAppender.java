package com.ibm.streamsx.log4j;


import java.net.Socket;
import java.util.Set;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;


public class StreamsAppender extends AppenderSkeleton {
	static ReceiveLog4j receiveLog4j;
	Socket socket;
	public StreamsAppender() {
		super();
	}
	@Override
	public void close() {
		receiveLog4j.clientDisconnect(socket);
	}
	
	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent arg0) {
		Set<String> set = arg0.getProperties().keySet();
		int idx = 0;
		// TODO check if you're in debug log mode - spare the cycles
		for(String key : set) {
	        Logger.getLogger(this.getClass()).info("key["+ idx++  +" of " + set.size() + "]: " + key);
	        
		}
        Logger.getLogger(this.getClass()).info("Logging event : " + arg0.toString());

        try {
			receiveLog4j.produceTuples(this.layout.format(arg0));
		} catch (Exception e) {
	        Logger.getLogger(this.getClass()).error("Failed to write log message to streams, message:" + e.getMessage()); 
			e.printStackTrace();
		}
	}

}
// TODO - Fix this mess......
//I want to get this String into the StreamsAppender
//so I set the storage in StreamsAppender but set the value
//in the function that extends it. Putting the value 
//on the constructor of StreamsAppender() did not work. 
//Is their is a better way - work it out now that we have this.
class StreamsAppenderInitialize extends StreamsAppender{
//	public StreamsAppenderInitialize(ReceiveLog4j receiveLog4jIn, OperatorContext context) {
	public StreamsAppenderInitialize(ReceiveLog4j receiveLog4jIn) {	
		receiveLog4j = receiveLog4jIn;
		System.out.println("setting receiveLog4j:" + receiveLog4j );
	}
}