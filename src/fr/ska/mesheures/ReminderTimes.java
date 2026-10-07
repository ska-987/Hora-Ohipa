/* Copyright (C) 2026 ska_987. SPDX-License-Identifier: GPL-3.0-only */
package fr.ska.mesheures;
import java.util.*;
public final class ReminderTimes {
 public static boolean shouldNotify(int kind,boolean running,long delay){return delay>=0&&delay<=7200000&&(kind==0&&!running||kind==1&&running);}
 public static long next(long now,String time,int days,TimeZone zone){if(time==null||!time.matches("([01][0-9]|2[0-3]):[0-5][0-9]")||days==0)return -1;String[] t=time.split(":");Calendar c=Calendar.getInstance(zone);c.setTimeInMillis(now);for(int n=0;n<8;n++){Calendar v=(Calendar)c.clone();v.add(Calendar.DATE,n);v.set(Calendar.HOUR_OF_DAY,Integer.parseInt(t[0]));v.set(Calendar.MINUTE,Integer.parseInt(t[1]));v.set(Calendar.SECOND,0);v.set(Calendar.MILLISECOND,0);int day=(v.get(Calendar.DAY_OF_WEEK)+5)%7;if((days&(1<<day))!=0&&v.getTimeInMillis()>now)return v.getTimeInMillis();}return -1;}
}
