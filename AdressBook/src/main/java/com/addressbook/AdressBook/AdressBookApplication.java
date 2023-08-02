package com.addressbook.AdressBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.addressbook")

public class AdressBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdressBookApplication.class, args);
	}

}
