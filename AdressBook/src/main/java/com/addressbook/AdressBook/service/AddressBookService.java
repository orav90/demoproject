package com.addressbook.AdressBook.service;

import com.addressbook.AdressBook.exception.IdDoestNotExist;
import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.model.State;
import com.addressbook.AdressBook.repository.AddressBookRepository;
import com.addressbook.AdressBook.model.AddressBookEvent;
import com.addressbook.AdressBook.statemachine.AddressBookMachine;
import com.addressbook.AdressBook.model.RuleKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookService {
    private Logger logger = LoggerFactory.getLogger(AddressBookService.class);

    @Autowired
    private AddressBookMachine addressBook;

    @Autowired
    private AddressBookRepository addressBookRepository;

    private BusinessCard currentCard = null;

    public void uploadKnownBusinessCard(BusinessCard businessCard) {
        currentCard = businessCard;
        addressBook.saveBusinessCard(businessCard, State.KNOWN);
    }

    public void uploadUnknownBusinessCard(BusinessCard businessCard) {
        currentCard = businessCard;
        addressBook.saveBusinessCard(businessCard, State.UNKNOWN);
    }

    public List<BusinessCard> retrieveBusinessCardByState(State state) {

        List<BusinessCard> cards = addressBookRepository.findByState(state);
        return cards;
    }

    //the state machine logic is implemented here by using the transition table
    public void update(BusinessCard card, AddressBookEvent event){
        if(!card.equals(currentCard)){
            currentCard = fetchCard(card.getId());
        }
        RuleKey key = new RuleKey(card.getState(),event);
        State nextState = addressBook.getNextState(key);
        if(nextState != null){
            addressBook.handleAction(nextState, card);
        } else{
            logger.warn("illegal action for card id {}: event {} is not defined for state {}",card.getId() ,event.name(), card.getState());
        }

    }

    @Transactional(readOnly = true)
    private BusinessCard fetchCard(long id){
        return addressBookRepository.findById(id).orElseThrow(() ->{
            logger.error("Could not fetch card with id {}, since it does not exist", id);
            throw new IdDoestNotExist("id does not exist");
        });
    }
}
