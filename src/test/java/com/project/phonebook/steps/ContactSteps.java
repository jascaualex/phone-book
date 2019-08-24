package com.project.phonebook.steps;

import com.project.phonebook.domain.Contact;
import com.project.phonebook.service.ContactService;
import com.project.phonebook.transfer.contact.CreateContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ContactSteps {

    @Autowired
    private ContactService contactService;

    public Contact createContact() {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Pop");
        request.setLastName("Ion");
        request.setPhoneNumber(043241123);

        Contact createdContact = contactService.createContact(request);

        assertThat(createdContact, notNullValue());
        assertThat(createdContact.getId(), greaterThan(0L));
        assertThat(createdContact.getFirstName(), is(request.getFirstName()));
        assertThat(createdContact.getLastName(),is(request.getLastName()));
        assertThat(createdContact.getPhoneNumber(), is(request.getPhoneNumber()));

        return createdContact;

    }



}
