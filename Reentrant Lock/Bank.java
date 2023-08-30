import java.util.concurrent.locks.ReentrantLock;

public class Bank
{
	private ReentrantLock lock;
	private BankAccount savings;

	Bank (BankAccount savings)
	{
		this.lock = new ReentrantLock ();
		this.savings = savings;
	}

	public double getBalance ()
	{
		lock.lock ();
		try
		{
			return this.savings.getBalance();
		}
		finally
		{
			lock.unlock();
		}
	}

	boolean deposit (double value)
	{
		lock.lock ();
		try
		{
			return this.savings.deposit (value);
		}
		finally
		{
			lock.unlock ();
		}
	}
}
