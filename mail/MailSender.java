/**
 * 
 */
package com.shinedu.utils.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.shinedu.common.LmsConstants;

/**
 * @author zhoumingming
 *
 * Apr 28, 2010
 */
public class MailSender{

	private static MailSender instance;

	private JavaMailSenderImpl mailSender;
	
	public final static int TOLIST_MAX_SIZE = 20;
	
	private MailSender(){
        //设定Mail Server
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(LmsConstants.MAIL_SMTP);
		Map<String, Object> javaMailPropertiesMap = new HashMap<String, Object>();
		javaMailPropertiesMap.put("mail.smtp.auth", true);
		javaMailPropertiesMap.put("mail.smtp.timeout", 1000);
		Properties javaMailProperties = new Properties();
		javaMailProperties.putAll(javaMailPropertiesMap);
		mailSender.setJavaMailProperties(javaMailProperties);
        //SMTP验证时，需要用户名和密码
		mailSender.setUsername(LmsConstants.MAIL_DEFAULT_SENDER);
		mailSender.setPassword(LmsConstants.MAIL_DEFAULT_PSW);
		
	}
	
	public static MailSender getInstance(){
		if(instance == null){
			instance = new MailSender();
		}
		return instance;
	}
	
	/**
	 * 发送简单mail
	 * @param toList
	 * @param subject
	 * @param content
	 * @throws Exception 
	 */
	public void sendSimpleMail(SimpleMail simpleMail) throws Exception {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(LmsConstants.MAIL_DEFAULT_SENDER);
        mail.setSubject(simpleMail.getSubject());
        mail.setText(simpleMail.getContent());
        mail.setTo(simpleMail.getTos());
        try{
            mailSender.send(mail);
        }catch(Exception e){
        	throw e;
        }
    }
	
	public static List<SimpleMail> generateMailPerTime(Map<String, String> toMap, String subject, String content, String relationId){
		List<SimpleMail> mailList = new ArrayList<SimpleMail>();
		if(toMap != null && toMap.size() > 0){
			Iterator<String> tos = toMap.keySet().iterator();
			SimpleMail mailPerTime = new SimpleMail();
			String to;
			while(tos.hasNext()){
				if(mailPerTime.getTos().length == TOLIST_MAX_SIZE){
					mailList.add(mailPerTime);
					mailPerTime = new SimpleMail();
				}
				if(mailPerTime.getTos().length == 0){
					mailPerTime.setSubject(subject);
					mailPerTime.setContent(content);
					mailPerTime.setRelationId(relationId);
				}
				to = tos.next();
				mailPerTime.addTo(to, toMap.get(to));
			}
			if(mailPerTime.getTos().length > 0){
				mailList.add(mailPerTime);
			}
		}
		return mailList;
	}
}