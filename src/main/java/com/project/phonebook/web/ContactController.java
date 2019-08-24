package com.project.phonebook.web;

import com.project.phonebook.domain.Contact;
import com.project.phonebook.exception.ResourceNotFoundException;
import com.project.phonebook.service.ContactService;
import com.project.phonebook.transfer.contact.ContactResponse;
import com.project.phonebook.transfer.contact.CreateContactRequest;
import com.project.phonebook.transfer.contact.GetContactRequest;
import com.project.phonebook.transfer.contact.UpdateContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/phonebook")
@CrossOrigin
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {this.contactService = contactService;}


    @GetMapping
    public ResponseEntity<Page<ContactResponse>> getContacts(
            GetContactRequest request, Pageable pageable) {
        Page<ContactResponse> response = contactService.getContacts(request, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody @Valid CreateContactRequest request) {
        Contact response = contactService.createContact(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteContact(@PathVariable("id") Long id ){
        contactService.deleteContact(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Contact contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateContact(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateContactRequest request) throws ResourceNotFoundException {
        contactService.updateContact(id,request);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
