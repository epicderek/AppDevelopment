package pocket;
import java.util.*;

/**
 * An assembly of weaker and component classes of the larger classes.
 * @author Derek
 *
 */
public class Sub 
{
	
	public static class Time implements Comparable<Time>
	{
		public final Date date;
		private final long time;
		private final long timeOp;
		
		public Time(long time)
		{
			this.time = time;
			date = new Date(time);
			timeOp = 0;
		}
		
		public Time(long time1, long time2)
		{
			time = time1;
			date = new Date(time);
			timeOp = time2;
		}
		
		public long[] getTime()
		{
			return new long[]{time,timeOp};
		}
		
		public int compareTo(Time other)
		{
			if(time==other.time)
				return 0;
			return time<other.time?-1:1;
		}
		
		public String toString()
		{
			return String.valueOf(time)+(timeOp==0?"":String.valueOf(timeOp));
		}
		
		public int hashCode()
		{
			return (int)time;
		}
		
		public boolean equals(Object other)
		{
			return time==((Time)other).time;
		}
	}
	
	public static class Coordinate
	{
		private final double[] coor;
		
		public Coordinate(double... coor)
		{
			if(coor.length!=3)
				throw new RuntimeException("Invalid Coordinate");
			this.coor = coor;
		}
		
		public void setLati(double lati)
		{
			coor[0] = lati;
		}
		
		public void setLng(double lng)
		{
			coor[1] = lng;
		}
		
		public double[] getCoordinate()
		{
			return coor;
		}
		
		public double getLati()
		{
			return coor[0];
		}
		
		public double getLng()
		{
			return coor[1];
		}
		
		public int hashCode()
		{
			return (int)((int)(coor[0]*Math.pow(10,3))*Math.pow(10,5))+(int)(coor[1]*Math.pow(10,3));
		}
		
		public boolean equals(Object other)
		{
			Coordinate cor = (Coordinate)(other);
			if(Math.abs(cor.coor[0]-coor[0])<=0.0001&&Math.abs(cor.coor[1]-coor[1])<=0.0001)
				return true;
			return false;
		}
	}
	
	public static class Zip
	{
		private final int zip;
		
		public Zip(Coordinate coor)
		{
			this(getZipCode(coor.getCoordinate()));
		}
		
		public Zip(int zip)
		{
			this.zip = zip;
		}
		
		private static int getZipCode(double[] coor)
		{
			return 0;
		}
		
		public int getZipCode()
		{
			return zip;
		}
		
		public String getRegionName()
		{
			return "";
		}
		
	}
	
	public static class Region
	{
		private final String city;
		
		public Region(String city)
		{
			this.city = city;
		}
		
		public Region(Zip zip)
		{
			this(zip.getRegionName());
		}
		
		public String toString()
		{
			return city;
		}
	}
	
	public static class Description
	{
		private Set<String> des = new HashSet<String>();
		
		public Description(String des)
		{
			this.des.add(des);
		}
		
		public void add(Description des)
		{
			this.des.addAll(des.des);
		}
		
		public boolean match(Description other)
		{
			for(String holder: other.des)
				if(des.contains(holder))
					return true;
			return false;
		}
		
		public String toString()
		{
			return des.size()==1?des.iterator().next():des.toString();
		}
	}
	
	public static <T> void printArray(Collection<T> arr)
	{
		for(T holder: arr)
			System.out.println(holder+"\n");
		System.out.println("------------------------------------------------------------------------");
	}
	
	public static void printArray(Object... objs)
	{
		for(Object holder: objs)
			System.out.println(holder+"\n");
		System.out.println("-----------------------------------------------------------------------");
	}

	
	public static void main(String[] args) 
	{

	}

}
