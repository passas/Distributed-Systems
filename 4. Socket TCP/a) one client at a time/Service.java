public class Service
{
	private int numbers;
	private int sum;

	public Service ()
	{
		this.numbers = 0;
		this.sum = 0;
	}

	public void sum ( int x )
	{
		this.sum += x;
		this.numbers++;
	}

	public int getSum ()
	{
		return this.sum;
	}

	public float mean ()
	{
		float mean;

		mean = 0.0f;

		return (float) this.sum / this.numbers;
	}
}
