package pocket;
import static pocket.Sub.*;

public interface Location 
{
	void appendEvents(Event... eve);
	void appendTimes(Time... times);
}
