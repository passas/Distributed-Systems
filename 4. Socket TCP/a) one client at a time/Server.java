import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
	public static void main (String[] args)
	{
		try
		{
			ServerSocket server_socket = new ServerSocket ( 12345 );
	
			while ( true )
			{
				Socket socket = server_socket.accept ();
	
				BufferedReader input = new BufferedReader ( new InputStreamReader ( socket.getInputStream () ) );
				PrintWriter output = new PrintWriter ( socket.getOutputStream() );
				
				Service service = new Service ();
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
            			service.sum ( number );
            			output.println( "sum = "+service.getSum() );
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
				output.println( "mean = "+service.mean() );
                output.flush();

				socket.shutdownInput ();
				socket.shutdownOutput ();
				socket.close ();
			}
		}
		catch (IOException e)
		{
            e.printStackTrace();
        }		
	}
}
