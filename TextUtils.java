/**
 * 
 */
package com.shinedu.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shinedu.common.LmsConstants;

/**
 * @author dugang
 * @date Jan 5, 2010 7:58:37 PM
 */
public class TextUtils {

	public static final int SUBSTRING_BY_BYTE_LENGTH_DOWN = 0;
	public static final int SUBSTRING_BY_BYTE_LENGTH_UP = 1;
	private static final int[] SUBSTRING_BY_BYTE_LENGTH_STANDARD_MOD = {
			SUBSTRING_BY_BYTE_LENGTH_DOWN, SUBSTRING_BY_BYTE_LENGTH_UP};
	/**
	 * @param src
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String src,String delimiter){
		if(src==null || "".equals(src)){
			return new String[0];
		}
		int index = src.indexOf(delimiter);
		if(index==-1){
			return new String[]{src};
		}
		List<String> temp = new ArrayList<String>();
		while(index!=-1){
			String subStr = src.substring(0,index);
			temp.add(subStr);
			src = src.substring(index+delimiter.length());
			index = src.indexOf(delimiter);
		}
		temp.add(src);
		String[] result = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			result[i] = (String)temp.get(i);
		}
		return result;
	}
	/**
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str){
		int len = 0;
		if(isBlank(str)){
			return len;
		}
		for(int i=0;i<str.length();i++){
			if(str.codePointAt(i) > 255){
				len+=2;
			}else{
				len+=1;
			}
		}
		return len;
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param mod
	 * @return
	 */
	public static String getSubStringByByte(String src, int length, int mod) {
		String failedRet = "";
		if (src == null) {
			return failedRet;
		}
		boolean isValidMod = false;
		for(int i = 0; i < SUBSTRING_BY_BYTE_LENGTH_STANDARD_MOD.length; i++){
			if(mod == SUBSTRING_BY_BYTE_LENGTH_STANDARD_MOD[i]){
				isValidMod = true;
				break;
			}
		}
		if(!isValidMod){
			return failedRet;
		}
		// byte length  
		int d = 0;
		// char length
		int n = 0; 
		for (; n < src.length(); n++) {
			d = src.codePointAt(n) > 255 ? d + 2 : d + 1;
			if (d >= length) {
				break;
			}
		}
		if(d > length){
			if(mod == SUBSTRING_BY_BYTE_LENGTH_UP){
				n++;
			}
		}
		return src.substring(0, n);
	}  
	/**
	 * @param ids
	 * @return
	 */
	public static boolean isNull(String[] ids){
		return ids==null || ids.length==0;
	}
	/**
	 * @param src
	 * @return
	 */
	public static boolean isBlank(String src){
		return src==null || src.equals("");
	}
	
	/**
	 * @param src
	 * @param length
	 * @return
	 */
	public static String getSubString(String src,int length){
		if(TextUtils.isBlank(src)){
			return src;
		}
		if(src.length()<=length){
			return src;
		}
		return src.substring(0,length)+"...";
	}
	
	/**
	 * @param src
	 * @return
	 */
	public static String getHtmlContent(String src){
		if(!TextUtils.isBlank(src)){
			src = StringUtils.replace(src,"\r\n","<br>");
			src = StringUtils.replace(src,"\n","<br>");
		}
		return src;
	}
	/**
	 * @param src
	 * @return
	 */
	public static String getJSContent(String src){
		if(!TextUtils.isBlank(src)){
			src = StringUtils.replace(src,"'","\\\'");
		}
		return src;
	}
	/**
	 * @param src
	 * @return
	 */
	public static String getXmlContent(String src){
		if(!TextUtils.isBlank(src)){
			src = StringUtils.replace(src,"'","&amp;acute;");
			src = StringUtils.replace(src,"\"","&amp;quot;");
			src = StringUtils.replace(src,"&","&amp;");
			src = StringUtils.replace(src,"<","&lt;");
		}
		return src;
	}
	/**
	 * 得到html的引号转义
	 * @param src
	 * @return
	 */
	public static String getHtmlQuoteContent(String src){
		if(!TextUtils.isBlank(src)){
			src = StringUtils.replace(src,"'","&acute;");
			src = StringUtils.replace(src,"\"","&quot;");
		}
		return src;
	}
	/**
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Throwable exp){
		StringWriter s = new StringWriter();
		Throwable e = null;
		if(exp instanceof javax.servlet.ServletException){
			e = ((javax.servlet.ServletException)exp).getRootCause();
		}else{
			e = exp;
		}
		if(e==null){
			return "";
		}
		s.write("<b>出错原因：");
		s.write(e.toString());
		s.write("</b>");
        StackTraceElement[] trace = e.getStackTrace();
        s.write("<pre>");
        for (int i=0; i < trace.length; i++){
            s.write("　　at " + trace[i]);
            if(i<trace.length-1){
            	s.write("<br>");
            }
        }
        s.write("</pre>");
		return s.toString();
	}
	/**
	 * @return
	 */
	public static String getDefaultSystemName(){
		return LmsConstants.DEFAULT_SYSTEM_NAME;
	}
	/**
	 * @param value
	 * @return
	 */
	public static Double formatDouble2ScaleDecimal(Double value,int scale){
		if(value==null){
			return new Double(0);
		}
		String s_value = String.valueOf(value.doubleValue());
		if(s_value.indexOf(".")==-1){
			return value;
		}
		String prefixValue = s_value.substring(0,s_value.indexOf("."));
		s_value = s_value.substring(s_value.indexOf(".")+1);
		if(s_value.length()>scale){
			s_value = s_value.substring(0,scale);
		}
		String s_result = prefixValue + "." + s_value;
		return new Double(s_result);
	}
	
	/**
	 * @param branchCode
	 * @param maxCode
	 * @return
	 */
	public static String getDataCode(String branchCode,String maxCode){
		String dataCode = "";
		if(TextUtils.isBlank(maxCode)){
			dataCode = branchCode+"001";
		}else{
			String subCode = maxCode.replaceFirst(branchCode,"");
			int serialNum = Integer.valueOf(subCode)+1001;
			dataCode = branchCode + String.valueOf(serialNum).substring(1);
		}
		return dataCode;
	}
	
	/**
	 * 去掉字符串前后的全、半角空格
	 * @param src
	 * @return
	 */
	public static String trimSpace(String src){
		if(isBlank(src)){
			return "";
		}
		int beginIndex = 0;
		while(beginIndex < src.length() && (' ' == src.charAt(beginIndex) || '　' == src.charAt(beginIndex))){
			beginIndex++;
		}
		int endIndex = src.length() - 1;
		while(endIndex > beginIndex && (' ' == src.charAt(endIndex) || '　' == src.charAt(endIndex))){
			endIndex--;
		}
		src = src.substring(beginIndex, endIndex + 1);
		return src;
		
	}
	/**
	 * @return
	 */
	public static boolean isUseOracle(){
		return LmsConstants.SYSTEM_DATABASE_ORACLE.equals(LmsConstants.DEFAULT_SYSTEM_DATABASE);
	}
	
	/**
	 * @return
	 */
	public static boolean isUseMysql(){
		return LmsConstants.SYSTEM_DATABASE_MYSQL.equals(LmsConstants.DEFAULT_SYSTEM_DATABASE);
	}
}
