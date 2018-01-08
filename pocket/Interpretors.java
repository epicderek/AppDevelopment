package pocket;
import static pocket.Sub.*;
import java.util.*;
import java.io.*;
import javax.json.*;

public class Interpretors 
{
	
	public class NoteInterpretor
	{
		private Reader read;
		private JsonReader reader;
		
		public NoteInterpretor(File file) throws FileNotFoundException
		{
			read = new FileReader(file);
			reader = Json.createReader(read);
		}
		
		public void interpret()
		{
			JsonArray arr = reader.readObject().getJsonObject("itemMap").getJsonArray("grouped-items");
			for(JsonValue holder: arr)
			{
				JsonObject obj = ((JsonObject)holder).getJsonObject("itemMap");
				StringBuilder builder = new StringBuilder();
				String temp;
				builder.append((temp=obj.getString("catagory"))==null?"":temp);
				builder.append("");
				builder.append(obj.getString("action"));
				builder.append("  from__");
				builder.append(obj.getString("notification_title"));
				builder.append("");
				builder.append(obj.getString("package_name"));
				Description des = new Description(builder.toString());
				Time time = new Time(obj.getInt("time-created"));
				//Event eve = new Event(time,new PhyLocation(""));
				
			}
		}
	}
	
	public static void main(String[] args) 
	{

	}

}
