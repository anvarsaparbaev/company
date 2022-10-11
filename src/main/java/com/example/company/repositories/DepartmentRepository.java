package com.example.company.repositories;

import com.example.company.entities.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer> {

    boolean existsDepartmentByName(@NotNull String name);
    
}