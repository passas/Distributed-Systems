public class Client_Runner_0 implements Runnable
{
    Demultiplexer demultiplexer;

    public Client_Runner_0 (Demultiplexer demultiplexer)
    {
        this.demultiplexer = demultiplexer;
    }

    public void run () 
    {
        try
        {
            this.demultiplexer.send ( 0, ":-b".getBytes() );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
