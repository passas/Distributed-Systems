public class Client_Runner_3 implements Runnable
{
    Demultiplexer demultiplexer;

    public Client_Runner_3 (Demultiplexer demultiplexer)
    {
        this.demultiplexer = demultiplexer;
    }

    public void run () 
    {
        byte[] data;

        try 
        {
            this.demultiplexer.send ( 3, "Hello".getBytes() );
            
            data = this.demultiplexer.receive ( 3 );

            System.out.println( "(3) Reply: " + new String (data) );
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}
