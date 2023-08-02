package com.addressbook.AdressBook.runner;

import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.service.AddressBookService;
import com.addressbook.AdressBook.model.AddressBookEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class BusinessCardInitRunner implements ApplicationRunner {


    @Autowired
    private AddressBookService addressBookService;


    @Override
    public void run(ApplicationArguments args) {
        BusinessCard card = new BusinessCard(1L, "Home", "Guy");
        BusinessCard card3 = new BusinessCard(3L, "B", "John");
        BusinessCard card2 = new BusinessCard(2L, "A", "David");

        addressBookService.uploadKnownBusinessCard(card);
        addressBookService.uploadUnknownBusinessCard(card3);
        addressBookService.uploadUnknownBusinessCard(card2);

        addressBookService.update(card2, AddressBookEvent.CONTACT_ADDRESS);
        addressBookService.update(card3, AddressBookEvent.CONTACT_ADDRESS);
        addressBookService.update(card, AddressBookEvent.MANUAL_VERIFICATION);
        addressBookService.update(card3, AddressBookEvent.STRONG_VERIFICATION);

    }
}
