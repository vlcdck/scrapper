package com.scraper.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import com.scraper.model.dto.VacancyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.scraper.service.VacancyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    @Operation(summary = "Get vacancies with pagination and filters")
    public Page<VacancyResponse> getVacancies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false, defaultValue = "true") Boolean active,
            Pageable pageable) {

        return vacancyService.getVacancies(title, location, companyName, active, pageable);
    }
}