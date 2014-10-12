/**
 * 
 */
package com.jinhetech.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 乔伯涛
 *
 */
public class SHA {
	public static byte[] preImage(byte[] src) throws NoSuchAlgorithmException{
		
		if(src == null){
			return new byte[0];
		}
		MessageDigest md = MessageDigest.getInstance("sha-1");
		md.update(src);
		byte[] shaB = md.digest();
		return shaB;
	}
	/**
	 * 将字节数组转换成16进制字符串
	 * @param bArray
	 * @return
	 */
	public static String bytesToHexString(byte[] bArray) {
		
	    StringBuffer sb = new StringBuffer(bArray.length);
	    String sTemp;
	    for (int i = 0; i < bArray.length; i++) {
	     sTemp = Integer.toHexString(0xFF & bArray[i]);
	     if (sTemp.length() < 2)
	      sb.append(0);
	     sb.append(sTemp.toUpperCase());
	    }
	    return sb.toString();
	}
	public static String SHAtoString(String str) throws NoSuchAlgorithmException{
		if(str == null || "".equals(str)){
			return "";
		}
		return bytesToHexString(preImage(str.getBytes()));
	}

}
