package Socket;
import java.io.*;
import java.net.*;


public class ClientSockPlanner {
	static final int port = 4242;
	static final String addIp = "192.168.1.17";
	Socket socket;
	PrintWriter pred;
	BufferedReader plec;

	public ClientSockPlanner() throws Exception {
		// TODO Auto-generated constructor stub
		socket = new Socket(addIp, port);
		System.out.println("SOCKET = " + socket);

		plec = new BufferedReader(
	                               new InputStreamReader(socket.getInputStream())
	                               );

		pred = new PrintWriter(
	                             new BufferedWriter(
	                                new OutputStreamWriter(socket.getOutputStream())),
	                             true);

	}
	
	public void close() throws Exception {
		plec.close();
        pred.close();
        socket.close();
	}
	
}
