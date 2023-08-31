public class Main
{
	private static final int N_THREADS = 10;
	private static final int MAX_THREADS = 10;

	public static void main (String[] args)
	{
		//Shared Object
		Barrier barrier;

		barrier = new Barrier ( MAX_THREADS );

		//Object Sharing
		Thread[] ts;

		ts = new Thread[ N_THREADS ];

		for (int i=0; i<N_THREADS; i++)
			ts[i] = new Thread ( new Barrier_Run ( barrier ) );

		for (int i=0; i<N_THREADS; i++)
			ts[i].start();

		for (int i=0; i<N_THREADS; i++)
			try
			{
				ts[i].join();
			}
			catch (InterruptedException e)
			{}
	}
}
