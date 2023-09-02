import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server_Runner implements Runnable
{
	Socket socket;
	Service service;

	public Server_Runner (Socket s)
	{
		this.service = new Service ();

		this.socket = s;
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
	          			this.service.sum ( number );
	          			output.println( "sum = "+ this.service.getSum() );
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
			output.println( "mean = "+ this.service.mean() );
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
