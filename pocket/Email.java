package pocket;
import static pocket.Sub.*;
import java.util.*;

public class Email extends Message
{
	private List<Email> ems = new ArrayList<Email>();
	public Email(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		super(time,from,to,cont,loc);
		ems.add(this);
	}
	
	public String toString()
	{
		return "Email"+super.toString().substring(7);
	}
	
	public static void main(String[] args) 
	{
		
	}

}
