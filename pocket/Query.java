package pocket;
import static pocket.Sub.*;

public class Query extends Event
{
	private String que;
	private AbsLocation source;
	
	public Query(Time time, String query, String sor)
	{
		super(time);
		que = query;
		source = AbsLocation.processAbsLocation(sor, time);
	}
	
	public AbsLocation getSource()
	{
		return source;
	}
	
	public String getQuery()
	{
		return que;
	}
	
	public String toString()
	{
		return String.format("Query at time %s\nin %s\nof: %s",time,source,que);
	}
	
	public static void main(String[] args) 
	{

	}

}
