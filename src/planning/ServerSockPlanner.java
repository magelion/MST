package planning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSockPlanner {

	ServerSocket listener;
	PrintWriter out;
	    /**
	     * Runs the server.
	     */
	public ServerSockPlanner() throws IOException {
		listener = new ServerSocket(4242);
		try {
			Socket socket = listener.accept();
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				//out.println();
			} finally {
				socket.close();
			}
			
		}
		finally {
			listener.close();
		}
	}
	public void writestring(String message) {
		out.println(message);
	}
	    
}
