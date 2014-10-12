/**
 * 
 */
package com.jinhetech.common;

import sun.misc.BASE64Encoder;

/**
 * @author 乔伯涛
 *
 */
public class StringToBase64 {
	public static String toBase64(byte[] b){
		return new BASE64Encoder().encode(b);
	}

}
