package com.example.addressbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity  // Mark this class as a JPA entity
public class AddressBook {

    @Id  // Define 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
}
