/**
 * 
 */
package com.jinhetech.common;

import java.net.InetAddress;
public class UUIDHexGenerator {
	private static final int IP;
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private static String sep = "";

	protected static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * 生成主方法
	 * @return
	 */
	public static synchronized String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}

	protected static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	protected static int getJVM() {
		return JVM;
	}

	protected static short getCount() {
		synchronized (UUIDHexGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	protected static int getIP() {
		return IP;
	}

	protected static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {
			String id = UUIDHexGenerator.generate();
			System.out.println(id);
			String id2 = UUIDHexGenerator.generate();
			System.out.println(id2);
		}

	}
}
