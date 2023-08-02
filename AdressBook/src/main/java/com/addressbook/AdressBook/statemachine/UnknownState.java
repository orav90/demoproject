package com.addressbook.AdressBook.statemachine;

import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.model.State;

public class UnknownState implements AddressBookState{

    private AddressBookMachine addressBook;
    private State state;
    public UnknownState(AddressBookMachine addressBook, State state) {
        this.addressBook = addressBook;
        this.state = state;
    }


    @Override
    public void handle(BusinessCard card) {
        addressBook.saveBusinessCard(card, state);
    }
}
