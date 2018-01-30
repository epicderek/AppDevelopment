package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * A record of an event with information about time, location, the participants, and a description. A search of event based on time, location, or descripton may be conducted. 
 * @author Derek
 *
 */
public class Event 
{
	protected Description des;
	protected Time time;
	protected Location loc;
	protected List<Profile> part = new ArrayList<Profile>();
	static final Map<Event,Event> eves = new HashMap<Event,Event>();
	
	/**
	 * A comparator that compares based on time chronologically.
	 */
	static final Comparator<Event> comp = new Comparator<Event>()
			{
				public int compare(Event one, Event two)
				{
					return one.time.compareTo(two.time);
				}
			};
	/**
	 * Create an event as according to the description, the specific time, the location, and the possible affiliates. 
	 * @param time The time of this event. 
	 * @param loc The location this event advented, either a physical location or an abstract location of an app. 
	 * @param des The description of this location. 
	 * @param pro The profiles of the people associated with this event. 
	 */
	protected Event(Time time, Location loc, Description des, Profile... pro)
	{
		this.time = time;
		this.loc = loc;
		this.des = des;
		Collections.addAll(part,pro);
		for(Profile holder: pro)
			holder.appendEvents(this);
	}
	
	/**
	 * A reserved constructor for quick instantiation for sub-classes.
	 * @param time The time the event occasioned.
	 */
	protected Event(Time time)
	{
		this.time = time;
	}
	
	/**
	 * Process an Event object with the time, the location, the description, and the affiliated. 
	 * @param time The time the event occasioned. 
	 * @param loc The location the event occasioned.
	 * @param des The description of the event.
	 * @param pro The people involved. 
	 * @return An Event object representing all the giving information.
	 */
	public static Event processEvent(Time time, Location loc, Description des, Profile... pro)
	{
		Event see = new Event(time,loc,des,pro);
		if(eves.containsKey(see))
			return eves.get(see);
		eves.put(see,see);
		return see;
	}
	
	public void appendParticipants(Profile... profiles)
	{
		Collections.addAll(part,profiles);
	}
	
	public void appendLocation(Location loc)
	{
		if(this.loc!=null)
			throw new RuntimeException("Existent Location for this event.");
		this.loc = loc;
	}
	
	public Time getTime()
	{
		return time;
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public List<Profile> getParticipants()
	{
		return new ArrayList<Profile>(part);
	}
	
	public Description getDescription()
	{
		return des;
	}
	
	public static List<Event> searchEvent(Time time)
	{
		List<Event> output = new ArrayList<Event>();
		for(Event holder: eves.values())
			if(holder.getTime().equals(time))
				output.add(holder);
		return output;
	}
	
	public static List<Event> searchLocation(PhyLocation loc)
	{
		List<Event> output = new ArrayList<Event>();
		for(Event holder: eves.values())
			if(holder.getLocation().equals(loc))
				output.add(holder);
		return output;
	}
	
	public static List<Event> searchAffiliate(Profile pro)
	{
		List<Event> output = new ArrayList<Event>();
		for(Event holder: eves.values())
			if(holder.getParticipants().contains(pro))
				output.add(holder);
		return output;
	}
	
	public String toString()
	{
		return String.format("Event: %s\nin %s\nof participants %s",des,loc,part);
	}
	
	public int hashCode()
	{
		return time.hashCode();
	}
	
	public boolean equals(Object other)
	{
		return loc.equals(((Event)other).loc)&&des.equals(((Event)other).des);
	}
	
	public static void main(String[] args) 
	{
		

	}

}
