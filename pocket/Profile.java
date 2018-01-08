package pocket;
import static pocket.Sub.*;
import java.util.*;

public class Profile 
{
	private Description des;
	private List<Event> eve = new ArrayList<Event>();
	private List<PhyLocation> loc = new ArrayList<PhyLocation>();
	private static final Set<Profile> pros = new HashSet<Profile>();
	
	public Profile(Description des, PhyLocation[] loc, Event...eve)
	{
		this.des = des;
		Collections.addAll(this.loc,loc);
		Collections.addAll(this.eve,eve);
		pros.add(this);
	}
	
	public Description getDescription()
	{
		return des;
	}
	
	public void addEvent(Event eve)
	{
		this.eve.add(eve);
	}
	
	public void addLocation(PhyLocation loca)
	{
		loc.add(loca);
	}
	
	public static boolean existent(Profile profile)
	{
		return pros.contains(profile);
	}
	
	
	
	public static void main(String[] args) 
	{
		
	}

}
