package pers.chemyoo.html_unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class TimeUtils {

	private TimeUtils() {
	}

	private final static String MONTH_PATTERN = "^[1-9]\\d{3}-(0[1-9]|1[0-2])$";

	private final static String DAY_PATTERN = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
	/** 斜线 */
	private final static String INTERPUNCTION_DIAGONAL = "/";
	/** 短横线 */
	private final static String INTERPUNCTION_SHORT_LINE = "-";

	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	public static Date convertStringToDate(String strDate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertDateToString(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/** 验证月份字符串是否正确，格式: yyyy-MM 或 yyyy/MM */
	public static boolean validateMonth(String month) {
		if (month.contains(INTERPUNCTION_DIAGONAL)) {
			month = month.replace(INTERPUNCTION_DIAGONAL, INTERPUNCTION_SHORT_LINE);
		}
		return Pattern.matches(MONTH_PATTERN, month);
	}

	/** 验证日期字符串是否正确，格式: yyyy-MM-dd 或 yyyy/MM/dd */
	public static boolean validateDay(String day) {
		if (day.contains(INTERPUNCTION_DIAGONAL)) {
			day = day.replace(INTERPUNCTION_DIAGONAL, INTERPUNCTION_SHORT_LINE);
		}
		return Pattern.matches(DAY_PATTERN, day);
	}

}
