/*Milliseconds from the counting (1st second of 1970 Greenwich)
to the glorious change of millennium: 01/01/2000.
In the class Date, month starts at 0*/
var m=new Date(2000,0,1,0,0,0,0).getTime();
//If the year has or not 1 week more
function days(y) {
	return y==Math.round(Math.round(y*1.242189/7)*7/1.242189)?371:364;
}
class Ideal {
	//The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
	constructor(g) {//From Gregorian to ideal calendar
		this.D=0;//Days since the change of millennium initiating in 0.
		this.y=2000;//Year initiating in 2000
		this.d=6;//Day of the year counting since 0 (Sunday), 6 is Saturday, day that the change of millennium was.
		if((typeof g)!="number" & (typeof g)!="Date") g=new Date().getTime()
		this.setD(6+Math.floor((((typeof g)=="Date"?g.getTime():g)-m)/86400000));
	}
	//If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
	setD(n) {
		this.D+=n-this.d;
		this.d=n;
		var N=days(this.y);
		while(this.d>=N) {
			this.d-=N;
			N=days(++this.y);
		}
		while(this.d<0) this.d+=days(--this.y);
	}
	setY(n) {
		if(d>363) if(days(n)==364) {
			d-=7;D-=7;
		}
		while(this.y<n) this.D+=days(this.y++);
		while(this.y>n) this.D-=days(--this.y);
	}
	//Choose among year, month, week, day of the year/month/week.
	//Day, week and month start at 0 internally and here 1 is added.
	//Day of the year, day (1 to 28) and month or day (1 to 7) and week and the year.
	//Ideal calendar to Gregorian from milliseconds.
	get(f) {
		return f<1|f>11?0:f==1?this.y:f<7?(f<4?Math.floor(this.d/(f==2?28:7)):f==4?this.d:this.d%(f==5?28:7))+1:f==7?this.D:
			f==8?new Date(this.D*86400000+m):(f==9?this.d+1:this.get(f-5)+" "+this.get(f-8))+" "+this.y;
	}
}
document.getElementsByTagName("p")[0].innerHTML = new Ideal().get(10);//Now