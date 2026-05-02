package com.scraper.repository;

import com.scraper.model.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
    Optional<Vacancy> findByUrl(String url);

    @Modifying
    @Transactional
    @Query("UPDATE Vacancy v SET v.active = false WHERE v.lastUpdated < :thresholdTime")
    void deactivateOldVacancies(LocalDateTime thresholdTime);
}