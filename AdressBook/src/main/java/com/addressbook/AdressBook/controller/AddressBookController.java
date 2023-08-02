package com.addressbook.AdressBook.controller;

import com.addressbook.AdressBook.model.BusinessCard;
import com.addressbook.AdressBook.model.State;
import com.addressbook.AdressBook.service.AddressBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business-card")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    private Logger logger = LoggerFactory.getLogger(AddressBookController.class);



    @PostMapping("/known")
    public ResponseEntity<Void> uploadKnownBusinessCard(@RequestBody BusinessCard businessCard){
        try{
            addressBookService.uploadKnownBusinessCard(businessCard);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            logger.error("Error processing known business card with id {}", businessCard.getId());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/unknown")
    public ResponseEntity<Void> uploadUnknownBusinessCard(@RequestBody BusinessCard businessCard){
        try{
            addressBookService.uploadUnknownBusinessCard(businessCard);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            logger.error("Error processing unknown business card with id {}", businessCard.getId());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/states")
    public ResponseEntity<List<BusinessCard>> retrieveBusinessCardByState(@RequestParam State state){
        try {
            List<BusinessCard> cards = addressBookService.retrieveBusinessCardByState(state);
            return ResponseEntity.ok(cards);
        }catch (Exception e){
            logger.error("Error retrieving business by state {} ",state, e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
