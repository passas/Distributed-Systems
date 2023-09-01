import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import java.util.Map;
import java.util.HashMap;

public class Agreement
{
	private ReentrantLock lock;
	private Condition all_threads_are_here;

	private int max;
	private int wave;
	private int ticket;

	private int consensus;

	private Map<Integer, Integer> wave_consensus;

	public Agreement (int max)
	{
		this.lock = new ReentrantLock ();
		this.all_threads_are_here = this.lock.newCondition();

		this.max = max;
		this.wave = 0;
		this.ticket = 0;

		this.consensus = 0;

		this.wave_consensus = new HashMap <> ();
	}

	public int propose (int choice) throws InterruptedException
	{
		int my_ticket, my_wave;

		my_ticket = my_wave = 0;

		this.lock.lock ();
		try
		{	
			my_ticket = ++this.ticket;
			my_wave = this.wave;

			//verbose
			System.out.println ("~" + my_wave + " #" + my_ticket + " propose: " + choice);

			if ( my_ticket == 1 )
				this.consensus = choice;
			else if ( choice > this.consensus )
				this.consensus = choice;

			if ( my_ticket == this.max )
			{
				this.wave_consensus.put ( my_wave, this.consensus );

				this.ticket = 0;
				this.wave++;

				this.all_threads_are_here.signalAll();
			}

			while ( my_ticket < this.max && my_wave >= this.wave )
				this.all_threads_are_here.await();

			//verbose
			//System.out.println ("~" + my_wave + " consensus: " + this.wave_consensus.get ( my_wave ));
			
			return this.wave_consensus.get ( my_wave );
		}
		finally
		{
			this.lock.unlock ();
		}
	}
}
