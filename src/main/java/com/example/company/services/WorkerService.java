package com.example.company.services;

import com.example.company.entities.Address;
import com.example.company.entities.Worker;
import com.example.company.payloads.WorkerDTO;
import com.example.company.repositories.AddressRepository;
import com.example.company.repositories.DepartmentRepository;
import com.example.company.repositories.WorkersRepository;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkersRepository workersRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;

    public List<Worker> getAllWorkers() {
        return (List<Worker>) workersRepository.findAll();
    }

    public Worker getWorkerById(Integer workerId) {
        return workersRepository.findById(workerId).orElse(null);
    }

    public Result addWorker(WorkerDTO workerDTO) {
        boolean existsByPhoneNumber = workersRepository.existsByPhoneNumber(workerDTO.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("This phone number already exist!", false, HttpStatus.CONFLICT.value());
        }
        Worker newWorker = new Worker();
        newWorker.setName(workerDTO.getName());
        newWorker.setPhoneNumber(workerDTO.getPhoneNumber());

        Optional<Address> addressOptional = addressRepository.findById(workerDTO.getAddressId());
        addressOptional.ifPresent(newWorker::setAddress);

        departmentRepository
                .findById(workerDTO.getDepartmentId())
                .ifPresent(newWorker::setDepartment);

        workersRepository.save(newWorker);
        return new Result("Worker successfully added!", true, HttpStatus.CREATED.value());
    }

    public Result updateWorker(WorkerDTO workerDTO, Integer workerId) {
        boolean existsByPhoneNumber = workersRepository.existsByPhoneNumber(workerDTO.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("This phone number already exist!", false, HttpStatus.CONFLICT.value());
        }
        Optional<Worker> optionalWorker = workersRepository.findById(workerId);
        if (optionalWorker.isPresent()) {
            Worker updatingWorker = optionalWorker.get();
            updatingWorker.setName(workerDTO.getName());
            updatingWorker.setPhoneNumber(workerDTO.getPhoneNumber());

            Optional<Address> addressOptional = addressRepository.findById(workerDTO.getAddressId());
            addressOptional.ifPresent(updatingWorker::setAddress);

            departmentRepository
                    .findById(workerDTO.getDepartmentId())
                    .ifPresent(updatingWorker::setDepartment);

            workersRepository.save(updatingWorker);
            return new Result("Worker successfully updated!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Worker not found!", false, HttpStatus.NOT_FOUND.value());
    }

    public Result deleteWorker(Integer workerId) {
        Optional<Worker> optionalWorker = workersRepository.findById(workerId);
        if (optionalWorker.isPresent()) {
            workersRepository.deleteById(workerId);
            return new Result("Worker deleted!", true, HttpStatus.ACCEPTED.value());
        }
        return new Result("Worker not found!", false, HttpStatus.NOT_FOUND.value());
    }

}