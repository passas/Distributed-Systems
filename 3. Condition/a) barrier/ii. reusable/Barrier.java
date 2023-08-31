import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
/**
 * This is a Barrier Oject that can be used more than once. 
 * 
 **/
public class Barrier {

	private ReentrantLock l;
	private Condition max_n_condition;

	private int phase;

	private int ticket;
	private int n;

	public Barrier (int n)
	{
		this. l = new ReentrantLock ();
		this. max_n_condition = this.l.newCondition ();

		this.phase = 0;

		this. ticket = 0;
		this. n = n;
	}

	public void await() throws InterruptedException
	{
		int phase, ticket;
		
		phase = ticket = 0;

		this.l.lock();
		try
		{
			phase = this.phase;
			ticket = ++this.ticket;
			
			//dbg
			System.out.println ( "~" + phase + " #" +ticket+ ": IN" );
			//

			if ( this.ticket == this.n )
			{
				this.ticket = 0;
				this.phase++;
				max_n_condition.signalAll();
			}

			while ( ticket < this.n && phase >= this.phase )
				max_n_condition.await();

			//dbg
			System.out.println ( "~" + phase + " #" +ticket+ ": OUT" ); //dbg
			//
		}
		finally
		{
			this.l.unlock();
		}

	}
}
