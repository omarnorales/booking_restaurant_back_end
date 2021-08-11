package com.odre.bookingrestaurantapi01.services.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.odre.bookingrestaurantapi01.dtos.EmailTemplateDto;
import com.odre.bookingrestaurantapi01.entities.Notification;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.exceptions.InternalServerErrorException;
import com.odre.bookingrestaurantapi01.exceptions.NotFoundException;
import com.odre.bookingrestaurantapi01.repositories.NotificationRepository;
import com.odre.bookingrestaurantapi01.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Override
	public String processSendEmail(String receiver, String templateCode, String currentName)
			throws BookingException {
		
		final EmailTemplateDto emailTemplateDto = findTemplateAndRemplace(templateCode, currentName);
		
		this.sendEmail(receiver, emailTemplateDto.getSubject(), emailTemplateDto.getTemplate());
		
		return "EMAIL_SENT";
	}
	
	private void sendEmail(final String receiver, final String subject, final String template) throws BookingException{
		
		final MimeMessage email = javaMailSender.createMimeMessage();
		final MimeMessageHelper content;
		
		try {
			
			content = new MimeMessageHelper(email, true);
			content.setSubject(subject);
			content.setTo(receiver);
			content.setText(template, true);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		javaMailSender.send(email);
	}
	
	//Dto: Data Transfer Object
	private EmailTemplateDto findTemplateAndRemplace(final String templateCode, final String currentName) throws BookingException {
		
		final Notification notificationTemplate = notificationRepository.findByTemplateCode(templateCode)
				.orElseThrow(()-> new NotFoundException("TEMPLATE_CODE_NOT_FOUND","TEMPLATE_CODE_NOT_FOUND"));
		
		final EmailTemplateDto emailTemplateDto = new EmailTemplateDto();
		emailTemplateDto.setSubject(notificationTemplate.getTemplateCode());
		emailTemplateDto.setTemplate(notificationTemplate.getTemplate().replaceAll("\\{"+"name"+"\\}", currentName));
		
		return emailTemplateDto;
	}

}
