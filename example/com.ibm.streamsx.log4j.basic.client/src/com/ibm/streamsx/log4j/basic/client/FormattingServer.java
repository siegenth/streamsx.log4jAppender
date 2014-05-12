package com.ibm.streamsx.log4j.basic.client;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.net.SocketNode;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;

public class FormattingServer extends Thread {

	/**
	 * @param args
	 */

	private static Logger cat = Logger.getLogger(SocketServer.class);
	private final int port;

	public FormattingServer(int port) {
			this.port = port;
	}
	LoggerRepository streamsRepository = null;
	LoggerRepository getRepository() {
		if (streamsRepository == null) {
			File f = new File("/home/streamsadmin/Development/workspace/logToStreams/log4jSocketProperties/streams.config");
			streamsRepository = new Hierarchy(new RootLogger((Level)Priority.DEBUG));
			new PropertyConfigurator().doConfigure(f.getAbsolutePath(), streamsRepository);
			new StreamsAppenderTrick("in the beginging");
		}
		return streamsRepository;
	}
	public final void run() {

			try {
				cat.info("listening on port " + port);
				System.out.println("test");
				ServerSocket serverSocket = new ServerSocket(port);
					while(true) {
							cat.info("waiting");
							Socket socket  = serverSocket.accept();
							InetAddress inetAddress = socket.getInetAddress();
							cat.info("Connected " + inetAddress);
							//LoggerRepository h = LogManager.getLoggerRepository();
							LoggerRepository h = getRepository();
/*							Enumeration<Logger> log = h.getCurrentLoggers();
							while(log.hasMoreElements()) {
								System.out.println("logger:" + log.nextElement().getName());
							}
							Logger logger = h.getLogger("root");
							System.out.println("root logger:" + logger);
							Enumeration<StreamsAppender>app =logger.getAllAppenders();
							while(app.hasMoreElements()) {
								System.out.print("appender:" + app.nextElement().getName()); 
							}
							StreamsAppender strmAppender = (StreamsAppender)logger.getAppender("Xstream");
							//System.out.println("strmAppender:" + strmAppender);							
							//strmAppender.setMessage("message from inside");

							System.out.println("root logger - " + h.getRootLogger().getName());
*/
							cat.info("starting");
							new Thread(new SocketNode(socket,h)).start();
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		FormattingServer fs = new FormattingServer(2020);
		fs.start();
	}

}
