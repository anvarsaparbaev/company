package com.example.company.controllers;

import com.example.company.entities.Worker;
import com.example.company.payloads.WorkerDTO;
import com.example.company.services.WorkerService;
import com.example.company.templates.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService service;

    @GetMapping
    private ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok(service.getAllWorkers());
    }

    @GetMapping("/{workerId}")
    private ResponseEntity<Worker> getWorkerById(@PathVariable Integer workerId) {
        return ResponseEntity.ok(service.getWorkerById(workerId));
    }

    @PostMapping
    private ResponseEntity<Result> addWorker(@Valid @RequestBody WorkerDTO workerDTO) {
        Result addWorker = service.addWorker(workerDTO);
        return ResponseEntity.status(addWorker.getStatus()).body(addWorker);
    }

    @PutMapping("/{workerId}")
    private ResponseEntity<Result> updateWorker(@Valid @RequestBody WorkerDTO workerDTO,
                                                @PathVariable Integer workerId) {
        Result updateWorker = service.updateWorker(workerDTO, workerId);
        return ResponseEntity.status(updateWorker.getStatus()).body(updateWorker);
    }

    @DeleteMapping("/{workerId}")
    private ResponseEntity<Result> deleteWorker(@PathVariable Integer workerId) {
        Result deleteWorker = service.deleteWorker(workerId);
        return ResponseEntity.status(deleteWorker.getStatus()).body(deleteWorker);
    }

}