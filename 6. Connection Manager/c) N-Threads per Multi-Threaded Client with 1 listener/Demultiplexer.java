import java.util.Map;
import java.util.HashMap;

import java.util.ArrayDeque;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Demultiplexer implements AutoCloseable
{

	private TaggedConnection connection;

	private Map<Integer, ArrayDeque<byte[]>> tag_byte_queue;

	private Map<Integer, ReentrantLock> tag_lock;
	private Map<Integer, Condition> tag_condition;


	public Demultiplexer(TaggedConnection conn)
	{
		this.connection = conn;


		this.tag_byte_queue = new HashMap<>();
		for (int i=0; i<=4; i++)
			this.tag_byte_queue.put ( i, new ArrayDeque<>() );


		this.tag_lock = new HashMap<>();
		for (int i=0; i<=4; i++)
			this.tag_lock.put ( i, new ReentrantLock () );

		this.tag_condition = new HashMap<>();
		for (int i=0; i<=4; i++)
			this.tag_condition.put ( i,  this.tag_lock.get(i).newCondition() );
	}

	public void start() throws Exception
	{
		Runnable demultiplexer_runnable = () -> 
		{
			try
			{
				TaggedConnection.Frame frame;
				int tag; byte[] data;
				
				while ( (frame = this.connection.receive()) != null )
				{
					tag = frame.tag;
					data = frame.data;
		
					this.tag_lock.get ( tag ).lock();
					try
					{
						this.tag_byte_queue.get ( tag ).add ( data );
						this.tag_condition.get ( tag ).signalAll();
					}
					finally
					{
						this.tag_lock.get ( tag ).unlock();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		};

		new Thread ( demultiplexer_runnable ).start();
	}

	public void send(TaggedConnection.Frame frame) throws Exception
	{
		this.connection.send ( frame );
	}

	public void send(int tag, byte[] data) throws Exception
	{
		this.connection.send ( tag, data );
	}

	public byte[] receive(int tag) throws Exception
	{
		this.tag_lock.get( tag ).lock();
		try
		{
			while ( this.tag_byte_queue.get(tag) == null || this.tag_byte_queue.get(tag).size() == 0 )
			{
				this.tag_condition.get (tag).await();
			}

			return this.tag_byte_queue.get(tag).remove();
		}
		finally
		{
			this.tag_lock.get( tag ).unlock();
		}
	}

	public void close() throws Exception
	{
		this.connection.close();
	}

}
