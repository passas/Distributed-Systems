import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank
{
	private ReentrantReadWriteLock lock;

	private Map<Integer, BankAccount> accounts;
	private int next_id;

	public Bank ()
	{
		this. lock = new ReentrantReadWriteLock ();

		this. accounts = new HashMap <> ();
		this.next_id = 0;
	}

	public int createAccount (double balance)
	{
		int id;
		BankAccount ba;

		this.lock.writeLock().lock ();
		try
		{
			id = ++next_id;

			ba = new BankAccount ( balance );

			this.accounts. put ( id, ba );

			return id;
		}
		finally
		{
			this.lock.writeLock().unlock();
		}
	}

	public double closeAccount (int id)
	{
		double balance;
		BankAccount ba;

		ba = null;
		balance = 0.0;

		this.lock.writeLock().lock ();
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
				this.lock.writeLock().unlock ();
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
				this.lock.writeLock().unlock ();
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

		this.lock.readLock().lock ();
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
				this.lock.readLock().unlock ();
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
				this.lock.readLock().unlock ();
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

		this.lock.readLock().lock ();
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
				this.lock.readLock().unlock ();
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
				this.lock.readLock().unlock ();
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

		this.lock.readLock().lock ();
		try //wait: the account can be at removal process
		{
			ba_f = this.accounts. get ( from );
			ba_t = this.accounts. get ( to );
		}
		finally
		{
			if ( ba_f != null && ba_t != null )
			{
				if ( from < to )
				{ //read is multi-threaded
					ba_f.lock ();
					ba_t.lock ();
				}
				else
				{
					ba_t.lock ();
					ba_f.lock ();
				}
				this.lock.readLock().unlock ();

				//balance ok?
				if ( ba_f.balance () >= value )
				{ //ok
					ba_f.withdraw ( value );
					ba_f.unlock ();
					
					ba_t.deposit ( value );
					ba_t.unlock ();

					success = true;
				}
				else
				{ //not ok
					ba_f.unlock ();
					ba_t.unlock ();
				}
			}
			else
			{ //account id not founded
				this.lock.readLock().unlock ();
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
		
		this.lock.readLock().lock ();
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

			for (BankAccount ba : bas)
			{
				total += ba.balance();
				ba.unlock ();
			}

		}
		finally
		{
			this.lock.readLock().unlock();	
		}

		return total;
	}
}
