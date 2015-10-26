package com.zowee.kefr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.textmining.text.extraction.WordExtractor;

public class TxtReader {

	/**
	 * ͨ��һ��InputStream��ȡ����
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			//inputStreamReader = new InputStreamReader(inputStream, "gbk");
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * ͨ��txt�ļ���·����ȡ������
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getString(String filepath) {
		File file = new File(filepath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return getString(fileInputStream);
	}
	
	public static String readWord(InputStream inputStream){
	    	// 创建输入流读取doc文件
			//FileInputStream in;
			String text = null;
			try {
				//in = new FileInputStream(new File(file));
				WordExtractor extractor = null;
				// 创建WordExtractor
				extractor = new WordExtractor();
				// 对doc文件进行提取
				//text = extractor.extractText(in);
				text = extractor.extractText(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return text;
	}
		
}
