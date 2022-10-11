package com.example.company.controllers;

import com.example.company.entities.Company;
import com.example.company.payloads.CompanyDTO;
import com.example.company.services.CompanyService;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService service;

    @GetMapping
    private ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(service.getAllCompanies());
    }

    @GetMapping("/{companyId}")
    private ResponseEntity<Company> getCompany(@Valid @PathVariable Integer companyId) {
        return ResponseEntity.ok(service.getCompanyById(companyId));
    }

    @PostMapping
    private ResponseEntity<Result> addCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity
                .status(service.addNewCompany(companyDTO).getStatus())
                .body(service.addNewCompany(companyDTO));
    }

    @PutMapping("/{companyId}")
    private ResponseEntity<Result> updateCompany(@Valid @PathVariable Integer companyId,
                                                 @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity
                .status(service.updateCompanyById(companyId, companyDTO).getStatus())
                .body(service.updateCompanyById(companyId, companyDTO));
    }

    @DeleteMapping("/{companyId}")
    private ResponseEntity<Result> deleteCompany(@Valid @PathVariable Integer companyId) {
        return ResponseEntity
                .status(service.deleteCompanyById(companyId).getStatus())
                .body(service.deleteCompanyById(companyId));
    }

}