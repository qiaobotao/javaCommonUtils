/**
 * 
 */
package com.jinhetech.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zmm
 *
 * 2011-2-22
 */
public class FileUtils {
	
	public static OutputStream getOutputStream(HttpServletResponse response,
			String fileName,
			String fileType,
			String encode) throws IOException{
		String fileNameEncode = URLEncoder.encode(fileName, encode);
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileNameEncode);
		response.setContentType("application/" + fileType);
		OutputStream os = response.getOutputStream();
		return os;
	}
	
	/**
	 * @param os
	 * @param realPath 绝对路径
	 */
	public static void downloadFile(OutputStream os, String realPath) throws Exception{
		FileInputStream fis = null;
		try{
			File file = new File(realPath);
			fis = new FileInputStream(file);
			int buffer;
			byte[] bufferSize = new byte[1024];
			while ((buffer = fis.read(bufferSize, 0, 1024)) != -1) {
				os.write(bufferSize, 0, buffer);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if (null != fis) {
				fis.close();
			}
		}
	}

	public static boolean deleteFileAndDirectory(String path) {
		File file = new File(path);
		return deleteFileAndDirectory(file);
	}
	
	/**
	 * 删除文件、文件夹（删除前，请确保文件相关的流都已关闭）
	 * @author zhoumingming
	 * @param file
	 */
	public static boolean deleteFileAndDirectory(File file) {
		if(file == null || !file.exists()){
			return false;
		}
		if (file.isDirectory()) {
			File[] child = file.listFiles();
			if (child != null && child.length != 0) {
				for (int i = 0; i < child.length; i++) {
					deleteFileAndDirectory(child[i]);
				}
			}
		}
		file.delete();
		return true;
	} 
}
