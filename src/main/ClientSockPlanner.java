package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSockPlanner {
	Socket socket;
	PrintWriter pwr;
	BufferedReader bfr;
	public ClientSockPlanner() {
		//TODO FINIR SOCKETS
		try{
		     socket = new Socket("192.168.1.23", 4242);
		     pwr = new PrintWriter(socket.getOutputStream(), 
		                 true);
		     bfr = new BufferedReader(new InputStreamReader(
		                socket.getInputStream()));
		   } catch (UnknownHostException e) {
		     System.out.println("Unknown host: MST");
		     System.exit(1);
		   } catch  (IOException e) {
		     System.out.println("No I/O");
		     System.exit(1);
		   }
	}

}
