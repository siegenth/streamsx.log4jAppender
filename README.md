##README --  IBMStreams/streamsx.log4j

The IBMStreams/log4jAppender toolkit project is an open source IBM InfoSphere Streams 
toolkit project supporting log4j appender.

The log4j logging facility can be configured to append log messages to  network 
socket which will transmit them to a server. Messages arriving at the server are processed by the 
server's appender. InfoSphere Streams can act as the server's appender in order so that all the messages
can injected into Streams. Once the messages are in Streams they can be processed filter/analyzied/alerted.

Benifits processing log messages in this manner :
 - Reduces the resource impact, log files do not need to be written to file.
 - Processing log analysis is more timlely, elimiates step(s) copying files to an central server.
 - Reduction in complexity, fewer components (files) to move about and track.   

Below is a overview graphic showing two programs configured to send logging messages to a Streams instance, 
in this example all the messages will be collected one file. 

![overview graphic](log4jOverview.png)

Project Overview at: http://ibmstreams.github.io/streamsx.log4j/

Operators, functions & types are described using SPLDOC at




 
