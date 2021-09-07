package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.ContactUs;
import com.hashedin.product.kyeazy.repositories.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactUsService {

    private ContactUsRepository contactUsRepository;

    @Autowired
    ContactUsService(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    public ActionDTO register(ContactUs contactUs) {
        contactUs.setMessageSentTime(new Date());
        ContactUs newMessage = contactUsRepository.save(contactUs);
        return new ActionDTO(newMessage.getMessageID(), true, "Your message has been received. We will contact you for further communication.");
    }
}
