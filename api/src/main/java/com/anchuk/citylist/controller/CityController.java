package com.anchuk.citylist.controller;

import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.dto.city.CityUpdateRequest;
import com.anchuk.citylist.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/cities", produces = "application/json")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<CitiesResponse> getAllCities(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(required = false) String search) {
        return ResponseEntity.ok(cityService.getAllCities(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @Valid @RequestBody CityUpdateRequest city) {
        return ResponseEntity.ok(cityService.updateCity(id, city));
    }

}
