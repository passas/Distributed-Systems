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

    	try
      {
	    	while ( (buf = frame.receive()) != null )
	    	{
	    	    answear = new String( buf );
	    	    System.out.println("[Client]: " + answear);
								
				    frame.send ( answear.getBytes() );
    		}
    	}
    	catch (Exception e)
      {
        try
        { //client offline
          e.printStackTrace ();
          frame.close();
        }
        catch (Exception e2)
        { //_server_ already closed
          e2.printStackTrace();
        }
      }
  }
  
}
