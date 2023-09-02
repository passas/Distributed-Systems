import java.net.Socket;
import java.net.ServerSocket;

public class Server
{
	public static void main (String[] args)
	{
		Shared_State shared_state;

		shared_state = new Shared_State ();

		try
		{
			ServerSocket server_socket = new ServerSocket ( 12345 );
	
			while ( true )
			{								
				Socket socket = server_socket.accept ();
			
				Thread thread = new Thread ( new Server_Runner ( shared_state, socket ) );
				thread.start();
			}
		}
		catch (Exception e)
		{
            e.printStackTrace();
        }		
	}
}
