public class Bank
{
	private BankAccount savings;

	Bank (BankAccount savings)
	{
		this.savings = savings;
	}

	public double getBalance ()
	{
		return this.savings.getBalance();
	}

	boolean deposit (double value)
	{
		return this.savings.deposit (value);
	}
}
