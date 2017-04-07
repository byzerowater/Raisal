package me.fourground.raisal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YoungSoo Kim on 2016-08-10.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 날짜 포맷 유틸
 */
public class DateUtil {

    /**
     * 날짜를 형식에 맞게 변경
     *
     * @param date   날짜
     * @param format 변경 형식
     * @return 형식에 맞게 변경 된 날짜
     */
    public static String getDateFormat(Date date, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        return sdf.format(date).toLowerCase();
    }

    /**
     * 날짜를 형식에 맞게 변경
     *
     * @param date        날짜
     * @param curFormat   지금 형식
     * @param parseFormat 변경할 형식
     * @return 형식에 맞게 변경 된 날짜
     */
    public static String convertDateFormat(String date, String curFormat, String parseFormat) {
        String parseDate = null;
        if (!StringUtil.isEmpty(date)) {
            try {
                SimpleDateFormat curSdf = new SimpleDateFormat(curFormat, Locale.US);
                SimpleDateFormat parSdf = new SimpleDateFormat(parseFormat, Locale.US);
                parseDate = parSdf.format(curSdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return StringUtil.isEmpty(parseDate) ? parseDate : parseDate.toLowerCase();
    }

    /**
     * 날짜 문자열을 날짜로 변경
     *
     * @param date      날짜 문자열
     * @param curFormat 지금 형식
     * @return 변경한 날짜
     */
    public static Date parseDateFormat(String date, String curFormat) {
        Date parse = null;
        if (!StringUtil.isEmpty(date)) {
            try {
                SimpleDateFormat curSdf = new SimpleDateFormat(curFormat, Locale.US);
                parse = curSdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return parse;
    }

    /**
     * 현재 시간 기점으로 지난 시간(분) 가져오기
     *
     * @param time   비교할 시간
     * @param format 비교할 시간 형식
     * @return 지난 시간(분)
     */
    public static int getPassMinute(String time, String format) {
        //시간 설정
        int min = -1;
        if (!StringUtil.isEmpty(time)) {
            try {
                Date startDate = parseDateFormat(time, format);
                Date todayDate = new Date();

                long startTime = startDate.getTime();

                long todayTime = todayDate.getTime();

                long mills = todayTime - startTime;

                min = (int) (mills / 60000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return min;

    }

    /**
     * 오늘 기점으로 몇일 지났는지 가져오기
     *
     * @param date   비교할 날짜
     * @param format 비교할 날짜 형식
     * @return 지난 일 수
     */
    public static int getScheduleDay(String date, String format) {
        int day = -999;
        if (!StringUtil.isEmpty(date)) {
            try {
                Calendar startDate = Calendar.getInstance();
                startDate.setTime(parseDateFormat(date, format));

                Calendar todayDate = Calendar.getInstance();

                int startYear = startDate.get(Calendar.YEAR);
                int todayYear = todayDate.get(Calendar.YEAR);

                int nDiffOfDay = (startYear - todayYear) * 365;

                int startDay = startDate.get(Calendar.DAY_OF_YEAR);
                int todayDay = todayDate.get(Calendar.DAY_OF_YEAR);

                day = startDay - todayDay + nDiffOfDay;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return day;
    }
}
