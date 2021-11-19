package jmav.ideal
import java.util.*
import kotlin.math.roundToInt
/*Milliseconds from the counting (1st second of 1970 Greenwich)
  to the glorious change of millennium: 01/01/2000.
  In the class Date, month starts at 0 and year=1900+parameter*/
val t:Long=Date(100,0,1).time
//If the year has or not 1 week more
fun days(y:Int):Int = if(y==((y*1.242189/7).roundToInt()*7/1.242189).roundToInt()) 371 else 364
var f:Boolean?=true;
class Ideal : Comparable<Ideal> {
    private var _y: Int = 2000//Year initiating in 2000
    private var D: Int = 0//Days since the change of millennium initiating in 0.
    private var _d: Int = 6
    //Day of the year counting since 0 (Sunday), 6 is Saturday, day that the change of millennium was.
    //The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
    constructor(g: Date) {
        d=6+((g.time-t)/86400000).toInt()
    }
    constructor(Y: Int, D: Int) {
        y=Y
        d=D
    }
    //If d>days(y), days pass to some posterior year. If d<1, days pass to some previous year.
    var d: Int
        get() = _d
        set(n) {
            D+=n-_d
            _d=n
            var a=days(_y)
            while(_d>=a) {
                _d-=a
                a=days(++_y)
            }
            while(_d<0) _d+=days(--_y)
        }
    var y: Int
        get() = _y
        set(n) {
            if(_d>363) if(days(n)==364) {
                _d-=7
                D-=7
            }
            while(_y<n) D+=days(_y++)
            while(_y>n) D-=days(--_y)
        }
    //Day, week and month start at 0 internally and here 1 is added.
    val m: Int get()=d/28+1
    val w: Int get()=d/7+1
    val dm: Int get()=d%28+1
    val dw: Int get()=d%7+1
    val day: Int get()=D
    //Ideal calendar to Gregorian from milliseconds.
    val g: Long get()=(D.toLong())*86400000+t
    val gregorian: Date get()=Date(g)
    //Day of the year, day (1 to 28) and month or day (1 to 7) and week and the year
    override fun toString(): String {
        var s=StringBuffer()
        if(f==null) s.append(d+1)
        else s.append(if(f as Boolean) dm else dw).append(' ').append(if(f as Boolean) m else w)
        return s.append(' ').append(y).toString()
    }
    override fun equals(o: Any?): Boolean {
		if(this==o) return true
		if(o is Int) return D==o
		if(o is Ideal) return D==o.D
		return false
	}
    override fun compareTo(o: Ideal): Int = D-o.D
}
fun main() {
    print(Ideal(Date()))
}