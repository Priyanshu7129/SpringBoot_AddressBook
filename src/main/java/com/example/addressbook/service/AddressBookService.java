package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final AddressBookRepository repository;

    public AddressBookService(AddressBookRepository repository) {
        this.repository = repository;
    }

    public List<AddressBookDTO> getAllContacts() {
        List<AddressBook> contacts = repository.findAll();
        return contacts.stream()
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail())) // Include ID
                .collect(Collectors.toList());
    }

    public Optional<AddressBookDTO> getContactById(Long id) {
        return repository.findById(id)
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail())); // Include ID
    }

    public AddressBookDTO addContact(AddressBookDTO dto) {
        AddressBook contact = new AddressBook(dto.getName(), dto.getPhoneNumber(), dto.getEmail());
        AddressBook savedContact = repository.save(contact);
        return new AddressBookDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhoneNumber(), savedContact.getEmail()); // Include ID
    }

    public Optional<AddressBookDTO> updateContact(Long id, AddressBookDTO dto) {
        return repository.findById(id).map(contact -> {
            contact.setName(dto.getName());
            contact.setPhoneNumber(dto.getPhoneNumber());
            contact.setEmail(dto.getEmail());
            repository.save(contact);
            return new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail()); // Include ID
        });
    }

    public boolean deleteContact(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
