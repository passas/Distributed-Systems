import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        FramedConnection frame = new FramedConnection( socket );

        // send requests
        frame.send("Ola".getBytes());
        frame.send("Hola".getBytes());
        frame.send("Hello".getBytes());

        // get replies
        byte[] b1 = frame.receive();
        byte[] b2 = frame.receive();
        byte[] b3 = frame.receive();

        System.out.println("Some Reply: " + new String(b1));
        System.out.println("Some Reply: " + new String(b2));
        System.out.println("Some Reply: " + new String(b3));

        frame.close();
    }
}
