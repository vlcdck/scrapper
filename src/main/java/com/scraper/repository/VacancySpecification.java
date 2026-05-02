package com.scraper.repository;

import com.scraper.model.entity.Vacancy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class VacancySpecification {

    private VacancySpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Vacancy> filterBy(String title, String location, String companyName, Boolean active) {
        return Specification.allOf(
                hasTitle(title),
                hasLocation(location),
                hasCompanyName(companyName),
                isActive(active)
        );
    }

    private static Specification<Vacancy> hasTitle(String title) {
        return (root, query, cb) -> StringUtils.hasText(title) ?
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%") : null;
    }

    private static Specification<Vacancy> hasLocation(String location) {
        return (root, query, cb) -> StringUtils.hasText(location) ?
                cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%") : null;
    }

    private static Specification<Vacancy> hasCompanyName(String companyName) {
        return (root, query, cb) -> StringUtils.hasText(companyName) ?
                cb.like(cb.lower(root.get("companyName")), "%" + companyName.toLowerCase() + "%") : null;
    }

    private static Specification<Vacancy> isActive(Boolean active) {
        return (root, query, cb) -> active != null ? cb.equal(root.get("active"), active) : null;
    }
}
