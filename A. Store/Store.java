import java.util.concurrent.locks.ReentrantLock;

import java.util.Map;
import java.util.HashMap;

import java.util.Set;

public class Store
{
	private ReentrantLock lock_products;	//to ensure fairness of operations
	private Map<String, Product> products;

	public Store ()
	{
		this.lock_products = new ReentrantLock ();
		this.products = new HashMap <> ();
	}

	public void supply (String item, int quantity)
	{
		Product aux;

		this.lock_products.lock ();
		try
		{
			System.out.println ( Thread.currentThread().getName()+" #"+" supply "+item);
			aux = this.products.get ( item );
			if ( aux != null )
			{
				aux.incrementQuantity ( quantity );
			}
			else
			{
				aux = new Product ( item, quantity, lock_products );
				this.products.put ( item, aux );
			}
			aux.signalAll ();
		}
		finally
		{
			this.lock_products.unlock();
		}
	}

	public void consume (String[] items) throws InterruptedException
	{
		Product aux;

		this.lock_products.lock();
		try
		{
			for (String id : items)
			{
				aux = this.products.get ( id );
				if ( aux != null )
				{
					//System.out.println ( Thread.currentThread().getName()+" #"+" consuming "+id);
					while ( aux.decrementQuantity ( 1 ) == false )
					{
						//System.out.println ( Thread.currentThread().getName()+" #"+" waiting for "+id);
						aux.await();
					}
					//System.out.println ( Thread.currentThread().getName()+" #"+" consumed "+id);
				}
				else
				{
					//System.out.println ( Thread.currentThread().getName()+" #"+" NULL "+id);
				}
			}
		}
		finally
		{
			this.lock_products.unlock();
		}
	}
}
