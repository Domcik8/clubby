package api.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatesHelper {

    public static boolean inThisMonth(Date startingDate){
        return isAfter(startingDate,Calendar.MONTH, -1);
    }

    public static boolean inThisYear(Date date){
        GregorianCalendar nowGc = createGregorianCalendar(new Date());
        GregorianCalendar otherGc = createGregorianCalendar(date);

        return nowGc.get(Calendar.YEAR) == otherGc.get(Calendar.YEAR);
    }

    public static boolean inPastYear(Date date){
        GregorianCalendar nowGc = createGregorianCalendar(new Date());
        GregorianCalendar otherGc = createGregorianCalendar(date);

        return otherGc.get(Calendar.YEAR) == nowGc.get(Calendar.YEAR);
    }

    public static GregorianCalendar createGregorianCalendar(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return gc;
    }

    private static boolean isAfter(Date startingDate,int field, int add){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(field, add);
        return startingDate.after(gc.getTime());
    }
}
