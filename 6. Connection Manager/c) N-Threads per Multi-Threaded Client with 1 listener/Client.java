import java.net.Socket;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Thread[] threads = new Thread[5];

        Socket socket;
        Demultiplexer demultiplexer;

        socket = new Socket("localhost", 12345);
        demultiplexer = new Demultiplexer( new TaggedConnection( socket ) );
        
        demultiplexer.start();

        //...requests...
        threads[0] = new Thread ( new Client_Runner_0 ( demultiplexer ) );
        threads[1] = new Thread ( new Client_Runner_1 ( demultiplexer ) );
        threads[2] = new Thread ( new Client_Runner_2 ( demultiplexer ) );
        threads[3] = new Thread ( new Client_Runner_3 ( demultiplexer ) );
        threads[4] = new Thread ( new Client_Runner_4 ( demultiplexer ) );

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();

        demultiplexer.close();
    }
}
