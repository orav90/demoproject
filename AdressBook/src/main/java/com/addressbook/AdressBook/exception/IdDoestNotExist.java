package com.addressbook.AdressBook.exception;

public class IdDoestNotExist extends RuntimeException{
    public IdDoestNotExist() {
        super();
    }

    public IdDoestNotExist(String message) {
        super(message);
    }
}
