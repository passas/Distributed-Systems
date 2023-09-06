import java.net.ServerSocket;
import java.net.Socket;

//import static g8.TaggedConnection.Frame;

public class Server
{

    private final static int WORKERS_PER_CONNECTION = 3;

    public static void main(String[] args) throws Exception
    {
        ServerSocket server_socket;

        Socket socket;
        FramedConnection frame;
        Runnable work;

        server_socket = new ServerSocket(12345);

        while(true)
        {
            socket = server_socket.accept();
            frame = new FramedConnection( socket );
            for (int i = 0; i < WORKERS_PER_CONNECTION; ++i)
            {
                work = new Server_Runnable ( frame );
                new Thread( work ).start();
            }
        }
    }
}

