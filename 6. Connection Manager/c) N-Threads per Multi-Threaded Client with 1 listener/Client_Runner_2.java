public class Client_Runner_2 implements Runnable
{
	Demultiplexer demultiplexer;

	public Client_Runner_2 (Demultiplexer demultiplexer)
	{
		this.demultiplexer = demultiplexer;
	}

	public void run () 
	{
		byte[] data;

		try 
		{
			this.demultiplexer.send ( 2, "ABCDE".getBytes() );
			
			while ( (data = this.demultiplexer.receive ( 2 )) != null )
			{
				if (data.length == 0)
					break;
				else
					System.out.println( "(2) From stream: " + new String (data) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}
}
