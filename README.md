# library-management-system
Library Management System is a full-stack application built with Next.js and Spring Boot. It automates core library tasks, featuring secure JWT authentication, efficient book/user management.

## Features

- **Backend:** A robust REST API built with Spring Boot that handles all the business logic of the library management system.
- **Frontend:** A modern and responsive user interface built with Next.js.
- **Database:** PostgreSQL is used for persistent data storage.
- **Cache:** Redis is used for caching to improve performance.
- **Containerization:** The entire application is containerized using Docker for easy setup and deployment.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Docker](https://www.docker.com/get-started) and [Docker Compose](https://docs.docker.com/compose/install/)

### Setup

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/yigit-7/library-management-system.git
    cd library-management-system
    ```

2.  **Configure environment variables:**

    Create a `.env` file in the root of the project by copying the example file:

    ```bash
    cp .env.example .env
    ```

    Now, open the `.env` file and fill in the required environment variables:

    - `POSTGRES_USER`: The username for the PostgreSQL database.
    - `POSTGRES_PASSWORD`: The password for the PostgreSQL database.
    - `POSTGRES_DB`: The name of the PostgreSQL database.
    - `BACKEND_PORT`: The port on which the backend server will run (defaults to 8080).

3.  **Run the application with Docker Compose:**

    ```bash
    docker-compose -f compose.yaml up --build
    ```

    This command will build the Docker images for the frontend and backend services and start all the services defined in the `compose.yaml` file.

    The services include:
    - `database`: The PostgreSQL database.
    - `backend`: The Spring Boot application.
    - `cache`: The Redis cache.
    - `frontend`: The Next.js application.

4.  **Access the application:**

    - The frontend will be available at [http://localhost:3000](http://localhost:3000).
    - The backend API will be available at [http://localhost:8080](http://localhost:8080).

## Project Structure

This project is a monorepo containing two main applications:

- `apps/spring-boot-app`: The backend application, built with Spring Boot.
- `apps/nextjs-app`: The frontend application, built with Next.js.

## Technologies Used

### Backend

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Redis
- Lombok
- Maven

### Frontend

- Next.js
- React
- TypeScript
- Tailwind CSS
