public class Server_Runnable implements Runnable
{
	private FramedConnection frame;

	public Server_Runnable (FramedConnection frame)
	{
		this.frame = frame;
	}

	public void run ()
	{
		byte[] buf;
        String answear;

    	while (true)
    	{
	    	try
        	{
	    	    buf = frame.receive();

	    	    answear = new String( buf );
	    	    System.out.println("Replying to: " + answear);
								
				frame.send ( answear.getBytes() );
    		}
    		catch (Exception e)
            {
            	try
            	{
            		e.printStackTrace ();
            		frame.close();
            		break;
            	}
            	catch (Exception e2)
            	{
            		e2.printStackTrace();
            	}
            }
    	}
    }
}
