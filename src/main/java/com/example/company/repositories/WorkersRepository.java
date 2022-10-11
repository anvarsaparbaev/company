package com.example.company.repositories;

import com.example.company.entities.Worker;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface WorkersRepository extends PagingAndSortingRepository<Worker, Integer> {

    boolean existsByPhoneNumber(@NotNull String phoneNumber);

}