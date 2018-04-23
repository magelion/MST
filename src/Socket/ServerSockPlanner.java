package Socket;

import java.io.*;
import java.net.*;

public class ServerSockPlanner {

	static final int port = 4242;
	
	ServerSocket s;
	BufferedReader plec;
	PrintWriter pred;
	Socket soc;
	
	public ServerSockPlanner() throws Exception {
		s = new ServerSocket(port);
        soc = s.accept();


        plec = new BufferedReader(
                               new InputStreamReader(soc.getInputStream())
                              );


        pred = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(soc.getOutputStream())), 
                             true);
		
	}
	
	public String receptionstring () throws Exception {
		String str = plec.readLine();
		return str;
	}
	
	public void close() throws Exception {
        plec.close();
        pred.close();
        soc.close();
	}
}
