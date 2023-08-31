import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
/**
 * This is a Barrier Oject that can only be used once. 
 * 
 **/
public class Barrier {

	private ReentrantLock l;
	private Condition max_n_condition;

	private int ticket;
	private int n;

	public Barrier (int n)
	{
		this. l = new ReentrantLock ();
		this. max_n_condition = this.l.newCondition ();

		this. ticket = 0;
		this. n = n;
	}

	public void await() throws InterruptedException
	{
		this.l.lock();
		try
		{
			this.ticket ++;
			if ( this.ticket == this.n )
				max_n_condition.signalAll();

			while ( this.ticket < this.n )
				max_n_condition.await();
		}
		finally
		{
			this.l.unlock();
		}

	}
}
