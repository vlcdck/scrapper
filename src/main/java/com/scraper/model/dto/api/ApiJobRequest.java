package com.scraper.model.dto.api;

public record ApiJobRequest(
        int hitsPerPage,
        int page,
        String query,
        Object filters
) {}