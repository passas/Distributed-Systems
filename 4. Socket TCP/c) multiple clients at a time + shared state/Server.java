import java.net.Socket;
import java.net.ServerSocket;

public class Server
{
	public static void main (String[] args)
	{
		//Server:
		ServerSocket server_socket;
		Shared_State shared_state;
		
		//Attendence:
		Socket socket;
		Server_Runner runner;
		Thread thread;

		//Start shared object
		shared_state = new Shared_State ();

		try
		{ //Start server: wait for connections
			server_socket = new ServerSocket ( 12345 );
	        
			while ( true )
			{								
				socket = server_socket.accept (); // *waiting connection* -> establishing communication channel

				runner = new Server_Runner ( shared_state, socket ); //create -thread- runner: send it the communication channel for the client & the server shared state obj.
				thread = new Thread ( runner ); //create thread: send it the runner
				thread.start(); //start thread: runner
			}
		}
		catch (Exception e)
		{
            		e.printStackTrace();
        	}		
	}
}
