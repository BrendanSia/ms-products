# My Spring Boot Project

## Overview

This project is a Spring Boot application utilizing Java with a PostgreSQL database for production and H2 for testing. The project includes Liquibase for database migrations.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Java**: Programming language.
- **Liquibase**: Tool for database versioning and migrations.
- **PostgreSQL**: Database for production.
- **H2**: In-memory database for testing.

## Getting Started

To run and test the application, you'll need to set up a PostgreSQL server and configure it appropriately. Below are the steps to get started:

### Prerequisites

1. **Java 17 or higher**: Ensure you have Java installed on your machine.
2. **Maven**: Used for building the project. You can install it from [Maven’s official website](https://maven.apache.org/download.cgi) if it's not already installed.
3. **PostgreSQL**: Install PostgreSQL and ensure it's running on your local machine.

### Setting Up PostgreSQL

1. **Download and Install PostgreSQL**: Follow the [official PostgreSQL installation guide](https://www.postgresql.org/download/) for your operating system.

2. **Start the PostgreSQL Server**: Ensure the PostgreSQL server is running on your machine.

3. **Ensure the PostgreSQL server is accessible at the following JDBC URL:** jdbc:postgresql://localhost:5432/my_product
4. **Credentials**: Use postgres as both the username and password.


