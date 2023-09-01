import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import java.util.Map;
import java.util.HashMap;

import java.util.Set;

public class Store
{
	private ReentrantLock lock_products;	//to ensure fairness of operations
	private Map<String, Product> products;
	
	private Map<String, Condition> waiting_for_stock;

	public Store ()
	{
		this.lock_products = new ReentrantLock ();
		this.products = new HashMap <> ();

		this.waiting_for_stock = new HashMap <> ();
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
				aux = new Product ( item, quantity );
				this.products.put ( item, aux );

				this.waiting_for_stock.put ( item, this.lock_products.newCondition() );
			}
			this.waiting_for_stock.get ( item ).signalAll ();
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
					System.out.println ( Thread.currentThread().getName()+" #"+" consuming "+id);
					while ( aux.decrementQuantity ( 1 ) == false )
					{
						System.out.println ( Thread.currentThread().getName()+" #"+" waiting for "+id);
						this.waiting_for_stock.get ( id ).await ();
					}
					System.out.println ( Thread.currentThread().getName()+" #"+" consumed "+id);
				}
				else
				{
					System.out.println ( Thread.currentThread().getName()+" #"+" NULL "+id);
				}
			}
		}
		finally
		{
			this.lock_products.unlock();
		}
	}
}
