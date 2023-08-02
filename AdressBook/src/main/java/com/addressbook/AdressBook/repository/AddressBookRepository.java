package com.addressbook.AdressBook.repository;

import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressBookRepository extends JpaRepository<BusinessCard, Long> {
    List<BusinessCard> findByState(State state);
}
