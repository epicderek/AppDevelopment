package pocket;
import java.util.*;

public class Search 
{
	private List<Event> events = new ArrayList<Event>();
	private List<Location> locs = new ArrayList<Location>();
	private List<Profile> pros = new ArrayList<Profile>();
	
	public void appendProfiles(Profile... pros)
	{
		Collections.addAll(this.pros,pros);
	}
	
	public void appendLocations(Location... locs)
	{
		Collections.addAll(this.locs,locs);
	}
	
	
	public static void main(String[] args) 
	{

	}

}
