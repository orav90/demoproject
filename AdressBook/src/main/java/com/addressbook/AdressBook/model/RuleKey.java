package com.addressbook.AdressBook.model;

import java.util.Objects;

public class RuleKey {

    private State state;
    private AddressBookEvent event;

    public RuleKey(State state, AddressBookEvent event) {
        this.state = state;
        this.event = event;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public AddressBookEvent getEvent() {
        return event;
    }

    public void setEvent(AddressBookEvent event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleKey ruleKey = (RuleKey) o;
        return state == ruleKey.state && event == ruleKey.event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, event);
    }
}
