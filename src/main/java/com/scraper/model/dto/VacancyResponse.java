package com.scraper.model.dto;

public record VacancyResponse(
        String title,
        String companyName,
        String location,
        String url,
        boolean active
) {
}
