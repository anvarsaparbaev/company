package com.example.company.services;

import com.example.company.entities.Company;
import com.example.company.entities.Department;
import com.example.company.payloads.DepartmentDTO;
import com.example.company.repositories.CompanyRepository;
import com.example.company.repositories.DepartmentRepository;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public List<Department> getDepartmentList() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer departmentId) {
        return departmentRepository.findById(departmentId).orElse(null);
    }

    public Result addNewDepartment(DepartmentDTO departmentDTO) {
        boolean existsDepartmentByName = departmentRepository.existsDepartmentByName(departmentDTO.getName());
        if (existsDepartmentByName) {
            return new Result("Department already exist!", false, HttpStatus.CONFLICT.value());
        }
        Department newDepartment = new Department();
        newDepartment.setName(departmentDTO.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (optionalCompany.isPresent()) {
            newDepartment.setCompany(optionalCompany.get());
            departmentRepository.save(newDepartment);
            return new Result("Department successfully saved!", true, HttpStatus.CREATED.value());
        } else {
            return new Result("Company not found!", false, HttpStatus.NOT_FOUND.value());
        }
    }

    public Result updateDepartmentById(DepartmentDTO departmentDTO, Integer departmentId) {
        boolean existsDepartmentByName = departmentRepository.existsDepartmentByName(departmentDTO.getName());
        if (existsDepartmentByName) {
            return new Result("This department already exist!", false, HttpStatus.CONFLICT.value());
        }
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            Department updatingDepartment = departmentOptional.get();
            updatingDepartment.setName(departmentDTO.getName());
            Optional<Company> companyOptional = companyRepository.findById(departmentDTO.getCompanyId());
            if (companyOptional.isPresent()) {
                updatingDepartment.setCompany(companyOptional.get());
                departmentRepository.save(updatingDepartment);
                return new Result("Department successfully updated!", true, HttpStatus.ACCEPTED.value());
            }
            return new Result("Company not found!", false, HttpStatus.NOT_FOUND.value());
        }
        return new Result("Department not found!", false, HttpStatus.NOT_FOUND.value());
    }

    public Result deleteDepartmentById(Integer departmentId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            departmentRepository.deleteById(departmentId);
            return new Result("Department deleted!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Department not found!", false, HttpStatus.NOT_FOUND.value());
    }
}