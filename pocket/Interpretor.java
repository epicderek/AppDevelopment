package pocket;
import static pocket.Sub.*;
import static pocket.PhyLocation.LocationType;
import static pocket.PhyLocation.LocationType.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.json.*;

public class Interpretor 
{
	private Reader read;
	private JsonReader reader;
	private static final String uFirst = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
	private static final String uSecond = "&key=AIzaSyBP8kf07_FR9y-hUyazmerZEWgl5uBi7j4";
	
	public Interpretor(File file) throws FileNotFoundException
	{
		read = new FileReader(file);
		reader = Json.createReader(read);
	}
	
	public void set(File other) throws FileNotFoundException
	{
		read = new FileReader(other);
		reader = Json.createReader(read);
	}
	
	public Event[] interpretNote()
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Event[] eves = new Event[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			StringBuilder builder = new StringBuilder();
			builder.append(!obj.containsKey("category")?"":obj.getString("category"));
			boolean isMsg = obj.getString("category").equals("msg");
			builder.append(" ");
			builder.append(obj.getString("action"));
			builder.append("\n\"");
			builder.append(obj.containsKey("notification_text")?obj.getString("notification_text"):"");
			builder.append("\"\nby ");
			builder.append(obj.getString("notification_title"));
			builder.append("\nin ");
			Description des = new Description(builder.toString());
			Time time = new Time(obj.getInt("time_created"));
			if(isMsg)
				eves[counter++] = new Message(time,new Profile(obj.getString("notification_title")),new Profile("User"),obj.getString("notification_text"),AbsLocation.processAbsLocation(obj.getString("package_name"),time));
			else
				eves[counter++] = new Event(time,AbsLocation.processAbsLocation(obj.getString("package_name"),time),des);
		}
		reader.close();
		return eves;
	}
	
	public Query[] interpretBro()
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped_items");
		Query[] ques = new Query[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			String des = obj.getString("text");
			Time time = new Time(obj.getInt("time_created"));
			Query que = new Query(time,des,obj.getString("browser_name"));
			ques[counter++] = que;
		}
		reader.close();
		return ques;
	}
	
	public Query[] interetVis()
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
			Query que = new Query(time,String.format("Browser Visit\n%s",title),source);
			ques[counter++] = que;
		}
		return ques;
	}
	
	public Profile[] interpretLog()
	{
		JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("contact_list");
		Profile[] pros = new Profile[arr.size()];
		int counter = 0;
		for(JsonValue holder: arr)
		{
			JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
			Profile pro = new Profile(obj.getString("name"));
			JsonArray ref;
			if((ref=obj.getJsonArray("phone_numbers")).size()!=0)
				pro.addDial(ref.getString(0));
			if((ref=obj.getJsonArray("emails")).size()!=0)
				pro.addEmail(ref.getString(0));
			pros[counter++] = pro;
		}
		return pros;
	}
	
	
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
	
	public static Object[] interpretLoc(Double lati, Double lon, Map<LocationType,String> record) throws IOException
	{
		URL url = new URL(String.format("%s%s,%s%s",uFirst,lati,lon,uSecond));
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
		System.out.println(resp);
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
	
	public static void main(String[] args) throws IOException 
	{
		interpretLoc(40.4618536,-79.9313251,new HashMap<LocationType,String>());
	}

}
