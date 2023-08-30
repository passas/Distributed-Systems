import java.util.concurrent.locks.ReentrantLock;

public class BankAccount
{
	private ReentrantLock l;

	private double balance;

	public BankAccount ( double balance )
	{
		this.l = new ReentrantLock ();

		this. balance = balance;
	}

	public double balance ()
	{
		return this. balance;
	}

	public void deposit ( double value )
	{
		this. balance += value;
	}

	public boolean withdraw ( double value )
	{
		boolean success;

		success = false;
		if ( this. balance >= value )
		{
			this. balance -= value;
			success = true;
		}

		return success;
	}

	public void lock ()
	{
		this. l . lock ();
	}

	public void unlock ()
	{
		this. l . unlock ();
	}
}
