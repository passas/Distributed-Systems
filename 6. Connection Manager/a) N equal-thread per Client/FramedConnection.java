import java.util.concurrent.locks.ReentrantLock;

import java.net.Socket;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class FramedConnection implements AutoCloseable
{
	private ReentrantLock lock_receive;
	private ReentrantLock lock_send;
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;

	public FramedConnection (Socket socket) throws Exception
	{
		this.lock_receive = new ReentrantLock ();
		this.lock_send = new ReentrantLock ();
		this.socket = socket;

		this.output = new DataOutputStream ( this.socket.getOutputStream () );
		this.input = new DataInputStream ( this.socket.getInputStream () );
	}
	
	public void send (byte[] data) throws Exception
	{
		int data_length;

		data_length = 0;

		this.lock_send.lock ();
		try
		{
			data_length = data.length;
			this.output.writeInt ( data_length );
			this.output.write (data, 0, data_length);
		}
		finally
		{
			this.lock_send.unlock();
		}
	}
	
	public byte[] receive () throws Exception
	{
		byte[] buf;
		int data_length;

		buf = null;
		data_length = 0;

		this.lock_receive.lock ();
		try
		{
			data_length = this.input.readInt();
			buf = new byte[data_length];
			this.input.readFully (buf, 0, data_length);
			return buf;
		}
		catch (Exception e)
		{
			return null;
		}
		finally
		{
			this.lock_receive.unlock();
		}
	}
	
	public void close() throws Exception
	{
		this.lock_send.lock ();
		this.lock_receive.lock ();
		try
		{
			this.output.close();
			this.input.close();
			this.socket.close();
		}
		finally
		{	
			this.lock_receive.unlock ();
			this.lock_send.unlock();
		}
	}
}
