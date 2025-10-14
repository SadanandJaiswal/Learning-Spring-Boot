package com.hclSoftware.learningRESTAPIs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddStudentRequestDto {

    @NotBlank(message = "Name is Required")
    @Size(min=3 , max=32, message = "Name should be of length 3 to 32 characters")
    private String name;

    @Email
    @NotBlank(message = "Email is Required")
    private String email;

    public AddStudentRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
