##README --  IBMStreams/streamsx.log4j

The IBMStreams/social toolkit project is an open source IBM InfoSphere Streams 
toolkit project supporting log4j appender.

The log4j logging faciliy has can be configured to append log messages to  network 
socket which will transmit them to a server. Messages arriveing at the server are processed by the 
server's appender. InfoSphere Streams can act as the server's appender in order so that all the messages
can injected into Streams. Once the messages are in Streams they can be processed filter/analyzied/alerted.

Benifits processing log messages in this manner :
 - Reduces the resource impact, log files do not need to be written to file.
 - Processing log analysis is more timlely, elimiated step(s) copying log files to log analysis server.
 - Reduction in complexity. 

Below is a overview graphic.. ![overview graphic][log4jOverview.png]

Project Overview at: http://ibmstreams.github.io/streamsx.log4j/

Operators, functions & types are described using SPLDOC at




 
