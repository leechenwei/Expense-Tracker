# Expense Tracker - Full-Stack Application

This project is a complete full-stack Expense Tracker application. It features a React frontend that communicates with a secure, RESTful Spring Boot backend with a database.

## Features

- **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens).
- **CRUD Operations:** Authenticated users can Create, Read, Update, and Delete their personal expenses.
- **RESTful API:** A backend built with Spring Boot, Spring Security, and Spring Data JPA.
- **Modern Frontend:** A responsive and modern user interface built with React and Bootstrap.
- **Database:** Uses an in-memory H2 database for easy development and setup.

---

## Project Structure

The repository is organized into two main directories:

- `/expense-tracker-frontend`: Contains the React application.
- `/backend`: Contains the Spring Boot application.

---

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Node.js and npm:** (v16 or higher recommended). You can download it from [nodejs.org](https://nodejs.org/).
- **Java Development Kit (JDK):** (v17 or higher recommended). You can get it from [Adoptium](https://adoptium.net/).
- **Apache Maven:** (v3.6 or higher). This is used to build and run the backend. You can download it from [maven.apache.org](https://maven.apache.org/download.cgi).

You can verify your installations by running:
```bash
node -v
npm -v
java -version
mvn -v
```

## Core Technologies Used

## Backend
- Java 17
- Spring Boot 3
- Spring Security (with JWT)
- Spring Data JPA / Hibernate
- Maven
- H2 Database

## Frontend
- React 18 (with Hooks)
- React Router DOM
- Axios
- Bootstrap 5
- jwt-decode

## Start up Project

## Backend
```bash
cd backend 
mvn spring-boot:run
```

## Frontend
```bash
cd expense-tracker-frontend
npm start
```

## How to Use the Application
- Navigate to http://localhost:3000 in your browser.
- You will be redirected to the login page.
- Click on the "Sign Up" link to create a new account.
- After successful registration, you will be redirected back to the login page.
- Log in with your new credentials.
- You will be taken to the dashboard, where you can add, view, edit, and delete your expenses.