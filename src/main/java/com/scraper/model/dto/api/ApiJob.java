package com.scraper.model.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiJob(
        String title,
        String url,
        List<String> locations,
        Organization organization,
        List<String> skills
) {
}