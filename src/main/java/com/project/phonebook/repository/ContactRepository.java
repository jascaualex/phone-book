package com.project.phonebook.repository;

import com.project.phonebook.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findByFirstNameContaining(String partialName, Pageable pageable);

}
