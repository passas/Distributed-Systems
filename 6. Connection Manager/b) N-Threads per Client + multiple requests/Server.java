import java.net.ServerSocket;
import java.net.Socket;

public class ServerWithWorkers
{
    final static int WORKERS_PER_CONNECTION = 3;

    public static void main(String[] args) throws Exception
    {
        ServerSocket server_socket;

        Socket socket;
        TaggedConnection connection;
        Runnable worker;

        server_socket = new ServerSocket ( 12345 );

        while(true)
        {
            socket = server_socket.accept();
            connection = new TaggedConnection( socket );

            for (int i = 0; i < WORKERS_PER_CONNECTION; ++i)
            {
                worker = new Server_Runner ( connection );
                new Thread(worker).start();
            }
        }

    }
}

