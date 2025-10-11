package com.hclSoftware.learningRESTAPIs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor   // Sometime its not working (many time usually)
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String email;

    public StudentDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
