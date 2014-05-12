package com.ibm.streamsx.log4j.basic.client;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class StreamsAppender extends AppenderSkeleton {
	static String configMessage;
	
	//public StreamsAppender(String msg) {
	//	configMessage = msg;
	//}
	@Override
	public void close() {
		System.out.println("closing down logging");
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return true;
	}
	String msg;
	public void setMessage(String msg) {
		configMessage = msg;
	}

	@Override
	protected void append(LoggingEvent arg0) {
		
		System.out.println("appender name:" + name);
		System.out.println("Extra Message:" + configMessage);
		System.out.println("From Streams Appender" + this.layout.format(arg0));
	}

}
// I want to get this String into the StreamsAppender
// so I set the storage in StreamsAppender but set the value
// in the function that extends it. Putting the value 
// on the constructor of StreamsAppender() did not work. 
// Thier is a better way - work it out now that we have this.
class StreamsAppenderTrick extends StreamsAppender{
	public StreamsAppenderTrick(String msg) {
		configMessage = msg;
	}
}
