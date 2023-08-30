import java.util.Random;

public class Bank_Transfer implements Runnable
{
	private Bank b;
	private int max_accounts;

	public Bank_Transfer (Bank b, int max)
	{
		this.b = b;
		this.max_accounts = max;
	}

	public void run ()
	{
		int from, to;
		Random rand = new Random();
		
		for (int i=0; i<1000; i++)
		{
			from = rand.nextInt( this.max_accounts );
			while ( (to = rand.nextInt ( this.max_accounts )) == from );

			this.b.transfer (from, to, 100.0);
		}

	}
}
