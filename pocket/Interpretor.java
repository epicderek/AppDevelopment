package pocket;
import static pocket.Sub.*;
import static pocket.PhyLocation.LocationType;
import static pocket.PhyLocation.LocationType.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.net.*;
import javax.json.*;

/**
 * A JSON interpreter that interprets specific JSON data from specific apps as well as reverse geocoding and parsing irregular JSON files that contains more than one JSON object. 
 * The specific apps included are notification, device event, browser search, browser visit, logs, and location, which respectively correspond to Message of Event, Query, Profiles, and PhyLocation.   
 * @author Derek
 *
 */
public class Interpretor 
{
	//The file in consideration and their readers.
	private File file;
	private Reader read;
	private JsonReader reader;
	/**
	 * The api keys for the google geocoding api.
	 */
	static String[] keys;
	static String key;
	//The portions of the url for the google api.
	private static final String uFirst;
	private static final String uSecond;
	/**
	 * The counter that keeps track of the number of keys exausted.
	 */
	public static int keyCount;
	/**
	 * A counter that tracks the number of 
	 */
	public static int count;
	static
	{
		keys = new String[]{"AIzaSyAe67qjsHOQomCNyIIyi_UKm5uqNvTGcvA","AIzaSyDzqYwvSvnHhBAPf0ZisEPCuZWZv2ldry4","AIzaSyDG4Gjp_mW0T25VA17Jmk5pYJRJUFvnbUA","AIzaSyDnPcdlEZyqR6Cr2ite9aAvoTMDzDhty_E","AIzaSyDnfk0csfpKC1G12EUh4BzyuXOoa_B0fKE"};
		key = keys[0];
		uFirst = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		uSecond = "&key=";
	}
	
	/**
	 * A constructor reserved for package testing. 
	 */
	Interpretor()
	{
		
	}
	
	/**
	 * Instantiate an interpreter with respect to the given JSON file.
	 * @param file The JSON file to be processed.
	 * @throws IOException
	 */
	public Interpretor(File file) throws IOException
	{
		this.file = file;
		read = new FileReader(file);
		reader = Json.createReader(read);
	}
	
	/**
	 * Set the target JSON file of the interpreter and instantiate the necessary readers.
	 * @param other The file to be processed.
	 * @throws FileNotFoundException
	 */
	public void set(File other) throws FileNotFoundException
	{
		file = other;
		read = new FileReader(other);
		reader = Json.createReader(read);
	}
	
	/**
	 * Interpret a Notification JSON File and record the data as native objects. As notification may contain messages, the output may be a message, which is a sub-type of event.
	 * @return An array of events whose quantity directly equals the number of JSON Objects processed.
	 * @throws IOException
	 */
	public Event[] interpretNote() throws IOException
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Event[] eves = new Event[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			boolean cat = obj.containsKey("category");
			StringBuilder builder = new StringBuilder();
			builder.append(!cat?"":obj.getString("category"));
			boolean isMsg = false;
			if(cat&&obj.getString("category").equals("msg"))
				isMsg = true;
			builder.append(" ");
			builder.append(obj.getString("action"));
			builder.append("\n\"");
			builder.append(obj.containsKey("notification_text")?obj.getString("notification_text"):"");
			builder.append("\"\nby ");
			builder.append(obj.containsKey("notification_title")?obj.getString("notification_title"):"");
			Description des = new Description(builder.toString());
			Time time = new Time(obj.getInt("time_created"));
			if(isMsg)
			{
				eves[counter++] = Message.processMessage(time,Profile.processProfile(obj.getString("notification_title")),Profile.processProfile("User"),obj.containsKey("notification_text")?obj.getString("notification_text"):"",AbsLocation.processAbsLocation(obj.getString("package_name"),time));
			}
			else
				eves[counter++] = Event.processEvent(time,AbsLocation.processAbsLocation(obj.getString("package_name"),time),des);
		}
		read.close();
		return eves;
	}
	
	/**
	 * Interpret a Browser Search JSON File and record the data as Query objects.
	 * @return An array containing the exact number of Query objects as the number of inputed Browser Search JSON Objects.
	 * @throws IOException
	 */
	public Query[] interpretBro() throws IOException
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Query[] ques = new Query[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			String des = obj.getString("text");
			Time time = new Time(obj.getInt("time_created"));
			Query que = Query.processQuery(time,des,obj.getString("browser_name"));
			ques[counter++] = que;
		}
		read.close();
		return ques;
	}
	
	/**
	 * Interpret a Browser Visit JSON File and record the data as Query objects with the description of a visit.
	 * @return An array of Query objects of the exact quantity as the inputed Browser Search JSON Objects.
	 * @throws IOException
	 */
	public Query[] interpretVis() throws IOException
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Query[] ques = new Query[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			Time time = new Time(obj.getInt("time_created"));
			String url = obj.getString("url");
			String title = obj.getString("title")+(url.equals("Search of type URL")?"":url);
			String source = obj.getString("package_name");
			Query que = Query.processQuery(time,String.format("Browser Visit\n%s",title),source);
			ques[counter++] = que;
		}
		read.close();
		return ques;
	}
	
	/**
	 * Interpret a Logs JSON File and record the data regarding the contact as Profile objects.
	 * @return An array of Profile Objects that are of the exact number of Contact JSON Objects inputed.
	 * @throws IOException
	 */
	public Profile[] interpretLog() throws IOException
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("contact_list");
		Profile[] pros = new Profile[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			count++;
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			Profile pro = Profile.processProfile(obj.getString("name"));
			JsonArray ref;
			if((ref=obj.getJsonArray("phone_numbers")).size()!=0)
				pro.addDial(ref.getString(0));
			if((ref=obj.getJsonArray("emails")).size()!=0)
				pro.addEmail(ref.getString(0));
			pros[counter++] = pro;
		}
		read.close();
		return pros;
	}
	
	/**
	 * Interpret a Device Event JSON File and record the data as Event objects.
	 * @return An array of events of the exact number of the inputed Device Event JSON Objects.
	 */
	public Event[] interpretDee()
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Event[] eves = new Event[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			Time time = new Time(obj.getInt("time_created"));
			Description des = new Description(String.format("%s, %s",obj.getString("type"),obj.getString("event")));
			Location loc = AbsLocation.processAbsLocation("Device",time);
			Event eve = new Event(time,loc,des);
			eves[counter++] = eve;
		}
		return eves;
	}
	
	/**
	 * Interpret a Location JSON File and record the data as PhyLocation objects. 
	 * @return An array of PhyLocation objects that are of the exact number of inputed Location JSON Objects.
	 * @throws IOException
	 */
	public PhyLocation[] interpretLoc() throws IOException
	{
		JsonObject[] objs = parseIrreJsonFile(file);
		PhyLocation[] locs = new PhyLocation[objs.length];
		int counter = 0;
		for(JsonObject holder: objs)
		{
			JsonObject obj = holder.getJsonObject("itemMap");
			Time time = new Time(obj.getInt("time_created"));
			JsonArray co = obj.getJsonArray("coordinates");
			locs[counter++] = PhyLocation.getLocation(new Coordinate(co.getJsonNumber(0).doubleValue(),co.getJsonNumber(1).doubleValue(),co.getJsonNumber(2).doubleValue()),time);
		}
		return locs;
	}
	
	/**
	 * A helper method that computes relevant geographic information based on the latitude and longitude given through the google map geocoding api. The results are recorded in the given map whose keys, to be filled by this method along with their proper values, are the enumerated types declared in PhyLocation.
	 * @param lati The latitude of the location.
	 * @param lon The longitude of the location.
	 * @param record The container of searched information.
	 * @return An object array whose first index is the formatted address, the second the types of this location, third and fourth the adjusted coordinate returned by the google map that comports with the facility located.
	 * @throws IOException
	 */
	public static Object[] interpretLoc(Double lati, Double lon, Map<LocationType,String> record) throws IOException
	{
		URL url = new URL(String.format("%s%s,%s%s%s",uFirst,lati,lon,uSecond,key));		
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder json = new StringBuilder();
		String line;
		while((line=reader.readLine())!=null)
		{
			json.append(line);
			json.append("\n");
		}
		String resp = json.substring(0,json.length()-1);
		JsonReader read = Json.createReader(new StringReader(resp));
		JsonObject loc = read.readObject().getJsonArray("results").getJsonObject(0);
		JsonArray add = loc.getJsonArray("address_components");
		for(JsonValue holder: add)
		{
			JsonObject obj = (JsonObject)holder;
			if(obj.getJsonArray("types").getString(0).equals("street_number"))
				record.put(STREET_NUM,obj.getString("long_name"));
			if(obj.getJsonArray("types").getString(0).equals("route"))
				record.put(ROUTE,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("neighborhood"))
				record.put(NEIGHBORHOOD,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("locality"))
				record.put(LOCALITY,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("administrative_area_level_2"))
				record.put(ADMINISTRATIVE2,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("administrative_area_level_1"))
				record.put(ADMINISTRATIVE1,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("country"))
				record.put(COUNTRY,obj.getString("short_name"));
			if(obj.getJsonArray("types").getString(0).equals("postal_code"))
				record.put(POSTAL_CODE,obj.getString("short_name"));
		}
		Object[] output = new Object[4];
		output[0] = loc.getString("formatted_address");
		JsonObject geo = loc.getJsonObject("geometry");
		Object[] inter = loc.getJsonArray("types").toArray();
		String[] types = new String[inter.length];
		int counter = 0;
		for(Object holder: inter)
			types[counter++] = ((JsonValue)(holder)).toString();
		output[1] = types;
		JsonObject lal = geo.getJsonObject("location");
		output[2] = lal.getJsonNumber("lat").doubleValue() ;
		output[3] = lal.getJsonNumber("lng").doubleValue();
		return output;
	}
	
	/**
	 * A helper method that parses an irregular JSON File of parallel none-nested JSON Objects into individual JSON Objects, which are subsequently returned. 
	 * @param text The irregular JSON File to be parsed.
	 * @return An array of valid JSON Objects. 
	 * @throws IOException
	 */
	public static JsonObject[] parseIrreJsonFile(File text) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(text));
		int counter1 = 0, counter2 = 0;
		List<StringReader> reds = new ArrayList<StringReader>();
		StringBuilder builder = new StringBuilder();
		String line;
		Pattern test = Pattern.compile("[\\w{}\\[\\]]");
		while((line=reader.readLine())!=null)
		{
			Matcher mat = test.matcher(line);
			if(!mat.find())
				continue;
			if(line.contains("{"))
				counter1++;
			else if(line.contains("}"))
				counter2++;
			builder.append(line);
			if(counter1==counter2)
			{
				reds.add(new StringReader(builder.toString()));
				builder = new StringBuilder();
				counter1 = 0;
				counter2 = 0;
			}
		}
		reader.close();
		JsonObject[] objs = new JsonObject[reds.size()];
		int counter = 0;
		for(StringReader holder: reds)
			objs[counter++] = Json.createReader(holder).readObject();
		return objs;
	}
	
	public static void main(String[] args) throws IOException 
	{

	}

}
