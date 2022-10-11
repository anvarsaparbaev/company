package com.example.company.services;

import com.example.company.entities.Address;
import com.example.company.entities.Company;
import com.example.company.payloads.CompanyDTO;
import com.example.company.repositories.AddressRepository;
import com.example.company.repositories.CompanyRepository;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public List<Company> getAllCompanies() {
        return (List<Company>) companyRepository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public Result addNewCompany(CompanyDTO companyDTO) {
        Company newCompany = new Company();
        newCompany.setCompanyName(companyDTO.getCompanyName());
        newCompany.setDirectorName(companyDTO.getDirectorName());
        Optional<Address> addressOptional = addressRepository.findById(companyDTO.getAddressId());
        if (addressOptional.isPresent()) {
            newCompany.setAddress(addressOptional.get());
            companyRepository.save(newCompany);
            return new Result("Company successfully saved!", true, HttpStatus.CREATED.value());
        }
        return new Result("Company not found!", false, HttpStatus.NOT_FOUND.value());
    }

    public Result updateCompanyById(Integer companyId, CompanyDTO companyDTO) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            Company updatingCompany = companyOptional.get();
            updatingCompany.setCompanyName(companyDTO.getCompanyName());
            updatingCompany.setDirectorName(companyDTO.getDirectorName());
            Optional<Address> addressOptional = addressRepository.findById(companyDTO.getAddressId());
            if (addressOptional.isPresent()) {
                updatingCompany.setAddress(addressOptional.get());
                companyRepository.save(updatingCompany);
                return new Result("Company successfully updated!", true, HttpStatus.ACCEPTED.value());
            }
            return new Result("Address not found!", false, HttpStatus.NOT_FOUND.value());
        }
        return new Result("Address not found!", false, HttpStatus.NOT_FOUND.value());
    }

    public Result deleteCompanyById(Integer companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            companyRepository.deleteById(companyId);
            return new Result("Company deleted!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Company not found!", false, HttpStatus.NOT_FOUND.value());
    }
}