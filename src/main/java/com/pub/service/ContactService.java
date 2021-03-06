package com.pub.service;

import static com.pub.constants.PUB_CONSTANTS.MODAL_MESSAGE;
import static com.pub.constants.PUB_CONSTANTS.MODAL_TITLE;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.pub.form.ContactForm;
import com.pub.mail.EmailService;
import com.pub.utils.ResultMessage;


@Service
public class ContactService {
	
	@Autowired
	private MessageService message;

	public List<ResultMessage> sendMessage(ContactForm form, HttpServletRequest request, BindingResult result) {
		
		List<ResultMessage> lista = new LinkedList<ResultMessage>();
		
		if (result.hasErrors()) {
			lista.add(new ResultMessage(MODAL_TITLE, message.getMessageFromResource(request, "config.error")));
			lista.add(new ResultMessage(MODAL_MESSAGE, message.getMessageFromResource(request, "config.error.fields")));
			
		} else {
			try {
				
				form.setDescription(
						"From: " + form.getFrom() + "<br>" + 
						"Name: " + form.getName() + "<br>" +
						"Subject: " + EmailService.validSubject(form.getSubject()) + "<br>" +
						"Description:" + form.getDescription());
				
				EmailService.sendMail(form, request, false);
				
				lista.add(new ResultMessage(MODAL_TITLE, message.getMessageFromResource(request, "config.success")));
				lista.add(new ResultMessage(MODAL_MESSAGE, message.getMessageFromResource(request, "message.email.success")));
				
			} catch (Exception e) {
				e.printStackTrace();
				lista.add(new ResultMessage(MODAL_TITLE, message.getMessageFromResource(request, "config.error")));
				lista.add(new ResultMessage(MODAL_MESSAGE, message.getMessageFromResource(request, "message.email.error")));
			}
		}
		return lista;
	}
}
