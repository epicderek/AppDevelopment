package pocket;
import java.util.*;

public class Sub 
{
	public static class Time
	{
		private final long time;
		private final long timeOp;
		
		public Time(long time)
		{
			this.time = time;
			timeOp = 0;
		}
		
		public Time(long time1, long time2)
		{
			time = time1;
			timeOp = time2;
		}
		
		public long[] getTime()
		{
			return new long[]{time,timeOp};
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
		
		public double[] getCoordinate()
		{
			return coor;
		}
		
		public boolean equals(Object other)
		{
			return Arrays.equals(coor,((Coordinate)other).coor);
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
		private String des;
		
		public Description(String des)
		{
			this.des = des;
		}
		
		public Description(Description des)
		{
			this.des = des.des;
		}
		
		public String getDescrption()
		{
			return des;
		}
	}
	
	public static void main(String[] args) 
	{

	}

}
