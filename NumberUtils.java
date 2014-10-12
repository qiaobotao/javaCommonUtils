/**
 * 
 */
package com.shinedu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author zhoumingming
 *
 * Jan 25, 2010
 */
public class NumberUtils {

	public static final String PATTERN_INTEGER = "#,##0";
	public static final String PATTERN_DECIMAL_ONE_BIT = "###0.0";
	public static final String PATTERN_DECIMAL_TWO_BITS = "#,##0.00";

	/**
	 * patterns
	 */
	private static final String[] patterns = {PATTERN_DECIMAL_ONE_BIT, PATTERN_DECIMAL_TWO_BITS, PATTERN_INTEGER};
	
	//round modes
	private static final int ROUND = 0;
	private static final int ROUND_DOWN = 1;
	private static final int ROUND_UP = 2;

	/**
	 * 格式化数字，四舍五入
	 * @param numberStr
	 * @param pattern
	 * @return
	 */
	public static String format(String numberStr, String pattern){
		return format(numberStr, pattern, ROUND);
	}

	/**
	 * 格式化数字，直接去掉超出的小数位
	 * @param numberStr
	 * @param pattern
	 * @return
	 */
	public static String formatRoundDown(String numberStr, String pattern){
		return format(numberStr, pattern, ROUND_DOWN);
	}

	/**
	 * 格式化数字，去掉超出的小数位，然后进1
	 * @param numberStr
	 * @param pattern
	 * @return
	 */
	public static String formatRoundUp(String numberStr, String pattern){
		return format(numberStr, pattern, ROUND_UP);
	}

	/**
	 * 将数值转换成非科学计数法的字符串
	 * @param target
	 * @return
	 */
	public static String toString(Object target){
		if(target == null){
			return "";
		}
		//判断值的类型后进行强制类型转换
		if (target instanceof Short) {
			return new BigDecimal((Short) target).toString();
		} else if (target instanceof Integer) {
			return new BigDecimal((Integer) target).toString();
		} else if (target instanceof Float) {
			return new BigDecimal(((Float) target).toString()).toString();
		} else if (target instanceof Double) {
			return new BigDecimal(((Double) target).toString()).toString();
		} else if (target instanceof Long) {
			return new BigDecimal((Long) target).toString();
		} else{
			return "";
		}
	}

	/**
	 * 解析long型数字
	 * @param target
	 * @return
	 */
	public static Long parseLong(String target){
		Double d = parseDouble(target);
		if(d == null || d > (double) Long.MAX_VALUE || d < (double) Long.MIN_VALUE){
		    return null;
		}
		return d.longValue();
	}

	/**
	 * 解析double型数字
	 * @param target
	 * @return
	 */
	public static Double parseDouble(String target){
		try{
			if(target == null || "".equals(target)){
				return null;
			}
			if("+".equals(target) || "-".equals(target) || ".".equals(target)){
				return null;
			}
			String pattern = "[+-]?[\\d]*[.]?[\\d]*";
			Pattern p = Pattern.compile(pattern);
			if(p.matcher(target).matches()){
				return Double.parseDouble(target);
			}
		}catch(Exception e){
		}
		return null;
	}

	/**
	 * 返回指定数字编码的下一个号码
	 * @param target
	 * @return
	 */
	public static String getNextCode(String target){
		Long l = parseLong(target);
		if(l == null || l == Long.MAX_VALUE){
			return "";
		}
		return getStrByBits(l + 1, target.length());
	}
	
	/**
	 * 取得指定数字的一定位数，少则补0
	 * @param target
	 * @param bit
	 * @return
	 */
	public static String getStrByBits(long target, int bit){
		if(bit < 1){
			return "";
		}
		//TODO 大数字不准确，待解决
		double modDou = Math.pow(10, bit);
		if(modDou + (double)target > (double)Long.MAX_VALUE){
			return "";
		}
		String str = toString(target + (long) modDou);
		return str.substring(str.length() - bit);
	}
	
	/**
	 * format main method
	 */
	private static String format(String numberStr, String pattern, int roundMode){
		BigDecimal numberBd;
		try{
			numberBd = new BigDecimal(numberStr);
		}catch(Exception e){
			//无效数字
			return "";
		}
		if(!Arrays.asList(patterns).contains(pattern)){
			//无效pattern
			return "";
		}
		numberBd = new BigDecimal(numberStr);
		if(roundMode != ROUND){
			//取得小数位数
	        int patternDecimalBits = 0;
	        int numberDecimalBits = 0;
	        int index = pattern.indexOf(".");
	        if(index >= 0){
	        	patternDecimalBits = pattern.length() - index - 1;
	        }
	        index = numberStr.indexOf(".");
	        if(index >= 0){
	        	numberDecimalBits = numberStr.length() - index - 1;
	        }
	        if(patternDecimalBits < numberDecimalBits){
	    		//直接去掉超出的小数位
	    		if(roundMode == ROUND_DOWN){
	                if(patternDecimalBits > 0){
	                	numberStr = numberStr.substring(0, index + patternDecimalBits + 1);
	                }else if(patternDecimalBits == 0){
	                	numberStr = numberStr.substring(0, index);
	                }
	                numberBd = new BigDecimal(numberStr);
	            //去掉超出的小数位，然后进1
	    		}else if(roundMode == ROUND_UP){
	    			numberBd = numberBd.add(new BigDecimal(0.5 * Math.pow(10, -patternDecimalBits)));
	    		}
	        }
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(numberBd);
	}
}
