package cn.hd.test.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_FULL_STANDARD = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_FULL_ONLYNUM = "yyyyMMddHHmmss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_FULL_ONLYNUM_MS = "yyyyMMddHHmmssSSS";
    /**
     * yyyyMMddHHmm
     */
    public static final String FORMAT_MINUTE_ONLYNUM ="yyyyMMddHHmm";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE_STANDARD = "yyyy-MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String FORMAT_DATA_ONLYNUM = "yyyyMMdd";
    /**
     * HH:mm:ss
     */
    public static final String FORMAT_TIME_STANDARD = "HH:mm:ss";
    /**
     * HHmmss
     */
    public static final String FORMAT_TIME_ONLYNUM = "HHmmss";

    public static void main(String[] args) {
        System.out.println(strFormatToDate("2011-01-01", FORMAT_DATE_STANDARD));

        System.out.println(addTimeToDate(new Date(), Calendar.MINUTE, 15));

        String timeFrom = "2017-02-01";
        String timeTo = "2017-02-08";


        Date startDate = strFormatToDate(timeFrom, FORMAT_DATE_STANDARD);
        Date endDate = strFormatToDate(timeTo, FORMAT_DATE_STANDARD);

        System.out.println(startDate.before(startDate));

        while (startDate.compareTo(endDate) <= 0) {
             System.out.println(dateFormatToStr(startDate, FORMAT_DATE_STANDARD));
             startDate = addTimeToDate(startDate, Calendar.DAY_OF_MONTH, 1);
        }
    }

    public static Date strFormatToDate(String str, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(str);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException("格式异常");
        }
    }

    public static String dateFormatToStr(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date addTimeToDate(Date date, int calendarField, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, n);
        return c.getTime();
    }
}
