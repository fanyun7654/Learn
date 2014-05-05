package com.chen.time;

import java.util.Calendar;

public class CalendarTime {
	
	
	public static void main(String[] args){
		Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_YEAR);
		Calendar start = Calendar.getInstance();
        Calendar stop = Calendar.getInstance();
        start.set(2014, 3, 28, 00, 00, 00);
        stop.set(2014, 4, 9, 23, 59, 59);
        System.out.println(start.getTime());
        System.out.println(stop.getTime());
        System.out.println(now.getTime());
	}
}
