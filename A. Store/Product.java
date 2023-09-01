import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Product
{
	private int id;
	private String name;
	private int quantity;

	private Condition waiting_for_stock;

	public Product (String name, ReentrantLock l)
	{
		this.id = 0;

		this.name = name;
		this.quantity = 0;

		this.waiting_for_stock = l.newCondition ();
	}

	public Product (String name, int quantity, ReentrantLock l)
	{
		this.id = 0;

		this.name = name;
		this.quantity = quantity;

		this.waiting_for_stock = l.newCondition ();
	}

	public String getName ()
	{
		return this.name;
	}

	public void incrementQuantity (int x)
	{
		this.quantity += x;
	}

	public boolean decrementQuantity (int x)
	{
		boolean success;

		success = true;
		if ( this.quantity >= x )
			this.quantity -= x;
		else
			success = false;
	
		return success;
	}

	public void await () throws InterruptedException
	{
		this.waiting_for_stock.await();
	}

	public void signalAll ()
	{
		this.waiting_for_stock.signalAll();
	}
}
