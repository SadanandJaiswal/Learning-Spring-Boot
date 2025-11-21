package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnboardDoctorRequestDto {
    private Long userId;
    private String specialization;
    private String name;
}
