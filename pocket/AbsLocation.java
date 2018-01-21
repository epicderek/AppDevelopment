package pocket;
import static pocket.Sub.*;
import java.util.*;

public class AbsLocation implements Location
{
	private Description des;
	private String source;
	private List<Time> times = new ArrayList<Time>();
	private Map<Event,Integer> events = new HashMap<Event,Integer>();
	static final Map<String,AbsLocation> locations = new HashMap<String,AbsLocation>();
	
	private AbsLocation(String des, Time time, Event... eve)
	{
		source = des;
		times.add(time);
		locations.put(source,this);
	}
	
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
	
	public String toString()
	{
		return source;
	}
	
	public static void main(String[] args) 
	{

	}

}
