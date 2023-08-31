public class Barrier_Run implements Runnable
{
	private Barrier barrier;

	public Barrier_Run (Barrier b)
	{
		this. barrier = b;
	}

	public void run ()
	{
		System.out.println ( "#"+Thread.currentThread().getId() + ": IN" );

		try
		{
			this.barrier.await ();
		}
		catch (InterruptedException e)
		{}

		System.out.println ( "#"+Thread.currentThread().getId() + ": OUT" );
	}
}
