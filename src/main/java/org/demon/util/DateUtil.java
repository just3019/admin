package org.demon.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Description:
 * Date: 2016/10/13 19:56
 *
 * @author Sean.xie
 */

public class DateUtil {
    public static final String PATTERN_YYYY_MM_DD_HH_MM_00 = "yyyy-MM-dd HH:mm:00";
    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_CHINESE = "yyyy-MM-dd";
    public static final String PATTERN_CHINESE_DOT = "yyyy.MM.dd";
    public static final String PATTERN_CHINESE_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YMDHMS_ZZZ = "yyyy-MM-dd HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final String PATTERN_MMDD = "MM/dd";
    private static final Collection DEFAULT_PATTERNS = Arrays.asList(
            PATTERN_YMDHMS, PATTERN_CHINESE, PATTERN_YYYYMMDD, PATTERN_YYYY_MM_DD_HH_MM, PATTERN_CHINESE_SSS,
            PATTERN_YMDHMS_ZZZ, PATTERN_CHINESE_DOT, PATTERN_ASCTIME, PATTERN_RFC1036, PATTERN_RFC1123);
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 0, 1, 0, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    private DateUtil() {
    }

    /**
     * 当前时间
     */
    public static DateTime now() {
        return DateTime.now();
    }


    /**
     * 当前时间
     */
    public static String now_YMDHMS() {
        return DateTime.now().toString(PATTERN_YMDHMS, Locale.CHINA);
    }

    /**
     * 转化Date
     */
    public static Date parseDate(String dateValue) {
        return parseDate(dateValue, null, null);
    }

    /**
     * 转化Date
     */
    public static Date parseDate(String dateValue, Collection dateFormats) {
        return parseDate(dateValue, dateFormats, null);
    }

    /**
     * 转化Date
     */
    public static Date parseDate(String dateValue, Collection dateFormats, Date startDate) {
        if (dateValue == null) {
            throw new IllegalArgumentException("dateValue is null");
        } else {
            if (dateFormats == null) {
                dateFormats = DEFAULT_PATTERNS;
            }

            if (startDate == null) {
                startDate = DEFAULT_TWO_DIGIT_YEAR_START;
            }

            if (dateValue.length() > 1 && dateValue.startsWith("\'") && dateValue.endsWith("\'")) {
                dateValue = dateValue.substring(1, dateValue.length() - 1);
            }

            SimpleDateFormat dateParser = null;
            Iterator formatIter = dateFormats.iterator();

            while (formatIter.hasNext()) {
                String format = (String) formatIter.next();
                if (dateParser == null) {
                    dateParser = new SimpleDateFormat(format, Locale.US);
                    dateParser.setTimeZone(TimeZone.getDefault());
                    dateParser.set2DigitYearStart(startDate);
                } else {
                    dateParser.applyPattern(format);
                }

                try {
                    return dateParser.parse(dateValue);
                } catch (ParseException e) {
                }
            }
        }
        return null;
    }

    /**
     * 格式化 Date
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_YMDHMS);
    }

    /**
     * 格式化 Date
     *
     * @param date
     * @return
     */
    public static String formatDate(Long date) {
        if (date == null) {
            return null;
        }
        return formatDate(new Date(date), PATTERN_YMDHMS);
    }

    public static String formatDate(Long date, String pattern) {
        if (date == null) {
            return null;
        }
        return formatDate(new Date(date), pattern);
    }

    /**
     * 格式化 Date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return formatDate(date, pattern, Locale.CHINA);
    }

    public static String formatDate(Date date, String pattern, Locale locale) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
            return formatter.format(date);
        }
    }

    /**
     * 获取今天日期
     *
     * @return
     */
    public static String getToday() {
        return DateTimeFormat.forPattern(PATTERN_CHINESE).print(DateTime.now());
    }

    /**
     * 格式化日期
     */
    public static String getMMDD_DayOfWeek(Date date) {
        return formatDate(date, PATTERN_MMDD) + new DateTime(date).dayOfWeek().getAsText(Locale.CHINA);
    }

}
