package com.project.phonebook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.phonebook.domain.Contact;
import com.project.phonebook.exception.ResourceNotFoundException;
import com.project.phonebook.repository.ContactRepository;
import com.project.phonebook.transfer.contact.ContactResponse;
import com.project.phonebook.transfer.contact.CreateContactRequest;
import com.project.phonebook.transfer.contact.GetContactRequest;
import com.project.phonebook.transfer.contact.UpdateContactRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public  ContactService (ContactRepository contactRepository, ObjectMapper objectMapper) {
        this.contactRepository = contactRepository;
        this.objectMapper = objectMapper;
    }

    public Contact createContact(CreateContactRequest request) {
        LOGGER.info("Creating contact {}", request);

        Contact contact = objectMapper.convertValue(request, Contact.class);

        return contactRepository.save(contact);

    }

    public Contact getContact(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving contact {}", id);
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Contact " + id + " does not exist."));
    }

    public Contact updateContact(long id, UpdateContactRequest request) throws  ResourceNotFoundException{
        LOGGER.info("Updating contact {} with {}", id ,request);

        Contact contact = getContact(id);

        BeanUtils.copyProperties(request, contact);

        return contactRepository.save(contact);
    }

    public void deleteContact(long id) {
        LOGGER.info("Deleting contact {}", id);
        contactRepository.deleteById(id);
        LOGGER.info("Deleted product {}",id);
    }

    public Page<ContactResponse> getContacts(GetContactRequest request, Pageable pageable) {
        LOGGER.info("Retrieving contact {}", request);

        Page<Contact> contacts;

        if (request.getPartialName() != null) {
            contacts = contactRepository.findByFirstNameContaining(
                    request.getPartialName(),pageable);
        } else {
            contacts = contactRepository.findAll(pageable);
        }

        List<ContactResponse> contactResponses = new ArrayList<>();

        contacts.getContent().forEach(contact -> {
            ContactResponse contactResponse= new ContactResponse();
            contactResponse.setId(contact.getId());
            contactResponse.setFirstName(contact.getFirstName());
            contactResponse.setLastName(contact.getLastName());
            contactResponse.setPhoneNumber(contact.getPhoneNumber());

            contactResponses.add(contactResponse);
        });

        return new PageImpl<>(contactResponses, pageable, contacts.getTotalElements());
    }
}
