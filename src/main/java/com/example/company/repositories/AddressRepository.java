package com.example.company.repositories;

import com.example.company.entities.Address;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {

    boolean existsByStreet(@NotNull String street);

}