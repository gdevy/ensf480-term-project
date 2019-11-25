package server.entity.mhs;

import entity.socket.MessageType;
import server.SocketController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class MessageHandlerStrategy
{
	protected SocketController sc;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;

	public MessageHandlerStrategy( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois )
	{
		this.sc = sc;
		this.oos = oos;
		this.ois = ois;
	}

	public abstract void handleMessage( MessageType msgType );
}