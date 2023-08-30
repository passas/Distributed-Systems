public class Main
{
	private static final int N_THREADS = 10;

	public static void main (String[] args)
	{
		Thread ts[] = new Thread [10];
		Bank my_bank;

		my_bank = new Bank ( new BankAccount (0.0) );

		for (int i=0; i<10; i++)
			ts[i] = new Thread ( new Bank_Deposit (my_bank) );

		for (int i=0; i<10; i++)
			ts[i].start();

		for (int i=0; i<10; i++)
			try
			{
				ts[i].join();
			} 
			catch (Exception e)
			{}

		System.out.println ("Current ammount: "+my_bank.getBalance());
	}
}
