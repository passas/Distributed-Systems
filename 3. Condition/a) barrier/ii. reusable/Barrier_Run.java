public class Barrier_Run implements Runnable
{
	private Barrier barrier;

	public Barrier_Run (Barrier b)
	{
		this. barrier = b;
	}

	public void run ()
	{
		try
		{
			this.barrier.await ();
		}
		catch (InterruptedException e)
		{}
	}
}
