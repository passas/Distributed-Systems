import java.util.Random;

public class Store_Runner implements Runnable
{
	private Store shared_object;

	public Store_Runner (Store obj)
	{
		this.shared_object = obj;
	}

	public void run ()
	{
		String[] items = {"Anel", "Bola", "Chupeta"};
		Random generator;
		int operation, item;

		generator = new Random ();

		operation = generator.nextInt ( 2 ); //{supply, consume}

		if ( operation == 0 )
		{
			item = generator.nextInt ( 3 );
			this.shared_object.supply ( items[item], 1 );
		}
		else if ( operation == 1 )
		{
			try
			{
				this.shared_object.consume (items);
			}
			catch (InterruptedException e)
			{}
		}

	}
}
