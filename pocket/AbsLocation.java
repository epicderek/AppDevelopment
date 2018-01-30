package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * An abstract location that represents a source in the client's software space.
 * @author Derek
 *
 */
public class AbsLocation implements Location
{
	/**
	 * Description of this abstract location.
	 */
	private Description des;
	/**
	 * The exact source represented.
	 */
	private String source;
	/**
	 * Times this source is active in generation of data.
	 */
	private Set<Time> times = new HashSet<Time>();
	/**
	 * The events associated with this abstract location.
	 */
	private Map<Event,Integer> events = new HashMap<Event,Integer>();
	/**
	 * All the sources that engendered data.
	 */
	static final Map<String,AbsLocation> locations = new TreeMap<String,AbsLocation>();
	
	/**
	 * An exclusive constructor that does not account for duplicity.
	 * @param des Description of the source.
	 * @param time The time the location is occasioned.
	 * @param eve The events associated with this source.
	 */
	private AbsLocation(String des, Time time, Event... eve)
	{
		source = des;
		times.add(time);
		locations.put(source,this);
	}
	
	/**
	 * Create a source with the name of the source, the time, and the event. If duplicate, information would be updated to the existent source and a reference to that object will be returned.
	 * @param des Description of the source.
	 * @param time The time the location is occasioned.
	 * @param eve The events associated with this source.
	 * @return The source representing the given informations.
	 */
	public static AbsLocation processAbsLocation(String des, Time time, Event... eve)
	{
		AbsLocation output;
		if(locations.containsKey(des))
		{
			output = locations.get(des);
			output.appendTimes(time);
			output.appendEvents(eve);
			return output;
		}
		output = new AbsLocation(des,time,eve);
		return output;
	}
	
	/**
	 * Append a description to this source that may be later searched.
	 * @param de The description to be appended.
	 */
	public void appendDescription(Description de)
	{
		if(des==null)
			des = de;
		else
			des.add(de);
	}

	public void appendTimes(Time... times)
	{
		Collections.addAll(this.times,times);
	}
	
	public void appendEvents(Event... ev) 
	{
		Integer fre;
		for(Event holder: ev)
			events.put(holder,(fre=events.get(holder))==null?1:fre+1);
	}
	
	public String getSource()
	{
		return source;
	}
	
	public Description getDescription()
	{
		return des;
	}
	
	public Collection<Time> getTimes()
	{
		return times;
	}
	
	public String toString()
	{
		return source;
	}
	
	public static void main(String[] args) 
	{

	}

}
