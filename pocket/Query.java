package pocket;
import static pocket.Sub.*;
import java.util.*;

/**
 * An Internet Query that contains the content of the query and the source, the url.
 * @author Maggie
 *
 */
public class Query extends Event
{
	/**
	 * The content of the query.
	 */
	private String que;
	/**
	 * The source of the query, tyhe website.
	 */
	private AbsLocation source;
	/**
	 * All the query objects created.
	 */
	static Map<Query,Query> ques = new HashMap<Query,Query>();
	
	/**
	 * A reserved constructor that does not handle possible duplicity.
	 * @param time The time the query is conducted.
	 * @param query The content of the query.
	 * @param sor The source, the url.
	 */
	private Query(Time time, String query, String sor)
	{
		super(time);
		que = query;
		source = AbsLocation.processAbsLocation(sor, time);
	}
	
	/**
	 * Create a Query object with the time, the content of the query, and the source.
	 * @param time The time the query is conducted.
	 * @param query The content of the query.
	 * @param sor The source, the url.
	 * @return The Query object representing the given information.
	 */
	public static Query processQuery(Time time, String query, String sor)
	{
		Query see = new Query(time,query,sor);
		if(ques.containsKey(see))
			return ques.get(see);
		ques.put(see,see);
		return see;
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
	
	public boolean equals(Object other)
	{
		return toString().equals(other.toString());
	}
	
	public static void main(String[] args) 
	{

	}

}
