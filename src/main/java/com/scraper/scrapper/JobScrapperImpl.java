package com.scraper.scrapper;

import com.scraper.model.dto.api.ApiJob;
import com.scraper.model.dto.api.ApiJobRequest;
import com.scraper.model.dto.api.ApiResponse;
import com.scraper.model.entity.Vacancy;
import com.scraper.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobScrapperImpl implements JobScraper {

    private final VacancyRepository vacancyRepository;
    private final RestTemplate restTemplate;

    @Value("${app.scraper.api-url}")
    private String apiUrl;

    @Value("${app.scraper.batch-size:50}")
    private int batchSize;

    @Override
    @Scheduled(cron = "${app.scraper.cron:0 0 3 * * *}")
    public void runScrapingProcess() {
        log.info("collecting data...");
        LocalDateTime startTime = LocalDateTime.now();
        int totalProcessed = 0;
        int page = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        while (true) {
            log.info("Loading a batch of data (page/offset: {})...", page);

            List<ApiJob> jobs = fetchJobsFromApi(page, headers);

            if (jobs == null || jobs.isEmpty()) {
                log.info("The data has run out.");
                break;
            }

            for (ApiJob apiJob : jobs) {
                saveOrUpdate(apiJob, startTime);
                totalProcessed++;
            }

            log.info("{} vacancies have been successfully saved. Total collected: {}", jobs.size(), totalProcessed);

            page++;
            sleep();
        }

        vacancyRepository.deactivateOldVacancies(startTime);
        log.info("The API request has been successfully completed! Total processed: {}", totalProcessed);
    }

    private List<ApiJob> fetchJobsFromApi(int page, HttpHeaders headers) {
        ApiJobRequest requestPayload = new ApiJobRequest(batchSize, page, "", null);
        HttpEntity<ApiJobRequest> request = new HttpEntity<>(requestPayload, headers);

        try {
            ApiResponse response = restTemplate.postForObject(apiUrl, request, ApiResponse.class);
            if (response != null && response.results() != null) {
                return response.results().jobs();
            }
        } catch (Exception e) {
            log.error("An error occurred during the API request on the {} page. Reason: {}", page, e.getMessage());
        }
        return null;
    }

    private void sleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Thread error:", e);
        }
    }

    private void saveOrUpdate(ApiJob apiJob, LocalDateTime scrapeTime) {
        if (apiJob.url() == null || apiJob.title() == null) return;

        String fullUrl = apiJob.url().startsWith("http") ? apiJob.url() : "https://jobs.techstars.com" + apiJob.url();

        Vacancy vacancy = vacancyRepository.findByUrl(fullUrl).orElseGet(Vacancy::new);

        vacancy.setTitle(apiJob.title());
        vacancy.setUrl(fullUrl);

        String companyName = (apiJob.organization() != null && apiJob.organization().name() != null)
                ? apiJob.organization().name()
                : "Unknown Company";
        vacancy.setCompanyName(companyName);

        String location = (apiJob.locations() != null && !apiJob.locations().isEmpty())
                ? String.join(", ", apiJob.locations())
                : "Unknown Location";
        vacancy.setLocation(location);

        String jobFunction = (apiJob.skills() != null && !apiJob.skills().isEmpty())
                ? String.join(", ", apiJob.skills())
                : "Not specified";
        vacancy.setJobFunction(jobFunction);

        vacancy.setActive(true);
        vacancy.setLastUpdated(scrapeTime);

        vacancyRepository.save(vacancy);
    }
}