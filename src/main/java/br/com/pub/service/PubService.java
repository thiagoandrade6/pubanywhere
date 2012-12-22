package br.com.pub.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pub.domain.Pub;
import br.com.pub.form.ContactForm;
import br.com.pub.mail.EmailUtils;
import br.com.pub.repository.PubRepository;

@Service
public class PubService {
	
	private static Logger log = LoggerFactory.getLogger(PubService.class);
	
	@Autowired private PubRepository pubRepository;
	@Autowired private MessageService messageService;
	
	public List<Pub> listNearPubs(Double lat, Double lng) {
		//TODO: listar somente pubs ativos
		return pubRepository.listAll();
	}
	
	public String registerPub(Pub pub, HttpServletRequest request) {
		
		String result = null;
		
		if (pub.getLat() != null || pub.getLng() != null) {
			pubRepository.insert(valid(pub));
			sendMail(pub, request);
			result = messageService.getMessageFromResource(request, "message.pub.success");
		} else {
			log.error("Lat or Lng null");
			result = messageService.getMessageFromResource(request, "message.error");
		}
		
		return result;
	}

	private void sendMail(Pub pub, HttpServletRequest request) {
		ContactForm form = new ContactForm();
		form.setName("Pub to Register");
		form.setEmail("pubanywhere@gmail.com");
		form.setSubject("Register " + pub.getNome());
		form.setDescription(
				"Pub: " + pub.getNome() + "<br/>" +
				"Location: " + pub.getLocal() + "<br/>" +
				"Description: " + pub.getDescricao() + "<br/>" +
				"<a href="+ EmailUtils.createURL(request, pub.getPubId()) + ">" + EmailUtils.createURL(request, pub.getPubId()) +"</a>");
		
		EmailUtils.sendMail(form, request);
	}
	
	public void activePub(Long id) {
		Pub pub = pubRepository.find(id);		
		pub.setEnabled(true);
		pubRepository.update(pub);
	}
	
	public Pub findPubById(Long pubId) {
		return pubRepository.find(pubId);		
	}

	private Pub valid(Pub pub) {
		pub.setEmail(pub.getEmail().toLowerCase());
		pub.setFacebook(pub.getFacebook().toLowerCase());
		pub.setTwitter(pub.getTwitter().toLowerCase());
		
		validWebSite(pub);
		
		pub.setDesde(new Date());
		pub.setEnabled(false);
		return pub;
	}

	private void validWebSite(Pub pub) {
		pub.setWebsite(pub.getWebsite().toLowerCase());
		if (!pub.getWebsite().contains("http://") && !pub.getWebsite().contains("https://")) {
			pub.setWebsite("http://" + pub.getWebsite());
		}
	}
}
