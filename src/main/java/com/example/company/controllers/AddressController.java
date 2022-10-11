package com.example.company.controllers;

import com.example.company.entities.Address;
import com.example.company.services.AddressService;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService service;

    /**
     * Get all Addresses
     * <p>
     * You can see the entire address of the company, employees and departments.
     * Use this method you will get a complete list of addresses
     */
    @GetMapping
    private ResponseEntity<List<Address>> getAddressesList() {
        return ResponseEntity.ok(service.getAllAddresses());
    }

    /**
     * Get Address by ID
     * <p>
     * From the list of addresses,
     * you can find the address that suits you. Using this method,
     * the address that you sent to the database will be transferred from the Database to you
     *
     * @param addressId
     */

    @GetMapping("/{addressId}")
    private ResponseEntity<Address> getAddressById(@PathVariable Integer addressId) {
        return ResponseEntity.ok(service.getAddressById(addressId));
    }

    /**
     * Create address
     * <p>
     * add new addresses to the Data Base (PostgreSQL)
     * in order to use these addresses later!
     *
     * @param address
     * @return {@value }
     */

    @PostMapping
    private ResponseEntity<Result> addAddress(@Valid @RequestBody Address address) {
        return ResponseEntity
                .status(service.addNewAddress(address).getStatus())
                .body(service.addNewAddress(address));
    }

    /**
     * Update Address by ID
     * <p>
     * Sometimes, for spelling or other reasons,
     * people mistakenly write down,
     * in order to correct this error,
     * this method has been developed.
     *
     * @param addressId
     * @param address
     * @return
     */

    @PutMapping("/{addressId}")
    private ResponseEntity<Result> updateAddress(@Valid @PathVariable Integer addressId,
                                                 @RequestBody Address address) {
        return ResponseEntity
                .status(service.updateAddressById(addressId, address).getStatus())
                .body(service.updateAddressById(addressId, address));
    }

    /**
     * Delete Address By ID
     * <p>
     * There are outdated addresses in the program,
     * in order to clear them, you can use this method
     *
     * @param addressId
     * @return {@value }
     */

    @DeleteMapping("/{addressId}")
    private ResponseEntity<Result> deleteAddress(@Valid @PathVariable Integer addressId) {
        return ResponseEntity
                .status(service.deletAddressById(addressId).getStatus())
                .body(service.deletAddressById(addressId));
    }

}