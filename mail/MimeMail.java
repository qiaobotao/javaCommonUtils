/**
 * 
 */
package com.shinedu.utils.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoumingming
 *
 * May 13, 2010
 */
public class MimeMail {

	private List<String> toList;
	
	private String subject;
	
	private String content;
	
	//模板绝对路径
	private String templatePath;
	
	//模板替换文本
	private Map<String, String> textMap;
	
	//内嵌多媒体
	private Map<String, String> realInlinePathMap;
	
	//附件
	private List<String> realAttachmentPathList;

	public List<String> getTo() {
		return toList;
	}

	public void addTo(String to) {
		if(this.toList == null){
			this.toList = new ArrayList<String>();
		}
		this.toList.add(to);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, String> getTextMap() {
		return textMap;
	}

	public void putText(String key, String text) {
		if(this.textMap == null){
			this.textMap = new HashMap<String, String>();
		}
		this.textMap.put(key, text);
	}

	public Map<String, String> getRealInlinePathMap() {
		return realInlinePathMap;
	}

	public void putRealInlinePath(String cid, String path) {
		if(this.realInlinePathMap == null){
			this.realInlinePathMap = new HashMap<String, String>();
		}
		this.realInlinePathMap.put(cid, path);
	}

	public List<String> getRealAttachmentPath() {
		return realAttachmentPathList;
	}

	public void addRealAttachmentPath(String path) {
		if(this.realAttachmentPathList == null){
			this.realAttachmentPathList = new ArrayList<String>();
		}
		this.realAttachmentPathList.add(path);
	}
}
