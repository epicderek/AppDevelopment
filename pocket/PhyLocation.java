package pocket;
import static pocket.Sub.*;

import java.io.IOException;
import java.util.*;

//AIzaSyAcqinQbVoIgQ6BX3paA5423pB1KeX6Em0
//AIzaSyBP8kf07_FR9y-hUyazmerZEWgl5uBi7j4
/**
 * A comprehensive representation of a geographical position in terms of a coordinate with the times accessed, people related, and events involved. Locations of different associated information but identical coordinates are stored as one location object. A search for a location object given the time, person, or event concerned may be conducted. Relevant information of this location such as the zip code and the region are derived. 
 * @author Derek
 *
 */
public class PhyLocation implements Location
{
	/**
	 * A description of this physical location.
	 */
	private Description des;
	/**
	 * The coordinate of the location.
	 */
	private Coordinate coor;
	
	private String[] types;
	
	private String formatted;
	
	private List<Time> times = new ArrayList<Time>();
	/**
	 * The people having affiliated in the given location.
	 */
	private Map<Profile,Integer> part = new HashMap<Profile,Integer>();
	/**
	 * All events associated with this location.
	 */
	private Map<Event,Integer> eve = new HashMap<Event,Integer>();
	private Map<LocationType,String> info = new HashMap<LocationType,String>();
	
	/**
	 * All created locations.
	 */
	private static final Map<Coordinate,PhyLocation> locations = new HashMap<Coordinate,PhyLocation>();
	
	/**
	 * Create a location in the simplest form--the coordinate and the location.
	 * @param coor The coordinate of this location.
	 * @param time The time involved.
	 * @throws IOException 
	 */
	public static PhyLocation getLocation(Coordinate coor, Time time) throws IOException
	{
		PhyLocation output = new PhyLocation(coor,time);
		output.processLoc();
		if(locations.containsKey(coor))
		{
			PhyLocation tar = locations.get(coor);
			tar.times.add(time);
			return tar;
		}
		return output;
	}
	
	/**
	 * Create a location in the simplest form--the coordinate and the location. A call to this constructor must not be directly invoked as it puts no check on the present existence of the coordinate in a form of location.
	 * @param coor The coordinate of this location.
	 * @param time The time involved.
	 */
	private PhyLocation(Coordinate coor, Time time)
	{
		this.coor = coor;
		this.times.add(time);
	}
	
	/**
	 * Create a location storage based on the given coordinate, time affiliated, and the profiles of people involved. If the given location is existent, as having the same coordinate, throw a mild ExistentLocationException that is handled by the process method after modifying the location with the identical coordinate. 
	 * @param coor The coordinate of this location.
	 * @param time The time this location is accessed.
	 * @param pro The profiles of the participants in some event at this location.
	 * @throws IOException 
	 */
	private PhyLocation(Coordinate coor, Time time, Event[] eve, Profile... pro) throws IOException
	{
		this.coor = coor;
		times.add(time);
		processLoc();
		if(locations.containsKey(coor))
		{
			PhyLocation loc = locations.get(coor);
			loc.times.add(time);
			Integer fre;
			for(Profile holder: pro)
				part.put(holder,(fre=loc.part.get(holder))==null?1:fre++);
			throw new ExistentLocationException(loc,"The Location Attempting to Create is Already Existant.");
		}
		for(Profile holder: pro)
			part.put(holder,1);
	}
	
	public void appendDescription(Description other)
	{
		des.add(other);
	}
	
	public String[] getTypes()
	{
		return types;
	}
	
	public String getFormmated()
	{
		return formatted;
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
	 * Return all the relevant geometric information pertaining to this coordinate.
	 * @return 
	 */
	public Map<LocationType,String> getInfo()
	{
		return info;
	}
	
	/**
	 * Process the given coordinate at the given time with the given participants. If the location is already existent, update the existent location and return reference; otherwise return a new location created with the specific information given. 
	 * @param pos The coordinate of this location.
	 * @param tim The time this location is accessed.
	 * @param pro The profiles of the participants in some event at this location.
	 * @param eve The events associated with this location.
	 * @return A location object representing the given coordinate.
	 * @throws IOException 
	 */
	public static PhyLocation processLocation(Description des, Coordinate pos, Time tim, Event[] eve, Profile... pro) throws IOException
	{
		try
		{
			PhyLocation output = new PhyLocation(pos,tim,eve,pro);
			output.des = des;
			return output;
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
	 * Append the times associated with this location.
	 * @param times The times associated with this location.
	 */
	public void appendTimes(Time... times)
	{
		Collections.addAll(this.times,times);
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
	
	private void processLoc() throws IOException
	{
		Object[] ret = Interpretor.interpretLoc(coor.getLati(),coor.getLng(),info);
		formatted = (String)ret[0];
		types = (String[])ret[1];
		coor.setLati((double)ret[2]);
		coor.setLng((double)ret[3]);
	}
	
	public static enum LocationType
	{
		STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE
	}
	
	public static void main(String[] args) 
	{
		
	}

}
