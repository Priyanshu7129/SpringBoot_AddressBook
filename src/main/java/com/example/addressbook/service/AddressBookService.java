package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final List<AddressBook> addressBookList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // To generate unique IDs

    public List<AddressBookDTO> getAllContacts() {
        return addressBookList.stream()
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail()))
                .collect(Collectors.toList());
    }

    public Optional<AddressBookDTO> getContactById(Long id) {
        return addressBookList.stream()
                .filter(contact -> contact.getId().equals(id))
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail()))
                .findFirst();
    }

    public AddressBookDTO addContact(AddressBookDTO dto) {
        AddressBook contact = new AddressBook(idCounter.getAndIncrement(), dto.getName(), dto.getPhoneNumber(), dto.getEmail());
        addressBookList.add(contact);
        return new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail());
    }

    public Optional<AddressBookDTO> updateContact(Long id, AddressBookDTO dto) {
        return addressBookList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .map(contact -> {
                    contact.setName(dto.getName());
                    contact.setPhoneNumber(dto.getPhoneNumber());
                    contact.setEmail(dto.getEmail());
                    return new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail());
                });
    }

    public boolean deleteContact(Long id) {
        return addressBookList.removeIf(contact -> contact.getId().equals(id));
    }
}
