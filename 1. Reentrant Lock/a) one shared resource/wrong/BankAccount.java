public class BankAccount
{
	private double balance;

	BankAccount (double balance)
	{
		this.balance = balance;
	}

	double getBalance ()
	{
		return this.balance;
	}

	boolean deposit (double value)
	{
		this.balance += value;
		return true;
	}
}
