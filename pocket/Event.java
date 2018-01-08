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
	private Time time;
	private PhyLocation loc;
	private List<Profile> part = new ArrayList<Profile>();
	private Description des;
	private static final Set<Event> events = new HashSet<Event>();
	
	/**
	 * 
	 * @param time
	 * @param loc
	 * @param des
	 * @param pro The profiles of the people associated with this event. 
	 */
	public Event(Time time, PhyLocation loc, Description des, Profile... pro)
	{
		this.time = time;
		this.loc = loc;
		this.des = des;
		Collections.addAll(part,pro);
		events.add(this);
		for(Profile holder: pro)
			holder.addEvent(this);
	}
	
	
	public Time getTime()
	{
		return time;
	}
	
	public PhyLocation getLocation()
	{
		return loc;
	}
	
	public List<Profile> getParticipants()
	{
		return new ArrayList<Profile>(part);
	}
	
	public Description getDescription()
	{
		return new Description(des);
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
	
	public static void main(String[] args) 
	{
		

	}

}
