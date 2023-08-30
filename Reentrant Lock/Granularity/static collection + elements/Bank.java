import java.util.concurrent.locks.ReentrantLock;

public class Bank
{
	private ReentrantLock lock;

	private int max_accounts;
	private BankAccount[] accounts;

	Bank (int max)
	{   //constructor
		this.lock = new ReentrantLock ();

		this.max_accounts = max;
		this.accounts = new BankAccount[max];

		//event simulator
		for (int i=0; i<max; i++)
			this.accounts[i] = new BankAccount (0.0);
	}

	public double getBalance (int id)
	{
		BankAccount b;

		b = this.accounts[id];

		b.lock();
		try
		{	
			return b.getBalance();
		}
		finally
		{
			b.unlock();
		}
		
	}

	public boolean deposit (int id, double value)
	{
		BankAccount b;

		b = this.accounts[id];
		
		b.lock();
		try
		{	
			return b.deposit( value );
		}
		finally
		{
			b.unlock();
		}

	}

	public boolean withdraw (int id, double value)
	{
		boolean success;
		BankAccount b;

		success = false;

		b = this.accounts[id];
		
		b.lock();
		try
		{	
			if (b.getBalance() >= value)
			{	
				b.withdraw (value);
	
				success = true;
			}
	
			return success;
		}
		finally
		{
			b.unlock();
		}
	}

	public boolean transfer (int from, int to, double value)
	{
		boolean success;
		BankAccount f, t;

		success = false;

		f = this.accounts[from];
		t = this.accounts[to];

		// Lock Acquisition: order criteria.
		if (from < to)
		{
			f.lock();
			t.lock();
		}
		else
		{
			t.lock ();
			f.lock();
		}
		// job
		try 
		{
			if (f.getBalance() >= value)
			{
				f.withdraw ( value );
				t.deposit ( value );

				success = true;
			}
			return success;
		}
		finally
		{
			t.unlock();
			f.unlock();
		}

	}
	
	public double totalBalance()
	{
		double total;

		total = 0.0;

		for (int i=0; i<this.max_accounts; i++)
			this.accounts[i].lock();
		try
		{	
			for (int i=0; i<this.max_accounts; i++)
				total += this.accounts[i].getBalance();
	
			return total;
		}
		finally
		{
			for (int i=0; i<this.max_accounts; i++)
				this.accounts[i].unlock();
		}
	}
}
