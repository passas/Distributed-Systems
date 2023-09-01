public class Main
{
	private static final int N_THREADS = 18;
	private static final int REPS = 1;

	public static void main (String[] args)
	{
		//Shared Object
		Store store;

		store = new Store ( );

		//Object Sharing
		for (int r=0; r<REPS; r++)
		{
			Thread[] ts;
	
			ts = new Thread[ N_THREADS ];
	
			for (int i=0; i<N_THREADS; i++)
				ts[i] = new Thread ( new Store_Runner ( store ) );
	
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
}
