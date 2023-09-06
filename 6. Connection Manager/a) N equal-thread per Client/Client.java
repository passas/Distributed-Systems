import java.net.Socket;

public class SimpleClient
{
    public static void main(String[] args) throws Exception
    {
        Socket socket;
        FramedConnection frame;

        byte[] b1, b2, b3;

        socket = new Socket("localhost", 12345);
        frame = new FramedConnection( socket );

        // send requests
        frame.send("Ola".getBytes());
        frame.send("Hola".getBytes());
        frame.send("Hello".getBytes());

        // get replies
        b1 = frame.receive();
        b2 = frame.receive();
        b3 = frame.receive();

        System.out.println("[Server]: " + new String(b1));
        System.out.println("[Server]: " + new String(b2));
        System.out.println("[Server]: " + new String(b3));

        frame.close();
    }
}
