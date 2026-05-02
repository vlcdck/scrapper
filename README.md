# Techstars Jobs Scraper API

A high-performance Spring Boot application designed to automatically collect, process, and serve job vacancy data from the Techstars platform.

### The Core Idea
Instead of relying on heavy, slow, and fragile HTML scraping (like Selenium or deep Jsoup parsing), this application intercepts the underlying **JSON API** used by the Techstars website. This architectural decision guarantees blazing-fast execution, extreme reliability, and immunity to frontend layout changes.

## Features

* **Smart Data Extraction:** Directly queries the external JSON API for fast and structured data retrieval without overwhelming the source server.
* **Automated Sync:** Runs a background scheduled task (Cron) to fetch new jobs, update existing ones, and automatically deactivate those no longer present.
* **Advanced Filtering:** Explore the jobs via REST API using dynamic filters (`title`, `location`, `companyName`, `active` status) powered by Spring Data JPA Specifications.
* **Pagination:** Implements standard Spring Boot pagination for optimized data delivery.
* **Dockerized Environment:** Fully containerized with PostgreSQL for zero-config local deployment.
* **Interactive API Docs:** Built-in Swagger UI for easy API testing and exploration.

## Tech Stack

* **Java 21**
* **Spring Boot 3.x** (Web, Data JPA)
* **PostgreSQL 15**
* **Docker & Docker Compose**
* **Maven**

## API Endpoints

**Base Path:** `/api/v1/vacancies`

* `GET /` - Retrieve a paginated list of vacancies.
    * **Query Parameters:**
        * `title` (String) - Filter by job title.
        * `location` (String) - Filter by job location.
        * `companyName` (String) - Filter by hiring company.
        * `active` (Boolean) - Filter by active status (defaults to `true`).
        * `page` (Integer) - Page number (default: 0).
        * `size` (Integer) - Elements per page (default: 20).

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

P.S.

To ensure maximum scraping speed and prevent IP blocking, the application fetches job summaries via a single API call. Detailed descriptions are omitted in this version to prioritize efficiency as per the technical requirements.