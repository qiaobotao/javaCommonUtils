/**
 * 
 */
package com.shinedu.utils.mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoumingming
 *
 * May 13, 2010
 */
public class SimpleMail implements Serializable{
	
	private Map<String, String> toList = new HashMap<String, String>();
	
	private String subject;
	
	private String content;
	
	private String relationId;
	
	public String[] getLearnerIds() {
		String[] learnerIds = new String[toList.size()];;
		return toList.keySet().toArray(learnerIds);
	}
	
	public String[] getTos() {
		String[] tos = new String[toList.size()];;
		return toList.values().toArray(tos);
	}

	public void addTo(String learnerId, String to) {
		this.toList.put(learnerId, to);
	}
	
	public String getToByLearnerId(String learnerId){
		return toList.get(learnerId);
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
	
	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public SimpleMail copyMail(){
		SimpleMail simpleMail = new SimpleMail();
		simpleMail.setContent(content);
		simpleMail.setSubject(subject);
		simpleMail.setRelationId(relationId);
		return simpleMail;
	}
}
