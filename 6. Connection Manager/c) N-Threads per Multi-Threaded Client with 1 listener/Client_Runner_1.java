public class Client_Runner_1 implements Runnable
{
	Demultiplexer demultiplexer;

	public Client_Runner_1 (Demultiplexer demultiplexer)
	{
		this.demultiplexer = demultiplexer;
	}

	public void run () 
	{
		byte[] data;

		try
		{
			this.demultiplexer.send ( 1, "Ola".getBytes() );
			
			data = this.demultiplexer.receive ( 1 );
			System.out.println ( "(1) Reply: " + new String (data) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
