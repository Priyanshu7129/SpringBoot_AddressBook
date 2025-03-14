package com.example.addressbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only contain letters and spaces")
    private String name;

    private String phoneNumber;
    private String email;
}
