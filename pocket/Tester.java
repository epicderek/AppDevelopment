package pocket;
import static pocket.PhyLocation.LocationType.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;



public class Tester 
{

	public static void runLoc(Path pat) throws IOException
	{
		PrintWriter write = new PrintWriter("LocationStatistics(1).csv");
		File[] files = pat.toFile().listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLoc(true);
		}
		Collection<PhyLocation> locs = PhyLocation.locations.values();
		System.out.println(String.format("Inputted Location Information %d pieces",Interpretor.count));
		System.out.println(String.format("Outputted PhyLocaiton Objects %d pieces",locs.size()));
		System.out.println("Difference is "+(Interpretor.count-locs.size()));
		write.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE,"Facility Type","\n"));
		for(PhyLocation holder: locs)
			write.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%s",holder.getInfo(STREET_NUM),holder.getInfo(ROUTE),holder.getInfo(NEIGHBORHOOD),holder.getInfo(LOCALITY),holder.getInfo(ADMINISTRATIVE1),holder.getInfo(ADMINISTRATIVE2),holder.getInfo(COUNTRY),holder.getInfo(POSTAL_CODE),Arrays.toString(holder.getTypes()),"\n"));
		write.close();
	}
	
	public static void runEvent(Path pat) throws IOException
	{
		File[] files = pat.toFile().listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			if(holder.getName().contains("Notification"))
				inte.interpretNote(false,true);
			else
				inte.interpretDee(true);
		}
		PrintWriter wri = new PrintWriter("EventStatistics.csv");
		wri.write(String.format("%s,%s,%s%s","Description","Time","Source","\n"));
		for(Event holder: Event.eves.values())
			wri.write(String.format("%s,%s,%s%s",holder.getDescription(),holder.getTime(),holder.getLocation(),"\n"));
		System.out.println(String.format("Inputed Event Information %d pieces",Interpretor.count));
		System.out.println(String.format("Outputed Event Objects %d pieces", Event.eves.size()));
		System.out.println("Difference is "+(Interpretor.count-Event.eves.size()));
		wri.close();
	}
	public static void runMess(Path pat) throws IOException
	{
		File[] files = pat.toFile().listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretNote(true,false);
		}
		PrintWriter wri = new PrintWriter("MessageStatistics.csv");
		wri.write(String.format("%s,%s,%s,%s,%s%s","From","To","Time","Content","Source","\n"));
		for(Message holder: Message.mess.values())
			wri.write(String.format("%s,%s,%s,%s,%s%s",String.format("%s %s %s",holder.getFrom().getName(),holder.getFrom().getDial(),holder.getFrom().getEmail()),"User",holder.getTime(),holder.getContent(),holder.getLocation(),"\n"));
		System.out.println(String.format("Inputed Message Information %d pieces",Interpretor.count));
		System.out.println(String.format("Outputed Message Objects %d pieces", Message.mess.size()));
		System.out.println("Difference is "+(Interpretor.count-Message.mess.size()));
		wri.close();
	}
	
	public static void runLog(Path path) throws IOException
	{
		File[] files = path.toFile().listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			inte.interpretLog(true);
		}
		PrintWriter wri = new PrintWriter("ProfileStatistics.csv");
		wri.write(String.format("%s,%s,%s%s","Name","Dial","Email","\n"));
		for(Profile holder: Profile.pros.values())
			wri.write(String.format("%s,%s,%s%s",holder.getName(),holder.getDial(),holder.getEmail(),"\n"));
		System.out.println(String.format("Inputed Profile Information %d pieces",Interpretor.count));
		System.out.println(String.format("Outputed Profile Objects %d pieces",Profile.pros.size()));
		System.out.println("Difference is "+(Interpretor.count-Profile.pros.size()));
		wri.close();
	}
	
	public static void runBro(Path pat) throws IOException
	{
		File[] files = pat.toFile().listFiles();
		Interpretor inte = new Interpretor();
		for(File holder: files)
		{
			inte.set(holder);
			if(holder.getName().contains("BrowserSearch"))
				inte.interpretBro(true);
			else
				inte.interpretVis(true);
		}
		PrintWriter wri = new PrintWriter("QueryStatistics.csv");
		wri.write(String.format("%s,%s,%s%s","Query","Time","Source","\n"));
		for(Query holder: Query.ques.values())
			wri.write(String.format("%s,%s,%s%s",holder.getQuery(),holder.getTime(),holder.getLocation(),"\n"));
		System.out.println(String.format("Inputted Browser Search Information %d pieces",Interpretor.count));
		System.out.println(String.format("Outputted Query Objects %d pieces", Query.ques.size()));
		System.out.println("Difference is "+(Interpretor.count-Query.ques.size()));
		wri.close();
	}
	
	public static void main(String[] args) throws IOException 
	{
		runLoc(Paths.get("C:\\PocketData\\P04\\Location"));
//		runEvent(Paths.get("C:\\PocketData\\P00\\DeviceEvent"));
	}

}
