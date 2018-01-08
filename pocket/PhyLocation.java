package pocket;
import static pocket.Sub.*;
import java.util.*;
import static fundamentals.Array.*;

/**
 * A comprehensive representation of a geographical position in terms of a coordinate with the times accessed, people related, and events involved. Locations of different associated information but identical coordinates are stored as one location object. A search for a location object given the time, person, or event concerned may be conducted. Relevant information of this location such as the zip code and the region are derived. 
 * @author Derek
 *
 */
public class PhyLocation implements Location
{
	/**
	 * The coordinate of the location.
	 */
	private Coordinate coor;
	/**
	 * The zip code of the location.
	 */
	private Zip zip;
	/**
	 * The region of the location.
	 */
	private Region region;
	/**
	 * The list of times the location is accessed.
	 */
	private List<Time> times = new ArrayList<Time>();
	/**
	 * The people having affiliated in the given location.
	 */
	private Map<Profile,Integer> part = new HashMap<Profile,Integer>();
	/**
	 * All events associated with this location.
	 */
	private Map<Event,Integer> eve = new HashMap<Event,Integer>();
	
	/**
	 * All created locations.
	 */
	private static final Map<Coordinate,PhyLocation> locations = new HashMap<Coordinate,PhyLocation>();
	
	/**
	 * Create a location storage based on the given coordinate, time affiliated, and the profiles of people involved. If the given location is existent, as having the same coordinate, throw a mild ExistentLocationException that is handled by the process method after modifying the location with the identical coordinate. 
	 * @param coor The coordinate of this location.
	 * @param time The time this location is accessed.
	 * @param pro The profiles of the participants in some event at this location.
	 */
	private PhyLocation(Coordinate coor, Time time, Event[] eve, Profile... pro)
	{
		int index;
		Set<Coordinate> cor;
		if((index=linearSearch(cor=locations.keySet(),coor))!=-1)
		{
			PhyLocation loc = locations.get(get(cor,index));
			loc.times.add(time);
			Integer fre;
			for(Profile holder: pro)
				part.put(holder,(fre=loc.part.get(holder))==null?1:fre++);
			throw new ExistentLocationException(loc,"The Location Attempting to Create is Already Existant.");
		}
		this.coor = coor;
		zip = new Zip(coor);
		region = new Region(zip);
		times.add(time);
		for(Profile holder: pro)
			part.put(holder,1);
	}
	
	/**
	 * Return the coordinate represented by this location.
	 * @return The coordinate represented by this location.
	 */
	public Coordinate getCoordinate()
	{
		return coor;
	}
	
	/**
	 * Return the zip code represented by this location.
	 * @return The zip code represented by this location.
	 */
	public Zip getZip()
	{
		return zip;
	}
	
	/**
	 * Return the region represented by this location.
	 * @return 
	 */
	public Region getRegion()
	{
		return region;
	}
	
	/**
	 * Process the given coordinate at the given time with the given participants. If the location is already existent, update the existent location and return reference; otherwise return a new location created with the specific information given. 
	 * @param pos The coordinate of this location.
	 * @param tim The time this location is accessed.
	 * @param pro The profiles of the participants in some event at this location.
	 * @return A location object representing the given coordinate.
	 */
	public static PhyLocation processLocation(Coordinate pos, Time tim, Event[] eve, Profile... pro)
	{
		try
		{
			return new PhyLocation(pos,tim,eve,pro);
		}
		catch(ExistentLocationException exc)
		{
			return exc.loc;
		}
	}
	
	/**
	 * Append the information of the given affiliates of this location to this location object.
	 * @param pro The profiles of the affiliates.
	 */
	public void appendAffiliates(Profile... pro)
	{
		Integer fre;
		for(Profile holder: pro)
			part.put(holder,(fre=part.get(holder))==null?1:fre+1);
	}
	
	/**
	 * Append the given event to this location.
	 * @param ev The event associated.
	 */
	public void appendEvents(Event... ev)
	{
		Integer fre;
		for(Event holder: ev)
			eve.put(holder,(fre=eve.get(holder))==null?1:fre+1);
	}
	
	/**
	 * A mild indicating that the location attempting to create is already existent by another location object having the same coordinate given. This exception contains a reference to that location object with identical coordinate.
	 * @author Derek
	 *
	 */
	protected static class ExistentLocationException extends RuntimeException
	{
		private static final long serialVersionUID = 1;
		public PhyLocation loc;
		
		public ExistentLocationException(PhyLocation loc)
		{
			this.loc = loc;
		}
		
		public ExistentLocationException(PhyLocation loc, String info)
		{
			super(info);
			this.loc = loc;
		}
		
		public ExistentLocationException(PhyLocation loc, Exception origin)
		{
			super(origin);
			this.loc = loc;
		}
	}
	
	
	public static void main(String[] args) 
	{
		

	}

}
