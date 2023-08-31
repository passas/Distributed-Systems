public class Main
{
	private static final int N_THREADS = 9;	  //9 //3
	private static final int MAX_THREADS = 3; //3 //3
	private static final int REPS = 1;		  //1 //3

	public static void main (String[] args)
	{
		//Shared Object
		Agreement consensus;

		consensus = new Agreement ( MAX_THREADS );

		//Object Sharing
		for (int r=0; r<REPS; r++)
		{
			Thread[] ts;
	
			ts = new Thread[ N_THREADS ];
	
			for (int i=0; i<N_THREADS; i++)
				ts[i] = new Thread ( new Agreement_Run ( consensus ) );
	
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
