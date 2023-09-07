import java.net.Socket;

public class SequentialClient
{
    public static void main(String[] args) throws Exception
    {
        Socket socket;
        TaggedConnection connection;
        TaggedConnection.Frame frame;

        socket = new Socket ( "localhost", 12345) ;
        connection =  new TaggedConnection ( socket );

        // send request
        connection.send(1, "Ola".getBytes());

        // One-way
        connection.send(0, ":-p".getBytes());

        // get reply
        frame = connection.receive();
        assert frame.tag == 1;
        System.out.println("Reply: " + new String(frame.data));

        // Get stream of messages until empty msg
        connection.send(2, "ABCDE".getBytes());
        try
        {
            while ( (frame = connection.receive()) != null )
            {
                assert frame.tag == 2;
                if (frame.data.length == 0)
                    connection.close();
                else
                    System.out.println("From stream: " + new String(frame.data));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }
            catch (Exception e2)
            {
                e2.printStackTrace ();
            }
        }

        try
        {
            connection.close();
        }
        catch (Exception e2)
        {
            e2.printStackTrace ();
        }
    }
}
