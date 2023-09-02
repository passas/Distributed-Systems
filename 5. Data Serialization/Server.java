import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import static java.util.Arrays.asList;

public class Server {

    public static void main (String[] args) throws IOException
    {
        //Service
        ContactManager contact_manager;

        //Server
        ServerSocket server_socket;

        //Client
        Socket socket;
        Server_Runnable runnable;
        Thread worker;

        //Service
        contact_manager = new ContactManager ();
       
        //Connector
        server_socket = new ServerSocket ( 12345 );
        while (true)
        {
            socket = server_socket.accept();
            runnable = new Server_Runnable ( socket, contact_manager );
            worker = new Thread( runnable );
            worker.start();
        }
    }

}
