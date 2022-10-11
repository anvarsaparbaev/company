package com.example.company.controllers;

import com.example.company.entities.Department;
import com.example.company.payloads.DepartmentDTO;
import com.example.company.services.DepartmentService;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    private ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(service.getDepartmentList());
    }

    @GetMapping("/{departmentId}")
    private ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId) {
        return ResponseEntity.ok(service.getDepartmentById(departmentId));
    }

    @PostMapping
    private ResponseEntity<Result> addDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity
                .status(service.addNewDepartment(departmentDTO).getStatus())
                .body(service.addNewDepartment(departmentDTO));
    }

    @PutMapping("/{departmentId}")
    private ResponseEntity<Result> updateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO,
                                                    @PathVariable Integer departmentId) {
        return ResponseEntity
                .status(service.updateDepartmentById(departmentDTO, departmentId).getStatus())
                .body(service.updateDepartmentById(departmentDTO, departmentId));
    }

    @DeleteMapping("/{departmentId}")
    private ResponseEntity<Result> deleteDepartment(@PathVariable Integer departmentId) {
        return ResponseEntity
                .status(service.deleteDepartmentById(departmentId).getStatus())
                .body(service.deleteDepartmentById(departmentId));
    }
}