public class Bank_Deposit implements Runnable
{
	private Bank b;

	public Bank_Deposit (Bank b)
	{
		this.b = b;
	}

	public void run ()
	{
		for (int i=0; i<1000; i++)
			this.b.deposit (100.0);
	}
}
