package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * A basic representation of an email, with the time, the affiliates, the content, and the source.
 * @author Maggie
 *
 */
public class Email extends Message
{
	/**
	 * All the emails created.
	 */
	static Map<Email,Email> ems = new HashMap<Email,Email>();
	
	/**
	 * Create an email with the time, the affiliates, the content, and the source.
	 * @param time The time the email is sent.
	 * @param from The sender.
	 * @param to The recipient.
	 * @param cont The content of the email.
	 * @param loc The source.
	 */
	private Email(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		super(time,from,to,cont,loc);
	}
	
	/**
	 * Create an email with the time, the affiliates, the content, and the source.
	 * @param time The time the email is sent.
	 * @param from The sender.
	 * @param to The recipient.
	 * @param cont The content of the email.
	 * @param loc The source.
	 * @return An Email object representing all the given information.
	 */
	public static Email processEmail(Time time, Profile from, Profile to, String cont, AbsLocation loc)
	{
		Email see = new Email(time,from,to,cont,loc);
		if(ems.containsKey(see))
			return ems.get(see);
		ems.put(see,see);
		return see;
	}
	
	public String toString()
	{
		return "Email"+super.toString().substring(7);
	}
	
	public int hashCode()
	{
		return time.hashCode();
	}
	
	public boolean equals(Object other)
	{
		return toString().equals(((Email)other).toString());
	}
	
	public static void main(String[] args) 
	{
		
	}

}
