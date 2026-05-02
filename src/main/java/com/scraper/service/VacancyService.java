package com.scraper.service;

import com.scraper.model.dto.VacancyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {
    Page<VacancyResponse> getVacancies(String title, String location, String companyName, Boolean active, Pageable pageable);
}