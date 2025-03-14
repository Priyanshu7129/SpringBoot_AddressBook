package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.service.AddressBookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private final AddressBookService service;

    public AddressBookController(AddressBookService service) {
        this.service = service;
    }

    @GetMapping
    public List<AddressBookDTO> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable Long id) {
        Optional<AddressBookDTO> contact = service.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(service.addContact(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @Valid @RequestBody AddressBookDTO dto) {
        Optional<AddressBookDTO> updatedContact = service.updateContact(id, dto);
        return updatedContact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
