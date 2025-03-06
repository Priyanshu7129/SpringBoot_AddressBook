package com.example.addressbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Welcome to the Address Book App!";
    }
}
