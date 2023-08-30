import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;

import java.util.concurrent.locks.ReentrantLock;

public class Bank
{
	private ReentrantLock lock;

	private Map<Integer, BankAccount> accounts;
	private int next_id;

	public Bank ()
	{
		this. lock = new ReentrantLock ();

		this. accounts = new HashMap <> ();
		this.next_id = 0;
	}

	public int createAccount (double balance)
	{
		int id;
		BankAccount ba;

		lock.lock ();
		try
		{
			id = ++next_id;

			ba = new BankAccount ( balance );

			this.accounts. put ( id, ba );

			return id;
		}
		finally
		{
			lock.unlock();
		}
	}

	public double closeAccount (int id)
	{
		double balance;
		BankAccount ba;

		ba = null;
		balance = 0.0;

		this.lock.lock ();
		try
		{
			ba = this.accounts. remove ( id );
		}
		finally
		{
			/*
			* A closeAccount() can lock() an account faster then a deposit() or a withdraw()...
			*/
			if ( ba != null )
			{
				ba.lock ();
				this.lock.unlock ();
				try
				{
					balance = ba.balance();
				}
				finally
				{
					ba.unlock ();
				}
			}
			else
			{
				this.lock.unlock ();
			}	
		}

		return balance;	
	}

	public boolean deposit (int id, double amount)
	{
		boolean success;
		BankAccount ba;

		ba = null;
		success = false;

		this.lock.lock ();
		try //wait: the account can be at removal process
		{
			ba = this.accounts. get ( id );
		}
		finally
		{	/*
			* A closeAccount() can lock() an account faster then a deposit()...
			*/
			if ( ba != null )
			{
				ba.lock ();
				this.lock.unlock ();
				try //wait: the account can be at withdrawl process
				{
					ba.deposit ( amount );
					success = true;
				}
				finally
				{
					ba.unlock ();
				}
			}
			else
			{
				this.lock.unlock ();
			}
		}
	
		

		return success;
	}

	public boolean withdraw (int id, double amount)
	{
		boolean success;
		BankAccount ba;

		ba = null;
		success = false;

		this.lock.lock ();
		try //wait: the account can be at removal process
		{
			ba = this.accounts. get ( id );
		}
		finally
		{	/*
			* A closeAccount() can lock() an account faster then a withdraw()...
			*/
			if ( ba != null )
			{
				ba.lock ();
				this.lock.unlock ();
				try //wait: the account can be at a deposit process
				{
					if ( ba.balance() >= amount )
					{	
						ba.withdraw ( amount );
						success = true;
					}
				}
				finally
				{
					ba.unlock ();
				}
			}
			else
			{
				this.lock.unlock ();
			}
		}

		return success;
	}

	public boolean transfer (int from, int to, double value)
	{
		boolean success;
		BankAccount ba_f, ba_t;

		ba_f = ba_t = null;
		success = false;

		this.lock.lock ();
		try //wait: the account can be at removal process
		{
			ba_f = this.accounts. get ( from );
			ba_t = this.accounts. get ( to );
		}
		finally
		{
			if ( ba_f != null && ba_t != null )
			{
				ba_f.lock ();
				ba_t.lock ();
				
				this.lock.unlock ();

				if ( ba_f.balance () >= value )
				{
					ba_f.withdraw ( value );
					ba_f.unlock ();
					
					ba_t.deposit ( value );
					ba_t.unlock ();

					success = true;
				}
				else
				{
					ba_f.unlock ();
					ba_t.unlock ();
				}
			}
			else
			{
				this.lock.unlock ();
			}
		}

		return success;
	}

	public double totalBalance (int[] ids)
	{
		double total;
		BankAccount aux;
		ArrayList<BankAccount> bas;

		total = 0.0;
		bas = new ArrayList <> ();
		
		this.lock.lock ();
		try
		{
			for (int i : ids)
			{
				if ( this.accounts. containsKey ( i ) )
				{
					aux = this.accounts. get ( i );
					aux. lock();

					bas.add ( aux );
				}
			}

			this.lock.unlock ();

			for (BankAccount ba : bas)
			{
				total += ba.balance();
				ba.unlock ();
			}

		}
		finally
		{
			if ( this.lock.isHeldByCurrentThread() == true )
				this.lock.unlock();	
		}

		return total;
	}
}
