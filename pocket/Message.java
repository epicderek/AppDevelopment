package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * This class handles the general form of a message, which, in concrete data, is represented in the JSON Notification files.
 * @author Derek
 *
 */
public class Message extends Event
{
	/**
	 * The source of this message.
	 */
	private AbsLocation source;
	/**
	 * The sender of the message.
	 */
	private Profile from;
	/**
	 * The receiver of the message. In the Notification Files, the receiver is usually delegated as the User.
	 */
	private Profile to;
	/**
	 * The content of the message.
	 */
	private String cont;
	/**
	 * All the messages created.
	 */
	static final Map<Message, Message> mess = new HashMap<Message,Message>();
	
	/**
	 * Create a message object with the time, the sender and recipient, the content of the message, and the source.
	 * @param time The time this message advented.
	 * @param from The sender.
	 * @param to The receiver.
	 * @param cont The content of the message.
	 * @param loc The source of the message.
	 */
	protected Message(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		super(time);
		source = loc;
		this.from = from;
		this.to = to;
		this.cont = cont;
		loc = source;
		des = new Description(this.toString());
	}
	
	/**
	 * Create a message object with the time, the sender and recipient, the content of the message, and the source.
	 * @param time The time this message advented.
	 * @param from The sender.
	 * @param to The receiver.
	 * @param cont The content of the message.
	 * @param loc The source of the message.
	 * @return The Message object created in accord with the given properties.
	 */
	public static Message processMessage(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		Message see = new Message(time,from,to,cont,loc);
		if(mess.containsKey(see))
			return mess.get(see);
		mess.put(see,see);
		return see;
	}
	
	/**
	 * Append a description for this Message object.
	 * @param de The description to be appended.
	 */
	public void appendDescription(Description de)
	{
		if(des!=null)
			des.add(de);
		else
			des = de;
	}
	
	public Profile getFrom()
	{
		return from;
	}
	
	public Profile getTo()
	{
		return to;
	}
		
	public String getContent()
	{
		return cont;
	}
	
	public AbsLocation getSource()
	{
		return source;
	}
	
	public String toString()
	{
		return String.format("Message from %s to %s\nat %s\nin %s\nof \"%s\"",from.getName()+(from.getDial()==null?"":from.getDial()),to.getName()+(to.getDial()==null?"":to.getDial()),time,source.getSource(),cont);
	}
	
	public int hashCode()
	{
		return time.hashCode();
	}
	
	public boolean equals(Object other)
	{
		return toString().equals(((Message)other).toString());
	}
	
	public static void main(String[] args) 
	{

	}

}
