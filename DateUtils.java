/**
 * 
 */
package com.surekam.iptv.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zmm
 * 
 *         2010-12-14
 */
public class DateUtils {

	public static final String P_YYYYMM = "yyyyMM";

	public static final String P_YYYYMMDD = "yyyyMMdd";

	public static final String P_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String P_YYYY_MM_DD_CN = "yyyy年MM月dd日";

	public static final String P_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String P_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String P_YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH时mm分";

	public static final String P_YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH时mm分ss秒";

	public static final String P_YYYY_MM_DD_HH_MM_SS_NO_DELIMITER = "yyyyMMddHHmmss";

	public static final String DAY_BEGIN_TIME = " 00:00:00";

	public static final String DAY_END_TIME = " 23:59:59";

	private static final String FORMAT_NULL_DATE = "--";

	public static String format(Date date, String pattern) {
		String s = FORMAT_NULL_DATE;
		if (date != null) {
			return format(date, pattern, s);
		}
		return s;
	}

	public static String format(Date date, String pattern, String errorFormat) {
		String s = errorFormat;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				s = sdf.format(date);
			} catch (Exception e) {
			}
		}
		return s;
	}

	public static Date parse(String dateStr, String pattern) {
		Date d = null;
		if (dateStr != null && dateStr.length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				d = sdf.parse(dateStr);
			} catch (Exception e) {
			}
		}
		return d;
	}

	/**
	 * @return
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * @return
	 */
	public static Date getCurrentDate() {
		Date now = getCurrentDateTime();
		return strToDate(dateToStr(now));
	}

	public static Date strToDate(String s) {
		Date d = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = sdf.parse(s);
		} catch (Exception e) {

		}

		return d;
	}

	public static Date strToDateHMS(String s) {
		String ss = s;
		if (s.length() == 16) {
			ss += ":00";
		}
		Date d = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = sdf.parse(ss);
		} catch (Exception e) {

		}

		return d;
	}

	//
	// public static Date strToDateHM(String s) {
	// Date d = null;
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	// try {
	// d = sdf.parse(s);
	// } catch (Exception e) {
	//
	// }
	//
	// return d;
	// }
	//
	// public static Date strToDateH(String str) {
	// Date d = null;
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	// try {
	// d = sdf.parse(str);
	// } catch (Exception e) {
	//
	// }
	//
	// return d;
	// }
	//
	public static String dateToStr(Date date) {
		String s = "";
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			s = sdf.format(date);
		} catch (Exception e) {

		}

		return s;
	}

	//
	// public static String dateToStrHMS(Date date) {
	// String s = "";
	// if (date == null) {
	// return "";
	// }
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// try {
	// s = sdf.format(date);
	// } catch (Exception e) {
	//
	// }
	//
	// return s;
	// }
	//
	// public static String dateToStrHM(Date date) {
	// String s = "";
	// if (date == null) {
	// return "";
	// }
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	// try {
	// s = sdf.format(date);
	// } catch (Exception e) {
	//
	// }
	//
	// return s;
	// }
	//
	// public static String dateToStrHMOnly(Date date) {
	// String s = "";
	// if (date == null) {
	// return "";
	// }
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	// try {
	// s = sdf.format(date);
	// } catch (Exception e) {
	//
	// }
	//
	// return s;
	// }

	/**
	 * @return
	 */
	public static Date getThisWeekMonday() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
		return strToDate(dateToStr(calendar.getTime()));
	}

	/**
	 * 判断某个日期是否在开始和结束日期范围内，精确到天
	 * 
	 * @param now
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenTwoDate(Date now, Date startDate, Date endDate) {
		if (startDate != null) {
			startDate = strToDate(dateToStr(startDate) + DAY_BEGIN_TIME);
		}
		if (endDate != null) {
			endDate = strToDateHMS(dateToStr(endDate) + DAY_END_TIME);
		}
		return isBetweenTwoDateHMS(now, startDate, endDate);
	}

	/**
	 * 判断某个时间是否在开始和结束时间范围内，精确到毫秒
	 * 
	 * @param now
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenTwoDateHMS(Date now, Date startDate, Date endDate) {
		if (startDate == null && endDate == null) {
			return true;
		} else if (startDate != null && endDate != null) {
			return now.getTime() >= startDate.getTime() && now.getTime() <= endDate.getTime();
		} else if (startDate != null) {
			return now.getTime() >= startDate.getTime();
		} else if (endDate != null) {
			return now.getTime() <= endDate.getTime();
		}

		return false;
	}

	/**
	 * 根据年数，取得年份列表。
	 * 
	 * @param yearNum
	 *            要取得的年数。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-15
	 */
	public static List<String> getLastYearsByNum(int yearNum) {
		int currentYear;
		List<String> lstTenYears = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		currentYear = cal.get(Calendar.YEAR);

		for (int i = 0; i < yearNum; i++) {
			lstTenYears.add((currentYear - i) + "");
		}

		return lstTenYears;
	}

	/**
	 * 取得最近十年的年份。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-15
	 */
	public static List<String> getLastTenYears() {
		return getLastYearsByNum(10);
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-21
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算给定的年，某个月 有多少天。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-21
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int calDaysInYearAndMonth(int year, int month) {

		if (month == 2 && isLeapYear(year)) {
			return 29;
		} else if (month == 2 && !isLeapYear(year)) {
			return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else {
			return 31;
		}
	}

	public static void main(String[] args) {
		// List<String> tenYears = getLastYearsByNum(3);
		// for(String year : tenYears) {
		// System.out.println(year + ",");
		// }

		System.out.println(calDaysInYearAndMonth(2007, 2));
	}
}
