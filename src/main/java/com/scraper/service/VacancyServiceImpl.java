package com.scraper.service;

import lombok.RequiredArgsConstructor;
import com.scraper.model.dto.VacancyResponse;
import com.scraper.model.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.scraper.repository.VacancyRepository;
import com.scraper.repository.VacancySpecification;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<VacancyResponse> getVacancies(String title, String location, String companyName, Boolean active, Pageable pageable) {
        Specification<Vacancy> spec = VacancySpecification.filterBy(title, location, companyName, active);

        return vacancyRepository.findAll(spec, pageable)
                .map(this::mapToDto);
    }

    private VacancyResponse mapToDto(Vacancy vacancy) {
        return new VacancyResponse(
                vacancy.getTitle(),
                vacancy.getCompanyName(),
                vacancy.getLocation(),
                vacancy.getUrl(),
                vacancy.isActive()
        );
    }
}
