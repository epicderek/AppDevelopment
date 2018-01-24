package pocket;
import static pocket.Sub.*;

/**
 * A representation of either a physical location or an abstract one in the software space. 
 * @author Maggie
 *
 */
public interface Location 
{
	void appendEvents(Event... eve);
	void appendTimes(Time... times);
}
