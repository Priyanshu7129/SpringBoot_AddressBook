package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.exception.AddressBookNotFoundException;
import com.example.addressbook.model.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService {

    private final List<AddressBook> addressBookList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<AddressBookDTO> getAllContacts() {
        log.debug("Fetching all contacts...");
        return addressBookList.stream()
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail()))
                .collect(Collectors.toList());
    }

    public AddressBookDTO getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        return addressBookList.stream()
                .filter(contact -> contact.getId().equals(id))
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail()))
                .findFirst()
                .orElseThrow(() -> new AddressBookNotFoundException("Contact with ID " + id + " not found"));
    }


    public AddressBookDTO addContact(AddressBookDTO dto) {
        AddressBook contact = new AddressBook(idCounter.getAndIncrement(), dto.getName(), dto.getPhoneNumber(), dto.getEmail());
        addressBookList.add(contact);
        log.info("Added new contact: {}", contact);
        return new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail());
    }

    public Optional<AddressBookDTO> updateContact(Long id, AddressBookDTO dto) {
        log.info("Updating contact with ID: {}", id);
        return addressBookList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .map(contact -> {
                    contact.setName(dto.getName());
                    contact.setPhoneNumber(dto.getPhoneNumber());
                    contact.setEmail(dto.getEmail());
                    log.info("Updated contact: {}", contact);
                    return new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail());
                });
    }
    public boolean deleteContact(Long id) {
        log.warn("Deleting contact with ID: {}", id);
        return addressBookList.removeIf(contact -> contact.getId().equals(id));
    }

}
