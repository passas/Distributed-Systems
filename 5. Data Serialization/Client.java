import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Client {

    public static Contact parseLine (String userInput)
    {
        String[] tokens = userInput.split(" ");

        if (tokens[3].equals("null")) tokens[3] = null;

        return new Contact(
                tokens[0],
                Integer.parseInt(tokens[1]),
                Long.parseLong(tokens[2]),
                tokens[3],
                new ArrayList<>(Arrays.asList(tokens).subList(4, tokens.length)));
    }


    public static void main (String[] args) throws IOException
    {
        //Server Communication
        Socket socket;
        //Client Input
        DataInputStream input;
        //Client Output
        DataOutputStream output;
        //Service
        Contact contact;

        //Command Line Interface
        InputStreamReader stdin;
        BufferedReader in;
        String line;

        //Set-up CLI
        stdin = new InputStreamReader( System.in );
        in = new BufferedReader ( stdin );

        try
        {
            //Set-up conn.
            socket = new Socket("localhost", 12345);
            //Set-up client input
            input = new DataInputStream ( socket.getInputStream() );
            //Set-up client output
            output = new DataOutputStream ( socket.getOutputStream() );

            while ( (line = in.readLine()) != null )
            { //client CLI writting
                contact = null;
                contact = Client.parseLine( line );
                if ( contact != null )  
                {   //Server Sending
                    System.out.println("Contact: " + contact.toString());
                    System.out.print  ("Send to server? [Y/n] ");
                    line = in.readLine();
                    if ( line.charAt(0) == 'Y' || line.charAt(0) == 'y')
                    { //contack OK
                        contact.serialize ( output );

                        //Server Confirmation
                        contact = null;
                        contact = Contact.deserialize ( input );
                        if ( contact != null )
                        {
                            System.out.println("[Server] Contact received: " + contact.toString());
                        }
                        else
                        {
                            System.out.println("[Server] Contact received: null");
                        }
                    }                    
                }
            }
            output.close();
            input.close();
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
