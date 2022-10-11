package com.example.company.services;

import com.example.company.entities.Address;
import com.example.company.repositories.AddressRepository;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;


    public List<Address> getAllAddresses() {
        return (List<Address>) repository.findAll();
    }


    public Address getAddressById(Integer addressId) {
        Optional<Address> addressOptional = repository.findById(addressId);
        return addressOptional.orElse(null);
    }

    public Result addNewAddress(Address address) {
        boolean existsByStreet = repository.existsByStreet(address.getStreet());
        if (existsByStreet) {
            return new Result("This street already exist!", false, HttpStatus.CONFLICT.value());
        } else {
            repository.save(address);
            return new Result("Address successfully saved!", true, HttpStatus.CREATED.value());
        }
    }

    public Result updateAddressById(Integer addressId, Address address) {
        boolean existsByStreet = repository.existsByStreet(address.getStreet());
        if (existsByStreet) {
            return new Result("This street already exist!", false, HttpStatus.CONTINUE.value());
        }
        Optional<Address> addressOptional = repository.findById(addressId);
        if (addressOptional.isPresent()) {
            Address updatingAddress = addressOptional.get();
            updatingAddress.setStreet(address.getStreet());
            updatingAddress.setHomeNumber(address.getHomeNumber());
            repository.save(updatingAddress);
            return new Result("Address successfully updated!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Address not found!", false, HttpStatus.NOT_FOUND.value());
    }

    public Result deletAddressById(Integer addressId) {
        Optional<Address> addressOptional = repository.findById(addressId);
        if (addressOptional.isPresent()) {
            repository.deleteById(addressId);
            return new Result("Address deleted!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Address not found!", false, HttpStatus.NOT_FOUND.value());
    }
}