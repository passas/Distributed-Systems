import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server_Runner implements Runnable
{
	Shared_State shared_state; //shared-object
	Socket socket;
	
	My_State my_state;

	public Server_Runner (Shared_State shared_state, Socket s)
	{
		this.shared_state = shared_state;
		this.socket = s;

		this.my_state = new My_State ();
	}

	public void run ()
	{
		try
		{
			BufferedReader input = new BufferedReader ( new InputStreamReader ( this.socket.getInputStream () ) );
			PrintWriter output = new PrintWriter ( this.socket.getOutputStream() );
			/*
			 * Welcome Disclaimer 
			 */
			output.println("Weclome To our Server. Please input only integer forms.");
	        output.flush();
	     
	        //Service
			String line;
			while ( (line = input.readLine()) != null )
			{ //while client doesnt shutdown() his Output <=> EoF
	      			try
	      			{ //Server input valdation & transform.:
	          			int number = Integer.parseInt( line );
	          			this.my_state.sum ( number );
	          			this.shared_state.sum ( number );
	          			output.println( "you're sum = "+ this.my_state.my_sum() );
	              		output.flush();
	              	}
	      			catch (NumberFormatException ex)
	      			{
	          			output.println("Invalid Input");
	              		output.flush();
	      			}
			}
			/*
			 * Good-Bye message.
			 */
			output.println( "server mean = "+ this.shared_state.mean() );
	        output.flush();
			
			socket.shutdownInput ();
			socket.shutdownOutput ();
			socket.close ();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
