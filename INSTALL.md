# Installation & Setup Guide

This guide describes how to run the Techstars Jobs Scraper application locally. The project is fully containerized, so you don't need to install Java, Maven, or PostgreSQL directly on your host machine.

## Prerequisites

* **Docker** installed and running.
* **Docker Compose** installed.
* **Git** (to clone the repository).

## How to Run (Using Docker)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/vlcdck/scrapper
   cd scrapper
   ```

2. **Start the application:**
   Run the following command in the root directory of the project (where the `docker-compose.yml` file is located):
   ```bash
   docker compose up --build -d
   ```
   *Note: This command will pull the necessary images, compile the Maven project, create the PostgreSQL database, and start the Spring Boot application in the background.*

3. **Check the application logs (optional):**
   To see the application starting up and the scraper performing its initial data fetch:
   ```bash
   docker logs -f techstars_api
   ```

4. **Access the Application:**
    * **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    * **API Example:** [http://localhost:8080/api/v1/vacancies?page=0&size=20](http://localhost:8080/api/v1/vacancies?page=0&size=20)

## Database Dump (Result of Task #2)

As requested in the technical task, a manual data dump is provided.
You can find the SQL dump file named `dump.sql` in the root directory of this repository. It contains the schema and the scraped vacancy data to demonstrate that the application successfully collected the information.

## How to Stop

To stop the containers and remove them (without deleting the database volume):
```bash
docker compose down
```

To completely wipe everything (including the database volume):
```bash
docker compose down -v
```