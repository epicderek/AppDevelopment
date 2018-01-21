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
	static final List<Event> events = new ArrayList<Event>();
	
	/**
	 * Create an event as according to the description, the specific time, the location, and the possible affiliates. 
	 * @param time The time of this event. 
	 * @param loc The location this event advented, either a physical location or an abstract location of an app. 
	 * @param des The description of this location. 
	 * @param pro The profiles of the people associated with this event. 
	 */
	public Event(Time time, Location loc, Description des, Profile... pro)
	{
		this.time = time;
		this.loc = loc;
		this.des = des;
		Collections.addAll(part,pro);
		events.add(this);
		for(Profile holder: pro)
			holder.appendEvents(this);
	}
	
	protected Event(Time time)
	{
		this.time = time;
		events.add(this);
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
		for(Event holder: events)
			if(holder.getTime().equals(time))
				output.add(holder);
		return output;
	}
	
	public static List<Event> searchLocation(PhyLocation loc)
	{
		List<Event> output = new ArrayList<Event>();
		for(Event holder: events)
			if(holder.getLocation().equals(loc))
				output.add(holder);
		return output;
	}
	
	public static List<Event> searchAffiliate(Profile pro)
	{
		List<Event> output = new ArrayList<Event>();
		for(Event holder: events)
			if(holder.getParticipants().contains(pro))
				output.add(holder);
		return output;
	}
	
	public String toString()
	{
		return String.format("Event: %s\nat time %s\nin %s\nof participants %s",des,time,loc,part);
	}
	
	public static void main(String[] args) 
	{
		

	}

}
