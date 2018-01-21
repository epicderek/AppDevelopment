package pocket;
import static pocket.Sub.*;
import java.util.*;

public class Message extends Event
{
	private AbsLocation source;
	private Profile from;
	private Profile to;
	private String cont;
	private List<Message> mes = new ArrayList<Message>();
	
	public Message(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		super(time);
		source = loc;
		this.from = from;
		this.to = to;
		this.cont = cont;
		loc = source;
		mes.add(this);
	}
	
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
	
	public static void main(String[] args) 
	{

	}

}
