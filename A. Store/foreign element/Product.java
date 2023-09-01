public class Product
{
	private int id;
	private String name;
	private int quantity;

	public Product (String name)
	{
		this.id = 0;

		this.name = name;
		this.quantity = 0;
	}

	public Product (String name, int quantity)
	{
		this.id = 0;

		this.name = name;
		this.quantity = quantity;
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
}
