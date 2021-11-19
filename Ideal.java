package a;
import java.util.Date;
@SuppressWarnings({"deprecation","preview"})
public class Ideal implements Comparable<Ideal> {
	/*Milliseconds from the counting (1st second of 1970 Greenwich)
	  to the glorious change of millennium: 01/01/2000.
	  In the class Date, month starts at 0 and year=1900+parameter*/
	private static long m=new Date(100,0,1).getTime();
	//If the year has or not 1 week more
	public static int days(int y) {
		return y==Math.round(Math.round(y*1.242189/7)*7/1.242189)?371:364;
	}
	private int D,//Days since the change of millennium initiating in 0.
		y=2000,//Year initiating in 2000
		d=6;//Day of the year counting since 0 (Sunday), 6 is Saturday, day that the change of millennium was.
	//The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
	public Ideal() {//Now
		this(System.currentTimeMillis());
	}
	//From Gregorian to ideal calendar
	public Ideal(long g) {
		setD(6+(int)((g-m)/86400000));
	}
	public Ideal(Date g) {
		this(g.getTime());
	}
	public Ideal(int y, int d) {
		setY(y);setD(d);
	}
	//If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
	public void setD(int n) {
		D+=n-d;
		d=n;
		int N=days(y);
		while(d>=N) {
			d-=N;
			N=days(++y);
		}
		while(d<0) d+=days(--y);
	}
	public void setY(int n) {
		if(d>363) if(days(n)==364) {
			d-=7;D-=7;
		}
		while(y<n) D+=days(y++);
		while(y>n) D-=days(--y);
	}
	//Choose among year, month, week, day of the year/month/week.
	//Day, week and month start at 0 internally and here 1 is added.
	public int get(int f) {
		return f<1|f>7?0:f==1?y:f==7?D:(f==2?d/28:f==3?d/7:f==4?d:f==5?d%28:d%7)+1;
	}
	//If this date is previous or posterior than another.
	public int compareTo(Ideal f) {
		return D-f.D;
	}
	public boolean equals(Object o) {
		if(this==o) return true;
		if(o instanceof Integer i) return D==i;
		if(o instanceof Ideal i) return D==i.D;
		return false;
	}
	//Day of the year, day (1 to 28) and month or day (1 to 7) and week and the year
	public static Boolean f=true;
	public String toString() {
		return (f==null?d+1:get(f?5:6)+" "+get(f?2:3))+" "+y;
	}
	//Ideal calendar to Gregorian from milliseconds.
	public long g() {
		return (long)D*86400000+m;
	}
	public Date gregorian() {
		return new Date(g());
	}
	public Ideal clone() {
		return new Ideal(y,d);
	}
	public static void main(String[] a) {
		System.out.print(new Ideal());
	}
}