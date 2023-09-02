public class My_State
{
	private int sum;

	public My_State ()
	{
		this.sum = 0;
	}

	public void sum ( int x )
	{
		this.sum += x;
	}

	public int my_sum ()
	{
		return this.sum;
	}
}
