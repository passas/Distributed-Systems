/**
 * Multi-Threaded ( ~~~ )
 **/
import java.net.Socket;

import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable
{
	/**//**//**//**//**//**//**//**//**//**//**//**//**/
	/**/public static class Frame					/**/
	/**/{											/**/
	/**/	public final int tag;					/**/
	/**/	public final byte[] data;				/**/
	/**/											/**/
	/**/	public Frame (int tag, byte[] data)		/**/
	/**/	{										/**/
	/**/		this.tag = tag;						/**/
	/**/		this.data = data;					/**/
	/**/	}										/**/
	/**/}											/**/
	/**//**//**//**//**//**//**//**//**//**//**//**//**/

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	private ReentrantLock lock_input;
	private ReentrantLock lock_output;

	public TaggedConnection(Socket socket) throws Exception
	{
		this.socket = socket;

		this.input = new DataInputStream ( this.socket.getInputStream () );
		this.output = new DataOutputStream ( this.socket.getOutputStream () );

		this.lock_input = new ReentrantLock ();
		this.lock_output = new ReentrantLock ();
	}

	public void send(Frame frame) throws Exception
	{
		this.lock_output.lock();
		try
		{
			output.writeInt ( frame.tag );
			output.writeInt ( frame.data.length );
			output.write ( frame.data, 0, frame.data.length );
		}
		finally
		{
			this.lock_output.unlock();
		}
	}

	public void send(int tag, byte[] data) throws Exception
	{
		this.lock_output.lock();
		try
		{
			output.writeInt ( tag );
			output.writeInt ( data.length );
			output.write ( data, 0, data.length );
		}
		finally
		{
			this.lock_output.unlock();
		}
	}

	public Frame receive() throws Exception
	{
		byte[] data; int size;
		int tag;
		Frame r;

		data = null;
		size = 0;
		tag = 0;
		r = null;

		this.lock_input.lock();
		try
		{
			tag = input.readInt ();
			size = input.readInt ();

			data = new byte[size];
			input.readFully ( data, 0, size);

			return new TaggedConnection.Frame ( tag, data );
		}
		catch (Exception e)
		{
			return null;
		}
		finally
		{
			this.lock_input.unlock();
		}
	}

	public void close() throws Exception
	{
		//this.lock_input.lock();
		this.lock_output.lock();
		try
		{
			this.input.close();
			this.output.close();
			this.socket.close();
		}
		finally
		{
			this.lock_output.unlock();
			//this.lock_input.unlock();
		}
	}
}
