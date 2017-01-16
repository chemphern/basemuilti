package com.ycsys.smartmap.sys.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ycsys.smartmap.sys.common.exception.SysException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成word工具类（目前支持.doc文件）
 * @author liweixiong
 * @date   2017年1月4日
 */
public class WordUtils {
	private static final Logger log = LoggerFactory.getLogger(WordUtils.class);
	private static Configuration configuration = null;
	//获得模板文件的位置
	private static final String templateFolder = WordUtils.class
			.getClassLoader().getResource("/template").getPath();
	static {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {
			configuration.setDirectoryForTemplateLoading(new File(
					templateFolder));
		} catch (IOException e) {
			log.debug("setDirectoryForTemplateLoading error...");
			throw new SysException("setDirectoryForTemplateLoading error...");
		}
	}
	
	/**
	 * 创建word
	 * @param fileName
	 * @param templateName
	 * @param dataMap
	 * @return
	 */
	public static File createDoc(String fileName,String templateName,Map<?, ?> dataMap) {
		fileName = fileName + ".doc";
		File file = new File(fileName);
		Template template = getTemplate(templateName);
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(file),
					"utf-8");
			template.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			log.error("createDoc failure...");
			throw new SysException("createDoc failure...");
		}
		return file;
	}
	
	/**
	 * 创建word
	 * @param name
	 * @param dataMap
	 * @param template
	 * @return
	 */
	public static File createDoc(String fileName, Map<?, ?> dataMap,Template template) {
		fileName = fileName + ".doc";
		File file = new File(fileName);
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(file),
					"utf-8");
			template.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			log.error("createDoc failure...");
			throw new SysException("createDoc failure...");
		}
		return file;
	}
	
	/**
	 * 根据模版名得到模版
	 * @param templateName
	 * @return
	 */
	public static Template getTemplate(String templateName){
		Template template = null;
		try {
			template = configuration.getTemplate(templateName + ".ftl");
		} catch (Exception e) {
			log.error("getTemplate failure...");
			throw new SysException("getTemplate failure...");
		}
		return template;
	}
}