package com.order.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class WebUtil {

	public static Logger log = Logger.getLogger(WebUtil.class);
	
	public static final int PAGE_SIZE = 20;

	/**
	 * ��requestȡ��ҳ��
	 * 
	 * @param request
	 * @return
	 */
	public static int getPage(HttpServletRequest request) {
		int page = 1;
		String temp = request.getParameter("page");

		if (!(temp == null || "".equals(temp))) {
			page = Integer.parseInt(temp);
		}

		return page;
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isEmpty(String source) {
		return source == null || "".equals(source.trim());
	}
	/**
	 * javascript��ʾ��Ϣ
	 * ���url��Ϊ��,ҳ�涨��ָ����url
	 * @param message
	 * @param url
	 * @return
	 */
	public static String alertMessage(String message, String url) {
		String result = "<script language='javascript'>alert('" + message
				+ "');";
		if (url != null) {
			result += "window.location='" + url + "';";
		}
		result += "</script>";
		return result;

	}
	/**
	 * javascript��ʾ��Ϣ��������һ��ҳ��
	 * @param message
	 * @return
	 */
	public static String alertAndReturn(String message){
		String result="<script language='javascript'>alert('" +	message+"');";
		result+="window.history.back(-1);";
		result+="</script>";
		return result;
		
	}
	/**
	 * javascript��ʾ��Ϣ���رմ���
	 * @param message
	 * @return
	 */
	public static String alertAndClose(String message){
		String result="<script language='javascript'>alert('"+message+"');";
		result+="returnValue=true;window.close();";

		result+="</script>";
		return result;
		
	}
	/**
	 * ���ַ�������ת����������
	 * @param source
	 * @return
	 */
	public static Integer[] toIntegerArray(String[] source) {
		
		if(source==null){
			return null;
		}
		
		Integer[] result = new Integer[source.length];
		for (int i = 0; i < source.length; i++) {
			result[i] = new Integer(source[i]);
		}
		return result;
	}

	/**
	 * ���ַ�������ת����������
	 * @param source
	 * @return
	 */
	public static int[] toIntArray(String[] source) {
		int[] result = new int[source.length];
		for (int i = 0; i < source.length; i++) {
			result[i] = Integer.parseInt(source[i]);
		}
		return result;
	}

	/**
	 * ����Ŀ¼
	 * @param folderPath
	 * @return
	 */
	public static String createFolder(String folderPath) {
		try {
			String[] paths = folderPath.split("/");
			String temp ="";
			File myFilePath;
			for(int i=0;i<paths.length;i++){
				temp += paths[i]+"/";
				myFilePath = new File(temp);
				if (myFilePath.exists()==false) {
					myFilePath.mkdir();
				}
				myFilePath = null;
			}
		} catch (Exception e) {
			log.warn("����Ŀ¼��������");
		}
		return folderPath;
	}

	
	/**
	 * ȡ��ָ·�����ļ�������
	 * @param filePath
	 * @return
	 */
	public static String getTemplate(String filePath) {

		File rf = new File(filePath);
		StringBuffer sLine = new StringBuffer("");
		try {

			InputStreamReader read = new InputStreamReader(new FileInputStream(rf), "GBK");

			BufferedReader reader = new BufferedReader(read);

			String line = new String();
			while ((line = reader.readLine()) != null) {
				sLine.append(line).append((char)13).append((char)10);
			}
			reader.close();
			read.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sLine.toString();

	}
	
	/**
	 * �ַ��滻
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
	public static String replace(String strSource,String strFrom,String strTo){ 
		String strDest = ""; 
		int intFromLen = strFrom.length(); 
		int intPos; 
		
		if(strTo==null){
			strTo="";
		}
		
		while((intPos=strSource.indexOf(strFrom))!=-1){ 
		strDest = strDest + strSource.substring(0,intPos); 
		strDest = strDest + strTo; 
		strSource = strSource.substring(intPos+intFromLen); 
		} 
		strDest = strDest + strSource; 

		return strDest; 
	} 
	
	public static Double parseDouble(String source) {
		return isEmpty(source)?null:new Double(source);
	}
	
	public static Integer parseInteger(String source) {
		return isEmpty(source)?null:new Integer(source);
	}
	
	public static Date parseDate(String source) {
		if(isEmpty(source)){
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	
}
