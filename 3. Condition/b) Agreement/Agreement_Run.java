import java.util.Random;

public class Agreement_Run implements Runnable
{
	private Agreement shared_object;

	public Agreement_Run (Agreement obj)
	{
		this.shared_object = obj;
	}

	public void run ()
	{
		Random rand;
		int my_wave_consensus;

		rand = new Random ();

		try
		{
			my_wave_consensus = this.shared_object.propose ( rand.nextInt() );

			System.out.println ("My wave consensus: " + my_wave_consensus);
		}
		catch (InterruptedException e)
		{}

	}
}
