package pocket;
import static pocket.PhyLocation.LocationType.*;
import static pocket.Sub.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * A tester that provides convenient testing for processing specific package of files contained in corresponding folders.
 * @author Derek
 *
 */
public class Tester 
{

	/**
	 * Run the Location files.
	 * @param fol The file folder for Location files.
	 * @throws IOException
	 */
	public static void runLoc(File fol, Interpretor inte) throws IOException
	{
		File[] files = fol.listFiles();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLoc(true);
		}
	}
	
	/**
	 * Run the Notification or Device Event files.
	 * @param fol The folder of the Notification files.
	 * @throws IOException
	 */
	public static void runNote(File fol, Interpretor inte) throws IOException
	{
		File[] files = fol.listFiles();
		for(File holder: files)
		{
			inte.set(holder);
			if(holder.getName().contains("Notification"))
				inte.interpretNote(true,true);
			else
				inte.interpretDee(true);
		}
	}
	
	/**
	 * Run the Message files.
	 * @param fol The folder of the Notification files.
	 * @throws IOException
	 */
	public static void runMess(File fol, Interpretor inte) throws IOException
	{
		File[] files = fol.listFiles();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretNote(true,false);
		}
	}
	
	/**
	 * Run the Contact files.
	 * @param fol The folder of the Log files.
	 * @throws IOException
	 */
	public static void runLog(File fol, Interpretor inte) throws IOException
	{
		File[] files = fol.listFiles();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLog(true);
		}
	}
	
	/**
	 * Run the browser search and browser query files.
	 * @param fol The folder of the browser search and browser visits files.
	 * @throws IOException
	 */
	public static void runBro(File fol, Interpretor inte) throws IOException
	{
		File[] files = fol.listFiles();
		for(File holder: files)
		{
			inte.set(holder);
			if(holder.getName().contains("BrowserSearch"))
				inte.interpretBro(true);
			else
				inte.interpretVis(true);
		}
	}
	
	/**
	 * Process all personal data contained in the given folder the path points to, store as native objects, and output as a csv file containing the statistics. Consult the readme for statistics for the structure of the statistcis.
	 * @param pat The path the folder of personal data contained in separate folders of names Log, DeviceEvent, Browser, Location, Notification, in which the according JSON files are contained.
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void runFile(Path pat) throws IOException
	{
		File person = pat.toFile();
		File[] levels = person.listFiles();
		PrintWriter wri = new PrintWriter(String.format("%s%s%s","C:\\Users\\Maggie\\git\\AppDevelopment\\Statistics\\",person.getName(),".csv"));
		Interpretor inte = new Interpretor();
		for(File holder: levels)
			if(holder.getName().contains("Log"))
				runLog(holder,inte);
			else if(holder.getName().contains("Notification"))
				runNote(holder,inte);
			else if(holder.getName().contains("Device"))
				runNote(holder,inte);
			else if(holder.getName().contains("Browser"))
				runBro(holder,inte);
			else if(holder.getName().contains("Location"))
				runLoc(holder,inte);
		wri.write(String.format("Inputted Profile Information %d pieces%s",inte.countP,"\n"));
		wri.write(String.format("Inputted Message Information %d pieces%s",inte.countM,"\n"));
		wri.write(String.format("Inputted Notification Information %d pieces%s",inte.countN,"\n"));
		wri.write(String.format("Inputted Device Event Information %d pieces%s",inte.countE,"\n"));
		wri.write(String.format("Inputted Browsing Information %d pieces%s",inte.countQ,"\n"));
		wri.write(String.format("Inputted Location Information %d pieces%s",inte.countL,"\n"));
		wri.write("\n\n");
		wri.write(String.format("Stored Profile Objects %d pieces%s",Profile.pros.size(),"\n"));
		wri.write(String.format("Stored AbsLocation Objects %d pieces%s",AbsLocation.locations.size(),"\n"));
		wri.write(String.format("Stored Message Objects %d pieces%s", Message.mess.size(),"\n"));
		wri.write(String.format("Stored Event Objects %d pieces%s", Event.eves.size(),"\n"));
		wri.write(String.format("Stored Query Objects %d pieces%s", Query.ques.size(),"\n"));
		wri.write(String.format("Stored PhyLocaiton Objects %d pieces%s",PhyLocation.locations.size(),"\n"));
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s%s","Name","Dial","Email","\n"));
		for(Profile holder: Profile.pros.values())
			wri.write(String.format("%s,%s,%s%s",holder.getName(),holder.getDial(),holder.getEmail(),"\n"));
		wri.write("\n\n");
		wri.write(String.format("%s%s","Source","\n"));
		for(AbsLocation holder: AbsLocation.locations.values())
			wri.write(String.format("%s%s",holder.getSource(),"\n"));
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s,%s,%s%s","From","To","Time","Content","Source","\n"));
		int counter = 1;
		List<Message> mess = new ArrayList<Message>(Message.mess.keySet());
		Collections.sort(mess,Event.comp);
		Message holder1 = mess.size()!=0?mess.get(0):null;
		wri.write(holder1!=null?String.format("%s,%s,%s,%s,%s%s",String.format("%s %s %s", holder1.getFrom().getName(),holder1.getFrom().getDial(),holder1.getFrom().getEmail()),"User",holder1.getTime().date,holder1.getContent(),holder1.getLocation(),"\n"):"");
		for(int i=1; i<mess.size(); i++)
		{
			holder1 = mess.get(i);
			wri.write(String.format("%s,%s,%s,%s,%s%s",String.format("%s %s %s",holder1.getFrom().getName(),holder1.getFrom().getDial(),holder1.getFrom().getEmail()),"User",holder1.getTime().date,holder1.getContent(),holder1.getLocation(),"\n"));
			Date one = holder1.getTime().date;
			Date two = mess.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			counter++;
		}
		wri.write(String.valueOf(mess.size()/counter));
		wri.write(" per day\n");
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s%s","Description","Time","Source","\n"));
		counter = 1;
		List<Event> eves = new ArrayList<Event>(Event.eves.keySet());
		Collections.sort(eves,Event.comp);
		Event holder2 = eves.size()!=0?eves.get(0):null;
		wri.write(holder2!=null?String.format("%s,%s,%s%s",holder2.des.toString().replaceAll("\n",""),holder2.getTime().date,((AbsLocation)holder2.getLocation()).getSource(),"\n"):"");
		for(int i=1; i<eves.size(); i++)
		{
			holder2 = eves.get(i);
			wri.write(String.format("%s,%s,%s%s",holder2.des.toString().replaceAll("\n",""),holder2.getTime().date,((AbsLocation)holder2.getLocation()).getSource(),"\n"));
			Date one = holder2.getTime().date;
			Date two = eves.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			counter++;
		}
		wri.write(eves.size()!=0?String.valueOf(eves.size()/counter):"");
		wri.write(" per day\n");
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s%s","Query","Time","Source","\n"));
		counter = 1;
		List<Query> ques = new ArrayList<Query>(Query.ques.keySet());
		Collections.sort(ques,Event.comp);
		Query holder3 = ques.size()!=0?ques.get(0):null;
		wri.write(holder3!=null?String.format("%s,%s,%s%s",holder3.getQuery(),holder3.getTime().date,holder3.getLocation(),"\n"):"");
		for(int i=1; i<ques.size(); i++)
		{
			holder3 = ques.get(i);
			wri.write(String.format("%s,%s,%s%s",holder3.getQuery(),holder3.getTime().date,holder3.getLocation(),"\n"));
			Date one = holder3.getTime().date;
			Date two = ques.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			counter++;
		}
		wri.write(ques.size()==0?"0":String.valueOf(ques.size()/counter));
		wri.write(" per day\n");
		wri.write("\n\n");
		List<Time> times = new ArrayList<Time>();
		counter = 0;
		wri.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE,"Facility Type","\n"));
		for(PhyLocation holder: PhyLocation.locations.values())
		{
			wri.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",holder.getInfo(STREET_NUM),holder.getInfo(ROUTE),holder.getInfo(NEIGHBORHOOD),holder.getInfo(LOCALITY),holder.getInfo(ADMINISTRATIVE1),holder.getInfo(ADMINISTRATIVE2),holder.getInfo(COUNTRY),holder.getInfo(POSTAL_CODE),Arrays.toString(holder.getTypes()),"\n"));
			counter+=holder.getTimes().size();
			times.addAll(holder.getTimes());
		}
		Collections.sort(times);
		int counter2 = 1;
		for(int i=1; i<times.size(); i++)
		{
			Date one = times.get(i-1).date;
			Date two = times.get(i).date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			counter2++;
		}
		wri.write(PhyLocation.locations.size()!=0?String.valueOf(counter/counter2):"0");
		wri.write(" pieces of advented per day\n");
		wri.write(String.valueOf(PhyLocation.locations.size()/counter2));
		wri.write(" native objects per day\n");
		wri.write("\n\n");
		wri.close();
	}
	
	public static void main(String[] args) throws IOException 
	{
		runFile(Paths.get("C:\\PocketData\\P060"));
		System.out.println(Interpretor.keyCount);
	}

}
