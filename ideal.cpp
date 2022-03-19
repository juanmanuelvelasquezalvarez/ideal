#include <iostream>
int days(int y) {//Days of the year
	return y == round(round(y * 1.242189 / 7) * 7 / 1.242189) ? 371 : 364;
}
class Ideal {
private:
	int S;//Seconds since the start of year 0
	int y;//Years since the end of the nuclear hecatomb
	int d;//Day of the year
	int s;//Seconds of the day
public:
	Ideal(int y, int d, int s) {
		addY(y);
		addD(d);
		addS(s);
	}
	void addY(int n) {
		int v = y + n;
		if (d > 363) if (days(v) == 364) {
			d -= 7;
			S -= 604800;
		}
		while (y < v) S += days(y++)*86400;
		while (y > v) S -= days(--y)*86400;
	}
	//If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
	void addD(int n) {
		S += n*86400;
		d += n;
		int N = days(y);
		while (d >= N) {
			d -= N;
			N = days(++y);
		}
		while (d < 0) d += days(--y);
	}
	void addS(int n) {
		S += n;
		int v = s + n;
		s = v % 86400;
		addD(v / 86400);
	}
	//Choose among year, month, week, day of the year/month/week, hour, minute, second or seconds since the start of the new count.
	//Day, week and month start at 0 internally and here 1 is added.
	int get(int f) {
		return f<1||f>10?0:f==1?y:f==7?s/3600:f==8?(s%3600)/60:f==9?s%60:f==10?S:(f==2?d/28:f==3?d/7:f==4?d:f==5?d%28:d%7)+1;
	}
	//If this date is previous or posterior than another.
	int operator-(Ideal i) {
		return S - i.S;
	}
	bool operator==(Ideal i) {
		return S == i.S;
	}
};
