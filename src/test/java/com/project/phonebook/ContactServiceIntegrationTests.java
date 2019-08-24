package com.project.phonebook;


import com.project.phonebook.domain.Contact;
import com.project.phonebook.exception.ResourceNotFoundException;
import com.project.phonebook.service.ContactService;
import com.project.phonebook.steps.ContactSteps;
import com.project.phonebook.transfer.contact.CreateContactRequest;
import com.project.phonebook.transfer.contact.UpdateContactRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceIntegrationTests {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactSteps contactSteps;

    @Test
    public void testCreateContact_whenValidRequest_ThenReturnCreatedContact() {
        contactSteps.createContact();
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenMissingMandatoryProperties_thenThrowException() {
        CreateContactRequest request = new CreateContactRequest();
        contactService.createContact(request);
    }

    @Test
    public void testGetContact_whenExistingId_thenReturnContact() throws ResourceNotFoundException {
        Contact createdContact = contactSteps.createContact();

        Contact contact = contactService.getContact(createdContact.getId());

        assertThat(contact, notNullValue());
        assertThat(contact.getId(), is(contact.getId()));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetContact_whenNonExistingId_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        contactService.getContact(9999L);
    }

    @Test
    public void testUpdateContact_whenValidRequest_thenReturnUpdatedContact() throws ResourceNotFoundException {
        Contact createdContact = contactSteps.createContact();

        UpdateContactRequest request= new UpdateContactRequest();
        request.setFirstName(createdContact.getFirstName() + "Updated");
        request.setLastName(createdContact.getLastName() + "Updated");
        request.setPhoneNumber(createdContact.getPhoneNumber());

        Contact updatedContact = contactService.updateContact(createdContact.getId(),request) ;

        assertThat(updatedContact, notNullValue());
        assertThat(updatedContact.getId(), is(createdContact.getId()));

        assertThat(updatedContact.getFirstName(), not(is(createdContact.getFirstName())));
        assertThat(updatedContact.getFirstName(), is(request.getFirstName()));

        assertThat(updatedContact.getLastName(), not(is(createdContact.getLastName())));
        assertThat(updatedContact.getLastName(), is(request.getLastName()));

        assertThat(updatedContact.getPhoneNumber(), not(is(createdContact.getPhoneNumber())));
        assertThat(updatedContact.getPhoneNumber(), is(request.getPhoneNumber()));
    }
}