package com.catalogue.verg.insecticide.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import com.catalogue.verg.insecticide.service.InsecticideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/insecticide")
public class InsecticideController {
    @Autowired
    private InsecticideService insecticideService;

    @PostMapping("/v1/create")
    public ResponseEntity<CustomResponse> create(@RequestBody JsonNode insecticideDetails) {
        CustomResponse response = insecticideService.createInsecticide(insecticideDetails);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    @PostMapping("/v1/draft")
    public ResponseEntity<CustomResponse> draft(@RequestBody JsonNode insecticideDetails) {
        CustomResponse response = insecticideService.draftInsecticide(insecticideDetails);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Lifecycle: create a new record submitted for approval (PENDING, full validation)
    @PostMapping("/v1/add")
    public ResponseEntity<CustomResponse> add(@RequestBody JsonNode insecticideDetails) {
        CustomResponse response = insecticideService.createInsecticide(insecticideDetails);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Lifecycle: (re-)submit an existing DRAFT/REWORK record for approval (PENDING, full validation)
    @PutMapping("/v1/add/{id}")
    public ResponseEntity<CustomResponse> addById(@PathVariable String id, @RequestBody JsonNode insecticideDetails) {
        CustomResponse response = insecticideService.addInsecticide(id, insecticideDetails);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    @PutMapping("/v1/approve")
    public ResponseEntity<CustomResponse> approve(@RequestBody LifecycleRequest request) {
        CustomResponse response = insecticideService.approveInsecticide(request);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    @PutMapping("/v1/review")
    public ResponseEntity<CustomResponse> review(@RequestBody LifecycleRequest request) {
        CustomResponse response = insecticideService.reviewInsecticide(request);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    @PutMapping("/v1/toggle/{id}")
    public ResponseEntity<CustomResponse> toggle(@PathVariable String id) {
        CustomResponse response = insecticideService.toggleStatus(id);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    @PostMapping("/v1/search")
    public ResponseEntity<?> search(@RequestBody SearchCriteria searchCriteria) {
        CustomResponse response = insecticideService.searchInsecticide(searchCriteria);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    @GetMapping("/v1/read/{id}")
    public ResponseEntity<?> read(@PathVariable String id) {
        CustomResponse response = insecticideService.read(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/v1/update/{id}")
    public ResponseEntity<CustomResponse> update(@PathVariable String id, @RequestBody JsonNode insecticideDetails) {
        CustomResponse response = insecticideService.updateInsecticide(id, insecticideDetails);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        CustomResponse response = insecticideService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1/import")
    public ResponseEntity<CustomResponse> importData(@RequestParam("file") MultipartFile file) {
        CustomResponse response = insecticideService.importData(file);
        return new ResponseEntity<>(response, response.getResponseCode());
    }
}