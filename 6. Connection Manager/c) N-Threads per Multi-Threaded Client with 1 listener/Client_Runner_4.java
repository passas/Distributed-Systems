public class Client_Runner_4 implements Runnable
{
    Demultiplexer demultiplexer;

    public Client_Runner_4 (Demultiplexer demultiplexer)
    {
        this.demultiplexer = demultiplexer;
    }

    public void run () 
    {
        byte[] data;

        try 
        {
            this.demultiplexer.send ( 4, "123".getBytes() );
            
            while ( (data = this.demultiplexer.receive ( 4 )) != null )
            {
                if (data.length == 0)
                    break;
                else
                    System.out.println( "(4) From stream: " + new String (data) );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}
