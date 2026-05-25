/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 * 提供常用的日期处理功能
 */
public class DateUtils {

    // 常用日期格式
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_DATETIME_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 获取当前日期
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间戳（毫秒）
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 日期格式化
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转日期
     */
    public static Date parse(String dateStr, String pattern) throws ParseException {
        if (dateStr == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    /**
     * 安全转换（不抛异常）
     */
    public static Date parseSafe(String dateStr, String pattern) {
        try {
            return parse(dateStr, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取某天的开始时间 00:00:00
     */
    public static Date getStartOfDay(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某天的结束时间 23:59:59
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取某月的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某月的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取某年的第一天
     */
    public static Date getFirstDayOfYear(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某年的最后一天
     */
    public static Date getLastDayOfYear(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 日期加减天数
     */
    public static Date addDays(Date date, int days) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 日期加减小时
     */
    public static Date addHours(Date date, int hours) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 日期加减分钟
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 日期加减月份
     */
    public static Date addMonths(Date date, int months) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 日期加减年份
     */
    public static Date addYears(Date date, int years) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 计算两个日期相差的天数
     */
    public static long diffDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * 计算两个日期相差的小时数
     */
    public static long diffHours(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * 计算两个日期相差的分钟数
     */
    public static long diffMinutes(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * 计算两个日期相差的秒数
     */
    public static long diffSeconds(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断是否为同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断是否为同一月
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    /**
     * 判断当前时间是否在指定时间范围内
     */
    public static boolean isInRange(Date date, Date startDate, Date endDate) {
        if (date == null || startDate == null || endDate == null) return false;
        return date.after(startDate) && date.before(endDate);
    }

    /**
     * 获取当前日期是星期几（1=周一, 7=周日）
     */
    public static int getDayOfWeek(Date date) {
        if (date == null) return 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        // 将周日从1改为7，周一从2改为1
        return dayOfWeek == 1 ? 7 : dayOfWeek - 1;
    }

    /**
     * 获取中文星期几
     */
    public static String getChineseDayOfWeek(Date date) {
        String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        int dayOfWeek = getDayOfWeek(date);
        return weeks[dayOfWeek - 1];
    }

    /**
     * 获取日期所属的季度
     */
    public static int getQuarter(Date date) {
        if (date == null) return 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month / 3 + 1;
    }

    /**
     * 获取两个日期之间的所有日期（包含起止日期）
     */
    public static List<Date> getDateRange(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        if (startDate == null || endDate == null) return dates;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (!cal.getTime().after(endDate)) {
            dates.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    /**
     * 获取年龄（周岁）
     */
    public static int getAge(Date birthDate) {
        if (birthDate == null) return 0;
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDate);
        int birthYear = cal.get(Calendar.YEAR);
        int birthMonth = cal.get(Calendar.MONTH);
        int birthDay = cal.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }
        return age;
    }

    /**
     * 获取一天的开始时间（用于日期比较）
     */
    public static Date getDateWithoutTime(Date date) {
        return getStartOfDay(date);
    }

    /**
     * 检查是否为周末
     */
    public static boolean isWeekend(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * 检查是否为工作日
     */
    public static boolean isWorkday(Date date) {
        return !isWeekend(date);
    }
}