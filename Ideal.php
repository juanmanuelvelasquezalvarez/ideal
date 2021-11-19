<?php
//If the year has or not 1 week more
function days(int $y) {
	return $y==round(round($y*1.242189/7)*7/1.242189)?371:364;
}
$b=true;
class Ideal {
	private $D;//Days since the change of millennium initiating in 0.
	private $_y=2000;//Year initiating in 2000
	private $_d=6;//Day of the year counting since 0 (Sunday), 6 is Saturday, day that the change of millennium was.
	//The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
	public function __construct($g=null) {
		$this->d=6+floor((($g==null?date("U"):$g)-date("U",mktime(0,0,0,1,1,2000)))/86400);
	}
	//Choose among year, month, week, day of the year/month/week.
	//Week and month start at 0 internally and here 1 is added.
	public function __get($f) {
		switch($f) {
			case 'y': return $this->_y;
			case 'm': return floor($this->_d/28)+1;
			case 'w': return floor($this->_d/7)+1;
			case 'd': return $this->_d;
			case 'D': return $this->D;
			case 'dm': return $this->_d%28+1;
			case 'dw': return $this->_d%7+1;
			//Ideal calendar to Gregorian.
			case 'g':
				return date_add(date_create("2000-01-01"),date_interval_create_from_date_string($this->D.' days'));
		}
		return 0;
	}
	//If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
	public function __set($n,$v) {
		if($n=='d') {
			$this->D+=$v-$this->_d;
			$this->_d=$v;
			$N=days($this->_y);
			while($this->_d>=$N) {
				$this->_d-=$N;
				$N=days(++$this->_y);
			}
			while($this->_d<0) $this->_d+=days(--$this->_y);
		}
		else if($n=='y') {
			if($this->_d>363) if(days($v)==364) {
				$this->_d-=7;
				$this->D-=7;
			}
			while($this->_y<$v) $this->D+=days($this->_y++);
			while($this->_y>$v) $this->D-=days(--$this->_y);
		}
	}
	public function __toString() {
		return (($b=true|$b=false)?(($b?$this->dm:$this->dw)." ".($b?$this->m:$this->w)):$this->d+1)." ".$this->y;
	}
}
echo new Ideal();//Now