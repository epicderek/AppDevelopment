package pocket;
import static pocket.PhyLocation.LocationType.*;
import static pocket.Sub.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Tester 
{

	/**
	 * Run the Location files.
	 * @param pat The path of the file folder.
	 * @throws IOException
	 */
	public static void runLoc(File fol) throws IOException
	{
		File[] files = fol.listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLoc(true);
		}
	}
	
	/**
	 * Run the Notification or Device Event files.
	 * @param pat The path of the folder containing the files.
	 * @throws IOException
	 */
	public static void runNote(File fol) throws IOException
	{
		File[] files = fol.listFiles();
		Interpretor inte = new Interpretor();
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
	 * @param pat The path of the files.
	 * @throws IOException
	 */
	public static void runMess(File fol) throws IOException
	{
		File[] files = fol.listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretNote(true,false);
		}
	}
	
	/**
	 * Run the Contact files.
	 * @param path The path of the files.
	 * @throws IOException
	 */
	public static void runLog(File fol) throws IOException
	{
		File[] files = fol.listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLog(true);
		}
	}
	
	/**
	 * Run the browser search and browser query files.
	 * @param pat The path of the files.
	 * @throws IOException
	 */
	public static void runBro(File fol) throws IOException
	{
		File[] files = fol.listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			if(holder.getName().contains("BrowserSearch"))
				inte.interpretBro(true);
			else
				inte.interpretVis(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void runFile(Path pat) throws IOException
	{
		File person = pat.toFile();
		File[] levels = person.listFiles();
		PrintWriter wri = new PrintWriter(person.getName()+".csv");
		for(File holder: levels)
			if(holder.getName().contains("Log"))
				runLog(holder);
			else if(holder.getName().contains("Notification"))
				runNote(holder);
			else if(holder.getName().contains("Device"))
				runNote(holder);
			else if(holder.getName().contains("Browser"))
				runBro(holder);
			else if(holder.getName().contains("Location"))
				System.out.println();
		wri.write(String.format("Inputted Profile Information %d pieces%s",Interpretor.countP,"\n"));
		wri.write(String.format("Inputted Message Information %d pieces%s",Interpretor.countM,"\n"));
		wri.write(String.format("Inputted Notification Information %d pieces%s",Interpretor.countN,"\n"));
		wri.write(String.format("Inputted Device Event Information %d pieces%s",Interpretor.countE,"\n"));
		wri.write(String.format("Inputted Browsing Information %d pieces%s",Interpretor.countQ,"\n"));
		wri.write(String.format("Inputted Location Information %d pieces%s",Interpretor.countL,"\n"));
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
		Message holder1 = mess.get(0);
		wri.write(String.format("%s,%s,%s,%s,%s%s",String.format("%s %s %s", holder1.getFrom().getName(),holder1.getFrom().getDial(),holder1.getFrom().getEmail()),"User",holder1.getTime().date,holder1.getContent(),holder1.getLocation(),"\n"));
		for(int i=1; i<mess.size(); i++)
		{
			holder1 = mess.get(i);
			wri.write(String.format("%s,%s,%s,%s,%s%s",String.format("%s %s %s",holder1.getFrom().getName(),holder1.getFrom().getDial(),holder1.getFrom().getEmail()),"User",holder1.getTime().date,holder1.getContent(),holder1.getLocation(),"\n"));
			Date one = holder1.getTime().date;
			Date two = mess.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			else
				counter++;
		}
		wri.write(String.valueOf(mess.size()/counter));
		wri.write(" per day\n");
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s%s","Description","Time","Source","\n"));
		counter = 1;
		List<Event> eves = new ArrayList<Event>(Event.eves.keySet());
		Collections.sort(eves,Event.comp);
		Event holder2 = eves.get(0);
		wri.write(String.format("%s,%s,%s%s",holder2.des.toString().replaceAll("\n",""),holder2.getTime().date,((AbsLocation)holder2.getLocation()).getSource(),"\n"));
		for(int i=1; i<eves.size(); i++)
		{
			holder2 = eves.get(i);
			wri.write(String.format("%s,%s,%s%s",holder2.des.toString().replaceAll("\n",""),holder2.getTime().date,((AbsLocation)holder2.getLocation()).getSource(),"\n"));
			Date one = holder2.getTime().date;
			Date two = eves.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			else
				counter++;
		}
		wri.write(String.valueOf(eves.size()/counter));
		wri.write(" per day\n");
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s%s","Query","Time","Source","\n"));
		counter = 1;
		List<Query> ques = new ArrayList<Query>(Query.ques.keySet());
		Collections.sort(ques,Event.comp);
		Query holder3 = ques.get(0);
		wri.write(String.format("%s,%s,%s%s",holder3.getQuery(),holder3.getTime().date,holder3.getLocation(),"\n"));
		for(int i=1; i<ques.size(); i++)
		{
			holder3 = ques.get(i);
			wri.write(String.format("%s,%s,%s%s",holder3.getQuery(),holder3.getTime().date,holder3.getLocation(),"\n"));
			Date one = holder3.getTime().date;
			Date two = ques.get(i-1).getTime().date;
			if(one.getDate()==two.getDate()&&one.getMonth()==two.getMonth()&&one.getYear()==two.getYear())
				continue;
			else
				counter++;
		}
		wri.write(String.valueOf(ques.size()/counter));
		wri.write(" per day\n");
		wri.write("\n\n");
		wri.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE,"Facility Type","\n"));
		for(PhyLocation holder: PhyLocation.locations.values())
			wri.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",holder.getInfo(STREET_NUM),holder.getInfo(ROUTE),holder.getInfo(NEIGHBORHOOD),holder.getInfo(LOCALITY),holder.getInfo(ADMINISTRATIVE1),holder.getInfo(ADMINISTRATIVE2),holder.getInfo(COUNTRY),holder.getInfo(POSTAL_CODE),Arrays.toString(holder.getTypes()),"\n"));
		wri.write("\n\n");
		wri.close();
	}
	
	public static void main(String[] args) throws IOException 
	{
		runFile(Paths.get("C:\\PocketData\\P00"));
	}

}
