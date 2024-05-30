# Task Management Tool

## Overview

This project is a software development management tool, similar to Trello, designed to assist in managing tasks efficiently. The tool allows users to create and manage tasks with specific descriptions and start and end dates. The application includes a user authentication system with login and registration views, a task creation view, and a main view that displays all tasks in a data table format.

## Features

- **User Authentication**:
  - Login view
  - Registration view

- **Task Management**:
  - Create new tasks with descriptions, start dates, and end dates
  - View all tasks in a main view data table

## Technologies Used

- **Programming Language**: Java
- **Database**: PostgreSQL

## Prerequisites

1. **PostgreSQL Installation**:
   - Ensure PostgreSQL is installed and running on your machine.

## Setup Instructions

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/VladoFilchev/MyTasks.git
    ```

2. **Set Up the Database**:
    - Ensure PostgreSQL is installed and running on your machine.
    - Create a database named `tms`:
    - Use the following connection details in your `DatabaseConnection` class:
      
         DB_URL = "jdbc:postgresql://localhost:5432/tms";
         USER = "postgres";
         PASSWORD = "1234";

3. **Build and Run the Application**:
    - Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
    - Build the project to ensure all dependencies are resolved.
    - Run the application.

## Team Members

- Vladimir Filchev
- Aleksandar Hristov

