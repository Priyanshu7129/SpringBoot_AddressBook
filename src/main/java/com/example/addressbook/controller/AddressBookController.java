package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private final AddressBookRepository repository;

    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AddressBookDTO> getAllContacts() {
        List<AddressBook> contacts = repository.findAll();
        return contacts.stream()
                .map(contact -> new AddressBookDTO(contact.getName(), contact.getPhoneNumber(), contact.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable Long id) {
        Optional<AddressBook> contact = repository.findById(id);
        return contact.map(value -> ResponseEntity.ok(new AddressBookDTO(value.getName(), value.getPhoneNumber(), value.getEmail())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AddressBookDTO addContact(@RequestBody AddressBookDTO dto) {
        AddressBook contact = new AddressBook(dto.getName(), dto.getPhoneNumber(), dto.getEmail());
        repository.save(contact);
        return dto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookDTO> updateContact(@PathVariable Long id, @RequestBody AddressBookDTO dto) {
        System.out.println("Received update request for ID: " + id); // Debugging log

        return repository.findById(id).map(contact -> {
            contact.setName(dto.getName());
            contact.setPhoneNumber(dto.getPhoneNumber());
            contact.setEmail(dto.getEmail());
            repository.save(contact);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> {
            System.out.println("Contact with ID " + id + " not found!"); // Debugging log
            return ResponseEntity.notFound().build();
        });
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
