import time
from datetime import datetime, date
#If the year has or not 1 week more
def days(y): return 371 if y==round(round(y*1.242189/7)*7/1.242189) else 364
#Milliseconds from the counting to the glorious change of millennium: 01/01/2000.
#The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
ts=datetime(2000, 1, 1).timestamp()
f=True
class ideal:
	def __init__(self, g=None):
		self._D=0#Days since the change of millennium
		self._y=2000#Year initiating in 2000
		self._d=6#Day of the year counting since 0 (Sunday), 6 is Saturday, day the change of millennium was.
		self.d=6+int(((time.time() if g is None else g)-ts)/86400)#From Gregorian to ideal calendar
	#Choose among year, month, week, day of the year/month/week.
	#Day, week and month start at 0 internally and here 1 is added.
	@property
	def y(self): return self._y
	@property
	def m(self): return int(self._d/28)+1
	@property
	def w(self): return int(self._d/7)+1
	@property
	def d(self): return self._d
	@property
	def dm(self): return self._d%28+1
	@property
	def dw(self): return self._d%7+1
	@property
	def D(self): return self._D
	#Ideal calendar to Gregorian from milliseconds.
	@property
	def gregorian(self): return date.fromtimestamp(self._D*86400+ts)
	#If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
	@d.setter
	def d(self,n):
		if not isinstance(n, int): return NotImplemented
		self._D+=n-self._d
		self._d=n
		N=days(self._y)
		while self._d>=N:
			self._d-=N
			self._y+=1
			N=days(self._y)
		while self._d<0:
			self._y-=1
			self._d+=days(self._y)
	@y.setter
	def y(self,n):
		if not isinstance(n, int): return NotImplemented
		if self._d>363:
			if days(n)==364:
				self._d-=7
				self._D-=7
		while self._y<n:
			self._D+=days(self._y)
			self._y+=1
		while self._y>n:
			self._y-=1
			self._D-=days(self._y)
	def __eq__(self, o):
		if self==o: return True
		if isinstance(o, int): return self._D==o
		if isinstance(o, ideal): return self._D==o.D
		return False
	def __le__(self, o):
		if self is o: return True
		if isinstance(o, int): return self._D<=o
		if isinstance(o, ideal): return self._D<=o.D
		return False
	def __lt__(self, o):
		if self==o: return True
		if isinstance(o, int): return self._D<o
		if isinstance(o, ideal): return self._D<o.D
		return False
	def __ge__(self, o):
		if self==o: return True
		if isinstance(o, int): return self._D>=o
		if isinstance(o, ideal): return self._D>=o.D
		return False
	def __gt__(self, o):
		if self==o: return True
		if isinstance(o, int): return self._D>o
		if isinstance(o, ideal): return self._D>o.D
		return False
	#Day of the year, day (1 to 28) and month or day (1 to 7) and week and the year
	def __str__(self): return (str(self.d+1) if not isinstance(f, bool) else str(self.dm if f else self.dw)+" "+str(self.m if f else self.w))+" "+str(self.y)
print(ideal())#Now