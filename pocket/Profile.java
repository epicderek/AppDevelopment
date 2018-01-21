package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * A comprehensive representation of a person as a contact. 
 * @author Maggie
 *
 */
public class Profile 
{
	/**
	 * A specific description of the person.
	 */
	private Description des;
	/**
	 * The name of the person.
	 */
	private String name;
	/**
	 * The email of the person.
	 */
	private String email;
	/**
	 * The phone number of the person.
	 */
	private String phone;
	/**
	 * The list of events the person is involved.
	 */
	protected List<Event> eve = new ArrayList<Event>();
	/**
	 * The list of locations the person is present.
	 */
	protected List<Location> loc = new ArrayList<Location>();
	/**
	 * All the existent profiles.
	 */
	static final Set<Profile> pros = new HashSet<Profile>();
	
	public Profile(String name, String email, String phone)
	{
		this.name = name;
		this.email = email;
		this.phone = phone;
		pros.add(this);
	}
	
	protected Profile(String name)
	{
		this.name = name;
	}
	
	public void addDial(String dial)
	{
		if(phone!=null)
			throw new RuntimeException("Existent Phone Number");
		phone = dial;
	}
	
	public void addEmail(String email)
	{
		if(email!=null)
			throw new RuntimeException("Existent Email");
		this.email = email;
	}
	
	public void appendDescription(Description des)
	{
		if(this.des==null)
			this.des = des;
		else
			this.des.add(des);
	}
	
	public void appendEvents(Event... eve)
	{
		Collections.addAll(this.eve,eve);
	}
	
	public void appendLocations(Location... loca)
	{
		Collections.addAll(loc,loca);
	}
	
	public Description getDescription()
	{
		return des;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDial()
	{
		return phone;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public Set<Location> getLocations()
	{
		return new LinkedHashSet<Location>(loc);
	}
	
	public Set<Event> getEvents()
	{
		return new LinkedHashSet<Event>(eve);
	}
	
	public static boolean existent(Profile profile)
	{
		return pros.contains(profile);
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public boolean equals(Object other)
	{
		return name.equals(((Profile)other).name);
	}
	
	public String toString()
	{
		return String.format("Profile of %s\nemail %s, dial %s",name,email,phone);
	}
	
	public static void main(String[] args) 
	{
		
	}

}
