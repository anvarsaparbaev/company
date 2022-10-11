package com.example.company.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {
    private String name;
    private String phoneNumber;
    private Integer addressId;
    private Integer departmentId;
}