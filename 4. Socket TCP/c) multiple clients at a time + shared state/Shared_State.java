import java.util.concurrent.locks.ReentrantLock;

public class Shared_State
{
	private ReentrantLock lock;
	private int total_numbers_summed;
	private int sum;

	public Shared_State ()
	{
		this.lock = new ReentrantLock ();
		this.total_numbers_summed = 0;
		this.sum = 0;
	}

	public float mean ()
	{
		float mean;

		mean = 0.0f;

		this.lock.lock();
		try
		{
			mean = (float) this.sum / this.total_numbers_summed;
			return mean;
		}
		finally
		{
			this.lock.unlock();
		}
	}

	public void sum ( int x )
	{
		this.lock.lock();
		try
		{
			this.sum += x;
			this.total_numbers_summed++;
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
