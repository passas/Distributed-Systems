import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{

    public static void main(String[] args)
    {
        //server comunication
        Socket socket;
        //server comunication input
        InputStreamReader byte_to_char_socket_input;
        BufferedReader char_buf_socket_input;
        String line_socket_input;
        //server comunication output
        PrintWriter object_to_text_socket_output;

        //client-stdin input
        InputStreamReader byte_to_char_stdin;
        BufferedReader char_buf_stdin;
        String line_stdin;

        try
        {
            //establish server connection
            socket = new Socket("localhost", 12345);

            //set server communication input
            byte_to_char_socket_input = new InputStreamReader ( socket.getInputStream() );
            char_buf_socket_input = new BufferedReader ( byte_to_char_socket_input ); 
            
            //set server communication output
            object_to_text = new PrintWriter( socket.getOutputStream() );

            //set local-stdin communication input
            byte_to_char_stdin = new InputStreamReader ( System.in );
            char_buf_stdin = new BufferedReader( byte_to_char_stdin );

            /*
             * Server Welcome Disclaimer
             */
			System.out.println( input.readLine() );
            
           	//Server Service
            while ( (line_stdin = char_buf_stdin.readLine()) != null ) //read stdin
            {
                object_to_text_socket_output.println( line_stdin ); //put in socket output
                object_to_text_socket_output.flush();               //flush it to server

                line_socket_input = input.readLine();               //read from socket input
                System.out.println("Server answear: " + line_socket_input); //put in stdout
            }
            socket.shutdownOutput(); //send EoF <=> close socket output
            
            /*
             * Server Good-Bye.
             */
            line_socket_input = input.readLine(); //read from socket input
            System.out.println("Server answear: " + line_socket_input); //put in stdout

            socket.shutdownInput(); //close socket input
           	socket.close(); //close connection
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
