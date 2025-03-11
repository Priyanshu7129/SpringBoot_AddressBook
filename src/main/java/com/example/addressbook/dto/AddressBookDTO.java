package com.example.addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor  // Generates a No-Args Constructor
@AllArgsConstructor  // Generates an All-Args Constructor
public class AddressBookDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
