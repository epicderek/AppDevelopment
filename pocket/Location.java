package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * A representation of either a physical location or an abstract one in the software space. 
 * @author Derek
 *
 */
public interface Location 
{
	/**
	 * A comparator that compares based on time chronologically.
	 */
	static final Comparator<Location> comp = new Comparator<Location>()
			{
				public int compare(Location one, Location two)
				{
					return last(one.getTimes()).compareTo(last(two.getTimes()));
				}
			};
			
	void appendEvents(Event... eve);
	void appendTimes(Time... times);
	Collection<Time> getTimes();
	
	public static Time last(Collection<Time> col)
	{
		Iterator<Time> ite = col.iterator();
		Time last = null;
		while(ite.hasNext())
			last = ite.next();
		return last;
	}
}
