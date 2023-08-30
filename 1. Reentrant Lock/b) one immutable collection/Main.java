public class Main
{
	private static final int N_THREADS = 10;
	private static final int N_ACCOUNTS = 10;

	public static void main (String[] args)
	{
		Thread ts[] = new Thread [ N_THREADS ];
		Bank my_bank;

		my_bank = new Bank ( N_ACCOUNTS );
		//scenario simulation
		for (int i=0; i<N_ACCOUNTS; i++)
			my_bank.deposit (i, 1000.0);

		for (int i=0; i<N_THREADS; i++)
			ts[i] = new Thread ( new Bank_Transfer (my_bank, N_ACCOUNTS) );

		for (int i=0; i<N_THREADS; i++)
			ts[i].start();

		for (int i=0; i<N_THREADS; i++)
			try
			{
				ts[i].join();
			} 
			catch (Exception e)
			{}

		System.out.println ("Bank Total: "+my_bank.totalBalance());
	}
}
