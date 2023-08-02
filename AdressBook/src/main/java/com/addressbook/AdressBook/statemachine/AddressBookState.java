package com.addressbook.AdressBook.statemachine;


import com.addressbook.AdressBook.model.BusinessCard;

public interface AddressBookState {

    //handle is identical in all the classes which implement this interface.
    //however, this allows specific implementation of the states for future use
    void handle(BusinessCard card);

}
