package com.addressbook.AdressBook.statemachine;

import com.addressbook.AdressBook.model.AddressBookEvent;
import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.model.RuleKey;
import com.addressbook.AdressBook.model.State;
import com.addressbook.AdressBook.repository.AddressBookRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddressBookMachine {

    private AddressBookState known;
    private AddressBookState unknown;
    private AddressBookState additionalVerification;
    private AddressBookState manualApproved;
    private AddressBookState pendingVerification;
    private AddressBookState strongApproved;

    @Autowired
    private AddressBookRepository addressBookRepository;

    private Map<RuleKey,State> transitionMap;
    private Map<State, AddressBookState> statesMap;

    public AddressBookMachine() {
        createTransitionMap();
        createMachineState();
    }

    private void createMachineState() {
        statesMap = new HashMap<>();
        known = new KnownState(this, State.KNOWN);
        unknown = new UnknownState(this, State.UNKNOWN);
        additionalVerification = new AdditionalVerificationState(this, State.ADDITIONAL_VERIFICATION);
        manualApproved = new ManualApprovedState(this, State.MANUAL_APPROVED);
        pendingVerification = new PendingVerificationState(this, State.PENDING_VERIFICATION);
        strongApproved = new StrongApprovedState(this, State.STRONG_APPROVED);

        statesMap.put(State.KNOWN,known);
        statesMap.put(State.UNKNOWN,unknown);
        statesMap.put(State.ADDITIONAL_VERIFICATION,additionalVerification);
        statesMap.put(State.MANUAL_APPROVED,manualApproved);
        statesMap.put(State.PENDING_VERIFICATION,pendingVerification);
        statesMap.put(State.STRONG_APPROVED,strongApproved);
    }

    private void createTransitionMap() {
        transitionMap = new HashMap<>();
        transitionMap.put(new RuleKey(State.UNKNOWN, AddressBookEvent.CONTACT_ADDRESS), State.PENDING_VERIFICATION);
        transitionMap.put(new RuleKey(State.PENDING_VERIFICATION, AddressBookEvent.ABORT), State.UNKNOWN);
        transitionMap.put(new RuleKey(State.PENDING_VERIFICATION, AddressBookEvent.STRONG_VERIFICATION), State.STRONG_APPROVED);
        transitionMap.put(new RuleKey(State.KNOWN, AddressBookEvent.CONTACT_ADDRESS), State.ADDITIONAL_VERIFICATION);
        transitionMap.put(new RuleKey(State.ADDITIONAL_VERIFICATION, AddressBookEvent.ABORT), State.KNOWN);
        transitionMap.put(new RuleKey(State.ADDITIONAL_VERIFICATION, AddressBookEvent.STRONG_VERIFICATION), State.STRONG_APPROVED);
        transitionMap.put(new RuleKey(State.KNOWN, AddressBookEvent.MANUAL_VERIFICATION), State.MANUAL_APPROVED);
        transitionMap.put(new RuleKey(State.MANUAL_APPROVED, AddressBookEvent.UNVERIFY), State.KNOWN);
    }

    @Transactional
    public void saveBusinessCard(BusinessCard businessCard, State state){
        businessCard.setState(state);
        addressBookRepository.save(businessCard);
    }

    public AddressBookState getKnown() {
        return known;
    }

    public AddressBookState getUnknown() {
        return unknown;
    }

    public AddressBookState getAdditionalVerification() {
        return additionalVerification;
    }

    public AddressBookState getManualApproved() {
        return manualApproved;
    }

    public AddressBookState getPendingVerification() {
        return pendingVerification;
    }

    public AddressBookState getStrongApproved() {
        return strongApproved;
    }

    public AddressBookRepository getAddressBookRepository() {
        return addressBookRepository;
    }

    public State getNextState(RuleKey key){
        return transitionMap.get(key);
    }

    public void handleAction(State state, BusinessCard card) {
        statesMap.get(state).handle(card);
    }

}
