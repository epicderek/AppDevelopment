package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * A comprehensive representation of a person as a contact. 
 * @author Derek
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
	private List<String> email = new ArrayList<String>();
	/**
	 * The phone number of the person.
	 */
	private List<String> phone = new ArrayList<String>();
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
	static final Map<String,Profile> pros = new TreeMap<String,Profile>();
	
	/**
	 * A reserved constructor for quick instantiation with the minimum information.
	 * @param name The name of the person.
	 */
	protected Profile(String name)
	{
		this.name = name;
	}
	
	/**
	 * Create a Profile object with the name of the person, the email, and the phone.
	 * @param name The name of the person.
	 * @param email The email of the person.
	 * @param phone The dial of the person.
	 */
	private Profile(String name, String email, String phone)
	{
		this.name = name;
		this.email.add(email);
		this.phone.add(phone);
	}
	
	/**
	 * Create a Profile object with the name of the person, the emails, and the phones.
	 * @param name The name of the person.
	 * @param emails The email of the person.
	 * @param phones The dial of the person.
	 */
	private Profile(String name, String[] email, String[] phone)
	{
		this.name = name;
		Collections.addAll(this.email,email);
		Collections.addAll(this.phone,phone);
	}
	
	
	/**
	 * Instantiate a Profile Object with the minimum information.
	 * @param name The name of the person.
	 * @return The Profile object with the specific name.
	 */
	public static Profile processProfile(String name)
	{
		Profile see = new Profile(name);
		if(pros.containsKey(name))
			return pros.get(name);
		pros.put(name,see);
		return see;
	}
	
	/**
	 * Create a Profile object with the name of the person, the email, and the phone.
	 * @param name The name of the person.
	 * @param email The email of the person.
	 * @param phone The dial of the person.
	 */
	public static Profile processProfile(String name, String email, String phone)
	{
		Profile see = new Profile(name,email,phone);
		if(pros.containsKey(name))
			throw new RuntimeException("Not ready yet.");
		pros.put(name,see);
		return see;
	}
	
	/**
	 * Create a Profile object with the name of the person, the email, and the phone.
	 * @param name The name of the person.
	 * @param email The email of the person.
	 * @param phone The dial of the person.
	 */
	public static Profile processProfile(String name, String[] email, String[] phone)
	{
		Profile see = new Profile(name,email,phone);
		if(pros.containsKey(name))
			throw new RuntimeException("Not ready yet.");
		pros.put(name,see);
		return see;
	}
	
	public void addDial(String dial)
	{
		if(phone.contains(dial))
			return;
		this.phone.add(dial);
	}
	
	public void addEmail(String email)
	{
		if(this.email.contains(email))
			return;
		this.email.add(email);
	}
	
	/**
	 * Append a description for this Profile.
	 * @param des The description object to be appended.
	 */
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
	
	public List<String> getDial()
	{
		return phone;
	}
	
	public List<String> getEmail()
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
	
	public static boolean existent(String name)
	{
		return pros.containsKey(name);
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
