package com.safari.safari_2.service;

import com.safari.safari_2.model.ContactDetails;
import com.safari.safari_2.repository.ContactDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactDetailsService {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    public ContactDetails getContactDetails() {
        ContactDetails contactDetails = contactDetailsRepository.findFirstByOrderByIdAsc();
        if (contactDetails == null) {
            // Create default contact details if none exist
            contactDetails = new ContactDetails(
                "123 Jungle Lane, Wildlands",
                "+94 123 456 789",
                "info@wildlifesafari.com"
            );
            contactDetails = contactDetailsRepository.save(contactDetails);
        }
        return contactDetails;
    }

    public ContactDetails updateContactDetails(String address, String phone, String email) {
        ContactDetails contactDetails = contactDetailsRepository.findFirstByOrderByIdAsc();
        
        if (contactDetails == null) {
            // Create new contact details if none exist
            contactDetails = new ContactDetails(address, phone, email);
        } else {
            // Update existing contact details
            contactDetails.setAddress(address);
            contactDetails.setPhone(phone);
            contactDetails.setEmail(email);
        }
        
        return contactDetailsRepository.save(contactDetails);
    }
}
