public class Server_Runner implements Runnable
{
	private TaggedConnection connection;

	public Server_Runner (TaggedConnection connection)
	{
		this.connection = connection;
	}

	public void run ()
	{
		TaggedConnection.Frame frame;
		int tag; String text;

		try
		{
			while ( (frame = this.connection.receive()) != null )
			{
				tag = frame.tag;
				text = new String ( frame.data );

				if ( tag == 0 )
				{
					System.out.println("Got one-way: " + text);
				}
				else if ( tag % 2 == 1 )
				{
					System.out.println("Replying to: " + text);
                    this.connection.send(tag, text.toUpperCase().getBytes());
				}
				else if ( tag % 2 == 0 )
				{
					for (int i = 0; i < text.length(); ++i)
					{
                    	String str = text.substring(i, i+1);
                        System.out.println("Streaming: " + str);
                        this.connection.send(tag, str.getBytes());
                    }
                   	this.connection.send(tag, new byte[0]); //send byte STREAM terminator
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				connection.close ();
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
